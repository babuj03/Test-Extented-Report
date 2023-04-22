import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith(BaseTestClass.class)
@DisplayName("Testing PersonValidator")
class ExtentReportsTest1 {

    @Test
    @DisplayName("Open Google URL")
    void openUrl() {
        //setTestName(ExtentReportsTest1.class.getSimpleName());
        String url = "https://ww.google.co.uk";
        open(url);
    }



    @Test
    @DisplayName("Search for Selenide")
    void search() {
        $(By.name("q")).val("Selenide");
        $("div center:nth-child(2) >input[name='btnK']").click();
        $$(".g").get(0).shouldHave(text("Selenide: concise UI tests in JAVA"));
    }

    @Test
    @DisplayName("Verify Selenide Home Page")
    void goToSelenidePage() {
        $$(".g h3").get(0).click();
        $("div[class='short wiki'] h3").shouldHave(text("ЧТО ТАКОЕ SELENIDE?"));
    }
}
