package com.example.mybrowser20;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    EditText url;
    Button search;
    WebView webView;
    ProgressBar pb;
    ImageButton baidu;
    RelativeLayout relativeLayout;
    Button goback;
    Button goforward;
    Button flush;
    Button menu;
    static String strurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        search=(Button)findViewById(R.id.search);
        baidu=(ImageButton)findViewById(R.id.imageButton1);
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        goback=(Button)findViewById(R.id.goback);
        goforward=(Button)findViewById(R.id.goforward);
        flush=(Button)findViewById(R.id.flush);
        menu=(Button)findViewById(R.id.menu);
        webView=(WebView)findViewById(R.id.webView);
        pb=(ProgressBar)findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                relativeLayout.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                pb.setVisibility(View.GONE);

            }

            public void onReceivedError(WebView view, int errorCode, String des, String failurl) {
                super.onReceivedError(view, errorCode, des, failurl);
                pb.setVisibility(View.GONE);
            }
        });
        Intent intent=getIntent();
      //  Uri uri = intent.getData();
        //Log.e("tag",uri.toString());
        webView.loadUrl(intent.getDataString());
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strurl = url.getText().toString();
                if (strurl.contains("http:"))
                    webView.loadUrl(strurl);
                webView.loadUrl("http://" + strurl);
            }
        });
        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("http:baidu.com");
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });
        goforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });
        flush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=webView.getUrl().toString();
                webView.loadUrl(url);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });





    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu1, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()

        {
            @Override
            public boolean onMenuItemClick (MenuItem item){
                switch(item.getItemId()){
                    case R.id.favorite:
                     Intent intent2=new Intent();
                        intent2.setClass(MainActivity.this,FavoriteActivity.class);
                        startActivityForResult(intent2,3);
                        break;
                    case R.id.add:
                        String tempurl=webView.getUrl();
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,AddActivity.class);
                        intent.putExtra("url", tempurl.toString());
                        startActivity(intent);


                        break;
                    case R.id.html:
                        break;
                    case R.id.first:
                        break;

                }

                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keycode,KeyEvent event){
        if(keycode==KeyEvent.KEYCODE_BACK){
            webView.goBack();
            return  true;
        }
        return super.onKeyDown(keycode, event);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3
                )
            if (resultCode == 2) {
               webView.loadUrl(data.getStringExtra("sss"));


            }

    }


}
