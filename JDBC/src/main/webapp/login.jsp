<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            document.getElementById('name').required = true;
            document.getElementById('pswd').required = true;

            $('#post').click(function () {
                    let name = document.getElementById('name').value;
                    let pswd = document.getElementById('pswd').value;

                    if ((pswd.length < 8)) {
                        alert("Password length should be greater then 8 symbols")
                        return 0;
                    } else if (name.length > 50 || pswd.length > 50) {
                        alert("Username and password length should be less than 50 symbols");
                        return 0;
                    } else if ((name === '')) {
                        alert("Username can't be empty")
                        return 0;
                    }

                    $.ajax({
                        type: 'post',
                        url: 'http://localhost:8080/login',
                        data: {
                            'name': name,
                            'pswd': pswd

                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
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
<div align="center">
    <h1>Login Form</h1>
    <form method="post">
        <table style="width: 80%">
            <tr>
                <td>Enter username</td>
                <td><input type="text" name="name" id="name"/></td>
            </tr>
            <tr>
                <td>Enter password</td>
                <td><input type="password" name="pswd" id="pswd"/></td>
            </tr>

        </table>
        <button type="submit" value="Submit" id="post">Submit</button>
    </form>
</div>
</body>
</html>