package com.bristot.lockthings.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bristot.lockthings.databinding.FragmentRegisterBinding
import com.bristot.lockthings.util.extensions.textview.observeChanges
import com.bristot.lockthings.util.extensions.toast.shortToast
import com.bristot.lockthings.util.livedata.observeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [LockIdRegisterFragment] subclass as the default destination in the navigation.
 */
class LockIdRegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val registerLockIdViewModel: RegisterLockIdViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.inputTag.observeChanges { text ->
            println(text)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(registerLockIdViewModel)
        subscribe(view)
        setupUI()
    }

    private fun setupUI() {
        binding.addTagButton.setOnClickListener {
            registerLockIdViewModel.onCheckLockIdentifier(binding.inputTag.text.toString())
        }
    }

    private fun subscribe(view: View) {
        registerLockIdViewModel.isIdentifierValid.observeEvent(viewLifecycleOwner) { state ->
            success(state, view)
            error(state, view)
        }
    }

    private fun success(
        state: LockIdRegisterState?,
        view: View
    ) {
        (state as? LockIdRegisterState.CreatedLock)?.run {
            showMessage(message, view)
            val args =
                LockIdRegisterFragmentDirections.actionRegisterFragmentToLockUnlockFragment(id)
            findNavController().navigate(args)
        }
    }

    private fun error(
        state: LockIdRegisterState?,
        view: View
    ) {
        (state as? LockIdRegisterState.Error)?.run {
            showMessage(errorMessage, view)
        }
    }


    private fun showMessage(message: String, view: View) {
        view.context.shortToast(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}