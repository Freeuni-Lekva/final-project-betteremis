package Model;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {

    private int classroomID ;
    private int userID ;
    private String postContent ;
    private Timestamp time ;

    public Post(int classroomID, int userID, String postContent, Timestamp time) {
        this.classroomID = classroomID;
        this.userID = userID;
        this.postContent = postContent;
        this.time = time;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public int getUserID() {
        return userID;
    }

    public String getPostContent() {
        return postContent;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return classroomID == post.classroomID && userID == post.userID && Objects.equals(postContent, post.postContent) && Objects.equals(time, post.time);
    }

}
