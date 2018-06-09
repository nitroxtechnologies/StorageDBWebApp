<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="edit"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        <link rel="stylesheet" href="/assets/font-awesome/css/font-awesome.min.css?compile=true">
        <script type="text/javascript" src="/assets/popper.min.js?compile=true" ></script>
        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
        <style>
        .focusedInput {
            /*border-color: rgba(82,168,236,.8);*/
            /*outline: 0;*/
            /*outline: thin dotted \9;*/
            /*-moz-box-shadow: 0 0 8px rgba(82,168,236,.6);*/
            /*box-shadow: 0 0 8px rgba(82,168,236,.6) !important;*/
            outline-offset: -15px;
        }
        input[type=number]::-webkit-inner-spin-button,
        input[type=number]::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
        </style>
    </head>
    <body>
        <div class="container">
            <nav style ="margin-top: 50px" class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" style='color:black;'>    <img src="/assets/icon.png?compile=true" width="30" height="30" class="d-inline-block align-top" alt="">
                    PriceDB</a>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="/LocalGrailsCompany">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${createLink(controller:'LocalGrailsCompany' ,action:'input')}">Add Unit(s)</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Menu
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="${createLink(controller:'LocalGrailsCompany' ,action:'graph')}">See price history</a>
                                <a class="dropdown-item" href="#">Anything</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Something else here</a>
                            </div>
                        </li>
                    </ul>
                    <form class="form-inline my-2 my-lg-0">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>
            </nav>
            <div class ="row">
                <div class="col-sm page-header" style = "margin-top:30px">
                    <h1>Editing <small>${compareFacility.name}'s units</small></h1>
                </div>
                <div class="col-sm text-center" style="margin-top: 50px">
                    <button type="button" class="btn btn-outline-primary">Add row</button></td>
                </div>
                <div class="col-sm text-center" style="margin-top: 50px">
                    <button onclick = "saveTable()" type="button" class="btn btn-outline-success">Save</button></td>
                </div>
            </div>
            <table id = "unitTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    %{--<th scope="col">ID</th>--}%
                    <th scope="col">Unit Type</th>
                    <th scope="col">Width</th>
                    <th scope="col">Depth</th>
                    <th scope="col">Floor</th>
                    <th scope="col" class="text-center">Climate Controlled?</th>
                    <th scope="col" class="text-center">Old Price</th>
                    <th scope="col" class="text-center">New Price</th>
                    <th scope="col"> </th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${units}" var="unit" status="i">
                    <tr class = "entries ${unit.name} ${unit.climate} ${i}">
                        <td contenteditable="false" class="text-left">${unit.name}</td>
                        <td contenteditable="true" class="text-left">${unit.width}</td>
                        <td contenteditable="true" class="text-left">${unit.depth}</td>
                        <td contenteditable="true" class="text-left">${unit.floor}</td>
                        <td contenteditable="false" class="text-center" style="width: 10px">
                            <div class="dropdown">
                                <button class="btn btn-light dropdown-toggle" type="button" id="${i}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${unit.climate}
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button onclick="changeText('Climate', ${i})" class="dropdown-item" type="button">Climate</button>
                                    <button onclick="changeText('Non-Climate', ${i})" class="dropdown-item" type="button">Non-Climate</button>
                                    <button onclick="changeText('Parking', ${i})" class="dropdown-item" type="button">Parking</button>
                                </div>
                            </div>
                        </td>
                        <g:each in="${unit.prices}" var="price" status="j">
                                <span>
                                    <td style ="color:gray" contenteditable="false" class="text-right">$${String.format("%.02f", price.val)}
                                    <button type="button" class = "btn btn-link" onclick="copyPrice(${i})">
                                        <i style="vertical-align: top" class="fa fa-arrow-right" aria-hidden="true"></i>
                                    </button>
                                </span>
                            </td>
                        </g:each>
                        <td contenteditable="true" class="text-right focusedInput" id = "focused">
                            <div class="input-group">
                                <div contenteditable="false" class="input-group-prepend">
                                    <span class="input-group-text">$</span>
                                </div>
                                <input style="width: 10px" type="number" step=".01" onkeypress="return validateFloatKeyPress(this,event);" class="form-control text-right" aria-label="Amount">

                            </div>
                        </td>
                        <td class="text-center"><button type="submit" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
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
    <script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
    <script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
    <script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
    <g:javascript>
        function validateFloatKeyPress(el, evt) {
            var charCode = (evt.which) ? evt.which : event.keyCode;
            var number = el.value.split('.');
            if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            //just one dot
            if(number.length > 1 && charCode == 46){
                 return false;
            }
            //get the carat position
            var caratPos = getSelectionStart(el);
            var dotPos = el.value.indexOf(".");
            if( caratPos > dotPos && dotPos > -1 && (number[1].length > 1)){
                return false;
            }

            return true;
        }


        // $("input").focusout(function(){
        //    // alert($(this).get(0).value);
        // });

        //thanks: http://javascript.nwbox.com/cursor_position/
        function getSelectionStart(o) {
            if (o.createTextRange) {
                var r = document.selection.createRange().duplicate();
                r.moveEnd('character', o.value.length);
                if (r.text == '') return o.value.length
                    return o.value.lastIndexOf(r.text);
            } else return o.selectionStart;
        }
        function changeText(s, i) {
            var dropdown = document.getElementById(i);
            dropdown.innerText = s;
        }


        $('#unitTable').on('click', 'button[type="submit"]', function () {
            if(confirm('Are you sure you want to delete this unit?'))
                $(this).closest('tr').remove();
        })

        $('#unitTable').on('click', 'button[class="dropdown-item"]', function () {
            // alert($(this).closest('.dropdown-toggle').toString());
        })

        function copyPrice(val) {
            var row = document.getElementsByClassName(val);
            var cells = row[0].getElementsByTagName("td");
            // var price = cells[5].innerText.substring(1);
            cells[6].getElementsByClassName("form-control")[0].setAttribute("value", cells[5].innerText.substring(1, cells[5].innerText.length-2));
        }

        $('.btn-outline-primary').click(function () {
            $('#unitTable').append('<tr class = "entries"><td contenteditable="true" class="text-left"> </td> <td contenteditable="true" class="text-left"> </td> <td contenteditable="true" class="text-center"></td><td contenteditable="true" class="text-center"></td>' +
             '<td contenteditable="false" class="text-center" style="width: 10px"><div class="dropdown"><button class="btn btn-light dropdown-toggle" type="button" id="${i}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Climate</button>' + '<div class="dropdown-menu" aria-labelledby="dropdownMenu2">' +
            '<button onclick="changeText()" class="dropdown-item" type="button">Climate</button>' +
            '<button onclick="changeText()" class="dropdown-item" type="button">Non-Climate</button>' +
            '<button onclick="changeText()" class="dropdown-item" type="button">Parking</button>' +
        '<td contenteditable="false" class="text-center">N/A</td><td contenteditable="true" class="text-right focusedInput">' +
            '<div class="input-group">' +
                '<div contenteditable="false" class="input-group-prepend">' +
                    '<span class="input-group-text">$</span>' +
                '</div>' +
                '<input style="width: 10px" type="number" step=".01" onkeypress="return validateFloatKeyPress(this,event);" class="form-control text-right" aria-label="Amount">' +
            '</div>' +
        '</td>' +
        '</div></div></td><td class="text-center"><button type="submit" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td></tr>');
        });

        function saveTable() {
            var params = "";
            var table = document.getElementById("unitTable");
            var rows = table.getElementsByTagName("tr");
            var first = true;
            for (var i = 1; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName("td");
                for (var j = 0; j < cells.length; j++) {
                    if (j == 0 || j%7 != 0) {
                        // alert(cells[j].innerText);
                        if (first) {
                            params += "?";
                            first = false;
                        } else {
                            params += "&";
                        }

                        params += i;
                        params += j;
                        params += "=";

                        if (cells[j].innerText.indexOf("$") > -1 && j == 5) {
                            params += cells[j].innerText.substring(1);
                        } else if (j != 0 && j%6 == 0) {
                            // alert(cells[6].getElementsByClassName("form-control")[0].value);
                            params += cells[6].getElementsByClassName("form-control")[0].value;
                        } else {
                            // alert("no");
                            params += cells[j].innerText;
                        }

                    }
                }
            }

            window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'save')}" + params;

        }
    </g:javascript>

    </body>
</html>