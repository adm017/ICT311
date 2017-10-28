package au.edu.usc.adm017.myactivities.fragments.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import au.edu.usc.adm017.myactivities.activity.ActivityType;
import au.edu.usc.adm017.myactivities.activity.AppActivity;
import au.edu.usc.adm017.myactivities.fragments.settings.AppSettings;
import au.edu.usc.adm017.myactivities.fragments.util.Util;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyActivities.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContractActivities.SQL_CREATE_ENTRIES);
        db.execSQL(DbContractSettings.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbContractActivities.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Create new activity and store it
     *
     * @param title the title
     * @param date the date
     * @param type the type
     * @param place the place
     * @param comments the comments
     * @param picture the picture
     */
    public void createActivity(String title, String date, ActivityType type, String place, String comments, Bitmap picture) {

        String dbPicture = "";

        if (picture != null) {
            dbPicture = Util.convert(picture);
        }

        String dbCoordinates = "";
        double[] coordData = Util.getCoordinates();

        if (coordData.length > 0) {
            dbCoordinates = String.valueOf(coordData[0]) + "," + String.valueOf(coordData[1]);
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbContractActivities.COLUMN_NAME_TITLE, title);
        values.put(DbContractActivities.COLUMN_NAME_DATE, date);
        values.put(DbContractActivities.COLUMN_NAME_TYPE, type.name());
        values.put(DbContractActivities.COLUMN_NAME_PLACE, place);
        values.put(DbContractActivities.COLUMN_NAME_COMMENTS, comments);
        values.put(DbContractActivities.COLUMN_NAME_COORDINATES, dbCoordinates);
        values.put(DbContractActivities.COLUMN_NAME_PICTURE, dbPicture);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DbContractActivities.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Get list of activities from the database
     *
     * @return the list of activities
     */
    public List<AppActivity> getActivitiesFromDb() {

        List<AppActivity> activities = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContractActivities.TABLE_NAME + " ORDER BY id DESC", null);

        while(cursor.moveToNext()){

            Bitmap picture = null;
            String pictureDb = cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_PICTURE));

            if (pictureDb.length() > 0) {
                picture = Util.convert(pictureDb);
            }

            AppActivity activity = new AppActivity(
                cursor.getInt(cursor.getColumnIndex(DbContractActivities._ID)),
                cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_TITLE)),
                cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_DATE)),
                ActivityType.valueOf(cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_TYPE))),
                cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_PLACE)),
                cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_COMMENTS)),
                picture,
                cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_COORDINATES)));

            activities.add(activity);
        }

        cursor.close();
        db.close();

        return activities;
    }

    /**
     * Get list of activities from the database
     *
     * @return the list of activities
     */
    public AppActivity getActivityById(int activityId) {

        AppActivity activity = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContractActivities.TABLE_NAME + " WHERE id = " + activityId, null);

        if (cursor.moveToNext()){

            Bitmap picture = null;
            String pictureDb = cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_PICTURE));

            if (pictureDb.length() > 0) {
                picture = Util.convert(pictureDb);
            }

            activity = new AppActivity(
                    cursor.getInt(cursor.getColumnIndex(DbContractActivities._ID)),
                    cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_DATE)),
                    ActivityType.valueOf(cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_TYPE))),
                    cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_PLACE)),
                    cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_COMMENTS)),
                    picture,
                    cursor.getString(cursor.getColumnIndex(DbContractActivities.COLUMN_NAME_COORDINATES)));
        }

        cursor.close();
        db.close();

        return activity;
    }

    /**
     * Save activity
     *
     * @return the list of activities
     */
    public void saveActivity(AppActivity activity) {

        String dbPicture = "";

        if (activity.getPicture() != null) {
            dbPicture = Util.convert(activity.getPicture());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbContractActivities.COLUMN_NAME_TITLE, activity.getTitle());
        values.put(DbContractActivities.COLUMN_NAME_DATE, activity.getDate());
        values.put(DbContractActivities.COLUMN_NAME_TYPE, activity.getType().name());
        values.put(DbContractActivities.COLUMN_NAME_PLACE, activity.getPlace());
        values.put(DbContractActivities.COLUMN_NAME_COMMENTS, activity.getComments());
        values.put(DbContractActivities.COLUMN_NAME_PICTURE, dbPicture);

        String where = "id = ?";
        String[] whereArgs = new String[] {String.valueOf(activity.getId()) };

        // Edit the row, returning the primary key value of the new row
        db.update(DbContractActivities.TABLE_NAME, values, where, whereArgs);
        db.close();
    }

    /**
     * Delete activity
     *
     * @return the list of activities
     */
    public void deleteActivity(AppActivity activity) {

        SQLiteDatabase db = this.getWritableDatabase();

        String where = "id = ?";
        String[] whereArgs = new String[] {String.valueOf(activity.getId()) };

        // Edit the row, returning the primary key value of the new row
        db.delete(DbContractActivities.TABLE_NAME, where, whereArgs);
        db.close();
    }

    /**
     * Create profile
     */
    public AppSettings createSettings(int id, String name, String email, String gender, String comment) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbContractSettings.ID, id);
        values.put(DbContractSettings.COLUMN_NAME_NAME, name);
        values.put(DbContractSettings.COLUMN_NAME_EMAIL, email);
        values.put(DbContractSettings.COLUMN_NAME_GENDER, gender);
        values.put(DbContractSettings.COLUMN_NAME_COMMENT, comment);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DbContractSettings.TABLE_NAME, null, values);
        db.close();

        return new AppSettings((int)newRowId, name, email, gender, comment);
    }

    /**
     * Create profile
     */
    public AppSettings getSettings() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContractSettings.TABLE_NAME + " LIMIT 1", null);
        AppSettings profile = null;

        while (cursor.moveToNext()){

            // public AppSettings(int id, String name, String email, String gender, String comment) {

            profile = new AppSettings(
                    cursor.getInt(cursor.getColumnIndex(DbContractSettings.ID)),
                    cursor.getString(cursor.getColumnIndex(DbContractSettings.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(DbContractSettings.COLUMN_NAME_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(DbContractSettings.COLUMN_NAME_GENDER)),
                    cursor.getString(cursor.getColumnIndex(DbContractSettings.COLUMN_NAME_COMMENT)));

            continue;
        }

        cursor.close();
        db.close();

        return profile;
    }

    /**
     * Save Profile
     */
    public void saveSettings(AppSettings profile) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbContractSettings.COLUMN_NAME_NAME, profile.getName());
        values.put(DbContractSettings.COLUMN_NAME_EMAIL, profile.getEmail());
        values.put(DbContractSettings.COLUMN_NAME_GENDER, profile.getGender());
        values.put(DbContractSettings.COLUMN_NAME_COMMENT, profile.getComment());

        String where = "id = ?";
        String[] whereArgs = new String[] {String.valueOf(profile.getId()) };

        // Edit the row, returning the primary key value of the new row
        db.update(DbContractSettings.TABLE_NAME, values, where, whereArgs);
        db.close();
    }
}
