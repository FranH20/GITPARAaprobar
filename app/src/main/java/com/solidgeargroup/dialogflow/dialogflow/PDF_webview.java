package com.solidgeargroup.dialogflow.dialogflow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class PDF_webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_webview);

        WebView webview = findViewById(R.id.webView);
        final ProgressBar progresbar = findViewById(R.id.progressbar);

        progresbar.setVisibility(View.VISIBLE);
        String url = "https://firebasestorage.googleapis.com/v0/b/documentos-bc6e5.appspot.com" +
                "/o/TRAZABILIDAD%20DIAGRAMAS%20UML.pdf";
        String final_url = "http://docs.google.com/gview?embedded=true&url=" + url;
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100)
                {
                    progresbar.setVisibility(View.GONE);
                }
            }
        });
        //webview.loadUrl("https://drive.google.com/file/d/1TfBn0IJREZzOl_EDiLCqU2HVqkI9KNls/view");

        webview.loadUrl("https://drive.google.com/file/d/1ubA2Zv-TRYePSJcxZroHX9BVsGYLjiHt/view?usp=sharing");


    }
}
