package com.codefororlando.petadoption;

import android.app.Application;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.component.AppComponent;
import com.codefororlando.petadoption.component.DaggerAppComponent;
import com.codefororlando.petadoption.module.AppModule;
import com.codefororlando.petadoption.module.NetworkModule;

/**
 * Created by tencent on 10/8/16.
 */
public class PetApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        String baseUrl = getString(R.string.base_url);
        String apiUrl = getString(R.string.api_url, baseUrl);

        component = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(apiUrl))
                .appModule(new AppModule(this))
                .build();

    }

    @NonNull
    public AppComponent component() {
        return component;
    }

}
