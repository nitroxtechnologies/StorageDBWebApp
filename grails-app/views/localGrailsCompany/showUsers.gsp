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
    <div class="container">
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
                        <a class="dropdown-item" href="#">Log out/a>
                    </div>
                </div>
            </div>
        </nav>
            <table id = "userTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">Type</th>
                    <th scope="col">Date Created</th>
                    <th scope="col">Date Updated</th>
                    <th scope="col">Save</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${users}" var="user" status="i">
                    <tr>
                        <td contenteditable="false" class="text-left">${user.username}</td>
                        <td contenteditable="false" class="text-left">${user.type}</td>
                        <td class="text-center"><button type="submit" id="${user.id + 100000}" name="deleter" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                        <td class="text-center"><button name="editButton" id="${user.id}" type="submit" class="btn btn-success"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                    </tr>
                </g:each>



                </tbody>
            </table>

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
            window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'deleteUser')}" + "?id=" + (indexClicked - 100000);
        }

    })
    var something;
    var addingId;
    var indexClicked;
    $(document).ready(function() {
        something = "${users}";
        addingId = ${maxId};
        $('#userTable').append('\
        <tr>\
            <td style="display:none;">FUN TIMES</td>\
            <td contenteditable="false" class="text-left">' + addingId + '</td>\
            <td contenteditable="false" class="text-left">MORE NEW DATA</td>\
            <td class="text-center"><button type="submit" name="deleter" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
            <td class="text-center"><button name="editButton" type="submit" class="btn btn-success"><i class="fa fa-trash" aria-hidden="true"></i></button></td>\
        </tr>');
    });

    var status = false;

    $('#userTable').on('click', 'button[name="editButton"]', function () {
        //String input = "<form id='infos' action=''>" +
        //               "First name:<input type='text' id = 'first_name_field' name='first_name'>" +
        //               "Last name:<input type='text' id = 'last_name_field' name='first_name'>" +
        //               "Username:<input type='text' id = 'username_field' name='first_name'>" +
        //               "Type:<input type='text' id = 'type_field' name='first_name'>" +
        //               "</form>";
        //input.replace(/\n/g, "<br />");
        indexClicked = this.id;
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
                    //Save stuff
                    var id = indexClicked;
                    var firstName = document.getElementById("first_name_field").value;
                    var lastName = document.getElementById("last_name_field").value;
                    var username = document.getElementById("username_field").value;
                    var password = document.getElementById("password_field").value;
                    var type = document.getElementById("type_field").value;
                    window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'addUser')}" + "?id=" +
                    id + "&firstName=" + firstName + "&lastName=" + lastName + "&username=" + username +
                    "&password=" + password + "&type=" + type;
                }
                console.log(result);
        });
        /*
        theBox.on('shown.bs.modal', function() {
            theBox.find('.modal-body').on('click', 'button[label="OK"]', function(e) {
                console.log("WHEN OK CLICK I SAVE");
            });
        });*/
        var array = something.split(" ");
        var firstName = array[4*indexClicked+0];
        if(indexClicked == 0)
        {
            firstName = firstName.substring(1);
        }
        var lastName = array[4*indexClicked+1];
        var username = array[4*indexClicked+2];
        var type = array[4*indexClicked+3];
            type = type.substring(0,type.length-1);
        document.getElementById("first_name_field").value = firstName;
        document.getElementById("last_name_field").value = lastName;
        document.getElementById("username_field").value = username;
        document.getElementById("type_field").value = type;
    })



</g:javascript>

    </body>
</html>

