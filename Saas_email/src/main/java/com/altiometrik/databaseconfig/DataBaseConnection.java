package com.altiometrik.databaseconfig;

import java.sql.*;


import java.util.Properties;

public class DataBaseConnection {

	public static void saveData(String InvoiceNumber, String invoiceDate, String customerPO, String address,
			String amount) {
		Connection conn = null;

		try {
			String userName = "hr";
			String password = "hr";
			String url1 = "jdbc:oracle:thin:@localhost:1521:xe";

			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			conn = DriverManager.getConnection(url1, connectionProps);
			PreparedStatement pre1 = conn.prepareStatement("SELECT * FROM InvoiceDetails1 where InvoiceNumber=?");
			pre1.setString(1, InvoiceNumber);
			ResultSet re = pre1.executeQuery();
			String invoicenumber1 = null;
			// pre1.setString(1, InvoiceNumber);
			while (re.next()) {
				invoicenumber1 = re.getString(1);
				// System.out.println(re.getString(1));
			}

			if (invoicenumber1 == null) {

				PreparedStatement pre = conn.prepareStatement("INSERT INTO InvoiceDetails1 VALUES(?,?,?,?,?)");
				pre.setString(1, InvoiceNumber);
				pre.setString(2, invoiceDate);
				pre.setString(3, customerPO);
				pre.setString(4, address);
				pre.setString(5, amount);
				pre.executeUpdate();
				System.out.println("Data records are inserted!!");
				System.out.println("____________________________");
			} else {

				System.out.println("Data record is already in the table");
				System.out.println("____________________________");
			}
		}

		catch (SQLException e) {
			System.err.println("Failed to connect to database" + e);
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}

			}
		}

	}

	public static void Invoices() {
		Connection conn = null;
		try {
			String userName = "hr";
			String password = "hr";
			String url1 = "jdbc:oracle:thin:@localhost:1521:xe";

			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			conn = DriverManager.getConnection(url1, connectionProps);
			PreparedStatement pre1 = conn.prepareStatement("SELECT * FROM InvoiceDetails1 ");

			ResultSet re = pre1.executeQuery();

			while (re.next()) {

				System.out.println("Invoice_No:-\t" + re.getString(1));
				System.out.println("Invoice_Date:-\t" + re.getString(2));
				System.out.println("Customer_Po:-\t " + re.getString(3));
				System.out.println("Address:-\t" + re.getString(4));
				System.out.println("Amount:-\t" + re.getString(5));
				System.out.println("\n------------------------------------");
			}
		} catch (SQLException e) {
			System.err.println("Failed to connect to database" + e);
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}

		}

	}
}
