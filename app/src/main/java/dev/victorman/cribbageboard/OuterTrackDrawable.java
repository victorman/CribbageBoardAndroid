package dev.victorman.cribbageboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class OuterTrackDrawable extends TrackDrawable {


    public OuterTrackDrawable(Context context) {
        super(context);
    }

    @Override
    public void buildPath() {

        divisionWidth = getBounds().width() / canvasDivisions;
        middle = divisionWidth / 2f;

        trackPath.rewind();
        trackPath.moveTo( divisionWidth + middle - viewFieldRect.left, getBounds().bottom - divisionWidth * 5 - viewFieldRect.top);
        RectF oval1 = new RectF(divisionWidth + middle - viewFieldRect.left, divisionWidth + middle - viewFieldRect.top, divisionWidth * 11 +middle - viewFieldRect.left, divisionWidth * 10 - viewFieldRect.top);
        trackPath.arcTo(oval1, 180, 180);
        RectF oval2 = new RectF(divisionWidth * 5 + middle - viewFieldRect.left, getBounds().bottom - divisionWidth * 8 - middle - viewFieldRect.top, divisionWidth * 11 + middle - viewFieldRect.left, getBounds().bottom - divisionWidth - middle - viewFieldRect.top);
        trackPath.arcTo(oval2, 0, 180);
        trackPath.lineTo(divisionWidth * 5 + middle - viewFieldRect.left, divisionWidth * 6 - viewFieldRect.top);

        if(measure.getLength() == 0) {
            measure.setPath(trackPath, false);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
    }
}
