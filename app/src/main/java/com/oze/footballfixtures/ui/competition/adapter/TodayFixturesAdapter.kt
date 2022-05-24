package com.oze.footballfixtures.ui.competition.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.R
import com.oze.footballfixtures.databinding.CompetitionListItemBinding
import com.oze.footballfixtures.presentation.Match

class TodayFixturesAdapter(
    private var matchList: List<Match>
): RecyclerView.Adapter<TodayFixturesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.competition_list_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.match = matchList[position]
    }

    fun updateAdapter(value: List<Match>){
        matchList = value
        return notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding: CompetitionListItemBinding? = DataBindingUtil.bind(view)
        init {
            view.tag = binding
        }
    }
}