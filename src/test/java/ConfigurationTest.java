import com.deb.processing.utils.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {
    Configuration config;
    @Before
    public void setUp() throws Exception {
        config = new Configuration(System.getProperty("user.dir")
                + "/src/test/resources/test_file.properties");

    }

    @Test
    public void testGet() {
        Assert.assertEquals("value1", config.getConfigValue("key1"));
    }


}
