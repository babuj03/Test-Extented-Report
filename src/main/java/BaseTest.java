import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeEach
    public void setUp() {
        // initialize WebDriver and open browser
        //System.setProperty("webdriver.chrome.driver","/Users/jbabu/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/Users/jbabu/Downloads/chromedriver");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // initialize ExtentReports and ExtentTest instances
        extent = new ExtentReports();
        test = extent.createTest(getClass().getSimpleName());
    }

    @AfterEach
    public void tearDown() {
        // close browser and clean up resources
        driver.quit();

        // flush the ExtentReports instance
        extent.flush();
    }

    public void log(String message) {
        test.log(Status.INFO, message);
    }
}
