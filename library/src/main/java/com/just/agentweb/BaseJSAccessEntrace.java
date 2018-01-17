package com.just.agentweb;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by cenxiaozhong on 2017/5/26.
 * source code  https://github.com/Justson/AgentWeb
 */

public abstract class BaseJSAccessEntrace implements JSAccessEntrace {

    private WebView mWebView;
    public static final String TAG=BaseJSAccessEntrace.class.getSimpleName();
    BaseJSAccessEntrace(WebView webView){
        this.mWebView=webView;
    }

    @Override
    public void callJS(String js, final ValueCallback<String> callback) {

        LogUtils.i(TAG,"method callJS:"+js);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.evaluateJs(js, callback);
        } else {
            this.loadJs(js);
        }


    }
    @Override
    public void callJS(String js) {
        this.callJS(js,  null);
    }


    private void loadJs(String js) {

        mWebView.loadUrl(js);

    }
    private void evaluateJs(String js, final ValueCallback<String>callback){

        mWebView.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                if (callback != null)
                    callback.onReceiveValue(value);
            }
        });
    }


    @Override
    public void quickCallJS(String method, ValueCallback<String> callback, String... params) {

        StringBuilder sb=new StringBuilder();
        sb.append("javascript:"+method);
        if(params==null||params.length==0){
            sb.append("()");
        }else{
            sb.append("(").append(concat(params)).append(")");
        }


        callJS(sb.toString(),callback);

    }

    private String concat(String...params){

        StringBuilder mStringBuilder=new StringBuilder();

        for(int i=0;i<params.length;i++){
            String param=params[i];
            if(!AgentWebUtils.isJson(param)){

                mStringBuilder.append("\"").append(param).append("\"");
            }else{
                mStringBuilder.append(param);
            }

            if(i!=params.length-1){
                mStringBuilder.append(" , ");
            }

        }

        return mStringBuilder.toString();
    }

    @Override
    public void quickCallJS(String method, String... params) {

        this.quickCallJS(method,null,params);
    }

    @Override
    public void quickCallJS(String method) {
        this.quickCallJS(method,(String[])null);
    }
}
