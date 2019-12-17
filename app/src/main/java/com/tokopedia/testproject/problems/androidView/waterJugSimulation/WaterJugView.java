package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Math.round;


public class WaterJugView extends View {

    private int maxWater = 0;
    private int waterFill = 0;

    Paint jug, water;

    public WaterJugView(Context context) {
        super(context);
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        jug = new Paint();
        water = new Paint();
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMaxWater(int maxWater) {
        this.maxWater = maxWater;
    }

    public void setWaterFill(int waterFill) {
        this.waterFill = waterFill;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        jug.setColor(Color.BLACK);
        jug.setStrokeWidth(10);
        water.setColor(Color.BLUE);

        float topwater = (float) this.waterFill / this.maxWater * 100;
        canvas.drawRect(0, getHeight() - round((topwater / 100) * getHeight()), getWidth(), getHeight(), water);
        canvas.drawLine(0, 0, 0, getHeight(), jug);
        canvas.drawLine(0, getHeight(), getHeight(), getWidth(), jug);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), jug);
    }

    //TODO
    /*
    Based on these variables: maxWater and waterFill, draw the jug with the water

    Example a:
    maxWater = 10
    waterFill = 0

    Result,
    View will draw like below
    |        |
    |        |
    |        |
    |        |
    `--------'

    Example b:
    maxWater = 10
    waterFill = 5

    Result,
    View will draw like below
    |        |
    |        |
    |--------|
    |        |
    `--------'

    Example c:
    maxWater = 10
    waterFill = 8

    Result,
    View will draw like below
    |        |
    |--------|
    |        |
    |        |
    `--------'

    Example d:
    maxWater = 10
    waterFill = 10

    Result,
    View will draw like below
     ________
    |        |
    |        |
    |        |
    |        |
    `--------'
    */

}
