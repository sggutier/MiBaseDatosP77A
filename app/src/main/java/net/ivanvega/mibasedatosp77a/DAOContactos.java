package net.ivanvega.mibasedatosp77a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

class DAOContactos {
    final SQLiteDatabase _sqLiteDatabase;
    final Context ctx;

    public DAOContactos(Context ctx) {
        this.ctx = ctx;
        _sqLiteDatabase =
                new MiDB(ctx).getWritableDatabase();
    }

    private ContentValues createContentValues(Contacto contacto) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[1],
                contacto.getUsuario());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[2],
                contacto.getEmail());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[3],
                contacto.getTel());
        Date fecNac = contacto.getFecNac();
        if(fecNac != null) {
            contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[4], fecNac.toString());
        }

        return contentValues;
    }

    public void insert(Contacto contacto){
        _sqLiteDatabase.insert(MiDB.TABLE_NAME_CONTACTOS,
                null, createContentValues(contacto));
    }

    public void delete(final int id) {
        _sqLiteDatabase.delete(MiDB.TABLE_NAME_CONTACTOS, MiDB.COLUMNS_NAME_CONTACTO[0] + "=" + id, null);
    }

    public void update(final Contacto contacto) {
        _sqLiteDatabase.update(MiDB.TABLE_NAME_CONTACTOS, createContentValues(contacto), MiDB.COLUMNS_NAME_CONTACTO[0] + "=" + contacto.getId(), null);
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

    public List<Contacto> getAll (){
        List<Contacto> lst = null;

        Cursor c = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                null,
                null,
                null,
                null,
                null,
                null);

        if (c.moveToFirst() ){
           lst = new ArrayList<>();
           do {
              Contacto contacto = inflaCursor(c);
              lst.add(contacto);

           }while(c.moveToNext());
        }
        c.close();

        return  lst;

    }

    public Cursor getAllCursor (){
        return _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllByUsuario(String criterio){
        if(criterio.isEmpty())
            return getAllCursor();
        return _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                "usuario like '%" + criterio + "%'",
                null,
                null,
                null,null
        );
    }

}
