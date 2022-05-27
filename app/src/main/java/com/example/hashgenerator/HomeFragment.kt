package com.example.hashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)  //create option menu in toolbar for the fragment

        binding.generateBtn.setOnClickListener{
            onGenerateClicked()
        }

        return binding.root
    }

    private fun onGenerateClicked() {
        if(binding.plainEditText.text.isEmpty()){
            Snackbar.make(binding.root,"Field empty.",Snackbar.LENGTH_SHORT).setAction("Ok"){}.show()
        }else{
            lifecycleScope.launch {
                applyAnimation()
                navigateToSuccess(getHashData())
            }
        }
    }

    private fun getHashData() :String{
        val algorithm=binding.autoCompleteTextView.text.toString()
        val plainText=binding.plainEditText.text.toString()
        return homeViewModel.getHash(plainText,algorithm)
    }

    private suspend fun applyAnimation() {
        binding.generateBtn.isClickable=false
        binding.titleText.animate().alpha(0f).duration=500L
        binding.generateBtn.animate().alpha(0f).duration=500L
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).duration=400L
        binding.plainEditText.animate().alpha(0f).translationXBy(-1200f).duration=400L

        delay(300L)

        binding.successBackground.animate().alpha(1f).duration=600L
        binding.successBackground.animate().rotationBy(720f).duration=800L
        binding.successBackground.animate().scaleXBy(900f).duration=800L
        binding.successBackground.animate().scaleYBy(900f).duration=800L

        delay(500L)

        binding.successImage.animate().alpha(1f).duration=1000L

        delay(1500L)
    }

    private fun navigateToSuccess(hash:String){
        val directions=HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_clear->{
                binding.plainEditText.text.clear()
                Toast.makeText(requireContext(),"Cleared",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val hashAlgorithm=resources.getStringArray(R.array.hash_algorithms)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item,hashAlgorithm)
        binding.autoCompleteTextView.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding=null
    }
}