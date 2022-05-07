package com.mabnets.videouploaderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabnets.videouploaderapp.databinding.VideostuffBinding
import com.mabnets.videouploaderapp.models.Healings


class Healadapter (private val listener: OnItemClickListner) :
    PagingDataAdapter<Healings, Healadapter.HealViewHolder>(
        HEAL_COMPARATOR
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealViewHolder {
        val binding= VideostuffBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HealViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HealViewHolder, position: Int) {
        val currentitem=getItem(position);
        if(currentitem!=null){
            holder.bind(currentitem)
        }
    }

    inner class HealViewHolder(private val binding: VideostuffBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }


            }
        }

        fun bind(heals: Healings) {
            binding.tvvmsgs.text = heals.message

            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy")
            val date = java.util.Date(heals.time.toLong() * 1000)
            binding.tme.text=sdf.format(date)
            binding.btnbtn.setOnClickListener {
                    listener.onItemClick(heals)
            }

        }
    }

    interface OnItemClickListner {
        fun onItemClick(heals: Healings)
    }

    companion object {
        private val HEAL_COMPARATOR = object : DiffUtil.ItemCallback<Healings>() {
            override fun areItemsTheSame(oldItem: Healings, newItem: Healings) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Healings, newItem: Healings
            ) = oldItem == newItem

        }
    }


}