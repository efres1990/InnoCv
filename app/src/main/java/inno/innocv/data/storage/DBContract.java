package inno.innocv.data.storage;

import android.provider.BaseColumns;

/**
 * @author eladiofreire
 */

public class DBContract {
    private static final String COMMA_SEP = ", ";

    public static abstract class TableUsers implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_BIRTHDATE = "birthdate";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                        COLUMN_NAME + " TEXT" + COMMA_SEP +
                        COLUMN_BIRTHDATE + " TEXT" + ")";
    }
}
