package ferranti.bikerbikus;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class SeleniumTest {

    @Test
    public  static void main( String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://remotemysql.com/phpmyadmin/index.php");

        //login
        driver.findElement(By.xpath("//*[@id=\"input_username\"]")).sendKeys("UUS95gGFLJ");
        driver.findElement(By.xpath("//*[@id=\"input_password\"]")).sendKeys("LVbcF3JyvK");

        driver.findElement(By.xpath("//*[@id=\"input_go\"]")).click();

        //select db
        driver.findElement(By.xpath("//*[@id=\"pma_navigation_tree_content\"]/ul/li/a")).click();

        //aspetta finche non compare lista di tabelle
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pma_navigation_tree_content\"]/ul/li/div[4]/ul/li[1]/a"))).findElement(By.xpath("//*[@id=\"topmenu\"]/li[2]/a")).click();

        //select sqlquery
        //aspetta finche non compare pannello query
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"sqlquerycontainerfull\"]/div[1]/div[6]")));


        new WebDriverWait(driver, 10).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"sqlquerycontainerfull\"]/div[1]/div[6]/div[1]/div/div/div/div[5]/div/pre")));

        new WebDriverWait(driver, 10).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"sqlquerycontainerfull\"]/div[1]/div[6]/div[1]/div/div/div/div[5]/div/pre/span/span")));

        driver.findElement(By.xpath("//*[@id=\"sqlquerycontainerfull\"]/div[1]/div[6]/div[1]/div/div/div/div[5]/div/pre")).click();

        driver.switchTo().activeElement().sendKeys("SELECT * FROM Utente WHERE TipoUtente = 3");

        driver.findElement(By.xpath("//*[@id=\"button_submit_query\"]")).submit();

        new WebDriverWait(driver, 15).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@id, 'resultsForm')]/div[1]/div/table[2]")));

        List<WebElement> rows = driver.findElements(By.xpath("//*[contains(@id, 'resultsForm')]/div[1]/div/table[2]/tbody/tr"));

        int expecteAdvanceMaster= 1;
        Assert.assertTrue(rows.size() >= expecteAdvanceMaster);
        if(rows.size() >= expecteAdvanceMaster){
            System.out.println("Test passed");
        }else{
            System.out.println("Test failed");
        }
        driver.close();
    }
}