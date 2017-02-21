package com.codefororlando.petadoption.presenter.gallery;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.view.ImageGalleryActivity;
import com.codefororlando.petadoption.viewpager.ImageGalleryAdapter;

import nucleus.presenter.Presenter;

public class ImageGalleryPresenter extends Presenter<ImageGalleryActivity> {

    @Override
    protected void onTakeView(ImageGalleryActivity imageGalleryActivity) {
        super.onTakeView(imageGalleryActivity);

        ((PetApplication) imageGalleryActivity.getApplication()).appComponent()
                .inject(this);

        Animal animal = imageGalleryActivity.getAnimal();
        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(animal);
        imageGalleryActivity.setAdapter(imageGalleryAdapter);
    }

}
