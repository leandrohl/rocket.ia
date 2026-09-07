package com.example.rocketia.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.rocketia.R
import com.example.rocketia.databinding.FragmentWelcomeBinding
import com.example.rocketia.ui.event.WelcomeUiEvent
import com.example.rocketia.ui.viewmodel.WelcomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment() : Fragment() {

    private val viewModel: WelcomeViewModel by viewModel()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onEvent(event = WelcomeUiEvent.CheckHasSelectedStack)

        setupObservers()

        with(binding) {
            btnWelcomeStart.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_chooseStackFragment)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.hasSelectedStack?.let { hasSelectedStack ->
                        if (hasSelectedStack) {
                            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                        } else {
                            binding.pbWelcomeLoading.visibility = View.GONE
                            binding.llWelcomeContainer.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}