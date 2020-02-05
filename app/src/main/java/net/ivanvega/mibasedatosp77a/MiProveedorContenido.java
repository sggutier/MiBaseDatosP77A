package net.ivanvega.mibasedatosp77a;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MiProveedorContenido extends ContentProvider {
    SQLiteDatabase  _sqliteDB ;
    Context _ctx;


    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        uriMatcher.addURI("net.ivanvega.mibasedatosp77a.provider", "contactos", 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://net.ivanvega.mibasedatosp77a.provider/contactos doesn't.
         */
        uriMatcher.addURI("net.ivanvega.mibasedatosp77a.provider", "contactos/#", 2);

        //
        uriMatcher.addURI("net.ivanvega.mibasedatosp77a.provider", "contactos/*", 3);
    }


    @Override
    public boolean onCreate() {

        _sqliteDB = new MiDB(this.getContext()).getWritableDatabase();

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,

                        @Nullable String selection,
                        @Nullable String[] selectionargs,
                        @Nullable String orderby) {

        Cursor c  = null;


        switch (uriMatcher.match(uri)){
            case 1:

                c = _sqliteDB.query(MiDB.TABLE_NAME_CONTACTOS,
                        projection,
                        null,
                        null,
                        null,
                        null, null );
                break;

            case 2:



                c =_sqliteDB.query(MiDB.TABLE_NAME_CONTACTOS ,
                        projection,
                        MiDB.COLUMNS_NAME_CONTACTO[0] + "=?",
                        new String[]{uri.getLastPathSegment()},
                        null,null,null
                        );
                break;
        }


        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        String resul = "";

        switch (uriMatcher.match(uri)){
            case 1:
                resul = "vnd.android.cursor.dir/vnd." +
                        "net.ivanvega.mibasedatosp77a.provider.contactos";
                break;

            case 2:
                resul = "vnd.android.cursor.item/vnd. " +
                        "net.ivanvega.mibasedatosp77a.provider.contactos";
                break;
        }




        return resul;



    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues contentValues) {



        Long id  = null;


        switch (uriMatcher.match(uri)){
            case 1:

                id = _sqliteDB.insert(MiDB.TABLE_NAME_CONTACTOS,
                        null, contentValues);
                break;
        }

        return    Uri.parse(uri.toString()
                + "/" +
                String.valueOf(id)) ;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
