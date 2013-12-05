package test.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestSendEmail {
	public static void main(String[] args) throws AddressException, MessagingException, InterruptedException {
		//LineInputStream
		String host = "smtp.163.com";// "mail.sinoss.net"; 
        String from = "java_firefly@163.com";// "xmsb2009@sinoss.net"; 
        String username = "java_firefly";// "xmsb2009@sinoss.net"; 
        String password = "lvliangjava";// "sinoss4340"; 
        
        // Get system properties 
        Properties props = new Properties(); 

        // Setup mail server 
        props.put("mail.smtp.host", host); 
        props.put("mail.smtp.auth", "true"); //这样才能通过验证 
        for(int j = 0; j < 30; j++){
        	for (int i = 0; i < 20; i++) {
        		String msg = j+"-"+i+"-----------------------------------";
           	 	// Get session 
				javax.mail.Session session = javax.mail.Session.getDefaultInstance(props); 
				// watch the mail commands go by to the mail server 
				session.setDebug(false); 
				 
				// Define message 
				MimeMessage message = new MimeMessage(session); 
				message.setFrom(new InternetAddress(from)); 
				message.addRecipient(Message.RecipientType.TO,new InternetAddress("zhangzhiyong@e-plugger.com")); 
				message.setSubject(msg); 
				message.setContent(msg, "text/html;charset=GBK");
				message.saveChanges(); 
				Transport transport = session.getTransport("smtp"); 
				System.out.println(msg);
				try {
	               	transport.connect(host, username, password);
	   			} catch (Exception e) {
	   				e.printStackTrace();
	   			}
				transport.sendMessage(message, message.getAllRecipients()); 
				transport.close(); 
		   	}
        }
	}
}
