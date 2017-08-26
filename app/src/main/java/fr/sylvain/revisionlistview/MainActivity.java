package fr.sylvain.revisionlistview;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PersonneAdapter.PersonneAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Récupération de la liste des personnes
        ArrayList<Personne> listP = Personne.getAListOfPersonne();

        //Création et initialisation de l'Adapter pour les personnes
        PersonneAdapter adapter = new PersonneAdapter(this, listP); //context, notre Liste
        //Correspondant à tous les traiement fait sur nos cellules


        //Ecoute des évènements sur votre liste
        adapter.addListener(this);

        //Récupération du composant ListView //creer au tout début
        ListView list = (ListView)findViewById(R.id.ListView01);
        //Initialisation de la liste avec les données
        list.setAdapter(adapter);


    }
    public void onClickNom(Personne item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Personne");

        builder.setMessage("Vous avez cliqué sur : " + item.nom);
        builder.setPositiveButton("Oui", null);
        builder.setNegativeButton("Non", null);
        builder.show();
    }
}
