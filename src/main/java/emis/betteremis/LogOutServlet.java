package emis.betteremis;

import DAO.Mapping;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;

import static DAO.Mapping.USER_OBJECT;

@WebServlet(name = "LogOutServlet", value = "/LogOutServlet")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Enumeration<String> attributes = request.getSession().getAttributeNames();
        while(attributes.hasMoreElements()){
            session.removeAttribute(attributes.nextElement());
        }
        response.sendRedirect("homepage");
        return;
    }
}
