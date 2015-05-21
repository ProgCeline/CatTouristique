package fr.imerir.cattouristique.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.imerir.cattouristique.Models.Etablissement;
import fr.imerir.cattouristique.Models.EtablissementsAdapter;
import fr.imerir.cattouristique.R;



public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, FragmentDrawer.FragmentDrawerListener {

    AQuery aQueryObject;
    ArrayList<Etablissement> listEtablissements, listEtablissementsBar, listEtablissementsResto, listEtablissementsHotel;
    ListView listViewEtablissements;
    RadioButton CB_Hotel, CB_Restaurant, CB_Bar;
    EditText search_bar;
    RadioGroup radioGroup;
    Switch ToggleButton;

    android.support.v7.widget.Toolbar toolbar;
    private FragmentDrawer drawerFragment;

    EtablissementsAdapter etablissementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aQueryObject = new AQuery(this);
        listEtablissements = new ArrayList<>();
        listEtablissementsBar = new ArrayList<>();
        listEtablissementsResto = new ArrayList<>();
        listEtablissementsHotel = new ArrayList<>();
        etablissementsAdapter = new EtablissementsAdapter(this, listEtablissementsBar);


        listViewEtablissements = (ListView) findViewById(R.id.elementsListView);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        CB_Hotel = (RadioButton) findViewById(R.id.CB_HOTEL);
        CB_Restaurant = (RadioButton) findViewById(R.id.CB_RESTAURANT);
        CB_Bar = (RadioButton) findViewById(R.id.CB_BAR);
        ToggleButton = (Switch) findViewById(R.id.ToggleButton);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


        listViewEtablissements.setOnItemClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);


        search_bar = (EditText) findViewById(R.id.search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("TEXT CHANGE ", "text change");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("TEXT a CHANGE ", "text a change");
                asyncJsonSearch();
            }
        });

        ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        displayView(0);
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

        if (id == R.id.search_bar){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void asyncJson() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "requestCallback");
    }

    public void asyncJsonSearch() {
        String URL = "http://perso.imerir.com/cboyer/etablissements.json";
        aQueryObject.ajax(URL, JSONObject.class, this, "searchEtablissement");
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

    public void searchEtablissement(String url, JSONObject json, AjaxStatus status) {
        if (listEtablissements.size() > 0)
            listEtablissements.clear();
        if (json != null) {
            //SUCCES DE LA REQUETE
            Log.i("JSON REMPLI: ", "JSON REMPLI");
            JSONArray jsonArray = json.optJSONArray("etablissements");

            Log.i("RESULT ETABLISS: ", "RESULT ETABLISS "+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    String nameJSON = jsonArray.getJSONObject(i).getString("name");
                    //if (search_bar.getText().)
                    if (search_bar.getText().equals(nameJSON)){
                        Log.i("SEARCH BAR GOOD: ", "SEARCH BAR GOOD");
                        long idJSON = jsonArray.getJSONObject(i).getLong("id");
                        String typeJSON = jsonArray.getJSONObject(i).getString("type");
                        String adresseJSON = jsonArray.getJSONObject(i).getString("adresse");
                        String phoneJSON = jsonArray.getJSONObject(i).getString("phone");
                        String photoJSON = jsonArray.getJSONObject(i).getString("photo");
                        double latitudeJSON = jsonArray.getJSONObject(i).getDouble("lat");
                        double longitudeJSON = jsonArray.getJSONObject(i).getDouble("lon");

                        Etablissement newEtablissement =  new Etablissement(nameJSON, typeJSON, adresseJSON ,phoneJSON, photoJSON, latitudeJSON, longitudeJSON, idJSON);
                        listEtablissements.add(newEtablissement);
                    }else
                        Log.i("SEARCH BAR PAS GOOD ", "SEARCH BAR PAS GOOD");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //ECHEC DE LA REQUETE
            Log.i("ELEMENT RECUS: -", "RIEN RECU");
        }

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

        if (view == CB_Bar)
            goToDetail.putExtra("etablissements", listEtablissementsBar.get(position));
        else if (view == CB_Hotel)
            goToDetail.putExtra("etablissements", listEtablissementsHotel.get(position));
        else if (view == CB_Restaurant)
            goToDetail.putExtra("etablissements", listEtablissementsResto.get(position));
        else
            goToDetail.putExtra("etablissements", listEtablissements.get(position));

        startActivity(goToDetail);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new FavoriteFragment();
                title = getString(R.string.title_activity_favorite);
                break;
            case 1:
                //fragment = new FriendsFragment();
                //title = getString(R.string.title_friends);
                break;
            case 2:
                //fragment = new MessagesFragment();
                //title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}