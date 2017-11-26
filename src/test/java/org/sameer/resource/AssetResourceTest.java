package org.sameer.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.sameer.bl.AssetBl;

import io.dropwizard.testing.junit.ResourceTestRule;

public class AssetResourceTest {
    private static final AssetBl mockAssetBl = Mockito.mock(AssetBl.class);
    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder().addResource(new AssetResource(mockAssetBl)).build();
    private static final Map<String, String> mockCreateResult = new HashMap<>();
    private static final Map<String, String> mockGetResult = new HashMap<>();

    private static final String id = "bbe72d6f-829c-46f4-ab4d-0a2a9ae5dd01";
    private static final String url = "https://sameerplayground.s3.us-west-1.amazonaws.com/235e6bb2-f213-49c8-8a44-c941d9d7ac16?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20171125T223758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=AKIAJPLPMUTD3JM2HICA%2F20171125%2Fus-west-1%2Fs3%2Faws4_request&X-Amz-Signature=256aeeeca2ca46b6418773a15b5a1b7534c631619748495972bba6589009152d\"";

    @Before
    public void setUp() throws Exception {
       mockCreateResult.put("id", id);
       mockCreateResult.put("upload_url", url);

       mockGetResult.put("download_url", url);
    }

    @Test
    public void WHEN_createResource_THEN_returnCorrectResult() throws Exception {
        when(mockAssetBl.createAsset()).thenReturn(mockCreateResult);

        Response response = resource.target("/asset").request().post(Entity.json(null));
        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, String> result = response.readEntity(Map.class);
        assertThat(result).isEqualTo(mockCreateResult);
    }

    @Test
    public void WHEN_updateResource_THEN_returnCorrectResult() throws Exception {
        doNothing().when(mockAssetBl).updateAsset(id, "uploaded");

        Map<String, String> input = new HashMap<>();
        input.put("status", "uploaded");
        Response response = resource.target("/asset/" + id).request().put(Entity.json(input));
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void WHEN_getResource_THEN_returnCorrectResult () {
        when(mockAssetBl.getAsset(id, 300)).thenReturn(mockGetResult);

        Response response = resource.target("/asset/" + id).queryParam("timeout", "300").request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, String> result = response.readEntity(Map.class);
        assertThat(result).isEqualTo(mockGetResult);
    }

}
