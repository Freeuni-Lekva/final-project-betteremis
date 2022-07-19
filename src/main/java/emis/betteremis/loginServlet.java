package emis.betteremis;


import DAO.Mapping;
import DAO.SqlUserDAO;
import Helper.Utils;
import Model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Contains email and password hash
        Map<String, Object> map = Utils.parseJson(req);
        SqlUserDAO usrDAO = (SqlUserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        //User usr = usrDAO.getUser((String)map.get("email"), (String)map.get("passhash"));
        //if(usr != null)
        //    System.out.println(usr.getEmail());
    }


}


