package com.andersonmarques.selenium.models.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LeiloesPage {

	private WebDriver driver;

	public LeiloesPage(WebDriver driver) {
		this.driver = driver;
	}

	public LeiloesPage visitar() {
		this.driver.get("http://localhost:8080/leiloes");
		return this;
	}

	public NovoLeilaoPage novo() {
		this.driver.findElement(By.linkText("Novo Leilão")).click();
		return new NovoLeilaoPage(this.driver);
	}

	public boolean existeNaListagem(String nome, double valor, String nomeDoCriador, boolean usado) {
		return this.driver.getPageSource().contains(nome)
			&& this.driver.getPageSource().contains(String.valueOf(valor))
			&& this.driver.getPageSource().contains(nomeDoCriador)
			&& this.driver.getPageSource().contains(usado ? "Sim" : "Não");
	}

	public DetalhesDoLeilaoPage detalhesDoLeilaoNaPosicao(int posicao) {
		List<WebElement> linksComNomeExibir = this.driver.findElements(By.linkText("exibir"));
		linksComNomeExibir.get(posicao - 1).click();
		return new DetalhesDoLeilaoPage(this.driver);
	}
}