<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<%
    File[] files = (File[]) request.getAttribute("files");
    File[] folders = (File[]) request.getAttribute("folders");
    String path = request.getParameter("path");
    if(path == null){
        path = "D:/";
    }
    String previousPath = (new File(path)).getParent();
%>

<head>
    <title>First JSP</title>
    <style>
        img{
            height: 20px;
            width: 20px;
        }
    </style>
</head>
<body>
<%=LocalDateTime.now()
%>
<h1>Список файлов в папке</h1>
<h2><%=path%></h2>
<% if (previousPath != null) { %>
<a href="?path=<%=java.net.URLEncoder.encode(previousPath, "UTF-8")%>">Вернуться на уровень выше: <%=previousPath%></a>
<% } %>
<hr/>
<table>
    <tr>
        <th></th>
        <th>Название</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <% for (File file : files) {%>
    <tr>
        <td><img src="file.png" alt=""></td>
        <td><a href="download-file?path=<%=java.net.URLEncoder.encode(file.getAbsolutePath(), "UTF-8")%>"><%= file.getName()%></a></td>
        <td><%= file.length()%></td>
        <td><%= new Date(file.lastModified())%></td>
    </tr>
    <% } %>

    <tr>
        <th></th>
        <th>Название папки</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <% for (File folder : folders) {%>
    <tr>
        <td><img src="folder.png" alt=""></td>
        <td><a href="?path=<%=java.net.URLEncoder.encode(folder.getAbsolutePath(), "UTF-8")%>"><%= folder.getName()%></a></td>
        <td><%= folder.length()%></td>
        <td><%= new Date(folder.lastModified())%></td>
    </tr>
    <% } %>
</table>
</body>
</html>