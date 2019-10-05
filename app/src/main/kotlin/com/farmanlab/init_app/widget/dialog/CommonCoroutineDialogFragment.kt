package com.farmanlab.init_app.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import com.farmanlab.coroutinedialogfragment.CoroutineDialogFragment
import com.farmanlab.coroutinedialogfragment.DialogResult
import com.farmanlab.init_app.util.StringHolder

class CommonCoroutineDialogFragment : CoroutineDialogFragment<Unit>() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arg = requireArguments()
        return AlertDialog.Builder(requireActivity())
            .setTitle(arg.getString(ARG_TITLE))
            .setMessage(arg.getString(ARG_MESSAGE))
            .setPositiveButton(arg.getString(ARG_POSITIVE)) { _, _ ->
                channelViewModel.channel.offer(DialogResult.Ok(Unit))
                dialog?.dismiss()
            }
            .setCancelable(arg.getBoolean(ARG_CANCELABLE))
            .apply {
                arg.getString(ARG_NEGATIVE)?.let {
                    setNegativeButton(it) { _, _ ->
                        channelViewModel.channel.offer(DialogResult.Cancel)
                    }
                }
            }
            .apply {
                arg.getString(ARG_NEUTRAL)?.let {
                    setNeutralButton(it) { _, _ ->
                        channelViewModel.channel.offer(DialogResult.Neutral)
                    }
                }
            }
            .create()
    }

    data class Params(
        val title: StringHolder,
        val message: StringHolder,
        val positiveLabel: StringHolder,
        val negativeLabel: StringHolder? = null,
        val neutralLabel: StringHolder? = null,
        val cancelable: Boolean = true
    )

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_POSITIVE = "positive"
        private const val ARG_NEGATIVE = "negative"
        private const val ARG_NEUTRAL = "neutral"
        private const val ARG_CANCELABLE = "cancelable"

        fun newInstance(context: Context, params: Params): CommonCoroutineDialogFragment =
            CommonCoroutineDialogFragment().apply {
                arguments = bundleOf(
                    Pair(ARG_TITLE, params.title.getString(context)),
                    Pair(ARG_MESSAGE, params.message.getString(context)),
                    Pair(ARG_POSITIVE, params.positiveLabel.getString(context)),
                    Pair(ARG_NEGATIVE, params.negativeLabel?.getString(context)),
                    Pair(ARG_NEUTRAL, params.neutralLabel?.getString(context)),
                    Pair(ARG_CANCELABLE, params.cancelable)
                )
            }
    }
}
