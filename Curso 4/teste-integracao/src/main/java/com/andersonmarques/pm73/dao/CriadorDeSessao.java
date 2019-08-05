package com.andersonmarques.pm73.dao;

import com.andersonmarques.pm73.dominio.Lance;
import com.andersonmarques.pm73.dominio.Leilao;
import com.andersonmarques.pm73.dominio.Usuario;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe responsável por configurar o hibernate, substituindo o clássico xml.
 */
@SuppressWarnings("deprecation")
public class CriadorDeSessao {

	private static Configuration config;
	private static SessionFactory sessionFactory;

	public Session getSession() {
		if (sessionFactory == null) {
			sessionFactory = getConfig().buildSessionFactory();
		}

		return sessionFactory.openSession();
	}

	// Config equivalente ao hibernate.cfg.xml
	public Configuration getConfig() {
		if (config == null) {
			config = new Configuration()
				.addAnnotatedClass(Lance.class)
				.addAnnotatedClass(Leilao.class)
				.addAnnotatedClass(Usuario.class)
				.setProperty("hibernate.archive.autodetection", "class,hbm")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.format_sql", "true")
				.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
				.setProperty("hibernate.connection.url", "jdbc:hsqldb:andersonmarques.db;shutdown=true")
				.setProperty("hibernate.connection.username", "sa")
				.setProperty("hibernate.connection.password", "");
		}
		return config;
	}
}
