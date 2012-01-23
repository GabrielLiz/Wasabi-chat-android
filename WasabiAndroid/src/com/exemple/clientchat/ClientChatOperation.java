/**
 * @author Med_Regaya
 */
package com.exemple.clientchat;

import android.os.Debug;
import android.os.Handler;
import android.widget.TextView;

import com.exemple.clientchat.*;
import DataModel.*;
import DataTransport.*;

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
	
	
	
	//Our user informations returned by the server during auth
	private ModelContact owner;
	
	private String login;
	private String pass;
	//private static int clientId;
	private LinkedList<ModelContact> contacts;
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
	}
	
	/**
     * connect to server
     * @Exception: 
     * 
     */
    public static int connectToServer() throws Exception{
    	
		// On commence par chercher l'adresse du serveur
		try {
			address = InetAddress.getByName("www.mubedded.com");
		} catch (UnknownHostException e) {
			throw new ClientChatException(ClientChatException.HOST_INCONNU, 
										"ERROR: Server unreachable");
		}
		
		// On tente de se connecter au serveur
		try {
			s_active = new Socket(address, 3002);
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
    
		return 0;
    }//connectToServer
    
    /**
     * try to register the new user to the server
     */
    public boolean registerToServer() throws Exception{
    	
		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Registration avec comme params le login et le mot de pass
    	RegistrationRequest regRequest = new RegistrationRequest(login, pass);
		try {
			// On tente d'envoyer les données
			oos_active.writeObject(regRequest);
			// On tente de recevoir les données
			RegistrationResponse regResponse = (RegistrationResponse)ois_active.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
			switch(regResponse.getResponseType()){
			case REG_ALREADY_EXISTS:
				opResult = "Error : User already exist!";
				return false;
			case REG_LOGIN_BAD_FORMAT:
				opResult = "Error : login bad format!";
				return false;
			case REG_PASS_BAD_FORMAT:
				opResult = "Error : password bad format!";
				return false;
			case REG_SUCCESS:
				opResult = "Account registration succeed!";
				return true;
			case REG_UNKNOW_ERROR:
				opResult = "Error : Unknown error!";
				return false;
			}
		} catch (Exception e) {
			throw new ClientChatException (ClientChatException.IO_ERREUR, "ERROR: Communication Error");
		}
		return false;
    }//registerToServer()
	
    /**
     * Auth to server
     */
    public boolean authToServer() throws ClientChatException{

		// On crée un objet container pour contenir nos donnée
		// On y ajoute un AdminStream de type Authentification avec comme params le login et le mot de pass
    	AuthentificationRequest request = new AuthentificationRequest(login, pass);
		
		//Auth sur le port actif
		try {
			// On tente d'envoyer les données
			oos_active.writeObject(request);
			// On tente de recevoir les données
			AuthentificationResponse response = (AuthentificationResponse)ois_active.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
			switch(response.getResponseType()){
			case AUTH_SUCCESS:
				
				owner = response.getOwner();
				
				//creation du socket
				try {
					s_passive = new Socket(address, 3003);
				} catch (IOException e) {
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"ERROR : passive socket creation failed!");
					
				}
				
				// On crée le flux sortant !!!OBLIGATOIRE de créer le flux sortant avant le flux entrant!!!
				try {
					oos_passive = new ObjectOutputStream(s_passive.getOutputStream());
				} catch (IOException e) {
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"ERROR : passive OOS creation failed!");
				}
				// On crée le flux entrant
				try {
					ois_passive = new ObjectInputStream(s_passive.getInputStream());
				} catch (Exception e) {
					throw new ClientChatException(ClientChatException.IO_ERREUR, 
					"ERROR : passive OIS creation failed!");
				}
				
				request = new AuthentificationRequest(owner);			
				//Auth sur le port passif
				try {
					// On tente d'envoyer les données
					oos_passive.writeObject(request);
					// On tente de recevoir les données
					response = (AuthentificationResponse)ois_passive.readObject();

					// On switch sur le type de retour que nous renvoie le serveur
					// !!!Le type enum "Value" est sujet à modification et va grandir en fonction de l'avance du projet!!!
					switch(response.getResponseType()){
					case AUTH_SUCCESS:
						opResult = "Welcome!";
						return true;
					case AUTH_FAIL:
						opResult = "ERROR : check login/pwd! (passive socket)";
						return false;
					}
				} catch(Exception e){
					throw new ClientChatException (ClientChatException.IO_ERREUR, "ERROR: Communication Error (passive)");
				}
			
			case AUTH_FAIL:
				opResult = "ERROR : check login/pwd! (active socket)";
				return false;

			}
		} catch (Exception e) {
			throw new ClientChatException (ClientChatException.IO_ERREUR, "ERROR: Communication Error (active)");
		}
		return true;
    }//authToServer()
    
    /**
     * return the command result
     * @return
     */
    public static String getOpResult (){
    	return opResult;
    }
    
