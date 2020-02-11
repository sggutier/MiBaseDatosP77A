package net.ivanvega.consoomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private SimpleCursorAdapter adp;
    NeoDAO mNeoDAO;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNeoDAO = new NeoDAO(this);

        lv = findViewById(R.id.lv);

        recargaAdaptador();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                          Context context = view.getContext();
                                          Cursor cursor = (Cursor) lv.getItemAtPosition(pos);
                                          Intent intent = new Intent(context, ContactoActivity.class);
                                          Contacto con = mNeoDAO.inflaCursor(cursor);
                                          intent.putExtra("Creacion", con);
                                          ((AppCompatActivity) context).startActivityForResult(intent, con.getId() + 1);
                                      }
                                  });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                final Contacto contacto = mNeoDAO.inflaCursor((Cursor) lv.getItemAtPosition(pos));
                Snackbar.make(view, "¿Estás seguro de borrar a " + contacto.getUsuario() + "?", Snackbar.LENGTH_LONG)
                    .setAction("Si", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mNeoDAO.delete(contacto.getId());
                            recargaAdaptador();
                        }
                    }).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_selector, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(android.R.attr.width);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(String query) {
                        recargaAdaptadorConCursor(mNeoDAO.getAllByUsuario(query));
                        return false;
                    }
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    private void recargaAdaptadorConCursor(Cursor c) {
        adp = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                c,
                new String[]{"usuario","email"},
                new int[]{android.R.id.text1, android.R.id.text2
                },
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

        );
        lv.setAdapter(adp);
    }

    private void recargaAdaptador() {
        recargaAdaptadorConCursor(mNeoDAO.getAllCursor());
    }

    public void anadeUsuario(View view) {
        Intent intent = new Intent(this, ContactoActivity.class);
        startActivityForResult(intent, 0);
        Log.e("Regreso", "Envio");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Regreso", "Saludos desde actividad 1 con requestCode " + requestCode);
        boolean val = resultCode == RESULT_OK;
        Log.e("Regreso", val? "Chido" : "No chido");
        if (val) {
            Contacto con = data.getParcelableExtra("Contacto");
            if(con != null) {
                Log.e("Regreso", "boogaloo " + con.getUsuario() + " " + con.getEmail() + " " + con.getTel() + " " + (con.getFecNac()==null? "" : con.getFecNac().toString()));
                if(requestCode == 0) {
                    mNeoDAO.insert(con);
                }
                else {
                    con.setId(requestCode-1);
                    mNeoDAO.update(con);
                }
            }
            else {
                Log.e("Regreso", "whoops, no pude obtener algo del contacto");
            }
        }
        recargaAdaptador();
    }
}
