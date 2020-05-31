<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
<form action="users" method="POST">
    Name: <input name="username" />
    <br><br>
    Password: <input name="password" />
    <br><br>
    Gender: <input type="radio" name="gender" value="female" checked />Female
    <input type="radio" name="gender" value="male" />Male
    <br><br>
    Preferences:
    <input type="checkbox" name="preferences" value="Music" checked />Music
    <input type="checkbox" name="preferences" value="Films" checked />Films
    <input type="checkbox" name="preferences" value="Games" checked />Games
    <br><br>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
