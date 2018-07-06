<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        %{--<asset:stylesheet src="application.css"/>--}%
    </head>
<style>
    .false {
        display:none;
    }
</style>
<body>
    <div class="container" id="fullContainer">
        <!-- As a heading -->
        <div class="container" style = "margin-top:30px">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="checkbox">
                <label class="form-check-label" for="checkbox">
                    Show inactive users
                </label>
            </div>
        </div>
            <table id = "userTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    <th onclick="sortTable(1)" scope="col">Username <i class="fa fa-sort"/></th>
                    <th onclick="sortTable(2)" scope="col">First Name <i class="fa fa-sort"/></th>
                    <th onclick="sortTable(3)" scope="col">Last Name <i class="fa fa-sort"/></th>
                    <th onclick="sortTable(4)" scope="col">Type <i class="fa fa-sort"/></th>
                    <th onclick="sortTable(5)" scope="col">Date Created <i class="fa fa-sort"/></th>
                    <th onclick="sortTable(6)" scope="col">Date Updated <i class="fa fa-sort"/></th>
                    <th scope="col">Active</th>
                    <th scope="col" class="text-center">Edit</th>
                    <th scope="col" class="text-center">Delete</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${users}" var="user" status="i">
                    <tr id = "row${user.id}" class = "${user.isActive}">
                        <td id="userInfoId${user.id}" style="display:none;" >${user.id}*${user.type}*${user.firstName}*${user.lastName}*${user.username}*${user.password}*${user.dateCreatedString}*${user.dateUpdatedString}*${user.isActive}</td>
                        <td contenteditable="false" class="text-left">${user.username}</td>
                        <td contenteditable="false" class="text-left">${user.firstName}</td>
                        <td contenteditable="false" class="text-left">${user.lastName}</td>
                        <td contenteditable="false" class="text-left">${user.type}</td>
                        <td contenteditable="false" class="text-left">${user.dateCreatedString}</td>
                        <td contenteditable="false" class="text-left">${user.dateUpdatedString}</td>
                        <td contenteditable="false" class="text-left">${user.isActive}</td>
                        <td class="text-center"><button name="editButton" id="editButton${user.id}" type="submit" class="btn btn-outline-info"><i class="fa fa-edit" aria-hidden="true"></i></button></td>
                        <td class="text-center"><button type="submit" id="deleteButton${user.id}" name="deleter" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                    </tr>
                </g:each>
                </tbody>
            </table>

            <div class ="row">
                <div class="col-sm text-center" style="margin-top: 20px">
                    <button name="addUserButton" onclick = "compare(document.getElementById('facilitiesDropdown'))" type="submit" class="btn btn-outline-success">Compare Prices!</button></td>
                </div>
                <div class="col-sm text-center" style="margin-top: 20px">
                    <button name="saveTableButton" onclick = "saveUsers()" type="submit" class="btn btn-outline-primary">Edit Facility</button></td>
            </div>
    </div>

    <g:javascript>
        $('#checkbox').click(function(){
            if (this.checked) {
                $('.false').show();
            }
            else {
                $('.false').hide();
            }
        });


        var formSubmitting = false;
        var setFormSubmitting = function() { formSubmitting = true; };

        window.onload = function() {
            window.addEventListener("beforeunload", function (e) {
                if (formSubmitting) {
                    return undefined;
                }

                var confirmationMessage = 'It looks like you have been editing something. '
                                        + 'If you leave before saving, your changes will be lost.';

                (e || window.event).returnValue = confirmationMessage; //Gecko + IE
                return confirmationMessage; //Gecko + Webkit, Safari, Chrome etc.
            });
        };
        function sortTable(n) {
          var table, rows, icons, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
          table = document.getElementById("userTable");
          switching = true;
          dir = "asc";
          while (switching) {
            switching = false;
            rows = table.getElementsByTagName("tr");
            icons = table.getElementsByTagName('i');
            for (var j = 0; j < 6; j++)
                icons[j].className = "fa fa-sort";
            for (i = 1; i < (rows.length - 1); i++) {
              // Start by saying there should be no switching
              shouldSwitch = false;
              x = rows[i].getElementsByTagName("td")[n];
              y = rows[i + 1].getElementsByTagName("td")[n];
              if (dir == "asc") {
                  icons[n-1].className = "fa fa-sort-asc";
                  if (n == 5 || n == 6) {
                      var xList = x.innerHTML.split(" ");
                      var xYMD = xList[0].split("-");
                      var xTS = xList[1].split(":");
                      var xDate = new Date(xYMD[0],xYMD[1],xYMD[2],xTS[0],xTS[1]);

                      var yList = y.innerHTML.split(" ");
                      var yYMD = yList[0].split("-");
                      var yTS = yList[1].split(":");
                      var yDate = new Date(yYMD[0],yYMD[1],yYMD[2],yTS[0],yTS[1]);
                      if (xDate > yDate) {
                          shouldSwitch = true;
                          break;
                        }
                  }
                  else {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                      shouldSwitch = true;
                      break;
                    }
                }
              } else if (dir == "desc") {
                  icons[n-1].className = "fa fa-sort-desc";
                  if (n == 5 || n == 6) {
                      var xList = x.innerHTML.split(" ");
                      var xYMD = xList[0].split("-");
                      var xTS = xList[1].split(":");
                      var xDate = new Date(xYMD[0],xYMD[1],xYMD[2],xTS[0],xTS[1]);

                      var yList = y.innerHTML.split(" ");
                      var yYMD = yList[0].split("-");
                      var yTS = yList[1].split(":");
                      var yDate = new Date(yYMD[0],yYMD[1],yYMD[2],yTS[0],yTS[1]);
                      if (xDate < yDate) {
                          shouldSwitch = true;
                          break;
                        }
                  }
                  else {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                      shouldSwitch = true;
                      break;
                    }
                }
              }
            }
            if (shouldSwitch) {
              rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
              switching = true;
              switchcount ++;
            } else {
              if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
              }
            }
          }
        }

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

    $('#userTable').on('click', 'button[class="dropdown-item"]', function () {
            // alert($(this).get(0).innerHTML);
            var cli = $(this).get(0).innerText;
            // alert($(this).parent().parent().children().get(0).innerText);
            $(this).parent().parent().children().get(0).innerText = cli;
        })

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
            bootbox.confirm("<div class='container'><form id='infos' action=''>" +
           "<div class='form-group'><label for = 'first_name_field'>First name: </label><input type='text' id = 'first_name_field' name='first_name'></div>" +
           "<div class='form-group'><label for = 'last_name_field'>Last name: </label><input type='text' id = 'last_name_field' name='last_name'></div>" +
           "<div class='form-group'><label for = 'username_field'>Username: </label><input type='text' id = 'username_field' name='username'><br/></div>" +
           "<div class='form-group'><label for = 'password_field'>Password: </label><input type='text' id = 'password_field' name='password'></div>" +
           '<div class="form-group">Type: <select id = "type_field">\
                    <option value="Admin">Admin</option>\
                    <option value="User">User</option>\
                </select></div>' +
               "</form></div>", function(result) {
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
                               <td style="display:none;" id="userInfoId' + id + '" style="display:none;">' + text + '</td>\
                               <td contenteditable="false" class="text-left">' + username + '</td>\
                               <td contenteditable="false" class="text-left">' + firstName + '</td>\
                               <td contenteditable="false" class="text-left">' + lastName + '</td>\
                               <td contenteditable="false" class="text-left">' + type + '</td>\
                               <td contenteditable="false" class="text-left">' + date + '</td>\
                               <td contenteditable="false" class="text-left">' + date + '</td>\
                               <td class="text-center"><button name="editButton" id=editButton' + id + 'type="submit" class="btn btn-outline-info"><i class="fa fa-edit" aria-hidden="true"></i></button></td>\
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
        bootbox.confirm("<div class='container'><form id='infos' action=''>" +
           "<div class='form-group'><label for = 'first_name_field'>First name: </label><input type='text' id = 'first_name_field' name='first_name'></div>" +
           "<div class='form-group'><label for = 'last_name_field'>Last name: </label><input type='text' id = 'last_name_field' name='last_name'></div>" +
           "<div class='form-group'><label for = 'username_field'>Username: </label><input type='text' id = 'username_field' name='username'><br/></div>" +
           "<div class='form-group'><label for = 'password_field'>Password: </label><input type='text' id = 'password_field' name='password'></div>" +
           '<div class="form-group">Type: <select id = "type_field">\
                    <option value="Admin">Admin</option>\
                    <option value="User">User</option>\
                </select></div>' +
           '<div class="form-group"><div class="form-check">\
                <input class="form-check-input" type="checkbox" value="" id="active_box">\
                <label class="form-check-label" for="active_box">\
                    Active\
                </label>\
            </div></div>' +
           "</form></div>", function(result) {
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

                    var active = document.getElementById("active_box").checked;

                    var d = new Date();
                    var dateUpdated = d.getFullYear()  + "-" +
                    ("0"+(d.getMonth()+1)).slice(-2) + "-" +
                    ("0" + d.getDate()).slice(-2) + " " +
                    ("0" + d.getHours()).slice(-2) + ":" +
                    ("0" + d.getMinutes()).slice(-2);
                    var dateCreated = document.getElementById("userInfoId" + id).innerText.split("*")[6];
                    var text = id + "*" + type + "*" + firstName + "*" + lastName + "*" + username + "*" + password + "*" + dateCreated + "*" + dateUpdated + "*" + active;
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
                            <td contenteditable="false" class="text-left">' + active + '</td>\
                            <td class="text-center"><button name="editButton" id=editButton' + id + 'type="submit" class="btn btn-outline-info"><i class="fa fa-edit" aria-hidden="true"></i></button></td>\
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
        // alert(something);
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
        var checked = array[8];
        document.getElementById("first_name_field").value = firstName;
        document.getElementById("last_name_field").value = lastName;
        document.getElementById("username_field").value = username;
        document.getElementById("type_field").value = type;
        document.getElementById("active_box").checked = checked;
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
        setFormSubmitting();
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'saveUsers')}" + params;
    }

</g:javascript>

    </body>
</html>