package com.codefororlando.petadoption.di.component;

import android.app.Application;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.di.module.AppModule;
import com.codefororlando.petadoption.di.module.NetworkModule;
import com.codefororlando.petadoption.detail.DetailsPresenter;
import com.codefororlando.petadoption.feed.PetFeedFragment;
import com.codefororlando.petadoption.feed.PetFeedPresenter;
import com.codefororlando.petadoption.presenter.list.ListPresenter;
import com.codefororlando.petadoption.presenter.list.LocationDialogPresenter;
import com.codefororlando.petadoption.detail.DetailsActivity;
import com.codefororlando.petadoption.feed.ListActivity;

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
    void inject(PetFeedFragment target);
    void inject(PetFeedPresenter target);

    Application application();
}
