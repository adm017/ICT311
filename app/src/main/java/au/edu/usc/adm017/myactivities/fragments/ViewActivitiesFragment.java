package au.edu.usc.adm017.myactivities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import au.edu.usc.adm017.myactivities.CreateActivity;
import au.edu.usc.adm017.myactivities.ModifySettings;
import au.edu.usc.adm017.myactivities.R;
import au.edu.usc.adm017.myactivities.ViewActivities;
import au.edu.usc.adm017.myactivities.activity.AppActivity;
import au.edu.usc.adm017.myactivities.fragments.adapter.ActivityAdapter;

public class ViewActivitiesFragment extends Fragment{

    private View view;
    private ListView activityList;

    private Button logButton;
    private Button settingsButton;

    private ViewActivities parent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.parent = (ViewActivities)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        this.view = inflater.inflate(R.layout.view_activities, container, false);
        this.construct();
        return this.view;
    }

    public void construct() {
        this.locateResources();
        this.addActivities();
        this.setupButtons();
    }


    /**
     * Locates all resources for this fragment and sets them to the fields.
     */
    private void locateResources() {

        if (this.view == null) {
            return;
        }

        this.activityList = (ListView)this.view.findViewById(R.id.activity_list);
        this.logButton = (Button)this.view.findViewById(R.id.activity_button_log);
        this.settingsButton = (Button)this.view.findViewById(R.id.activity_button_settings);
    }

    /**
     * Add all activities
     */
    private void addActivities() {

        List<AppActivity> activities = ViewActivities.getDbHelper().getActivitiesFromDb();

        if (activities.isEmpty()) {
            activities.add(null); // add a null to for the adapter to tell the user to start adding more
        }

        ActivityAdapter adapter = new ActivityAdapter(
                activities,
                this.activityList.getContext(),
                R.id.activity_list,
                R.layout.view_activitie_row);

        activityList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Setup click button handler which shows the new create activity activity
     */
    private void setupButtons() {
        this.logButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateActivity.class);
                startActivity(i);
            }
        });

        this.settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ModifySettings.class);
                startActivity(i);
            }
        });
    }
}
