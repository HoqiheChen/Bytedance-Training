package bytedance.com.tikshow.HoqiheChen.UserManage.database;

import android.provider.BaseColumns;

public final class UserContract {
    private UserContract() {
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String  COLUMN_NAME_USERNAME = "username";
        public static final String  COLUMN_NAME_PASSWORD = "password";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + UserEntry.TABLE_NAME
            + " (" + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + UserEntry.COLUMN_NAME_USERNAME
            + " TEXT," + UserEntry.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

}
