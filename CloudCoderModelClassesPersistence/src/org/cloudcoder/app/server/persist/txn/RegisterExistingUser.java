// CloudCoder - a web-based pedagogical programming environment
// Copyright (C) 2011-2015, Jaime Spacco <jspacco@knox.edu>
// Copyright (C) 2011-2015, David H. Hovemeyer <david.hovemeyer@gmail.com>
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

package org.cloudcoder.app.server.persist.txn;

import java.sql.Connection;
import java.sql.SQLException;

import org.cloudcoder.app.server.persist.NoSuchUniqueIdException;
import org.cloudcoder.app.server.persist.util.AbstractDatabaseRunnableNoAuthException;
import org.cloudcoder.app.server.persist.util.DBUtil;
import org.cloudcoder.app.shared.model.Course;
import org.cloudcoder.app.shared.model.CourseRegistration;
import org.cloudcoder.app.shared.model.CourseRegistrationSpec;
import org.cloudcoder.app.shared.model.OperationResult;
import org.cloudcoder.app.shared.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterExistingUser extends AbstractDatabaseRunnableNoAuthException<OperationResult> {
	private static final Logger logger = LoggerFactory.getLogger(RegisterExistingUser.class);
	
	private CourseRegistrationSpec spec;

	public RegisterExistingUser(CourseRegistrationSpec spec) {
		this.spec = spec;
	}

	@Override
	public String getDescription() {
		return " register a single existing user in a course";
	}

	@Override
	public OperationResult run(Connection conn) throws SQLException {
		// First, find the user
		User user = Queries.getUser(conn, spec.getUsername(), this);
		if (user == null) {
			return new OperationResult(false, "Unknown user " + spec.getUsername());
		}
		
		// Create the CourseRegistration
		CourseRegistration reg = new CourseRegistration();
		reg.setUserId(user.getId());
		reg.setCourseId(spec.getCourseId());
		reg.setRegistrationType(spec.getRegistrationType());
		reg.setSection(spec.getSection());
		
		// Insert the CourseRegistration
		DBUtil.storeModelObject(conn, reg);
		
		Course course = null;
		try {
			course = DBUtil.loadModelObjectForId(conn, Course.SCHEMA, spec.getCourseId());
		} catch (NoSuchUniqueIdException e) {
			logger.error("Could not find course for id={}", spec.getCourseId());
		}
		
		return new OperationResult(true, "Added user " + spec.getUsername() + " to course" +
				(course != null ? " " + course.getNameAndTitle() : ""));
	}
}
