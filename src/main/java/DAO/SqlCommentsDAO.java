package DAO;

import DAO.Interfaces.CommentsDAO;
import Model.Comment;
import Model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCommentsDAO implements CommentsDAO {
    ConnectionPool pool;

    public SqlCommentsDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public int addComment(Comment comment) {
        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO POST_COMMENTS (PostID, UserID, Message) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getPostID());
            ps.setInt(2, comment.getWriterID());
            ps.setString(3, comment.getMessage());
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return keys.getInt(1);
            }
            return -1;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Comment> getCommentsByPostID(int PostID, boolean asc) {
        Connection conn = pool.getConnection();
        List<Comment> result = new ArrayList<>();
        try{
            String statement = "SELECT * FROM POST_COMMENTS WHERE PostID = ? ORDER BY PostDate " + (asc ? "asc" : "desc") + ";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, PostID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                result.add(new Comment(rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5)));
            }

            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally{
            pool.releaseConnection(conn);
        }
    }
}
