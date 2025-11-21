package com.example.androidproject


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidproject.databinding.ActivityHelpBinding

class Help : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggleFAQ(binding.faq1Title, binding.faq1Content)
        toggleFAQ(binding.faq2Title, binding.faq2Content)
        toggleFAQ(binding.faq3Title, binding.faq3Content)
        toggleFAQ(binding.faq4Title, binding.faq4Content)
        toggleFAQ(binding.faq5Title, binding.faq5Content)
    }

    private fun toggleFAQ(titleView: View, contentView: View) {
        titleView.setOnClickListener {
            contentView.visibility =
                if (contentView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }
}
