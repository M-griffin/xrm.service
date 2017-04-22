package com.xrm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIt() {

		String response = "{ 'Name': 'Test Response' }";
		return Response.status(Response.Status.OK).entity(response).build();
	}

	@GET
	@Path("/dbtest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDatabaseTest() throws ClassNotFoundException {

		System.out.println("getting database name");
		String name = null;

		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		
		try {		

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Blue\\apache-tomcat-8.5.14\\dbs\\xrm_users.sqlite3");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			//statement.executeUpdate("DROP TABLE IF EXISTS person");
			//statement.executeUpdate("CREATE TABLE person (id INTEGER, name STRING)");

			/*
			int ids[] = { 1, 2, 3, 4, 5 };
			String names[] = { "Peter", "Pallar", "William", "Paul", "James Bond" };

			for (int i = 0; i < ids.length; i++) {
				statement.executeUpdate("INSERT INTO person values(' " + ids[i] + "', '" + names[i] + "')");
			}*/

			// statement.executeUpdate("UPDATE person SET name='Peter' WHERE
			// id='1'");
			// statement.executeUpdate("DELETE FROM person WHERE id='1'");

			ResultSet resultSet = statement.executeQuery("SELECT * from users");
			while (resultSet.next()) {
				// iterate & read the result set
				System.out.println("sHandle = " + resultSet.getString("sHandle"));
				System.out.println("iId = " + resultSet.getInt("iId"));
				
				name = resultSet.getString("sHandle");
			}
		}

		catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}

	

	String response = "{ \"Name\": \"" + name + "\" }";return Response.status(Response.Status.OK).entity(response).build();
}}
