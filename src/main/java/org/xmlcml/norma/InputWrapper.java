package org.xmlcml.norma;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;

/** wraps the input, optionally determing its type.
 * 
 * @author pm286
 *
 */
public class InputWrapper {

	
	private static final Logger LOG = Logger.getLogger(InputWrapper.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private String inputName;
	private URL url;
	private File file;
	private String content;
	private Pubstyle journal;
	private HtmlElement htmlElement;

	public InputWrapper(File file, String inputName) {
		this.file = file;
		this.inputName = inputName;
	}

	public InputWrapper(URL url, String inputName) {
		this.url = url;
		this.inputName = inputName;
	}

	public static List<InputWrapper> expandDirectories(File dir, List<String> extensions, boolean recursive) {
		List<InputWrapper> inputWrapperList = new ArrayList<InputWrapper>();
		Iterator<File> fileList = FileUtils.iterateFiles(dir, extensions.toArray(new String[0]), recursive);
		while (fileList.hasNext()) {
			inputWrapperList.add(new InputWrapper(fileList.next(), null));
		}
		return inputWrapperList;
	}

	public HtmlElement transform() {
		preliminaryReadToDetermineEncoding();
		deduceContentType();
		if (journal == null) {
			journal = Pubstyle.deduceJournal(content);
		}
		journal.read();
		return htmlElement;
	}

	private Object deduceContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	private void preliminaryReadToDetermineEncoding() {
		// TODO Auto-generated method stub
		
	}

}
