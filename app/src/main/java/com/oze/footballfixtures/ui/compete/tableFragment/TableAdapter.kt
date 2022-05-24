package com.oze.footballfixtures.ui.compete.tableFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.footballfixtures.R
import com.oze.footballfixtures.databinding.TableListItemBinding
import com.oze.footballfixtures.presentation.Table

class TableAdapter(
    private var tableList: List<Table>
): RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.table_list_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.tables = tableList[position]
    }

    fun updateAdapter(value: List<Table>){
        tableList = value
        return notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: TableListItemBinding? = DataBindingUtil.bind(view)
        init {
            view.tag = binding
        }
    }
}