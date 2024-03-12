package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@WebServlet("/download-file")
public class DownloadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filePath = req.getParameter("path");
        File downloadFile = new File(filePath);
        try (FileInputStream fis = new FileInputStream(downloadFile)) {

            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", downloadFile.getName()));

            resp.setContentLength((int) downloadFile.length());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                resp.getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}
