package ing.superdevs.geo_parking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText EtNombreComp,EtCedula,EtTelefono,EtNroPosPArqueadero;
    Button BtReservar;
    TextView TLlenado;
    String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();

    }

    public void Reservar(View view) {

        Usuario usuario=new Usuario(EtNombreComp.getText().toString(),EtCedula.getText().toString(),EtTelefono.getText().toString(),EtNroPosPArqueadero.getText().toString());

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, ("De los permisos correspondientes"), Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        }else {//si los permisos estan dados que pase a generar el QR

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
        TLlenado=findViewById(R.id.TLlenado);
        EtNombreComp=findViewById(R.id.EtNombreComp);
        EtCedula=findViewById(R.id.EtCedula);
        EtTelefono=findViewById(R.id.EtTelefono);
        EtNroPosPArqueadero=findViewById(R.id.EtNroPosPArqueadero);
        BtReservar=findViewById(R.id.BtReservar);
    }

}
