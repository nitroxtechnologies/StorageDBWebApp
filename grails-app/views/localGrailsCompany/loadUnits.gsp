<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="loadUnits"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
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
                <g:select id = "cDropdown" optionKey="dbId" optionValue="name" value ="${company}"
                          name="companydropdown" from="${companies}"
                          onChange= 'loadFacilities(document.getElementById("cDropdown"))'>
                </g:select>
                <label>Facility:</label>
                <g:select id = 'facilitiesDropdown' optionKey="dbId" optionValue="name" value = "${facility}"
                          name="facilitydropdown" from="${facilities}"
                          onChange= 'loadUnits(document.getElementById("cDropdown"),
                    document.getElementById("facilitiesDropdown"), document.getElementById("climate"))'>
                </g:select>
            </div>
            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <label>Climate Controlled:</label>
                <select name="climate" id="climate" onChange='loadUnits(document.getElementById("cDropdown"),
                    document.getElementById("facilitiesDropdown"), document.getElementById("climate"))'>
                    <option disabled selected value> Select an option </option>
                    <option value="yes">Yes</option>
                    <option value="no">No</option>
                </select>
                <label>Unit:</label>
                <g:select id = 'units' optionKey="dbId" optionValue="name"
                          name="unitdropdown" from="${units}" noSelection="['null':'Select a Unit']"
                          onChange = 'showUnit(document.getElementById("facilitiesDropdown"), document.getElementById("units"))'>
                </g:select>
            </div>

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

    function loadUnits(c, e, cli) {
        var climate = cli.options[cli.selectedIndex].text;
        var cID = c.selectedIndex - 1;
        var cName = c.options[c.selectedIndex].text;
        var fID = e.selectedIndex - 1;
        var fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadUnits')}" + "?cID=" + cID + "&cName=" + cName + "&fID=" + fID + "&fName=" + fName + "&climate=" + climate;
    }
    function showUnit(f, e) {
        var fID = f.selectedIndex;
        var fName = f.options[f.selectedIndex].text;
        var uID = e.selectedIndex - 1;
        var uName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadUnitTable')}" + "?uID=" + uID + "&uName=" + uName + "&fID=" + fID + "&fName=" + fName;

    }
</g:javascript>
    </body>
</html>