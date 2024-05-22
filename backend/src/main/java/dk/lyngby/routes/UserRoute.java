package dk.lyngby.routes;

import dk.lyngby.controller.impl.UserController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * @author Carsten Juhl
 * This Endpointgroup contains endpoints related to Userinfo
 */


public class UserRoute {

        private final UserController rc = new UserController();

        public EndpointGroup getInfo(){
            return () -> {
                path("/passengerinfo", () -> {
                    get("/{id}", rc::read);
                });
            };
        }
    }

