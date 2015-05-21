package fr.imerir.cattouristique.Models;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import fr.imerir.cattouristique.R;

/**
 * Created by rcdsm on 18/05/2015.
 */
public class EtablissementsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Etablissement> listOfEtablissements;
    LayoutInflater inflater;
    AQuery aQueryObject;


    public EtablissementsAdapter(Context context, ArrayList<Etablissement> listOfEtablissements) {
        this.context = context;
        this.listOfEtablissements = listOfEtablissements;
        this.aQueryObject = new AQuery(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listOfEtablissements.size();
    }

    //@Override
    public Object getItem(int position) {
        return listOfEtablissements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final NewView newView;

        if(view == null){
            view = inflater.inflate(R.layout.etablissement_item, null);

            newView = new NewView();
            newView.etablissement_name = (TextView) view.findViewById(R.id.etablissementName);
            newView.etablissement_type = (TextView) view.findViewById(R.id.etablissementType);
            newView.etablissement_photo = (ImageView) view.findViewById(R.id.etablissementPicture);
            //newView.favoEtat = (ImageButton) view.findViewById(R.id.favoriteEtat);
            view.setTag(newView);
        }
        else{
            newView = (NewView) view.getTag();
        }



        newView.etablissement_name.setText(listOfEtablissements.get(position).getName().toString());
        newView.etablissement_type.setText(listOfEtablissements.get(position).getType().toString());
        if(listOfEtablissements.get(position).getPhoto_link() != null && listOfEtablissements.get(position).getPhoto_link().length()>0) {
            aQueryObject.id(newView.etablissement_photo).image(listOfEtablissements.get(position).getPhoto_link());
        }



        return view;
    }

    class NewView {
        public ImageView etablissement_photo;
        public TextView etablissement_name;
        public TextView etablissement_type;
        //private ImageButton favoEtat;
    }

}
