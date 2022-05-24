package com.oze.footballfixtures.ui.compete.teamFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.R
import com.oze.footballfixtures.databinding.TeamListItemBinding
import com.oze.footballfixtures.presentation.Team

class TeamAdapter(
    private var teamList: List<Team>
): RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.team_list_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.team = teamList[position]
    }

    fun updateAdapter(value: List<Team>){
        teamList = value
        return notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: TeamListItemBinding? = DataBindingUtil.bind(view)
        init {
            view.tag = binding
        }
    }
}