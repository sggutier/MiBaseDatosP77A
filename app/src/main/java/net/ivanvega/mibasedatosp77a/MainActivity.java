package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DAOContactos dao = new DAOContactos(this);
        dao.insert(new Contacto(0, "perronegro",
                "perronegro@","445"));
        dao.insert(new Contacto(0, "perroblanco",
                "perroblanco@","544"));
         for (Contacto c : dao.getAll()){
             Toast.makeText(this,
                     c.usuario,
                     Toast.LENGTH_SHORT).show();
         }

         lv = findViewById(R.id.lv);

        SimpleCursorAdapter adp =
                new SimpleCursorAdapter(
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
}
