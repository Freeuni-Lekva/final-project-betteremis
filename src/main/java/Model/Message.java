package Model;

import java.util.Date;

public class Message {

    private String senderEmail;
    private String receiverEmail;
    private String message;
    private Date date;

    public Message(String sender, String receiver, String message, Date date) {
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
}
