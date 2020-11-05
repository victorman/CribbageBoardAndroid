package dev.victorman.cribbageboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MovableCanvasView extends View {
    private final Context context;
    private float baseWidth;
    private float baseHeight;
    private float width;
    private float height;
    private Scale scale;
    private RectF viewFieldRect;
    private List<ScaledDrawable> drawablesList;
    private float prevx;
    private float prevy;
    private Drawable liftedPeg;
    private List<Drawable> pegs;
    private Rect prevPegBounds;


    public MovableCanvasView(Context context, float baseCanvasWidth, float baseCanvasHeight) {
        super(context);
        this.context = context;
        this.baseWidth = baseCanvasWidth;
        this.baseHeight = baseCanvasHeight;
        width = baseCanvasWidth;
        height = baseCanvasHeight;
        scale = new Scale(1f, 1f);
        drawablesList = new ArrayList<ScaledDrawable>();
        viewFieldRect = new RectF(0, 0, getWidth(), getHeight());
        pegs = new ArrayList<Drawable>(6);
    }

    public boolean addPeg(PegDrawable peg) {
        peg.viewFieldRect = viewFieldRect;
        peg.scale = scale;
        return pegs.add(peg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(scale.getWidthScale(), scale.getHeightScale());
        super.onDraw(canvas);
        if(viewFieldRect.width() == 0) {
            viewFieldRect.set(0, height - getHeight(), getWidth(), getHeight());
        }

        for(Drawable d: drawablesList) {
            d.draw(canvas);
        }
        for(Drawable d: pegs) {
            d.draw(canvas);
        }

    }


    public void addDrawable(ScaledDrawable drawable) {
        drawable.viewFieldRect = viewFieldRect;
        drawable.scale = scale;
        drawablesList.add(drawable);
    }

    public void fingerMove(float dx, float dy) {

        if(viewFieldRect.left + dx < 0) {
            viewFieldRect.left = 0;
            dx = 0;
//            Log.i("move", "" + dx + " " + dy);
        }
        if(viewFieldRect.top + dy < 0) {
            viewFieldRect.top = 0;
                    dy = 0;
//            Log.i("move", "" + dx + " " + dy);
        }
        if(viewFieldRect.right + dx > width) {
            viewFieldRect.right = width;
            if(dx > 0)
                dx = 0;
//            Log.i("move", "" + dx + " " + dy);
        }
        if(viewFieldRect.bottom + dy > height) {
            viewFieldRect.bottom = height;
            if(dy > 0)
                dy = 0;
//            Log.i("move", "" + dx + " " + dy);
        }
        viewFieldRect.set((int)(viewFieldRect.left + dx),
                (int)(viewFieldRect.top + dy),
                (int)(viewFieldRect.right + dx),
                (int)(viewFieldRect.bottom + dy));

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void fingerDown(float x, float y) {
        prevx = x;
        prevy = y;
    }

    public void scaleCanvas(float scaleFactor) {
        scale.setHeightScale(scaleFactor);
        scale.setWidthScale(scaleFactor);
        width = baseWidth * scale.getWidthScale();
        height = baseHeight * scale.getHeightScale();

//        Log.i("scale", "scale: " + scale.getWidthScale() + " " + scale.getHeightScale());
//        Log.i("scale", "rect: " + width + " " + height);
//        Log.i("scale", "screen: " + getWidth() + " " + getHeight());
    }

    public boolean liftPeg(float x, float y) {

        for(Drawable peg: pegs) {
            if(peg.getBounds().contains((int)(x-viewFieldRect.left),(int)(y+viewFieldRect.top))) {
                liftedPeg = peg;
                prevPegBounds = new Rect(liftedPeg.getBounds());
                return true;
            }
        }

        return false;
    }

    public void movePeg(int dx, int dy) {
        Rect bounds = liftedPeg.getBounds();
        liftedPeg.setBounds(bounds.left + dx, bounds.top + dy, bounds.right + dx, bounds.bottom + dy);
    }

    public void dropPeg() {
//        Log.i("drop peg", "dropped");
        boolean intersected = false;
        for(Drawable hole: drawablesList) {
            if(hole.getBounds().intersects(liftedPeg.getBounds().left,liftedPeg.getBounds().top,liftedPeg.getBounds().right,liftedPeg.getBounds().bottom)) {
                Rect hr = hole.getBounds();
                Rect pr = liftedPeg.getBounds();
                int cx = hr.left + hr.width() / 2;
                int cy = hr.top + hr.height() / 2;
                int r = pr.width()/2;
                liftedPeg.setBounds(cx - r, cy - r, cx + r, cy + r);
                intersected = true;
                break;
            }
        }

        if (!intersected) {
            liftedPeg.setBounds(prevPegBounds);
            liftedPeg = null;
        }
    }

    public void addTrack(TrackDrawable track) {
        track.viewFieldRect = viewFieldRect;
        track.scale = scale;
        track.buildPath();
        float len = track.measure.getLength();
        float progPerHole = len / track.holes;
        PegDrawable peg = null;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float r = 20;
        for(int i = 0 ; i < track.holes; i++) {
            float[] pos = new float[2];
            float progress = progPerHole * i;
            track.measure.getPosTan(progress, pos, null);
            peg = new PegDrawable(paint);
            peg.setBounds((int)(pos[0] - r), (int)(pos[1] - r), (int)(pos[0] + r), (int)(pos[1] + r));
            addDrawable(peg);
        }
    }
}
