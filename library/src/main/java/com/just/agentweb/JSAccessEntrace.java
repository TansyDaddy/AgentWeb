package com.just.agentweb;

import android.webkit.ValueCallback;

/**
 * Created by cenxiaozhong on 2017/5/14.
 * source code  https://github.com/Justson/AgentWeb
 */

public interface JSAccessEntrace extends QuickCallJS {


    void callJS(String js, ValueCallback<String> callback);

    void callJS(String js);


}
