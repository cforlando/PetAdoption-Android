package com.codefororlando.petadoption.component;

import android.app.Application;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.module.AppModule;
import com.codefororlando.petadoption.module.NetworkModule;
import com.codefororlando.petadoption.view.DetailsActivity;
import com.codefororlando.petadoption.view.ListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(PetApplication application);
    void inject(ListActivity target);
    void inject(DetailsActivity target);

    Application application();

}
