/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.artemis.toolkit.test.table;

import java.io.IOException;

import org.artemis.toolkit.common.configparser;
import org.artemis.toolkit.table.analyticsops;
import org.artemis.toolkit.table.column;
import org.artemis.toolkit.table.datatype;
import org.artemis.toolkit.table.facttable;
import org.artemis.toolkit.table.lookuptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * tabledef TODO
 * tabledef.java is written at Jun 15, 2014
 * @author return_jun
 */
@RunWith(JUnit4.class)
public class tabledef {

	@Test
	public void testfacttbl() throws IOException {
		facttable lfacttable = new facttable("fact_table");
		
		lfacttable.insertnewcolumn(new column("index", datatype.LONG, analyticsops.order.Ascend, "0-"));
		lfacttable.insertnewcolumn(new column("gender", datatype.SHORT, analyticsops.order.Random, "0-1"));
		lfacttable.insertnewcolumn(new column("date", datatype.DATE, analyticsops.order.Random, "2012-06-28~2014-06-15"));
		lfacttable.insertnewcolumn(new column("description", datatype.STRING, analyticsops.order.Random, "0-255"));
		lfacttable.insertnewcolumn(new column("metric", datatype.LONG));
		
		configparser lconfigparser = new configparser(System.getProperty("user.dir") +
				"/tmp/testfacttbl.json");
		
		lconfigparser.serialize(lfacttable);
	}
	
	@Test
	public void testfacttblinit() throws IOException {
		
		configparser lconfigparser = new configparser(System.getProperty("user.dir") +
				"/tmp/testfacttbl.json");
		
		facttable lfacttable = lconfigparser.deserialize(facttable.class);
		System.out.println(lfacttable.getmFactTableName());
	}
	
	@Test
	public void testLUtbl() throws IOException {
		facttable lfacttable = new facttable("fact_table");
		
		lfacttable.insertnewcolumn(new column("index", datatype.LONG, analyticsops.order.Ascend, "0-"));
		lfacttable.insertnewcolumn(new column("gender", datatype.SHORT, analyticsops.order.Random, "0-1"));
		lfacttable.insertnewcolumn(new column("date", datatype.DATE, analyticsops.order.Random, "2012-06-28~2014-06-15"));
		lfacttable.insertnewcolumn(new column("description", datatype.STRING, analyticsops.order.Random, "0-255"));
		lfacttable.insertnewcolumn(new column("metric", datatype.LONG));
		
		lookuptable llookuptable = new lookuptable("LU_date", lfacttable);
		
		llookuptable.adddependency(new int[] {2});
		
		configparser lconfigparser = new configparser(System.getProperty("user.dir") +
				"/tmp/testLUtbl.json");
		
		lconfigparser.serialize(llookuptable);
	}
	
}
