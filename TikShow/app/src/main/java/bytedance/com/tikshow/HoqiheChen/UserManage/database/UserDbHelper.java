package bytedance.com.tikshow.HoqiheChen.UserManage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static bytedance.com.tikshow.HoqiheChen.UserManage.database.UserContract.SQL_CREATE_ENTRIES;

public class UserDbHelper extends SQLiteOpenHelper {

    public static final int DATEBASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";

    public UserDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
