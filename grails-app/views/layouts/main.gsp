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
                <label for="countryddl" >Company:</label>
                <g:select id = "cDropdown" optionKey="id" optionValue="name"
                          name="companydropdown" from="${companies}"
                          onChange= 'goToPage(2)'>
                </g:select>

                <g:select id = 'facilities' optionKey="id" optionValue="name"
                          name="LocalGrailsFacility.name" from="${facilities}">
                </g:select>
            </div>

        </div>
    <g:javascript>
       function goToPage(cID){
           window.location.href="${createLink(controller:'LocalGrailsCompany' ,action:'loadFacilities')}" + "?cID=" + cID;
    }
</g:javascript>
    </body>
</html>



