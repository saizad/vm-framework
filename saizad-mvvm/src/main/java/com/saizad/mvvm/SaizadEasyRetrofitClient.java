package com.saizad.mvvm;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sa.easyandroidform.ObjectUtils;

import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import sa.zad.easyretrofit.CachePolicy;
import sa.zad.easyretrofit.EasyRetrofitClient;

public class SaizadEasyRetrofitClient extends EasyRetrofitClient {

    protected final Application application;
    protected final CurrentUserType currentUser;

    public SaizadEasyRetrofitClient(Application application, CurrentUserType currentUser) {
        super(application);
        this.application = application;
        this.currentUser = currentUser;
    }

    @Override
    protected OkHttpClient.Builder builderReady(OkHttpClient.Builder builder) {
        builder.addInterceptor(getAuthInterceptor(currentUser));
        if (isDebugMode()) {
            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addNetworkInterceptor(new TextToJsonInterceptor());
        }
        return builder;
    }

    protected HttpLoggingInterceptor.Level loggingLevel() {
        return isDebugMode() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
    }

    private static Interceptor getAuthInterceptor(final CurrentUserType currentUser) {
        return chain -> {
            Request request = chain.request();
            final String decode = URLDecoder.decode(request.url().toString(), "UTF-8");
            request = request.newBuilder().url(decode).build();
            final String token = currentUser.getToken();
            if (ObjectUtils.isNotNull(token)) {
                request = request.newBuilder().header("Authorization",  "Bearer " + token).build();
            }
            return chain.proceed(request);
        };
    }

    @Override
    protected long cacheStale(int cachePolicy) {
        if (cachePolicy == CachePolicy.LOCAL_IF_FRESH) {
            return 60L;
        }
        return super.cacheStale(cachePolicy);
    }

    protected boolean isDebugMode(){
        return true;
    }
}
