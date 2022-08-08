package Model;

import java.sql.Timestamp;
import java.util.Date;

public class Message implements Comparable{

    private String senderEmail;
    private String receiverEmail;
    private String message;
    private Timestamp date;

    public Message(String sender, String receiver, String message, Timestamp date) {
        this.senderEmail = sender;
        this.receiverEmail = receiver;
        this.message = message;
        this.date = date;
    }

    public String getSender(){
        return this.senderEmail;
    }

    public String getReceiver(){
        return this.receiverEmail;
    }

    public String getMessage(){
        return this.message;
    }

    public Date getDate(){
        return this.date;
    }

    @Override
    public int compareTo(Object o) {
        Message other = (Message) o;
        return this.date.compareTo(other.date);
    }
}
