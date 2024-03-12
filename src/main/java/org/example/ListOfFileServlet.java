package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
@WebServlet(urlPatterns = {"/list-files"})
public class ListOfFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String directoryPath = req.getParameter("path");
        if (directoryPath == null) {
            directoryPath = "D:/";
        }
        File folder = new File(directoryPath);
        File[] files = folder.listFiles(File::isFile);
        File[] folders = folder.listFiles(File::isDirectory);
        req.setAttribute("files", files);
        req.setAttribute("folders", folders);
        req.setAttribute("currentPath", directoryPath);
        req.setAttribute("previousPath",  (new File(directoryPath)).getParent());
        req.getRequestDispatcher("listOfFiles.jsp").forward(req, resp);
    }
}
