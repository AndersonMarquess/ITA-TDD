package com.andersonmarques.selenium.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetalhesDoLeilaoPage {

	private WebDriver driver;

	public DetalhesDoLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void darLanceCom(String nome, double valor) {
		Select select = new Select(this.driver.findElement(By.name("lance.usuario.id")));
		select.selectByVisibleText(nome);
		this.driver.findElement(By.name("lance.valor")).sendKeys(String.valueOf(valor));

		this.driver.findElement(By.id("btnDarLance")).click();
	}

	/**
	 * Espera a requisição AJAX
	 */
	public boolean existeLanceDe(String nome, double valor) {
		WebElement containerLances = this.driver.findElement(By.id("lancesDados"));
		
		// necessário para trabalhar com ajax, espera no máximo 10 segundo pela resposta.
		boolean achouNome = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.textToBePresentInElement(containerLances, nome));

		if (achouNome) {
			return this.driver.getPageSource().contains(nome)
					&& this.driver.getPageSource().contains(String.valueOf(valor));
		}

		return false;
	}
}