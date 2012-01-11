package com.exemple.clientchat;

import java.util.ArrayList;
import java.util.HashMap;

import com.exemple.clientchat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

public class ClientTchat extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * create user menu
     */
    public void createMenu(View btn){
    	setContentView(R.layout.create);
    	
    }
    
    public void authentification(View btn){
    	EditText editLogin   = (EditText)findViewById(R.id.loginText);
    	EditText editPass    = (EditText)findViewById(R.id.passText);
    	EditText editConfirm = (EditText)findViewById(R.id.passConfText);
        	
    	String login   = editLogin.getText().toString();
    	String pass    = editPass.getText().toString();
    	String confirm = editConfirm.getText().toString();
    	
    	//TODO Envoyer au serveur pour vérification
    	// A revoir car lorsque les 2 mdp sont correct
    	//ca passe  quand meme dans le if et non dans le else.
    	if(pass!=confirm){
    	
	    	//On instancie notre layout en tant que View
	        LayoutInflater factory = LayoutInflater.from(this);
	        final View alertDialogView = factory.inflate(R.layout.error_create, null);
	 
	        //Création de l'AlertDialog
	        AlertDialog.Builder adb = new AlertDialog.Builder(this);
	 
	        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
	        adb.setView(alertDialogView);
	 
	        //On donne un titre à l'AlertDialog
	        adb.setTitle("Attention");
	 
	        //On modifie l'icône de l'AlertDialog pour le fun ;)
	        adb.setIcon(android.R.drawable.ic_dialog_alert);
	 
	        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
	        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	setContentView(R.layout.create);
	            	
	          } });
	 
	        adb.show();
    	}
    	else{
    		setContentView (R.layout.list);
    	}
    }
    
    /**
     * authenticate user menu
     * @param btn
     */
    public void loginMenu(View btn) {
    	setContentView(R.layout.authent);
    }
    
    /**
     * get the client list in the ListView  
     * @param btn
     */
    public void getClientList (View btn){
    	setContentView (R.layout.list);
    	String [] clientList = new String[]{"alain", "toto", "tom", "jack"};
    	final ListView list = (ListView)findViewById(R.id.clientList);
    	list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, clientList));  
    	
    	list.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int pos, long l) {
				tchatMenu(list.getItemAtPosition(pos).toString());
			}
		});
    	
    }
    
    /**
     * the principal window of tchat  
     * @param clientName
     */
    public void tchatMenu (String clientName){
    	setContentView(R.layout.tchat);
    	TextView clientNameLbl = (TextView)findViewById(R.id.clientNameLbl);
    	clientNameLbl.setText(clientName);
    }
    
    /**
     * the listener for send message button
     * @param btn
     */
    public void sendMsg(View btn){
    	//setContentView(R.layout.tchat);
    	EditText edit = (EditText)findViewById(R.id.tchatText);
    	ScrollView scroll = (ScrollView)findViewById(R.id.scrollView);
    	TextView messageTxt = (TextView)findViewById(R.id.tchatLbl);
    	String msg = edit.getText().toString();
    	messageTxt.append("\n");
    	messageTxt.append(msg);
    	edit.setText("");
    }
    
    
    /**
     * Add a contact
     * @param btn
     */
    public void ViewAddContact(View btn){
    	setContentView(R.layout.addcontact);

    }
    
    /**
     * Add a contact
     * @param btn
     */
    public void addContact(View btn){
    	//TODO Conecter au serveur pour ajouter contact
    	getClientList(btn);
    }
    
    private ListView requestList;
    /**
     * View request
     * @param btn
     */
    public void ViewRequest(View btn){
    	//TODO Conecter au serveur pour voir les demande d'ajout
    	setContentView(R.layout.request);
    	 
        //Récupération de la listview créée dans le fichier request.xml
    	requestList = (ListView) findViewById(R.id.RequestList);
 
        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Création d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        map.put("titre", "Jim");
        //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
        map.put("description", "Saluta ajoute moi stp...");
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.photo));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView
 
        map = new HashMap<String, String>();
        map.put("titre", "Murphy");
        map.put("description", "Add me plz");
        map.put("img", String.valueOf(R.drawable.photo));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Lara");
        map.put("description", "Hello tu m'ajoute?");
        map.put("img", String.valueOf(R.drawable.photo));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Jerry");
        map.put("description", "ajoute moi!");
        map.put("img", String.valueOf(R.drawable.photo));
        listItem.add(map);
 
        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue request
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.requestview,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
 
        //On attribut à notre listView l'adapter que l'on vient de créer
        requestList.setAdapter(mSchedule);
 
        //Enfin on met un écouteur d'évènement sur notre listView
        requestList.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) requestList.getItemAtPosition(position);
        		//on créer une boite de dialogue
        		AlertDialog.Builder adb = new AlertDialog.Builder(ClientTchat.this);
        		//on attribut un titre à notre boite de dialogue
        		adb.setTitle("Sélection Item");
        		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
        		adb.setMessage("Votre choix : "+map.get("titre"));
        		//on indique que l'on veut le bouton ok à notre boite de dialogue
        		adb.setPositiveButton("Ok", null);
        		//on affiche la boite de dialogue
        		adb.show();
        	}
         });
    }
}