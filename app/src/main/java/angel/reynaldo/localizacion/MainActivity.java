package angel.reynaldo.localizacion;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends Activity {
    //Declaramos los atributos que se utilizaran

    private Button btnactualizar, btndesactivar;
    private TextView lbllatitud, lbllongitud, lblprecision, lblestadoproveedor;

    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //enlazamos la parte grafica con el codigo
        btnactualizar = (Button) findViewById(R.id.BtnActualizar);
        btndesactivar = (Button) findViewById(R.id.BtnDesactivar);
        lbllatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lbllongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblprecision = (TextView) findViewById(R.id.LblPosPrecision);
        lblestadoproveedor = (TextView) findViewById(R.id.LblEstado);

        //Se declara el metodo para escuchar cuando se presiona el boton
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });

       btndesactivar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               locManager.removeUpdates(locListener);
           }
       });
    }



    private void mostrarPosicion (Location loc) {

        if (loc!=null){
            lbllatitud.setText("Latitud" + String.valueOf(loc.getLatitude()));
            lbllongitud.setText("Longitud" + String.valueOf(loc.getLongitude()));
            lblprecision.setText("Precision" + String.valueOf(loc.getAccuracy()));
            //Log.i("", String.valueOf(loc.get)));
            } else {

            lbllatitud.setText("Latitud: (sin_datos)");
            lbllongitud.setText("Longitud: (sin_datos)");
            lblprecision.setText("Precision: (sin_datos)");

        }
    }

    private void comenzarLocalizacion(){
        //obtenemos una referencia al locationmanager
        locManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //mostramos la ultima posicion conocida
        mostrarPosicion(loc);

        //nos registramos para recibir atualizaciones de la posicion
        locListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                lblestadoproveedor.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider) {
                lblestadoproveedor.setText("Provider ON");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

                //Log.i("", "Provider Status: " + status);
                lblestadoproveedor.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
              LocationManager.GPS_PROVIDER, 30000, 0, locListener  );
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
