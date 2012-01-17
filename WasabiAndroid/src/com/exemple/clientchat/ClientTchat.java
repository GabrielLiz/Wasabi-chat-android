package com.exemple.clientchat;

import DataTransport.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.exemple.clientchat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
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
import android.widget.Toast;

public class ClientTchat extends Activity {

	InetAddress address = null;
	private static Socket s_active, s_passive;		// Socket client
	private static ObjectInputStream ois_active;	// Flux d'entré du socket
	private static ObjectOutputStream oos_active;	// Flux de sortie du socket
	private static ObjectInputStream ois_passive;	// Flux d'entré du socket (passif = réception de donnée)
	private static ObjectOutputStream oos_passive;	// Flux de sortie du socket (passif = réception de donnée)

	
	/**
	 * used to know the current layout and to switch between the current layout
	 * and the precedent one
	 */
	enum ListLayout {
		MainLayout, CreateUserLayout, ConnectUserLayout, ListContactLayout, 
		TchatLayout	};
	
	ListLayout currentLayout; 
    
    /**
	 * when destroying the activity
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.finish();
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        currentLayout = ListLayout.MainLayout;
        connectToServer();
    }
   
    /**
     * create user menu
     */
    public void createMenu(View btn){
    	setContentView(R.layout.create);   
    	currentLayout = ListLayout.CreateUserLayout; 
    }
    
    public void authentification(View btn){
    	EditText editLogin   = (EditText)findViewById(R.id.loginText);
    	EditText editPass    = (EditText)findViewById(R.id.passText);
    	EditText editConfirm = (EditText)findViewById(R.id.passConfText);
        	
    	String login   = editLogin.getText().toString();
    	String pass    = editPass.getText().toString();
    	String confirm = editConfirm.getText().toString();
    	
    	//Si les champs sont pas tous remplis = erreur
    	if(login.compareTo("")==0 || pass.compareTo("")==0 || confirm.compareTo("")==0){
    		printToast("Error : fully fill this please!");
    	}
    	//Si les 2 mdp sont pas les mêmes
    	else if(pass.compareTo(confirm)!=0){
    		printToast("Error : pwd and confirm must be the same!");
    	}
    	//Si tout est en ordre essaie de s'enregistrer sur le serveur
    	else{
    		
    		if(registerToServer(login, pass))
    			setContentView(R.layout.main);	
    	}
    }
    
    /**
     * print toast
     */
    public void printToast(String msg){
    	Toast toast;
    	toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
    	toast.show();
    }
    
    /**
     * connect to server
     */
    public int connectToServer(){
    	
		// On commence par chercher l'adresse du serveur
		try {
			address = InetAddress.getByName("www.mubedded.com");
		} catch (UnknownHostException e) {
			printToast("Error : server unreachable!");
			return -1;
		}
		
		// On tente de se connecter au serveur
		try {
			s_active = new Socket(address, 3000);
		} catch (IOException e) {
			printToast("Error : active socket creation failed!");
			return -1;
		}
		
		// On crée le flux sortant !!!OBLIGATOIRE de créer le flux sortant avant le flux entrant!!!
		try {
			oos_active = new ObjectOutputStream(s_active.getOutputStream());
		} catch (IOException e) {
			printToast("Error : active OOS creation failed!");
			return -1;
		}
		// On crée le flux entrant
		try {
			ois_active = new ObjectInputStream(s_active.getInputStream());
		} catch (Exception e) {
			printToast("Error : active OIS creation failed!");
			return -1;
		}
    	
		return 0;
    }//connectToServer
    
    /**
     * try to register the new user to the server
     */
    public boolean registerToServer(String login, String password){
    	
		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Registration avec comme params le login et le mot de pass
    	Container container = new Container(new Registration(login, password), login);
		try {
			// On tente d'envoyer les données
			oos_active.writeObject(container);
			// On tente de recevoir les données
			Status status = (Status)ois_active.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
			switch(status.getValue()){
			case OK:
				printToast("Account registration succeed!");
				return true;
			case CONTACT_ALREADY_EXISTS:
				printToast("Error : User already exist!");
				return false;
			case CONTACT_CREATION_FAILED:
				printToast("Error : User creation failed!");
				return false;
			default:
				printToast("Error : Unknown error!");
				return false;
			}
		} catch (Exception e) {
			printToast("Error : communication error!");
			return false;
		}
    }//registerToServer()
    
