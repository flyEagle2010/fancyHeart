package com.doteyplay.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils
{

	public String SMTP_HOST = "mail.palmlink.com.cn";
	public String SMTP_USER = "misstime";
	public String SMTP_PASSWORD = "11111";

	private String senderAccount;
	private String toAccount;
	private String subject;
	private String content;

	public MailUtils(String toAccount, String subject, String content, String senderAccount, String smtpHost,
			String smtpUser, String password)
	{
		this.toAccount = toAccount;
		this.subject = subject;
		this.content = content;
		this.senderAccount = senderAccount;
		this.SMTP_HOST = smtpHost;
		this.SMTP_USER = smtpUser;
		this.SMTP_PASSWORD = password;
	}

	public String getToAccount()
	{
		return toAccount;
	}

	public void setToAccount(String account)
	{
		this.toAccount = account;
	}

	public String getSenderAccount()
	{
		return senderAccount;
	}

	public void setSenderAccount(String senderAccount)
	{
		this.senderAccount = senderAccount;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void sendMail()
	{
		try
		{
			Properties props = new Properties();
			Session sendMailSession;
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", SMTP_HOST);
			Authenticator auth = new PopupAuthenticator(SMTP_USER, SMTP_PASSWORD);
			sendMailSession = Session.getInstance(props, auth);
			Message newMessage = new MimeMessage(sendMailSession);
			newMessage.setFrom(new InternetAddress(senderAccount));
			newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAccount));
			newMessage.setSubject(subject);
			newMessage.setSentDate(new Date());
			newMessage.setText(content);
			Transport transport = sendMailSession.getTransport("smtp");
			transport.connect(SMTP_HOST, SMTP_USER, SMTP_PASSWORD);
			newMessage.saveChanges();
			transport.send(newMessage, newMessage.getRecipients(Message.RecipientType.TO));
			transport.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void sendMailHtml()
	{
		try
		{
			Properties props = new Properties();
			Session sendMailSession;
			MimeMultipart mp = new MimeMultipart();
			Transport transport;
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", SMTP_HOST);
			Authenticator auth = new PopupAuthenticator(SMTP_USER, SMTP_PASSWORD);
			sendMailSession = Session.getInstance(props, auth);
			Message newMessage = new MimeMessage(sendMailSession);
			newMessage.setFrom(new InternetAddress(senderAccount));
			newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAccount));
			newMessage.setSubject(subject);
			newMessage.setSentDate(new Date());
			newMessage.setText(content);

			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.setContent(content, "text/html; charset=GB2312");
			mp.addBodyPart(mbp2);
			newMessage.setContent(mp);
			transport = sendMailSession.getTransport("smtp");
			transport.connect(SMTP_HOST, SMTP_USER, SMTP_PASSWORD);
			newMessage.saveChanges();
			transport.send(newMessage, newMessage.getRecipients(Message.RecipientType.TO));
			transport.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
//		MailUtils mail = new MailUtils("misstime@palmlink.com.cn", "test", "testtesttest",
//				"misstime@palmlink.com.cn","mail.palmlink.com.cn", "misstime", "11111");
//		mail.sendMail();
		

		MailUtils mail = new MailUtils("palmlink@126.com", "test", "testtesttest",
				"palmlink@126.com","smtp.126.com", "palmlink", "plstudio");
		mail.sendMail();
	}

}
