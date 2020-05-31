<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update user</title>
</head>
<body>
<h1>Type any user data to find and update</h1>
<form method="POST">
    Data: <input name="data" />
    <br><br>
    <h2>Type new user data</h2>
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
    <input type="submit" value="Find" />
</form>
</body>
</html>
