package com.example.conversor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Conversor.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "user";

    public SQLHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("oi", "SQLHelper: oi");
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query = "CREATE TABLE " + TABLE_NAME + " (nome TEXT primary key, senha TEXT);";

        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists user;");
        onCreate(DB);
    }

    public Boolean insertUserData(String nome, String senha){
        SQLiteDatabase banco = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", nome);
        values.put("senha", senha);

        long result = banco.insert("user", null, values);

        if (result == -1){
            return false;
        }
        return true;
    }

    public Boolean updateUserData(String nome, String senha){
        SQLiteDatabase banco = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("senha", senha);

        Cursor cursor = banco.rawQuery("Select * from user where nome = ?", new String[]{nome});

        if (cursor.getCount() > 0){
            long result = banco.update("user", values, "nome=?", new String[] {nome});

            if (result == -1){
                return false;
            }
            return true;
        }

        return false;
    }

    public Cursor getData(){
        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery("Select * from user", null);

        return cursor;
    }

    public Cursor findUser(String nome){
        Log.d("find user", nome);
        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery("Select senha from user where nome = ?", new String[] {nome});

        return cursor;
    }
}
