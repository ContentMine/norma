package org.xmlcml.norma.nlp;

import java.util.List;

import junit.framework.Assert;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.biblio.PMIDParserTest;
import org.xmlcml.svg2xml.paths.Chunk;

/** uses OpenNLP
 * 
 * @author pm286
 *
 */
public class OpenNLPTest {
	
	public static final Logger LOG = Logger.getLogger(OpenNLPTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public String ABSTRACT = ""+
			"BACKGROUND: Obesity is prevalent in patients with cognitive impairment, but the"+
			" risks and benefits in this complex group are unknown. OBJECTIVES: The aim of this"+
			" study was to assess outcomes in a small cohort of patients with lifelong"+
			" cognitive impairment who underwent bariatric surgery and to introduce important"+
			" concepts when considering surgery in this complex group. SETTING: Academic"+
			" institution, United States. METHODS: We retrospectively analyzed all patients"+
			" with an objective psychological and/or neuropsychological diagnosis of lifelong,"+ 
			" nonacquired cognitive impairment who had bariatric surgery at our center."+
			" RESULTS: We identified 6 patients with a diagnosis of nonacquired cognitive"+
			" impairment who underwent a bariatric procedure. The cohort (3 male, 3 female) had"+
			" a mean age of 33.3 years and a mean body mass index (BMI) of 49.4 kg/m2. Two of"+
			" the patients had a diagnosis of trisomy 21, and the other 4 patients had lifelong"+
			" cognitive impairment from unknown causes. The distribution of surgical approaches"+
			" was 2 laparoscopic Roux-en-Y gastric bypasses (LRYGBs), 3 laparoscopic sleeve"+
			" gastrectomies (SGs), and 1 laparoscopic adjustable gastric band (LAGB). There"+
			" were no complications and no mortality. At a mean follow-up of 33.7 months, the"+
			" cohort had a mean percent excess weight loss (%EWL) of 31.1% (range -1.8%-72.2%)."+
			" Two patients achieved a %EWL>50%. CONCLUSIONS: This case series suggests that"+
			" bariatric surgery can be performed with minimal morbidity in patients with"+
			" nonacquired cognitive impairment after intensive multidisciplinary management."+
			" However, it appears this population may lose less weight than what is reported"+
			" for patients without cognitive delay."+
			"";

	static String ABSTRACT1 = ""+
			"We used data from James Ford and Henry Ford and Elizabeth Smith and Tommy and the"+
		    " California Neighborhoods Data System to examine the neighborhood environment,"+
		    " body mass index, and mortality after breast cancer. We studied 8,995 African"+
		    " American, Asian American, Latina, and non-Latina White women with breast cancer."; 	
	
	static String DATE_ABSTRACT = ""+
		"METHODS: A total of 726 patients aged 55 to 72 years with treatable HNC were"+
	    " included from January 2004 to December 2009; these patients were randomized to"+
	    " either group with PEG and enteral nutrition and nonPEG group with nutritional"+
	    " counselling according to nutritional care.";
	
	static String LOCATION_ABSTRACT = "The terpenoid compositions of the Late Cretaceous Xixia amber "
			+ "from Central China and the middle Miocene Zhangpu amber from Southeast China were analyzed "
			+ "by gas chromatography-mass spectrometry (GC-MS) to elucidate their botanical origins. The "
			+ "Xixia amber is characterized by sesquiterpenoids, abietane and phyllocladane type diterpenoids, "
			+ "but lacks phenolic abietanes and labdane derivatives. The molecular compositions indicate that "
			+ "the Xixia amber is most likely contributed by the conifer family Araucariaceae, which is today "
			+ "distributed primarily in the Southern Hemisphere, but widely occurred in the Northern Hemisphere "
			+ "during the Mesozoic according to paleobotanical evidence. The middle Miocene Zhangpu amber is "
			+ "characterized by amyrin and amyrone-based triterpenoids and cadalene-based sesquiterpenoids. "
			+ "It is considered derived from the tropical angiosperm family Dipterocarpaceae based on these "
			+ "compounds and the co-occurring fossil winged fruits of the family in Zhangpu. This provides new "
			+ "evidence for the occurrence of a dipterocarp forest in the middle Miocene of Southeast China. "
			+ "It is the first detailed biomarker study for amber from East Asia.";
	
	static String LOCATION1 = "Cambridge is within easy reach of some but not all of London's international airports. "
			+ "London Stansted[4] is 30 mi away, for example, has regular bus and rail services into Cambridge. "
			+ "Direct rail services leave every hour from platform 2 (to Birmingham New Street) and take about "
			+ "35 min with a return fare £12.80. For more frequent services take the Stansted Express to London "
			+ "from platform 1 and change at Bishops's Stortford or Stansted Mountfitchet, taking about 50 min. "
			+ "Note, however, that rail services may be unavailable if your flight arrives Stansted very late or "
			+ "departs very early in the day, and while the airport likes to advertise hourly services, there "
			+ "are some strange gaps in the timetable so check the boards before you buy a ticket, and go to the "
			+ "bus terminal if there is nothing sensible on offer. National Express coaches run between Cambridge "
			+ "and Stansted (including late at night), taking about 55 minutes and costing £11.50. Abacus Airport "
			+ "Cars Cambridge rides there from £40.00 one way.";
			
    @Test
	public void testDetectSentence() {
		SentenceDetector _sentenceDetector = OpenNLPWrapper.createSentenceDetector();
		String[] sentences = _sentenceDetector.sentDetect("Hello, I found a sentence. And I found another.");
		Assert.assertEquals(2,  sentences.length);
		sentences = _sentenceDetector.sentDetect(ABSTRACT);
		Assert.assertEquals(13,  sentences.length);
		Assert.assertEquals("sent0",  
				"BACKGROUND: Obesity is prevalent in patients with cognitive impairment,"
				+ " but the risks and benefits in this complex group are unknown.",
				sentences[0]);
		Assert.assertEquals("sent9",  
				"At a mean follow-up of 33.7 months, the"+
				" cohort had a mean percent excess weight loss (%EWL) of 31.1% (range -1.8%-72.2%).",
				sentences[9]);
	}

	@Test
	public void testTokenizer() {
		SentenceDetector _sentenceDetector = OpenNLPWrapper.createSentenceDetector();
		String[] sentences = _sentenceDetector.sentDetect(ABSTRACT);
		Tokenizer tokenizer = OpenNLPWrapper.createTokenizer();
		String[] tokens = tokenizer.tokenize(sentences[0]);
		Assert.assertEquals(23,  tokens.length);
		Assert.assertEquals("sent0",  "BACKGROUND", tokens[0]);
		Assert.assertEquals("sent0",  ":", tokens[1]);
		Assert.assertEquals("sent0",  "Obesity", tokens[2]);
		tokens = tokenizer.tokenize(sentences[9]);
		Assert.assertEquals(29,  tokens.length);
		Assert.assertEquals("sent9",  "follow-up", tokens[3]);
		Assert.assertEquals("sent9",  "33.7", tokens[5]);
		Assert.assertEquals("sent9",  "months", tokens[6]);
		Assert.assertEquals("sent9",  ",", tokens[7]);
		Assert.assertEquals("sent9",  "(", tokens[17]);
		Assert.assertEquals("sent9",  "31.1", tokens[21]);
		Assert.assertEquals("sent9",  "%", tokens[22]);
		Assert.assertEquals("sent9",  "(", tokens[23]);
		Assert.assertEquals("sent9",  "range", tokens[24]);
		Assert.assertEquals("sent9",  "-1.8%-72.2", tokens[25]);
		Assert.assertEquals("sent9",  "%", tokens[26]);
		Assert.assertEquals("sent9",  ")", tokens[27]);
		Assert.assertEquals("sent9",  ".", tokens[28]);
	}
	
	@Test
	public void testPOSTagger() {
		OpenNLPWrapper wrapper = new OpenNLPWrapper();
		List<String> tokens = wrapper.tokenizeSentenceInText(ABSTRACT, 0);
		POSTagger posTagger = wrapper.getOrCreatePOSTagger();
		List<String> pos = posTagger.tag(tokens);
		Assert.assertEquals(23,  tokens.size());
		Assert.assertEquals(23,  pos.size());
		String[] posExp = {
				"NNP", ":", "NN", "VBZ", "JJ", "IN", "NNS", "IN", "JJ", "NN", ",", "CC", "DT", 
				"NNS", "CC", "NNS", "IN", "DT", "JJ", "NN", "VBP", "JJ", "."};
		for (int i = 0; i < pos.size(); i++) {
			Assert.assertEquals(posExp[i], pos.get(i));
		}
	}

	@Test
	public void testChunker() {
		OpenNLPWrapper wrapper = new OpenNLPWrapper();
		List<WordChunk> concatenatedChunks = wrapper.createConcatenatedChunks(ABSTRACT, 0);
		Assert.assertEquals("[B_NP:[BACKGROUND], O_COLON:[:], B_NP:[Obesity], B_VP:[is], B_ADJP:[prevalent], B_PP:[in], B_NP:[patients], B_PP:[with], B_NP:[cognitive, impairment], O_COMMA:[,], O_but:[but], B_NP:[the, risks, and, benefits], B_PP:[in], B_NP:[this, complex, group], B_VP:[are], B_ADJP:[unknown], O_FULLSTOP:[.]]", concatenatedChunks.toString());
	}

	@Test
	public void testChunkerSentences() {
		String[] chunks = {
		"[B_NP:[BACKGROUND], O_COLON:[:], B_NP:[Obesity], B_VP:[is], B_ADJP:[prevalent], B_PP:[in], B_NP:[patients], B_PP:[with], B_NP:[cognitive, impairment], O_COMMA:[,], O_but:[but], B_NP:[the, risks, and, benefits], B_PP:[in], B_NP:[this, complex, group], B_VP:[are], B_ADJP:[unknown], O_FULLSTOP:[.]]",
		"[B_NP:[OBJECTIVES], O_COLON:[:], B_NP:[The, aim], B_PP:[of], B_NP:[this, study], B_VP:[was, to, assess], B_NP:[outcomes], B_PP:[in], B_NP:[a, small, cohort], B_PP:[of], B_NP:[patients], B_PP:[with], B_NP:[lifelong, cognitive, impairment], B_NP:[who], B_VP:[underwent], B_NP:[bariatric, surgery], O_and:[and], B_VP:[to, introduce], B_NP:[important, concepts], B_ADVP:[when], B_NP:[considering, surgery], B_PP:[in], B_NP:[this, complex, group], O_FULLSTOP:[.]]",
		"[B_VP:[SETTING], O_COLON:[:], B_NP:[Academic, institution], O_COMMA:[,], B_NP:[United, States], O_FULLSTOP:[.]]",
		"[B_NP:[METHODS], O_COLON:[:], B_NP:[We], B_ADVP:[retrospectively], B_VP:[analyzed], B_NP:[all, patients], B_PP:[with], B_NP:[an, objective, psychological, and/or, neuropsychological, diagnosis], B_PP:[of], B_NP:[lifelong, ,, nonacquired, cognitive, impairment], B_NP:[who], B_VP:[had, bariatric], B_NP:[surgery], B_PP:[at], B_NP:[our, center], O_FULLSTOP:[.]]",
		"[B_NP:[RESULTS], O_COLON:[:], B_NP:[We], B_VP:[identified], B_NP:[6, patients], B_PP:[with], B_NP:[a, diagnosis], B_PP:[of], B_NP:[nonacquired, cognitive, impairment], B_NP:[who], B_VP:[underwent], B_NP:[a, bariatric, procedure], O_FULLSTOP:[.]]",
		"[B_NP:[The, cohort], O_(:[(], B_NP:[3, male], O_COMMA:[,], B_NP:[3, female, )], B_VP:[had], B_NP:[a, mean, age], B_PP:[of], B_NP:[33.3, years], O_and:[and], B_NP:[a, mean, body, mass, index], B_VP:[(], B_NP:[BMI, )], B_PP:[of], B_NP:[49.4, kg/m2], O_FULLSTOP:[.]]",
		"[B_NP:[Two], B_PP:[of], B_NP:[the, patients], B_VP:[had], B_NP:[a, diagnosis], B_PP:[of], B_NP:[trisomy, 21], O_COMMA:[,], O_and:[and], B_NP:[the, other, 4, patients], B_VP:[had], B_NP:[lifelong, cognitive, impairment], B_PP:[from], B_NP:[unknown, causes], O_FULLSTOP:[.]]",
		"[B_NP:[The, distribution], B_PP:[of], B_NP:[surgical, approaches], B_VP:[was], B_NP:[2, laparoscopic, Roux-en-Y, gastric, bypasses], B_PP:[(], B_NP:[LRYGBs, )], O_COMMA:[,], B_NP:[3, laparoscopic, sleeve, gastrectomies, (SGs], B_VP:[)], O_COMMA:[,], O_and:[and], B_NP:[1, laparoscopic, adjustable, gastric, band], B_NP:[(, LAGB, )], O_FULLSTOP:[.]]",
		"[B_NP:[There], B_VP:[were], B_NP:[no, complications], O_and:[and], B_NP:[no, mortality], O_FULLSTOP:[.]]",
		"[B_PP:[At], B_NP:[a, mean, follow-up], B_PP:[of], B_NP:[33.7, months], O_COMMA:[,], B_NP:[the, cohort], B_VP:[had], B_NP:[a, mean, percent, excess, weight, loss], B_PP:[(], B_NP:[%EWL, )], B_PP:[of], B_NP:[31.1, %, (, range, -1.8%-72.2, %, )], O_FULLSTOP:[.]]",
		"[B_NP:[Two, patients], B_VP:[achieved], B_NP:[a, %EWL>50, %], O_FULLSTOP:[.]]",
		"[B_NP:[CONCLUSIONS], O_COLON:[:], B_NP:[This, case, series], B_VP:[suggests], B_PP:[that], B_NP:[bariatric, surgery], B_VP:[can, be, performed], B_PP:[with], B_NP:[minimal, morbidity], B_PP:[in], B_NP:[patients], B_PP:[with], B_NP:[nonacquired, cognitive, impairment], B_PP:[after], B_NP:[intensive, multidisciplinary, management], O_FULLSTOP:[.]]",
		"[B_ADVP:[However], O_COMMA:[,], B_NP:[it], B_VP:[appears], B_NP:[this, population], B_VP:[may, lose], B_NP:[less, weight], B_PP:[than], B_NP:[what], B_VP:[is, reported], B_PP:[for], B_NP:[patients], B_PP:[without], B_NP:[cognitive, delay], O_FULLSTOP:[.]]",
		};

		OpenNLPWrapper wrapper = new OpenNLPWrapper();
		List<List<WordChunk>> allConcatenatedChunks = wrapper.createConcatenatedChunks(ABSTRACT);
		int i = 0;
		for (List<WordChunk> wordChunks : allConcatenatedChunks) {
			Assert.assertEquals(chunks[i++], wordChunks.toString());
//			System.out.println(">>>"+wordChunks);
		}
	}

	@Test
	public void testPersonFinder() {
		NameFinderWrapper nameFinderWrapper = new NameFinderWrapper();
		nameFinderWrapper.setType(NameFinderWrapper.PERSON);
		nameFinderWrapper.searchSentenceForNames(ABSTRACT1, 0);
		List<List<String>> compoundNames = nameFinderWrapper.findCompoundNames();
		List<Span> spanList = nameFinderWrapper.getSpans();
		Assert.assertEquals(4,  spanList.size());
		Assert.assertEquals(4,  compoundNames.size());
		Assert.assertEquals("[James, Ford]",  compoundNames.get(0).toString());
		Assert.assertEquals("[Henry, Ford]",  compoundNames.get(1).toString());
		Assert.assertEquals("[Elizabeth, Smith]",  compoundNames.get(2).toString());
		Assert.assertEquals("[Tommy]",  compoundNames.get(3).toString());
	}

	@Test
	public void testPersonFinder1() {
		NameFinderWrapper nameFinderWrapper = new NameFinderWrapper();
		nameFinderWrapper.setType(NameFinderWrapper.PERSON);
		nameFinderWrapper.searchText(ABSTRACT1);
		List<List<String>> compoundNames = nameFinderWrapper.findCompoundNames();
		Assert.assertEquals(0, compoundNames.size());
	}

	@Test
	public void testDateFinder() {
//		findNames(DATE_ABSTRACT, OpenNLPWrapper.RESOURCE_BASE+OpenNLPWrapper.EN_DATE_BIN, "date");
		NameFinderWrapper nameFinderWrapper = new NameFinderWrapper();
		nameFinderWrapper.setType(NameFinderWrapper.DATE);
		nameFinderWrapper.searchText(DATE_ABSTRACT);
		List<List<String>> compoundNames = nameFinderWrapper.findCompoundNames();
		Assert.assertEquals(1, compoundNames.size());
		Assert.assertEquals("[from, January, 2004, to, December, 2009]", compoundNames.get(0).toString());
	}

	@Test
	public void testLocationFinder() {
		List<List<String>> values = searchText(LOCATION_ABSTRACT, NameFinderWrapper.LOCATION);
		Assert.assertEquals("[[East, Asia]]", values.toString());
		values = searchText(LOCATION1, NameFinderWrapper.LOCATION);
		Assert.assertEquals("[[Abacus, Airport], [Cambridge]]", values.toString());
	}

	private List<List<String>> searchText(String text, String type) {
		NameFinderWrapper nameFinderWrapper = new NameFinderWrapper();
		nameFinderWrapper.setType(type);
		nameFinderWrapper.searchText(text);
		List<List<String>> compoundNames = nameFinderWrapper.findCompoundNames();
		return compoundNames;
	}


}
