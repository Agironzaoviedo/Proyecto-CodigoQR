package ing.superdevs.geo_parking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VistaCodigoQR extends AppCompatActivity {

    TextView Nombre;
    ImageView Imagen;
    Button BCompartir,BCompartirCon;
    String nombre,celular,CadenaQR,Cedula;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_codigo_qr);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Nombre=findViewById(R.id.Vnombre);
        Imagen =findViewById(R.id.VimagenQR);
        BCompartir=findViewById(R.id.VbtnCompartir);
        BCompartirCon=findViewById(R.id.VbtnCompartirCon);

        File nuevaCarpeta = new File (Environment.getExternalStorageDirectory(), "QRGeoParking");
        nuevaCarpeta.mkdirs();

        try{

            Intent intent =getIntent();
            Bundle ext= intent.getExtras();

            nombre=ext.getString("Nombre","---");
            celular=ext.getString("Celular","---");
            CadenaQR=ext.getString("CadenaQR","---");
            Cedula=ext.getString("Cedula","---");


            MultiFormatWriter multiFormatWriter=new MultiFormatWriter();

            BitMatrix bitMatrix=multiFormatWriter.encode(CadenaQR, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            bitmap=barcodeEncoder.createBitmap(bitMatrix);

            if(!CadenaQR.equals("---")) {
                BCompartirCon.setText(BCompartirCon.getText() + "" + celular);
                Nombre.setText(nombre);
                Imagen.setImageBitmap(bitmap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        BCompartirCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri uri=Uri.parse("https://wa.me/57"+celular+"?text=Hola "+nombre +" Te enviaremos tu *Tarjeta QR de GeoParking*, con este codigo tienes reservado tu cupo en el parqueadero");
                Intent IntentNav= new Intent(Intent.ACTION_VIEW,uri);
                startActivity(IntentNav);

                Compartir_imagen(uri);

            }

        });

    }

    private void Compartir_imagen(Uri uriComp) {

        //Se carga la imagen que se quiere compartir
        //Se guarda la imagen en la SDCARD
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File( Environment.getExternalStorageDirectory()+"/QRGeoParking/QR_"+Cedula+"QRGeoParking.jpg");

        try {

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            Toast.makeText(this, "Fallé--------", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", e.getMessage() );
        }
        //compartir imagen
        Intent share = new Intent(Intent.ACTION_SEND,uriComp);
        share.setPackage("com.whatsapp");
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        share.putExtra(Intent.EXTRA_TEXT, ""+nombre+", Celular: "+celular);
        startActivity(Intent.createChooser(share, "Compartir imagen QR"));
        //   startActivity(share);
    }

    public void compartir(View v){

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/QRGeoParking/QR_"+Cedula+"QRGeoParking.jpg");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File f = new File(Environment.getExternalStorageDirectory() + "/QR Generated/QRGeoParking.jpg");

            //compartir imagen
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            share.putExtra(Intent.EXTRA_TEXT, "" + nombre + ", Celular: " + celular);
            startActivity(Intent.createChooser(share, "Compartir Tarjeta QRGeoParking con:"));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Debe crear la imagen primero", Toast.LENGTH_SHORT).show();
        }
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
