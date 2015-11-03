package com.codefororlando.petadoption.fragment;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.codefororlando.petadoption.R;

public class FragmentLaunch extends Fragment implements View.OnClickListener {

    public static final String TAG = "FragmentLaunch";

    private Runnable endLaunchRunner;
    private Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        endLaunchRunner = new Runnable() {
            @Override
            public void run() {
                endLaunch();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);

        //Outline the image for proper shadow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupOutline(view);
        }

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(endLaunchRunner, 1500);
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(endLaunchRunner);
    }

    private void endLaunch() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new FragmentListings(), FragmentListings.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupOutline(View view) {
        view.findViewById(R.id.launch_puppy)
                .setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        int size = getResources().getDimensionPixelSize(R.dimen.launch_image_size);
                        outline.setOval(0, 0, size, size);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        endLaunch();
    }

}
