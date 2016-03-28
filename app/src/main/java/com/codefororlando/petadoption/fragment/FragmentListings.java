package com.codefororlando.petadoption.fragment;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IAnimalProvider;
import com.codefororlando.petadoption.data.IRetrievable;
import com.codefororlando.petadoption.data.impl.StubbedAnimalProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragmentListings extends Fragment {

    public static final String TAG = "FragmentListings";

    private IAnimalProvider animalProvider;
    private AnimalAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animalProvider = new StubbedAnimalProvider(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        adapter = new AnimalAdapter(new ClickListenerImpl());
        adapter.setAnimals(animalProvider.getAnimals());

        final int spans = getResources().getInteger(R.integer.grid_spans);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spans);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    interface IClickListener {

        void onClick(AnimalViewHolder viewHolder, int position);

    }

    static class AnimalAdapter extends RecyclerView.Adapter<AnimalViewHolder> {

        final IClickListener clickListener;
        final List<IAnimal> animals;

        public AnimalAdapter(IClickListener clickListener) {
            this.clickListener = clickListener;
            animals = new ArrayList<>();
        }

        @Override
        public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.animal_item, parent, false);

            return new AnimalViewHolder(view, clickListener);
        }

        @Override
        public void onBindViewHolder(AnimalViewHolder holder, int position) {
            IAnimal animal = animals.get(position);
            holder.bind(animal);
        }

        @Override
        public int getItemCount() {
            return animals.size();
        }

        void setAnimals(List<IAnimal> animals) {
            this.animals.clear();
            this.animals.addAll(animals);
        }
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final IClickListener clickListener;
        private ImageView imageView;
        private IAnimal animal;

        public AnimalViewHolder(View itemView, IClickListener clickListener) {
            super(itemView);

            this.clickListener = clickListener;

            imageView = (ImageView) itemView.findViewById(R.id.puppy_image);
            imageView.setOnClickListener(this);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        void bind(IAnimal animal) {

            this.animal = animal;

            if (Is.equal(SdkVersion.KITKAT)) {
                ViewCompat.setTransitionName(imageView, animal.getTag());
            } else if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
                imageView.setTransitionName(animal.getTag());
            }

            Uri uri = animal.getImages().get(0).getUri();
            Picasso.with(imageView.getContext()).load(uri).into(imageView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(this, getAdapterPosition());
        }

        ImageView getImageView() {
            return imageView;
        }

        IAnimal getAnimal() {
            return animal;
        }
    }

    class ClickListenerImpl implements IClickListener {

        @Override
        public void onClick(AnimalViewHolder viewHolder, int position) {

            IAnimal animal = viewHolder.getAnimal();
            ImageView imageView = viewHolder.getImageView();

            String tag = animal.getTag();

            getFragmentManager().beginTransaction()
                    .addSharedElement(imageView, tag)
                    .replace(R.id.container, FragmentDetails.newInstance(getContext(), animal), FragmentDetails.TAG)
                    .addToBackStack(FragmentDetails.TAG)
                    .addSharedElement(imageView, tag)
                    .commit();
        }

    }

}
