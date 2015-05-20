package fr.imerir.cattouristique.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

import fr.imerir.cattouristique.Models.Etablissement;
import fr.imerir.cattouristique.Models.EtablissementsAdapter;
import fr.imerir.cattouristique.R;



public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    AQuery aQueryObject;
    ArrayList<Etablissement> listEtablissements, listEtablissementsBar, listEtablissementsResto, listEtablissementsHotel;
    ListView listViewEtablissements;
    RadioButton CB_Hotel, CB_Restaurant, CB_Bar;
    EditText search_bar;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aQueryObject = new AQuery(this);
        listEtablissements = new ArrayList<>();
        listEtablissementsBar = new ArrayList<>();
        listEtablissementsResto = new ArrayList<>();
        listEtablissementsHotel = new ArrayList<>();
        listViewEtablissements = (ListView) findViewById(R.id.elementsListView);
        listViewEtablissements.setOnItemClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        CB_Hotel = (RadioButton) findViewById(R.id.CB_HOTEL);
        CB_Restaurant = (RadioButton) findViewById(R.id.CB_RESTAURANT);
        CB_Bar = (RadioButton) findViewById(R.id.CB_BAR);

        search_bar = (EditText) findViewById(R.id.search_bar);
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

    public void asyncJson() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestCallback");
    }

    public void asyncJsonBar() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestBAR");
    }

    public void asyncJsonResto() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestRESTAURANT");
    }

    public void asyncJsonHotel() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestHOTEL");
    }

    public void requestCallback(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
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
        } else {
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

        EtablissementsAdapter etablissementsAdapter = new EtablissementsAdapter(this, listEtablissements);
        listViewEtablissements.setAdapter(etablissementsAdapter);
    }

    public void requestBAR(String url, JSONObject json, AjaxStatus status) {
        if (listEtablissementsBar.size() > 0)
            listEtablissementsBar.clear();
        if (json != null) {
            //SUCCES DE LA REQUETE
            Log.i("JSON REMPLI: ", "json rempli"+9);
            JSONArray jsonArray = json.optJSONArray("etablissements");

            Log.i("contenu eta: ", "contenu etab "+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    long idJSON = jsonArray.getJSONObject(i).getLong("id");
                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    String typeJSON = jsonArray.getJSONObject(i).getString("type");

                    if (typeJSON.equals("Bars")){
                        Log.i("TYPE EGAL BAR: ", "TYPE EGAL BAR");
                        String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                        String phoneJSON = jsonArray.getJSONObject(i).getString("phone");
                        String photoJSON = jsonArray.getJSONObject(i).getString("photo");
                        double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                        double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                        Etablissement newEtablissement =  new Etablissement(nameJSON, typeJSON, adresseJSON ,phoneJSON, photoJSON, latitudeJSON, longitudeJSON, idJSON);
                        listEtablissementsBar.add(newEtablissement);
                    }else
                        Log.i("TYPE EGAL PAS BAR: ", "TYPE EGAL PAS BAR");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

        EtablissementsAdapter etablissementsAdapter = new EtablissementsAdapter(this, listEtablissementsBar);
        listViewEtablissements.setAdapter(etablissementsAdapter);
    }

    public void requestRESTAURANT(String url, JSONObject json, AjaxStatus status) {
        if (listEtablissementsResto.size() > 0)
            listEtablissementsResto.clear();

        if (json != null) {
            //SUCCES DE LA REQUETE
            Log.i("JSON REMPLI: ", "json rempli"+9);
            JSONArray jsonArray = json.optJSONArray("etablissements");

            Log.i("contenu eta: ", "contenu etab "+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    long idJSON = jsonArray.getJSONObject(i).getLong("id");
                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    String typeJSON = jsonArray.getJSONObject(i).getString("type");

                    if (typeJSON.equals("Restaurants")){
                        Log.i("TYPE EGAL RESTAURANT: ", "TYPE EGAL RSTAURANT");
                        String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                        String phoneJSON = jsonArray.getJSONObject(i).getString("phone");
                        String photoJSON = jsonArray.getJSONObject(i).getString("photo");
                        double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                        double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                        Etablissement newEtablissement =  new Etablissement(nameJSON, typeJSON, adresseJSON ,phoneJSON, photoJSON, latitudeJSON, longitudeJSON, idJSON);
                        listEtablissementsResto.add(newEtablissement);
                    }else
                        Log.i("TYPE EGAL PAS RESTO: ", "TYPE EGAL PAS RESTAURANT");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

        EtablissementsAdapter etablissementsAdapter = new EtablissementsAdapter(this, listEtablissementsResto);
        listViewEtablissements.setAdapter(etablissementsAdapter);
    }

    public void requestHOTEL(String url, JSONObject json, AjaxStatus status) {
        if (listEtablissementsHotel.size() > 0)
            listEtablissementsHotel.clear();

        if (json != null) {
            //SUCCES DE LA REQUETE
            Log.i("JSON REMPLI: ", "json rempli"+9);
            JSONArray jsonArray = json.optJSONArray("etablissements");

            Log.i("contenu eta: ", "contenu etab "+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    long idJSON = jsonArray.getJSONObject(i).getLong("id");
                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    String typeJSON = jsonArray.getJSONObject(i).getString("type");

                    if (typeJSON.equals("Hotels")){
                        Log.i("TYPE EGAL RESTAURANT: ", "TYPE EGAL RSTAURANT");
                        String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                        String phoneJSON = jsonArray.getJSONObject(i).getString("phone");
                        String photoJSON = jsonArray.getJSONObject(i).getString("photo");
                        double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                        double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                        Etablissement newEtablissement =  new Etablissement(nameJSON, typeJSON, adresseJSON ,phoneJSON, photoJSON, latitudeJSON, longitudeJSON, idJSON);
                        listEtablissementsHotel.add(newEtablissement);
                    }else
                        Log.i("TYPE EGAL PAS RESTO: ", "TYPE EGAL PAS RESTAURANT");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

        EtablissementsAdapter etablissementsAdapter = new EtablissementsAdapter(this, listEtablissementsHotel);
        listViewEtablissements.setAdapter(etablissementsAdapter);
    }

    public void onResume() {
        super.onResume();

        asyncJson();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                if (checkedID == R.id.CB_BAR)
                    asyncJsonBar();
                else if (checkedID == R.id.CB_HOTEL)
                    asyncJsonHotel();
                else if (checkedID == R.id.CB_RESTAURANT)
                    asyncJsonResto();
                else
                    asyncJson();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent goToDetail = new Intent(this, DetailActivity.class);
        goToDetail.putExtra("etablissements", listEtablissements.get(position));

        startActivity(goToDetail);
    }
}