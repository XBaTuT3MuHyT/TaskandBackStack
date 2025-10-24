package com.example.taskandbackstack

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class ActivityA : AppCompatActivity() {

    private var currentColorForActivityB: Int = 0

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setShowWhenLocked(true)
        setTurnScreenOn(true)


        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("TaskInfo", "${this::class.simpleName} started in taskId = $taskId")

        currentColorForActivityB = savedInstanceState?.getInt(
            "current_color",
            ContextCompat.getColor(this, R.color.second_activity)
        ) ?: ContextCompat.getColor(this, R.color.second_activity)

        val editText = findViewById<EditText>(R.id.editTextNumber)
        val root = findViewById<View>(R.id.main)
        val changeActivityButton = findViewById<Button>(R.id.activity_a_button)
        val changeColorButton = findViewById<Button>(R.id.button_color)
        val generateColorBtn = findViewById<ImageButton>(R.id.btn_generate_color)

        root.setBackgroundColor(ContextCompat.getColor(this, R.color.first_activity))
        setEditText(editText, currentColorForActivityB)

        changeActivityButton.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.putExtra("color", currentColorForActivityB)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        changeColorButton.setOnClickListener {
            val textColor = editText.text.toString().trim()
            if (isValidColorCode(textColor)){
                currentColorForActivityB = Color.parseColor(textColor)
                setEditText(editText, currentColorForActivityB)
            }else {
                Toast.makeText(this, "Некорректный код цвета", Toast.LENGTH_SHORT).show()
            }
        }
        generateColorBtn.setOnClickListener {
            val color = (0xFF000000.toInt() or (Random.nextInt(0xFFFFFF)))
            setEditText(editText, color)
            currentColorForActivityB = color
        }


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("TaskInfo", "${this::class.simpleName}  onNewIntent() in taskId = $taskId")
    }
    override fun onResume() {
        super.onResume()
        Log.d("TaskInfo", "${this::class.simpleName} onResume() in taskId = $taskId")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current_color", currentColorForActivityB)
    }

    fun isValidColorCode(input: String): Boolean {
        if (input.isEmpty()) return false
        return try {
            Color.parseColor(input)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun setEditText(editText: EditText, color: Int){
        val background = editText.background as GradientDrawable
        background.setColor(color)
        val colorHex = String.format("#%06X", 0xFFFFFF and color)
        editText.setText(colorHex)
    }

}