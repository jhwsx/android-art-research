package com.wzc.chapter_7;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author wzc
 * @date 2018/11/17
 */
public class FlipAnimation extends Animation {
    private float mStartDegree;
    private float mEndDegree;
    private float mCenterX;
    private float mCenterY;
    private Camera mCamera;
    private boolean mFlag;
    private float mZDistance = 400f;
    public FlipAnimation(float startDegree, float endDegree, float centerX, float centerY, boolean flag) {
        mStartDegree = startDegree;
        mEndDegree = endDegree;
        mCenterX = centerX;
        mCenterY = centerY;
        mFlag = flag;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final float startDegree = mStartDegree;
        final float endDegree = mEndDegree;
        final float zDistance = mZDistance;
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        float currDegree = startDegree + interpolatedTime * (endDegree - startDegree);
        Camera camera = mCamera;
        Matrix matrix = t.getMatrix();
        camera.save();
        if (mFlag) {
            camera.translate(0, 0, interpolatedTime * zDistance);
        } else {
            camera.translate(0,0, (1 - interpolatedTime) * zDistance);
        }
        camera.rotateX(currDegree);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }


}
