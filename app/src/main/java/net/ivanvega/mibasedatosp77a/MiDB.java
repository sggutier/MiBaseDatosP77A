package net.ivanvega.mibasedatosp77a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class MiDB extends SQLiteOpenHelper {


    public static final String[] COLUMNS_NAME_CONTACTO =
            {
             "_id", "usuario", "email", "tel", "fecNacimiento"
            };

    public  static final String TABLE_NAME_CONTACTOS =
            "Contactos";

    public MiDB(@Nullable Context context) {
        super(context,
                "mibaseD",
                null,
                1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SCRIPT_DB = "create table Contactos (" +
                "_id integer primary key autoincrement," +
                "usuario text not null," +
                "email text not null," +
                "tel text not null," +
                "fecNacimiento date);";
        sqLiteDatabase.execSQL(SCRIPT_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
