package emis.betteremis;


import DAO.UserDAO;
import Helper.Utils;
import Model.*;
import org.json.*;

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
        Map<String, String> map = Utils.parseJson(req);
        UserDAO usrDAO = (UserDAO) req.getServletContext().getAttribute("usrdao");
        User usr = usrDAO.isValidUser(map.get("email"), map.get("passhash"));
        if(usr != null)
            System.out.println(usr.getEmail());
    }


}


