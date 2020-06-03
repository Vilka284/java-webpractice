<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function drawTable(data) {
            //table = document.createElement("table");
            //document.getElementsByTagName('body').appendChild(table);
            for (var i = 1; i < data.length; i++) {
                console.log(data[i]);
            }

        }

        function getDataFromTextInput(idname, idpswd, idmusic, idfilms, idgames) {

            let name = document.getElementById(idname).value;
            let pswd = document.getElementById(idpswd).value;
            let gender = 'male';
            let pref = (document.getElementById(idmusic).value == null ? '' : 'music;') +
                (document.getElementById(idfilms).value == null ? '' : 'films;') +
                (document.getElementById(idgames).value == null ? '' : 'games');
            var radiosG = document.getElementsByName('gender');
            for (var i = 0, length = radiosG.length; i < length; i++) {
                if (radiosG[i].checked) {
                    gender = radiosG[i].value;
                    break;
                }
            }

            return JSON.stringify({
                'name': name,
                'pswd': pswd,
                'gender': gender,
                'pref': pref
            });
        }

        $(document).ready(function () {
            document.getElementById('name').required = true;
            document.getElementById('pswd').required = true;
            document.getElementById('nameToDelete').required = true;
            document.getElementById('nameToUpdate').required = true;

            $('#post').click(function () {
                    let myData = getDataFromTextInput('name', 'pswd', 'music', 'films', 'games');
                    if (myData.name || myData.pswd === '') {
                        return 0;
                    }

                    $.ajax({
                        type: 'post',
                        url: 'http://localhost:8080/mainservlet',
                        data: {myData: myData},
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

            $('#delete').click(function () {
                let nameToDelete = document.getElementById('nameToDelete').value;

                $.ajax({
                    type: 'delete',
                    url: 'http://localhost:8080/mainservlet',
                    data: {
                        myData: JSON.stringify({
                            "nameToDelete": nameToDelete
                        })
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
            });

            $('#update').click(function () {
                let nameToBeUpdated = document.getElementById('nameToUpdate').value;
                let data = getDataFromTextInput('nameU', 'pswdU', 'musicU', 'filmsU', 'gamesU');

                $.ajax({
                    type: 'put',
                    url: 'http://localhost:8080/mainservlet',
                    data: {
                        myData: JSON.stringify({
                            "nameToBeUpdated": nameToBeUpdated,
                            'name': data.name,
                            'pswd': data.pswd,
                            'gender': data.gender,
                            'pref': data.pref
                        })
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
            });

            $.ajax({
                type: 'get',
                url: 'http://localhost:8080/mainservlet',
                success: function (data) {
                    drawTable(data);
                },
                statusCode: {
                    400: function (data) {
                        console.log(data);
                    }
                }
            });

        });
    </script>
    <title>User Form</title>
</head>
<body>
<form>
    <h2>User creating</h2>
    Name: <input name="username" id="name"/>
    <br><br>
    Password: <input name="password" id="pswd"/>
    <br><br>
    Gender: <input type="radio" name="gender" value="female" id="gf"/>Female
    <input type="radio" name="gender" value="male" id="gm" checked/>Male
    <br><br>
    Preferences:
    <input type="checkbox" name="preferences" value="Music" id="music" checked/>Music
    <input type="checkbox" name="preferences" value="Films" id="films" checked/>Films
    <input type="checkbox" name="preferences" value="Games" id="games" checked/>Games
    <br><br>
    <button type="submit" value="Submit" id="post">Submit</button>
</form>
<hr>
<form>
    <h2>User deleting</h2>
    Type name or id to delete: <input name="delete" id="nameToDelete"/>
    <br><br>
    <button type="submit" value="Submit" id="delete">Submit</button>
</form>
<hr>
<form>
    <h2>User updating</h2>
    Type name or id to update: <input name="update" id="nameToUpdate"/>
    <br><br>
    <h4>Before submitting enter new data</h4>
    <div style="border-style: dotted;border-color: red">
    Name: <input name="username" id="nameU"/>
    <br><br>
    Password: <input name="password" id="pswdU"/>
    <br><br>
    Preferences:
    <input type="checkbox" name="preferences" value="Music" id="musicU" checked/>Music
    <input type="checkbox" name="preferences" value="Films" id="filmsU" checked/>Films
    <input type="checkbox" name="preferences" value="Games" id="gamesU" checked/>Games
    <br><br>
    </div>
    <button type="submit" value="Submit" id="update">Submit</button>
</form>
<hr>
<h2>List of users</h2>
<div class="table" id="table">

</div>
</body>

</html>
