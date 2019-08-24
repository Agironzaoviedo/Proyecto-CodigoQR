package ing.superdevs.geo_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static ing.superdevs.geo_parking.MainActivity.Texto;

public class EscanerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }


    @Override
    public void handleResult(Result result) {

        final Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Lectura", result.getText());
        Texto=result.getText();
        // vanderas para que cuando pase a la informacion y dé atras no vuelva al scan
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if(!result.getText().equals("---")) {
            MediaPlayer Sound;
            Sound=MediaPlayer.create(this,R.raw.aprobado);
            Sound.start();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Tarjeta Leída");
            dialog.setMessage(result.getText());
            dialog.setCancelable(false);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(i);
                        }
                    }
            );
            dialog.show();
        }

        //finish();
    }

}
