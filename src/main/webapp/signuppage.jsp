<%--
  Created by IntelliJ IDEA.
  User: 4769003
  Date: 16.03.2024
  Time: 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<div class="container-fluid d-flex justify-content-center align-items-center" style="height: 100vh;">
    <form style="width: 30%;" method="post" action="/sign-up">
        <h2 class="text-center mb-4">Sign up</h2>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Login</label>
            <div class="col-sm-10">
                <input class="form-control" id="login" name="login" type="text" placeholder="Login">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control border" id="password" name="password" placeholder="Password">
            </div>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary mb-2">LOGIN</button>
            <p class="mt-3">No profile? <a href="/registration">Create</a></p>
        </div>
    </form>
</div>
</body>
</html>
