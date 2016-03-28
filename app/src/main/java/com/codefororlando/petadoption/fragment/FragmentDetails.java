package com.codefororlando.petadoption.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IRetrievable;
import com.squareup.picasso.Picasso;

public class FragmentDetails extends Fragment {

    public static final String TAG = "FragmentDetails";

    private static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static FragmentDetails newInstance(@NonNull Context context,
                                              @NonNull IAnimal animal) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ANIMAL, animal);

        FragmentDetails fragmentDetails = new FragmentDetails();
        fragmentDetails.setArguments(bundle);

        if (Is.equal(SdkVersion.KITKAT)) {
            final int duration = context.getResources()
                    .getInteger(android.R.integer.config_mediumAnimTime);

            fragmentDetails.setSharedElementEnterTransition(new AutoTransition().setDuration(duration));
            fragmentDetails.setSharedElementReturnTransition(new AutoTransition().setDuration(duration));
        } else if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            Transition transition = TransitionInflater.from(context)
                    .inflateTransition(R.transition.transition_transform_bounds);

            fragmentDetails.setSharedElementEnterTransition(transition);
            fragmentDetails.setSharedElementReturnTransition(transition);
        }

        return fragmentDetails;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.puppy_image);

        Bundle arguments = getArguments();
        if (arguments != null) {
            IAnimal animal = arguments.getParcelable(EXTRA_ANIMAL);

            if (animal == null) {
                throw new NullPointerException("Missing required animal argument");
            }

            Uri uri = animal.getImages().get(0).getUri();
            Picasso.with(imageView.getContext()).load(uri).into(imageView);

            if (Is.equal(SdkVersion.KITKAT)) {
                ViewCompat.setTransitionName(imageView, animal.getTag());
            } else if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
                imageView.setTransitionName(animal.getTag());
            }
        }
        return view;
    }
}
