package com.codefororlando.petadoption.di.module;

import android.content.Context;

import com.codefororlando.petadoption.R;
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
import timber.log.Timber;

/**
 * Created by tencent on 10/8/16.
 */
@Module
public class NetworkModule {

    private final String apiUrl;
    private final String apiKey;

    public NetworkModule(String apiUrl, String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalUrl = original.url();

                        HttpUrl modifiedUrl = originalUrl.newBuilder()
                                .addQueryParameter("key", apiKey)
                                .addQueryParameter("format", "json")
                                .addQueryParameter("output", "full")
                                .build();

                        Request.Builder requestBuilder = original.newBuilder()
                                .url(modifiedUrl);

                        Timber.i(modifiedUrl.url().toString());

                        return chain.proceed(requestBuilder.build());
                    }
                })
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

        String petfinderUrl = context.getString(R.string.petfinder_url);

        return new Retrofit.Builder()
                .addConverterFactory(jsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(petfinderUrl)
                .build();
    }

    @Provides
    IPetfinderService providePetfinderService(Retrofit retrofit){
        return retrofit.create(IPetfinderService.class);
    }

}
