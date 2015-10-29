package com.rubenkid.newupdatesbutton;

/**
 * Created by ruben on 10/26/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;


public class NewUpdatesButton extends Button {

    private static final String TAG = NewUpdatesButton.class.getSimpleName();

    private static final long NEW_ARTICLES_BUTTON_ANIMATION_DURATION = 500;

    int mColor = Color.WHITE;

    boolean isShowing;


    public NewUpdatesButton(Context context) {
        super(context);
    }

    public NewUpdatesButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewUpdatesButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(int color) {
        this.mColor = color;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimensionPixelSize(R.dimen.new_updates_button_height) / 2, metrics);

        float shadowPadding = getResources().getDimension(R.dimen.new_updates_shadow_padding);

        Paint paint = new Paint();
        paint.setColor(mColor == 0 ? getResources().getColor(android.R.color.white) : (0xFF << 24) | mColor);
        paint.setAntiAlias(true);
        paint.setShadowLayer(shadowPadding, 0f, 0f, Color.BLACK);

        setLayerType(View.LAYER_TYPE_SOFTWARE, paint);


        int triangleHeight = getResources().getDimensionPixelSize(R.dimen.new_updates_button_triangle_height);
        int triangleWidth = triangleHeight * 2;

        //Draw rectangle
        int horizontalMargin = canvas.getWidth() / 2 - getResources().getDimensionPixelSize(R.dimen.new_updates_button_width) / 2;
        RectF rect = new RectF(horizontalMargin + shadowPadding, triangleHeight,
                horizontalMargin + getResources().getDimensionPixelSize(R.dimen.new_updates_button_width) - shadowPadding, canvas.getHeight() - triangleHeight);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        //Draw triangle
        Path triangle = new Path();
        triangle.moveTo(canvas.getWidth() / 2 - triangleWidth / 2, triangleHeight);
        triangle.lineTo(canvas.getWidth() / 2, 0);
        triangle.lineTo(canvas.getWidth() / 2 + triangleWidth / 2, triangleHeight);
        triangle.close();
        paint.setShadowLayer(0, 0, 0, 0);
        canvas.drawPath(triangle, paint);

        //Set Text
        setTextColor();

        super.onDraw(canvas);
    }

    private void setTextColor() {
        final int textColor = Utils.isDarkColor(mColor) ?
                ContextCompat.getColor(getContext(), android.R.color.white)
                : ContextCompat.getColor(getContext(), android.R.color.black);
        setTextColor(textColor);
    }

    public void showAnimated() {
        Log.i(TAG, "Show New Articles Button");
        clearAnimation();
        setTranslationY(0.0f);
        ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new OvershootInterpolator(1.0f));
        animation.setDuration(NEW_ARTICLES_BUTTON_ANIMATION_DURATION);
        animation.setFillAfter(true);
        setVisibility(View.VISIBLE);
        setAnimation(animation);
        animate();
        isShowing = true;
    }

    public void hideWithScaleAnimation() {
        Log.i(TAG, "Hide New Articles Button using scale");

        clearAnimation();
        ScaleAnimation animation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(NEW_ARTICLES_BUTTON_ANIMATION_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isShowing = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        setAnimation(animation);
        animate();
    }

    public void hideWithSlideAnimation(float translationY) {
        Log.i(TAG, "Hide New Articles Button using slide");

        clearAnimation();
        animate().setDuration(NEW_ARTICLES_BUTTON_ANIMATION_DURATION)
                .translationY(-translationY);
        isShowing = false;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
