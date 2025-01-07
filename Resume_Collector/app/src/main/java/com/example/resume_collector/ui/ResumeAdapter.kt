package com.example.resume_collector.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.resume_collector.R
import com.example.resume_collector.data.Resume

class ResumeAdapter : ListAdapter<Resume, ResumeAdapter.ResumeViewHolder>(ResumeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resume, parent, false)
        return ResumeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResumeViewHolder, position: Int) {
        val resume = getItem(position)
        holder.bind(resume)
    }

    class ResumeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullNameTextView: TextView = itemView.findViewById(R.id.tvName)
        private val emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
        private val phoneTextView: TextView = itemView.findViewById(R.id.tvPhone)

        fun bind(resume: Resume) {
            fullNameTextView.text = resume.fullName
            emailTextView.text = resume.email
            phoneTextView.text = resume.phone
        }
    }

    class ResumeDiffCallback : DiffUtil.ItemCallback<Resume>() {
        override fun areItemsTheSame(oldItem: Resume, newItem: Resume): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Resume, newItem: Resume): Boolean {
            return oldItem == newItem
        }
    }
}
