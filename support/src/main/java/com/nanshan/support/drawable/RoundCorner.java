package com.nanshan.support.drawable;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import com.nanshan.support.R;

public class RoundCorner extends GradientDrawable {
    public RoundCorner(Context context, int color) {
        this(context, color, (int) context.getResources().getDimension(R.dimen.button_radius));
    }

    public RoundCorner(Context context, int color, int radius) {
        setColor(color);
        setShape(RECTANGLE);
        setCornerRadius(radius);
    }

    public RoundCorner(Context context, int color, int radius, int stroke) {
        setColor(color);
        setShape(RECTANGLE);
        setCornerRadius(radius);
        setStroke(stroke, context.getResources().getColor(android.R.color.white));
    }

    public RoundCorner(Context context, int color, int radius, int stroke, int strokeColor) {
        setColor(color);
        setShape(RECTANGLE);
        setCornerRadius(radius);
        setStroke(stroke, strokeColor);
    }

    //
    public RoundCorner(Context context, int color, boolean isLeft) {
        this(context, color, (int) context.getResources().getDimension(R.dimen.button_radius), isLeft);
    }

    public RoundCorner(Context context, int color, int radius, boolean isLeft) {
        setColor(color);
        setShape(RECTANGLE);
        float[] radii = {0, 0, 0, 0, 0, 0, 0, 0};
        if (isLeft) {
            radii[6] = radius;
            radii[7] = radius;
        } else {
            radii[4] = radius;
            radii[5] = radius;
        }
        setCornerRadii(radii);
    }

    public RoundCorner(Context context, int color, int radius, boolean isLeft, int stroke) {
        setColor(color);
        setShape(RECTANGLE);
        float[] radii = {0, 0, 0, 0, 0, 0, 0, 0};
        if (isLeft) {
            radii[6] = radius;
            radii[7] = radius;
        } else {
            radii[4] = radius;
            radii[5] = radius;
        }
        setCornerRadii(radii);
        setStroke(stroke, context.getResources().getColor(android.R.color.white));
    }

    public RoundCorner(Context context, int color, int radius, boolean isLeft, int stroke, int strokeColor) {
        setColor(color);
        setShape(RECTANGLE);
        float[] radii = {0, 0, 0, 0, 0, 0, 0, 0};
        if (isLeft) {
            radii[6] = radius;
            radii[7] = radius;
        } else {
            radii[4] = radius;
            radii[5] = radius;
        }
        setStroke(stroke, strokeColor);
    }
}
