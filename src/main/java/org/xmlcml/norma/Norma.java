package org.xmlcml.norma;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xmlcml.html.HtmlElement;

public class Norma {

	private NormaArgProcessor argProcessor;
	private List<String> inputList;
	private List<HtmlElement> outputList;
	private List<InputWrapper> inputWrapperList;

	public static void main(String[] args) {
		Norma norma = new Norma();
		norma.run(args);
	}

	public void run(String[] args) {
		argProcessor = new NormaArgProcessor(args);
		createInputList();
		findFileTypesAndExpandDirectories();
		normalizeInputs();
	}

	private void findFileTypesAndExpandDirectories() {
		inputWrapperList = new ArrayList<InputWrapper>();
		for (String inputName : inputList) {
			InputWrapper inputWrapper;
			File file = new File(inputName);
			if (file.exists()) {
				List<String> extensions = argProcessor.extensions;
				boolean recursive = argProcessor.isRecursive();
				addInputWrappersForFiles(inputName, file, extensions, recursive);
			} else{
				addInputWrapperForURL(inputName);
			}
		}
	}

	private void addInputWrapperForURL(String inputName) {
		InputWrapper inputWrapper;
		URL url = null;
		if (!inputName.startsWith("http")) {
			try {
				url = new URL(inputName = "http://"+inputName); 
			} catch (Exception e) {
				// fails
			}
		} else {
			try {
				url = new URL(inputName);
			} catch (Exception e) {
				// fails
			}
		}
		if (url != null) {
			inputWrapper = new InputWrapper(url, inputName);
			inputWrapperList.add(inputWrapper);
		}
	}

	private void addInputWrappersForFiles(String inputName, File file,
			List<String> extensions, boolean recursive) {
		InputWrapper inputWrapper;
		if (!file.isDirectory()) {
			inputWrapper = new InputWrapper(file, inputName);
			inputWrapperList.add(inputWrapper);
		} else {
			List<InputWrapper> inputWrappers = InputWrapper.expandDirectories(file, extensions, recursive);
			inputWrappers.addAll(inputWrappers);
		}
	}

	private void normalizeInputs() {
		outputList = new ArrayList<HtmlElement>();
		for (InputWrapper inputWrapper : inputWrapperList) {
			HtmlElement element = inputWrapper.transform();
			outputList.add(element);
		}
	}


	private void createInputList() {
		argProcessor.expandWildcardsExhaustively();
		inputList = argProcessor.getInputList();
	}
}
