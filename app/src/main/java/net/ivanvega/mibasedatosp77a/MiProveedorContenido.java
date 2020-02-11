package net.ivanvega.mibasedatosp77a;

import android.app.IntentService;
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
    DAOContactos mDAOContactos;

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
        mDAOContactos = new DAOContactos(this.getContext());
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
                c = mDAOContactos.getAllCursor();
                break;
            case 2:
                c = mDAOContactos.getAllById(uri.getLastPathSegment());
                break;
            case 3:
                c = mDAOContactos.getAllByUsuario(uri.getLastPathSegment());
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
                resul = "vnd.android.cursor.item/vnd." +
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
            case 2:
            case 3:
                id = mDAOContactos.insert(contentValues);
                break;
        }
        return Uri.parse(uri.toString()
                + "/" +
                String.valueOf(id)) ;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int delS = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
            case 2:
            case 3:
                delS = mDAOContactos.delete(Integer.parseInt(s));
                break;
        }
        return delS;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String idS, @Nullable String[] strings) {
        int updS = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
                updS = mDAOContactos.update(contentValues, Integer.parseInt(idS));
                break;
            case 2:
            case 3:
                updS = mDAOContactos.update(contentValues, Integer.parseInt(uri.getLastPathSegment()));
                break;
        }
        return updS;
    }
}
