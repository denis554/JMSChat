package tgm.geyerritter.dezsys06.net;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import tgm.geyerritter.dezsys06.data.Configuration;
import tgm.geyerritter.dezsys06.data.MessageData;
import tgm.geyerritter.dezsys06.data.StaticConfiguration;

/**
 * 
 * Implementierung von {@link Controller} wobei das Networking von ActiveMQ benutzt wird
 * 
 * @author sgeyer, mritter
 * @version 1.0
 */
public class Networking implements NetworkController {
	
	private String username;
	
	private Session session;
	private Connection connection;
	private MessageConsumer consumer;
	private MessageProducer producer;
	private Destination destination;
	
	private Receiver reciever;
	private Sender sender;
	
	public Networking(String username) throws JMSException {
		init(new StaticConfiguration());
		
		this.username = username;
		
		this.reciever = new ChatReceiver();
		this.sender = new ChatSender(this.session, this.destination, this.producer);
	}
	
	/**
	 * @see NetworkController#init(Configuration)
	 */
	public void init(Configuration conf) throws JMSException {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(conf.getUser(), conf.getPassword(), conf.getHostAddress());
			connection = connectionFactory.createConnection();
			connection.start();

			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic(conf.getSystemName());

			// Create the consumer
			consumer = session.createConsumer(destination);

			// Create a producer
			producer = session.createProducer(destination);
	}
	
	/**
	 * @see NetworkController#halt()
	 */
	public void halt() throws JMSException {
		this.consumer.close();
		this.session.close();
		
		this.connection.close();
	}

	/**
	 * @see NetworkController#broadcast(String)
	 */
	public void broadcast(String message) {
		try {
			this.sender.broadcast(this.username, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see NetworkController#mail(String, String)
	 */
	public void mail(String reciever, String message) {
		try {
			this.sender.mail(this.username, reciever, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see NetworkController#getMails()
	 */
	public List<MessageData> getMails() {
		return null;
	}

}
