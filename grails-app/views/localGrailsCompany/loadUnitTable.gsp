<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="loadUnitTable"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
        <style>
            .show {
                display: block;
            }
            .entries {
                display: none;
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
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Menu
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="#">Something</a>
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
            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <label>Company:</label>
                <g:select id = "cDropdown" optionKey="id" optionValue="name" value ="${company}"
                          name="companydropdown" from="${companies}"
                          onChange= 'loadFacilities(document.getElementById("cDropdown"))'>
                </g:select>
                <label>Facility:</label>
                <g:select id = 'facilitiesDropdown' optionKey="id" optionValue="name" value = "${facility}"
                          name="facilitydropdown" from="${facilities}"
                          onChange= 'loadUnits(document.getElementById("cDropdown"), document.getElementById("facilitiesDropdown"))'>
                </g:select>
            </div>
            <div class="col-lg-12 text-center" style="margin-top: 20px">
                <label>Climate Controlled:</label>
                <select name="climate" id="climate" onChange='filterTable(document.getElementbyId("climate"))'>
                    <option selected value="all"> All </option>
                    <option value="yes">Yes</option>
                    <option value="no">No</option>
                </select>
                <br>
                <label>Unit:</label>
                <g:select id = 'units' optionKey="id" optionValue="name"
                          name="unitdropdown" from="${units}"
                          onChange = 'filterTable(document.getElementbyId("units"))'
                          noSelection="['null':'Select a Facility']">
                </g:select>
            </div>

            <table id = "unitTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    %{--<th scope="col">ID</th>--}%
                    <th scope="col">Dimensions</th>
                    <th scope="col">Floor</th>
                    <th scope="col">Price</th>
                    <th scope="col">Climate Controlled?</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${units}" var="unit" status="i">
                    <tr class = "entries ${unit.name} ${unit.climate}">
                        %{--<th scope = "row"> ${unit.id}</th>--}%
                        <td class="text-left">${unit.name}</td>
                        <td class="text-left">${unit.floor}</td>
                        <td class="text-left">${unit.climate}</td>
                        <td class="text-left">${unit.price}</td>
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
       function loadFacilities(e){
            var cID = e.selectedIndex;
            var cName = e.options[e.selectedIndex].text;
           window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadFacilities')}" + "?cID=" + cID + "&cName=" + cName;
    }

    function loadUnits(c, e) {
        var cID = c.selectedIndex;
        var cName = c.options[c.selectedIndex].text;
        var fID = e.selectedIndex;
        var fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadUnitTable')}" + "?cID=" + cID + "&cName=" + cName + "&fID=" + fID + "&fName=" + fName;
    }

    function filterTable(e) {
        var i;
        var filter = e.options[e.selectedIndex].text;
        if (filter == "All") filter = "";

        var units = document.getElementsByClassName("entries");

        for (i = 0; i < units.length; i++) {
            hideRow(units[i], "show");
            if (units[i].className.indexOf(filter) > -1) showRow(units[i], "show");
        }


        function showRow(element, name) {
          var i, arr1, arr2;
          arr1 = element.className.split(" ");
          arr2 = name.split(" ");
          for (i = 0; i < arr2.length; i++) {
            if (arr1.indexOf(arr2[i]) == -1) {
              element.className += " " + arr2[i];
            }
          }
        }

        // Hide elements that are not selected
        function hideRow(element, name) {
          var i, arr1, arr2;
          arr1 = element.className.split(" ");
          arr2 = name.split(" ");
          for (i = 0; i < arr2.length; i++) {
            while (arr1.indexOf(arr2[i]) > -1) {
              arr1.splice(arr1.indexOf(arr2[i]), 1);
            }
          }
          element.className = arr1.join(" ");
        }

    }

    %{--function showUnit(f, e) {--}%
        %{--var fID = f.selectedIndex;--}%
        %{--var fName = f.options[f.selectedIndex].text;--}%
        %{--var uID = e.selectedIndex - 1;--}%
        %{--var uName = e.options[e.selectedIndex].text;--}%
        %{--window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadUnitTable')}" + "?uID=" + uID + "&uName=" + uName + "&fID=" + fID + "&fName=" + fName;--}%

    %{--}--}%
</g:javascript>
    </body>
</html>