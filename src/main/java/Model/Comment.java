package Model;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {

    private int postID ;
    private int userID ;
    private String message;
    private Timestamp time ;

    public Comment(int postID, int userID, String message, Timestamp time) {
        this.postID = postID;
        this.userID = userID;
        this.message = message;
        this.time = time;
    }

    public int getPostID() {
        return postID;
    }

    public int getWriterID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return postID == comment.postID && userID == comment.userID && Objects.equals(message, comment.message) && Objects.equals(time, comment.time);
    }

}
