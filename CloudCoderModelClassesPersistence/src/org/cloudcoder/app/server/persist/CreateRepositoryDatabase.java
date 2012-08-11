// CloudCoder - a web-based pedagogical programming environment
// Copyright (C) 2011-2012, Jaime Spacco <jspacco@knox.edu>
// Copyright (C) 2011-2012, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.cloudcoder.app.server.persist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import org.cloudcoder.app.shared.model.ModelObjectSchema;
import org.cloudcoder.app.shared.model.RepoProblem;
import org.cloudcoder.app.shared.model.RepoTestCase;
import org.cloudcoder.app.shared.model.User;

/**
 * Create the exercise repository database.
 * 
 * @author David Hovemeyer
 */
public class CreateRepositoryDatabase {
	public static void main(String[] args) throws Exception {
		try {
			doCreateRepositoryDatabase();
		} catch (SQLException e) {
		    e.printStackTrace(System.err);
			System.err.println("Database error: " + e.getMessage());
		}
	}
	
	private static void doCreateRepositoryDatabase() throws Exception {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter a username for your repository server account: ");
		String ccUserName = keyboard.nextLine();
		
		System.out.print("Enter a password for your repository server account: ");
		String ccPassword = keyboard.nextLine();
		
		Class.forName("com.mysql.jdbc.Driver");

		Properties config = DBUtil.getConfigProperties();
		
		Connection conn = DBUtil.connectToDatabaseServer(config, "cloudcoder.repoapp.db");
		
		System.out.println("Creating the database");
		DBUtil.createDatabase(conn, config.getProperty("cloudcoder.repoapp.db.databaseName"));
		
		// Connect to the newly-created database
		conn.close();
		conn = DBUtil.connectToDatabase(config, "cloudcoder.repoapp.db");
		
		// Create tables
		createTable(conn, JDBCTableNames.USERS, User.SCHEMA);
		createTable(conn, JDBCTableNames.REPO_PROBLEMS, RepoProblem.SCHEMA);
		createTable(conn, JDBCTableNames.REPO_TEST_CASES, RepoTestCase.SCHEMA);

		// Create an initial user
		System.out.println("Creating initial user...");
		User user = new User();
		user.setUsername(ccUserName);
		user.setPasswordHash(BCrypt.hashpw(ccPassword, BCrypt.gensalt(12)));
		DBUtil.storeBean(conn, user, User.SCHEMA, JDBCDatabase.USERS);
		
		System.out.println("Done!");
	}

	private static void createTable(Connection conn, String tableName, ModelObjectSchema schema) throws SQLException {
		System.out.println("Creating table " + tableName);
		DBUtil.createTable(conn, tableName, schema);
	}
}
