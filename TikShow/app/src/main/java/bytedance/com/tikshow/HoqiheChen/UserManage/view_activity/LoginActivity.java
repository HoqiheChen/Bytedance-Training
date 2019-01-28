package bytedance.com.tikshow.HoqiheChen.UserManage.view_activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bytedance.com.tikshow.HoqiheChen.UserManage.database.UserContract;
import bytedance.com.tikshow.HoqiheChen.UserManage.database.UserDbHelper;
import bytedance.com.tikshow.HoqiheChen.UserManage.model.User;
import bytedance.com.tikshow.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEdit;
    private EditText passWordEdit;
    private Button button;
    private TextView textView;
    private UserDbHelper userDbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDbHelper = new UserDbHelper(this);
        database = userDbHelper.getReadableDatabase();
        userNameEdit = findViewById(R.id.user_name);
        passWordEdit = findViewById(R.id.user_password);
        button = findViewById(R.id.submit);
        textView = findViewById(R.id.text);

        String userName = userNameEdit.getText().toString();
        String passWord = passWordEdit.getText().toString();
        database.execSQL("INSERT INTO " + UserContract.UserEntry.TABLE_NAME
                        + " (" + UserContract.UserEntry.COLUMN_NAME_USERNAME + "," + UserContract.UserEntry.COLUMN_NAME_PASSWORD
                + " ) VALUES(" + "123456,123456" + ")");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit("123456","123456");
            }
        });

    }

    public boolean  submit(String userName,String passWord) {
        if (database == null) {
            return false;
        }
        List<User> users = new LinkedList<>();
        Cursor cursor = null;

        String args[] = {userName, passWord};
        cursor = database.query("User.db", new String[]{"username", "password"}, "username=? AND password=?", args, null, null, null, null);
        while (cursor.moveToNext()){
            if (cursor != null) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

}