    /**
     * Auth to server
     */
    public boolean authToServer(String login, String password){

		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Authentification avec comme params le login et le mot de pass
		Container container = new Container(new Authentification(login, password), login);
		
		//Auth sur le port actif
		try {
			// On tente d'envoyer les données
			oos_active.writeObject(container);
			// On tente de recevoir les données
			Status status = (Status)ois_active.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
			switch(status.getValue()){
			case CONTACT_AUTHENTIFICATION_SUCCESS:

				//creation du socket
				try {
					s_passive = new Socket(address, 3001);
				} catch (IOException e) {
					printToast("Error : passive socket creation failed!");
					return false;
				}
				
				// On crée le flux sortant !!!OBLIGATOIRE de créer le flux sortant avant le flux entrant!!!
				try {
					oos_passive = new ObjectOutputStream(s_passive.getOutputStream());
				} catch (IOException e) {
					printToast("Error : passive OOS creation failed!");
					return false;
				}
				// On crée le flux entrant
				try {
					ois_passive = new ObjectInputStream(s_passive.getInputStream());
				} catch (Exception e) {
					printToast("Error : passive OIS creation failed!");
					return false;
				}
				
				container = new Container(new Authentification(login, password), login);
				
				//Auth sur le port passif
				try {
					// On tente d'envoyer les données
					oos_passive.writeObject(container);
					// On tente de recevoir les données
					status = (Status)ois_passive.readObject();

					// On switch sur le type de retour que nous renvoie le serveur
					// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
					switch(status.getValue()){
					case CONTACT_AUTHENTIFICATION_SUCCESS:
						break;
					case CONTACT_AUTHENTIFICATION_FAILED:
						printToast("Error : login failed on passive socket!");
						return false;
					default:
						printToast("Error : unknown error!");
						return false;
					}
				} catch(Exception e){
					e.printStackTrace();
					printToast("Error : communication error!");
					return false;
				}
			break;
			
			case CONTACT_AUTHENTIFICATION_FAILED:
				printToast("Error : login failed on active socket!");
				return false;
				
			default:
				printToast("Error : unknown error!");
				return false;
			}
		} catch (Exception e) {
			printToast("Error : communication error!");
			return false;
		}
		
		printToast("Login succeed!");
		
		return true;
    }
    /**
     * authenticate user menu
     * @param btn
     */
    public void loginMenu(View btn) {
    	setContentView(R.layout.authent);
    	currentLayout = ListLayout.ConnectUserLayout; 
    }
    
    /**
     * get the client list in the ListView  
     * @param btn
     */
    public void getClientList (View btn){
    	
    	EditText editLogin = (EditText)findViewById(R.id.loginText);
    	EditText editPassword = (EditText)findViewById(R.id.passText);
   
    	String login   = editLogin.getText().toString();
    	String password    = editPassword.getText().toString();
    	
    	if(authToServer(login,password)){
    		currentLayout = ListLayout.ListContactLayout; 
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
    	
    }
    
    /**
     * the principal window of tchat  
     * @param clientName
     */
    public void tchatMenu (String clientName){
    	setContentView(R.layout.tchat);
    	currentLayout = ListLayout.TchatLayout; 
    	TextView clientNameLbl = (TextView)findViewById(R.id.clientNameLbl);
    	clientNameLbl.setText(clientName);
    }
    
    /**
     * the listener for send message button
     * @param btn
     */
    public void sendMsg(View btn){
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
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			changeLayout();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    /**
	 * to update the new layout to show 
	 */
	public void changeLayout() {

		switch (currentLayout) {
			case MainLayout: {
				showAlert("Quit Wasabi, Are you sure?", currentLayout);
				break;
			}
			case ConnectUserLayout:
			case CreateUserLayout: {
				setContentView(R.layout.main);
				currentLayout = ListLayout.MainLayout;
				break;
			}
			case ListContactLayout: {
				showAlert("Disconnect user?", currentLayout);
				break;
			}
			case TchatLayout: {
				setContentView(R.layout.list);
				currentLayout = ListLayout.ListContactLayout;
				break;
			}
		}
	}
	
	/**
	 * to show message alert 
	 * @param msg
	 */
	public void showAlert(String msg, final ListLayout layout) {
		new AlertDialog.Builder(this).setTitle("Attention!").setMessage(msg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					//@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (layout) {
						case MainLayout: {
							onDestroy();
							break;
						}
						
						case ListContactLayout: {
							setContentView(R.layout.main);
							currentLayout = ListLayout.MainLayout;
							break;
						}
						default: {
							break;
						}
						}
					}
				}).show();
	}

}