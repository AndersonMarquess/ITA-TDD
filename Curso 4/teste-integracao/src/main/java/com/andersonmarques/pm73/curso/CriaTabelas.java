package com.andersonmarques.pm73.curso;

import com.andersonmarques.pm73.dao.CriadorDeSessao;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Classe responsável por receber as configurações do hibernate e executar a criação das tabelas.
 */
public class CriaTabelas {

	public static void main(String[] args) {
		
		Configuration cfg = new CriadorDeSessao().getConfig();
		SchemaExport se = new SchemaExport(cfg);
		
		se.create(true, true);
	}
	
}
