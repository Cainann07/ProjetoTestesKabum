package testes;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.text.Normalizer;
import java.time.Duration;
import java.util.Locale;

public class PaginaCadastro {
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

        // Gerador de dados
        Faker faker = new Faker(new Locale("pt", "BR"));

        String nomeAleatorio = faker.name().fullName();
        String emailAleatorio = faker.internet().emailAddress();
        String cpfAleatorio = faker.cpf().valid();
        String telefoneAleatorio = faker.phoneNumber().cellPhone();
        String cepAleatorio = faker.address().zipCode();

        String nomeLimpo = Normalizer.normalize(nomeAleatorio, Normalizer.Form.NFD).replaceAll("[^\\p{IsAlphabetic}\\p{Digit}\\s]", "");
        String cpfLimpo = cpfAleatorio.replace(".", "").replace("-", "");
        String telefoneLimpo = telefoneAleatorio.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");


        System.out.println("--- DADOS GERADOS PARA ESTE TESTE ---");
        System.out.println("Nome: " + nomeAleatorio);
        System.out.println("E-mail: " + emailAleatorio);
        System.out.println("CPF: " + cpfAleatorio);
        System.out.println("Telefone: " + telefoneAleatorio);
        System.out.println("-------------------------------------");

        opcoes.setProfile(perfil);
        WebDriver driver = new FirefoxDriver(opcoes);
        try {
            driver.get("https://www.kabum.com.br");
            WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

            Thread.sleep(3000);

            WebElement botaoEntrar = espera.until(ExpectedConditions.presenceOfElementLocated(By.id("linkLoginHeader")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", botaoEntrar);
            System.out.println("click entrar");

            WebElement campoEmail = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='check-login-input']")));
            campoEmail.sendKeys("mateuscainan15@gmail.com");
            System.out.println("input preenchido");

            WebElement botaoLogin = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ENTRAR']")));
            botaoLogin.click();

            // Copia para a área de transferência o CPF gerado
            StringSelection copiaParaAreaTransferencia = new StringSelection(cpfLimpo);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copiaParaAreaTransferencia, null);

            WebElement campoCPF = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cpf-input']")));
            campoCPF.click();
            Thread.sleep(500);

            campoCPF.sendKeys(Keys.CONTROL, "v");

            System.out.println("CPF colado com sucesso!");
            Thread.sleep(2000);


            WebElement campoTelefone = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='mobile-number-input']")));
            campoTelefone.sendKeys(telefoneLimpo);
            System.out.println("telefone preenchido");
            Thread.sleep(2000);

            WebElement campoData = espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='birth-date-input']")));
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

            // Para burlar o controle de estado do React 16+ e preencher a data, pois da maneira mais convencional estava com bug
            String scriptBurlarReact =
                    "var input = arguments[0];" +
                            "var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                            "nativeSetter.call(input, '2004-03-28');" +
                            "var evento = new Event('input', { bubbles: true });" +
                            "input.dispatchEvent(evento);";

            js.executeScript(scriptBurlarReact, campoData);

            System.out.println("Data Preenchida");
            Thread.sleep(1500);

            WebElement campoNome = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='complete-name-input']")));
            campoNome.sendKeys(nomeLimpo);
            System.out.println("nome preenchido");
            Thread.sleep(2000);


            WebElement campoSenha = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='password-input-cpf']")));
            campoSenha.sendKeys("Kabum26.");
            Thread.sleep(2000);

            System.out.println("Tentando clicar com o Actions");

            WebElement checkBoxPolitica = espera.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='privacy-policies-checkbox']")));
            Actions acoes = new org.openqa.selenium.interactions.Actions(driver);
            acoes.moveToElement(checkBoxPolitica).click().perform();

            // Para validar o estado do formulário, pois só preencher os campos não basta
            campoSenha.sendKeys(Keys.TAB);
            Thread.sleep(2000); // Aguarda o botão processar a liberação


            WebElement botaoContinuar = driver.findElement(By.xpath("//button[contains(., 'Continuar')]"));
            js.executeScript("arguments[0].removeAttribute('disabled');", botaoContinuar);
            js.executeScript("arguments[0].click();", botaoContinuar);

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement campoCep = espera.until(ExpectedConditions.elementToBeClickable(By.name("zipcode")));
            campoCep.sendKeys("01310900");
            System.out.println("input preenchido");

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement botaoContinuarAposCep = driver.findElement(By.xpath("//span[contains(., 'Confirmar')]"));
            botaoContinuarAposCep.click();

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement campoNumero = espera.until(ExpectedConditions.elementToBeClickable(By.name("number")));
            campoNumero.sendKeys("304");
            System.out.println("input preenchido");

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement campoComplemento = driver.findElement(By.name("complement"));
            campoComplemento.sendKeys("Condomínio Boa Vista");

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement campoReferencia = espera.until(ExpectedConditions.visibilityOfElementLocated(By.name("reference")));
            campoReferencia.sendKeys("Próximo a Padaria do Seu Chico");

            Thread.sleep(2000); // Aguarda o botão processar a liberação

            WebElement botaoConfirmarAposComplemento = driver.findElement(By.xpath("//span[contains(., 'Confirmar')]"));
            botaoConfirmarAposComplemento.click();

            Thread.sleep(120000);


        } catch (Exception e) {
            System.out.println(e);

        } finally {
            driver.quit();
        }
    }
}
