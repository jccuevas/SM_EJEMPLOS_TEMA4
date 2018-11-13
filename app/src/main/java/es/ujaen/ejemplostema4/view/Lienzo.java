package es.ujaen.ejemplostema4.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;


import android.widget.Toast;

import es.ujaen.ejemplostema4.R;

public class Lienzo extends View implements OnGestureListener {

    final int SCALE=2;
    float mTextWidth=0.0f;
    Bitmap mBitmap = null;
    Context context = null;
    Rect src, dst;
    float x = 0, y = 0;
    GestureDetector mGesture = null;

    final Paint paint = new Paint();

    public Lienzo(Context context) {
        super(context);

        this.context = context;

        if (!isInEditMode()) {
            initialize();
        }
    }

    public Lienzo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;


        if (!isInEditMode()) {
            initialize();
        }
    }


    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        if (!isInEditMode()) {
            initialize();
        }


    }


    protected void initialize() {

        setFocusable(true);
        setFocusableInTouchMode(true);
        mGesture = new GestureDetector(this.getContext(), this);

        if (context != null) {

            BitmapDrawable bd = (BitmapDrawable) this.context.getResources()
                    .getDrawable(R.drawable.disco01);
            mBitmap = bd.getBitmap();

            src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());

            dst = new Rect();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int bw=40;
        int bh=40;
        int bkminh=0,bkh=0;
        int bkminw=0,bkw=0;

        if(mBitmap!=null){
            bw= mBitmap.getWidth();
            bh= mBitmap.getHeight();

        }

        Drawable backgound = getBackground();
        if(backgound!=null){

            bkminh=backgound.getMinimumHeight();
            bkminw=backgound.getMinimumWidth();
            bkh=backgound.getIntrinsicHeight();
            bkw=backgound.getIntrinsicWidth();
        }
                // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)Math.max(mTextWidth,bh) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth , heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    public void onDraw(Canvas canvas) {

        String texto;

        if (mBitmap != null) {

            texto = "x=" + x + " y=" + y;
            mTextWidth = paint.measureText(texto);
            float w = mBitmap.getWidth();
            float h = mBitmap.getHeight();
            dst.set((int) (x - w / 2), (int) (y - h / 2), (int) (x + w / 2), (int) (y + h / 2));
            paint.setColor(Color.RED);
            paint.setTextSize(40);

            canvas.drawText(texto, (int) (x - w / 2), (int) (y - h / 2), paint);
            //paint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
            canvas.drawBitmap(mBitmap, src, dst, paint);

        } else {
            paint.setColor(Color.RED);
            canvas.drawRect(x, y, x + 40, y + 40, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mGesture.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                postInvalidate();
                break;
        }
        return result;
    }


    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        x = e2.getX();
        y = e2.getY();
        postInvalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Toast.makeText(getContext(), "Fling!!", Toast.LENGTH_SHORT).show();
        return false;
    }
}
