<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="login"/>
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
        YOUR LOGIN FAILED.<br>

        Username: <input type="text" id="usernameField"><br>
        Password: <input type="text" id="passwordField"><br>

        <div class="col-sm text-center" style="margin-top: 50px">
          <button onclick = 'verify()' type="button" class="btn btn-outline-success">Log In</button></td>
        </div>
    </div>

    <footer class="footer">
        <div class="container">
            <span class="text-muted">&copy;2018 Nitrox Technologies</span>
        </div>
    </footer>
    <g:javascript>

    function verify()
    {
        var username = document.getElementById("usernameField").value;
        var password = document.getElementById("passwordField").value;
        window.location.href="${createLink(controller:'LocalGrailsCompany', action:'verify')}" + "?username=" + username + "&password=" + password;
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