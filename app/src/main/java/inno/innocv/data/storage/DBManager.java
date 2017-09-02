package inno.innocv.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import inno.innocv.data.model.UserInfoValue;

import java.util.ArrayList;

/**
 * @author eladiofreire
 */

public class DBManager {
    private static SQLiteDatabase sDB;
    private static DBManager sInstance;

    private DBManager(Context context) {
        sDB = new DBHelper(context).getWritableDatabase();
    }

    public static DBManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBManager(context);
        }
        return sInstance;
    }

    public void clearWholeTables() {
        deleteUsers(null, null);
    }

    //USERS
    private Cursor queryUsers(String selection, String[] selectionArgs) {
        Cursor cursor = sDB.query(
                DBContract.TableUsers.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    private void insertUsers(UserInfoValue data) {
        ContentValues values = new ContentValues();
        values.put(DBContract.TableUsers.COLUMN_ID, data.getId());
        values.put(DBContract.TableUsers.COLUMN_BIRTHDATE, data.getId());
        values.put(DBContract.TableUsers.COLUMN_NAME, data.getName());
        sDB.insert(DBContract.TableUsers.TABLE_NAME, null, values);

    }

    private boolean deleteUsers(String selection, String[] selectionArgs) {
        return sDB.delete(DBContract.TableUsers.TABLE_NAME, selection, selectionArgs) > 0;
    }

    public ArrayList<UserInfoValue> getUserList() {
        ArrayList<UserInfoValue> result = new ArrayList<>();
        Cursor cursor = queryUsers(null, null);

        if (cursor.moveToFirst()) {
            UserInfoValue userInfoValue;
            do {
                userInfoValue = new UserInfoValue();
                userInfoValue.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.TableUsers.COLUMN_ID)));
                userInfoValue.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TableUsers.COLUMN_NAME)));
                userInfoValue.setBrithdate(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TableUsers.COLUMN_BIRTHDATE)));
                result.add(userInfoValue);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public void setUsersList(ArrayList<UserInfoValue> dataList) {
        deleteUsers(null, null);
        for (UserInfoValue user : dataList) {
            insertUsers(user);
        }
    }
}
