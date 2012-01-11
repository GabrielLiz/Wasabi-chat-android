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
    	
    	//TODO Envoyer au serveur pour v�rification
    	// A revoir car lorsque les 2 mdp sont correct
    	//ca passe  quand meme dans le if et non dans le else.
    	if(pass!=confirm){
    	
	    	//On instancie notre layout en tant que View
	        LayoutInflater factory = LayoutInflater.from(this);
	        final View alertDialogView = factory.inflate(R.layout.error_create, null);
	 
	        //Cr�ation de l'AlertDialog
	        AlertDialog.Builder adb = new AlertDialog.Builder(this);
	 
	        //On affecte la vue personnalis� que l'on a cr�e � notre AlertDialog
	        adb.setView(alertDialogView);
	 
	        //On donne un titre � l'AlertDialog
	        adb.setTitle("Attention");
	 
	        //On modifie l'ic�ne de l'AlertDialog pour le fun ;)
	        adb.setIcon(android.R.drawable.ic_dialog_alert);
	 
	        //On affecte un bouton "OK" � notre AlertDialog et on lui affecte un �v�nement
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
    	 
        //R�cup�ration de la listview cr��e dans le fichier request.xml
    	requestList = (ListView) findViewById(R.id.RequestList);
 
        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier affichageitem.xml
        map.put("titre", "Jim");
        //on ins�re un �l�ment description que l'on r�cup�rera dans le textView description cr�� dans le fichier affichageitem.xml
        map.put("description", "Saluta ajoute moi stp...");
        //on ins�re la r�f�rence � l'image (convertit en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.photo));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre ListView
 
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
 
        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue request
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.requestview,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
 
        //On attribut � notre listView l'adapter que l'on vient de cr�er
        requestList.setAdapter(mSchedule);
 
        //Enfin on met un �couteur d'�v�nement sur notre listView
        requestList.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) requestList.getItemAtPosition(position);
        		//on cr�er une boite de dialogue
        		AlertDialog.Builder adb = new AlertDialog.Builder(ClientTchat.this);
        		//on attribut un titre � notre boite de dialogue
        		adb.setTitle("S�lection Item");
        		//on ins�re un message � notre boite de dialogue, et ici on affiche le titre de l'item cliqu�
        		adb.setMessage("Votre choix : "+map.get("titre"));
        		//on indique que l'on veut le bouton ok � notre boite de dialogue
        		adb.setPositiveButton("Ok", null);
        		//on affiche la boite de dialogue
        		adb.show();
        	}
         });
    }
}