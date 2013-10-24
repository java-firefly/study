package test.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestSendEmail {
	public static void main(String[] args) throws AddressException, MessagingException {
		//LineInputStream
		String host = "smtp.qq.com";// "mail.sinoss.net"; 
        String from = "407844082@qq.com";// "xmsb2009@sinoss.net"; 
        String username = "407844082@qq.com";// "xmsb2009@sinoss.net"; 
        String password = "epluggerxin";// "sinoss4340"; 

        // Get system properties 
        Properties props = new Properties(); 

        // Setup mail server 
        props.put("mail.smtp.host", host); 
        props.put("mail.smtp.auth", "true"); //这样才能通过验证 

        // Get session 
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props); 

        // watch the mail commands go by to the mail server 
        session.setDebug(false); 

        // Define message 
        MimeMessage message = new MimeMessage(session); 
        message.setFrom(new InternetAddress(from)); 
        message.addRecipient(Message.RecipientType.TO,new InternetAddress("zhangzhiyong@e-plugger.com")); 
        message.setSubject("你好"); 
        message.setContent("你好", "text/html;charset=GBK");
        message.saveChanges(); 
        Transport transport = session.getTransport("smtp"); 
        transport.connect(host, username, password); 
        transport.sendMessage(message, message.getAllRecipients()); 
        transport.close(); 
	}
}
