package com.bristot.lockthings.lock_unlock.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.bristot.lockthings.R
import com.bristot.lockthings.databinding.BottomSheetWaitRegirestedTagBinding
import com.bristot.lockthings.util.livedata.observeEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WaitConnectTagBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetWaitRegirestedTagBinding

    private val args: WaitConnectTagBottomSheetArgs by navArgs()

    private val viewModel: WaitConnectTagBottomViewModel by viewModel {
        parametersOf(
            args.lockId,
            args.action
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetWaitRegirestedTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        subscribe()
        viewModel.onWait()
    }

    private fun subscribe() {
        viewModel.message.observeEvent(viewLifecycleOwner) { message ->
            binding.tagName.text = message
        }
    }
}