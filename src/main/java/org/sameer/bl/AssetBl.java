package org.sameer.bl;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.sameer.dao.CassandraDao;
import org.sameer.helper.S3Helper;

import com.amazonaws.HttpMethod;
import com.google.inject.Inject;

public class AssetBl {
    private final CassandraDao cassandraDao;
    private final String s3bucket;
    private final S3Helper s3Helper = new S3Helper();

    @Inject
    public AssetBl(CassandraDao cassandraDao, String s3bucket) {
        this.cassandraDao = cassandraDao;
        initCassandra();
        this.s3bucket = s3bucket;
    }

    public Map<String, String> createAsset() {
        HashMap<String, String> result = new HashMap<>();

        String id = UUID.randomUUID().toString();
        URL url = s3Helper.generateSignedUrl(s3bucket, id, HttpMethod.POST, 0);
        result.put("id", id);
        result.put("upload_url", url.toString());
        insertRow(id, AssetBlConstants.STATUS_CREATED);
        return result;
    }

    public void updateAsset(@NotNull String id, @NotNull String status) {
        insertRow(id, status);
    }

    public Map<String, String> getAsset(String id, Integer timeoutSeconds) {
        Map<String, String> row = getRow(id);
        if (row == null || !row.get("STATUS").toLowerCase().equals(AssetBlConstants.STATUS_UPLOADED))
            return null;
        Map<String, String > result = new HashMap<>();
        long useTimeout = timeoutSeconds == null? 60000L : timeoutSeconds * 1000L;
        String url = s3Helper.generateSignedUrl(s3bucket, id, HttpMethod.GET, useTimeout).toString();
        result.put("download_url", url);
        return result;
    }

    private void initCassandra() {
        cassandraDao.createKeyspaceIfNotExists(AssetBlConstants.keyspaceName, "SimpleStrategy", 3);
        Map<String, String> columnNameToTypes = new HashMap<>();
        columnNameToTypes.put("ID", "text");
        columnNameToTypes.put("STATUS", "text");
        List<String> primaryKey = new LinkedList<>();
        primaryKey.add("id");
        cassandraDao.createTableIfNotExists(AssetBlConstants.keyspaceName, AssetBlConstants.tableName, columnNameToTypes, primaryKey);
    }

    private void insertRow(String id, String status) {
        Map<String, String> columnNameToValue = new HashMap<>();
        columnNameToValue.put("ID", id);
        columnNameToValue.put("STATUS", status);
        cassandraDao.insertValues(AssetBlConstants.keyspaceName, AssetBlConstants.tableName, columnNameToValue);
    }

    private Map<String, String> getRow(String id) {
        List<Map<String, String>> results = cassandraDao.getValue(AssetBlConstants.keyspaceName, AssetBlConstants.tableName,
                "ID", id, Arrays.asList(new String[]{"ID", "STATUS"}));
        // As we are searching using the primary key, we will only get back one row.
        if(results.size() == 0)
            return null;
        return results.get(0);
    }
}
