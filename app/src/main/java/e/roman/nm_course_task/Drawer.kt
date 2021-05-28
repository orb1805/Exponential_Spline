    package e.roman.nm_course_task

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.exp

class Drawer {

    companion object {

        fun drawExactFunction(a: Float, b: Float, step: Float, multer: Float, sizeX: Float, sizeY: Float, canvas: Canvas, paint: Paint, fExact: (x: Float) -> Float) {
            var xTmp = a
            while (xTmp < b) {
                canvas.drawLine(
                        xTmp * multer + sizeX,
                        -fExact(xTmp) * multer + sizeY,
                        (xTmp + step) * multer + sizeX,
                        -fExact(xTmp + step) * multer + sizeY,
                        paint
                )
                xTmp += step
            }
        }

        fun drawSplineWithAverage(x: MutableList<Float>, y: MutableList<Float>, a: MutableList<Float>, b: MutableList<Float>, c: MutableList<Float>, step: Float, multer: Float, sizeX: Float, sizeY: Float, canvas: Canvas, paint: Paint) {
            var xTmp = x[0]
            while (xTmp < x[1]) {
                canvas.drawLine(
                        xTmp * multer + sizeX,
                        -f(xTmp, a[0], b[0], c[0]) * multer + sizeY,
                        (xTmp + step) * multer + sizeX,
                        -f(xTmp + step, a[0], b[0], c[0]) * multer + sizeY,
                        paint
                )
                xTmp += step
            }
            for (i in 1 until x.lastIndex - 1) {
                xTmp = x[i]
                while (xTmp < x[i + 1]) {
                    canvas.drawLine(
                            xTmp * multer + sizeX,
                            -((f(xTmp, a[i], b[i], c[i]) + f(xTmp, a[i - 1], b[i - 1], c[i - 1])) / 2f) * multer + sizeY,
                            (xTmp + step) * multer + sizeX,
                            -((f(xTmp + step, a[i], b[i], c[i]) + f(xTmp + step, a[i - 1], b[i - 1], c[i - 1])) / 2f) * multer + sizeY,
                            paint
                    )
                    xTmp += step
                }
            }
            xTmp = x[x.lastIndex - 1]
            while (xTmp < x.last()) {
                canvas.drawLine(
                        xTmp * multer + sizeX,
                        -f(xTmp, a.last(), b.last(), c.last()) * multer + sizeY,
                        (xTmp + step) * multer + sizeX,
                        -f(xTmp + step, a.last(), b.last(), c.last()) * multer + sizeY,
                        paint
                )
                xTmp += step
            }
        }

        fun drawSplineWithSimple(x: MutableList<Float>, y: MutableList<Float>, a: MutableList<Float>, b: MutableList<Float>, c: MutableList<Float>, step: Float, multer: Float, sizeX: Float, sizeY: Float, canvas: Canvas, paint: Paint) {
            var xTmp = x[0]
            while (xTmp < x[1]) {
                canvas.drawLine(
                        xTmp * multer + sizeX,
                        -f(xTmp, a[0], b[0], c[0]) * multer + sizeY,
                        (xTmp + step) * multer + sizeX,
                        -f(xTmp + step, a[0], b[0], c[0]) * multer + sizeY,
                        paint
                )
                xTmp += step
            }
            for (i in 1 until x.lastIndex - 1) {
                xTmp = x[i]
                while (xTmp < x[i + 1]) {
                    canvas.drawLine(
                            xTmp * multer + sizeX,
                            -((x[i] - xTmp) * f(xTmp, a[i - 1], b[i - 1], c[i - 1]) + (xTmp - x[i - 1]) * f(xTmp, a[i], b[i], c[i])) / (x[i] - x[i - 1]) * multer + sizeY,
                            (xTmp + step) * multer + sizeX,
                            -((x[i] - xTmp - step) * f(xTmp + step, a[i - 1], b[i - 1], c[i - 1]) + (xTmp + step - x[i - 1]) * f(xTmp + step, a[i], b[i], c[i])) / (x[i] - x[i - 1]) * multer + sizeY,
                            paint
                    )
                    xTmp += step
                }
            }
            xTmp = x[x.lastIndex - 1]
            while (xTmp < x.last()) {
                canvas.drawLine(
                        xTmp * multer + sizeX,
                        -f(xTmp, a.last(), b.last(), c.last()) * multer + sizeY,
                        (xTmp + step) * multer + sizeX,
                        -f(xTmp + step, a.last(), b.last(), c.last()) * multer + sizeY,
                        paint
                )
                xTmp += step
            }
        }

        fun drawPoints(x: MutableList<Float>, y: MutableList<Float>, multer: Float, sizeX: Float, sizeY: Float, canvas: Canvas, paint: Paint) {
            for (i in x.indices) {
                canvas.drawCircle(
                        x[i] * multer + sizeX,
                        -y[i] * multer + sizeY,
                        10f,
                        paint
                )
            }
        }

        fun f(x: Float, a: Float, b: Float, c: Float): Float {
            return c + b * exp(a * x)
        }

    }

}