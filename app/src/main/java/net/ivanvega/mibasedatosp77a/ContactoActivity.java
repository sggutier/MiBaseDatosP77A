package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

public class ContactoActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextEmail, editTextTelefono, editTextTwitter, editTextFechaNac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextFechaNac = findViewById(R.id.editTextFechaNac);
        Intent intent = getIntent();
        Contacto cont = intent.getParcelableExtra("Creacion");
        if(cont != null) {
            editTextUsuario.setText(cont.getUsuario());
            editTextEmail.setText(cont.getEmail());
            editTextTelefono.setText(cont.getTel());
            Date fecNac = cont.getFecNac();
            if(fecNac != null)
                editTextFechaNac.setText(fecNac.toString());
        }
    }

    public void calenClick(View view) {
        if (view.getId() == R.id.editTextFechaNac) {
            Calendar c = Calendar.getInstance();
            final EditText eto = (EditText) view;
            DatePickerDialog mdiDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String sf = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear, dayOfMonth);
                    eto.setText(sf);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            mdiDialog.show();
        }
    }

    public void botono(View v) {
        Log.e("Regreso", "2 got pressed");
        Intent retVal = new Intent();
        Contacto con = new Contacto(
                editTextUsuario.getText().toString(),
                editTextEmail.getText().toString(),
                editTextTelefono.getText().toString());
        try {
            Date fecNac = Date.valueOf(editTextFechaNac.getText().toString());
            con.setFecNac(fecNac);
        }
        catch (Exception e) {
            Log.e("Regreso", "algo esta mal, seguramente la fecha");
        }
        Log.e("Regreso", con.getUsuario() + " " + con.getEmail() + " " + con.getTel() + " " + (con.getFecNac()==null? "" : con.getFecNac().toString()));
        retVal.putExtra("Contacto", con);
        Log.e("Regreso", "Regresando OK");
        setResult(RESULT_OK, retVal);
        finish();
    }
}
