package com.sistemi.informativi.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.jupiter.api.Test;


public class TestApplication {

	@Before
	public Connection getConnection() throws ClassNotFoundException, SQLException {

		final String dbDriver = "com.mysql.cj.jdbc.Driver";
		final String dbUrl = "jdbc:mysql://localhost:3306/sistemi";
		final String dbUser = "root";
		final String dbPass = "";

		Class.forName(dbDriver);

		return DriverManager.getConnection(dbUrl, dbUser, dbPass);

	}

	@Test
	public void addCompany() throws ClassNotFoundException, SQLException {

		String sqlInsert = "insert into company(vat_number,business_name,address_location,employees_number) values (?,?,?,?)";
		PreparedStatement ps = getConnection().prepareStatement(sqlInsert);
		ps.setString(1, "10983771003");
		ps.setString(2, "companyB");
		ps.setString(3, "addressB");
		ps.setInt(4, 348);

		int nRows = ps.executeUpdate();

		/*
		 * invocando il metodo assertEquals passiamo in input 2 argomenti:
		 * 
		 * il primo argomento è una variabile, il secondo argomento è il valore che ci
		 * aspettiamo che quella variabile assuma
		 */
		assertEquals(nRows, 1);

	}

	@Test
	public void updateCompany() throws ClassNotFoundException, SQLException {

		String sqlUpdate = "update company set business_name=?,address_location=?,employees_number=? where vat_number=?";

		PreparedStatement ps = getConnection().prepareStatement(sqlUpdate);

		ps.setString(1, "company1");
		ps.setString(2, "address1");
		ps.setInt(3, 50);
		ps.setString(4, "10283771004");

		int nRows = ps.executeUpdate();

		assertEquals(nRows, 1);

	}

	@Test
	public void deleteCompany() throws ClassNotFoundException, SQLException {

		String sqlDelete = "delete from company where vat_number=?";

		PreparedStatement ps = getConnection().prepareStatement(sqlDelete);
		ps.setString(1, "10283771009");

		int nRows = ps.executeUpdate();

		assertEquals(nRows, 1);

	}

	@Test
	public void findCompaniesByEmployeesNumberGreaterThan() throws ClassNotFoundException, SQLException {

		String sqlRead = "select count(*) from company where employees_number > ?";
		PreparedStatement ps = getConnection().prepareStatement(sqlRead);
		ps.setInt(1, 15);
		ResultSet rs = ps.executeQuery();

		int count = 0;

		while (rs.next()) {

			count = rs.getInt(1);
		}

		assertEquals(count, 3);

	}

}
