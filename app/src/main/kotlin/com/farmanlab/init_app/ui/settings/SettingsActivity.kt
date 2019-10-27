package com.farmanlab.init_app.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.farmanlab.init_app.R
import com.farmanlab.init_app.databinding.ActivitySettingsBinding
import com.farmanlab.init_app.ui.settings.items.SettingsItem
import com.farmanlab.init_app.widget.EasyDividerItemDecoration
import com.farmanlab.kodeinextension.retainedKodeinWithCopy
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class SettingsActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by retainedKodeinWithCopy {}

    private lateinit var bind: ActivitySettingsBinding
    private val adapter = GroupAdapter<GroupieViewHolder<*>>()
    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind = DataBindingUtil.setContentView<ActivitySettingsBinding>(
            this,
            R.layout.activity_settings
        ).apply {
            lifecycleOwner = this@SettingsActivity
            viewModel = this@SettingsActivity.viewModel
        }
        bind.recyclerViewRoot.apply {
            adapter = this@SettingsActivity.adapter
            addItemDecoration(
                EasyDividerItemDecoration(
                    this@SettingsActivity,
                    R.drawable.divider_vertical,
                    showAfterLastItem = true
                )
            )
        }
        observeViewModel()
        viewModel.onFetchSettings()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.settings.observe(this, Observer {
            adapter.update(createItem(*it.toTypedArray()))
        })
    }

    private val onSettingItemClicked = { data: SettingsContract.Setting ->
        when(data.id) {
            ID_PRIVACY_POLICY -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.uri_privacy_policy))))
            }
            ID_OPEN_SOURCE_LICENSE -> {
                startActivity(
                    Intent(this, OssLicensesMenuActivity::class.java).apply {
                        putExtra("title", getString(R.string.oss_title))
                    }
                )
            }
            ID_FEEDBACK -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.uri_privacy_policy))))
            }
            ID_REVIEW_APP -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.uri_google_play_store_base_url, packageName))
                    )
                )
            }
        }
    }

    private fun createItem(vararg settings: SettingsContract.Setting): List<BindableItem<*>> =
        settings.map { setting ->
            SettingsItem(
                id = setting.id,
                params = SettingsItem.Params(
                    title = setting.title,
                    description = setting.description
                ),
                data = setting,
                listener = onSettingItemClicked
            )
        }

    companion object {
        private const val ID_OPEN_SOURCE_LICENSE = "open_source_license"
        private const val ID_PRIVACY_POLICY = "privacy_policy"
        private const val ID_FEEDBACK = "feed_back"
        private const val ID_REVIEW_APP = "review_app"

        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}
