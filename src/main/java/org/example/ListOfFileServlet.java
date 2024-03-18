package org.example;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = {"/list-files"})
public class ListOfFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String password = (String)req.getSession().getAttribute("password");
        if (login == null || password == null) {
            resp.sendRedirect("/sign-up");
            return;
        }
        String directoryPath = req.getParameter("path");
        if (directoryPath == null || directoryPath.equals("D:\\filemanager")) {
            directoryPath = "D:\\filemanager\\"+ login;
        }
        if(!directoryPath.startsWith("D:\\filemanager\\" + login)){
            directoryPath = "D:\\filemanager\\" + login;
        }
        File folder = new File(directoryPath);
        File[] files = folder.listFiles(File::isFile);
        File[] folders = folder.listFiles(File::isDirectory);
        req.setAttribute("files", files);
        req.setAttribute("folders", folders);
        req.setAttribute("currentPath", directoryPath);
        if (directoryPath.equals("D:\\filemanager\\" + login)) {
            req.setAttribute("previousPath", directoryPath);
        }
        else{
            req.setAttribute("previousPath", (new File(directoryPath)).getParent());
        }
        req.getRequestDispatcher("listOfFiles.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("password");
        resp.sendRedirect("/sign-up");
    }
}

