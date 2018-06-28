<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="compareGraph"/>
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
            <div id="myDiv"><!-- Plotly chart will be drawn inside this DIV --></div>
            <script>
            </script>
        </div>
    <footer class="footer">
        <div class="container">
            <span class="text-muted">&copy;2018 Nitrox Technologies</span>
        </div>
    </footer>
    <script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
    <script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
    <script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
    <script type="text/javascript"src="/assets/plotly-latest.min.js?compile=true"></script>
    %{--<script type="text/javascript" src="/assets/chosen.jquery.min.js?compile=true" ></script>--}%
    <g:javascript>
        var dates = [];
        var prices = [];

        <g:each in="${dates}" var="date">
            dates.push("${date}");
        </g:each>

        <g:each in="${prices}" var="price">
            prices.push("${price}");
        </g:each>

        var data = [
          {
            x: dates,
            y: prices,
            type: 'scatter',
            name: 'Price'
          }
        ];

        var layout = {
          title: "${facilityName}"
        };
        Plotly.newPlot('myDiv', data, layout);

</g:javascript>
    </body>
</html>