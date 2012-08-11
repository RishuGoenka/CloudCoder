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

package org.cloudcoder.app.shared.model;

import java.util.Arrays;

/**
 * RepoTestCase represents a test case in the exercise repository.
 * As such, it contains just a unique id, the unique id of the
 * {@link RepoProblem} it belongs to,
 * and the information in {@link TestCaseData}.
 * 
 * @author David Hovemeyer
 */
public class RepoTestCase extends TestCaseData {
	private static final long serialVersionUID = 1L;

	private int id;
	private int repoProblemId;
	
	/**
	 * Description of fields.
	 */
	public static final ModelObjectSchema SCHEMA = new ModelObjectSchema(
			ModelObjectUtil.combineLists(
					Arrays.asList(
							new ModelObjectField("id", Integer.class, 0, ModelObjectIndexType.IDENTITY),
							new ModelObjectField("repo_problem_id", Integer.class, 0, ModelObjectIndexType.NON_UNIQUE)
					),
					TestCaseData.SCHEMA.getFieldList()
			)
	);
	
	/**
	 * Constructor.
	 */
	public RepoTestCase() {
		
	}
	
	/**
	 * Set the unique id.
	 * @param id the unique id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the unique id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the unique id of the {@link RepoProblem} this test case belongs to.
	 * @param repoProblemId the unique id of the RepoProblem
	 */
	public void setRepoProblemId(int repoProblemId) {
		this.repoProblemId = repoProblemId;
	}
	
	/**
	 * @return get the unique id of the {@link RepoProblem} this test case belongs to
	 */
	public int getRepoProblemId() {
		return repoProblemId;
	}
}
