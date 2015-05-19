package fr.imerir.cattouristique.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import fr.imerir.cattouristique.Models.Etablissement;
import fr.imerir.cattouristique.Models.EtablissementsAdapter;
import fr.imerir.cattouristique.R;


public class DetailActivity extends ActionBarActivity {

    AQuery aQueryObject;
    Etablissement etablissement;
    TextView detailNameEtablissement, detailAdressEtablissement, detailTypeEtablissement, detailPhonEtablissement;
    WebView webViewMap;
    ImageView imageViewPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        aQueryObject = new AQuery(this);

        detailAdressEtablissement = (TextView) findViewById(R.id.detailAdressEtablissement);
        detailTypeEtablissement = (TextView) findViewById(R.id.detailTypEtablissement);
        detailPhonEtablissement = (TextView) findViewById(R.id.detailPhonEtablissement);
        detailNameEtablissement = (TextView) findViewById(R.id.detailNamEtablissement);
        webViewMap = (WebView) findViewById(R.id.webViewMap);
        imageViewPicture = (ImageView) findViewById(R.id.detailPicture);



        etablissement = (Etablissement) getIntent().getSerializableExtra("etablissements");

        detailNameEtablissement.setText(etablissement.getName());
        detailTypeEtablissement.setText(etablissement.getType());
        detailAdressEtablissement.setText(etablissement.getAdresse());
        detailPhonEtablissement.setText(etablissement.getPhone());


    }

    public void onResume() {
        super.onResume();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
