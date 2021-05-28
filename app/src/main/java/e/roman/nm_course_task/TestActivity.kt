package e.roman.nm_course_task

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import java.nio.channels.FileLock
import kotlin.math.*

class TestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imView: ImageView
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    private lateinit var firstRadioBtn: RadioButton
    private lateinit var secondRadioBtn: RadioButton
    private lateinit var averageBtn: Button
    private lateinit var simpleBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        imView = findViewById(R.id.im_view)
        paint = Paint()
        paint.color = Color.BLACK
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        imView.setImageBitmap(bitmap)
        canvas = Canvas(bitmap)
        firstRadioBtn = findViewById(R.id.radio_first)
        secondRadioBtn = findViewById(R.id.radio_second)
        averageBtn = findViewById(R.id.btn_average)
        simpleBtn = findViewById(R.id.btn_simple)
        averageBtn.setOnClickListener(this)
        simpleBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        canvas.drawColor(Color.WHITE)
        val x = mutableListOf<Float>()
        val y = mutableListOf<Float>()
        val a1: Float
        val b1: Float
        if (firstRadioBtn.isChecked) {
            a1 = 0f
            b1 = 7f
        } else {
            a1 = 2f
            b1 = 4f
        }
        val h = (b1 - a1) / 3f
        val step = 0.1f
        var xTmp = a1
        if (firstRadioBtn.isChecked) {
            while (xTmp < b1) {
                x.add(xTmp)
                y.add(2f.pow(xTmp) / 12f)
                xTmp += h
            }
            x.add(xTmp)
            y.add(2f.pow(xTmp) / 12f)
        }
        else {
            while (xTmp < b1) {
                x.add(xTmp)
                y.add(1 / xTmp)
                xTmp += h
            }
            x.add(xTmp)
            y.add(1 / xTmp)
        }
        val a = mutableListOf<Float>()
        val b = mutableListOf<Float>()
        val c = mutableListOf<Float>()
        for (i in 0 .. x.lastIndex - 2) {
            a.add(ln((y[i + 2] - y[i + 1]) / (y[i + 1] - y[i])) / (x[i + 2] - x[i + 1]))
            c.add((y[i + 1] * y[i + 1] - y[i] * y[i + 2]) / (2f * y[i + 1] - y[i] - y[i + 2]))
            b.add((y[i + 2] - c.last()) * exp(-a.last() * x[i + 2]))
        }
        val multer: Float
        val xAver = x.sum() / x.size
        val yAver = y.sum() / y.size
        multer = if ((x.last() - x[0]) > (y.last() - y[0])) {
            450f / (xAver - x[0])
        }
        else {
            450f / (y.last() - yAver)
        }
        val sizeX = -x[0] * multer + 25f//250f
        val sizeY = max(y[0], y.last()) * multer + 25f
        paint.color = Color.BLACK
        if (firstRadioBtn.isChecked)
            Drawer.drawExactFunction(a1, b1, step, multer, sizeX + 10f, sizeY + 10f, canvas, paint) {xFix -> 2f.pow(xFix) / 12f}
        else
            Drawer.drawExactFunction(a1, b1, step, multer, sizeX + 10f, sizeY + 10f, canvas, paint) {xFix -> 1f / xFix}
        paint.color = Color.BLUE
        if (view.id == R.id.btn_average)
            Drawer.drawSplineWithAverage(x, y, a, b, c, step, multer, sizeX, sizeY, canvas, paint)
        else
            Drawer.drawSplineWithSimple(x, y, a, b, c, step, multer, sizeX, sizeY, canvas, paint)
        paint.color = Color.RED
        Drawer.drawPoints(x, y, multer, sizeX, sizeY, canvas, paint)
        canvas.setBitmap(bitmap)
    }
}