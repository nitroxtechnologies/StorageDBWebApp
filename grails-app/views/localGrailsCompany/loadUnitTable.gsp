<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <div class="container">
            <div class="col-lg-12 text-center" style="margin-top: 50px">
                <label>Company:</label>
                <g:select id = "cDropdown" optionKey="dbId" optionValue="name" value ="${company}"
                          name="companydropdown" from="${companies}"
                          onChange= 'loadFacilities(document.getElementById("cDropdown"))'>
                </g:select>
                <label>Facility:</label>
                <g:select id = 'facilitiesDropdown' optionKey="dbId" optionValue="name" value = "${facility}"
                          name="facilitydropdown" from="${facilities}"
                          onChange= 'loadUnits(document.getElementById("cDropdown"), document.getElementById("facilitiesDropdown"))'>
                </g:select>
            </div>
            <div class ="row">
                <div class="col-sm text-center" style="margin-top: 50px">
                    <button onclick = "compare(document.getElementById('facilitiesDropdown'))" type="button" class="btn btn-outline-success">Compare Prices!</button></td>
                </div>
                <div class="col-sm text-center" style="margin-top: 50px">
                    <button onclick = "edit(document.getElementById('facilitiesDropdown'))" type="button" class="btn btn-outline-primary">Edit Facility</button></td>
                </div>
            </div>
            %{--<div class="col-lg-12 text-center" style="margin-top: 20px">--}%
                %{--<label>Climate Controlled:</label>--}%
                %{--<select name="climate" id="climate" onChange='filterTable(document.getElementById("climate"), document.getElementById("units"))'>--}%
                    %{--<option selected value="all"> All </option>--}%
                    %{--<option value="yes">Climate</option>--}%
                    %{--<option value="no">Non-Climate</option>--}%
                %{--</select>--}%
                %{--<label>Unit:</label>--}%
                %{--<g:select id = 'units' optionKey="dbId" optionValue="name"--}%
                          %{--name="unitdropdown" from="${units}"--}%
                          %{--onChange = 'filterTable(document.getElementById("units"), document.getElementById("climate"))'--}%
                          %{--noSelection="['null':'All']">--}%
                %{--</g:select>--}%
            %{--</div>--}%
            <table id = "unitTable" class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    %{--<th scope="col">ID</th>--}%
                    <th scope="col">Unit Type</th>
                    <th scope="col">Width</th>
                    <th scope="col">Depth</th>
                    <th scope="col">Floor</th>
                    <th scope="col" class="text-center">Climate Controlled?</th>
                    <th scope="col" class="text-center">Price Updated Time</th>
                    <th scope="col" class="text-right">Price</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${units}" var="unit" status="i">
                    <tr class = "entries ${unit.name} ${unit.type}">
                        <td class="text-left">${unit.name}</td>
                        <td class="text-left">${unit.width}</td>
                        <td class="text-left">${unit.depth}</td>
                        <td class="text-left">${unit.floor}</td>
                        <td class="text-center" style="width: 10px" >${unit.type}</td>
                        <td class="text-center">${unit.time}</td>
                        <g:each in="${unit.prices}" var="price" status="j">
                            <td class="text-right">${price.displayPrice}</td>
                        </g:each>
                        <td class="text-center"><button onclick='graph("${unit.name}", "${unit.type}", "${unit.floor}", "${unit.rateType}")' type="submit" class="btn btn-link"><i class="fa fa-line-chart" aria-hidden="true"></i></button></td>

                    </tr>
                </g:each>
                </tbody>
            </table>

        </div>
    <g:javascript>

    function loadFacilities(e){
        var cID = e.selectedIndex;
        var cName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadFacilities')}" + "?cID=" + cID + "&cName=" + cName;
    }

    function loadUnits(c, e) {
        var cID = c.selectedIndex;
        var cName = c.options[c.selectedIndex].text;
        var fID = e.selectedIndex + 1;
        var fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadUnitTable')}" + "?cID=" + cID + "&cName=" + cName + "&fID=" + fID + "&fName=" + fName;
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

    function compare(e) {
        var fID = e.selectedIndex;
        var fName = fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'compare')}" + "?fID=" + fID + "&fName=" + fName + "&initialLoad=a";

    }

    function edit(e) {
        var fID = e.selectedIndex;
        var fName = fName = e.options[e.selectedIndex].text;
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'edit')}" + "?fID=" + fID + "&fName=" + fName;
    }

    function graph(n, c, f, r) {
        window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'graph')}" + "?uName=" + n + "&uType=" + c + "&uFloor=" + f + "&rateType=" + r;
    }

    </g:javascript>

    </body>
</html>