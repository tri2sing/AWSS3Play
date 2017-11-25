package org.sameer.resource;

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
import org.sameer.bl.AssetBlConstants;

import com.google.inject.Inject;

@Path("/asset")
@Consumes(MediaType.APPLICATION_JSON)
public class AssetResource {
    private AssetBl assetBl;

    @Inject
    public AssetResource(AssetBl assetBl) {
        this.assetBl = assetBl;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAsset() {
        Map<String, String> result = assetBl.createAsset();
        return Response.ok(result).type(MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAsset(@NotNull @PathParam("id") String id, @NotNull Map<String, String> body) {
        String status = body.get("status");
        if(status == null || !status.toLowerCase().equals(AssetBlConstants.STATUS_UPLOADED))
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect status information").build();
        assetBl.updateAsset(id, status);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsset(@NotNull @PathParam("id") String id, @QueryParam("timeout") Integer timeoutSeconds) {
        Map<String, String> result = assetBl.getAsset(id, timeoutSeconds);
        if (result == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect asset id or status").build();
        return Response.ok(result).type(MediaType.APPLICATION_JSON).build();
    }
}
