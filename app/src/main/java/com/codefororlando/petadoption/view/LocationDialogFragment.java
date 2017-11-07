package com.codefororlando.petadoption.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.presenter.list.LocationDialogPresenter;

/**
 * Created by ryan on 11/6/17.
 */

public class LocationDialogFragment extends DialogFragment {

    private EditText locationEditText;
    private ImageButton findLocationButton;

    private LocationDialogPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
        presenter = new LocationDialogPresenter(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_location, null);

        locationEditText = (EditText) layout.findViewById(R.id.location_edit_text);
        findLocationButton = (ImageButton) layout.findViewById(R.id.location_button);

        locationEditText.setText(presenter.getLocation());

        findLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationEditText.setText(presenter.fetchLocation());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle("Set Location")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.setLocation(getEnteredZip());
                                ((ListActivity) getActivity()).refreshList();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setView(layout)
                .create();
    }

    @NonNull
    private String getEnteredZip() {
        return locationEditText.getText().toString();
    }
}
