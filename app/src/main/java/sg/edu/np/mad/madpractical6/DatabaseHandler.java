package sg.edu.np.mad.madpractical6;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_FOLLOWED + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
        insertRandomUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    private void insertRandomUsers(SQLiteDatabase db) {
        for (int i = 1; i <= 20; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "User" + i);
            values.put(COLUMN_DESCRIPTION, "Description for User" + i);
            values.put(COLUMN_FOLLOWED, new Random().nextBoolean() ? 1 : 0);
            db.insert(TABLE_USER, null, values);
        }
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FOLLOWED)) == 1
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.getFollowed() ? 1 : 0);

        db.update(TABLE_USER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.id)});
    }
}
