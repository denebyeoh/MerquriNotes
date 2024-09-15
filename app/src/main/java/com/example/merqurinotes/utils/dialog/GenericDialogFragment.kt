package com.example.merqurinotes.utils.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.axs.base.BaseDialogFragment
import com.example.merqurinotes.R
import com.example.merqurinotes.databinding.FragmentGenericDialogBinding
import com.example.merqurinotes.utils.GenCallBack

class GenericDialogFragment :
    BaseDialogFragment<FragmentGenericDialogBinding>(
        R.layout.fragment_generic_dialog,
        { view -> FragmentGenericDialogBinding.bind(view) }) {

    enum class Mode {
        OK_ONLY,
        POSITIVE_NEGATIVE,
        TWO_ACTION_ALL_BLUE,
        TWO_ACTION_WITH_GRAY,
        THREE_ACTION
    }

    companion object {

        private const val GENERIC_DIALOG_TAG = "GENERIC_DIALOG"

        @JvmStatic
        private fun newInstance() =
            GenericDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }

        @JvmStatic
        fun show(
            manager: FragmentManager,
            mode: Mode = Mode.OK_ONLY,
            title: String = "",
            message: String = "",
            proceedText: String = "",
            proceedCallback: GenCallBack? = null,
            negativeText: String = "",
            negativeCallback: GenCallBack? = null,
            actionOneText: String = "",
            actionOneCallback: GenCallBack? = null,
            actionTwoText: String = "",
            actionTwoCallback: GenCallBack? = null,
            willDismissOnProceed: Boolean = true
        ): GenericDialogFragment {
            return newInstance().apply {
                this.mode = mode
                this.title = title
                this.message = message
                this.proceedText = proceedText
                this.proceedCallback = proceedCallback
                this.negativeText = negativeText
                this.negativeCallback = negativeCallback
                this.actionOneText = actionOneText
                this.actionOneCallback = actionOneCallback
                this.actionTwoText = actionTwoText
                this.actionTwoCallback = actionTwoCallback
                this.willDismissOnProceed = willDismissOnProceed
                show(manager, GENERIC_DIALOG_TAG)
            }
        }
    }

    private var mode = Mode.OK_ONLY
    private var title = ""
    private var message = ""
    private var proceedText = ""
    private var negativeText = ""
    private var negativeCallback: GenCallBack? = null // Negative action
    private var actionOneText = ""
    private var actionOneCallback: GenCallBack? = null // First button action
    private var actionTwoText = ""
    private var actionTwoCallback: GenCallBack? = null // Second button action
    private var willDismissOnProceed = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        binding.apply {

            initTitleAndMessage()

            when (mode) {
                Mode.OK_ONLY -> {
                    toggleNegativeButton(false)
                    toggleTwoButtonLayout(true)
                    toggleThreeButtonLayout(false)
                    okButton.setOnClickListenerWithDismiss { proceedCallback?.onReturn(0) }
                    okButton.setOnClickListenerWithDismiss {}
                }

                Mode.POSITIVE_NEGATIVE -> {
                    toggleNegativeButton(true)
                    toggleTwoButtonLayout(true)
                    toggleThreeButtonLayout(false)
                    okButton.apply {
                        if (willDismissOnProceed) {
                            setOnClickListenerWithDismiss { proceedCallback?.onReturn(0) }
                        } else {
                            setOnClickListener { proceedCallback?.onReturn(0) }
                        }
                    }
                    negativeButton.setOnClickListenerWithDismiss {
                        negativeCallback?.onReturn(
                            0
                        )
                    }
                }

                Mode.THREE_ACTION -> {
                    toggleNegativeButton(false)
                    toggleTwoButtonLayout(false)
                    toggleThreeButtonLayout(true)
                    actionOneButton.setOnClickListenerWithDismiss {
                        actionOneCallback?.onReturn(
                            0
                        )
                    }
                    actionTwoButton.setOnClickListenerWithDismiss {
                        actionTwoCallback?.onReturn(
                            0
                        )
                    }
                    actionNegativeButton.setOnClickListenerWithDismiss {
                        negativeCallback?.onReturn(
                            0
                        )
                    }
                }

                Mode.TWO_ACTION_ALL_BLUE -> {
                    toggleNegativeButton(false)
                    toggleTwoButtonLayout(false)
                    toggleThreeButtonLayout(true)
                    actionOneButton.setOnClickListenerWithDismiss {
                        actionOneCallback?.onReturn(
                            0
                        )
                    }
                    actionTwoButton.setOnClickListenerWithDismiss {
                        actionTwoCallback?.onReturn(
                            0
                        )
                    }
                    actionNegativeButton.visibility = View.GONE
                }

                Mode.TWO_ACTION_WITH_GRAY -> {
                    toggleNegativeButton(false)
                    toggleTwoButtonLayout(false)
                    toggleThreeButtonLayout(true)
                    actionOneButton.setOnClickListenerWithDismiss {
                        actionOneCallback?.onReturn(
                            0
                        )
                    }
                    actionTwoButton.visibility = View.GONE
                    actionNegativeButton.setOnClickListenerWithDismiss {
                        negativeCallback?.onReturn(
                            0
                        )
                    }
                }
            }
        }
    }

    private fun FragmentGenericDialogBinding.initTitleAndMessage() {
        if (title.isNotBlank()) {
            dialogTitleTextview.text = title
            dialogTitleTextview.visibility = View.VISIBLE
        } else {
            dialogTitleTextview.visibility = View.GONE
        }

        if (message.isNotBlank()) {
            dialogSubtitleTextview.text = message
            dialogSubtitleTextview.visibility = View.VISIBLE
        } else {
            dialogSubtitleTextview.visibility = View.GONE
        }

        okButton.text = proceedText
        negativeButton.text = negativeText
        actionOneButton.text = actionOneText
        actionTwoButton.text = actionTwoText
        actionNegativeButton.text = negativeText
    }

    private fun FragmentGenericDialogBinding.toggleNegativeButton(isVisible: Boolean) {
        negativeButton.visibility = if (isVisible) View.VISIBLE else View.GONE
        negativeSpacer.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun FragmentGenericDialogBinding.toggleTwoButtonLayout(isVisible: Boolean) {
        twoButtonLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun FragmentGenericDialogBinding.toggleThreeButtonLayout(isVisible: Boolean) {
        threeButtonLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

//    private fun Button.setOnClickListenerWithDismiss(callback: GenCallBack?) {
//        setOnClickListener {
//            callback?.onReturn(0)
//            dismiss()
//        }
//    }

    private fun Button.setOnClickListenerWithDismiss(function: () -> Unit?) {
        setOnClickListener {
            dismiss()
        }
    }
}