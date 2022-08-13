package Servlets;

import DAO.Mapping;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ServletUploader", value = "/ServletUploader")
@MultipartConfig
public class ServletUploader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Receive file uploaded to the Servlet from the HTML form */

        Part filePart = request.getPart(Mapping.FILE);
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println(fileName);
        InputStream fileContent = filePart.getInputStream();
        InputStreamReader isr = new InputStreamReader(fileContent, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        //Print for testing
        br.lines().forEach(line -> System.out.println(line));
        //TODO : parse file and redirect client.

    }
}
