package com.example.androidproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class HistoryItem(
    val index: Int,
    val date: String,
    val crop: String,
    val disease: String,
    val confidence: String
)

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val index: TextView = view.findViewById(R.id.colIndex)
        val date: TextView = view.findViewById(R.id.colDate)
        val crop: TextView = view.findViewById(R.id.colCrop)
        val disease: TextView = view.findViewById(R.id.colDisease)
        val confidence: TextView = view.findViewById(R.id.colConfidence)
        val action: TextView = view.findViewById(R.id.colAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.index.text = item.index.toString()
        holder.date.text = item.date
        holder.crop.text = item.crop
        holder.disease.text = item.disease
        holder.confidence.text = item.confidence
        holder.action.text = "View"
    }

    override fun getItemCount(): Int = historyList.size
}
