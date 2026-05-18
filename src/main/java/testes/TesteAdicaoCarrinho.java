package testes;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static testes.TesteLogin.Login;

public class TesteAdicaoCarrinho {
    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getDriver();

        // Criação do Mouse Virtual
        org.openqa.selenium.interactions.Actions acoesMouse = new org.openqa.selenium.interactions.Actions(driver);

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            Login(driver);

            Thread.sleep(1000);

            WebElement btnHardware = espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-container\"]/header/div/div[2]/div[2]/div/div[2]/div[2]/a[1]")));
            btnHardware.click();

            Thread.sleep(3000);

            WebElement produtoSelecionado = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='listing']/div[3]/div/div/div[2]/div/main//a[contains(@href, '/produto/')])[2]")));
            produtoSelecionado.click();

            Thread.sleep(3000);

            WebElement adicionaProdutoAoCarrinho = espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label='Adicionar ao carrinho']")));

            acoesMouse.moveToElement(adicionaProdutoAoCarrinho).click().perform();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
