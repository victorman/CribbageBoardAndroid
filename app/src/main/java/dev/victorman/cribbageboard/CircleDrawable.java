package dev.victorman.cribbageboard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircleDrawable extends ScaledDrawable {
    private final Paint paint;

    public CircleDrawable() {
        this.paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RectF r = getScaledBounds();
        float cx = (r.left - viewFieldRect.left + r.width() / 2f);// * scale.getWidthScale();
        float cy = (r.top - viewFieldRect.top + r.height() / 2f);// * scale.getHeightScale();
        float radius = (Math.min(r.width(),r.height()) / 2f);// * scale.getWidthScale();
        canvas.drawCircle(cx, cy, radius,paint);
    }

    @Override
    public void setAlpha(int i) { }
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) { }

    @Override
    public int getOpacity() {
        return 0;
    }
}
