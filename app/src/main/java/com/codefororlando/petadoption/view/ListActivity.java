package com.codefororlando.petadoption.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.presenter.details.DetailsPresenter;
import com.codefororlando.petadoption.presenter.list.ListPresenter;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;

import javax.inject.Inject;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal list display
 */
@RequiresPresenter(ListPresenter.class)
public class ListActivity extends NucleusAppCompatActivity<ListPresenter> {

    @Inject
    AAnimalListAdapter animalListAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        final int gridSpans = getResources()
                .getInteger(R.integer.grid_spans);

        recyclerView.setLayoutManager(new GridLayoutManager(this, gridSpans));
        recyclerView.setAdapter(animalListAdapter);
    }

    public void navigateToDetailView(Animal animal) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void notifyAnimalLoadingFailed() {
        Toast.makeText(this, "Failed to get animals", Toast.LENGTH_SHORT)
                .show();
    }

    public AAnimalListAdapter getAnimalListAdapter() {
        return animalListAdapter;
    }

}
