package com.wzc.chapter_3.viewslide;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private Button mBtnScrollBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide);

        mLlParent = (LinearLayout) findViewById(R.id.ll_parent);
        mBtnScrollTo = (Button) findViewById(R.id.btn_scrollto);
        mBtnScrollBy = (Button) findViewById(R.id.btn_scrollby);
        // 注意： scrollTo/scrollBy 滑动的是 View 的内容。所以，如果要实现 View 本身的滑动，就要调用它父控件的 scrollTo/scrollBy 方法。
        mBtnScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlParent.scrollTo(-50,-50);
            }
        });

        mBtnScrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlParent.scrollBy(-50,-50);
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
                        iv.scrollBy(x,y);
                        result.setText("iv.getScrollX() = " + iv.getScrollX() + ", iv.getScrollY() = " + iv.getScrollY());
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
                        iv2.scrollTo(x,y);
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

        final Button btnLayoutParams = (Button) findViewById(R.id.btn_layoutparams);
        btnLayoutParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) btnLayoutParams.getLayoutParams();
//              params.width += 100;
              params.leftMargin += 100;
              btnLayoutParams.requestLayout();

            }
        });
    }
}
