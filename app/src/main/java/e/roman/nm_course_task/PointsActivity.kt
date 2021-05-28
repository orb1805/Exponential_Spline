package e.roman.nm_course_task

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.pow

class PointsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imView: ImageView
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    private lateinit var averageBtn: Button
    private lateinit var simpleBtn: Button
    private lateinit var aEt: EditText
    private lateinit var bEt: EditText
    private lateinit var yEts: MutableList<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)

        imView = findViewById(R.id.im_view)
        paint = Paint()
        paint.color = Color.BLACK
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        imView.setImageBitmap(bitmap)
        canvas = Canvas(bitmap)
        averageBtn = findViewById(R.id.btn_average)
        simpleBtn = findViewById(R.id.btn_simple)
        averageBtn.setOnClickListener(this)
        simpleBtn.setOnClickListener(this)
        yEts = mutableListOf()
        yEts.add(findViewById(R.id.et_y1))
        yEts.add(findViewById(R.id.et_y2))
        yEts.add(findViewById(R.id.et_y3))
        yEts.add(findViewById(R.id.et_y4))
        yEts.add(findViewById(R.id.et_y5))
        aEt = findViewById(R.id.et_a)
        bEt = findViewById(R.id.et_b)
    }

    override fun onClick(view: View) {
        canvas.drawColor(Color.WHITE)
        val x = mutableListOf<Float>()
        val a1 = aEt.text.toString().toFloat()
        val b1 = bEt.text.toString().toFloat()
        var xTmp = a1
        val h = (b1 - a1) / 4
        while (xTmp < b1) {
            x.add(xTmp)
            xTmp += h
        }
        x.add(b1)
        val y = mutableListOf<Float>()
        for (i in yEts)
            y.add(i.text.toString().toFloat())
        val step = 0.001f
        val a = mutableListOf<Float>()
        val b = mutableListOf<Float>()
        val c = mutableListOf<Float>()
        for (i in 0..x.lastIndex - 2) {
            a.add(ln((y[i + 2] - y[i + 1]) / (y[i + 1] - y[i])) / (x[i + 2] - x[i + 1]))
            c.add((y[i + 1] * y[i + 1] - y[i] * y[i + 2]) / (2f * y[i + 1] - y[i] - y[i + 2]))
            b.add((y[i + 2] - c.last()) * exp(-a.last() * x[i + 2]))
        }
        val multer: Float
        val xAver = x.sum() / x.size
        val yAver = y.sum() / y.size
        multer = if ((x.last() - x[0]) > (y.last() - y[0])) {
            450f / (xAver - x[0])
        } else {
            450f / (y.last() - yAver)
        }
        val sizeX = -x[0] * multer + 25f//250f
        val sizeY = max(y[0], y.last()) * multer + 25f
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