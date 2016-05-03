package com.codefororlando.petadoption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codefororlando.petadoption.data.impl.Animal;
import com.codefororlando.petadoption.fragment.FragmentListings;
import com.codefororlando.petadoption.network.PetAdoptionService;

import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentListings(), FragmentListings.TAG)
                    .commit();
        }



    }

}
