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
                        %{--<li class="navbar-nav mr-auto" id = "adminDropdownHead">--}%
                            %{--<a class="nav-link dropdown-toggle" id="adminDropdownLabel" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--}%
                                %{--Welcome <strong>${username}</strong>--}%
                            %{--</a>--}%
                            %{--<div class="dropdown-menu" id="adminDropdown" aria-labelledby="adminDropdownLabel">--}%
                                %{--<g:if test="${type == 'Admin'}">--}%
                                %{--<a class="dropdown-item" href="${createLink(controller:'LocalGrailsCompany' ,action:'showUsers')}">Manage Users</a>--}%
                                %{--</g:if>--}%
                                %{--<a class="dropdown-item" href="#">Anything</a>--}%
                                %{--<div class="dropdown-divider"></div>--}%
                                %{--<a class="dropdown-item" href="#">Something else here</a>--}%
                            %{--</div>--}%
                        %{--</li>--}%
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
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </div>
            </div>
        </nav>
            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <label>Company:</label>
                <g:select id = "cDropdown" optionKey="dbId" optionValue="name"
                          name="companydropdown" from="${companies}" value = ""
                          onChange= 'loadFacilities(document.getElementById("cDropdown"))' noSelection="['null':'Select a Company']">
                </g:select>
            </div>

            <div class="col-sm text-center" style="margin-top: 50px">
                <button onclick = 'login(document.getElementById("cDropdown"))' type="button" class="btn btn-outline-success">Log In</button></td>
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
</g:javascript>
    %{--<asset:javascript src="application.js"/>--}%
    <script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
    <script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
    %{--<script type="text/javascript" src="/assets/bootstrap.bundle.js?compile=true" ></script>--}%
    <script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
    %{--<script type="text/javascript" src="/assets/bootstrap.min.js?compile=true" ></script>--}%
    %{--<script type="text/javascript" src="/assets/jquery-2.2.0.min.js?compile=true" ></script>--}%
    %{--<script type="text/javascript" src="/assets/application.js?compile=true" ></script>--}%
    </body>
</html>