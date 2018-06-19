package storagedbwebapp

class UrlMappings {

    static mappings = {

//        "/"(controller: "LocalGrailsCompany", view:"/index")
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/LocalGrailsCompany/login")
        "500"(view:'/error')

    }
}
