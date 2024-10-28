package Admin.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.time.Duration;

public class T1NormalLogin {
    WebDriver driver;

    @BeforeTest
    public void createDriver() {
        driver = new ChromeDriver();
        // Get the screen resolution for the second monitor
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        if (screens.length > 1) {
            // If there's more than one screen, use the second screen
            Rectangle secondScreenBounds = screens[1].getDefaultConfiguration().getBounds();
            // Set the browser window's position to the second screen
            driver.manage().window().setPosition(new org.openqa.selenium.Point(
                    secondScreenBounds.x + 100,
                    secondScreenBounds.y + 100));
        } else {
            System.out.println("Only one screen detected, opening browser on the primary screen.");
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    //@BeforeMethod
//public void accessPage(){
//    driver.get("http://dev3.vitex.asia:8009/admin/login");
//}
    @Test(priority = 1)
    public void checkCorrectPage() {
        driver.get("http://dev3.vitex.asia:8009/admin/login");

        Assert.assertEquals(driver.findElement(By.xpath("//button[@type='submit']")).getText(), "LOGIN", "You are in wrong page");
    }

    @Test(priority = 2)
    public void loginSuccess() throws InterruptedException {
        //Enter email
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("clickdoc.dev@gmail.com");
        Thread.sleep(2000);
        //PW
        driver.findElement(By.id("password")).sendKeys("nnN3ZYjw");
        Thread.sleep(2000);
        //Click login
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        String actualUserName = "Dev CLICKDOC";
        String expectedUserName = driver.findElement(By.xpath("//a[@class='user-profile dropdown-toggle']")).getText();

        // Assertion to check if the displayed username matches the expected username
        Assert.assertEquals(actualUserName, expectedUserName, "Login fail");
    }

    @Test(priority = 3)
    public void blankEmail() throws InterruptedException {
        driver.findElement(By.xpath("//a[@class='user-profile dropdown-toggle']")).click();

//         Click "Log Out"
        driver.findElement(By.xpath("//*[normalize-space()='Log Out']")).click();

        //Click login
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        String actualErrorMessage = "The email field is required.";
        String expectedErrorMessage = driver.findElement(By.xpath("//li[normalize-space()='The email field is required.']")).getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 4)

    public void blankPassword() throws InterruptedException {
        driver.get("http://dev3.vitex.asia:8009/admin/login");

        //Click login
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        String actualErrorMessage = "The password field is required.";
        String expectedErrorMessage = driver.findElement(By.xpath("//li[normalize-space()='The password field is required.']")).getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 5)
    public void wrongEmail() throws InterruptedException {
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("clickdoc.dev@yopmail.com");
        Thread.sleep(1000);
        driver.findElement(By.id("password")).sendKeys("nnN3ZYjw");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        String actualErrorMessage = "The email address or password you have entered was not correct. Please try again!";
        String expectedErrorMessage = driver.findElement(By.xpath("//li[contains(text(),'The email address or password you have entered was not')]")).getText();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);

    }

    @Test(priority = 6)
    public void wrongPassword() throws InterruptedException {
        driver.get("http://dev3.vitex.asia:8009/admin/login");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("clickdoc.dev@gmail.com");
        Thread.sleep(1000);
        driver.findElement(By.id("password")).sendKeys("nnN3ZYjw123");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        String actualErrorMessage = "The email address or password you have entered was not correct. Please try again!";
        String expectedErrorMessage = driver.findElement(By.xpath("//li[contains(text(),'The email address or password you have entered was not')]")).getText();


        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
