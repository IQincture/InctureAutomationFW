package com.incture.utility;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;

public class JavaRecieveMail {

   public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

      //create properties field
      Properties properties = new Properties();

      properties.put("mail.imap.host", host);
      properties.put("mail.imap.port", "995");
      properties.put("mail.imap.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("imaps");

      store.connect(host, user, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_WRITE);
    

      // retrieve the messages from the folder in an array and print it
     
  
    Flags seen = new Flags(Flags.Flag.SEEN);
   SearchTerm unseenFlagTerm = new FlagTerm(seen, false);
   
 FromStringTerm ft=new FromStringTerm("satish.a@incture.com");
 unseenFlagTerm=new AndTerm(unseenFlagTerm, ft);
    Message[]  messages = emailFolder.search(unseenFlagTerm);
    
  
      System.out.println("messages.length---" + messages.length);

      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i + 1));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
         System.out.println("Text: " + message.getContent().toString());

      }

      //close the store and folder objects
      emailFolder.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      String host = "outlook.office365.com";// change accordingly
      String mailStoreType = "imap";
      String username = "satish.a@incture.com";// change accordingly
      String password = "Incture#123";// change accordingly

      check(host, mailStoreType, username, password);

   }

}
