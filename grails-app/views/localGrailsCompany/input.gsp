<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="input"/>
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
            <div class="page-header" style="margin-top:20px; margin-bottom: 30px;">
                <h1>Submit a single unit</h1>
            </div>
            <form>
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label for="validationDefault01">Company name</label>
                        <input type="text" class="form-control" id="validationDefault01" placeholder="Company name" value="Green Storage Plus" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="validationDefault02">Facility name</label>
                        <input type="text" class="form-control" id="validationDefault02" placeholder="Facility name" value="Green Storage" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="validationDimensions">Unit Dimensions</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend2">inches</span>
                            </div>
                            <input type="text" class="form-control" id="validationDimensions" placeholder="10'x 5'" aria-describedby="inputGroupPrepend2" required>
                            <div class="input-group-append">
                                <span class="input-group-text">W x L</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="validationFloor">Floor</label>
                        <input type="text" class="form-control" id="validationFloor" placeholder="Floor" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="input-group col-md-6 mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input type="text" class="form-control" aria-label="Amount (to the nearest dollar)">
                        <div class="input-group-append">
                            <span class="input-group-text">.00</span>
                        </div>
                    </div>
                    <div class="form-check col-md-6" style="padding-left: 50px;">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                        <label class="form-check-label" for="defaultCheck1">
                            Climate controlled
                        </label>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Submit unit</button>
            </form>

            <div class="page-header" style = "margin-top:50px; margin-bottom:30px">
                <h1>Submit multiple units</h1>
            </div>

            <form>
                <div class="form-group">
                    <label for="exampleFormControlFile1">For multiple units, upload spreadsheet</label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1">
                </div>
                <button type="submit" class="btn btn-primary">Submit units</button>
                %{--<div class="custom-file">--}%
                    %{--<input type="file" class="custom-file-input" id="customFile">--}%
                    %{--<label class="custom-file-label" for="customFile">Choose file</label>--}%
                %{--</div>--}%
            </form>

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