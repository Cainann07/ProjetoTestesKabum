package testes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static testes.TesteLogin.Login;

public class  TesteRemocaoCarrinho {

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getDriver();

        // Criação do Mouse Virtual
        org.openqa.selenium.interactions.Actions acoesMouse = new org.openqa.selenium.interactions.Actions(driver);

        try {
            WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

            Login(driver);

            Thread.sleep(5000);

            WebElement iconeCarrinho = espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("linkCarrinhoHeader")));
            iconeCarrinho.click();

            Thread.sleep(3000);

            WebElement excluirProdutos = espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("removerTodosProdutos")));
            excluirProdutos.click();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}