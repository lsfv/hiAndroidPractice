package com.linson.android.hiandroid2.UI.NewCustomView.MyTouch.cv;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import app.lslibrary.androidHelper.LSLog;

public class MyOuterLayout extends ConstraintLayout
{
    public float dx = -1, dy = -1, mx = -1, my = -1;
    public MyOuterLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        LSLog.Log_INFO(ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    public void initTouchRecord()
    {
        dx = -1;dy = -1;mx = -1;my = -1;
    }


    //截断判断时，就一直关注事件，直到需要就截断，
    //onTouchEvent可以并根据view本身的dy-my变量来进行处理。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        LSLog.Log_INFO(ev.toString());
        boolean res=false;

        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            dx = ev.getX();
            dy = ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE)
        {
            mx = ev.getX();
            my = ev.getY();

            if (dx != -1 && dy != -1 && mx != -1 && my != -1)
            {
                if(Math.abs(dx-mx)>Math.abs(dy-my) && mx-dx>10)
                {
                    res=true;
                   // onTouchEvent(ev);
                }
                else if (Math.abs(dx-mx)>Math.abs(dy-my) && dx-mx>10)
                {
                   // onTouchEvent(ev);
                    res=true;
                }
            }
        }
        return res;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        LSLog.Log_INFO(event.toString());
        return super.onTouchEvent(event);
    }
}