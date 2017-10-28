package au.edu.usc.adm017.myactivities.fragments.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import au.edu.usc.adm017.myactivities.ModifyActivity;
import au.edu.usc.adm017.myactivities.R;
import au.edu.usc.adm017.myactivities.ViewActivities;
import au.edu.usc.adm017.myactivities.activity.AppActivity;

/**
 * Created by Alex on 28/10/2017.
 */

public class ActivityAdapter  extends ArrayAdapter<AppActivity>
{
    private final Context context;
    private final List<AppActivity> activityList;
    private final int resourceRowId;

    public ActivityAdapter(List<AppActivity> activityList, Context context, int resource, int resourceRowId) {
        super(context, resource, resourceRowId, activityList);
        this.context = context;
        this.activityList = activityList;
        this.resourceRowId = resourceRowId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resourceRowId, null);
        }

        AppActivity activity = getItem(position);

        if (activity != null) {
            this.registerActivity(v, activity);
        } else {
            TextView headerLarge = v.findViewById(R.id.text_header_large);
            TextView headerSmall = v.findViewById(R.id.text_header_small);

            headerLarge.setText("No Activities Logged!");
            headerSmall.setText("Press the log button above to get started");
        }

        return v;
    }

    private void registerActivity(View view, final AppActivity activity) {
        TextView headerLarge = view.findViewById(R.id.text_header_large);
        headerLarge.setText(activity.getTitle());

        TextView headerSmall = view.findViewById(R.id.text_header_small);
        headerSmall.setText("At " + activity.getDate() + ", " + activity.getComments() + " at " + activity.getPlace() + "." + (activity.getPicture() != null ? "A photo is attached for the activity." : ""));

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewActivities.getInstance(), ModifyActivity.class);
                i.putExtra("activityId", activity.getId());
                ViewActivities.getInstance().startActivity(i);
            }
        });
    }
}
