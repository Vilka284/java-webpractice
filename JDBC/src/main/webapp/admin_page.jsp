<%@ page import="com.andrii.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#manager').click(function () {
                var userId = document.getElementById('userId').value;
                var roleAction = 'add_manager';
                var radios = document.getElementsByName('role');
                for (var i = 0, length = radios.length; i < length; i++) {
                    if (radios[i].checked) {
                        roleAction = radios[i].value;
                        break;
                    }
                }

                $.ajax({
                    type: 'post',
                    url: 'http://localhost:8080/store',
                    data: {
                        myData: JSON.stringify(
                            {
                                'action': roleAction,
                                'userId': userId
                            })
                    },
                    dataType: "json",
                    success: function (data) {
                        alert(data.message);
                    },
                    statusCode: {
                        400: function (data) {
                            console.log(data);
                        }
                    }
                });
            });

            $('#addItem').click(function () {
                var e = document.getElementById("group");
                var group = e.options[e.selectedIndex].value;

                $.ajax({
                    type: 'post',
                    url: 'http://localhost:8080/store',
                    data: {
                        myData: JSON.stringify(
                            {
                                'action': 'add_item',
                                'itemName': document.getElementById('addItemName').value,
                                'itemQuantity': document.getElementById('addItemQ').value,
                                'itemPrice': document.getElementById('addItemPrice').value,
                                'itemGroupId': group
                            })
                    },
                    dataType: "json",
                    success: function (data) {
                        alert(data.message);
                    },
                    statusCode: {
                        400: function (data) {
                            console.log(data);
                        }
                    }
                });
            });

            $('#removeItem').click(function () {

                $.ajax({
                    type: 'post',
                    url: 'http://localhost:8080/store',
                    data: {
                        myData: JSON.stringify(
                            {
                                'action': 'remove_item',
                                'itemId': document.getElementById('removeItem').value
                            })
                    },
                    dataType: "json",
                    success: function (data) {
                        alert(data.message);
                    },
                    statusCode: {
                        400: function (data) {
                            console.log(data);
                        }
                    }
                });
            });

        });
    </script>
</head>
<body>
<center>
    <% User currentUser = (User) (session.getAttribute("currentSessionUser"));%>

    Welcome <%= currentUser.getUsername() %>, you are on the admin page.
    <br><br>

    <hr>

    <h4>Hire and fire managers here</h4>
    Choose user:
    <input type="text" name="userId" id="userId" placeholder="Input id of user"/>
    <br><br>
    Assign:
    <input type="radio" name="role" value="add_manager"/>Hire manager
    <input type="radio" name="role" value="remove_manager" checked/>Fire manager
    <br><br>
    <button id="manager">Submit</button>

    <hr>

    <h4>Add new items</h4>
    Name:
    <input type="text" name="itemName" id="addItemName" placeholder="Input item name"/>
    <br><br>
    Price:
    <input type="text" name="itemPrice" id="addItemPrice" placeholder="Input item price"/>
    <br><br>
    Quantity:
    <input type="number" min="0" name="itemQ" id="addItemQ" placeholder="How many are items?"/>
    <br><br>
    Group:
    <label for="group">Choose a group:</label>
    <select name="group" id="group">
        <option value="1">Sport</option>
        <option value="2">Hunting</option>
        <option value="3">Tools</option>
    </select>
    <br><br>
    <button id="addItem">Submit</button>

    <hr>

    <h4>Remove items</h4>
    Item id:
    <input type="text" name="itemId" id="removeItem" placeholder="Input item id to remove"/>
    <br><br>
    <button id="removeItem">Submit</button>
    <hr>
</center>

</body>
</html>
