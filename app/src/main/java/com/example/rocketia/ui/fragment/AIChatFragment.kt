package com.example.rocketia.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.rocketia.R
import com.example.rocketia.databinding.FragmentAIChatBinding
import com.example.rocketia.ui.adapter.AiChatAdapter
import com.example.rocketia.ui.event.AIChatEvent
import com.example.rocketia.ui.extension.gone
import com.example.rocketia.ui.extension.hideKeyboard
import com.example.rocketia.ui.extension.visible
import com.example.rocketia.ui.viewmodel.AIChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class AIChatFragment : Fragment() {
    private val viewModel: AIChatViewModel by viewModel()

    private var _binding: FragmentAIChatBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAIChatBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            clAIChatContainer.setOnClickListener {
                clearQuestionInputField()
            }
            val userSettingsPopMenu = PopupMenu(requireContext(), ibUserSettings)
            userSettingsPopMenu.setupUserSettingsPopMenu()


            binding.rvStudyAIChat.adapter = AiChatAdapter()

            ibUserSettings.setOnClickListener {
                userSettingsPopMenu.show()
            }

            tietAIQuestion.doOnTextChanged { text, start, before, count ->
                if (tietAIQuestion.error != null) {
                    tietAIQuestion.error = null
                }
            }

            btnSendAIQuestion.setOnClickListener {
                val questionText = tietAIQuestion.text.toString()
                if (questionText.isNotEmpty()) {
                    showLoadingAIChat()
                    viewModel.onEvent(event = AIChatEvent.SendUserQuestionToAI(questionText))

                    clearQuestionInputField()
                } else {
                    tietAIQuestion.error = "Campo obrigatório"
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStack.collect { selectedStack ->
                        selectedStack?.let {
                            binding.tvHelloWhichStackAreYouGoingToStudy.text = getString(R.string.ola_dev, selectedStack)
                            binding.tilAIQuestion.hint = getString(R.string.qual_a_sua_duvida_sobre, selectedStack)
                        }
                    }
                }

                launch {
                    viewModel.aiChatBySelectedStack.collect { aiChatBySelectedStack ->
                        val aiChatAdapter = binding.rvStudyAIChat.adapter as? AiChatAdapter
                        aiChatAdapter?.apply {
                            submitList(aiChatBySelectedStack)

                            binding.rvStudyAIChat.smoothScrollToPosition(0)
                            delay(200)
                            binding.showLoadedAIChat()
                        }
                    }
                }
            }
        }
    }

    private fun FragmentAIChatBinding.clearQuestionInputField() {
        tietAIQuestion.text?.clear()
        tietAIQuestion.text = null
        this.root.hideKeyboard()
    }

    private fun PopupMenu.setupUserSettingsPopMenu() {
        this.menuInflater.inflate(R.menu.user_settings_menu, this.menu)

        this.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_user_settings -> {
                    requireActivity().findNavController(R.id.fcvMainContainer).navigate(R.id.action_homeFragment_to_chooseStackFragment)
                    true
                }
                else -> false
            }
        }

    }


    private fun FragmentAIChatBinding.showLoadingAIChat(){
        pbAIChatLoading.visible()
        rvStudyAIChat.gone()
    }


    private fun FragmentAIChatBinding.showLoadedAIChat(){
        pbAIChatLoading.gone()
        rvStudyAIChat.visible()
    }
}