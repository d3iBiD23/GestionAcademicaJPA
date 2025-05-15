package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import factory.DAOFactory;
import factory.JDBCDAOFactory;
import factory.JPADAOFactory;

public class DAOFactoryProvider {
	private static final String CONFIG_FILE = "src/config.properties";
	private static final String DAO_TYPE_KEY = "dao.type";

	public static DAOFactory getDAOFactory() {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream(CONFIG_FILE);) {
			props.load(input);
			String daoType = props.getProperty(DAO_TYPE_KEY, "jdbc").toLowerCase();

			switch (daoType) {
			case "jpa":
				return new JPADAOFactory();
			case "jdbc":
			default:
				return new JDBCDAOFactory();
			}
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de configuración. Usando JDBC por defecto.");
			return new JDBCDAOFactory();
		}
	}
}
