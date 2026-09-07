package com.example.rocketia.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.rocketia.domain.model.AIChatText


class AIChatDiffCallback : DiffUtil.ItemCallback<AIChatText>() {
    override fun areItemsTheSame(oldItem: AIChatText, newItem: AIChatText): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AIChatText, newItem: AIChatText): Boolean {
        return oldItem == newItem
    }
}