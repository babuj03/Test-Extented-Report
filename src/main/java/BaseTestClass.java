import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementShould;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class BaseExtentedReport implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback {
    static ExtentReports reports;
    static ExtentTest test;

    private static final Logger logger = LoggerFactory.getLogger(BaseTestClass.class);
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        String testClassName = context.getDisplayName();
        String filename = System.getProperty("user.dir") + "/build/reports/" +testClassName + "_Results.html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filename);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://ww.google.co.uk/";
        Configuration.screenshots=false;

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        test = reports.createTest(context.getDisplayName());

        logger.info( context.getDisplayName() + " - started^^^^^^^^");

    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (!context.getExecutionException().isPresent()) {
            test.pass(context.getDisplayName() + " - passed");
        } else {
            String dest ="";
            test.fail(context.getExecutionException().get().getLocalizedMessage());
            WebDriver driver = WebDriverRunner.getWebDriver();
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                dest =  "/build/reports/screenshots/" + context.getDisplayName().replaceAll(" ","_") + ".jpg";
                File destination = new File( System.getProperty("user.dir") + dest);
                FileUtils.copyFile(source, destination);
                System.out.println("Screenshot taken at " + dest);

            } catch (IOException e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
            test.fail("screen shot", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
      }
    }

    //SelenideExtentReports/build/reports/screenshots




    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        reports.flush();
        Selenide.close();
    }


}
