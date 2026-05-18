package testes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TesteLogin {

    public static void Login(WebDriver driver) {

        try {
            driver.manage().window().maximize();

            driver.get("https://www.kabum.com.br");

            WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

            Thread.sleep(3000);

            WebElement botaoCadastrar = espera.until(ExpectedConditions.presenceOfElementLocated(By.id("linkLoginHeader")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", botaoCadastrar);

            // Esse seletor é o mais recomendado pois é único e o mais apropriado para testes
            String emailLogin = System.getenv("EMAIL_LOGIN_KABUM");
            WebElement campoEmail = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='check-login-input']")));
            campoEmail.sendKeys(emailLogin);

            WebElement botaoLogin = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ENTRAR']")));
            botaoLogin.click();


            String senhaLogin = System.getenv("SENHA_LOGIN_KABUM");
            WebElement campoSenha = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='password-input']")));
            campoSenha.sendKeys(senhaLogin);

            WebElement botaoEntrar = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='CONTINUAR']")));
            botaoEntrar.click();

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getDriver();

        Login(driver);
    }
}
