package org.xmlcml.norma.nlp;

/** OpenNLP has useful entity recognition resources but is large (ca 60+ MBytes)
 * 
 * @author pm286
 *
 */
public class OpenNLPWrapper {

//	public final static String RESOURCE_BASE = "/org/xmlcml/norma/util/";
//	public final static String EN_SENT_BIN = "en-sent.bin";
//	public final static String EN_TOKEN_BIN = "en-token.bin";
//	public final static String EN_POS_MAXENT_BIN = "en-pos-maxent.bin";
//	public final static String EN_CHUNKER_BIN = "en-chunker.bin";
//	// name recognizers
//	public final static String EN_DATE_BIN = "en-ner-date.bin";
//	public final static String EN_LOCATION_BIN = "en-ner-location.bin";
//	public final static String EN_MONEY_BIN = "en-ner-money.bin";
//	public final static String EN_ORGANIZATION_BIN = "en-ner-organization.bin";
//	public final static String EN_PERCENTAGE_BIN = "en-ner-percentage.bin";
//	public final static String EN_PERSON_BIN = "en-ner-person.bin";
//	public final static String EN_TIME_BIN = "en-ner-time.bin";
//	
//	private List<String> sentences;
//	private SentenceDetector sentenceDetector;
//	private List<String> sentenceTokens;
//	private List<String> sentenceTags;
//	private POSTagger posTagger;
//	private Tokenizer tokenizer;
//	private Chunker chunker;
//	private List<String> chunkTags;
//	
//	
//	public List<String> tokenizeSentenceInText(String text, int sentenceIndex) {
//		clearText();
//		sentences = getOrCreateSentences(text);
//		String sentence = sentences.get(sentenceIndex);
//		tokenizeSentence(sentence);
//		return sentenceTokens;
//	}
//
//	void clearText() {
//		sentences = null;
//		clearSentence();
//	}
//
//	private void clearSentence() {
//		sentenceTokens = null;
//		sentenceTags = null;
//		chunkTags = null;
//	}
//
//	public List<String> tokenizeSentence(String sentence) {
//		clearSentence();
//		getOrCreateTokenizer();
//		sentenceTokens = Arrays.asList(tokenizer.tokenize(sentence));
//		return sentenceTokens;
//	}
//
//	public List<String> posTagSentence(String text, int sentenceIndex) {
//		tokenizeSentenceInText(text, sentenceIndex);
//		getOrCreatePOSTagger();
//		String[] tags = posTagger.tag(sentenceTokens.toArray(new String[0]));
//		sentenceTags = Arrays.asList(tags);
//		return sentenceTags;
//	}
//
//	public List<String> getOrCreateSentences(String text) {
//		if (sentences == null) {
//			getOrCreateSentenceDetector();
//			sentences = Arrays.asList(sentenceDetector.sentDetect(text));
//		}
//		return sentences;
//	}
//
//	public List<String> getOrCreateChunkTags() {
//		getOrCreateChunker();
//		String[] chunkTs = chunker.chunk(sentenceTokens.toArray(new String[0]), sentenceTags.toArray(new String[0]));
//		chunkTags = new ArrayList<String>(Arrays.asList(chunkTs));
//		return chunkTags;
//	}
//
//	private Tokenizer getOrCreateTokenizer() {
//		if (tokenizer == null) {
//			tokenizer = OpenNLPWrapper.createTokenizer();
//		}
//		return tokenizer;
//	}
//
//	public POSTagger getOrCreatePOSTagger() {
//		if (posTagger == null) {
//			posTagger = OpenNLPWrapper.createPOSTagger();
//		}
//		return posTagger;
//	}
//
//	private SentenceDetector getOrCreateSentenceDetector() {
//		if (sentenceDetector == null) {
//			sentenceDetector = OpenNLPWrapper.createSentenceDetector();
//		}
//		return sentenceDetector;
//	}
//
//	private void getOrCreateChunker() {
//		if (chunker == null) {
//			chunker = OpenNLPWrapper.createChunker();
//		}
//	}
//
//	public List<String> getSentenceTokens() {
//		return sentenceTokens;
//	}
//
//	List<Span> findSpansInSentence(NameFinderWrapper nameFinderWrapper) {
//		TokenNameFinder nameFinder = OpenNLPWrapper.createTokenNameFinder(nameFinderWrapper.resourceString);
//		nameFinderWrapper.spans = Arrays.asList(nameFinder.find(getSentenceTokens().toArray(new String[0])));
//		return nameFinderWrapper.spans;
//	}
//
//	public List<WordChunk> createRawChunkList(List<String> chunkTags, List<String> tokens) {
//		List<WordChunk> rawChunks = new ArrayList<WordChunk>();
//		if (chunkTags.size() > 0) {
//			for (int i = 0; i < chunkTags.size(); i++) {
//				WordChunk rawChunk = new WordChunk(chunkTags.get(i), tokens.get(i));
//				rawChunks.add(rawChunk);
//			}
////			System.out.println(rawChunks);
//		}
//		return rawChunks;
//	}
//
//	public List<WordChunk> createConcatenatedChunks(List<WordChunk> tempChunks) {
//		List<WordChunk> newChunks = new ArrayList<WordChunk>();
//		WordChunk lastChunk = tempChunks.get(0);
//		WordChunk newChunk = new WordChunk(lastChunk.getChunkTag(), lastChunk.getToken());
//		newChunks.add(newChunk);
//		for (int i = 1; i < tempChunks.size(); i++) {
//			lastChunk = tempChunks.get(i - 1);
//			WordChunk thisChunk = tempChunks.get(i);
//			String leader = thisChunk.getLeader();
//			if (WordChunk.B.equals(leader) || WordChunk.O.equals(leader)) {
//				newChunk = new WordChunk(thisChunk.getChunkTag(), thisChunk.getToken());
//				newChunks.add(newChunk);
//			} else if (WordChunk.I.equals(leader)) {
//				String lastTag = lastChunk.getPosTag();
//				String thisTag = thisChunk.getPosTag();
//				if (lastTag.equals(thisTag)) {
//					newChunk.addToken(thisChunk.getToken());
//				} else {
//					System.err.println("incompatible POSTags: "+lastTag+" != "+thisTag);
//				}
//			} else {
//				System.err.println("Unknown leader ("+leader+") in tag: "+thisChunk);
//			}
//		}
////		System.out.println(newChunks);
//		return newChunks;
//	}
//
//	public List<WordChunk> createConcatenatedChunks(String text, int sentenceIndex) {
//		posTagSentence(text, sentenceIndex);
//		List<String> chunkTags = getOrCreateChunkTags();
//		List<String> tokens = getSentenceTokens();
//		List<WordChunk> tempChunks = createRawChunkList(chunkTags, tokens);
//		List<WordChunk> concatenatedChunks = createConcatenatedChunks(tempChunks);
//		return concatenatedChunks;
//	}
//
//	public List<List<WordChunk>> createConcatenatedChunks(String text) {
//		int sentenceCount = getOrCreateSentenceDetector().sentDetect(text).length;
//		List<List<WordChunk>> chunkListList = new ArrayList<List<WordChunk>>();
//		for (int sentenceIndex = 0; sentenceIndex < sentenceCount; sentenceIndex++) {
//			posTagSentence(text, sentenceIndex);
//			List<String> chunkTags = getOrCreateChunkTags();
//			List<String> tokens = getSentenceTokens();
//			List<WordChunk> tempChunks = createRawChunkList(chunkTags, tokens);
//			List<WordChunk> concatenatedChunks = createConcatenatedChunks(tempChunks);
//			chunkListList.add(concatenatedChunks);
//		}
//		return chunkListList;
//	}
//
//	public static SentenceDetector createSentenceDetector() {
//		SentenceDetector _sentenceDetector = null;		
//		InputStream modelIn = null;
//		
//		try {
//		   // Loading sentence detection model
//		   modelIn = OpenNLPWrapper.class.getResourceAsStream(RESOURCE_BASE+EN_SENT_BIN);
//		   final SentenceModel sentenceModel = new SentenceModel(modelIn);
//		   modelIn.close();
//		   _sentenceDetector = new SentenceDetectorME(sentenceModel);
//		 
//		} catch (final IOException ioe) {
//		   ioe.printStackTrace();
//		} finally {
//		   if (modelIn != null) {
//		      try {
//		         modelIn.close();
//		      } catch (final IOException e) {} // oh well!
//		   }
//		}
//		return _sentenceDetector;
//	}
//	
//	public static Tokenizer createTokenizer() {
//		Tokenizer _tokenizer = null;
//		 
//		InputStream modelIn = null;
//		try {
//		   // Loading tokenizer model
//		   modelIn = OpenNLPWrapper.class.getResourceAsStream(RESOURCE_BASE+EN_TOKEN_BIN);
//		   final TokenizerModel tokenModel = new TokenizerModel(modelIn);
//		   modelIn.close();
//		 
//		   _tokenizer = new TokenizerME(tokenModel);
//		 
//		} catch (final IOException ioe) {
//		   ioe.printStackTrace();
//		} finally {
//		   if (modelIn != null) {
//		      try {
//		         modelIn.close();
//		      } catch (final IOException e) {} // oh well!
//		   }
//		}
//		return _tokenizer;
//	}
//
//	public static POSTagger createPOSTagger() {
//		POSTagger _posTagger = null;
//		String binResource = RESOURCE_BASE+EN_POS_MAXENT_BIN;
//		InputStream modelIn = null;
//		try {
//		   // Loading tagger model
//		   modelIn = OpenNLPWrapper.class.getResourceAsStream(binResource);
//		   if (modelIn == null) {
//			   throw new RuntimeException("Cannot find POSTagger resource: "+binResource);
//		   }
//		   final POSModel posTaggerModel = new POSModel(modelIn);
//		   modelIn.close();
//		 
//		   _posTagger = new POSTaggerME(posTaggerModel);
//		 
//		} catch (final IOException ioe) {
//		   ioe.printStackTrace();
//		} finally {
//		   if (modelIn != null) {
//		      try {
//		         modelIn.close();
//		      } catch (final IOException e) {} // oh well!
//		   }
//		}
//		return _posTagger;
//	}
//
//	public static Chunker createChunker() {
//		Chunker _chunker = null;
//		String binResource = RESOURCE_BASE+EN_CHUNKER_BIN;
//		InputStream modelIn = null;
//		try {
//		   // Loading chunker model
//		   modelIn = OpenNLPWrapper.class.getResourceAsStream(binResource);
//		   if (modelIn == null) {
//			   throw new RuntimeException("Cannot find Chunker resource: "+binResource);
//		   }
//		   final ChunkerModel chunkerModel = new ChunkerModel(modelIn);
//		   modelIn.close();
//		 
//		   _chunker = new ChunkerME(chunkerModel);
//		 
//		} catch (final IOException ioe) {
//		   ioe.printStackTrace();
//		} finally {
//		   if (modelIn != null) {
//		      try {
//		         modelIn.close();
//		      } catch (final IOException e) {} // oh well!
//		   }
//		}
//		return _chunker;
//	}
//
//	public static TokenNameFinder createTokenNameFinder(String binResource) {
//		TokenNameFinder _nameFinder = null;
//		 
//		InputStream modelIn = null;
//		try {
//		   // Loading chunker model
//		   modelIn = OpenNLPWrapper.class.getResourceAsStream(binResource);
//		   if (modelIn == null) {
//			   throw new RuntimeException("Cannot find NameFinder resource: "+binResource);
//		   }
//		   final TokenNameFinderModel chunkerModel = new TokenNameFinderModel(modelIn);
//		   modelIn.close();
//		 
//		   _nameFinder = new NameFinderME(chunkerModel);
//		 
//		} catch (final IOException ioe) {
//		   ioe.printStackTrace();
//		} finally {
//		   if (modelIn != null) {
//		      try {
//		         modelIn.close();
//		      } catch (final IOException e) {} // oh well!
//		   }
//		}
//		return _nameFinder;
//	}





}
