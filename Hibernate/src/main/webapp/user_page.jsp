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
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#change').click(function () {
                    var e = document.getElementById("group");
                    var group = e.options[e.selectedIndex].value;

                    $.ajax({
                        type: 'post',
                        url: 'http://localhost:8080/store',
                        data: {
                            myData: JSON.stringify(
                                {
                                    'action' : 'get_items',
                                    'groupId' : group
                                })
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data.message);
                        },
                        statusCode: {
                            400: function (data) {
                                console.log(data);
                            }
                        }
                    });
                }
            );
        });
    </script>
</head>

<body>

<center>
    <% User currentUser = (User) (session.getAttribute("currentSessionUser"));%>

    Welcome <%= currentUser.getUsername() %>
    <br><br>
    Group:
    <label for="group">Choose a group:</label>
    <select name="group" id="group">
        <option value="1">Sport</option>
        <option value="2">Hunting</option>
        <option value="3">Tools</option>
    </select>
    <br><br>
    <button id="change">Change group</button>
</center>


</body>

</html>