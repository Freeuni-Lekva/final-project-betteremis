package emis.betteremis;

import DAO.Mapping;
import DAO.UserDAO;
import Helper.Utils;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*map contains all the necessary information needed for registration
        * mapping:
        * firstname: first name of the user
            lastname: last name of the user
            email: email
            passhash: hash of user's password
            male: boolean if user is male or not
            profession:
            nationality:
            birthdate:
            address:
            phone: phone number
            type: boolean. true if user type is student, false otherwise
            school: empty if type === false, else contains school name
        * */
        Map<String, Object> map = Utils.parseJson(req);
        for(String key : map.keySet()){
            System.out.println(key + " : " + map.get(key));
        }
    }
}
