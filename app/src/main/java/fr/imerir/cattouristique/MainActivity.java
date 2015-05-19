package fr.imerir.cattouristique;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    AQuery aQueryObject;
    ArrayList<Etablissement> listEtablissements;
    //Etablissement etablissement;

    /**
     * FONCTION GET DATA FROM JSON: METHODE 1
     */
    public void asyncJson(){
        String url = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(url, JSONObject.class, this, "jsonCallback");
    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status){
        if(json != null){
            //successful ajax call
            JSONArray jsonArray = json.optJSONArray("etablissements");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                    String typeJSON = jsonArray.getJSONObject(i).getString("type");
                    String photoJSON = jsonArray.getJSONObject(i).getString("photo");

                    double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                    double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                    //etablissement = new Etablissement(nameJSON, typeJSON, adresseJSON, photoJSON, latitudeJSON, longitudeJSON);

                    //listEtablissements.add(i, etablissement);
                    listEtablissements.add(new Etablissement(nameJSON, typeJSON, adresseJSON, photoJSON, latitudeJSON, longitudeJSON));

                    //Log.i("OBJET ETABLISSEMENT: ", "" + etablissement);
                    Log.i("OBJET ETABLISSEMENT: ", "" + listEtablissements.get(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //ajax error
            Log.i("ELEMENT RECUS -", "RIEN RECU");
        }
    }


    /**
     * FONCTION GET DATA FROM JSON: METHODE 2
     */
    public void getDataFromJSON() {
        String url = "http://perso.imerir.com/cboyer/etablissements.json";

        aQueryObject.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if(json != null){
                    JSONArray jsonArray = json.optJSONArray("etablissements");
                     String name = "";

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            String nameJSON = jsonArray.getJSONObject(i).getString("name");
                            String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                            String typeJSON = jsonArray.getJSONObject(i).getString("type");
                            String photoJSON = jsonArray.getJSONObject(i).getString("photo");

                            double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                            double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                            //etablissement = new Etablissement(nameJSON, typeJSON, adresseJSON, photoJSON, latitudeJSON, longitudeJSON);
                            //listEtablissements.add(etablissement);

                            listEtablissements.get(i).toString();
                            //Log.i("OBJET ETABLISSEMENT: ", "" + etablissement);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else
                    Log.i("ELEMENT RECUS -", "RIEN RECU");
            }
        });
    }


    public void onResume() {
        super.onResume();

        //getDataFromJSON();
        asyncJson();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aQueryObject = new AQuery(this);
        listEtablissements = new ArrayList<>();
        //getDataFromJSON();
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
