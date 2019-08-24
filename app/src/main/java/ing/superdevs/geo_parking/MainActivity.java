package ing.superdevs.geo_parking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText EtNombreComp, EtCedula, EtTelefono, EtNroPosPArqueadero;
    Button BtReservar;
    TextView TLlenado;
    String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        try {

            String Datos = getIntent().getExtras().getString("Lectura", "---");

            if (!Datos.equals("---")) {
                sacarTexto(Datos);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void sacarTexto(String texto) {


        String pausa = "*";

        StringTokenizer token = new StringTokenizer(texto, pausa);

        String Nombre = token.nextToken();
        token.nextToken();
        String Cedula = token.nextToken();
        token.nextToken();
        String Telefono = token.nextToken();
        token.nextToken();
        String NroPosPArqueadero = token.nextToken();

        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
        EtNombreComp.setText(Nombre);
        this.EtCedula.setText(Cedula);
        this.EtTelefono.setText(Telefono);
        this.EtNroPosPArqueadero.setText(NroPosPArqueadero);

    }

    public void Reservar(View view) {

        Usuario usuario = new Usuario(EtNombreComp.getText().toString(), EtCedula.getText().toString(), EtTelefono.getText().toString(), EtNroPosPArqueadero.getText().toString());

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, ("De los permisos correspondientes"), Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        } else {//si los permisos estan dados que pase a generar el QR

            try {

                Intent intent = new Intent(this, VistaCodigoQR.class);

                intent.putExtra("CadenaQR", usuario.toString());
                intent.putExtra("Nombre", usuario.getEtNombreComp());
                intent.putExtra("Celular", usuario.getEtTelefono());
                intent.putExtra("Cedula", usuario.getEtCedula());


                startActivity(intent);

                limpiar();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void limpiar() {
        this.EtNombreComp.setText("");
        this.EtCedula.setText("");
        this.EtTelefono.setText("");
        this.EtNroPosPArqueadero.setText("");
    }

    public void Llenar(View v) {
        this.EtNombreComp.setText("Daniela Delgado Causil");
        this.EtCedula.setText("107318745");
        this.EtTelefono.setText("3143787821");
        this.EtNroPosPArqueadero.setText("A-101");
    }

    private void inicializar() {
        TLlenado = findViewById(R.id.TLlenado);
        EtNombreComp = findViewById(R.id.EtNombreComp);
        EtCedula = findViewById(R.id.EtCedula);
        EtTelefono = findViewById(R.id.EtTelefono);
        EtNroPosPArqueadero = findViewById(R.id.EtNroPosPArqueadero);
        BtReservar = findViewById(R.id.BtReservar);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA
        }, 1);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 2);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            Intent intent = getIntent();
            String Datos = intent.getExtras().getString("Lectura", "---");
            Toast.makeText(this, Datos, Toast.LENGTH_SHORT).show();

            if (!Datos.equals("---")) {
                sacarTexto(Datos);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.escanear:
                Intent intent = new Intent(getApplicationContext(), EscanerActivity.class);
                startActivity(intent);
                break;


        }


        return super.onOptionsItemSelected(item);
    }

}
