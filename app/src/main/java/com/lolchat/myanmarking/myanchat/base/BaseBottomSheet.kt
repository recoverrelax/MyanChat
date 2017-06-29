package com.lolchat.myanmarking.myanchat.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    open val cancelable: Boolean = false

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        isCancelable = cancelable
        context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }
}