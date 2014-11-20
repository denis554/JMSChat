package tgm.geyerritter.dezsys06;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import tgm.geyerritter.dezsys06.io.ChatConsoleReader;

/**
 * Main-Klasse mit Main-Methode zum Start des Chats
 * 
 * @author mritter
 * @version sgeyer
 */
public class Main {

	/**
	 * Main-Methode zum Start des Chats
	 * 
	 * @param args
	 *            Kommandozeilenargumente
	 */
	public static void main(String[] args) {

		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "[%d{HH:mm:ss}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		// add appender to any Logger (here is root)
		BasicConfigurator.configure(console);

		// Chat in einem neuen Thread starten
		new Thread(new ChatConsoleReader()).start();
	}

}
