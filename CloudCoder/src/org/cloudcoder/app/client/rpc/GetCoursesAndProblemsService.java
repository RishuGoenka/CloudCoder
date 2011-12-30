// NetCoder - a web-based pedagogical programming environment
// Copyright (C) 2011, Jaime Spacco <jspacco@knox.edu>
// Copyright (C) 2011, David H. Hovemeyer <dhovemey@ycp.edu>
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

package org.cloudcoder.app.client.rpc;

import org.cloudcoder.app.shared.model.Course;
import org.cloudcoder.app.shared.model.NetCoderAuthenticationException;
import org.cloudcoder.app.shared.model.Problem;
import org.cloudcoder.app.shared.model.ProblemAndSubscriptionReceipt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getCoursesAndProblems")
public interface GetCoursesAndProblemsService extends RemoteService {
	/**
	 * Get the courses the client user is registered for.
	 * 
	 * @return list of courses the client user is registered for
	 * @throws NetCoderAuthenticationException if the client is not authenticated
	 */
	public Course[] getCourses() throws NetCoderAuthenticationException;
	
	public Problem[] getProblems(Course course) throws NetCoderAuthenticationException;
	
	public ProblemAndSubscriptionReceipt[] getProblemAndSubscriptionReceipts(Course course) throws NetCoderAuthenticationException;
}
