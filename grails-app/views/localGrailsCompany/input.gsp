<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    %{--<asset:stylesheet src="application.css"/>--}%
    <link rel="stylesheet" href="/assets/bootstrap.css?compile=true" />
    %{--<link rel="stylesheet" href="/assets/grails.css?compile=true" />--}%
    <link rel="stylesheet" href="/assets/main.css?compile=true" />
    <link rel="stylesheet" href="/assets/font-awesome/css/font-awesome.min.css?compile=true">
    <script type="text/javascript" src="/assets/popper.min.js?compile=true" ></script>
    %{--<link rel="stylesheet" href="/assets/mobile.css?compile=true" />--}%
    %{--<link rel="stylesheet" href="/assets/application.css?compile=true" />--}%
    <style>
    .focusedInput {
        /*border-color: rgba(82,168,236,.8);*/
        /*outline: 0;*/
        /*outline: thin dotted \9;*/
        /*-moz-box-shadow: 0 0 8px rgba(82,168,236,.6);*/
        /*box-shadow: 0 0 8px rgba(82,168,236,.6) !important;*/
        outline-offset: -15px;
    }
    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    </style>
</head>
<body>
<div class="container">
    <div class ="row">
        <div class="col-sm page-header" style = "margin-top:30px">
            <h1>URLs to scrape data from</h1>
        </div>
        <div class="col-sm text-center" style="margin-top: 50px">
            <button type="button" class="btn btn-outline-primary">Add URL</button></td>
        </div>
        <div class="col-sm text-center" style="margin-top: 50px">
            <button onclick = "saveTable()" type="button" class="btn btn-outline-success">Update</button></td>
        </div>
    </div>
    <table id = "unitTable" class="table" style="margin-top: 50px">
        <thead>
        <tr>
            %{--<th scope="col">ID</th>--}%
            <th scope="col">URL</th>
            <th scope="col"> </th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${urlist}" var="url" status="i">
            <tr class = "">
            <td contenteditable="true" class="text-left">${url}</td>
            <td class="text-center"><button type="submit" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>


<script type="text/javascript" src="/assets/jquery-3.3.1.min.js?compile=true" ></script>
<script type="text/javascript" src="/assets/bootstrap.js?compile=true" ></script>
<script type="text/javascript" src="/assets/bootstrap.bundle.min.js?compile=true" ></script>
<g:javascript>

        $('#unitTable').on('click', 'button[type="submit"]', function () {
            if(confirm('Are you sure you want to delete this URL?'))
                $(this).closest('tr').remove();
        })

        $('.btn-outline-primary').click(function () {
            $('#unitTable').append('<tr class = ""><td contenteditable="true" class="text-left"> </td><td class="text-center"><button type="submit" class="btn btn-outline-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></td></tr>');
        });

        function saveTable() {
            var params = "";
            var table = document.getElementById("unitTable");
            var rows = table.getElementsByTagName("tr");
            var first = true;
            for (var i = 1; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName("td");
                for (var j = 0; j < cells.length; j++) {
                    if (j == 0 || j%8 != 0) {
                        // alert(cells[j].innerText);
                        if (first) {
                            params += "?";
                            first = false;
                        } else {
                            params += "&";
                        }

                        params += i;
                        params += j;
                        params += "=";
                        params += encodeURIComponent(cells[j].innerText);

                    }
                }
            }
            //params += "?ended=yup";
            params += "&reset=reset"
            window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'input')}" + params;

        }
</g:javascript>

</body>
</html>