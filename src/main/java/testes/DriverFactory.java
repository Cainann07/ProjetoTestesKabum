package testes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver == null) {

            FirefoxOptions opcoes = new FirefoxOptions();

            FirefoxProfile perfil = new FirefoxProfile();

            // Oculta automação
            perfil.setPreference("dom.webdriver.enabled", false);

            // Remove extensão de automação
            perfil.setPreference("useAutomationExtension", false);

            // Bloqueia notificações
            perfil.setPreference("dom.webnotifications.enabled", false);

            // Simula navegador real
            perfil.setPreference(
                    "general.useragent.override",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:120.0) Gecko/20100101 Firefox/120.0"
            );

            opcoes.setProfile(perfil);

            driver = new FirefoxDriver(opcoes);

            // Maximiza a janela
            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void fecharDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}