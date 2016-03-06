package com.example.clairenoble.pebbleapp20;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Malzberry on 3/6/2016.
 */
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            // do your thang
            this.setEnabled(false);
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
