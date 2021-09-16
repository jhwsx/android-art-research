package com.wzc.chapter_3.viewslide;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzc.chapter_3.R;

/**
 * @author wzc
 * @date 2018/5/13
 */
public class ViewSlideActivity extends Activity {
    private LinearLayout mLlParent;
    private Button mBtnScrollTo;
    private Button mBtnScrollBy1;
    private Button mBtnScrollBy2;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide);

        mLlParent = (LinearLayout) findViewById(R.id.ll_parent);
        mBtnScrollTo = (Button) findViewById(R.id.btn_scrollto);
        mBtnScrollBy1 = (Button) findViewById(R.id.btn_scrollby1);
        mBtnScrollBy2 = (Button) findViewById(R.id.btn_scrollby2);
        // 注意： scrollTo/scrollBy 滑动的是 View 的内容。所以，如果要实现 View 本身的滑动，就要调用它父控件的 scrollTo/scrollBy 方法。
        mBtnScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlParent.scrollTo(-50, -50);
            }
        });

        mBtnScrollBy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlParent.scrollBy(-50, -50);
            }
        });

        mBtnScrollBy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlParent.scrollBy(50, 50);
            }
        });

        final ImageView iv = (ImageView) findViewById(R.id.iv);
        final EditText etX = (EditText) findViewById(R.id.et_x);
        final EditText etY = (EditText) findViewById(R.id.et_y);
        final TextView result = (TextView) findViewById(R.id.result);
        etY.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        int x = Integer.parseInt(etX.getText().toString());
                        int y = Integer.parseInt(etY.getText().toString());
                        iv.scrollBy(x, y);
                        result.setText(
                                "iv.getScrollX() = " + iv.getScrollX()
                                        + ", iv.getScrollY() = " + iv.getScrollY()
                                        + ", iv.getX() = " + iv.getX()
                                        + ", iv.getY() = " + iv.getY()
                                        + ", iv.getLeft() = " + iv.getLeft()
                                        + ", iv.getRight() = " + iv.getRight()
                        );
                        Log.d("MainActivity", "iv.getScrollX() = " + iv.getScrollX() + ", iv.getScrollY() = " + iv.getScrollY());
                        return true;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        });

        final ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        final EditText etX2 = (EditText) findViewById(R.id.et_x2);
        final EditText etY2 = (EditText) findViewById(R.id.et_y2);
        final TextView result2 = (TextView) findViewById(R.id.result2);
        etY2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        int x = Integer.parseInt(etX2.getText().toString());
                        int y = Integer.parseInt(etY2.getText().toString());
                        iv2.scrollTo(x, y);
                        result2.setText("iv2.getScrollX() = " + iv2.getScrollX() + ", iv2.getScrollY() = " + iv2.getScrollY());
                        Log.d("MainActivity", "iv2.getScrollX() = " + iv2.getScrollX() + ", iv2.getScrollY() = " + iv2.getScrollY());
                        return true;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        });

        final Button btnAnim = (Button) findViewById(R.id.btn_anim);
        btnAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float toXValue = flag ? -1f : 1f;
                flag = !flag;
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, toXValue,
                        TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0);
                translateAnimation.setDuration(100);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setFillAfter(true);
                btnAnim.startAnimation(translateAnimation);
            }
        });

        final Button btnAnim2 = (Button) findViewById(R.id.btn_anim2);
        btnAnim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float toXValue = flag ? -1f : 1f;
                flag = !flag;
                ObjectAnimator.ofFloat(btnAnim2, "translationX", 0, toXValue * 200)
                        .setDuration(100)
                        .start();
            }
        });

        final Button btnLayoutParams = (Button) findViewById(R.id.btn_layoutparams);
        btnLayoutParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) btnLayoutParams.getLayoutParams();
//              params.width += 100;
                params.leftMargin += 100;
                // TODO requestLayout() 如何导致 View 的布局参数的刷新？
//                btnLayoutParams.requestLayout();
                // 或者
                btnLayoutParams.setLayoutParams(params);

            }
        });
    }
}
