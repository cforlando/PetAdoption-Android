package com.codefororlando.petadoption.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.codefororlando.petadoption.R;
import com.koushikdutta.ion.Ion;

public class FragmentListings extends Fragment {

    public static final String TAG = "FragmentListings";
    static final int[] PUPPIES = {
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
            R.drawable.cat_5,
            R.drawable.cat_6,
            R.drawable.cat_7,
            R.drawable.cat_8
    };
    private Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        adapter = new Adapter(new ClickListenerImpl());

        final int spans = getResources().getInteger(R.integer.grid_spans);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spans));
        recyclerView.setAdapter(adapter);

        return view;
    }

    interface ClickListener {

        void onClick(ViewHolder viewHolder, int position);

    }

    static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        final ClickListener clickListener;

        public Adapter(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.puppy_listing, parent, false);

            return new ViewHolder(view, clickListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(PUPPIES[position]);
        }

        @Override
        public int getItemCount() {
            return PUPPIES.length;
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ClickListener clickListener;
        private ImageView imageView;

        public ViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            this.clickListener = clickListener;

            imageView = (ImageView) itemView.findViewById(R.id.puppy_image);
            imageView.setOnClickListener(this);
        }

        void bind(int puppyRes) {
            Uri uri = Uri.parse("android.resource://" + imageView.getContext().getPackageName() + "/" + puppyRes);
            Ion.with(imageView.getContext())
                    .load(uri.toString())
                    .withBitmap()
                    .centerCrop()
                    .intoImageView(imageView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(this, getAdapterPosition());
        }

        ImageView getImageView() {
            return imageView;
        }

    }

    class ClickListenerImpl implements ClickListener {

        @Override
        public void onClick(ViewHolder viewHolder, int position) {

            // Get the coordinates of the source view
            ImageView sourceImageView = viewHolder.getImageView();
            final int[] coordinates = new int[2];
            sourceImageView.getLocationInWindow(coordinates);
            coordinates[1] -= getStatusBarHeight();

            // Find the floating layer
            ViewGroup floatingLayer = (ViewGroup) (getActivity().findViewById(android.R.id.content))
                    .findViewById(R.id.floating_layer);

            // Create the layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(sourceImageView.getWidth(), sourceImageView.getHeight());
            //layoutParams.setMargins(coordinates[0], coordinates[1], 0, 0);

            // Create the floating view
            View floatingView = LayoutInflater.from(getContext())
                    .inflate(R.layout.puppy_listing, floatingLayer, false);
            floatingView.setX(coordinates[0]);
            floatingView.setY(coordinates[1]);
            floatingLayer.addView(floatingView, layoutParams);

            // Bring in the selected view into the new floating view
            ImageView floatingImageView = (ImageView) floatingView.findViewById(R.id.puppy_image);
            Uri uri = Uri.parse("android.resource://" + floatingImageView.getContext().getPackageName() + "/" + PUPPIES[position]);
            Ion.with(floatingImageView.getContext())
                    .load(uri.toString())
                    .withBitmap()
                    .centerCrop()
                    .intoImageView(floatingImageView);

            // Bring in the new fragment
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                    .replace(R.id.container, new FragmentDetails(), FragmentDetails.TAG)
                    .commit();

            // Move the image
            floatingImageView.animate()
                    .translationX(0)
                    .translationY(0)
                    .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime))
                    .start();
        }

        public int getStatusBarHeight() {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

    }

}
