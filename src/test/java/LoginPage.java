import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

  private WebDriver driver;

  private By emailField = By.name("email");
  private By passwordField = By.name("password");
  private By loginButton = By.cssSelector("button[type='submit']");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

  public void login(String email, String password) {
    driver.findElement(emailField).sendKeys(email);
    driver.findElement(passwordField).sendKeys(password);
    driver.findElement(loginButton).click();
  }
}
