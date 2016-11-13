package com.codefororlando.petadoption.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.presenter.DetailsPresenter;
import com.codefororlando.petadoption.presenter.ListPresenter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal list display
 */
@RequiresPresenter(ListPresenter.class)
public class ListActivity extends NucleusAppCompatActivity<ListPresenter> {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void onAnimalSelected(Animal animal) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
