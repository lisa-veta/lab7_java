package org.example;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import accounts.UserProfile;
import accounts.AccountService;
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
        if (login == null || password == null || email == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<h1 style='color: red;'>" +
                    "Не все поля заполнены, <a href='javascript:history.go(-1)'>вернитесь" +
                    " назад</a> и заполните</h1>");
            return;
        }
        UserProfile userProfile = new UserProfile(login, password, email);
        AccountService.addNewUser(userProfile);
        String session = req.getSession().getId();
        AccountService.addNewSession(session, userProfile);
        File directory = new File("D:/filemanager/"+login);
        if (!directory.mkdir()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<h1 style='color: red;'>" +
                    "Ошибка создания профиля, <a href='javascript:history.go(-1)'>назад </a></h1>");
            return;
        }
        resp.sendRedirect("/sign-up");
    }
}
