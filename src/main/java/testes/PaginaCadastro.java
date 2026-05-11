package testes;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        // 1. INICIALIZA O GERADOR DE DADOS (Configurado para o Brasil)
        Faker faker = new Faker(new Locale("pt", "BR"));

        // Gera os dados aleatórios válidos para ESTA execução específica
        String nomeAleatorio = faker.name().fullName();
        String emailAleatorio = faker.internet().emailAddress();
        String cpfAleatorio = faker.cpf().valid();
        String telefoneAleatorio = faker.phoneNumber().cellPhone();

        String cpfLimpo = cpfAleatorio.replace(".", "").replace("-", "");
        String telefoneLimpo = telefoneAleatorio.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");


        // Imprime no console quem é o usuário falso desta rodada
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
            campoEmail.sendKeys(emailAleatorio);
            System.out.println("input preenchido");

            WebElement botaoLogin = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ENTRAR']")));
            botaoLogin.click();

            WebElement campoCPF = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cpf-input']")));
            Thread.sleep(500); // Pausa meio segundo para a máscara "acordar"

            // DIGITAÇÃO HUMANA: Vai enviar um número de cada vez
            System.out.println("Digitando o CPF humanamente...");
            for (char numero : cpfLimpo.toCharArray()) {
                campoCPF.sendKeys(String.valueOf(numero));
                Thread.sleep(100); // Pausa de 100 milissegundos entre cada número
            }
            System.out.println("cpf preenchido");

            Thread.sleep(2000);


            WebElement campoTelefone = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='mobile-number-input']")));
            campoTelefone.sendKeys(telefoneLimpo);
            System.out.println("telefone preenchido");

            Thread.sleep(2000);


            WebElement campoData = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='birth-date-input']")));
            campoData.sendKeys("28032004");
            System.out.println("data preenchido");

            Thread.sleep(2000);


            WebElement campoNome = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='complete-name-input']")));
            campoNome.sendKeys(nomeAleatorio);
            System.out.println("nome preenchido");

            Thread.sleep(2000);


            WebElement campoSenha = espera.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='password-input']")));
            campoSenha.sendKeys("Kabum26.");

            Thread.sleep(2000);

            WebElement checkBoxPolitica = espera.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='privacy-policies-checkbox']")));
            checkBoxPolitica.click();

            WebElement botaoContinuar = espera.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Continuar']")));
            botaoContinuar.click();

            Thread.sleep(10000);


        } catch (Exception e) {
            System.out.println(e);

        } finally {
            driver.quit();
        }
    }
}
