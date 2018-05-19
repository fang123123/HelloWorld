package com.example.fj.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private SeekBar seekBar;
    private TextView textView2;
    private Button button;
    private ListView mListView;
    private List<MusicItem> list;
    private MusicAdapter adapter;
    private EditText sendText;
    private Button sendButton;
    private TextView getText;
    private Button StartButton;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("123", "main");
        textView = (TextView) findViewById(R.id.TextView);
        seekBar = (SeekBar) findViewById(R.id.SeekBar);
        textView2 = (TextView) findViewById(R.id.textview2);
        button = (Button) findViewById(R.id.Button_login);
        sendText=(EditText) findViewById(R.id.message);
        sendButton=(Button)findViewById(R.id.send);
        getText=(TextView)findViewById(R.id.getmessage);
        StartButton=(Button)findViewById(R.id.IntentButton);
        initView();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.arg1 = Integer.parseInt(sendText.getText().toString());
                        h.sendMessage(message);
                    }
                }.start();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText(String.valueOf(seekBar.getProgress()));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", "fangjie");
                startActivityForResult(intent, 0);
            }
        });
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("com.example.fj.helloworld.Main2Activity");
                startActivity(intent);
                Log.e("123", "start");
            }
        });
    }
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int text=msg.arg1;
            getText.setText(String.valueOf(text));
            Toast.makeText(MainActivity.this, "收到啦", Toast.LENGTH_LONG).show();
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            String str = data.getStringExtra("result");
            textView2.setText(str);
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.ListMusic);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtil.getMusicData(this);
        adapter = new MusicAdapter(this, R.layout.layout_musicitem, list);
        mListView.setAdapter(adapter);
    }
}
