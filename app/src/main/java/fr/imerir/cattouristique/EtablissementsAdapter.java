package fr.imerir.cattouristique;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rcdsm on 18/05/2015.
 */
public class EtablissementsAdapter {

    Context context;
    ArrayList<Etablissement> listOfEtablissements;
    LayoutInflater inflater;


    public EtablissementsAdapter(Context _context, ArrayList<Etablissement> _listOfEtablissements) {
        this.context = _context;
        this.listOfEtablissements = _listOfEtablissements;

        inflater = LayoutInflater.from(context);
    }

    //@Override
    public int getCount() {
        return listOfEtablissements.size();
    }

    //@Override
    public Object getItem(int position) {
        return listOfEtablissements.get(position);
    }

    /**
     @Override
     public long getItemId(int position){
     return listOfEtablissements.get(position).get
     return notes.get(position).getId();
     }
     **/

    //@Override
    public View getNewView(int position, View convertView, ViewGroup parent){
        NewView newView;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.etablissement_item, null);

            newView = new NewView();
            newView.etablissement_name = (TextView) convertView.findViewById(R.id.etablissementName);
            newView.etablissement_type = (TextView) convertView.findViewById(R.id.etablissementType);
            newView.etablissement_photo = (ImageView) convertView.findViewById(R.id.etablissementPicture);
            convertView.setTag(newView);
        }
        else{
            newView = (NewView) convertView.getTag();
        }

        //holder.title.setText(notes.get(position).getTitle());
        //holder.date.setText(format.format(notes.get(position).getCreatedAt()));

        return convertView;
    }
    class NewView {
        public ImageView etablissement_photo;
        public TextView etablissement_name;
        public TextView etablissement_type;
    }
}
