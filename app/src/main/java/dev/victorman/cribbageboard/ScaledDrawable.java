package dev.victorman.cribbageboard;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class ScaledDrawable extends Drawable {
    public Scale scale;
    public RectF viewFieldRect;

//    public void setViewFieldRect(Rect viewFieldRect) {
//        this.viewFieldRect = viewFieldRect;
//    }

    public RectF getScaledBounds() {
        Rect oldBounds = getBounds();
        return new RectF(oldBounds.left,
                oldBounds.top,
                (int)(oldBounds.left + oldBounds.width() * scale.getWidthScale()),
                (int)(oldBounds.top + oldBounds.height() * scale.getHeightScale()));
    }

}
