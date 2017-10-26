package com.codefororlando.petadoption.di.module;

import android.content.Context;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.network.IPetAdoptionService;
import com.codefororlando.petadoption.network.IPetfinderService;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by tencent on 10/8/16.
 */
@Module
public class NetworkModule {

    private final String apiUrl;

    public NetworkModule(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        int cacheSize = 4 * 1024 * 1024; // 4 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @Provides
    Converter.Factory provideJsonConverterFactory() {
        return LoganSquareConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context,
                                    Converter.Factory jsonFactory,
                                    OkHttpClient client) {

        String baseUrl = context.getString(R.string.base_url);
        String apiUrl = context.getString(R.string.api_url, baseUrl);

        return new Retrofit.Builder()
                .addConverterFactory(jsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(apiUrl)
                .build();
    }

    @Provides
    IPetAdoptionService providePetAdoptionService(Retrofit retrofit) {
        return retrofit.create(IPetAdoptionService.class);
    }

}
