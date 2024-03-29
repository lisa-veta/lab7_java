package org.example;

import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import dbservice.DBService;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String password = (String)req.getSession().getAttribute("password");
        if (login != null && password != null) {
            resp.sendRedirect("/list-files");
            return;
        }
        req.getRequestDispatcher("signuppage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user;
        try {
            user = DBService.getUserByLogin(login);
            if (user != null && user.getPass().equals(password)) {
                req.getSession().setAttribute("login", login);
                resp.sendRedirect("list-files?path=D:/filemanager/"+user.getLogin());
            }
            else{
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println("<script>alert('Неверный логин или пароль');</script>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
