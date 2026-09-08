package com.example.rocketia.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rocketia.R
import com.example.rocketia.databinding.FragmentAiChatHistoryBinding
import com.example.rocketia.ui.adapter.AiChatAdapter
import com.example.rocketia.ui.event.AIChatHistoryEvent
import com.example.rocketia.ui.viewmodel.AIChatHistoryViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class AIChatHistoryFragment : Fragment() {

    private val viewModel: AIChatHistoryViewModel by viewModel()

    private var _binding: FragmentAiChatHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiChatHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            setupStackChips()

            binding.rvHistoryAIChat.adapter = AiChatAdapter()
        }
    }


    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStack.collect { selectedStack ->
                        selectedStack?.let {
                            val selectedStackChipId = binding.getStackChipId(selectedStack)
                            selectedStackChipId?.let {
                                viewModel.onEvent(
                                    AIChatHistoryEvent.SelectStack(
                                        selectedStack,
                                        selectedStackChipId
                                    )
                                )
                            }
                        }
                    }

                }

                launch {
                    viewModel.selectedStackChipId.collect { selectedStackChipId ->
                        selectedStackChipId?.let {
                            binding.changeSelectedStack(selectedStackChipId)
                        }
                    }

                }

                launch {
                    viewModel.aiChatHistoryBySelectedStack.collect { aiChatBySelectedStack ->
                        val aiChatAdapter = binding.rvHistoryAIChat.adapter as? AiChatAdapter
                        aiChatAdapter?.apply {
                            submitList(aiChatBySelectedStack)

                            binding.rvHistoryAIChat.smoothScrollToPosition(0)
                        }
                    }
                }
            }
        }
    }

    private fun FragmentAiChatHistoryBinding.setupStackChips() {
        flwFilterStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip?.setOnClickListener {
                viewModel.onEvent(
                    event = AIChatHistoryEvent.SelectStack(
                        selectedStackName = stackChip.text.toString(),
                        selectedStackChipId = stackChipId
                    )
                )
            }
        }
    }

    private fun FragmentAiChatHistoryBinding.getStackChipId(stackName: String): Int? =
        flwFilterStackOptions.referencedIds.find { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)
            stackChip.text == stackName
        }


    private fun FragmentAiChatHistoryBinding.changeSelectedStack(selectedStackChipId: Int) {
        flwFilterStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip?.apply {
                setChipStrokeColorResource(
                    if (stackChipId == selectedStackChipId)
                        R.color.white
                    else
                        R.color.border_default
                )
                isChecked = stackChip.id == selectedStackChipId
            }
        }
    }
}