package dev.victorman.cribbageboard;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MoveViewFrameTouchListener implements View.OnTouchListener {
    private final ScaleGestureDetector scaleDetector;
    private int activePointerId;
    private float lastTouchX;
    private float lastTouchY;
    private float scaleFactor = 1.f;
    private MovableCanvasView canvasView;
    private boolean pegLifted;

    public MoveViewFrameTouchListener(Context context) {

        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        pegLifted = false;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        canvasView = (MovableCanvasView) view;

        if(!pegLifted)
            scaleDetector.onTouchEvent(motionEvent);


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = motionEvent.getActionIndex();

                // Remember where we started (for dragging)
                lastTouchX = x;
                lastTouchY = y;

                // Save the ID of this pointer (for dragging)
                activePointerId = motionEvent.getPointerId(pointerIndex);

                if(!pegLifted)
                    pegLifted = canvasView.liftPeg(x,y);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex = motionEvent.findPointerIndex(activePointerId);


                // Calculate the distance moved
                final float dx = x - lastTouchX;
                final float dy = y - lastTouchY;

//                posX += dx;
//                posY += dy;

                if(pegLifted)
                    canvasView.movePeg((int)dx, (int)dy);
                else
                    canvasView.fingerMove(-dx, -dy);

                // Remember this touch position for the next move event
                lastTouchX = x;
                lastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                if(pegLifted) {
                    canvasView.dropPeg();
                    pegLifted = false;
                }
                Log.i("touch", "up");
                activePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                activePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = motionEvent.getActionIndex();
                final int pointerId = motionEvent.getPointerId(pointerIndex);

                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = x;
                    lastTouchY = y;
                    activePointerId = motionEvent.getPointerId(newPointerIndex);

                }
                break;
            }

//            case MotionEvent.ACTION_DOWN:
//                cview.fingerDown(x, y);
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
        }
        view.performClick();
        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            canvasView.scaleCanvas(scaleFactor);

            return true;
        }
    }

}
