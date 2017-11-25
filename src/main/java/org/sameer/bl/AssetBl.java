package org.sameer.bl;

import org.sameer.dao.CassandraDao;

import com.google.inject.Inject;

public class AssetBl {
    private CassandraDao cassandraDao;

    @Inject
    public AssetBl(CassandraDao cassandraDao) {
        this.cassandraDao = cassandraDao;
    }
}
