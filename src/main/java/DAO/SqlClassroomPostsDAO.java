package DAO;

import DAO.Interfaces.ClassroomPostsDAO;
import Model.Post;
import org.apache.ibatis.io.ResolverUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlClassroomPostsDAO implements ClassroomPostsDAO {
    ConnectionPool pool;

    public SqlClassroomPostsDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public int addPost(Post post) {
        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO CLASSROOM_POSTS (ClassroomID, UserID, Message) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, post.getClassroomID());
            ps.setInt(2, post.getUserID());
            ps.setString(3, post.getPostContent());
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
    public List<Post> getPostsByClassroomID(int ClassroomID, boolean asc) {
        Connection conn = pool.getConnection();
        List<Post> result = new ArrayList<>();
        try{
            String statement = "SELECT * FROM CLASSROOM_POSTS WHERE ClassroomID = ? ORDER BY PostDate " + (asc ? "asc" : "desc") + ";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, ClassroomID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                result.add(new Post(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5)));
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
