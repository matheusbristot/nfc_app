package com.bristot.lockthings.lock_unlock.fragment

import android.media.RingtoneManager
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bristot.light.nfc.adapter.NfcBridgeAdapter
import com.bristot.lockthings.R
import com.bristot.lockthings.databinding.FragmentLockUnlockBinding
import com.bristot.lockthings.util.livedata.observeEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * A simple [LockUnlockFragment] subclass as the second destination in the navigation.
 */
class LockUnlockFragment : Fragment() {

    private var _binding: FragmentLockUnlockBinding? = null

    private val binding get() = _binding!!

    private val args: LockUnlockFragmentArgs by navArgs()

    private val nfcBridgeAdapter: NfcBridgeAdapter by inject { parametersOf(args.lockId) }

    private val viewModel: LockUnLockViewModel by viewModel { parametersOf(args.lockId) }

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockUnlockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        subscribeUi()
    }

    override fun onResume() {
        super.onResume()
        nfcBridgeAdapter.cantReader(viewModel::onCantReader)
    }

    override fun onStop() {
        super.onStop()
        nfcBridgeAdapter.disable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUi() {
        nfcBridgeAdapter.callbackForResultTagDiscovered(viewModel::onDiscoveredResult)
        binding.buttonLock.setOnClickListener {
            viewModel.onSendActionLock()
        }
        binding.buttonUnlock.setOnClickListener {
            viewModel.onSendActionUnLock()
        }
    }

    private fun subscribeUi() {
        viewModel.state.observeEvent(viewLifecycleOwner) { state ->
            binding.buttonLock.isVisible = true
            binding.buttonUnlock.isVisible = true
            subscribeActions(state)
            subscribeTagDetection(state)
            subscribeTicker(state)
            subscribeDeviceStatus(state)
        }
    }

    private fun subscribeDeviceStatus(state: LockUnlockState?) {
        (state as? LockUnlockState.TurnOnNFC)?.run {
            withoutNfcOn(message)
        }
        (state as? LockUnlockState.NotSupport)?.run {
            withoutNfcOn(message)
        }
    }

    private fun withoutNfcOn(message: String) {
        binding.buttonLock.isGone = true
        binding.buttonUnlock.isGone = true
        binding.timerContent.isInvisible = true
        renderMessage(getString(R.string.warning_title), message.toSpannable())
    }

    private fun subscribeTagDetection(state: LockUnlockState?) {
        (state as? LockUnlockState.WrongTag)?.run {
            renderMessage(getString(R.string.warning_title), message)
            hiddenComponents()
            playRingtone()
        }
        (state as? LockUnlockState.SuccessAction)?.run {
            renderMessage(getString(R.string.success_title), message)
            hiddenComponents()
        }
    }

    private fun subscribeActions(state: LockUnlockState?) {
        (state as? LockUnlockState.Lock)?.run { showWaitNFCBottomSheet(action) }
        (state as? LockUnlockState.UnLock)?.run { showWaitNFCBottomSheet(action) }
    }

    private fun subscribeTicker(state: LockUnlockState?) {
        (state as? LockUnlockState.Expired)?.run {
            renderMessage(getString(R.string.warning_title), message)
            hiddenComponents()
        }
        (state as? LockUnlockState.Ticker)?.run {
            binding.timerContent.isVisible = true
            binding.timerContent.text = message
        }
    }

    private fun hiddenComponents() {
        binding.timerContent.isInvisible = true
        navController.popBackStack()
        nfcBridgeAdapter.disable()
    }

    private fun renderMessage(
        title: String, message: Spanned
    ) = MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(getString(R.string.positive_button)) { dialogInterface, _ -> dialogInterface.dismiss() }
        .create()
        .show()


    private fun playRingtone() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(requireContext(), notification)
        ringtone.play()
    }

    private fun showWaitNFCBottomSheet(action: String) {
        nfcBridgeAdapter.enable()
        navController.navigate(
            LockUnlockFragmentDirections.actionLockUnlockFragmentToWaitRegisterBottomSheet(
                args.lockId,
                action
            )
        )
    }
}