package au.edu.usc.adm017.myactivities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import au.edu.usc.adm017.myactivities.R;
import au.edu.usc.adm017.myactivities.ViewActivities;
import au.edu.usc.adm017.myactivities.activity.ActivityType;
import au.edu.usc.adm017.myactivities.activity.AppActivity;
import au.edu.usc.adm017.myactivities.fragments.util.Util;

public class ModifyActivityFragment extends Fragment {

    private View view;

    private EditText activityTitle;
    private Spinner activityTypes;
    private Button dateSelectButton;

    private Calendar dateSelected;
    private EditText activityDate;

    private EditText activityPlace;
    private EditText activityComments;

    private Button takePhotoButton;
    private ImageView showPhotoView;

    private Button cancelButton;
    private Button saveButton;
    private Button deleteButton;
    private Button mapButton;

    private Bitmap photoTaken;
    private AppActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        this.view = inflater.inflate(R.layout.modify_activity, container, false);

        // Construct the rest
        this.construct();

        // Had to move the method out of here otherwise taking photos won't update the image.
        this.setupPhotoButtons();

        // Load activity
        this.loadActivity();

        return this.view;
    }

    public void construct() {
        this.locateResources();
        this.addActivityTypes();
        this.setupDateSelector();
        this.setupSaveCancelButtons();
        this.setupDeleteButton();
        this.setupMapButton();
    }


    /**
     * Locates all resources for this fragment and sets them to the fields.
     */
    private void locateResources() {

        if (this.view == null) {
            return;
        }

        this.activityTitle = (EditText) this.view.findViewById(R.id.activity_create_title);
        this.activityTypes = (Spinner) this.view.findViewById(R.id.activity_create_type);
        this.dateSelectButton = (Button) this.view.findViewById(R.id.activity_create_date_select);
        this.activityDate = (EditText) this.view.findViewById(R.id.activity_create_date_show);
        this.activityPlace = (EditText) this.view.findViewById(R.id.activity_create_place);
        this.activityComments = (EditText) this.view.findViewById(R.id.activity_create_comment);
        this.takePhotoButton = (Button) this.view.findViewById(R.id.activity_create_take_photo);
        this.showPhotoView = (ImageView) this.view.findViewById(R.id.activity_create_show_photo);
        this.cancelButton = (Button) this.view.findViewById(R.id.activity_create_button_cancel);
        this.saveButton = (Button) this.view.findViewById(R.id.activity_create_button_save);
        this.deleteButton = (Button) this.view.findViewById(R.id.activity_modify_button_delete);
        this.mapButton = (Button) this.view.findViewById(R.id.activity_modify_button_map);
    }

    /**
     * Loads activity data from the arguments passed
     */
    public void loadActivity() {

        if (getArguments() != null) {
            int activityId = getArguments().getInt("activityId", 0);

            if (activityId > 0) {
                this.activity = ViewActivities.getDbHelper().getActivityById(activityId);

                if (activity != null) {
                    this.activityTitle.setText(activity.getTitle());

                    int position = 0;
                    int typePosition = 0;
                    for (ActivityType type : ActivityType.values()) {

                        if (type == activity.getType()) {
                            typePosition = position;
                        }

                        position++;
                    }

                    this.activityTypes.setSelection(typePosition);
                    this.activityDate.setText(activity.getDate());
                    this.activityPlace.setText(activity.getPlace());
                    this.activityComments.setText(activity.getComments());

                    if (activity.getPicture() != null) {
                        this.showPhotoView.setImageResource(0);
                        this.showPhotoView.setImageBitmap(activity.getPicture());
                        this.photoTaken = activity.getPicture();
                    } else {
                        this.showPhotoView.setImageResource(android.R.drawable.ic_menu_gallery);
                    }

                    ((TextView) this.view.findViewById(R.id.activity_title)).setText("Modify Activity");
                }
            }
        }else {

            ((TextView) this.view.findViewById(R.id.activity_title)).setText("Create Activity");

            this.showPhotoView.setImageResource(android.R.drawable.ic_menu_gallery);
            this.deleteButton.setVisibility(View.INVISIBLE);
            this.mapButton.setVisibility(View.INVISIBLE);
        }


    }

    /**
     * Adds the activity titles to the Spinner layout value.
     */
    private void addActivityTypes() {

        if (this.activityTypes == null) {
            return;
        }

        List<String> activities = new ArrayList<>();

        for (ActivityType type : ActivityType.values()) {
            activities.add(type.getHumanReadable());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                activities);

        activityTypes.setAdapter(adapter);
    }

    /**
     * Setup the date selector and the text field which will show the date, either current date or it will be
     * updated once the new date has been selected.
     */
    private void setupDateSelector() {

        if (this.dateSelectButton == null) {
            return;
        }

        // Set the present date
        this.dateSelected = Calendar.getInstance();
        this.activityDate.setText(Util.getDateAsString());

        // Add date selector dialog on click
        this.dateSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectDialog();
            }
        });
    }

    /**
     * Date selector dialog handler
     */
    private void showDateSelectDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                activityDate.setText(Util.getDateFormatter().format(dateSelected.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Setup photo buttons for handling when a picture is taken.
     */
    private void setupPhotoButtons() {

        if (this.photoTaken != null) {
            // Default blank image lets the user know where the image will appear
            this.showPhotoView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        this.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Util.CAMERA_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            showPhotoView.setImageResource(0);
            showPhotoView.setImageBitmap(photo);
            photoTaken = photo;
        }
    }

    /**
     * Setup the save and cancel button handlers
     */
    private void setupSaveCancelButtons() {

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                ViewActivities.getInstance().refreshFragment();
            }
        });

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity == null) {

                    if (activityTitle.getText().toString().isEmpty()) {
                        Util.showToast("You cannot leave the title empty!");
                        return;
                    }

                    if (activityTitle.getText().toString().length() <= 3) {
                        Util.showToast("The title is too short!");
                        return;
                    }

                    if (activityTitle.getText().toString().length() > 50) {
                        Util.showToast("The title is far too long!");
                        return;
                    }

                    ViewActivities.getDbHelper().createActivity(
                            activityTitle.getText().toString(),
                            activityDate.getText().toString(),
                            ActivityType.fromHumanReadable(activityTypes.getSelectedItem().toString()),
                            activityPlace.getText().toString(),
                            activityComments.getText().toString(),
                            photoTaken);

                    getActivity().finish();
                    ViewActivities.getInstance().refreshFragment();

                } else {
                    if (activityTitle.getText().toString().isEmpty()) {
                        Util.showToast("You cannot leave the title empty!");
                        return;
                    }

                    if (activityTitle.getText().toString().length() <= 3) {
                        Util.showToast("The title is too short!");
                        return;
                    }

                    if (activityTitle.getText().toString().length() > 50) {
                        Util.showToast("The title is far too long!");
                        return;
                    }

                    activity.setTitle(activityTitle.getText().toString());
                    activity.setDate(activityDate.getText().toString());
                    activity.setType(ActivityType.fromHumanReadable(activityTypes.getSelectedItem().toString()));
                    activity.setPlace(activityPlace.getText().toString());
                    activity.setComments(activityComments.getText().toString());
                    activity.setPicture(photoTaken);
                    ViewActivities.getDbHelper().saveActivity(activity);
                    ViewActivities.getInstance().refreshFragment();
                    getActivity().finish();
                }
            }
        });

        //ViewActivities.getDbHelper().
    }

    /**
     * Setup delete handle button, with dialog yes and no option.
     */
    private void setupDeleteButton() {

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    ViewActivities.getDbHelper().deleteActivity(activity);
                    ViewActivities.getInstance().refreshFragment();
                    getActivity().finish();
                }
            }
        };

        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(deleteButton.getContext());
                builder.setMessage("Are you sure you want to delete this activity?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
            }
        });
    }

    /**
     * Setup Map handle button
     */
    private void setupMapButton() {

        this.mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String map = "http://maps.google.com.au/maps?q=" + activity.getLatitude() + "," + activity.getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(map));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
    }
}