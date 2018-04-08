package com.codefororlando.petadoption.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.data.provider.consumer.UpdateAnimalEntityConsumer;
import com.codefororlando.petadoption.data.provider.petfinder.PetfinderProvider;
import com.codefororlando.petadoption.helper.IPreferencesHelper;
import com.codefororlando.petadoption.helper.ILocationManager;
import com.codefororlando.petadoption.helper.LocationManager;
import com.codefororlando.petadoption.helper.PreferencesHelper;
import com.codefororlando.petadoption.data.provider.petfinder.PetfinderShelterProvider;
import com.codefororlando.petadoption.network.IPetfinderService;
import com.codefororlando.petadoption.persistence.dao.AnimalAndImagesDao;
import com.codefororlando.petadoption.persistence.dao.AnimalDao;
import com.codefororlando.petadoption.persistence.PetDatabase;
import com.codefororlando.petadoption.persistence.dao.AnimalImageDao;
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
    IAnimalProvider providePetfinderProvider(IPetfinderService petfinderService, IPreferencesHelper preferencesHelper, UpdateAnimalEntityConsumer updateAnimalEntityConsumer) {
        return new PetfinderProvider(petfinderService, preferencesHelper, updateAnimalEntityConsumer);
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

    @Provides
    @Singleton
    PetDatabase providePetDatabase(Context context) {
        PetDatabase db = Room.databaseBuilder(context,
                PetDatabase.class, "pet-adoption-db").build();
        return db;
    }

    @Provides
    @Singleton
    AnimalDao provideAnimalDao(PetDatabase petDatabase) {
        return petDatabase.animalDao();
    }

    @Provides
    @Singleton
    AnimalImageDao provideAnimalImageDao(PetDatabase petDatabase) {
        return petDatabase.animalImageDao();
    }

    @Provides
    @Singleton
    AnimalAndImagesDao provideAnimalAndImageDao(PetDatabase petDatabase) {
        return petDatabase.animalAndImagesDao();
    }

}
