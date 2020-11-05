package dev.victorman.cribbageboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;

import androidx.annotation.NonNull;

public class InnerTrackDrawable extends TrackDrawable {


    public InnerTrackDrawable(Context context) {
        super(context);
    }

    @Override
    public void buildPath() {


        divisionWidth = getBounds().width() / canvasDivisions;
        middle = divisionWidth / 2f;

        trackPath.rewind();
        trackPath.moveTo( divisionWidth * 3 + middle - viewFieldRect.left, getBounds().bottom - divisionWidth * 5 - viewFieldRect.top);
        RectF oval1 = new RectF(divisionWidth * 3 + middle - viewFieldRect.left, divisionWidth * 3 + middle - viewFieldRect.top, divisionWidth * 9 +middle - viewFieldRect.left, divisionWidth * 10 - viewFieldRect.top);
        trackPath.arcTo(oval1, 180, 180);
        RectF oval2 = new RectF(divisionWidth * 7 + middle - viewFieldRect.left, getBounds().bottom - divisionWidth * 8 - middle - viewFieldRect.top, divisionWidth * 9 + middle - viewFieldRect.left, getBounds().bottom - divisionWidth * 3 - middle - viewFieldRect.top);
        trackPath.arcTo(oval2, 0, 180);
        trackPath.lineTo(divisionWidth * 7 + middle - viewFieldRect.left, divisionWidth * 6 - viewFieldRect.top);


        if(measure.getLength() == 0) {
            measure.setPath(trackPath, false);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
    }
}
