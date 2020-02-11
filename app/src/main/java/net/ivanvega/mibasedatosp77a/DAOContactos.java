package net.ivanvega.mibasedatosp77a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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

    public Long insert(@Nullable ContentValues contentValues) {
        return _sqLiteDatabase.insert(MiDB.TABLE_NAME_CONTACTOS,
                null, contentValues);
    }

    public Long insert(Contacto contacto){
        return this.insert(createContentValues(contacto));
    }

    public int deleteAll() {
        return _sqLiteDatabase.delete(MiDB.TABLE_NAME_CONTACTOS, null, null);
    }

    public int delete(final int id) {
        return _sqLiteDatabase.delete(MiDB.TABLE_NAME_CONTACTOS, MiDB.COLUMNS_NAME_CONTACTO[0] + "=" + id, null);
    }

    public int update(final ContentValues valr, final int id) {
        return _sqLiteDatabase.update(MiDB.TABLE_NAME_CONTACTOS, valr, MiDB.COLUMNS_NAME_CONTACTO[0] + "=" + id, null);
    }

    public int update(final Contacto contacto) {
        return this.update(createContentValues(contacto), contacto.getId());
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

    public Cursor getAllById(String id){
        if(id.isEmpty())
            return getAllCursor();
        return _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                MiDB.COLUMNS_NAME_CONTACTO[1] + "=?",
                new String[] {id},
                null,
                null,null
        );
    }

    public Cursor getAllByUsuario(String criterio){
        if(criterio.isEmpty())
            return getAllCursor();
        return _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                MiDB.COLUMNS_NAME_CONTACTO[1] + " like '%" + criterio + "%'",
                null,
                null,
                null,null
        );
    }

}
