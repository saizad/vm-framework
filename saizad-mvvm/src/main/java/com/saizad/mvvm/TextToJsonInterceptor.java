package com.saizad.mvvm;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class TextToJsonInterceptor implements Interceptor {

  @Override public Response intercept(Chain chain) throws IOException {
    Response originalResponse = chain.proceed(chain.request());

    boolean intercept = originalResponse.header("Content-Type", "").contains("text/plain");
    if (intercept) {
      return originalResponse.newBuilder()
          .header("Content-Type", "application/json; charset=utf-8")
          .build();
    }

    return originalResponse;
  }

}
