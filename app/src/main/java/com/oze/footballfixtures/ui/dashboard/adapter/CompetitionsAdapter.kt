package com.oze.footballfixtures.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.R
import com.oze.footballfixtures.databinding.CompeteListItemBinding
import com.oze.footballfixtures.presentation.Competitions

class CompetitionsAdapter(
    private var competitionsList: List<Competitions>,
    private val listener: View.OnClickListener
): RecyclerView.Adapter<CompetitionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.compete_list_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return competitionsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.click = listener
        holder.binding?.competitions = competitionsList[position]
    }

    fun updateAdapter(list: List<Competitions>){
        competitionsList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: CompeteListItemBinding? = DataBindingUtil.bind(view)

        init {
            view.tag = binding
        }

    }
}