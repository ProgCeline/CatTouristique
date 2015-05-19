package fr.imerir.cattouristique.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.imerir.cattouristique.Models.Etablissement;
import fr.imerir.cattouristique.Models.EtablissementsAdapter;
import fr.imerir.cattouristique.R;



public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    AQuery aQueryObject;
    ArrayList<Etablissement> listEtablissements;
    ListView listViewEtablissements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aQueryObject = new AQuery(this);
        listEtablissements = new ArrayList<>();
        listViewEtablissements = (ListView) findViewById(R.id.elementsListView);
        listViewEtablissements.setOnItemClickListener(this);
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

    /**
     * FONCTION GET DATA FROM JSON
     */
    public void asyncJson(){
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestCallback");
    }
    public void requestCallback(String url, JSONObject json, AjaxStatus status){
        if(json != null){
            //SUCCES DE LA REQUETE
            JSONArray jsonArray = json.optJSONArray("etablissements");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                    String typeJSON = jsonArray.getJSONObject(i).getString("type");
                    String phoneJSON = jsonArray.getJSONObject(i).getString("phone");
                    String photoJSON = jsonArray.getJSONObject(i).getString("photo");
                    double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                    double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");
                    long idJSON = jsonArray.getJSONObject(i).getLong("id");


                    listEtablissements.add(new Etablissement(nameJSON, typeJSON, adresseJSON ,phoneJSON, photoJSON, latitudeJSON, longitudeJSON, idJSON));
                    Log.i("OBJET ETABLISSEMENT: ", "" + listEtablissements.get(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

        EtablissementsAdapter etablissementsAdapter = new EtablissementsAdapter(this, listEtablissements);
        listViewEtablissements.setAdapter(etablissementsAdapter);
    }

    public void onResume() {
        super.onResume();

        asyncJson();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent goToDetail = new Intent(this, DetailActivity.class);
        goToDetail.putExtra("etablissements", listEtablissements.get(position));

        startActivity(goToDetail);
    }

}