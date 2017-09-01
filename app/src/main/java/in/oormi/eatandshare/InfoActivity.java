package in.oormi.eatandshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        WebView mywebview = (WebView) findViewById(R.id.webViewInfo);

        mywebview.loadUrl("file:///android_asset/infodata.html");      }
}
