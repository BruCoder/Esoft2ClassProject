package pt.ipleiria.estg.dei.ei.es2.bio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import modelo.Contacto;


public class MainActivity extends Activity {

    private static final int FILLCONTACTDATA = 1 ;
    private boolean hasCheckedIn=false;

    private Contacto myContacto;

    private MenuItem item;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContacto = new Contacto("Bruno", "bru@gmail.com");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        item = menu.findItem(R.id.action_checkin);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_checkin) {
            checkin();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkin() {

        if (!hasCheckedIn) {

            if (hasContactData()) {

                AlertDialog.Builder alerterBuilder = new AlertDialog.Builder(this);


                alerterBuilder.setTitle(getString(R.string.titleConfirmation));
                alerterBuilder.setMessage(getString(R.string.shareConfirmation) + myContacto);

                alerterBuilder.setPositiveButton(getString(R.string.okBtn), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //            se sim
                        if (hasNetwork()) {


                            hasCheckedIn = true;

                            item.setIcon(R.drawable.ic_action_name2);

                            Intent intent = new Intent(getApplicationContext(), ParticipantsListActivity.class);
                            intent.putExtra(ParticipantsListActivity.HASCHECKEDIN, hasCheckedIn);
                            startActivity(intent);

                        } else {

//                            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
//                                apresenta dialogo em vez do toast

                            presentNoNetworkDialog();


                        }

                    }
                });

                alerterBuilder.setNegativeButton("Não", null);
                alerterBuilder.show();


            } else {
//                Toast.makeText(getApplicationContext(), "No contact data", Toast.LENGTH_LONG).show();
                    presentNoDataDialog();
            }


        } else {

            item.setIcon(R.drawable.ic_action_name);
            hasCheckedIn = false;

        }



    }

    public void presentNoNetworkDialog(){

            AlertDialog.Builder alerterBuilder = new AlertDialog.Builder(this);

            alerterBuilder.setTitle("Sem rede");
            alerterBuilder.setMessage("Aviso, não tem rede. Tente mais tarde");
            alerterBuilder.setPositiveButton(getString(R.string.confirmBtn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



                }
            });
            alerterBuilder.show();


    }

    public void presentNoDataDialog(){

        AlertDialog.Builder alerterBuilder = new AlertDialog.Builder(this);

        alerterBuilder.setTitle("Sem dados");
        alerterBuilder.setMessage("Aviso, não tem dados para partilhar. Tente mais tarde");
        alerterBuilder.setPositiveButton(getString(R.string.confirmBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intent, FILLCONTACTDATA);

            }
        });
        alerterBuilder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == FILLCONTACTDATA && resultCode== RESULT_OK){

            if (hasContactData()) {

                AlertDialog.Builder alerterBuilder = new AlertDialog.Builder(this);


                alerterBuilder.setTitle(getString(R.string.titleConfirmation));
                alerterBuilder.setMessage(getString(R.string.shareConfirmation) + myContacto);

                alerterBuilder.setPositiveButton(getString(R.string.okBtn), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //            se sim
                        if (hasNetwork()) {


                            hasCheckedIn = true;

                            item.setIcon(R.drawable.ic_action_name2);

                            Intent intent = new Intent(getApplicationContext(), ParticipantsListActivity.class);
                            intent.putExtra(ParticipantsListActivity.HASCHECKEDIN, hasCheckedIn);
                            startActivity(intent);

                        } else {

//                            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
//                                apresenta dialogo em vez do toast

                            presentNoNetworkDialog();


                        }

                    }
                });

                alerterBuilder.setNegativeButton("Não", null);
                alerterBuilder.show();


            }


        }

    }

    private boolean hasNetwork() {


        //      Context context = getApplicationContext();
//
//        ConnectivityManager cm =
//                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
//
//
//        return isConnected;

//        alternativa prof

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }

    private boolean hasContactData() {

        String nome = prefs.getString("NAME", "");
        String email = prefs.getString("EMAIL", "");

        if(!nome.isEmpty() && !email.isEmpty()) {
            myContacto = new Contacto(nome, email);
        } else{

            myContacto=null;
        }


        return myContacto != null ? true : false;
    }




}
