package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTh;
import org.xmlcml.html.HtmlTr;

public class CSVTransformer {

	public static HtmlTable createTable(File inputFile) {
		HtmlTable table = null;
		List<CSVRecord> recordList = null;
		try {
			CSVParser csvParser = CSVParser.parse(inputFile, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
			recordList = csvParser.getRecords();
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform CSV "+inputFile, e);
		}
		if (recordList != null) {
			table = new HtmlTable();
			int irow = 0;
			for (CSVRecord record : recordList) {
				HtmlTr tr = createTr(record, irow);
				table.appendChild(tr);
				irow++;
			}
		}
		return table;
	}

	private static HtmlTr createTr(CSVRecord record, int irow) {
		HtmlTr tr = null;
		if (record != null) {
			tr = new HtmlTr();
			for (int i = 0; i < record.size(); i++) {
				String s = record.get(i);
				HtmlElement t = (irow == 0) ? new HtmlTh() : new HtmlTd();
				t.appendChild(s);
				tr.appendChild(t);
			}
		}
		return tr;
	}


}
