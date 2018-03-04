package com.codefororlando.petadoption;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.LoganSquare;
import com.codefororlando.petadoption.about.OpenSourceModel;
import com.codefororlando.petadoption.di.component.AppComponent;
import com.codefororlando.petadoption.di.component.DaggerAppComponent;
import com.codefororlando.petadoption.di.module.AppModule;
import com.codefororlando.petadoption.di.module.NetworkModule;
import com.codefororlando.petadoption.persistence.PetDatabase;
import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import timber.log.Timber;

/**
 * Created by tencent on 10/8/16.
 */
public class PetApplication extends Application {

    private static AppComponent component;
    private static PetApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

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

        String apiKey = BuildConfig.PETFINDER_API_KEY;

        component = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(apiUrl, apiKey))
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent appComponent() {
        return component;
    }

    public static final PetApplication getApp() {
        return application;
    }
}
