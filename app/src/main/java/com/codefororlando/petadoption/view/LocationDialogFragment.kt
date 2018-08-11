package com.codefororlando.petadoption.view

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.ImageButton

import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.presenter.list.LocationDialogPresenter

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class LocationDialogFragment : DialogFragment() {

    private lateinit var locationEditText: EditText
    private lateinit var findLocationButton: ImageButton

    private lateinit var presenter: LocationDialogPresenter

    private val dismissalSubject = PublishSubject.create<Unit>()

    var enteredZip: String
        get() = locationEditText.text.toString()
        set(zip) = locationEditText.setText(zip)

    private val isLocationPermissionGranted: Boolean
        get() {
            val check = activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) }

            return check == PackageManager.PERMISSION_GRANTED
        }

    val shouldRefreshFeedOnDismissalObservable: Observable<Unit>
        get() = dismissalSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme)
        presenter = LocationDialogPresenter(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = activity?.layoutInflater?.inflate(R.layout.fragment_dialog_location, null)

        locationEditText = layout?.findViewById(R.id.location_edit_text) as EditText
        findLocationButton = layout?.findViewById(R.id.location_button) as ImageButton

        locationEditText.setText(presenter.location)
        val textLength = locationEditText.text.toString().length
        locationEditText.setSelection(textLength)

        findLocationButton.setOnClickListener { v ->
            if (isLocationPermissionGranted) {
                populateWithCurrentLocation()
            } else {
                requestLocationPermission()
            }
        }

        return AlertDialog.Builder(activity!!)
                .setTitle("Set Location")
                .setPositiveButton("OK"
                ) { dialog, which ->
                    presenter.location = enteredZip
                    dialog.dismiss()
                    dismissalSubject.onNext(Unit)
                }
                .setNegativeButton("Cancel"
                ) { dialog, which -> dialog.dismiss() }
                .setView(layout)
                .create()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (isLocationPermissionGranted) {
                    populateWithCurrentLocation()
                }
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun populateWithCurrentLocation() {
        presenter.fetchCurrentLocation()
    }

    companion object {
        private val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
