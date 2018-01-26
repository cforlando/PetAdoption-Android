package com.codefororlando.petadoption.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.presenter.details.DetailsPresenter;
import com.codefororlando.petadoption.presenter.list.ListPresenter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal list display
 */
@RequiresPresenter(ListPresenter.class)
public class ListActivity extends NucleusAppCompatActivity<ListPresenter> {

    private RecyclerView recyclerView;
    private LocationDialogFragment locationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        locationDialog = new LocationDialogFragment();

        final int gridSpans = getResources()
                .getInteger(R.integer.grid_spans);

        recyclerView.setLayoutManager(new GridLayoutManager(this, gridSpans));

        recyclerView.addOnScrollListener(scrollToEndListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_location:
                showLocationDialog();
                return true;
            case R.id.menu_info:
                goToAboutPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshList() {
        getPresenter().refreshList();
        recyclerView.scrollToPosition(0);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void navigateToDetailView(Animal animal) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void notifyAnimalLoadingFailed() {
        showLocationDialog();
    }

    public int getLastVisibleItemIndex() {
        return ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }

    public void scrollToPosition(int index) {
        recyclerView.scrollToPosition(index);
    }

    private void showLocationDialog() {
        if(!locationDialog.isAdded()){
            locationDialog.show(getSupportFragmentManager(), "location_dialog");
        }
    }

    private RecyclerView.OnScrollListener scrollToEndListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {
                getPresenter().loadMore();
            }
        }
    };

    public void goToAboutPage() {
        Intent aboutPageIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutPageIntent);
    }
}
