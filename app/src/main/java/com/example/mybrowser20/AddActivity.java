package com.example.mybrowser20;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    TextView showurl;
    EditText tab;
    Button ok,cancel;
    SQLiteDatabase db;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = SQLiteDatabase.openOrCreateDatabase(
                this.getFilesDir().toString()
                        + "/myurl", null);
        showurl=(TextView)findViewById(R.id.textView4);
        tab=(EditText)findViewById(R.id.editText);
        ok=(Button)findViewById(R.id.button2);
        cancel=(Button)findViewById(R.id.button3);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        showurl.setText(intent.getStringExtra("url"));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=tab.getText().toString();
                try
                {
                    insertData(db, tab.getText().toString(), url);

                }
                catch (SQLiteException se)
                {
                    // 执行DDL创建数据表
                    db.execSQL("create table news_inf(_id integer"
                            + " primary key autoincrement,"
                            + " news_tab varchar(50),"
                            + " news_url varchar(255))");
                    // 执行insert语句插入数据
                    insertData(db, tab.getText().toString(), url);
            }
            finish();}
        });}



        private void insertData(SQLiteDatabase db
                , String tab, String url)  // ②
        {
            // 执行插入语句
            db.execSQL("insert into news_inf values(null , ? , ?)"
                    , new String[] {tab, url });
        }

}
