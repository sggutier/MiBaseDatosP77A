package net.ivanvega.consoomer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

class ColumnasContacto {
    public static final Uri CONTENT_URI_CONTACTOS = Uri.parse("content://net.ivanvega.mibasedatosp77a.provider/contactos");


    public static final  String [] PROJECTION_CONTACTOS
            = {
            "_id", "usuario", "email", "tel", "fecNacimiento"
    };

    public static final  String FIELD_ID = "_id";
    public static final  String FIELD_USUARIO = "usuario";
    public static final  String FIELD_EMAIL = "email";
}
