package com.codefororlando.petadoption.di.component;

import android.app.Application;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.di.module.AppModule;
import com.codefororlando.petadoption.di.module.NetworkModule;
import com.codefororlando.petadoption.presenter.details.DetailsPresenter;
import com.codefororlando.petadoption.presenter.gallery.ImageGalleryPresenter;
import com.codefororlando.petadoption.presenter.list.ListPresenter;
import com.codefororlando.petadoption.view.DetailsActivity;
import com.codefororlando.petadoption.view.ImageGalleryActivity;
import com.codefororlando.petadoption.view.ListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(PetApplication target);
    void inject(ListActivity target);
    void inject(DetailsActivity target);
    void inject(ImageGalleryActivity target);
    void inject(ListPresenter target);
    void inject(DetailsPresenter target);
    void inject(ImageGalleryPresenter target);

    Application application();
}
