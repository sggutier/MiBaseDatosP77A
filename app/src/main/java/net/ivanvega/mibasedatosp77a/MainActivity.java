package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private SimpleCursorAdapter adp;
    DAOContactos dao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAOContactos(this);

        lv = findViewById(R.id.lv);

        recargaAdaptador();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                          Context context = view.getContext();
                                          Cursor cursor = (Cursor) lv.getItemAtPosition(pos);
                                          Intent intent = new Intent(context, ContactoActivity.class);
                                          intent.putExtra("Creacion", dao.inflaCursor(cursor));
                                          ((AppCompatActivity) context).startActivityForResult(intent, pos+1);
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
        return super.onCreateOptionsMenu(menu);
    }

    private void recargaAdaptador() {
        adp = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                dao.getAllCursor(),
                new String[]{"usuario","email"},
                new int[]{android.R.id.text1, android.R.id.text2
                },
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

        );
        lv.setAdapter(adp);
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
                    dao.insert(con);
                }
                else {
                    // TODO: Añadir codigo para hacer actualizaciones
                    con.setId(requestCode-1);
                    dao.insert(con);
                }
            }
            else {
                Log.e("Regreso", "whoops, no pude obtener algo del contacto");
            }
        }
        recargaAdaptador();
    }
}