//    /**
//     * add a contact to client by using his login 
//     * @param login
//     */
//    public void setContact(String login){
//    	clientId++;
//    	contacts.addContact(new ModelContact(clientId,login));
//    }
    
    
    /**
     * return the contacts names of owner client 
     * @return
     * @throws ClientChatException 
     */
    public void retreiveContactList() throws ClientChatException{
    	
    	ContactListRequest cListRequest = new ContactListRequest(owner);
    	ContactListResponse cListResponse;
    	try{
    		oos_active.writeObject(cListRequest);
    		cListResponse = (ContactListResponse)ois_active.readObject();
    	}catch (Exception e){
    		throw new ClientChatException (ClientChatException.IO_ERREUR, "ERROR: Communication Error");
    	}
    	
    	contacts = cListResponse.getContactList(); 
    	contacts.remove(owner); //Avoid to display current user in the list
    	
    	//String [] contactsName = new String[cListResponse.getContactList().size()];
    	//int i = 0;
    	
    	//Store locally the contact list
//    	for(ModelContact contact:  cListResponse.getContactList()){
//    		contactsName[i]= contact.getLogin();
//    		i++;
//    	}
    }
    
//    public void listenAndReceiveMsg(final String selectedContact, final Hander handler) throws Exception{
//		new Thread(new Runnable(){
//			public void run(){
//				Container container;
//				while(true){
//					try {
//						container = (Container)ClientChatOperation.ois_passive.readObject();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return;
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return;
//					}
//					Class<? extends Stream> streamClass = container.getStream().getClass();
//					if(streamClass == Conversation.class){
//							if(container.getLogin() != null && container.getLogin().compareTo(selectedContact) == 0){
//
//						    	//Recover the message received
//						    	msgRcvd = ((Conversation)container.getStream()).getConv().getMsg();	
//  
//								//display the message (back in the UI main thread, because you can't here : CalledFromWrongThreadException)
//								handler.post(new Runnable() {
//							        public void run() {
//							        	TextView messageTxt = (TextView)findViewById(R.id.tchatLbl);
//							        	messageTxt.append("\n");
//							    		messageTxt.append(selectedContact + " : " +clientOp.getMsgReceived());
//							        }
//							    });
//						}
//					}
//				}
//			}
//		}).start();
//    }
    
//    /**
//     * 
//     */
//    public void sendMsg(String msg, String selectedContact) throws Exception{
//    	
//    	ModelConversationSimple conv = new ModelConversationSimple();
//    	conv.setContactLogin(selectedContact);
//    	conv.setMsg(msg);
//    	Conversation conv_send = new Conversation(conv);
//    	Container container = new Container(conv_send, login);
//    	try {
//    		//send message on active socket
//    		ClientChatOperation.oos_active.writeObject(container);
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
//    	
//    }
    
    public String getUserName(){
    	return this.login;
    }
    
    public String getMsgReceived(){ 
    	return msgRcvd == null?"":msgRcvd;
    	
    }
    
    public LinkedList<ModelContact> getContacts() {
		return contacts;
	}


	public void OnDestroy(){
    	
    	try {
			ois_passive.close();
	    	oos_passive.close();
	    	s_passive.close();
	    	
	    	ois_active.close();
	    	oos_active.close();
	    	s_active.close();
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}