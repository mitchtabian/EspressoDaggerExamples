package com.codingwithmitch.espressodaggerexamples.views


import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView


/**
 * ImageView that you can pinch to scale (zoom in and out)
 * Created by Mitch on 3/9/2018.
 */
class ScalingImageView : ImageView,
    View.OnTouchListener,
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener
{
    //shared constructing
    lateinit var imageContext: Context
    var scaleDetector: ScaleGestureDetector? = null
    var gestureDetector: GestureDetector? = null
    lateinit var myMatrix: Matrix
    lateinit var imageMatrixValues: FloatArray
    var mode = NONE
    // Scales
    var saveScale = 1f
    var minScale = 1f
    var maxScale = 4f
    // view dimensions
    var origWidth = 0f
    var origHeight = 0f
    var viewWidth = 0
    var viewHeight = 0
    var last = PointF()
    var start = PointF()

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        context?.run {
            sharedConstructing(this)
        }
    }

    private fun sharedConstructing(context: Context) {
        super.setClickable(true)
        imageContext = context
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
        myMatrix = Matrix()
        imageMatrixValues = FloatArray(9)
        imageMatrix = myMatrix
        setScaleType(ImageView.ScaleType.MATRIX)
        this.gestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val prevScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / prevScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / prevScale
            }
            if (origWidth * saveScale <= viewWidth
                || origHeight * saveScale <= viewHeight
            ) {
                myMatrix.postScale(
                    mScaleFactor, mScaleFactor, viewWidth / 2.toFloat(),
                    viewHeight / 2.toFloat()
                )
            } else {
                myMatrix.postScale(
                    mScaleFactor, mScaleFactor,
                    detector.focusX, detector.focusY
                )
            }
            fixTranslation()
            return true
        }
    }

    fun fitToScreen() {
        saveScale = 1f
        val scale: Float
        val drawable: Drawable? = drawable
        if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0)
            return
        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight
        val scaleX = viewWidth.toFloat() / imageWidth.toFloat()
        val scaleY = viewHeight.toFloat() / imageHeight.toFloat()
        scale = Math.min(scaleX, scaleY)
        myMatrix.setScale(scale, scale)
        // Center the image
        var redundantYSpace = (viewHeight.toFloat()
                - scale * imageHeight.toFloat())
        var redundantXSpace = (viewWidth.toFloat()
                - scale * imageWidth.toFloat())
        redundantYSpace /= 2.toFloat()
        redundantXSpace /= 2.toFloat()
        myMatrix.postTranslate(redundantXSpace, redundantYSpace)
        origWidth = viewWidth - 2 * redundantXSpace
        origHeight = viewHeight - 2 * redundantYSpace
        imageMatrix = myMatrix
    }

    fun fixTranslation() {
        myMatrix.getValues(imageMatrixValues) //put imageMatrix values into a float array so we can analyze
        val transX =
            imageMatrixValues[Matrix.MTRANS_X] //get the most recent translation in x direction
        val transY =
            imageMatrixValues[Matrix.MTRANS_Y] //get the most recent translation in y direction
        val fixTransX =
            getFixTranslation(transX, viewWidth.toFloat(), origWidth * saveScale)
        val fixTransY =
            getFixTranslation(transY, viewHeight.toFloat(), origHeight * saveScale)
        if (fixTransX != 0f || fixTransY != 0f) myMatrix.postTranslate(fixTransX, fixTransY)
    }

    fun getFixTranslation(
        trans: Float,
        viewSize: Float,
        contentSize: Float
    ): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) { // case: NOT ZOOMED
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else { //CASE: ZOOMED
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }
        if (trans < minTrans) { // negative x or y translation (down or to the right)
            return -trans + minTrans
        }
        if (trans > maxTrans) { // positive x or y translation (up or to the left)
            return -trans + maxTrans
        }
        return 0f
    }

    fun getFixDragTrans(
        delta: Float,
        viewSize: Float,
        contentSize: Float
    ): Float {
        return if (contentSize <= viewSize) {
            0f
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (saveScale == 1f) { // Fit to screen.
            fitToScreen()
        }
    }

    /*
            Ontouch
         */
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        scaleDetector!!.onTouchEvent(event)
        gestureDetector!!.onTouchEvent(event)
        val currentPoint = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                last.set(currentPoint)
                start.set(last)
                mode = DRAG
            }
            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                val dx = currentPoint.x - last.x
                val dy = currentPoint.y - last.y
                val fixTransX =
                    getFixDragTrans(dx, viewWidth.toFloat(), origWidth * saveScale)
                val fixTransY =
                    getFixDragTrans(dy, viewHeight.toFloat(), origHeight * saveScale)
                myMatrix.postTranslate(fixTransX, fixTransY)
                fixTranslation()
                last[currentPoint.x] = currentPoint.y
            }
            MotionEvent.ACTION_POINTER_UP -> mode = NONE
        }
        imageMatrix = myMatrix
        return false
    }

    /*
        GestureListener
     */
    override fun onDown(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(motionEvent: MotionEvent) {}
    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {}
    override fun onFling(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean {
        return false
    }

    /*
        onDoubleTap
     */
    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        fitToScreen()
        return false
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        return false
    }

    companion object {
        private const val TAG = "ScalingImageView"
        // Image States
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
    }
}










