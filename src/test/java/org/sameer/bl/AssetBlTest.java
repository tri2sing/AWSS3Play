package org.sameer.bl;

import static org.mockito.Mockito.doNothing;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sameer.dao.CassandraDao;

public class AssetBlTest {
    private static final CassandraDao mockCassandraDao = Mockito.mock(CassandraDao.class);
    private AssetBl assetBl = new AssetBl(mockCassandraDao, "mybucket");

    @Before
    public void setUp() {
    }

    @Test
    public void updateAsset() throws Exception {
        Map<String, String> columnNameToValue = new HashMap<>();
        columnNameToValue.put("ENTITYID", "sameer-id-1");
        columnNameToValue.put("STATE", "uploaded");
        doNothing().when(mockCassandraDao).insertValues("sameer", "table1", columnNameToValue);

        assetBl.updateAsset("sameer-entity-id-1", "uploaded");
    }

}
