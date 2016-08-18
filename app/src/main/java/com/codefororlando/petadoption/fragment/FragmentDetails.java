package com.codefororlando.petadoption.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.impl.PetAdoptionProvider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentDetails extends Fragment implements View.OnClickListener {

    public static final String TAG = "FragmentDetails";
    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    private Toolbar toolbar;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.puppy_image);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            IAnimal animal = arguments.getParcelable(EXTRA_ANIMAL);

            if (animal == null) {
                throw new NullPointerException("Missing required animal argument");
            }

            setAnimalDetails(rootView, animal);

            Callback imageLoadCallback = new Callback() {
                @Override
                public void onSuccess() {
                    collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedDetailAppBar);
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedDetailAppBar);
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                }

                @Override
                public void onError() {
                    collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedDetailPlaceHolderAppBar);
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedDetailPlaceHolderAppBar);
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_primary_24dp);
                }
            };
            loadAnimalPicture(animal, imageView, imageLoadCallback);
        }
        return rootView;
    }


    @DrawableRes
    private int getAnimalPlaceholder(IAnimal animal) {
        //Todo move this and the getAnimalPlaceholder method in FragmentListings into one place
        switch (animal.getSpecies()) {
            case "cat":
                return R.drawable.placeholder_cat;
            case "dog":
                return R.drawable.placeholder_dog;
            default:
                throw new IllegalArgumentException("Unknown species " + animal.getSpecies());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fragment_details_action_call)
                .setOnClickListener(this);
        view.findViewById(R.id.fragment_details_action_email)
                .setOnClickListener(this);
        view.findViewById(R.id.fragment_details_action_web)
                .setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                fm.popBackStack(FragmentDetails.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        IAnimal animal = getArguments().getParcelable(EXTRA_ANIMAL);
        if (animal == null) {
            return;
        }

        String shelterId = animal.getShelterId();
        //Todo use shelterId to get shelter contact info
        Intent contactIntent;
        switch (v.getId()) {
            case R.id.fragment_details_action_call:
                contactIntent = new Intent(Intent.ACTION_DIAL);
                String uri = "tel:3527511530";
                contactIntent.setData(Uri.parse(uri));
                startActivity(contactIntent);
                break;
            case R.id.fragment_details_action_email:
                contactIntent = new Intent(Intent.ACTION_SEND);
                contactIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@ladylake.org"});
                contactIntent.putExtra(Intent.EXTRA_SUBJECT, "Request Information on " + animal.getName());
                contactIntent.setType("plain/text");
                ;
                break;
            case R.id.fragment_details_action_web:
                contactIntent = new Intent(Intent.ACTION_VIEW);
                contactIntent.setData(Uri.parse("http://ladylake.org/departments/police-department/animal-control-2"));
                break;
            default:
                throw new IllegalArgumentException("Unhandled click for " + v);
        }
        startActivity(contactIntent);
    }

    public void setAnimalDetails(View rootView, IAnimal animal) {
        TextView gender = (TextView) rootView.findViewById(R.id.gender);
        TextView size = (TextView) rootView.findViewById(R.id.size);
        TextView age = (TextView) rootView.findViewById(R.id.age);
        TextView location = (TextView) rootView.findViewById(R.id.city_state);
        TextView description = (TextView) rootView.findViewById(R.id.description);

        toolbar.setTitle(animal.getName());
        gender.setText(getAnimalPropertyOrDefault(animal.getGender()));
        size.setText(getUnavailableFieldDefault());
        age.setText(getAnimalPropertyOrDefault(animal.getAge()));
        location.setText(getUnavailableFieldDefault());
        description.setText(animal.getDescription());
    }

    private String getUnavailableFieldDefault() {
        return "N/A";
    }

    private String getAnimalPropertyOrDefault(String property) {
        if (property != null && property.length() > 0) {
            return property;
        }
        return getUnavailableFieldDefault();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void loadAnimalPicture(@NonNull final IAnimal animal,
                                   @NonNull final ImageView animalImageView,
                                   @NonNull final Callback imageLoadCallback) {
        PetAdoptionProvider animalProvider = new PetAdoptionProvider(getActivity());
        List<String> qualifiedImagePaths = animalProvider.getQualifiedImagePaths(animal);
        if (qualifiedImagePaths.size() > 0) {
            Picasso.with(animalImageView.getContext())
                    .load(qualifiedImagePaths.get(0))
                    .placeholder(getAnimalPlaceholder(animal))
                    .into(animalImageView, imageLoadCallback);

        } else {
            Drawable drawable = getContext().getDrawable(getAnimalPlaceholder(animal));
            animalImageView.setImageDrawable(drawable);
        }

        if (Is.equal(SdkVersion.KITKAT)) {
            ViewCompat.setTransitionName(animalImageView, animal.getTag());
        } else if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            animalImageView.setTransitionName(animal.getTag());
        }
    }

}
