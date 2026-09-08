package com.example.rocketia.ui.adapter

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.rocketia.R
import com.example.rocketia.databinding.ItemAiChatBalloonBinding
import com.example.rocketia.databinding.ItemUserChatBalloonBinding
import com.example.rocketia.domain.model.AIChatText
import io.noties.markwon.Markwon
import android.content.ClipboardManager
import android.widget.Toast

private const val AI_ANSWER_CLIP_DATA_LABEL = "Resposta da IA copiada"
class AiChatAdapter : ListAdapter<AIChatText, AiChatAdapter.AiChatViewHolder>(AIChatDiffCallback()) {

    class AiChatViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clipboardManager = binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        fun bindQuestion(question: String) {
            with(binding as ItemUserChatBalloonBinding) {
                tvUserQuestion.text = question
            }
        }
        fun bindAnswer(answer: String) {
            with(binding as ItemAiChatBalloonBinding) {
                val markwon = Markwon.create(binding.root.context)

                markwon.setMarkdown(tvAIAnswer, answer)

                tvAIAnswer.setOnLongClickListener {
                    val clipData = ClipData.newPlainText(AI_ANSWER_CLIP_DATA_LABEL, answer)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(binding.root.context, AI_ANSWER_CLIP_DATA_LABEL, Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AiChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            R.layout.item_user_chat_balloon -> {
                val binding = ItemUserChatBalloonBinding.inflate(inflater, parent, false)
                AiChatViewHolder(binding)
            }
            R.layout.item_ai_chat_balloon -> {
                val binding = ItemAiChatBalloonBinding.inflate(inflater, parent, false)
                AiChatViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: AiChatViewHolder,
        position: Int
    ) {
        when (val aiChatText = getItem(position)) {
            is AIChatText.UserQuestion -> holder.bindQuestion(aiChatText.question)
            is AIChatText.AiAnswer -> holder.bindAnswer(aiChatText.answer)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AIChatText.UserQuestion -> R.layout.item_user_chat_balloon
            is AIChatText.AiAnswer -> R.layout.item_ai_chat_balloon
        }
    }
}