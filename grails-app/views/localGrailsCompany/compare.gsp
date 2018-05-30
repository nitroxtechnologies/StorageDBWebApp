<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="compare"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        <link rel="stylesheet" href="/assets/chosen.min.css?compile=true" />
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
            <div class="page-header">
                <h1>Comparing prices for...<small>${facility}'s units to</small></h1>
            </div>

            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <div class = "row">
                <div class = "col-md-6 text-center">
                    <div class="page-header" style = "margin-bottom: 20px">
                        <button type="button" class="btn btn-outline-success" onclick="addUnit(document.getElementById('addFacility'))">Add</button>
                    </div>
                    <label>Company:</label>
                    <g:select id = "company" optionKey="dbId" optionValue="name" value ="${compareCompany}"
                              name="companydropdown" from="${compareCompanies}"
                              onChange= 'addCompany(document.getElementById("company"))'>
                    </g:select>
                    <label>Facility:</label>
                    <g:select id = 'addFacility' optionKey="dbId" optionValue="name"
                              name="addF" from="${addFacilities}">
                    </g:select>
                </div>
                <div class = "col-md-6 text-center">
                    <div class="page-header" style = "margin-bottom: 20px">
                        <button type="button" class="btn btn-outline-danger" onclick="addUnit(document.getElementById('removeFacility'))">Remove</button>
                    </div>
                    <label>Facility:</label>
                    <g:select id = 'removeFacility' optionKey="dbId" optionValue="name"
                              name="removeF" from="${removeFacilities}">
                    </g:select>
                </div>
                </div>

            </div>

            <div class="col-lg-12 text-center" style="margin-top: 60px">
                <label>Climate Controlled:</label>
                    <select name="climate" id="climate" onChange='filterTable(document.getElementById("climate"), document.getElementById("units"))'>
                        <option selected value="all"> All </option>
                        <option value="yes">Climate</option>
                        <option value="no">Non-Climate</option>
                    </select>
                <label>Unit:</label>
                <g:select id = 'units' optionKey="dbId" optionValue="name"
                          name="unitdropdown" from="${units}"
                          onChange = 'filterTable(document.getElementById("units"), document.getElementById("climate"))'
                          noSelection="['null':'All']">
                </g:select>
            </div>
            <table id = "unitTable" class="table" style="margin-top: 50px">
                <thead>
                    <tr>
                        %{--<th scope="col">ID</th>--}%
                        <th scope="col">Dimensions</th>
                        <th scope="col">Floor</th>
                        <th scope="col" class="text-center">Climate Controlled?</th>
                        <g:each in="${removeFacilities}" var="f" status="i">
                        <th scope="col" class="text-right">${f.name}</th>
                        </g:each>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${units}" var="unit" status="i">
                    <tr class = "entries ${unit.name} ${unit.climate}">
                        <td class="text-left">${unit.name}</td>
                        <td class="text-left">${unit.floor}</td>
                        <td class="text-center">${unit.climate}</td>
                        <g:each in="${unit.prices}" var="price" status="j">
                        <td class="text-right">$${price.val}</td>
                        </g:each>
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
    %{--<script type="text/javascript" src="/assets/chosen.jquery.min.js?compile=true" ></script>--}%
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

    function addCompany(e) {
        var cID = e.selectedIndex;
        var cName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'compare')}" + "?cID=" + cID + "&cName=" + cName;
    }

    function addUnit(e) {
        var fID = e.selectedIndex;
        var fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'compare')}" + "?fID=" + fID + "&fName=" + fName;
    }

    function filterTable(e, o) {
        var i;
        var filter = e.options[e.selectedIndex].text;
        var other = o.options[o.selectedIndex].text;

        if (filter == "All") filter = "entries";
        if (other == "All") other = "entries";

        var tr = document.getElementsByClassName("entries");

          for (i = 0; i < tr.length; i++) {
              var array = tr[i].className.split(" ");
              if (array.indexOf(filter) > -1
              && array[array.indexOf(filter)].length == filter.length
              && array.indexOf(other) > -1 && array[array.indexOf(other)].length == other.length) {
                  tr[i].style.display = "";
              } else {
                  tr[i].style.display = "none";
              }
          }
    }

</g:javascript>
    </body>
</html>