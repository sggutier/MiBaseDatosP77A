package net.ivanvega.consoomer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.sql.Date;

public class NeoDAO {
    private Context mContext;

    public NeoDAO(Context context) {
        mContext = context;
    }

    private ContentValues createContentValues(Contacto contacto) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ColumnasContacto.PROJECTION_CONTACTOS[1],
                contacto.getUsuario());
        contentValues.put(ColumnasContacto.PROJECTION_CONTACTOS[2],
                contacto.getEmail());
        contentValues.put(ColumnasContacto.PROJECTION_CONTACTOS[3],
                contacto.getTel());
        Date fecNac = contacto.getFecNac();
        if(fecNac != null) {
            contentValues.put(ColumnasContacto.PROJECTION_CONTACTOS[4], fecNac.toString());
        }

        return contentValues;
    }

    public Contacto inflaCursor(Cursor c) {
        Contacto contacto = new Contacto(c.getInt(0), c.getString(1),
                c.getString(2), c.getString(3));
        String fecha = c.getString(4);
        if(fecha != null) {
            try {
                contacto.setFecNac(Date.valueOf(fecha));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contacto ;
    }
    
    public void delete(int idContacto) {
        mContext.getContentResolver().delete(
                ColumnasContacto.CONTENT_URI_CONTACTOS,
                Integer.toString(idContacto),
                null
        );
    }


    public Cursor getAllByUsuario(String query) {
        Uri msg = Uri.parse(ColumnasContacto.CONTENT_URI_CONTACTOS.toString() + "/" + query);
        Cursor c = mContext.getContentResolver().query(
                msg,   // The content URI of the words table
                ColumnasContacto.PROJECTION_CONTACTOS,    // The columns to return for each row
                null,                             // Selection criteria
                null,                          // Selection criteria
                null);//
        return c;
    }

    public void insert(Contacto con) {
        mContext.getContentResolver().insert(
                ColumnasContacto.CONTENT_URI_CONTACTOS,
                createContentValues(con)
        );
    }

    public void update(Contacto con) {
        mContext.getContentResolver().update(
                ColumnasContacto.CONTENT_URI_CONTACTOS,
                createContentValues(con),
                Integer.toString(con.getId()),
                null
        );
    }

    public Cursor getAllCursor() {
        Cursor c = mContext.getContentResolver().query(
                ColumnasContacto.CONTENT_URI_CONTACTOS,   // The content URI of the words table
                ColumnasContacto.PROJECTION_CONTACTOS,    // The columns to return for each row
                null,                             // Selection criteria
                null,                          // Selection criteria
                null);                            //
        return c;
    }
}
