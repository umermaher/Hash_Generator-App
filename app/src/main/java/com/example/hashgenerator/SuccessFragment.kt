package com.example.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {
    private val args:SuccessFragmentArgs by navArgs()
    private var _binding:FragmentSuccessBinding?=null
    private val binding get() =_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSuccessBinding.inflate(inflater,container, false)

        binding.hashText.text = args.hash

        binding.copyBtn.setOnClickListener {
            onCopyClicked()
        }

        return binding.root
    }

    private fun onCopyClicked() {
        lifecycleScope.launch {
            animateCopyBtn()
        }
        copiedToClipBoard(args.hash)
    }

    private fun copiedToClipBoard(hash:String) {
        val clipboardManager=requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData=ClipData.newPlainText("Encrypted Text",hash)
        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun animateCopyBtn() {
        binding.copyBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_tick))
        delay(2000L)
        binding.copyBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_copy))
    }
}