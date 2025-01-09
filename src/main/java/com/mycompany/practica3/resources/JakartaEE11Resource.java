package com.mycompany.practica3.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Ivan
 */
@Path("jakartaee11")
public class JakartaEE11Resource {
    
    /**
     *
     * @return
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
