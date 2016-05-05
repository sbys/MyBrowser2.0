package com.example.mybrowser20;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FavoriteActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView listView;
    static  int AlarmIndex;
    static String urlstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        db = SQLiteDatabase.openOrCreateDatabase(
                this.getFilesDir().toString()
                        + "/myurl", null); // ①
        listView = (ListView) findViewById(R.id.listView);
        Cursor cursor = db.rawQuery("select * from news_inf"
                , null);
        inflateList(cursor);

    }
    private void inflateList(final Cursor cursor)
    {
        // 填充SimpleCursorAdapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                FavoriteActivity.this,
                R.layout.line, cursor,
                new String[] { "news_tab", "news_url" }
                , new int[] {R.id.textView, R.id.textView1 },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);  // ③
        // 显示数据
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Cursor cursor1 = db.query("news_inf", null, null, null, null, null, null);
                if (cursor1 != null) {

                    int i=0;
                    while (cursor1.moveToNext()) {
                        i++;
                        String tab = cursor1.getString(cursor1.getColumnIndex("news_tab"));
                        String url = cursor1.getString(cursor1.getColumnIndex("news_url"));
                        if(i==id){
                            urlstr=url;
                        }

                    }
                    // 关闭

                    cursor.close();
                    Intent intent=new Intent();
                    intent.putExtra("sss",urlstr);
                    setResult(2,intent);
                    finish();
                }
            }
        });

    }

}
