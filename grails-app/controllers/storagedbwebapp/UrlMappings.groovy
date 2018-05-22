package storagedbwebapp

class UrlMappings {

    static mappings = {

//        "/"(controller: "LocalGrailsCompany", view:"/index")
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')

    }
}
