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
package org.artemis.toolkit.table.gen;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.artemis.toolkit.common.configparser;
import org.artemis.toolkit.common.fileutils;
import org.artemis.toolkit.metadata.reportmd;
import org.artemis.toolkit.metadata.tablemd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * reportgenerate generate report with data
 * reportgenerate.java is written at Jun 15, 2014
 * @author return_jun
 * @since 0.2
 */
public class genreport {
	private static final Logger LOG = LoggerFactory.getLogger(genreport.class);
			
	/**
	 * the folder is used to store generated report
	 */
	@SerializedName("storage_path") private String mStoragePath = null;
	@SerializedName("report_name") private String mReportName = null;
	@SerializedName("report") private reportmd mReport = null;
	
	public genreport() {
		
	}
	
	public static genreport createReportGenerate(String iconfigurefile) throws IOException {
		configparser lconfigparser = new configparser(iconfigurefile);
		return lconfigparser.deserialize(genreport.class);
	}
	
	public String getmStoragePath() {
		return mStoragePath;
	}

	public void setmStoragePath(String mStoragePath) {
		this.mStoragePath = mStoragePath;
	}

	public String getmReportName() {
		return mReportName;
	}

	public void setmReportName(String mReportName) {
		this.mReportName = mReportName;
	}

	public reportmd getmReport() {
		return mReport;
	}

	public void setmReport(reportmd mReport) {
		this.mReport = mReport;
	}
	
	/**
	 * generate report based on schema information
	 */
	public void generateReport() {
		if (mReport == null) {
			throw new RuntimeException("fatal error, there is no report instance in current generating environment.");
		}
		LOG.trace("start generating report");
		
		long lBegin = System.currentTimeMillis();
		
		String lReportpath = mStoragePath + File.separator + mReportName;
		try {
			fileutils.initpath(lReportpath);
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage());
			return;
		}
		
		List<tablemd> lLUTables = mReport.getmlookuptableset();
		for (tablemd lLUTable : lLUTables) {
			gentable lgentable = new gentable(lLUTable, lReportpath, false);
			lgentable.genTableData();
			lgentable.wait(1, TimeUnit.DAYS);
		}
		
		long lEnd = System.currentTimeMillis();
		LOG.info("generate lookup table time : " + Long.toString(lEnd - lBegin) + " millis");
		//System.out.println("generate lookup table time: " + Long.toString(lEnd - lBegin) + " millis");
		
		lBegin = lEnd;
		
		tablemd lFactTable = mReport.getmfacttable();
		gentable lgentable = new gentable(lFactTable, lReportpath, false);
		
		lgentable.genTableData();
		lgentable.wait(1, TimeUnit.DAYS);
		
		lEnd = System.currentTimeMillis();
		LOG.info("generate fact table time : " + Long.toString(lEnd - lBegin) + " millis");
		//System.out.println("generate fact table time: " + Long.toString(lEnd - lBegin) + " millis");
		
		LOG.trace("end of generating report");
	}
}
