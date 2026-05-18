package testes;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static testes.TesteLogin.Login;

public class TestePesquisa {

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getDriver();

        // Criação do Mouse Virtual
        org.openqa.selenium.interactions.Actions acoesMouse = new org.openqa.selenium.interactions.Actions(driver);

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            Login(driver);

            Thread.sleep(1000);

            WebElement btnHardware = espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputBusca")));
            btnHardware.click();
            btnHardware.sendKeys("Memoria Ram 16Gb" , Keys.ENTER);

            WebElement Anuncio01 = espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"listing\"]/div[3]/div/div/div[2]/div[1]/main/a[1]")));
            Anuncio01.click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // FECHA O NAVEGADOR
            driver.quit();
        }
    }
}
