package au.edu.usc.adm017.myactivities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import au.edu.usc.adm017.myactivities.R;
import au.edu.usc.adm017.myactivities.ViewActivities;
import au.edu.usc.adm017.myactivities.fragments.settings.AppSettings;

public class ModifySettingsFragment extends Fragment {

    private View view;

    private EditText profileId;
    private EditText profileName;
    private EditText profileEmail;
    private EditText profileComment;
    private Spinner profileGender;
    private Button saveProfile;
    private AppSettings profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        this.view = inflater.inflate(R.layout.modify_settings, container, false);
        this.construct();
        return this.view;
    }

    public void construct() {
        this.locateResources();
        this.loadProfile();
    }

    /**
     * Locates all resources for this fragment and sets them to the fields.
     */
    private void locateResources() {

        if (this.view == null) {
            return;
        }

        profileId = (EditText) view.findViewById(R.id.profile_id);
        profileName = (EditText) view.findViewById(R.id.profile_name);
        profileEmail =(EditText) view.findViewById(R.id.profile_email);
        profileComment = (EditText) view.findViewById(R.id.profile_comment);
        profileGender = (Spinner) view.findViewById(R.id.profile_gender);
        saveProfile = (Button)  view.findViewById(R.id.profile_save);

        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                genders);

        this.profileGender.setAdapter(adapter);
    }

    /**
     * Loads activity data from the arguments passed
     */
    public void loadProfile() {

        this.profile = ViewActivities.getDbHelper().getSettings();

        if (this.profile == null) {
            this.profile = ViewActivities.getDbHelper().createSettings(1, "Full Name", "Email", "0", "");
        }

        this.profileId.setText(profile.getId() + "");
        this.profileName.setText(profile.getName() + "");
        this.profileEmail.setText(profile.getEmail() + "");
        this.profileComment.setText(profile.getComment() + "");
        this.profileGender.setSelection(Integer.parseInt(profile.getGender()));

        this.saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSave(true);
            }
        });

        this.profileName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start,  int before, int count) {
                performSave(false);
            }
        });

        this.profileComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start,  int before, int count) {
                performSave(false);
            }
        });

        this.profileEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start,  int before, int count) {
                performSave(false);
            }
        });

        this.profileGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("ITEM SELECT" + position);
                performSave(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Performs saving of profile.
     *
     * @param close true if close this activity
     */
    private void performSave(boolean close) {

        this.profile.setName(this.profileName.getText().toString());
        this.profile.setGender(this.profileGender.getSelectedItemPosition()+ "");
        this.profile.setEmail(this.profileEmail.getText().toString());
        this.profile.setComment(this.profileComment.getText().toString());

        ViewActivities.getDbHelper().saveSettings(profile);

        if (close) {
            ViewActivities.getInstance().refreshFragment();
            getActivity().finish();
        }
    }

}