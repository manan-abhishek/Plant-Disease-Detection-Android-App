package com.example.androidproject


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyMessage: TextView
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.historyRecyclerView)
        emptyMessage = findViewById(R.id.emptyMessage)

        val historyList = getHistoryData()

        if (historyList.isEmpty()) {
            emptyMessage.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
        } else {
            emptyMessage.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE
            adapter = HistoryAdapter(historyList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    private fun getHistoryData(): List<HistoryItem> {
        return emptyList()
    }
}
