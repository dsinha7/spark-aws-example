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
        processor = new CreditProcessor("/Users/dsinha/Downloads/data-engineering-challenge/peerIQData.csv");
        data = processor.readCSVLocalFile(spark);
    }
    @Test
    public void readFile() {
        long count = processor.filterByStatusAndPurpose(data).count();
        Assert.assertEquals(24, count);
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
