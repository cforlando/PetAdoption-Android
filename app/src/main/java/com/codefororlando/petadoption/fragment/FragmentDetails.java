package com.codefororlando.petadoption.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.TypedValue;
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
import com.squareup.picasso.Picasso;

public class FragmentDetails extends Fragment {

    public static final String TAG = "FragmentDetails";
    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";
    String collapsedTitle = "";

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
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.puppy_image);

        Bundle arguments = getArguments();
        if (arguments != null) {
            IAnimal animal = arguments.getParcelable(EXTRA_ANIMAL);

            if (animal == null) {
                throw new NullPointerException("Missing required animal argument");
            }

            String uri = animal.getImages().get(0);
            Picasso.with(imageView.getContext()).load(uri).into(imageView);

            if (Is.equal(SdkVersion.KITKAT)) {
                ViewCompat.setTransitionName(imageView, animal.getTag());
            } else if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
                imageView.setTransitionName(animal.getTag());
            }
            setAnimalDetails(rootView, animal);
        }

        final AppCompatActivity activity = (AppCompatActivity)getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        final CollapsingToolbarLayout collapsingToolbarLayout
                = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(getActivity(), android.R.color.transparent));

        AppBarLayout appBarLayout = (AppBarLayout)rootView.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < getActionBarHeight(activity)) {
                    collapsingToolbarLayout.setTitle(collapsedTitle);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                fm.popBackStack (FragmentDetails.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAnimalDetails(View rootView, IAnimal animal) {
        TextView name = (TextView) rootView.findViewById(R.id.title);
        TextView breed = (TextView) rootView.findViewById(R.id.title_subtext);
        TextView gender = (TextView) rootView.findViewById(R.id.gender);
        TextView size = (TextView) rootView.findViewById(R.id.size);
        TextView age = (TextView) rootView.findViewById(R.id.age);
        TextView location = (TextView) rootView.findViewById(R.id.city_state);

        TextView description = (TextView) rootView.findViewById(R.id.description);
        TextView locationName = (TextView) rootView.findViewById(R.id.location_name);
        TextView locationStreet = (TextView) rootView.findViewById(R.id.location_street);
        TextView locationCityStateZip = (TextView) rootView.findViewById(R.id.location_city_state_zip);

        name.setText(animal.getName());
        breed.setText(animal.getBreed());
        gender.setText(animal.getGender());
//        size.setText("TODO");
        age.setText(String.valueOf(animal.getAge()));
//        location.setText("TODO");

        description.setText(animal.getDescription());
//        locationName.setText("TODO");
//        locationStreet.setText("TODO");
//        locationCityStateZip.setText("TODO");

        collapsedTitle = animal.getName() + " - " + animal.getBreed();
    }

    //TODO move this to a util file or something.
    public static int getActionBarHeight(final Context context)
    {
        // based on http://stackoverflow.com/questions/12301510/how-to-get-the-actionbar-height
        final TypedValue tv=new TypedValue();
        int actionBarHeight=0;
        if(context.getTheme().resolveAttribute(R.attr.actionBarSize,tv,true))
            actionBarHeight= TypedValue.complexToDimensionPixelSize(tv.data,context.getResources()
                    .getDisplayMetrics());
        return actionBarHeight;
    }

}
