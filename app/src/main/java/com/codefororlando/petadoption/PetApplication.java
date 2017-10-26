package com.codefororlando.petadoption;

import android.app.Application;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.di.component.AppComponent;
import com.codefororlando.petadoption.di.component.DaggerAppComponent;
import com.codefororlando.petadoption.di.module.AppModule;
import com.codefororlando.petadoption.di.module.NetworkModule;
import com.squareup.leakcanary.LeakCanary;

import java.util.Properties;

/**
 * Created by tencent on 10/8/16.
 */
public class PetApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);

        //Catch common mistakes
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        String baseUrl = getString(R.string.base_url);
        String apiUrl = getString(R.string.api_url, baseUrl);

        String apiKey = BuildConfig.API_KEY;

        component = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(apiUrl, apiKey))
                .appModule(new AppModule(this))
                .build();

    }

    @NonNull
    public AppComponent appComponent() {
        return component;
    }

}
