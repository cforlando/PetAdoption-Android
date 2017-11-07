package com.codefororlando.petadoption.di.component;

import android.app.Application;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.di.module.AppModule;
import com.codefororlando.petadoption.di.module.NetworkModule;
import com.codefororlando.petadoption.presenter.details.DetailsPresenter;
import com.codefororlando.petadoption.presenter.list.ListPresenter;
import com.codefororlando.petadoption.presenter.list.LocationDialogPresenter;
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
    void inject(ListPresenter listPresenter);
    void inject(DetailsPresenter detailsPresenter);
    void inject(LocationDialogPresenter locationDialogPresenter);

    Application application();
}
