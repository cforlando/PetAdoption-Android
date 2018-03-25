package com.codefororlando.petadoption.di.module;

import android.app.Application;
import android.content.Context;

import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.data.provider.petfinder.PetfinderProvider;
import com.codefororlando.petadoption.helper.IPreferencesHelper;
import com.codefororlando.petadoption.helper.ILocationManager;
import com.codefororlando.petadoption.helper.LocationManager;
import com.codefororlando.petadoption.helper.PreferencesHelper;
import com.codefororlando.petadoption.data.provider.petfinder.PetfinderShelterProvider;
import com.codefororlando.petadoption.network.IPetfinderService;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;
import com.codefororlando.petadoption.recyclerview.AnimalListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tencent on 10/8/16.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext(Application application){
        return application;
    }

    @Provides
    @Singleton
    IPreferencesHelper providePreferencesHelper(Context context) { return new PreferencesHelper(context); }

    @Provides
    @Singleton
    ILocationManager provideLocationManager(Context context) { return new LocationManager(context); }

    @Provides
    @Singleton
    IAnimalProvider providePetfinderProvider(IPetfinderService petfinderService, IPreferencesHelper preferencesHelper) {
        return new PetfinderProvider(petfinderService, preferencesHelper);
    }

    @Provides
    @Singleton
    IShelterProvider provideShelterProvider(IPetfinderService petfinderService) {
        return new PetfinderShelterProvider(petfinderService);
    }

    @Provides
    AAnimalListAdapter provideAnimalListAdapter() {
        return new AnimalListAdapter();
    }


    @Provides
    AnimalListAdapter provideAnimalListAdapterA() {
        return new AnimalListAdapter();
    }

}
