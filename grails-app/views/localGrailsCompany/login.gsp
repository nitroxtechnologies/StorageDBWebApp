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
    <style>
    div.well{
        height: 250px;
    }

    .Absolute-Center {
        margin: auto;
        position: absolute;
        top: 0; left: 0; bottom: 0; right: 0;
    }

    .Absolute-Center.is-Responsive {
        width: 50%;
        height: 50%;
        min-width: 200px;
        max-width: 400px;
        padding: 40px;
    }

    #logo-container{
        margin: auto;
        margin-bottom: 10px;
        width:200px;
        height:30px;
        /*background-image:url('http://placehold.it/200x30/000000/ffffff/&text=BRAND+LOGO');*/
    }
    </style>

<body>
    <div class="container">
        <form class="Absolute-Center is-Responsive">
            <div id="logo-container"><span class="text-muted">&copy;2018 Nitrox Technologies</span></div>
            <div class="form-row">
                <div class="input-group col-md-12 mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></span>
                    </div>
                    <input type="text" id="usernameField" class="form-control" aria-label="username">
                </div>
            </div>
            <div class="form-row">
                <div class="input-group col-md-12 mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fa fa-lock" aria-hidden="true"></i></span>
                    </div>
                    <input type="password" class="form-control" id="passwordField" aria-label="password">
                </div>
            </div>
            <div class="form-group">
                <button id = "loginButton" onclick = 'verify()' type="button" class="btn btn-outline-info btn-block">Log in</button>
            </div>
        </form>
          %{--Username: <input type="text" id="usernameField"><br>--}%
          %{--Password: <input type="text" id="passwordField"><br>--}%

          %{--<div class="col-sm text-center" style="margin-top: 50px">--}%
              %{--<button onclick = 'verify()' type="button" class="btn btn-outline-success">Log In</button></td>--}%
          %{--</div>--}%
    </div>

    %{--<footer class="footer">--}%
        %{--<div class="container">--}%
            %{--<span class="text-muted">&copy;2018 Nitrox Technologies</span>--}%
        %{--</div>--}%
    %{--</footer>--}%
    <g:javascript>

        var input = document.getElementById("passwordField");

        // Execute a function when the user releases a key on the keyboard
        input.addEventListener("keyup", function(event) {
          // Cancel the default action, if needed
          event.preventDefault();
          // Number 13 is the "Enter" key on the keyboard
          if (event.keyCode === 13) {
            // Trigger the button element with a click
            document.getElementById("loginButton").click();
          }
        });

    function verify()
    {
        var username = document.getElementById("usernameField").value;
        var password = document.getElementById("passwordField").value;
        console.log(username);
        console.log(password);
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