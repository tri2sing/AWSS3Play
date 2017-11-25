package org.sameer.app;

import org.sameer.bl.AssetBl;
import org.sameer.config.AssetAppConfig;
import org.sameer.config.CassandraConfig;
import org.sameer.dao.CassandraDao;
import org.sameer.resource.AssetResource;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetApp extends Application<AssetAppConfig> {
    @Override
    public void run(AssetAppConfig assetAppConfig, Environment environment) throws Exception {
        Injector injector = createInjector(assetAppConfig);
        environment.jersey().register(new AssetResource(injector.getInstance(AssetBl.class)));
    }

    private Injector createInjector(AssetAppConfig assetAppConfig) {
        CassandraConfig cassandraConfig = assetAppConfig.getCassandraConfig();
        log.info(cassandraConfig.toString());
        CassandraDao cassandraDao = new CassandraDao(cassandraConfig.getClusterNode(), cassandraConfig.getPort());
        AssetBl assetBl = new AssetBl(cassandraDao, assetAppConfig.getS3bucket());
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CassandraDao.class).toInstance(cassandraDao);
                bind(AssetBl.class).toInstance(assetBl);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new AssetApp().run(args);
    }
}
