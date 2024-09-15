package com.example.merqurinotes.utils.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.merqurinotes.R
import com.example.merqurinotes.utils.GenCallBack

object DialogUtils {

    private var progressDialog: AlertDialog? = null
    fun showLoadingDialog(activity: Activity, message: String = "Loading...") {
        if (progressDialog == null) {
            val builder = AlertDialog.Builder(activity)
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null)
            val loadingText = dialogView.findViewById<TextView>(R.id.loading_text)
            loadingText.text = message
            builder.setView(dialogView)
                .setCancelable(false)

            progressDialog = builder.create()
        }
        progressDialog?.show()
    }

    fun shutDownLoadingDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    fun popSimpleOkayDialog(
        activity: AppCompatActivity,
        title: String,
        message: String,
        proceedCallback: GenCallBack? = null
    ) {
        GenericDialogFragment.show(
            activity.supportFragmentManager,
            GenericDialogFragment.Mode.OK_ONLY,
            title,
            message,
            activity.getString(R.string.dialog_button_ok),
            proceedCallback = proceedCallback
        )
    }

    fun showSimpleOkDialog(
        context: Context,
        title: String = "Alert",
        message: String = "This is a message",
        positiveButtonText: String = "OK",
        positiveButtonAction: (() -> Unit)? = null,
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        // Set positive button action
        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            dialog.dismiss()
            positiveButtonAction?.invoke()
        }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }

    fun showNormalDialog(
        context: Context,
        title: String = "Alert",
        message: String = "This is a message",
        positiveButtonText: String = "OK",
        negativeButtonText: String? = null,
        positiveButtonAction: (() -> Unit)? = null,
        negativeButtonAction: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        // Set positive button action
        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            dialog.dismiss()
            positiveButtonAction?.invoke()
        }

        // Set negative button if provided
        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
                negativeButtonAction?.invoke()
            }
        }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }
}