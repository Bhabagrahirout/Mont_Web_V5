// 
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestMail
{
    public static String homedir;
    public static String smail_username;
    public static String smail_password;
    public static String ssmtphost;
    public static String ssmtp_auth;
    public static String sstart_TLS;
    public static String sfrom_mail_id;
    public static String sto_mail_id;
    public static String smail_subject;
    public static String smail_body;
    public static String ismtpport;
    public static Fillo fillo;
    public static Connection conn;
    public static Recordset rs;
    
    public static void main(final String[] args) throws URISyntaxException, FilloException {
        final CodeSource codeSource = TestMail.class.getProtectionDomain().getCodeSource();
        final File jarFile = new File(codeSource.getLocation().toURI().getPath());
        TestMail.homedir = jarFile.getParentFile().getPath();
        Sendmail("", "");
    }
    
    public static void Sendmail(final String sub, final String mesg) throws FilloException {
        TestMail.fillo = new Fillo();
        final String DataSheet = String.valueOf(String.valueOf(Framework.homedir)) + "/DataSheet/Mail_Data.xlsx";
        TestMail.conn = TestMail.fillo.getConnection(DataSheet);
        TestMail.rs = TestMail.conn.executeQuery("Select * from Sheet1");
        while (TestMail.rs.next()) {
            TestMail.smail_username = TestMail.rs.getField("mail_username");
            TestMail.smail_password = TestMail.rs.getField("mail_password");
            TestMail.ssmtphost = TestMail.rs.getField("smtp_host");
            TestMail.ismtpport = TestMail.rs.getField("smtp_port");
            TestMail.ssmtp_auth = TestMail.rs.getField("smtp_auth");
            TestMail.sstart_TLS = TestMail.rs.getField("start_TLS");
            TestMail.sfrom_mail_id = TestMail.rs.getField("from_mail_id");
            TestMail.sto_mail_id = TestMail.rs.getField("to_mail_id");
            final String username = TestMail.smail_username;
            final String password = TestMail.smail_password;
            final String subject = "IMPS and NEFT status : " + sub;
            final String text = "Please find IMPS and NEFT status :<br/><br/>" + Functions.mailTime + "<br/><br/>" + mesg;
            final String Finalmessage = "Dear Sir/Ma'am,<br/><br/> " + text;
            final String signature = "<br/><br/>Thanks and Regards<br/> Performance Monitoring Team | ApMoSys <br> Desk No.022 41222250 <br/> Email : apmosys@apmosys.in | www.apmosys.in";
            final Properties prop = new Properties();
            prop.put("mail.smtp.host", TestMail.ssmtphost);
            prop.put("mail.smtp.port", TestMail.ismtpport);
            prop.put("mail.smtp.auth", TestMail.ssmtp_auth);
            prop.put("mail.smtp.starttls.enable", TestMail.sstart_TLS);
            final Session session = Session.getInstance(prop, (Authenticator)new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {
                final Message message = (Message)new MimeMessage(session);
                message.setFrom((Address)new InternetAddress(TestMail.sfrom_mail_id));
                message.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(TestMail.sto_mail_id));
                message.setSubject(subject);
                final BodyPart messageBodyPart = (BodyPart)new MimeBodyPart();
                messageBodyPart.setContent((Object)(String.valueOf(String.valueOf(String.valueOf(Finalmessage))) + "<br/><br/>Thanks and Regards<br/> Performance Monitoring Team | ApMoSys <br> Desk No.022 41222250 <br/> Email : apmosys@apmosys.in | www.apmosys.in"), "text/html");
                final Multipart multipart = (Multipart)new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                System.out.println("Done");
            }
            catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void Sendmail1(final String sub, final String mesg) throws FilloException {
        TestMail.fillo = new Fillo();
        final String DataSheet = String.valueOf(String.valueOf(Framework.homedir)) + "/DataSheet/Mail_Data1.xlsx";
        TestMail.conn = TestMail.fillo.getConnection(DataSheet);
        TestMail.rs = TestMail.conn.executeQuery("Select * from Sheet1");
        while (TestMail.rs.next()) {
            TestMail.smail_username = TestMail.rs.getField("mail_username");
            TestMail.smail_password = TestMail.rs.getField("mail_password");
            TestMail.ssmtphost = TestMail.rs.getField("smtp_host");
            TestMail.ismtpport = TestMail.rs.getField("smtp_port");
            TestMail.ssmtp_auth = TestMail.rs.getField("smtp_auth");
            TestMail.sstart_TLS = TestMail.rs.getField("start_TLS");
            TestMail.sfrom_mail_id = TestMail.rs.getField("from_mail_id");
            TestMail.sto_mail_id = TestMail.rs.getField("to_mail_id");
            final String username = TestMail.smail_username;
            final String password = TestMail.smail_password;
            final String subject = "AIRPAY and INSTANT status : " + sub;
            final String text = "Please find AIRPAY and INSTANT status :<br/><br/>" + Functions.mailTime + "<br/><br/>" + mesg;
            final String Finalmessage = "Dear Sir/Ma'am,<br/><br/> " + text;
            final String signature = "<br/><br/>Thanks and Regards<br/> Performance Monitoring Team | ApMoSys <br> Desk No.022 41222250 <br/> Email : apmosys@apmosys.in | www.apmosys.in";
            final Properties prop = new Properties();
            prop.put("mail.smtp.host", TestMail.ssmtphost);
            prop.put("mail.smtp.port", TestMail.ismtpport);
            prop.put("mail.smtp.auth", TestMail.ssmtp_auth);
            prop.put("mail.smtp.starttls.enable", TestMail.sstart_TLS);
            final Session session = Session.getInstance(prop, (Authenticator)new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {
                final Message message = (Message)new MimeMessage(session);
                message.setFrom((Address)new InternetAddress(TestMail.sfrom_mail_id));
                message.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(TestMail.sto_mail_id));
                message.setSubject(subject);
                final BodyPart messageBodyPart = (BodyPart)new MimeBodyPart();
                messageBodyPart.setContent((Object)(String.valueOf(String.valueOf(String.valueOf(Finalmessage))) + "<br/><br/>Thanks and Regards<br/> Performance Monitoring Team | ApMoSys <br> Desk No.022 41222250 <br/> Email : apmosys@apmosys.in | www.apmosys.in"), "text/html");
                final Multipart multipart = (Multipart)new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                System.out.println("Done");
            }
            catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    static {
        TestMail.homedir = "";
        TestMail.fillo = null;
        TestMail.conn = null;
        TestMail.rs = null;
    }
}
