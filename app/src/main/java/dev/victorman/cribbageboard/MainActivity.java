package dev.victorman.cribbageboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private final float movableCanvasWidth = 2000f;
    private final float movableCanvasHeight = 5000f;
    private final float pegWidth = 60f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();


        final MovableCanvasView canvasView = new MovableCanvasView(context, movableCanvasWidth,movableCanvasHeight);
        canvasView.setOnTouchListener(new MoveViewFrameTouchListener(context));

        TrackDrawable outerTrack = new OuterTrackDrawable(context);
        outerTrack.setBounds(0, 0, (int)movableCanvasWidth, (int)movableCanvasHeight);
        TrackDrawable innerTrack = new InnerTrackDrawable(context);
        innerTrack.setBounds(0,0,(int)movableCanvasWidth,(int)movableCanvasHeight);
        canvasView.addTrack(innerTrack);
        canvasView.addTrack(outerTrack);

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        PegDrawable peg1 = new PegDrawable(redPaint);
        peg1.setBounds(200, (int)(movableCanvasHeight - 500), (int)(200 + pegWidth), (int)(movableCanvasHeight - 500 + pegWidth));
        canvasView.addPeg(peg1);
        PegDrawable peg2 = new PegDrawable(redPaint);
        peg2.setBounds(200, (int)(movableCanvasHeight - 400), (int)(200 + pegWidth), (int)(movableCanvasHeight - 400 + pegWidth));
        canvasView.addPeg(peg2);
        PegDrawable peg3 = new PegDrawable(bluePaint);
        peg3.setBounds(500, (int)(movableCanvasHeight - 500), (int)(500 + pegWidth), (int)(movableCanvasHeight - 500 + pegWidth));
        canvasView.addPeg(peg3);
        PegDrawable peg4 = new PegDrawable(bluePaint);
        peg4.setBounds(500, (int)(movableCanvasHeight - 400), (int)(500 + pegWidth), (int)(movableCanvasHeight - 400 + pegWidth));
        canvasView.addPeg(peg4);


        canvasView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        setContentView(canvasView);

        Runnable runnable = new Runnable() {
            long elapsedTime = 0L;
            long fps = 20L;
            long frameDuration = 1000L / fps;
            long lastTime = 0L;
            @Override
            public void run() {
                while(true) {
                    elapsedTime = System.currentTimeMillis() - lastTime;
                    if (elapsedTime > frameDuration) {
                        canvasView.invalidate();
                        lastTime = System.currentTimeMillis();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();
    }
}