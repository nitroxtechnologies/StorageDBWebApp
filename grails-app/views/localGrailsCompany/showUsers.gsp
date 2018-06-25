<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="landing"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        <link rel="stylesheet" href="/assets/font-awesome/css/font-awesome.css?compile=true">

        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
        %{--<asset:javascript src="application.js"/>--}%
        <script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
        <script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
        %{--<script type="text/javascript" src="/assets/bootstrap.bundle.js?compile=true" ></script>--}%
        <script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
        %{--<script type="text/javascript" src="/assets/bootstrap.min.js?compile=true" ></script>--}%
        %{--<script type="text/javascript" src="/assets/jquery-2.2.0.min.js?compile=true" ></script>--}%
        %{--<script type="text/javascript" src="/assets/application.js?compile=true" ></script>--}%
        <script type="text/javascript" src="/assets/bootbox.min.js?compile=true"></script>


    </head>
<body>
    <div class="container" id="fullContainer">
        <!-- As a heading -->
        <nav style ="margin-top: 50px" class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" style='color:black;'>    <img src="/assets/icon.png?compile=true" width="30" height="30" class="d-inline-block align-top" alt="">
                PriceDB</a>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto" id="parentBar">
                    <li class="nav-item active">
                        <a class="nav-link" href="/LocalGrailsCompany">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${createLink(controller:'LocalGrailsCompany' ,action:'input')}">Add Unit(s)</a>
                    </li>
                </ul>
                <div class="dropdown">
                    <a style = "color:black" class="nav-link dropdown-toggle" id="adminDropdownLabel" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Welcome <strong>${username}</strong>
                    </a>
                    <div class="dropdown-menu" id="adminDropdown" aria-labelledby="adminDropdownLabel">
                        <g:if test="${type == 'Admin'}">
                            <a class="dropdown-item" href="${createLink(controller:'LocalGrailsCompany' ,action:'showUsers')}">Manage Users</a>
                        </g:if>
                        <a class="dropdown-item" href="#">Anything</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Log out</a>
                    </div>
                </div>
            </div>
        </nav>
            <table id = "userTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Type</th>
                    <th scope="col">Date Created</th>
                    <th scope="col">Date Updated</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${users}" var="user" status="i">
                    <tr id = "row${i}">
                        <td id="userInfoId${i}" style="display:none;"  >${user.id}*${user.type}*${user.firstName}*${user.lastName}*${user.username}*${user.password}*${user.dateCreatedString}*${user.dateUpdatedString}</td>
                        <td contenteditable="false" class="text-left">${user.username}</td>
                        <td contenteditable="false" class="text-left">${user.firstName}</td>
                        <td contenteditable="false" class="text-left">${user.lastName}</td>
                        <td contenteditable="false" class="text-left">${user.type}</td>
                        <td contenteditable="false" class="text-left">${user.dateCreatedString}</td>
                        <td contenteditable="false" class="text-left">${user.dateUpdatedString}</td>
                        <td class="text-center"><button name="editButton" id="editButton${user.id}" type="submit" class="btn btn-success"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                        <td class="text-center"><button type="submit" id="deleteButton${user.id}" name="deleter" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                    </tr>
                </g:each>
                </tbody>
            </table>

            <button type="submit" name="addUserButton" class="btn btn-outline-danger">ADD IT<i class="fa fa-trash" aria-hidden="true"></i></button><br><br>
            <div class="col-sm text-center" style="margin-top: 50px">
                <button onclick = "saveUsers()" type="button" class="btn btn-outline-success">Save</button>
            </div>
    </div>

    <footer class="footer">
        <div class="container">
            <span class="text-muted">&copy;2018 Nitrox Technologies</span>
        </div>
    </footer>
    <g:javascript>

   function loadFacilities(e)
   {
        var cID = e.selectedIndex - 1;
        var cName = e.options[e.selectedIndex].text;
       window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadFacilities')}" + "?cID=" + cID + "&cName=" + cName;
   }

    function login(e)
    {
        window.location.href="${createLink(controller:'LocalGrailsCompany', action:'login')}";
    }

    $('#userTable').on('click', 'button[name="deleter"]', function () {
        if(confirm('Are you sure you want to delete this user?'))
        {
            var indexClicked = this.id;
            indexClicked = indexClicked.replace("deleteButton","");
            $('#row' + indexClicked).remove();
        }
    })
    var addingId;
    var indexClicked;
    $(document).ready(function() {
        addingId = ${maxId};
    });

    Number.prototype.padLeft = function(base,chr){
        var  len = (String(base || 10).length - String(this).length)+1;
        return len > 0? new Array(len).join(chr || '0')+this : this;
    }

    var status = false;

    $('#fullContainer').on('click', 'button[name="addUserButton"]', function () {
            console.log(indexClicked + "TIMMY");
            bootbox.confirm("<form id='infos' action=''>" +
               "First name:<input type='text' id = 'first_name_field' name='first_name'><br/>" +
               "Last name:<input type='text' id = 'last_name_field' name='last_name'><br/>" +
               "Username:<input type='text' id = 'username_field' name='username'><br/>" +
               "Password:<input type='text' id = 'password_field' name='password'><br/>" +
               "Type:<input type='text' id = 'type_field' name='type'>" +
               "</form>", function(result) {
                    if(result)
                    {
                        var id = addingId + 1;
                        addingId = addingId + 1;
                        var firstName = document.getElementById("first_name_field").value;
                        var lastName = document.getElementById("last_name_field").value;
                        var username = document.getElementById("username_field").value;
                        var password = document.getElementById("password_field").value;
                        if(password.length < 1)
                            password = "test";
                        var type = document.getElementById("type_field").value;

                        var d = new Date();
                        var date = d.getFullYear()  + "-" +
                        ("0"+(d.getMonth()+1)).slice(-2) + "-" +
                        ("0" + d.getDate()).slice(-2) + " " +
                        ("0" + d.getHours()).slice(-2) + ":" +
                        ("0" + d.getMinutes()).slice(-2);
                        var text = id + "*" + type + "*" + firstName + "*" + lastName + "*" + username + "*" + password + "*" + date + "*" + date;
                        $('#userTable').append('\
                           <tr id=row' + id + '>\
                               <td style="display:none;" id="userInfoId' + id + '" style="display:none;"> ' + text + '</td>\
                               <td contenteditable="false" class="text-left">' + username + '</td>\
                               <td contenteditable="false" class="text-left">' + firstName + '</td>\
                               <td contenteditable="false" class="text-left">' + lastName + '</td>\
                               <td contenteditable="false" class="text-left">' + type + '</td>\
                               <td contenteditable="false" class="text-left">' + date + '</td>\
                               <td contenteditable="false" class="text-left">' + date + '</td>\
                               <td class="text-center"><button name="editButton" id=editButton' + id + 'type="submit" class="btn btn-success"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
                               <td class="text-center"><button type="submit" onclick = "saveUsers()" name="deleter" id="deleteButton' + id + '" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
                           </tr>');
                    }
                    console.log(result);
            });
        })

    $('#userTable').on('click', 'button[name="editButton"]', function () {
        //String input = "<form id='infos' action=''>" +
        //               "First name:<input type='text' id = 'first_name_field' name='first_name'>" +
        //               "Last name:<input type='text' id = 'last_name_field' name='first_name'>" +
        //               "Username:<input type='text' id = 'username_field' name='first_name'>" +
        //               "Type:<input type='text' id = 'type_field' name='first_name'>" +
        //               "</form>";
        //input.replace(/\n/g, "<br />");
        indexClicked = this.id;
        indexClicked = indexClicked.replace("editButton","");
        indexClicked = parseInt(indexClicked);
        console.log(indexClicked + "TIMMY");
        bootbox.confirm("<form id='infos' action=''>" +
           "First name:<input type='text' id = 'first_name_field' name='first_name'><br/>" +
           "Last name:<input type='text' id = 'last_name_field' name='last_name'><br/>" +
           "Username:<input type='text' id = 'username_field' name='username'><br/>" +
           "Password:<input type='text' id = 'password_field' name='password'><br/>" +
           "Type:<input type='text' id = 'type_field' name='type'>" +
           "</form>", function(result) {
                if(result)
                {
                    var id = indexClicked;
                    var firstName = document.getElementById("first_name_field").value;
                    var lastName = document.getElementById("last_name_field").value;
                    var username = document.getElementById("username_field").value;
                    var password = document.getElementById("password_field").value;
                    if(password.length < 1)
                        password = "test";
                    var type = document.getElementById("type_field").value;

                    var d = new Date();
                    var dateUpdated = d.getFullYear()  + "-" +
                    ("0"+(d.getMonth()+1)).slice(-2) + "-" +
                    ("0" + d.getDate()).slice(-2) + " " +
                    ("0" + d.getHours()).slice(-2) + ":" +
                    ("0" + d.getMinutes()).slice(-2);
                    var dateCreated = document.getElementById("userInfoId" + id).innerText.split("*")[6];
                    var text = id + "*" + type + "*" + firstName + "*" + lastName + "*" + username + "*" + password + "*" + dateCreated + "*" + dateUpdated;
                    //Save stuff
                    var newtr = '\
                        <tr id=row' + id + '>\
                            <td style="display:none;" id="userInfoId' + id + '" >' + text + '</td>\
                            <td contenteditable="false" class="text-left">' + username + '</td>\
                            <td contenteditable="false" class="text-left">' + firstName + '</td>\
                            <td contenteditable="false" class="text-left">' + lastName + '</td>\
                            <td contenteditable="false" class="text-left">' + type + '</td>\
                            <td contenteditable="false" class="text-left">' + dateCreated + '</td>\
                            <td contenteditable="false" class="text-left">' + dateUpdated + '</td>\
                            <td class="text-center"><button name="editButton" id=editButton' + id + 'type="submit" class="btn btn-success"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
                            <td class="text-center"><button type="submit" name="deleter" id="deleteButton' + id + '" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
                        </tr>';
                    $("tr#row" + id).replaceWith(newtr);
                }
                console.log(result);
        });
        /*
        theBox.on('shown.bs.modal', function() {
            theBox.find('.modal-body').on('click', 'button[label="OK"]', function(e) {
                console.log("WHEN OK CLICK I SAVE");
            });
        });*/
        console.log("running code");
        var toLookFor = "userInfoId" + indexClicked;
        console.log(toLookFor);
        var something = document.getElementById("userInfoId" + indexClicked).innerText;
        console.log(document.getElementById("userInfoId" + indexClicked).innerText);
        var array = something.split("*");
        var firstName = array[2];
        if(firstName.includes("["))
        {
            firstName = firstName.substring(1);
        }
        var lastName = array[3];
        var username = array[4];
        var type = array[1];
        document.getElementById("first_name_field").value = firstName;
        document.getElementById("last_name_field").value = lastName;
        document.getElementById("username_field").value = username;
        document.getElementById("type_field").value = type;
    })

    function saveUsers()
    {
        var params = "";
        var table = document.getElementById("userTable");
        var rows = table.getElementsByTagName("tr");
        var first = true;
        for (var i = 1; i < rows.length; i++) {
            var cells = rows[i].getElementsByTagName("td");
            if (first) {
                params += "?";
                first = false;
            } else {
                params += "&";
            }
            params += i + "=";
            params += cells[0].innerText;
        }
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'saveUsers')}" + params;
    }

</g:javascript>

    </body>
</html>