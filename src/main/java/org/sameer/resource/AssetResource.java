package org.sameer.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/asset")
@Produces(MediaType.APPLICATION_JSON)
public class AssetResource {

    @GET
    @Path("/{id}")
    public Response getDowloadUrl(@PathParam("id") String id) {
        Map<String, String> result = new HashMap<>();

        return Response.ok(result).build();
    }
}
