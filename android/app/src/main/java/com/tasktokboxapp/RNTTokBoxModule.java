package com.tasktokboxapp;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNTTokBoxModule extends ReactContextBaseJavaModule {

    public RNTTokBoxModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
      return "StartActivity";
    }

    @ReactMethod
    public void navigateToActivity() {
        ReactApplicationContext context = getReactApplicationContext();
        Intent intent = new Intent(context, TokBoxActivity.class);

        context.startActivity(intent);
    }
}