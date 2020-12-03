import com.deb.processing.CreditProcessor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreditProcessingTest {

    SparkSession spark;
    CreditProcessor processor;
    Dataset<Row> data;

    @Before
    public void setUp() throws Exception {

        spark = SparkSession
                .builder().master("local[*]")
                .getOrCreate();
        //use the local config file with corect parameters
        String testConfigFile = System.getProperty("user.dir") + "/config/config_local.properties";
        processor = new CreditProcessor(testConfigFile);
        data = processor.readCSVLocalFile(spark);
    }
    @Test
    public void readFile() {
        long count = processor.readCSVLocalFile(spark).count();
        Assert.assertEquals(31, count);
    }
    @Test
    public void recordCountTestAfterFilter1() {
        long count = processor.filterByStatusAndPurpose(data).count();
        Assert.assertEquals(24, count);
    }

    @Test
    public void recordCountTestAfterFilter2() {
        long count = processor.filterByCreditScore(
                processor.filterByStatusAndPurpose(data)).count();
        Assert.assertEquals(11, count);
    }

    @After
    public void tearDown() throws Exception {
        spark.stop();
    }
}
