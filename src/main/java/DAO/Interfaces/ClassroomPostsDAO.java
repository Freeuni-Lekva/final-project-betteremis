package DAO.Interfaces;

import Model.Post;

import java.util.List;

public interface ClassroomPostsDAO {


    /**
     * This method adds a post into a table.
     * @param post post to be added.
     * @return -1 if post couldn't be added, ID with which post was added into a table otherwise.
     */
    int addPost(Post post);

    /**
     * This method returns List of posts on a given classroom.
     * @param ClassroomID Classroom ID
     * @param asc boolean which indicates the order of the posts (ordered by date). If true date will be ascending, if false date will be descending.
     * @return List of posts on a given classroom. If there were no posts emtpy List is returned. If some kind of exception happened null will be returned.
     */
    List<Post> getPostsByClassroomID(int ClassroomID, boolean asc);

}
