package com.exemple.clientchat;


import com.exemple.clientchat.ClientChatOperation;

import DataModel.*;
import DataTransport.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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




public class ClientChatApp extends Activity {
	

	
	/**
	 * used to know the current layout and to switch between the current layout
	 * and the precedent one
	 */
	enum ListLayout {
		MainLayout, CreateUserLayout, ConnectUserLayout, ListContactLayout, 
		TchatLayout	};
	
	ListLayout currentLayout; 
	
	ClientChatOperation clientOp;
	
	private ListView requestList;
	
    
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
        try {
			ClientChatOperation.connectToServer();
			printToast (ClientChatOperation.getOpResult());
		}
        catch (ClientChatException e){
        	printToast(e.getMsg());
		}
        catch (Exception e) {
			// TODO Auto-generated catch block
			printToast(e.getMessage());
		}
    }
   
    /**
     * create user menu
     */
    public void createMenu(View btn){
    	setContentView(R.layout.create);   
    	currentLayout = ListLayout.CreateUserLayout; 
    }
    
    public void createUser(View btn){
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
    	else if(!pass.equals(confirm)){
    		printToast("Error : pwd and confirm must be the same!");
    	}
    	//Si tout est en ordre essaie de s'enregistrer sur le serveur
    	else{
    		clientOp = new ClientChatOperation(login,pass);
    		try {
				if(clientOp.registerToServer()){
					setContentView(R.layout.main);
					printToast(ClientChatOperation.getOpResult());
				}
			}catch (ClientChatException e){
				printToast(e.getMsg());
			}
    		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}	
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
    	String pass   = editPassword.getText().toString();
    	
    	clientOp = new ClientChatOperation(login,pass);
    	
    	try {
			if(clientOp.authToServer()){
				currentLayout = ListLayout.ListContactLayout; 
				setContentView (R.layout.list);
				showContactList();
			}
			printToast(ClientChatOperation.getOpResult());
    	}
		catch(ClientChatException e){
			printToast (e.getMsg());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			printToast("hi");
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
    	// begin receiving messages form a contact
    	startReceiveMsg(clientName);
    }
    
    /**
     * this method 
     * @param selectedContact
     */
    private void startReceiveMsg(final String selectedContact){
    	try {
			clientOp.listenAndReceiveMsg(selectedContact);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Need handler for callbacks to the UI thread
    	final Handler handler = new Handler();
    	
    	//display the message (back in the UI main thread, bacause you can't here : CalledFromWrongThreadException)
    	handler.post(new Runnable() {
            public void run() {
            	TextView messageTxt = (TextView)findViewById(R.id.tchatLbl);
            	messageTxt.append("\n");
        		messageTxt.append(selectedContact + " : " +clientOp.getMsgReceived());
            }
        });
    	
    }
    
    /**
     * the listener for send message button
     * @param btn
     */
    public void sendMsg(View btn){
    	EditText edit = (EditText)findViewById(R.id.tchatText);
    	TextView messageTxt = (TextView)findViewById(R.id.tchatLbl);
    	String selectedContact = ((TextView)findViewById(R.id.clientNameLbl)).toString();
    	String msg = edit.getText().toString();
    	
    	try{
    		clientOp.sendMsg(msg,selectedContact);
    		messageTxt.append("\n");
    		messageTxt.append(clientOp.getUserName() + " : " + msg);
    		edit.setText("");
    	}catch (Exception e){
    		printToast(e.getMessage());
    	}
    	//display the message
		
    }
    
    /**
     * this method is used to refresh the contact 
     * list in the list Layout
     */
    public void showContactList(){
    	final ListView list = (ListView)findViewById(R.id.clientList);
    	String [] clientList = clientOp.retreiveContactList();	
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, clientList));  
		
		list.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int pos, long l) {
				tchatMenu(list.getItemAtPosition(pos).toString());
			}
		});
    	
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
        		AlertDialog.Builder adb = new AlertDialog.Builder(ClientChatApp.this);
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
				showContactList();
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