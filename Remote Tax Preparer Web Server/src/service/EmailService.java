package service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exception.ConfigException;

public final class EmailService {

	private static String smtpHost;
	private static String smtpPort;
	private static String username;
	private static String password;
	private static String recoverySubject;
	private static String recoveryTemplatePath;
	private static boolean init;

	private static void init() throws ConfigException {
		if (!init)
		{
			smtpHost = ConfigService.fetchFromConfig("smtphost:");
			smtpPort = ConfigService.fetchFromConfig("smtpport:");
			username = ConfigService.fetchFromConfig("emailusername:");
			password = ConfigService.fetchFromConfig("emailpassword:");
			recoverySubject = ConfigService.fetchFromConfig("recoverysubject:");
			recoveryTemplatePath = ConfigService.fetchFromConfig("recoverytemplatepath:");
			init = true;
		}
	}

	public static void sendRecovery(String email) throws ConfigException, MessagingException {
		init();
		sendEmail(username, email, recoverySubject, ConfigService.fetchContents(recoveryTemplatePath));
	}

	private static void sendEmail(String from, String to, String subject, String message) throws MessagingException {
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
	}
}
