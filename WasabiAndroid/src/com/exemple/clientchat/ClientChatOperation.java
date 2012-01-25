/**
 * @author Med_Regaya
 */
package com.exemple.clientchat;

import android.os.Handler;
import android.widget.TextView;

import com.exemple.clientchat.*;
import DataModel.*;
import DataTransport.*;

import com.exemple.clientchat.ClientChatException;
import com.exemple.clientchat.ClientChatApp.ListLayout;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;



public class ClientChatOperation{
	
	private static InetAddress address = null;
	private static Socket s_active, s_passive;		// Socket client
	public static ObjectInputStream ois_active;	// Flux d'entré du socket
	public static ObjectOutputStream oos_active;	// Flux de sortie du socket
	public static ObjectInputStream ois_passive;	// Flux d'entré du socket (passif = réception de donnée)
	public static ObjectOutputStream oos_passive;	// Flux de sortie du socket (passif = réception de donnée)
	
	
	
	private String login;
	private String pass;
	private static int clientId;
	private ContactListResponse contacts;
	private String msgRcvd = null;
	
	
	
	private static String opResult;
	
	/**
	 * create a new ClientChatOperation to 
	 * exchange commands with the server
	 * @param login the user login 
	 * @param pass the user password
	 */
	public ClientChatOperation (String login, String pass){
		this.login = login;
		this.pass= pass;
		contacts = new ContactListResponse();
	}
	
	/**
     * connect to server
     * @Exception: 
     * 
     */
    public static int connectToServer() throws Exception{
    	
		// On commence par chercher l'adresse du serveur
		try {
			//address = InetAddress.getByName("www.mubedded.com");
			address = InetAddress.getByName("10.192.83.108");
		} catch (UnknownHostException e) {
			throw new ClientChatException(ClientChatException.HOST_INCONNU, 
										"ERROR: Server unreachable");
		}
		
		// On tente de se connecter au serveur
		try {
			s_active = new Socket(address, 3000);
		} catch (IOException e) {
			throw new ClientChatException(ClientChatException.IO_ERREUR, 
										"Error : active socket creation failed!");
		}
		
		// On crée le flux sortant !!!OBLIGATOIRE de créer le flux sortant avant le flux entrant!!!
		try {
			oos_active = new ObjectOutputStream(s_active.getOutputStream());
		} catch (IOException e) {
			throw new ClientChatException(ClientChatException.IO_ERREUR, 
										"Error : active OOS creation failed!");
		}
		// On crée le flux entrant
		try {
			ois_active = new ObjectInputStream(s_active.getInputStream());
		} catch (Exception e) {
			throw new ClientChatException(ClientChatException.IO_ERREUR, 
										"Error : active OIS creation failed!");
		}
    	
		opResult = "Connected to server!";
		return 0;
    }//connectToServer
    
    /**
     * try to register the new user to the server
     */
    public boolean registerToServer() throws Exception{
    	
		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Registration avec comme params le login et le mot de pass
    	Container container = new Container(new Registration(login, pass), login);
		try {
			// On tente d'envoyer les données
			oos_active.writeObject(container);
			// On tente de recevoir les données
			Status status = (Status)ois_active.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
			switch(status.getValue()){
			case OK:
				opResult = "Account registration succeed!";
				return true;
			case CONTACT_ALREADY_EXISTS:
				opResult = "Error : User already exist!";
				return false;
			case CONTACT_CREATION_FAILED:
				opResult = "Error : User creation failed!";
				return false;
			default:
				opResult = "Error : Unknown error!";
				return false;
			}
		} catch (Exception e) {
			throw new ClientChatException (ClientChatException.IO_ERREUR, "ERROR: Communication Error");
		}
    }//registerToServer()
	
