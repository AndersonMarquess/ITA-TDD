package com.andersonmarques.leilao.infra.interfaces;

import java.util.Calendar;

public class RelogioImpl implements Relogio {

	@Override
	public Calendar hoje() {
		return Calendar.getInstance();
	}
}