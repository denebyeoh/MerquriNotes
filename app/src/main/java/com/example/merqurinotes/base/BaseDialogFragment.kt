package com.axs.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.example.merqurinotes.utils.GenCallBack

abstract class BaseDialogFragment<B : ViewBinding>(
    val contentLayoutId: Int,
    val bindingFactory: (View) -> B
) : DialogFragment() {

    protected lateinit var binding: B
    protected var activity: AppCompatActivity? = null

    var proceedCallback: GenCallBack? = null
    var cancelCallback: GenCallBack? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? AppCompatActivity).let { activity = it }
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(contentLayoutId, container, false)
        binding = bindingFactory(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    open fun reshow(activity: FragmentActivity) {}
}