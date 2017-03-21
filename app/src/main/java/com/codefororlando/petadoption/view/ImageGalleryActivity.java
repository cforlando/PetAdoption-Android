package com.codefororlando.petadoption.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.presenter.gallery.ImageGalleryPresenter;
import com.codefororlando.petadoption.viewpager.ImageGalleryAdapter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ImageGalleryPresenter.class)
public class ImageGalleryActivity extends NucleusAppCompatActivity<ImageGalleryPresenter> {

    private static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    private Animal animal;

    public static Intent createIntent(
            Context context,
            Animal animal) {

        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra(EXTRA_ANIMAL, animal);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        animal = getIntent().getParcelableExtra(EXTRA_ANIMAL);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_image_gallery_view_pager);
        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(animal);
        viewPager.setAdapter(imageGalleryAdapter);
    }

    public Animal getAnimal() {
        return animal;
    }

}
