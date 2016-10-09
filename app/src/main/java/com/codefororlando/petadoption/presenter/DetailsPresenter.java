package com.codefororlando.petadoption.presenter;

import com.codefororlando.petadoption.view.DetailsActivity;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by tencent on 10/8/16.
 */
public class DetailsPresenter extends Presenter<DetailsActivity> {

    private final Retrofit retrofit;

    @Inject
    DetailsPresenter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

}
