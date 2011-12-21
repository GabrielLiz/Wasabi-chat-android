import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import DataTransport.Container;
import DataTransport.Registration;
import DataTransport.Status;


public class Main {

	private static Socket s;				// Socket client
	private static ObjectInputStream ois;	// Flux d'entr� du socket
	private static ObjectOutputStream oos;	// Flux de sortie du socket
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// On commence par chercher l'adresse du serveur
		InetAddress address = null;
		System.out.print(String.format("%-60s", "Recherche du serveur"));
		try {
			address = InetAddress.getByName("www.mubedded.com");
			//address = InetAddress.getByName("localhost");
			System.out.println("[done]");
		} catch (UnknownHostException e) {
			System.out.println("[failed]");
			return;
		}
		
		// On tente de se connecter au serveur
		System.out.print(String.format("%-60s", "Cr�ation d'un socket avec le serveur"));
		try {
			s = new Socket(address, 3000);
			System.out.println("[done]");
		} catch (IOException e) {
			System.out.println("[failed]");
			return;
		}
		
		// On cr�e le flux sortant !!!OBLIGATOIRE de cr�er le flux sortant avant le flux entrant!!!
		System.out.print(String.format("%-60s", "Cr�ation du flux sortant"));
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			System.out.println("[done]");
		} catch (IOException e) {
			System.out.println("[failed]");
			return;
		}
		// On cr�e le flux entrant
		System.out.print(String.format("%-60s", "Cr�ation du flux entrant"));
		try {
			ois = new ObjectInputStream(s.getInputStream());
			System.out.println("[done]");
		} catch (Exception e) {
			System.out.println("[failed]");
			return;
		}
		
		String login = "jacky";
		String password = "prout";
		
		// Example de registration avec le login <login> et le mot de pass <password>
		System.out.print(String.format("%-60s", "Cr�ation d'un nouveau compte " + "<" + login + ":" + password + ">"));
		// On cr�e un objet container pour contenir nos donn�e
		// On y ajoute un AdminStream de type Registration avec comme params le login et le mot de pass
		Container container = new Container(new Registration(login, password));
		try {
			// On tente d'envoyer les donn�es
			oos.writeObject(container);
			// On tente de recevoir les donn�es
			Status status = (Status)ois.readObject();

			// On switch sur le type de retour que nous renvoie le serveur
			// !!!Le type enum "Value" est sujet � modification et va grandir en fonction de l'avance du projet!!!
			switch(status.getValue()){
			case OK:
				System.out.println("[done]");
				break;
			case CONTACT_ALREADY_EXISTS:
				System.out.println("[failed]");
				System.out.println(" => error: le contact existe d�j�");
				break;
			case CONTACT_CREATION_FAILED:
				System.out.println("[failed]");
				System.out.println(" => error: la cr�ation du contact � �chou�e");
				break;
			default:
				System.out.println("[failed]");
				System.out.println(" => error: FU!");
				break;
			}
		} catch (Exception e) {
			System.out.println("[failed]");
		}
		
		try {
			// On tente de fermer les flux et le socket
			oos.close();
			ois.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("End");
		
	}

}
