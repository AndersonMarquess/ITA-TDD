package com.andersonmarques.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteAutomatizado {

	public static void main(String[] args) {
		System.out.println("Tentando abrir o browser");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anderson\\Downloads\\chromedriver.exe");

		// Abre o chrome
		WebDriver chromeDriver = new ChromeDriver();
		
		// Acessa o site
		chromeDriver.get("https://www.google.com.br");

		// Inseri dados na barra de pesquisa
		WebElement barraDePesquisa = chromeDriver.findElement(By.name("q"));
		barraDePesquisa.sendKeys("Hello world from selenium.");

		// Envia o formulário
		barraDePesquisa.submit();

		System.out.println("Terminou execução");
	}
}