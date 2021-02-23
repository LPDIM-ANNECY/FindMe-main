package fr.test200.findme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test : Button = findViewById(R.id.btn_connection)

        test.setOnClickListener {
            Toast.makeText(this, test.text, Toast.LENGTH_LONG).show()
        }
    }
}