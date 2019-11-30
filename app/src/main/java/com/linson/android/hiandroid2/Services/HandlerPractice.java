package com.linson.android.hiandroid2.Services;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

import app.lslibrary.androidHelper.LSLog;

//模拟一个耗时的模块，比如计算数字的平方需要3秒。ui线程发送消息给后台计算。后台计算完毕之后把结果发送给ui。
//1.按道理应该是用系统提供的runOnUI是最简单的。
//2.handler的话就是android中最基础的api了。可以看成是android实现了一个线程安全的消费生产模型。
// 只要建立handler。就建立了一个生产消费模式。必须建立一个handler的派生类，因为需要自己重载如何消费数据。
// 在其他线程中直接handler.sendmessage(msg).就可以把msg数据，线程安全的放入到handler中。并且hander所在的线程就会触发消费方法。
//3.所以比handle更基础，或者想深入了解handler。可以自己用java实现一个线程安全的生产和消费者模式。
//4.异步任务进一步封装了。我们都不需要接触线程了。
public class HandlerPractice extends AppCompatActivity
{
    public enum asyncType
    {
        runonui,
        handler,
        aysncTask
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_practice);
        mMyControls=new MyControls();//cut it into 'onCreate'
        bindEvent();
    }

    private void clickSquare()
    {
        int number1=Integer.valueOf(mMyControls.mEditText2.getText().toString());
        asyncType type=asyncType.aysncTask;
        if(type==asyncType.runonui)
        {
            Thread bgthread = new Thread(new ComplexJob(number1));
            bgthread.start();
        }
        else if(type==asyncType.handler)
        {
            Thread bgthread2=new Thread(new ComplexJob2(number1, new MyHandler()));
            bgthread2.start();
        }
        else if(type==asyncType.aysncTask)
        {
            MyAsyncTask myAsyncTask=new MyAsyncTask();
            myAsyncTask.execute(number1);
        }
    }

    private void bindEvent()
    {
        mMyControls.mBtnSquare.setOnClickListener(new MyClickListener());
    }

    //region type:asyncTask
    public class MyAsyncTask extends AsyncTask<Integer,Integer,String>
    {

        @Override
        protected String doInBackground(Integer... integers)
        {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            final int res=integers[0]*integers[0];

            return res+"";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            mMyControls.mTvMsg.setText(s);
        }
    }
    //endregion

    //region type:runonui
    private class ComplexJob implements Runnable
    {
        private int num1;
        public ComplexJob(int a)
        {
            num1=a;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            final int res=num1*num1;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mMyControls.mTvMsg.setText(res+"");
                }
            });
        }
    }
    //endregion

    //region type:handler
    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            LSLog.Log_INFO("ui");
            try
            {
                mMyControls.mTvMsg.setText(msg.arg1+"");
            } catch (Exception e)
            {
                LSLog.Log_Exception(e);
            }

        }
    }


    private class ComplexJob2 implements Runnable
    {
        private int num1;
        private Handler mHandler;
        public ComplexJob2(int a,Handler handler)
        {
            num1=a;
            mHandler=handler;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            final int res=num1*num1;
            Message msg=new Message();
            msg.arg1=res;
            LSLog.Log_INFO("before send");
            mHandler.sendMessage(msg);
            LSLog.Log_INFO("after send");
        }
    }
    //endregion


    //region click listener
    private class MyClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v.getId()==R.id.btn_square)
            {
                clickSquare();
            }
        }
    }


    //endregion

    //region The class of FindControls
    private MyControls mMyControls=null;
    public class MyControls
    {
        private Button mBtnSquare;
        private Button mBtnStart;
        private TextView mTvMsg;
        private EditText mEditText2;

        public MyControls()
        {
            mBtnSquare = (Button) findViewById(R.id.btn_square);
            mBtnStart = (Button) findViewById(R.id.btn_start);
            mTvMsg = (TextView) findViewById(R.id.tv_msg);
            mEditText2 = (EditText) findViewById(R.id.editText2);
        }
    }
    //endregion
}