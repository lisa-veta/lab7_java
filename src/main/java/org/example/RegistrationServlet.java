package org.example;

import accounts.AccountService;
import accounts.UserProfile;
import dbservice.UsersDAO;

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
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        UserProfile userProfile = new UserProfile(login, password, email);
        try {
            if(UsersDAO.getUserByLogin(login) == null){
                UsersDAO.addUser(userProfile);
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
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
