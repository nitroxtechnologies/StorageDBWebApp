<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        %{--<asset:stylesheet src="application.css"/>--}%
        %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
        %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%

        <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
        <link rel="stylesheet" href="/assets/main.css?compile=true" />
        <link rel="stylesheet" href="/assets/font-awesome/css/font-awesome.css?compile=true">

        <script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
        <script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
        <script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
        <script type="text/javascript" src="/assets/bootbox.min.js?compile=true"></script>
        <script type="text/javascript" src="/assets/popper.min.js?compile=true" ></script>

        <g:layoutHead/>
    </head>
<body>

    <div class="container">
        <!-- As a heading -->
        <nav style ="margin-top: 50px" class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" style='color:black;'>    <img src="/assets/icon.png?compile=true" width="30" height="30" class="d-inline-block align-top" alt="">
                PriceDB</a>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${createLink(controller:'LocalGrailsCompany' ,action:'landing')}"" >Home</a>
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

        <g:layoutBody/>
    </div>
    <g:javascript>

    test()
    {
        alert("test");
    }
    $(document).ready(function () {
            $('.dropdown-toggle').dropdown();
        });
    </g:javascript>

    <footer class="footer">
        <div class="container">
            <span class="text-muted">&copy;2018 Nitrox Technologies</span>
        </div>
    </footer>
    </body>
</html>