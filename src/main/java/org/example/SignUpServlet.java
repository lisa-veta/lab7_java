package org.example;

import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import accounts.AccountService;

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
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile userProfile = AccountService.getUserByLogin(login);

        String session = req.getSession().getId();
        AccountService.addNewSession(session, userProfile);

        if(userProfile != null && Objects.equals(userProfile.getPass(), password)){
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("password", password);
            resp.sendRedirect("list-files?path=D:/filemanager/"+login);
        }
        else{
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<script>alert('Неверный логин или пароль'); window.addEventListener('beforeunload', function (e) { return e.returnValue = 'Точно хотите покинуть страницу?'; });</script>");
            //sendRedirect("/sign-up");
            //resp.getWriter().println("<h1 style='color: red;'>" +
              //      "Неверный логин или пароль, <a href='javascript:history.go(-1)'>попробовать еще раз</a></h1>");
        }
    }
}
