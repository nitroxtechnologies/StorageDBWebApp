<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
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
            <div id="myDiv"><!-- Plotly chart will be drawn inside this DIV --></div>
            <script>
            </script>
        </div>

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