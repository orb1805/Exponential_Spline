package e.roman.nm_course_task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var testBtn: Button
    private lateinit var pointsBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testBtn = findViewById(R.id.btn_test)
        pointsBtn = findViewById(R.id.btn_points)
        testBtn.setOnClickListener { startActivity(Intent(this, TestActivity::class.java)) }
        pointsBtn.setOnClickListener { startActivity(Intent(this, PointsActivity::class.java)) }
    }
}