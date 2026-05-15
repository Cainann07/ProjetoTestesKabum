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

public class TesteLogin {
    public static void main(String[] args) {

        FirefoxOptions opcoes = new FirefoxOptions();

        FirefoxProfile perfil = new FirefoxProfile();
         // Para o site achar que é um usuário comum
        perfil.setPreference("dom.webdriver.enabled", false);

        // Impede que o Firefox carregue extensões internas de automação
        perfil.setPreference("useAutomationExtension", false);

        // Bloqueia Pop-ups
        perfil.setPreference("dom.webnotifications.enabled", false);

        // Abafa a detecção de que o navegador é controlado por automação
        perfil.setPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:120.0) Gecko/20100101 Firefox/120.0");

        opcoes.setProfile(perfil);

        WebDriver driver = new FirefoxDriver(opcoes);
        try {
            driver.get("https://www.kabum.com.br");
            WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

            Thread.sleep(3000);

            WebElement botaoCadastrar = espera.until(ExpectedConditions.presenceOfElementLocated(By.id("linkLoginHeader")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", botaoCadastrar);

            // Esse seletor é o mais recomendado pois é único e o mais apropriado para testes
            WebElement campoEmail = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='check-login-input']")));
            campoEmail.sendKeys("mateuscainan14@gmail.com");

            WebElement botaoLogin = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ENTRAR']")));
            botaoLogin.click();

            String senhaLogin = System.getenv("SENHA_LOGIN_KABUM");
            WebElement campoSenha = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='password-input']")));
            campoSenha.sendKeys(senhaLogin);

            WebElement botaoEntrar = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='CONTINUAR']")));
            botaoEntrar.click();

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // FECHA O NAVEGADOR
            driver.quit();
        }
    }
}
