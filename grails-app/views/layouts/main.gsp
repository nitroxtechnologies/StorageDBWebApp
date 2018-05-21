<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <!-- Latest compiled and minified Bootstrap -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    </head>
    <body>
    <div class="container">
        <div class="col-lg-12 text-center" style="margin-top: 50px">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <g:each in="${companies}" var="company" status="i">
                        <li><a href="#">${company.name} (${company.id})</a></li>
                    </g:each>
                </ul>
            </div>
            %{--<table class="table">--}%
                %{--<thead>--}%
                %{--<tr>--}%
                    %{--<th scope="col">ID</th>--}%
                    %{--<th scope="col">Company Name</th>--}%
                %{--</tr>--}%
                %{--</thead>--}%
                %{--<tbody>--}%
                %{--<g:each in="${companies}" var="company" status="i">--}%
                    %{--<tr>--}%
                        %{--<th scope = "row"> ${company.id}</th>--}%
                        %{--<td class="text-left">${company.name}</td>--}%
                    %{--</tr>--}%
                %{--</g:each>--}%
                %{--<tr>--}%
                    %{--<th scope="row">1</th>--}%
                    %{--<td>Mark</td>--}%
                    %{--<td>Otto</td>--}%
                    %{--<td>@mdo</td>--}%
                %{--</tr>--}%
                %{--<tr>--}%
                    %{--<th scope="row">2</th>--}%
                    %{--<td>Jacob</td>--}%
                    %{--<td>Thornton</td>--}%
                    %{--<td>@fat</td>--}%
                %{--</tr>--}%
                %{--<tr>--}%
                    %{--<th scope="row">3</th>--}%
                    %{--<td>Larry</td>--}%
                    %{--<td>the Bird</td>--}%
                    %{--<td>@twitter</td>--}%
                %{--</tr>--}%
                %{--</tbody>--}%
            %{--</table>--}%
        </div>
    </div>
    </body>
</html>



