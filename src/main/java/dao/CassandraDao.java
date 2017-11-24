package dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;

public class CassandraDao {
    private Cluster cluster;
    private Session session;

    public CassandraDao(String clusterNode, Integer port) {
        Cluster.Builder builder = Cluster.builder().addContactPoint(clusterNode);
        if (port != null) {
            builder.withPort(port);
        }
        cluster = builder.build();
        session = cluster.connect();
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public void createKeyspaceIfNotExists(String keyspaceName, String replicationStrategy, int replicationFactor) {
        StringBuffer sb = new StringBuffer("create keyspace if not exists ").append(keyspaceName)
                .append(" with replication = {")
                .append("'class': '").append(replicationStrategy).append(("', "))
                .append("'replication_factor': ").append(replicationFactor)
                .append("};");
        session.execute(sb.toString());
    }

    public void createTable(String keyspaceName, String tableName, Map<String, String> columnNameToTypes, List<String> primaryKeys) {
        StringBuffer cql = new StringBuffer("create table if not exists ").append(keyspaceName).append(".").append(tableName).append(" (");
        for (Map.Entry<String, String> column : columnNameToTypes.entrySet()) {
            cql.append(column.getKey()).append(" ").append(column.getValue()).append((", "));
        }
        cql.append("primary key (");
        cql.append(String.join(",", primaryKeys));
        cql.append("));");
        session.execute(cql.toString());
    }

    public void insertValues (String keyspaceName, String tableName, Map<String, String> columnNameToValues) {
        StringBuffer cql = new StringBuffer("insert into ").append(keyspaceName).append(".").append(tableName).append(" ");
        StringBuffer names = new StringBuffer("(");
        StringBuffer holders = new StringBuffer("(");
        List<String> values = new LinkedList<>();
        for (Map.Entry<String, String> column : columnNameToValues.entrySet()) {
            names.append(column.getKey()).append(",");
            holders.append("?,");
            values.add(column.getValue());
        }
        // The last character has a comman which is not needed.
        names.setCharAt(names.length() - 1, ')');
        holders.setCharAt(holders.length() - 1, ')');
        cql.append(names);
        cql.append(" VALUES ");
        cql.append(holders);
        session.execute(new SimpleStatement(cql.toString(), values.toArray()));
    }

    public List<Map<String, String>> getValue (String keyspaceName, String tableName, String columnName, String matchingValue, List<String> columnsWanted) {
        List<Map<String, String>> result = new LinkedList<>();
        StringBuffer cql = new StringBuffer("select ").append(String.join(",", columnsWanted));
        cql.append(" FROM ").append(keyspaceName).append(".").append(tableName).append(" ");
        cql.append("WHERE ").append(columnName).append(" = ?");
        ResultSet rs = session.execute(new SimpleStatement(cql.toString(), matchingValue));
        for (Row row : rs) {
            Map<String , String> rowMap = new HashMap<>();
            for(String name: columnsWanted) {
                rowMap.put(name, row.getString(name));
            }
            result.add(rowMap);
        }
        return result;
    }

}
