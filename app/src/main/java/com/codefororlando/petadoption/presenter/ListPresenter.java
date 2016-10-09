package com.codefororlando.petadoption.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codefororlando.petadoption.view.DetailsActivity;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by tencent on 10/8/16.
 */
public class ListPresenter extends Presenter<DetailsActivity> {

    private final Retrofit retrofit;

    @Inject
    ListPresenter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
    }

}
