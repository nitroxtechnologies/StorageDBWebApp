<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Companies</title>
	</head>
	<body>
        <g:each in="${companies}" var="company" status="i">
            <h3>${i+1}. ${company.id}, ${company.name}</h3>
            <br/>
        </g:each>
    </body>
</html>