package org.sameer.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sameer.dao.CassandraDao;

public class CassandraApp {
    public static void main(String[] args) {
        CassandraDao cd = new CassandraDao("127.0.0.1", 9042);
        cd.createKeyspaceIfNotExists("sameer", "SimpleStrategy", 3);
        Map<String, String> columnNameToTypes = new HashMap<>();
        columnNameToTypes.put("ENTITYID", "text");
        columnNameToTypes.put("STATE", "text");
        List<String> primaryKey = new LinkedList<>();
        primaryKey.add("ENTITYID");
        cd.createTableIfNotExists("sameer", "table1", columnNameToTypes, primaryKey);

        Map<String, String> columnNameToValue = new HashMap<>();
        columnNameToValue.put("ENTITYID", "sameer-id-1");
        columnNameToValue.put("STATE", "created");
        cd.insertValues("sameer", "table1", columnNameToValue);
        List<Map<String, String>> results = cd.getValue("sameer", "table1",
                "ENTITYID", "sameer-id-1", Arrays.asList(new String[]{"ENTITYID", "STATE"}));

        System.out.println("results");
        for (Map<String, String> row : results) {
            System.out.println(row.get("ENTITYID") + ": " + row.get("STATE"));
        }
        columnNameToValue.put("STATE", "updated");
        cd.insertValues("sameer", "table1", columnNameToValue);
        results = cd.getValue("sameer", "table1",
                "ENTITYID", "sameer-id-1", Arrays.asList(new String[]{"ENTITYID", "STATE"}));

        System.out.println("results");
        for (Map<String, String> row : results) {
            System.out.println(row.get("ENTITYID") + ": " + row.get("STATE"));
        }
        cd.close();
    }

}
