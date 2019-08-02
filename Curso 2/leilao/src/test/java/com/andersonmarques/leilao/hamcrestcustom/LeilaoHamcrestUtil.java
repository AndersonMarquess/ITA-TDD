package com.andersonmarques.leilao.hamcrestcustom;

import com.andersonmarques.leilao.models.Lance;
import com.andersonmarques.leilao.models.Leilao;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class LeilaoHamcrestUtil extends TypeSafeMatcher<Leilao> {

	private Lance lance;

	public LeilaoHamcrestUtil(Lance lance) {
		this.lance = lance;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Você realmente precisa usar o Hamcrest?");
	}

	@Override
	protected boolean matchesSafely(Leilao item) {
		return item.getLances().contains(this.lance);
	}

	public static Matcher<Leilao> temUmLance(Lance lance) {
		return new LeilaoHamcrestUtil(lance);
	}
}