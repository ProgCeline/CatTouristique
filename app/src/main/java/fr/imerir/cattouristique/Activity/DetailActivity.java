package fr.imerir.cattouristique.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import fr.imerir.cattouristique.Models.Etablissement;
import fr.imerir.cattouristique.R;


public class DetailActivity extends ActionBarActivity implements View.OnClickListener{

    AQuery aQueryObject;
    Etablissement etablissement;
    TextView detailNameEtablissement, detailAdressEtablissement, detailTypeEtablissement, detailPhonEtablissement;
    WebView webViewMap;
    Button detailBtnPicture;
    ImageView imageViewPicture;
    ImageButton favoriteEtat;

    ArrayList<Etablissement> listFAV;

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
        detailBtnPicture = (Button) findViewById(R.id.detailBtnPicture);


        etablissement = (Etablissement) getIntent().getSerializableExtra("etablissements");

        detailNameEtablissement.setText(etablissement.getName());
        detailTypeEtablissement.setText(etablissement.getType());
        detailAdressEtablissement.setText(etablissement.getAdresse());
        detailPhonEtablissement.setText(etablissement.getPhone());


        if(etablissement.getPhoto_link()!=null && etablissement.getPhoto_link().length()>0) {
            aQueryObject.id(imageViewPicture).image(etablissement.getPhoto_link());
        }

        detailBtnPicture.setOnClickListener(this);

        //favoriteEtat = (ImageButton) findViewById(R.id.favoriteEtat);
        //listFAV = new ArrayList<>();

    }

    public void onResume() {
        super.onResume();

        /**
        favoriteEtat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteEtat.setBackgroundColor(Color.YELLOW);

                listFAV.add(etablissement);
            }
        });
         **/

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
    public void onClick(View v){
        Intent goToDetail = new Intent(this, GridActivity.class);
        startActivity(goToDetail);
    }


}

