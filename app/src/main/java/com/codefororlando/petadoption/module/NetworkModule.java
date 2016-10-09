package com.codefororlando.petadoption.module;

import android.app.Application;
import android.content.Context;

import com.codefororlando.petadoption.R;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
                .client(client)
                .baseUrl(apiUrl)
                .build();
    }

}
