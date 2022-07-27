package Servlets;

import DAO.Interfaces.TokenDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static DAO.Mapping.EMAIL;
import static DAO.Mapping.TOKEN_DAO;

@WebServlet(name = "TokenHandler", value = "/change-password/*")
public class TokenHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String token = getTokenFromURL(url);
        TokenDAO dao = (TokenDAO) req.getServletContext().getAttribute(TOKEN_DAO);
        if(dao.isValidToken(token)){
            resp.sendRedirect("../changePassword.jsp");
        }else{
            req.setAttribute("incorrect", true);
            req.setAttribute("mess", "404 Page not found");
            req.getRequestDispatcher("/invalidUser.jsp").forward(req,resp);
        }
    }

    private String getTokenFromURL(String url) {
        String[] path = url.split("/");
        return path[path.length - 1];
    }
}
