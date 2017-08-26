package fr.sylvain.revisionlistview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SYLVAIN on 26/08/2017.
 */

public class PersonneAdapter extends BaseAdapter {

    // Une liste de personnes
    private List<Personne> mListP;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;


    /**
     * Interface pour écouter les évènements sur le nom d'une personne
     */
    public interface PersonneAdapterListener {
        //liste de TOUSs nos listener qu'on peut faire par exemple onClick onMoseOverNom etc

        public void onClickNom(Personne item, int position);

    }

    //Contient la liste des listeners
    private ArrayList<PersonneAdapterListener> mListListener = new ArrayList<PersonneAdapterListener>();

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(PersonneAdapterListener aListener) {
        mListListener.add(aListener);
    }


    //On set notre listener sur chaque items de la listeView
    private void sendListener(Personne item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {

            //chaque item de la liste aura le listener onClickName de liste de nos listener
            mListListener.get(i).onClickNom(item, position);
        }
    }


    //On definit le comportement d'une method qui sera appeler)

    public PersonneAdapter(Context context, List<Personne> aListP) {
        mContext = context;
        mListP = aListP;
        mInflater = LayoutInflater.from(mContext);


    }


    @Override
    //renvoi une view en fonction de la position
    //convertView correspond au view "recyclé
    public View getView(int position, View convertView, ViewGroup parent) {

        //cellule
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts

        //SI il y a pas de view déjà utilisé et recyclé alors on commence par instancier un "pattern layout" pour une cellule de la view

        //On commence par vérifier si il y a deja un item qui a ete cree SI NON alors on commence par l'instancier, sinon on recopie son pattern
        //l'item (correspond à une cellule) = pattern (xml layout qu'on a creer au préalable + Class Personne dans notre cas qui correspond à la structure de ces donné)

        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.personne_layout, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }


        //(2) : Récupération des TextView de notre layout
        TextView tv_Nom = (TextView) layoutItem.findViewById(R.id.TV_Nom);
        TextView tv_Prenom = (TextView) layoutItem.findViewById(R.id.TV_Prenom);


        //(3) : Renseignement des valeurs

        tv_Nom.setText(mListP.get(position).nom);
        tv_Prenom.setText(mListP.get(position).prenom);

        //(4) Changement de la couleur du fond de notre item
        if (mListP.get(position).genre == Personne.MASCULIN) {
            layoutItem.setBackgroundColor(Color.BLUE);
        } else {
            layoutItem.setBackgroundColor(Color.MAGENTA);
        }

        //------------ Début de l'ajout ------- EVENT
//On mémorise la position de la "Personne" dans le composant textview
        tv_Nom.setTag(position);
//On ajoute un listener
        tv_Nom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer) v.getTag();

                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListener(mListP.get(position), position);

            }

        });
//------------ Fin de l'ajout -------

        //On retourne l'item créé.
        return layoutItem;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mListP.get(position);
    }

    @Override
    public int getCount() {
        return mListP.size();
    }


}
