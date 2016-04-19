package org.xmlcml.norma.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordChunk {

	public static final String O = "O";
	public static final String I = "I";
	public static final String B = "B";
	
	private String chunkTag;
	private String token;
	private String posTag;
	private String leader;
	private static Map<String, String> codeByPunctuation = new HashMap<String, String>();
	private List<String> tokenList;
	
	static {
		codeByPunctuation.put(",", "COMMA");
		codeByPunctuation.put(";", "SEMICOLON");
		codeByPunctuation.put(":", "COLON");
		codeByPunctuation.put(".", "FULLSTOP");
	};
	

	public WordChunk(String chunkTag, String token) {
		this.chunkTag = chunkTag;
		this.token = token;
		tokenList = new ArrayList<String>();
		addToken(token);
		interpretChunkTag();
	}

	private void interpretChunkTag() {
		if (chunkTag.contains("-")) {
			this.leader = chunkTag.split("-")[0];
			this.posTag = chunkTag.split("-")[1];
			if (!B.equals(leader) && !I.equals(leader)) {
				System.err.println("unknown leader: "+leader);
			}
		} else if (chunkTag.equals(O)) {
			this.leader = O;
			String punct = codeByPunctuation.get(token);
			this.posTag = punct == null ? token : punct;
		} else {
			System.err.println("unknown chunkTag: "+chunkTag);
		}
	}
	
	public String getChunkTag() {
		return chunkTag;
	}
	
	public String getPosTag() {
		return posTag;
	}
	
	public String getLeader() {
		return leader;
	}
	
	public String getToken() {
		return token;
	}
	
	public List<String> getTokenList() {
		return tokenList;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(leader);
		sb.append("_"+posTag);
		sb.append(":"+tokenList.toString());
		return sb.toString();
	}

	public void addToken(String token) {
		if (tokenList == null) {
		}
		this.tokenList.add(token);
	}

}
