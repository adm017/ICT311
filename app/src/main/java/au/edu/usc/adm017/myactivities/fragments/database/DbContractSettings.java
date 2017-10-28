package au.edu.usc.adm017.myactivities.fragments.database;

public final class DbContractSettings {

    public static final String TABLE_NAME = "settings";
    public static final String ID = "id";
    public static final String COLUMN_NAME_NAME= "name";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_GENDER = "gender";
    public static final String COLUMN_NAME_COMMENT = "comment";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_NAME + " TEXT," +
            COLUMN_NAME_EMAIL + " TEXT," +
            COLUMN_NAME_GENDER + " TEXT," +
            COLUMN_NAME_COMMENT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}