package com.andersonmarques.selenium.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditarUsuarioPage {

	private WebDriver driver;

	public EditarUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public EditarUsuarioPage novoNome(String nome) {
		this.driver.findElement(By.name("usuario.nome")).sendKeys(nome);
		return this;
	}

	public EditarUsuarioPage novoEmail(String email) {
		this.driver.findElement(By.name("usuario.email")).sendKeys(email);
		return this;
	}

	public void confirmar() {
		this.driver.findElement(By.tagName("button")).click();
	}
}