<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Index</title>
	</head>
	<body>
	    <div class="dropdown">
          <button onclick="myFunction()" class="dropbtn">Dropdown</button>
          <div id="myDropdown" class="dropdown-content">
            <a href="#">Link 1</a>
            <a href="#">Link 2</a>
            <a href="#">Link 3</a>
          </div>
        </div>

        <g:each in="${companies}" var="company" status="i">
            <h3>${i+1}. ${company.id}, ${company.name}</h3>
            <br/>
        </g:each>
	</body>
</html>