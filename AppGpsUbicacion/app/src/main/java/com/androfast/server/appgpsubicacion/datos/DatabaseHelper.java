package com.androfast.server.appgpsubicacion.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Server on 05/08/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes para nombres de bases de datos, tabla markers y sus columnas
    public static final String DB_NAME = "seguimiento";
    public static final String TABLE_NAME = " markers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre_usuario";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_MOVIL_USUARIO = "movil_usuario";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";
    public static final String COLUMN_BATERIA = "bateria";
    public static final String COLUMN_STATUS = "status";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creando la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOMBRE +
                " VARCHAR, " + COLUMN_DIRECCION + "VARCHAR" + COLUMN_LAT + "TEXT" +
                COLUMN_LNG + "TEXT" + COLUMN_MOVIL_USUARIO +"VARCHAR"+
                COLUMN_FECHA + "TEXT" + COLUMN_HORA + "TEXT"+ COLUMN_BATERIA + "VARCHAR" +
                COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql);
    }

    // actualización de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS markers";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
     * Este método está tomando dos argumentos
     * el primero es el nombre que se guardará
     * el segundo es el estado
     * 0 significa que el nombre está sincronizado con el servidor
     * 1 significa que el nombre no está sincronizado con el servidor
    * */
    public boolean addName(String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOMBRE, name);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    /*
    * This method taking two arguments
    * first one is the id of the name for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * this method is for getting all the unsynced name
    * so that we can sync it with database
    * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
