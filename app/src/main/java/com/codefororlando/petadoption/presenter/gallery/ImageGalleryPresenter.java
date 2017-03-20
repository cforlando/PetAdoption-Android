package com.codefororlando.petadoption.presenter.gallery;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.view.ImageGalleryActivity;

import nucleus.presenter.Presenter;

public class ImageGalleryPresenter extends Presenter<ImageGalleryActivity> {

    @Override
    protected void onTakeView(ImageGalleryActivity imageGalleryActivity) {
        super.onTakeView(imageGalleryActivity);

        ((PetApplication) imageGalleryActivity.getApplication()).appComponent()
                .inject(this);
    }

}
