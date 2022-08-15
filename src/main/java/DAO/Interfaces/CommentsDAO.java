package DAO.Interfaces;

import Model.Comment;
import Model.Post;

import java.util.List;

public interface CommentsDAO {


    /**
     * This method adds a comment into a table.
     * @param comment post to be added.
     * @return -1 if comment couldn't be added, ID with which post was added into a table otherwise.
     */
    int addComment(Comment comment);

    /**
     * This method returns List of comments on a given classroom.
     * @param PostID Post ID
     * @return List of comments on a given classroom.
     */
    List<Comment> getCommentsByPostID(int PostID);

}
