package Helper;
import com.mysql.cj.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static Servlets.ErrorMessages.ERROR_PAGE_NOT_FOUND;

public class ErrorPageRedirector {

    public static void redirect(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("mess", ERROR_PAGE_NOT_FOUND);
        try{
            request.getRequestDispatcher("/invalidUser.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
