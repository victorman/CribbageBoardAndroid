package dev.victorman.cribbageboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class TrackDrawable extends ScaledDrawable {
    protected float divisionWidth;
    protected float middle;
    public PathMeasure measure;
    protected float canvasDivisions;
    protected Path trackPath;
    protected final int holes = 120;
    protected Path holePath;
    protected PathDashPathEffect holePathEffect;
    Paint trackPaint;
    public TrackDrawable(Context context) {
        trackPath = new Path();
        canvasDivisions = 13;
        holePath = new Path();
        holePath.rewind();
        holePath.addCircle(0,0, 20, Path.Direction.CCW);
        holePathEffect = new PathDashPathEffect(holePath, 100, 0, PathDashPathEffect.Style.TRANSLATE);
        trackPaint = new Paint();
        trackPaint.setColor(Color.BLACK);
        trackPaint.setPathEffect(holePathEffect);

        measure = new PathMeasure(trackPath, false);
//        trackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        trackPaint.setColor(Color.RED);
//        trackPaint.setStrokeWidth(40f);
    }

    public abstract void buildPath();

    @Override
    public void draw(@NonNull Canvas canvas) {
    }

    @Override
    public void setAlpha(int i) {}
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return 0;
    }
}