    /**
     * Auth to server
     */
    public boolean authToServer() throws ClientChatException{

		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Authentification avec comme params le login et le mot de pass
    	Container container = new Container(new Authentification(login, pass), 
    											login);
		
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
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"Error : passive socket creation failed!");
					
				}
				
				// On crée le flux sortant !!!OBLIGATOIRE de créer le flux sortant avant le flux entrant!!!
				try {
					oos_passive = new ObjectOutputStream(s_passive.getOutputStream());
				} catch (IOException e) {
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"Error : passive OOS creation failed!");
				}
				// On crée le flux entrant
				try {
					ois_passive = new ObjectInputStream(s_passive.getInputStream());
				} catch (Exception e) {
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"Error : passive OIS creation failed!");
				}
				
				container = new Container(new Authentification(login,pass), login);				
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
						//ClientChatApp.startReceptionThread();
						break;
					case CONTACT_AUTHENTIFICATION_FAILED:
						opResult = "Error : login failed on passive socket!";
						return false;
					default:
						opResult = "Error : unknown error!"; 
						return false;
					}
				} catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			break;
			
			case CONTACT_AUTHENTIFICATION_FAILED:
				opResult = "Error : login failed on active socket!";
				return false;
				
			default:
				opResult = "Error : unknown error!";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"Error : communication error");
		}
		
		opResult = "Login succeed!";
		
		return true;
    }
    
    /**
     * return the command result
     * @return
     */
    public static String getOpResult (){
    	return opResult;
    }
    
    /**
     * add a contact to client by using his login 
     * @param login
     */
    public void setContact(String login){
    	clientId++;
    	contacts.addContact(new ModelContact(clientId,login));
    }
    
    
    /**
     * return the contacts names of owner client 
     * @return
     */
    public String [] retreiveContactList(){
    	
  
    	Container containerReq = new Container (new ContactListRequest(),login);
    	try{
    		oos_active.writeObject(containerReq);
    	}catch (IOException e){
    		e.printStackTrace();
    	}
    	Container containerResp = new Container(new ContactListResponse(), login);
    	try{
    		containerResp = (Container)ois_active.readObject();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	Stream stream = containerResp.getStream();
    	
    	Object[] objs= ((ContactListResponse)containerResp.getStream()).getContactList();
    	//ModelContact [] modelContacts; = (ModelContact[])objs;
    	String [] contactsName = new String[objs.length];
    	for (int i=0; i<objs.length;i++){
    		
    		contactsName[i]= ((ModelContact)(objs[i])).getLogin();
    	}
    	return contactsName;
    }
    
    public void listenAndReceiveMsg(final String selectedContact, final Handler handler, final TextView messageTxt) throws Exception{
		new Thread(new Runnable(){
			public void run(){
				Container container;
				while(true){
					try {
						container = (Container)ClientChatOperation.ois_passive.readObject();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					Class<? extends Stream> streamClass = container.getStream().getClass();
					if(streamClass == Conversation.class){
							if(container.getLogin() != null && container.getLogin().compareTo(selectedContact) == 0){

						    	//Recover the message received
						    	msgRcvd = ((Conversation)container.getStream()).getConv().getMsg();		
						    	
						    	//display the message (back in the UI main thread, bacause you can't here : CalledFromWrongThreadException)
						    	handler.post(new Runnable() {
						            public void run() {
						            	messageTxt.append("\n\n");
						        		messageTxt.append(selectedContact + " : " +msgRcvd);
						            }
						        });
						}
					}
				}
			}
		}).start();
    }
    
    /**
     * 
     */
    public void sendMsg(String msg, String selectedContact) throws Exception{
    	
    	ModelConversationSimple conv = new ModelConversationSimple();
    	conv.setContactLogin(selectedContact);
    	conv.setOwnLogin(login);
    	conv.setMsg(msg);
    	Conversation conv_send = new Conversation(conv);
    	Container container = new Container(conv_send, login);
    	try {
    		//send message on active socket
    		ClientChatOperation.oos_active.writeObject(container);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public String getUserName(){
    	return this.login;
    }
    
    public String getMsgReceived(){ 
    	return msgRcvd == null?"":msgRcvd;
    	
    }
    

  public void OnDestroy(){

	try {

			ois_passive.close();
			oos_passive.close();
			s_passive.close();

			ois_active.close();
			oos_active.close();
			s_active.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

}