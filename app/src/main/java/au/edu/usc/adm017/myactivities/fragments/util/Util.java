package au.edu.usc.adm017.myactivities.fragments.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import au.edu.usc.adm017.myactivities.ViewActivities;

public class Util {

    public static final int CAMERA_REQUEST = 1888;

    private static Location location;
    private static LocationListener listener;

    public static double[] getCoordinates() {

        if (listener == null) {
            listener = new ActivityLocationListener();
        }

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return new double[]{latitude, longitude};
        }

        return new double[]{0, 0};
    }

    /**
     * Gets the list of strings and represents it as a string array.
     *
     * @param list the list of strings
     * @return the array of strings.
     */
    private static String[] toArray(List<String> list) {
        String[] newArray = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            newArray[i] = list.get(i);
        }

        return newArray;
    }

    /**
     * Gets the date formatter for this app.
     *
     * @return the date formatter
     */
    public static DateFormat getDateFormatter() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM, yyyy");
        return format;
    }

    /**
     * Gets the current formatted date as string.
     *
     * @return the date as string
     */
    public static String getDateAsString() {

        try {
            Date date = new Date();
            return getDateFormatter().format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts a base64 string to bitmap file
     *
     * @param base64Str base64 string
     * @return bitmap
     */
    public static Bitmap convert(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str.substring(base64Str.indexOf(",")  + 1), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * Converts a bitmap file to base64 string
     *
     * @param bitmap the bitmap
     * @return bitmap
     */
    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    /**
     * Shows a toast (small text message) at the bottom of the screen.
     *
     * @param text the toast message
     */
    public static void showToast(String text) {
        Context context = ViewActivities.getInstance().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        Util.location = location;
    }

    public static LocationListener getListener() {
        return listener;
    }

    public static void setListener(LocationListener listener) {
        Util.listener = listener;
    }
}
