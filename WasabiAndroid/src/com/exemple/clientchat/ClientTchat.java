package com.exemple.clientchat;

import com.exemple.clientchat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.Toast;

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
}