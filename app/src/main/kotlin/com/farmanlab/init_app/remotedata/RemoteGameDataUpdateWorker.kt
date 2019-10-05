package com.farmanlab.init_app.remotedata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.farmanlab.init_app.domain.gamedata.GameDataUpdateUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.runBlocking
import map
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class RemoteGameDataUpdateWorker(
    private val context: Context,
    private val param: WorkerParameters
) : CoroutineWorker(context, param), KodeinAware {
    override val kodein: Kodein by kodein(context)
    private val gameDataUpdateUseCase: GameDataUpdateUseCase by instance()

    override suspend fun doWork(): Result {
        return try {
            val firebase = FirebaseRemoteConfig.getInstance()
            runBlocking {
                suspendCoroutine<Task<Boolean>> { continuous ->
                    firebase.fetchAndActivate()
                        .addOnCompleteListener { continuous.resume(it) }
                        .addOnFailureListener { continuous.resumeWithException(it) }
                }
            }
            gameDataUpdateUseCase.updateData(
                GameDataUpdateUseCase.RequestParams(
                    apiToken = firebase.getString(API_TOKEN_KEY)
                )
            )
            if (param.tags.contains(INIT_WORKER_NAME)) {
                launchWorker(context, LocalDateTime.now())
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            if (runAttemptCount < MAX_RERUN_ATTEMPT_COUNT) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        private const val WORKER_NAME = "remote_data"
        private const val INIT_WORKER_NAME = "remote_data_init"

        private const val MAX_RERUN_ATTEMPT_COUNT = 3
        private const val API_TOKEN_KEY = "init_app_db_api_key"

        fun launchWorker(context: Context, now: LocalDateTime) {
            internalLaunchWorker(context, calculateDiffTime(now))
        }

        fun launchWorkerJustNow(context: Context) {
            WorkManager.getInstance(context).enqueueUniqueWork(
                INIT_WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<RemoteGameDataUpdateWorker>(
                )
                    .setInitialDelay(0, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .addTag(INIT_WORKER_NAME)
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MINUTES
                    )
                    .build()
            )
        }

        fun getResult(context: Context): LiveData<WorkInfo?> =
            WorkManager.getInstance(context).getWorkInfosByTagLiveData(WORKER_NAME).map {
                it.firstOrNull()
            }

        fun getInitResultIfLaunching(context: Context): LiveData<WorkInfo?> =
            WorkManager.getInstance(context).getWorkInfosByTagLiveData(INIT_WORKER_NAME).map {
                it.firstOrNull()
            }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(WORKER_NAME)
        }

        private fun internalLaunchWorker(
            context: Context,
            diffTimeMinutes: Long
        ) {
            Timber.d("RemoteData Worker will launch after $diffTimeMinutes minutes.")
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<RemoteGameDataUpdateWorker>(
                    1L, TimeUnit.DAYS
                )
                    .setInitialDelay(diffTimeMinutes, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .addTag(WORKER_NAME)
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MINUTES
                    )
                    .build()
            )
        }

        private fun calculateDiffTime(now: LocalDateTime): Long = now
            .withHour(Random.nextInt(2, 3))
            .withMinute(Random.nextInt(0, 59))
            .apply {
                if (isAfter(now)) {
                    plusDays(1)
                }
            }
            .let { now.until(it, ChronoUnit.MINUTES) }
    }
}
