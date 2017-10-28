package au.edu.usc.adm017.myactivities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import au.edu.usc.adm017.myactivities.fragments.ModifyActivityFragment;

public class ModifyActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    private FragmentManager fm;
    private ModifyActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        this.setupFragmentManager();
    }

    /**
     * Load the fragment for this activity
     */
    private void setupFragmentManager() {
        this.fm = this.getSupportFragmentManager();

        if (this.fragment == null) {
            this.refreshFragment();
        }
    }

    @Override
    public void onRestart() {
        this.refreshFragment();
        super.onRestart();
    }

    /*
     * Refresh the fragment for this activity, will remove any previous fragment added.
     */
    public void refreshFragment() {

        if (this.fm != null) {

            if (this.fragment != null) { // we reload it...
                this.fragment.construct();
            } else {
                if (this.savedInstanceState == null) { // or else... first time? then we create the fragment
                    this.fragment = new ModifyActivityFragment();
                    this.fragment.setArguments(getIntent().getExtras());
                    FragmentTransaction transaction = this.fm.beginTransaction().add(R.id.app_main, this.fragment, "fragment");
                    transaction.commit();
                }
            }
        }
    }
}
