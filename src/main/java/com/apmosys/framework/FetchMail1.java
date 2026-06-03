// 
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.jsoup.Jsoup;

public class FetchMail1
{
    private static final String[] strArray;
    
    
    
    private static String getTextFromMessage(final Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        }
        else if (message.isMimeType("multipart/*")) {
            final MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }
    
    private static String getTextFromMimeMultipart(final MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        for (int count = mimeMultipart.getCount(), i = 0; i < count; ++i) {
            final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = String.valueOf(String.valueOf(String.valueOf(String.valueOf(result)))) + "\n" + bodyPart.getContent();
                break;
            }
            if (bodyPart.isMimeType("text/html")) {
                final String html = (String)bodyPart.getContent();
                result = String.valueOf(String.valueOf(String.valueOf(String.valueOf(result)))) + "\n" + Jsoup.parse(html).text();
            }
            else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = String.valueOf(String.valueOf(String.valueOf(String.valueOf(result)))) + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
    
    public static String getOtp(final String host, final String username, final String password) {
        String otp = null;
        try {
            System.out.println("In otp read ");
            final Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            final Session session = Session.getInstance(props, (Authenticator)null);
            final Store store = session.getStore();
            store.connect(host, username, password);
            System.out.println("My mail.....................");
            final Folder folder = store.getFolder("INBOX");
            if (!folder.exists()) {
                System.out.println("inbox not found");
                System.exit(0);
            }
            folder.open(2);
            final SearchTerm st1 = (SearchTerm)new SubjectTerm("OTP for verification");
            final Message[] message = folder.search(st1);
            for (int j = 0; j < message.length - 1; ++j) {
                System.out.println("Inside delete mail");
                message[j].setFlag(Flags.Flag.DELETED, true);
                System.out.println("Old Messages Deleted .....");
            }
            for (int i = 0, n = message.length; i < n; ++i) {
                if (message[i].equals("OTP for verification")) {
                    System.out.println("Inside if condition getting message");
                    if (i == n - 1) {
                        final String body = getTextFromMessage(message[n - 1]);
                        System.out.println(body);
                        final String[] strArray = body.split(" ");
                        System.out.println(String.valueOf(String.valueOf(String.valueOf(String.valueOf(i)))) + " : Mail Message body :" + body);
                        for (int i2 = 0; i2 < strArray.length; ++i2) {
                            if (i2 == 2) {
                                otp = strArray[2];
                            }
                        }
                    }
                }
            }
            folder.close(false);
            store.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return otp;
    }
    
    static {
        strArray = null;
    }
}
