package com.saizad.mvvm;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sa.easyandroidfrom.ObjectUtils;

import com.saizad.mvvm.model.Token;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import sa.zad.easyretrofit.CachePolicy;
import sa.zad.easyretrofit.EasyRetrofitClient;

public class SaizadEasyRetrofitClient extends EasyRetrofitClient {

    private final Application application;
    private final CurrentUser currentUser;

    public SaizadEasyRetrofitClient(Application application, CurrentUser currentUser) {
        super(application);
        this.application = application;
        this.currentUser = currentUser;
    }

    @Override
    protected OkHttpClient.Builder builderReady(OkHttpClient.Builder builder) {
        builder.addInterceptor(getAuthInterceptor(currentUser));
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addNetworkInterceptor(new TextToJsonInterceptor());
        }
        return builder;
    }

    protected HttpLoggingInterceptor.Level loggingLevel() {
        return BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
    }

    private static Interceptor getAuthInterceptor(final CurrentUser currentUser) {
        return chain -> {
            Request request = chain.request();
            final Token token = currentUser.getAuthToken();
            if (ObjectUtils.isNotNull(token)) {
                request = request.newBuilder().header("Authorization", "Bearer " + token.getAccess()).build();
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
}
