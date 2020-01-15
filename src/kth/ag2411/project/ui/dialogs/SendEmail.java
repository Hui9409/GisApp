package kth.ag2411.project.ui.dialogs;


import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendEmail
{
 
    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    public String receiver;
    public String user;
    
    public SendEmail(String receiver, String user) {
    	this.receiver = receiver;
    	this.user = user;  
    	
    }
    
    public void run() {
        // TODO Auto-generated method stub
        Properties prop = new Properties();
        prop.put("mail.host", "smtp.gmail.com");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");
        try {

            Session session = Session.getInstance(prop);
            session.setDebug(true);

            Transport ts;
            ts = session.getTransport();
            ts.connect("kringle2411@gmail.com", "btydqcnkwfdbhzcq");///
            Message msg = createSimpleMail(session);
            ts.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public MimeMessage createSimpleMail(Session session)
            throws AddressException, MessagingException, UnsupportedEncodingException {
        MimeMessage mm = new MimeMessage(session);

        mm.setFrom(new InternetAddress("kringle2411@gmail.com","Kringle Developer","UTF-8"));
        
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(
               receiver));

        String contents = "Dear "+ user +",<br> Welcome to the Kringle World and please enjoy it,<br> Expire date: 12/31-2099";
        mm.setSubject("Thanks for registering Kringle");
        mm.setContent(contents, "text/html;charset=utf-8");

        return mm;
    }

 


}