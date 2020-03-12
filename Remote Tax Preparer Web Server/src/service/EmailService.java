package service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exception.ConfigException;

/**
 * A set of methods for sending emails.
 * 
 * @author Jesse Goerzen
 */
public final class EmailService {
	/**
	 * The host domain or ip address of the smtp server.
	 */
	private static String smtpHost;
	/**
	 * The port of the smtp server.
	 */
	private static String smtpPort;
	/**
	 * The email address to send emails from.
	 */
	private static String username;
	/**
	 * The password for the account to send emails from.
	 */
	private static String password;
	/**
	 * The subject line for password recovery emails.
	 */
	private static String recoverySubject;
	/**
	 * The filepath to the html template for the body of password recovery emails.
	 */
	private static String recoveryTemplatePath;
	/**
	 * The subject line for account registry.
	 */
	private static String registerSubject;
	/**
	 * The filepath to the html template for the body account registry emails.
	 */
	private static String registerTemplatePath;
	/**
	 * Whether the static attributes in this class are initialized from config or not.
	 */
	private static boolean init;
	
	/**
	 * Initializes the static attributes in this class from config.
	 * 
	 * @throws ConfigException if the config file cannot be found.
	 */
	private static void init() throws ConfigException {
		if (!init)
		{
			smtpHost = ConfigService.fetchFromConfig("smtphost:");
			smtpPort = ConfigService.fetchFromConfig("smtpport:");
			username = ConfigService.fetchContents(ConfigService.fetchFromConfig("emailusernamepath:"));
			password = ConfigService.fetchContents(ConfigService.fetchFromConfig("emailpasswordpath:"));
			recoverySubject = ConfigService.fetchFromConfig("recoverysubject:");
			recoveryTemplatePath = ConfigService.fetchFromConfig("recoverytemplatepath:");
			registerSubject = ConfigService.fetchFromConfig("registersubject:");
			registerTemplatePath = ConfigService.fetchFromConfig("registertemplatepath:");
			init = true;
		}
	}
	
	/**
	 * Sends an email to a user that is trying to recover their lost password.
	 * 
	 * @param email <code>String</code> The email address to send the letter to.
	 * @param verificationID <code>String</code> The UUID to identify whose password to recover.
	 * @return true if the email was successfully sent.
	 * @throws ConfigException if this class was not initialized and the config file cannot be found.
	 * @throws MessagingException if the email was not sent.
	 */
	public static boolean sendRecovery(String email, UUID verificationID) throws ConfigException, MessagingException {
		init();
		String content = ConfigService.fetchContents(recoveryTemplatePath);
		content = content.replaceAll("\\[verification_id\\]", verificationID.toString());
		return sendEmail(username, email, recoverySubject, content);
	}
	
	/**
	 * Sends an email.
	 * 
	 * @param from <code>String</code> The email address to send the email from.
	 * @param to <code>String</code> The email address to send the email to.
	 * @param subject <code>String</code> The subject line for the email.
	 * @param message <code>String</code> The contents of the email.
	 * @return true if the email was successfully sent.
	 * @throws MessagingException if the email was not sent.
	 */
	private static boolean sendEmail(String from, String to, String subject, String message) throws MessagingException {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", smtpPort);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(properties);
		session.setDebug(true);

		Message email = new MimeMessage(session);
		email.setFrom(new InternetAddress(from));
		email.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		email.setContent(message, "text/html");
		email.setSubject(subject);

		Transport transport = session.getTransport("smtp");
		transport.connect(smtpHost, username, password);
		transport.sendMessage(email, email.getAllRecipients());
		
		return true;
	}
	
	/**
	 * Sends an email to a user that is trying to register a new user.
	 * 
	 * @param email <code>String</code> The email address to send the letter to.
	 * @param verificationID <code>String</code> The UUID to identify which account to activate.
	 * @return true if the email was successfully sent.
	 * @throws ConfigException if this class was not initialized and the config file cannot be found.
	 * @throws MessagingException if the email was not sent.
	 */
	public static boolean sendRegisterEmail(String email, UUID verificationID) throws ConfigException, MessagingException {
		init();
		String content = ConfigService.fetchContents(registerTemplatePath);
		content = content.replaceAll("\\[verification_id\\]", verificationID.toString());
		return sendEmail(username, email, registerSubject, content);
	}
}
