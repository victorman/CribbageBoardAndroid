package dev.victorman.cribbageboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();


        final MovableCanvasView canvasView = new MovableCanvasView(context, 2000f,4000f);
        canvasView.setOnTouchListener(new MoveViewFrameTouchListener(context));

        ScaledDrawable drawable = new CircleDrawable();
        drawable.setBounds(new Rect(200, 200, 300, 300));
        ScaledDrawable drawable2 = new CircleDrawable();
        drawable2.setBounds(new Rect(1580, 3580, 1680, 3680));

        ScaledDrawable drawable3 = new CircleDrawable();
        drawable3.setBounds(new Rect(880, 1580, 1080, 1780));
        canvasView.addDrawable(drawable);
        canvasView.addDrawable(drawable2);
        canvasView.addDrawable(drawable3);

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