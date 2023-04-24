import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class BaseExtendedReport implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback {
    static ExtentReports reports;
    static ExtentTest test;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        String testClassName = context.getDisplayName();
        String filename = System.getProperty("user.dir") + "/build/reports/" +testClassName + "_Results.html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filename);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        Configuration.startMaximized = true;
        Configuration.screenshots=false;

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        test = reports.createTest(context.getDisplayName());
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (!context.getExecutionException().isPresent()) {
            test.pass(context.getDisplayName() + " - passed");
        } else {
            String dest ="";
            if (context.getExecutionException().isPresent()) {
                Throwable exception = context.getExecutionException().orElse(null);
                String stackTrace = ExceptionUtils.getStackTrace(exception);
                test.fail("<pre style='max-height: 400px; overflow-y: auto'><font color='#800000'>"+stackTrace+"</font></pre>");
            }
            WebDriver driver = WebDriverRunner.getWebDriver();
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                dest =  "/build/reports/screenshots/" + context.getDisplayName().replaceAll(" ","_") + ".png";
                File destination = new File( System.getProperty("user.dir") + dest);
                FileUtils.copyFile(source, destination);
                System.out.println("Screenshot taken at " + dest);

            } catch (IOException e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
            test.fail(
                    "<font color='#800000'>Screen shot</font>", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());

        }
    }


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        reports.flush();
        Selenide.closeWindow();
    }


}
