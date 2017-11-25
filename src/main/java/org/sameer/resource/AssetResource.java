package org.sameer.resource;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sameer.bl.AssetBl;

import com.google.inject.Inject;

@Path("/asset")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssetResource {
    private AssetBl assetBl;

    @Inject
    public AssetResource(AssetBl assetBl) {
        this.assetBl = assetBl;
    }

    @POST
    public Response createAssetId() {
        Map<String, String> result = new HashMap<>();

        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAssetStatus(@NotNull @PathParam("id") String id, Map<String, String> status) {

        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getDowloadUrl(@NotNull @PathParam("id") String id, @QueryParam("timeout") Integer timeout) {
        Map<String, String> result = new HashMap<>();

        return Response.ok(result).build();
    }
}
