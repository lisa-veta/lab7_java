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
@WebServlet(urlPatterns = {"/list-files"})
public class ListOfFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String session = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySession(session);
        if (profile == null) {
            resp.sendRedirect("/sign-up");
            return;
        }

        String directoryPath = req.getParameter("path");
        if (directoryPath.equals("D:\\filemanager")) {
            directoryPath = "D:\\filemanager\\"+ profile.getLogin();
        }
        File folder = new File(directoryPath);
        File[] files = folder.listFiles(File::isFile);
        File[] folders = folder.listFiles(File::isDirectory);
        req.setAttribute("files", files);
        req.setAttribute("folders", folders);
        req.setAttribute("currentPath", directoryPath);
        if (directoryPath.equals("D:\\filemanager\\" + profile.getLogin())) {
            req.setAttribute("previousPath", directoryPath);
        }
        else{
            req.setAttribute("previousPath", (new File(directoryPath)).getParent());
        }
        req.getRequestDispatcher("listOfFiles.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String session = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySession(session);
        if (profile == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<h1 style='color: red;'>" +
                    "Ошибка, <a href='javascript:history.go(-1)'>назад </h1>");
            return;
        }
        AccountService.deleteSession(session);
        resp.sendRedirect("/sign-up");
    }
}

