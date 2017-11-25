package app;

import config.AssetAppConfig;
import config.CassandraConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetApp extends Application<AssetAppConfig>{
    @Override
    public void run(AssetAppConfig assetAppConfig, Environment environment) throws Exception {
        CassandraConfig cassandraConfig = assetAppConfig.getCassandraConfig();
        log.info(cassandraConfig.toString());
    }

    public static void main(String [] args) throws Exception {
        new AssetApp().run(args);
    }
}
