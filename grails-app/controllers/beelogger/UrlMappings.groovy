package beelogger

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/beehives"(resources: 'beehive')
        "/measurements"(resources: 'measurement')
        "/sensors"(resources: 'sensor')
        "/sensortypes"(resources: 'sensorType')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
