package au.edu.usc.adm017.myactivities.fragments.database;

public final class DbContractActivities {

    public static final String TABLE_NAME = "activities";
    public static final String _ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_PLACE = "place";
    public static final String COLUMN_NAME_COMMENTS = "comments";
    public static final String COLUMN_NAME_COORDINATES = "coordinates";
    public static final String COLUMN_NAME_PICTURE = "picture_data";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_TITLE + " TEXT," +
            COLUMN_NAME_DATE + " TEXT," +
            COLUMN_NAME_TYPE + " TEXT," +
            COLUMN_NAME_PLACE + " TEXT," +
            COLUMN_NAME_COMMENTS + " TEXT," +
            COLUMN_NAME_COORDINATES + " TEXT," +
            COLUMN_NAME_PICTURE + " LONGTEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}