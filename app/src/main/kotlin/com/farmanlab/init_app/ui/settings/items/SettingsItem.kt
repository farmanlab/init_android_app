package com.farmanlab.init_app.ui.settings.items

import com.farmanlab.init_app.R
import com.farmanlab.init_app.databinding.ListItemSettingsParentBinding
import com.farmanlab.init_app.ui.settings.SettingsContract
import com.xwray.groupie.databinding.BindableItem

data class SettingsItem(
    val id: String,
    val params: Params,
    val data: SettingsContract.Setting,
    val listener: ((SettingsContract.Setting) -> Unit)?
) : BindableItem<ListItemSettingsParentBinding>() {
    override fun getLayout(): Int = R.layout.list_item_settings_parent

    override fun bind(viewBinding: ListItemSettingsParentBinding, position: Int) {
        viewBinding.data = params
        viewBinding.root.setOnClickListener { listener?.invoke(data) }
    }

    data class Params(
        val title: String,
        val description: String?
    )
}
