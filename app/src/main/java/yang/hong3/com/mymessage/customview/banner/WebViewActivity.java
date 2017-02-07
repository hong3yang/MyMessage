package yang.hong3.com.mymessage.customview.banner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseActivity;

/**
 * Created by hong3 on 2017-1-10.
 */

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";

    private static final String URL = "url";
    WebView mWebView;
    LinearLayout mLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_banner_web;
    }

    @Override
    protected void initView() {
        mLayout = $(R.id.web_layout);
        mWebView = new WebView(this);
        mLayout.addView(mWebView,0);



        Bundle bundle = getIntent().getExtras();
        if (bundle == null){

            return;
        }
        String url = bundle.getString(URL);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//启用js
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setAppCacheEnabled(false); //设置H5的缓存打开,默认关闭
        settings.setUseWideViewPort(true);//设置webview自适应屏幕大小
        settings.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小
        settings.setSupportZoom(false);//关闭zoom按钮
        settings.setBuiltInZoomControls(false);//关闭zoom
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                String js = "var images=document.getElementsByTagName(\"img\");\n" +
                        "  var imgLen=images.length;\n" +
                        "   var srcx = '';" +
                        "  for(var i=0;i<imgLen;i++){" +
                        "       srcx += images[i].src+'|';" +
                        "   }"+
                        "  for(var i=0;i<imgLen;i++){\n" +
                        "    images[i].onclick=function(){window.demo.openImage(srcx,this.src);}" +
                        "  }" ;
                view.loadUrl("javascript:" + js);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 设置相应https请求
             * @param webView
             * @param sslErrorHandler
             * @param sslError
             */
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.proceed();

            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Log.d(TAG, "onPageStarted: -->"+s);
            }


        });


        //添加一个对象用js来调用
        mWebView.addJavascriptInterface(this,"demo");

        mWebView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public static void startActivity(Context context, String url){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(URL,url);
        context.startActivity(intent);
    }
}
