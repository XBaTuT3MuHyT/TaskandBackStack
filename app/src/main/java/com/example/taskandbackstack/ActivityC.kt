package com.example.taskandbackstack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        Log.d("TaskInfo", "${this::class.simpleName} started in taskId = $taskId")

        val root = findViewById<View>(R.id.main)
        root.setBackgroundColor(ContextCompat.getColor(this, R.color.third_activity))

        val button = findViewById<Button>(R.id.activity_c_button)

        button.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("TaskInfo", "${this::class.simpleName} onResume() in taskId = $taskId")
    }
}