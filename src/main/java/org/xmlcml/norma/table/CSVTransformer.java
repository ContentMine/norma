package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTh;
import org.xmlcml.html.HtmlTr;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/** transforms CSV into tables or TDV.
 * 
 * @author pm286
 *
 */
public class CSVTransformer {

	private static final Logger LOG = Logger.getLogger(CSVTransformer.class);
	private List<CSVRecord> recordList;
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private Charset charset = Charset.forName("UTF-8");
	private CSVFormat csvFormat = CSVFormat.DEFAULT;
	
	public CSVTransformer() {
		
	}

	
	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public CSVFormat getCsvFormat() {
		return csvFormat;
	}

	public void setCsvFormat(CSVFormat csvFormat) {
		this.csvFormat = csvFormat;
	}

	/** read CSV file into recordList.
	 * this can be reprocessed to create either HTMLTable or TDV.
	 * 
	 * @param inputFile
	 * @throws IOException
	 */
	public void readFile(File inputFile) throws IOException {
		CSVParser csvParser = CSVParser.parse(inputFile, charset, csvFormat);
		recordList = csvParser.getRecords();
	}
	
	/** processes recordList to create an HTMLTable.
	 * 
	 * @return null if no records read
	 */
	public HtmlTable createTable() {
		HtmlTable table = null;
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

	/** processes recordList to create a tab-delimited files (TSV/TDV)
	 * 
	 * @return null if no records read
	 */
	public String createTSV() throws IOException {
		StringBuilder sb = null;
		if (recordList != null) {
			sb = new StringBuilder();
			CSVPrinter csvPrinter = new CSVPrinter(sb, CSVFormat.TDF);
			for (CSVRecord record : recordList) {
				try {
					csvPrinter.printRecord(record);
				} catch (IOException e) {
					csvPrinter.close();
					throw new IOException("cannot write record ", e);
				}
			}
			csvPrinter.close();
		}
		return sb == null ? null : sb.toString();
	}

	/** processes recordList to create JSON
	 * 
	 * @return null if no records read
	 */
	public JSONObject createJSON() throws IOException {

	    JSONObject json = null;
	    if (recordList != null) {
	    	json = new JSONObject();
		    JSONArray rows = new JSONArray();
		    int irow = 0;
			for (CSVRecord record : recordList) {
		        JSONObject row = new JSONObject();
		        JSONArray cells = createJSONArray(record, irow);
		        row.put(((irow == 0) ? "header" : "row"), cells);
		        rows.add(row);
		        irow++;
		    }
		    json.put( "table", rows );
	    }
	    return json;
	
	}
	
	private HtmlTr createTr(CSVRecord record, int irow) {
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
	
	private JSONArray createJSONArray(CSVRecord record, int irow) {
        JSONArray cells = null;
		if (record != null) {
			cells = new JSONArray();
			for (int i = 0; i < record.size(); i++) {
				String s = record.get(i);
				cells.add(s);
			}
        }
        return cells;
	}
}
