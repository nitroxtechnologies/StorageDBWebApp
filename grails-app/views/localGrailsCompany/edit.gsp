<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="edit"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        <link rel="stylesheet" href="/assets/font-awesome/css/font-awesome.min.css?compile=true">
        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
        <style>
            /*.show {*/
                /*display: block;*/
            /*}*/
            /*.entries {*/
                /*!*display: none;*!*/
            /*}*/
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
                    <th scope="col">Dimensions</th>
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
                        <td contenteditable="true" class="text-left">${unit.name}</td>
                        <td contenteditable="true" class="text-left">${unit.floor}</td>
                        <td contenteditable="true" class="text-center">${unit.climate}</td>
                        <g:each in="${unit.prices}" var="price" status="j">
                                <span>
                                    <td style ="color:gray" contenteditable="false" class="text-right">$${String.format("%.02f", price.val)}
                                    <button type="submit" class = "btn btn-link" onclick="copyPrice(${i})">
                                        <i style="vertical-align: top" class="fa fa-arrow-right" aria-hidden="true"></i>
                                    </button>
                                </span>
                            </td>
                        </g:each>
                        <td contenteditable="true" class="text-right"></td>
                        <td class="text-center"><button type="button" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
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

        $('#unitTable').on('click', 'button[type="button"]', function () {
            $(this).closest('tr').remove();
        })

        function copyPrice(val) {
            var row = document.getElementsByClassName(val);
            var cells = row[0].getElementsByTagName("td");
            cells[4].innerText = cells[3].innerText;
        }

        $('.btn-outline-primary').click(function () {
            $('#unitTable').append('<tr class = "entries"><td contenteditable="true" class="text-left"> </td> <td contenteditable="true" class="text-left"> </td> <td contenteditable="true" class="text-center"></td><td contenteditable="false" class="text-center">N/A</td><td contenteditable="true" class="text-right"></td><td class="text-center"><button type="button" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td></tr>')
        });

        function saveTable() {
            var params = "";
            var table = document.getElementById("unitTable");
            var rows = table.getElementsByTagName("tr");
            var first = true;
            for (var i = 1; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName("td");
                for (var j = 0; j < cells.length; j++) {
                    if (j == 0 || (j%3 != 0 && j%5 != 0)) {
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
                        if (cells[j].innerText.indexOf("$") > -1)
                            params += cells[j].innerText.substring(1);
                        else
                            params += cells[j].innerText;
                    }
                }
            }

            window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'save')}" + params;

        }
    </g:javascript>

    </body>
</html>