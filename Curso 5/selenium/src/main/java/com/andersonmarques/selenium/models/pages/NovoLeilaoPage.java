package com.andersonmarques.selenium.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class NovoLeilaoPage {
	private WebDriver driver;

	public NovoLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void preencherComDados(String nome, double valor, String nomeDoCriador, boolean usado) {
		encontreElementoComNome("leilao.nome").sendKeys(nome);
		encontreElementoComNome("leilao.valorInicial").sendKeys(String.valueOf(valor));

		Select select = new Select(encontreElementoComNome("leilao.usuario.id"));
		select.selectByVisibleText(nomeDoCriador);

		if (usado) {
			this.driver.findElement(By.name("leilao.usado")).click();
		}

		this.driver.findElement(By.tagName("button")).click();
	}

	private WebElement encontreElementoComNome(String nomeElemento) {
		return this.driver.findElement(By.name(nomeElemento));
	}

	public boolean contemMensagem(String mensagem) {
		return this.driver.getPageSource().contains(mensagem);
	}
}