/**
 * @author Med_Regaya
 */

package com.exemple.clientchat;

public class ClientChatException extends Exception{
	
	public static final int HOST_INCONNU = 0;
	public static final int IO_ERREUR = 1;
	public static final int PASSIVE_SOCKET_ERREUR = 2;
	public static final int INCONNUE_ERREUR = 3;
	
	
	private int raison; // raison de l'exception
	private String msg; // message de l'exception
	
	public ClientChatException (int raison, String msg){
		this.raison = raison;
		this.msg = msg;
	}
	/**
	 * retourne la cause de l'exception
	 * @return
	 */
	public int getRaison (){
		return raison;
	}
	
	/**
	 * retourne le message d'erreur de l'exception
	 */
	public String getMsg(){
		return msg;
	}
	
}