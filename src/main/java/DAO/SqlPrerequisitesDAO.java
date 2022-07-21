package DAO;

import DAO.Interfaces.PrerequisitesDAO;
import Model.Subject;

import java.sql.*;
import java.util.Stack;

public class SqlPrerequisitesDAO implements PrerequisitesDAO {
    private final ConnectionPool pool;

    public SqlPrerequisitesDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    private boolean removeSubjectAndPrerequisite(String subjectName){
        Connection conn = pool.getConnection();
        boolean result = false;
        try{
            String statement = "DELETE FROM PREREQUISITES P JOIN SUBJECT S ON P.subjectID=S.ID HAVING SUBJECTNAME = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, subjectName);
            int updateResult = ps.executeUpdate();
            result = updateResult == 1? true : false;
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
            result = false;
        }

        pool.releaseConnection(conn);
        return result;
    }
    private int addSubjectAndPrerequisites(String subjectName,String prerequisites) throws SQLException {
        Connection conn = pool.getConnection();
        int result = -1;
        /*
        TODO: here we first need to make Prerequisites names into IDs and then continue;
         */
        String statement = "INSERT INTO PREREQUISITES (SubjectID, Prerequisites) VALUES (?, ?);";
        PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        SqlSubjectDAO dao=new SqlSubjectDAO(pool);
        ps.setInt(1, dao.getSubjectIDByName(subjectName));
        ps.setString(2, prerequisites);
        if (ps.executeUpdate() == 1) {
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
        }
        pool.releaseConnection(conn);
        return result;

    }
    public void updatePrerequisite(String subjectName,String prerequisites) {
        removeSubjectAndPrerequisite(subjectName);
        try {
            addSubjectAndPrerequisites(subjectName,prerequisites);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getSubjectPrerequisitesByName(String subjectName){
        Connection conn = pool.getConnection();
        Subject result = null;
        String answer="";
        try{
            String statement = "SELECT P.prerequisits FROM PREREQUISITES P JOIN SUBJECTS S ON P.subjectID=S.ID HAVING S.SubjectName = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                answer=rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
            return null;
        }

        pool.releaseConnection(conn);
        /*
        TODO: first we need to transform IDs into Names;
         */
        return answer;
    }
    private boolean notNumber(int i,String s){
        if(s.charAt(i)=='|'||s.charAt(i)=='&'||
                s.charAt(i)=='('||s.charAt(i)==')'||delim(s.charAt(i))){
            return true;
        }
        return false;
    }
    public boolean canThisSubjectChosenByStudent(String email,String subjectName){
        String prerequisitesOriginal=getSubjectPrerequisitesByName(subjectName);
        String prerequisitesBinaryForm="";
        for(int i=0;i<prerequisitesOriginal.length();i++){
            if(notNumber(i,prerequisitesOriginal)==true){
                prerequisitesBinaryForm+=prerequisitesOriginal.charAt(i);
            }else{
                int id=0;
                while(i<prerequisitesOriginal.length()){
                    id*=10;
                    id+=prerequisitesOriginal.charAt(i)-'0';
                    i++;
                }
                Connection conn = pool.getConnection();
                boolean x=false;
                try{
                    String statement = "SELECT SH.IsCompleted FROM USERS U JOIN STUDENTS ST ON U.ID=ST.UserID JOIN SUBJECTS_HISTORY SH ON ST.ID=SH.UserID JOIN SUBJECTS S ON SH.SubjectID=S.ID HAVING S.SubjectName = ? and ST.email = ?;";
                    PreparedStatement ps = conn.prepareStatement(statement);
                    ps.setString(1, subjectName);
                    ps.setString(2, email);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        x=rs.getBoolean(1);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                    pool.releaseConnection(conn);
                }
                pool.releaseConnection(conn);
                if(x) prerequisitesBinaryForm+='1';
                else prerequisitesBinaryForm+='0';
            }
        }
        if(evaluate(prerequisitesBinaryForm)==1)
            return true;
        return false;
    }
    boolean delim(char c) {
        return c == ' ';
    }
    boolean is_op(char c) {
        return c == '|' || c == '&' ;
    }

    private int priority (char op) {
        if (op == '&' || op == '|')
            return 1;
        return -1;
    }
    private void process_op(Stack<Integer> st, char op) {
        int r = st.peek(); st.pop();
        int l = st.peek(); st.pop();
        switch (op) {
            case '&': st.push(l & r); break;
            case '|': st.push(l | r); break;
        }
    }
    private int evaluate(String s) {
        Stack<Integer> st= new Stack<Integer>();
        Stack<Character> op = new Stack<Character>();

        for (int i = 0; i < (int)s.length(); i++) {
            if (delim(s.charAt(i)))
                continue;

            if (s.charAt(i)== '(') {
                op.push('(');
            } else if (s.charAt(i) == ')') {
                while (op.peek() != '(') {
                    process_op(st, op.peek());
                    op.pop();
                }
                op.pop();
            } else if (is_op(s.charAt(i))) {
                char cur_op = s.charAt(i);
                while (!op.empty() && priority(op.peek()) >= priority(cur_op)) {
                    process_op(st, op.peek());
                    op.pop();
                }
                op.push(cur_op);
            } else {
                int number = 0;
                if (i < (int)s.length() && (s.charAt(i)=='1'||s.charAt(i)=='0')) {
                    number = s.charAt(i) - '0';
                    i++;
                }
                --i;
//            cout<<number<<endl;
                st.push(number);
            }
        }

        while (!op.empty()) {
            process_op(st, op.peek());
            op.pop();
        }
        return st.peek();
    }
}
