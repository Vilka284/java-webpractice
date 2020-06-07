<%@ page language="java"
         contentType="text/html;"
         import="com.andrii.module.user.User"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type"
          content="text/html;">
    <title>User page</title>
</head>

<body>

<center>
    <% User currentUser = (User) (session.getAttribute("currentSessionUser"));%>

    Welcome <%= currentUser.getUsername() %>
</center>

</body>

</html>