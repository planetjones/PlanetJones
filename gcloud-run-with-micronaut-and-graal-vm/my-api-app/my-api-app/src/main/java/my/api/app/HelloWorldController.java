package my.api.app;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/helloworld") 
public class HelloWorldController {

    @Get(produces = MediaType.TEXT_PLAIN) 
    public String index() {
        return "Hello PlanetJones World"; 
    }
}