package com.codefororlando.petadoption.view;

import android.os.Bundle;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.presenter.ListPresenter;

import javax.inject.Inject;

import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal list display
 */
public class ListActivity extends NucleusAppCompatActivity<ListPresenter> {

    @Inject
    ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ((PetApplication) getApplication()).component()
                .inject(this);
    }

}
