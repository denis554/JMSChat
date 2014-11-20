package tgm.geyerritter.dezsys06.net;

import java.util.List;

import javax.jms.JMSException;

import tgm.geyerritter.dezsys06.data.Configuration;
import tgm.geyerritter.dezsys06.data.MessageData;

/**
 * Diese Klasse dient dazu, die von anderen Klassen bereitgestellten Daten
 * an den Server zu senden, bzw. Daten vom Server abzufragen
 * 
 * @author sgeyer, mritter
 * @version 1.0
 */
public interface NetworkController {

	/**
	 * Baut eine Verbindung zum ActiveMQ Server in dem ein Rciever und eine Sender
	 * bereitgestellt werden.
	 * 
	 * @param conf Die Einstellungen unter denen der Server zu erreichen ist
	 */
	public void init(Configuration conf) throws JMSException;
	
	/**
	 * Stopt die Komunikation mit dem Server und beendet die vom Server abhaengigen Transfere
	 */
	public void halt() throws JMSException;
	
	/**
	 * Sendet eine Nachricht als Topic an den Server damit alle anderen Clients die
	 * Nachricht empfangen koennen.
	 * 
	 * @param message Die Nachricht die an alle Clients gesendet werden soll
	 */
	public void broadcast(String message);
	
	/**
	 * Sendet eine Nachricht als Queue an den Server. Der Client fuer den die Nachricht vorgesehen
	 * ist kann diese jederzeit empfangen.
	 * 
	 * @param reciever Der User der das Mail empfangen soll
	 * @param message Die Nachricht die der Empfaenger empfangen soll
	 */
	public void mail(String reciever, String message);
	
	/**
	 * Laedt alle an den User gerichteten Mails vom Server 
	 * 
	 * @return Alle fuer den User vorgesehenen Mails
	 */
	public List<MessageData> getMails();
}
