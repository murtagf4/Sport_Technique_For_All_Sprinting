package com.essentialappsfm.sporttechniquesprinting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Fergus on 13/04/2015.
 */
class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback
{
    Canvas canvasMain;
    Bitmap bitmap;
    int width, height;
    Paint paint;
    Context context;
    SurfaceHolder holder;
    int lastX, lastY, currX, currY;

    public DrawingSurface(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currX = (int) event.getX();
                currY = (int) event.getY();
                canvasMain.drawLine(lastX, lastY, currX, currY, paint);
                lastX = currX;
                lastY = currY;
                break;
        }
        draw();
        return true;
    }

    protected void draw() {
        Canvas canvas;

        canvas = holder.lockCanvas(null);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (holder != null)
            holder.unlockCanvasAndPost(canvas);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        width = getWidth();
        height = getHeight();
        canvasMain = new Canvas();

        // create bitmap with specified width and height and config to store pixels drawn
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvasMain.setBitmap(bitmap);
        //canvasMain.drawColor(PixelFormat.TRANSPARENT);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        draw();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}