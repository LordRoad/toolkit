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
package org.artemis.toolkit.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tabledata TODO
 * tabledata.java is written at Jun 14, 2014
 * @author return_jun
 * @since 0.2
 */
public class tabledata {
	private static final Logger LOG = LoggerFactory.getLogger(tabledata.class);

	private String[][] mColumnsData;
	private int mColumnCount = 0;
	
	public tabledata(int iColumnCount) {
		mColumnCount = iColumnCount < 0 ? 0 : iColumnCount;
		mColumnsData = new String[iColumnCount][];
	}

	public void flushDataIntoDisk(String iStoragePath) {
		
	}
	
	public boolean isColumnEmpty(int iColumnIndex) {
		if (iColumnIndex < 0 || iColumnIndex > mColumnCount) return true;
		return mColumnsData[iColumnIndex] == null;
	}
	
	public String[] getColumnData(int iColumnIndex) {
		if (iColumnIndex < 0 || iColumnIndex > mColumnCount) return null;
		return mColumnsData[iColumnIndex];
	}
	
	public void putColumnData(int iColumnIndex, String [] iColumnData) {
		mColumnsData[iColumnIndex] = iColumnData;
	}
	
}
