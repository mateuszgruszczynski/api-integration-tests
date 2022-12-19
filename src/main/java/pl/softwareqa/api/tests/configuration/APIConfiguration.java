package pl.softwareqa.api.tests.configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class APIConfiguration {

    private static Config rootConfig = ConfigFactory.load();
    private static String environment = rootConfig.getString("environment");
    private static Config envConfig = rootConfig.getConfig(environment);

    public static String rootUrl = envConfig.getString("rootUrl");
}
