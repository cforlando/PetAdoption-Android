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

import com.codefororlando.petadoption.R;

/**
 * Created by ryan on 11/6/17.
 */

public class LocationDialogFragment extends DialogFragment {

    private EditText locationEditText;
    private Button findLocationButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_location, container, false);

        locationEditText = (EditText) view.findViewById(R.id.location_edit_text);
        findLocationButton = (Button) view.findViewById(R.id.location_button);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_location, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Set Location")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((ListActivity) getActivity()).onUseLocation(getEnteredZip());
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

    private String getEnteredZip() {
        return locationEditText.getText().toString();
    }
}
