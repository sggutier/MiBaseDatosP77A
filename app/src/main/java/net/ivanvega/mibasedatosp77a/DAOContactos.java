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

    public void insert(Contacto contacto){
        ContentValues contentValues
                = new ContentValues();

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

        _sqLiteDatabase.insert(MiDB.TABLE_NAME_CONTACTOS,
                null, contentValues);
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
        return _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                "usuario like %?%",
                new String[]{criterio},
                null,
                null,null

        );
    }

}
