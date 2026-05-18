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

public class TesteAdicaoCarrinho {
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

        // Criação do Mouse Virtual
        org.openqa.selenium.interactions.Actions acoesMouse = new org.openqa.selenium.interactions.Actions(driver);

        try {
            driver.get("https://www.kabum.com.br");
            WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));
            Thread.sleep(5000);

            WebElement botaoEntrarHeader = espera.until(ExpectedConditions.elementToBeClickable(By.id("linkLoginHeader")));
            botaoEntrarHeader.click();

            WebElement campoEmail = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='check-login-input']")));
            campoEmail.sendKeys("mateuscainan14@gmail.com");
            campoEmail.sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            String senhaLogin = System.getenv("SENHA_LOGIN_KABUM");
            WebElement campoSenha = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
            campoSenha.sendKeys(senhaLogin);
            campoSenha.sendKeys(Keys.ENTER);

            Thread.sleep(10000);

            WebElement btnHardware = espera.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-container\"]/header/div/div[2]/div[2]/div/div[2]/div[2]/a[1]")));
            btnHardware.click();

            Thread.sleep(3000);

            WebElement produtoSelecionado = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='listing']/div[3]/div/div/div[2]/div/main//a[contains(@href, '/produto/')])[2]")));
            produtoSelecionado.click();

            Thread.sleep(3000);

            WebElement adicionaProdutoAoCarrinho = espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label='Adicionar ao carrinho']")));

            acoesMouse.moveToElement(adicionaProdutoAoCarrinho).click().perform();
            Thread.sleep(3000);

            // Força a entrada na URL do carrinho para o servidor sincronizar os dados
            driver.get("https://www.kabum.com.br/carrinho");

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
