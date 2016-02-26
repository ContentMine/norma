package org.xmlcml.norma.biblio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlDiv;

public class RISParser extends BiblioParser {

	static final String ABSTRACT_LIST = "abstractList";
	/** adapted from Mark MacGillivray's bibserver
	Details of the RIS format
	http://en.wikipedia.org/wiki/RIS_%28file_format%29


	FIELD_MAP = {
	    "DO": "doi", 
	    "SP": "pages", 
...
	    "JF": "journal",
	}

	VALUE_MAP = {
	    'AU' : lambda v: [{u'name':vv.decode('utf8')} for vv in v]
	}
	DEFAULT_VALUE_FUNC = lambda v: u' '.join(vv.decode('utf8') for vv in v)

	class RISParser(object):

	    def __init__(self, fileobj):

	        data = fileobj.read()
	        self.encoding = chardet.detect(data).get('encoding', 'ascii')

	        # Some files have Byte-order marks inserted at the start
	        if data[:3] == '\xef\xbb\xbf':
	            data = data[3:]
	        self.fileobj = cStringIO.StringIO(data)
	        self.data = []
	        
	    def add_chunk(self, chunk):
	        if not chunk: return
	        tmp = {}
	        for k,v in chunk.items():
	            tmp[FIELD_MAP.get(k, k)] =  VALUE_MAP.get(k, DEFAULT_VALUE_FUNC)(v)   
	        self.data.append(tmp)
	        
	    def parse(self):
	        data, chunk = [], {}
	        last_field = None
	        for line in self.fileobj:
	            if line.startswith(' ') and last_field:
	                chunk.setdefault(last_field, []).append(line.strip())
	                continue
	            line = line.strip()
	            if not line: continue
	            parts = line.split('  - ')
	            if len(parts) < 2:
	                continue
	            field = parts[0]
	            last_field = field
	            if field == 'TY':
	                self.add_chunk(chunk)
	                chunk = {}
	            value = '  - '.join(parts[1:])
	            if value:
	                chunk.setdefault(field, []).append(value)
	        self.add_chunk(chunk)
	        return self.data, {}

	def parse():
	    parser = RISParser(sys.stdin)
	    records, metadata = parser.parse()
	    if len(records) > 0:
	        sys.stdout.write(json.dumps({'records':records, 'metadata':metadata}))
	    else:
	        sys.stderr.write('Zero records were parsed from the data')
	    
	def main():
	    conf = {"display_name": "RIS",
	            "format": "ris",
	            "contact": "openbiblio-dev@lists.okfn.org", 
	            "bibserver_plugin": True, 
	            "BibJSON_version": "0.81"}        
	    for x in sys.argv[1:]:
	        if x == '-bibserver':
	            sys.stdout.write(json.dumps(conf))
	            sys.exit()
	    parse()
	            
	if __name__ == '__main__':
	    main()
}
*/
	public static final Logger LOG = Logger.getLogger(RISParser.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	static Map<String, String> FIELD_MAP =  new HashMap<String, String>();
	static Set<String> UNKNOWN_KEYS =  new HashSet<String>();
	public final static String SPACE_DASH = "  - ";
	List<RISEntry> entryList;
	
	static {
		FIELD_MAP.put("A2", "author");
		FIELD_MAP.put("A3", "tertiary author");
		FIELD_MAP.put("A4", "subsidiary author");
		FIELD_MAP.put("AB", "note");
		FIELD_MAP.put("AD", "institution");
		FIELD_MAP.put("AN", "accession number");
		FIELD_MAP.put("AU", "author");
		FIELD_MAP.put("C1", "note");
		FIELD_MAP.put("C2", "pmcid");
		FIELD_MAP.put("C3", "custom 3");
		FIELD_MAP.put("C4", "custom 4");
		FIELD_MAP.put("C5", "custom 5");
		FIELD_MAP.put("C6", "nihmsid");
		FIELD_MAP.put("C7", "custom 7");
		FIELD_MAP.put("C8", "custom 8");
		FIELD_MAP.put("CA", "caption");
		FIELD_MAP.put("CN", "call number");
		FIELD_MAP.put("CY", "place published");
		FIELD_MAP.put("DA", "date");
		FIELD_MAP.put("DB", "name of database");
		FIELD_MAP.put("DO", "doi");
		FIELD_MAP.put("DP", "database provider");
		FIELD_MAP.put("ET", "epub date");
		FIELD_MAP.put("IS", "number");
		FIELD_MAP.put("J2", "alternate title");
		FIELD_MAP.put("JF", "journal");
		FIELD_MAP.put("KW", "keyword");
		FIELD_MAP.put("L1", "file attachments");
		FIELD_MAP.put("L4", "figure");
		FIELD_MAP.put("LA", "language");
		FIELD_MAP.put("LB", "label");
		FIELD_MAP.put("M1", "number");
		FIELD_MAP.put("M2", "start page");
		FIELD_MAP.put("M3", "type");
		FIELD_MAP.put("N1", "notes");
		FIELD_MAP.put("NV", "number of volumes");
		FIELD_MAP.put("OP", "original publication");
		FIELD_MAP.put("PB", "publisher");
		FIELD_MAP.put("PY", "year");
		FIELD_MAP.put("RI", "reviewed item");
		FIELD_MAP.put("RN", "note");
		FIELD_MAP.put("RP", "reprint edition");
		FIELD_MAP.put("SE", "section");
		FIELD_MAP.put("SN", "issn");
		FIELD_MAP.put("SP", "pages");
		FIELD_MAP.put("ST", "short title");
		FIELD_MAP.put("T2", "secondary title");
		FIELD_MAP.put("T3", "tertiary title");
		FIELD_MAP.put("TA", "translated author");
		FIELD_MAP.put("TI", "title");
		FIELD_MAP.put("TT", "translated title");
		FIELD_MAP.put("TY", "type ");
		FIELD_MAP.put("UR", "url");
		FIELD_MAP.put("VL", "volume");
		FIELD_MAP.put("Y2", "access date");
		// PMID additions; almost all of these are currently unknown functions
		FIELD_MAP.put("BTI", "BTI");
		FIELD_MAP.put("PMID", "pmid");
        FIELD_MAP.put("AID", "AID");
        FIELD_MAP.put("AUID", "AUID");
        FIELD_MAP.put("CDAT", "CDAT");
        FIELD_MAP.put("CI", "CI");
        FIELD_MAP.put("CIN", "CIN");
        FIELD_MAP.put("CON", "CON");
        FIELD_MAP.put("CRDT", "CRDT");
        FIELD_MAP.put("CTDT", "CTDT");
        FIELD_MAP.put("CTI", "CTI");
        FIELD_MAP.put("DCOM", "DCOM");
        FIELD_MAP.put("DEP", "DEP");
        FIELD_MAP.put("ED", "ED");
        FIELD_MAP.put("EDAT", "EDAT");
        FIELD_MAP.put("EIN", "EIN");
        FIELD_MAP.put("FAU", "FAU");
        FIELD_MAP.put("FED", "FED");
        FIELD_MAP.put("FIR", "FIR");
        FIELD_MAP.put("GN", "GN");
        FIELD_MAP.put("GR", "GR");
        FIELD_MAP.put("IP", "IP");
        FIELD_MAP.put("IR", "IR");
        FIELD_MAP.put("JID", "JID");
        FIELD_MAP.put("JT", "JT");
        FIELD_MAP.put("LID", "LID");
        FIELD_MAP.put("LR", "LR");
        FIELD_MAP.put("MH", "MH");
        FIELD_MAP.put("MHDA", "MHDA");
        FIELD_MAP.put("MID", "MID");
        FIELD_MAP.put("OAB", "OAB");
        FIELD_MAP.put("OABL", "OABL");
        FIELD_MAP.put("OID", "OID");
        FIELD_MAP.put("ORI", "ORI");
        FIELD_MAP.put("OT", "OT");
        FIELD_MAP.put("OTO", "OTO");
        FIELD_MAP.put("OWN", "OWN");
        FIELD_MAP.put("PG", "PG");
        FIELD_MAP.put("PHST", "PHST");
        FIELD_MAP.put("PL", "PL");
        FIELD_MAP.put("PMC", "PMC");
        FIELD_MAP.put("PMCR", "PMCR");
        FIELD_MAP.put("PST", "PST");
        FIELD_MAP.put("PT", "PT");
        FIELD_MAP.put("RF", "RF");
        FIELD_MAP.put("RIN", "RIN");
        FIELD_MAP.put("SB", "SB");
        FIELD_MAP.put("SI", "SI");
        FIELD_MAP.put("SO", "SO");
        FIELD_MAP.put("SPIN", "SPIN");
        FIELD_MAP.put("STAT", "STAT");
        FIELD_MAP.put("UIN", "UIN");
        FIELD_MAP.put("UOF", "UOF");
        FIELD_MAP.put("VI", "VI");
        /** section */

	}
	
	public RISParser() {
		
	}
	
	public List<RISEntry> getEntries() {
		if (entryList == null) {
			entryList = new ArrayList<RISEntry>();
			RISEntry entry = null;
			int i = 0;
			for (String line : lines) {
				i++;
				if (line.trim().length() == 0) {
					continue;
				}
				if (line.startsWith(RISEntry.TY) ||
				    line.startsWith(RISEntry.PMID)) {
					entry = new RISEntry();
					entryList.add(entry);
				} 
				if (entry != null) {
					entry.addLine(line);
				} else {
					LOG.error("Null current chunk: "+line.length()+"/"+i);
				}
			}
			entry.createAbstractHtml();
			if (UNKNOWN_KEYS.size() >0) {
				LOG.trace("Unknown fields: "+UNKNOWN_KEYS);
			}
		}
		return entryList;
	}
	
	public HtmlDiv getAbstractList() {
		BiblioAbstractAnalyzer abstractAnalyzer = new BiblioAbstractAnalyzer();
		HtmlDiv abstractList = abstractAnalyzer.createAbstractList(this);
		return abstractList;
	}

	public static void addUnknownKey(String field) {
		UNKNOWN_KEYS.add(field);
	}
	
}
