<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
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

            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <label>Company:</label>
                <g:select id = "cDropdown" optionKey="dbId" optionValue="name"
                          name="companydropdown" from="${companies}" value = ""
                          onChange= 'loadFacilities(document.getElementById("cDropdown"))' noSelection="['null':'Select a Company']">
                </g:select>
            </div>
    </div>

    <g:javascript>

   function loadFacilities(e)
   {
        var cID = e.selectedIndex - 1;
        var cName = e.options[e.selectedIndex].text;
       window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadFacilities')}" + "?cID=" + cID + "&cName=" + cName;
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