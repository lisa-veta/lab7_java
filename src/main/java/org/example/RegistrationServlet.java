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
        AccountService.addNewUser(userProfile);
        String session = req.getSession().getId();
        AccountService.addNewSession(session, userProfile);
        File directory = new File("D:/filemanager/"+login);
        if (!directory.mkdir()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<h1 style='color: red;'>" +
                    "Ошибка создания профиля, возможно, пользователь с таким логином уже существует," +
                    " <a href='javascript:history.go(-1)'>назад </a></h1>");
            return;
        }
        resp.sendRedirect("/sign-up");
    }
}
