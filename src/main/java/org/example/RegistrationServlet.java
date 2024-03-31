package org.example;

import accounts.UserProfile;
import dbservice.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = (String)req.getSession().getAttribute("login");
        String password = (String)req.getSession().getAttribute("password");
        if (login != null && password != null) {
            resp.sendRedirect("/list-files");
            return;
        }
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        UserProfile userProfile = new UserProfile(login, password, email);
        if(DBService.getUserByLogin(login) == null){
            DBService.addUser(userProfile);
            File directory = new File("D:/filemanager/"+login);
            if (!directory.mkdir()) {
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println("<script>alert('Ошибка создания профиля');</script>");
                return;
            }
            resp.sendRedirect("/sign-up");
        }
        else{
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<script>alert('Пользователь с таким именем уже существует, придумайте другое');</script>");
        }
    }
}
