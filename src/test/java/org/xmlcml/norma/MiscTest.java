package org.xmlcml.norma;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.util.SHTMLTransformer;

public class MiscTest {

	
	private static final Logger LOG = Logger.getLogger(MiscTest.class);
	
	public void test() {
		/** for example only
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");  // use saxon for xslt 2.0 support
		File styleSheet = new File("filePath");
	
		// Get a TransformerFactory
		System.setProperty("javax.xml.transform.TransformerFactory",
		                   "com.saxonica.config.ProfessionalTransformerFactory");
		TransformerFactory tfactory = TransformerFactory.newInstance();
	//	ProfessionalConfiguration config = (ProfessionalConfiguration)((TransformerFactoryImpl)tfactory).getConfiguration();
	
		// Get a SAXBuilder 
		SAXBuilder builder = new SAXBuilder(); 
	
		//Build JDOM Document
		Document toTransform = builder.build(inputFileHandle); 
	
		//Give it a Saxon wrapper
		DocumentWrapper docw = new DocumentWrapper(toTransform,  inputHandle.getAbsolutePath(), config);
	
		// Compile the stylesheet
		Templates templates = tfactory.newTemplates(new StreamSource(styleSheet));
		Transformer transformer = templates.newTransformer();
	
		// Now do a transformation
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(1024);                  
		transformer.transform(docw, new StreamResult(outStream));
	
		ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
		Document transformed = builder.build(inStream);
		*/
		
		/** http://stackoverflow.com/questions/11314604/how-to-set-saxon-as-the-xslt-processor-in-java
		 * 
		 * 

    Explicitly instantiate the Saxon factory (with a nod to Michael's comment above):

    TransformerFactory fact = new net.sf.saxon.TransformerFactoryImpl()

    Specify the factory class when constructing it:

    TransformerFactory fact = TransformerFactory.newInstance(
            "net.sf.saxon.TransformerFactoryImpl", null);

    (Note: available as of Java 6. The Java 5 version does not have this method.)

    Set the javax.xml.transform.TransformerFactory system property before creating an instance:

    System.setProperty("javax.xml.transform.TransformerFactory",    
            "net.sf.saxon.TransformerFactoryImpl");

    Or on the command line (line broken for readability):

    java -Djavax.xml.transform.TransformerFactory=
            cnet.sf.saxon.TransformerFactoryImpl YourApp

    Create the following file:

    JRE/lib/jaxp.properties

    With the following contents:

    javax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl

    Create the following file in any JAR on the CLASSPATH:

    META-INF/services/javax.xml.transform.TransformerFactory

    With the following contents:

    net.sf.saxon.TransformerFactoryImpl 

    If none of the above are done, then the platform default TransformerFactory instance will be loaded

A friendly description of this plugability layer can be found here:

    http://onjava.com/pub/a/onjava/excerpt/java_xslt_ch5/index.html?page=3#plug_layer ***

I'd consider this answer an argument against the Java way of doing things.
		 */
	}
	
//	@Test
//	public void testSaxon() throws Exception {
//	    SAXTransformerFactory saxTransformerFactory = new net.sf.saxon.TransformerFactoryImpl();
////	    File styleFile = new File("src/main/resources/org/xmlcml/norma/style/groupTest.xsl");
//	    File styleFile = new File("src/main/resources/org/xmlcml/norma/style/miniTest.xsl");
//	    Assert.assertTrue("xsl ", styleFile.exists());
//	    FileInputStream styleIs = new FileInputStream(styleFile);
//	    InputSource styleSource = new InputSource(styleIs);
//	    SAXSource saxStyleSource = new SAXSource(styleSource);
//	    TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler(saxStyleSource);
//	    StreamResult streamResult = new StreamResult(System.out);
//	    transformerHandler.setResult(streamResult);
//	    Transformer transformer = null;
//	    try {
//	    	transformer = saxTransformerFactory.newTransformer(saxStyleSource);
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    	throw e;
//	    }
//	    
//
//	    
//	    /**
//	    File inputFile = Fixtures.F507405_XML;
//	    FileInputStream inputIs = new FileInputStream(inputFile);
//	    InputSource inputSource = new InputSource(inputIs);
//	    SAXSource saxInputSource = new SAXSource(inputSource);
//	    SAXResult result = new SAXResult(transformerHandler);
//	    transformer.transform(saxInputSource, result);
//	    LOG.debug("result "+result);
//	    */
//	}
	
	@Test
	@Ignore // xsl file deleted
	public void testSaxon0() throws Exception {
//		http://stackoverflow.com/questions/9925483/calling-java-from-xsl-saxon	

	    System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    File xml_file = Fixtures.F507405_XML;
	    File xsl_file = Fixtures.MINI_XSL;
	    

	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(xsl_file));
	    transformer.transform(new StreamSource(xml_file), new StreamResult(System.out));
	}
	
	@Test
	public void testSaxonXslt2GroupTest1() throws Exception {
//		http://stackoverflow.com/questions/9925483/calling-java-from-xsl-saxon	

	    File xml_file = Fixtures.XSLT2_TEST1_XML;
	    File xsl_file = Fixtures.XSLT2_TEST1_XSL;
	    

	    System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(xsl_file));
	    File saxonDir = new File("target/saxon/");
	    saxonDir.mkdirs();
    	transformer.transform(new StreamSource(xml_file),  
    		new StreamResult(new FileOutputStream(new File(saxonDir, "test1.xml"))));
	}
	
	@Test
	@Ignore // wrong way of creating a Transformer
	public void testSaxonGroup() throws Exception {
//		http://stackoverflow.com/questions/9925483/calling-java-from-xsl-saxon	

	    File xml_file = Fixtures.F507405_XML;
	    File xsl_file = Fixtures.GROUP_MAJOR_SECTIONS_XSL;
	    
// this is wrong
	    TransformerFactory tfactory = net.sf.saxon.TransformerFactoryImpl.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(xsl_file));
	    transformer.transform(new StreamSource(xml_file),  new StreamResult(new FileOutputStream("target/saxon/group.xml")));
	}
	
	/** 
		// http://stackoverflow.com/questions/1312406/efficient-xslt-pipeline-in-java-or-redirecting-results-to-sources/1319774#1319774
	 */
	@Test
	@Ignore // resurrect when we need to chain processing.
	public void testChain() throws Exception {
		SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory)TransformerFactory.newInstance();

		// These templates objects could be reused and obtained from elsewhere.
		Templates templates1 = saxTransformerFactory.newTemplates(new StreamSource(
		  getClass().getResourceAsStream("MyStylesheet1.xslt")));
		Templates templates2 = saxTransformerFactory.newTemplates(new StreamSource(
		  getClass().getResourceAsStream("MyStylesheet1.xslt")));

		TransformerHandler transformerHandler1 = saxTransformerFactory.newTransformerHandler(templates1);
		TransformerHandler transformerHandler2 = saxTransformerFactory.newTransformerHandler(templates2);

		transformerHandler1.setResult(new SAXResult(transformerHandler2));
		transformerHandler2.setResult(new StreamResult(System.out));

		Transformer transformer = saxTransformerFactory.newTransformer();
		SAXResult saxResult1 = new SAXResult(transformerHandler1);
		transformer.transform(new StreamSource(System.in), saxResult1);

		// th1 feeds th2, which in turn feeds System.out.
	}
	
	@Test
	public void testRecursiveGrouping() throws Exception {
		File flatFile = new File("src/test/resources/org/xmlcml/norma/style/flatFile.xml");
		File styleFile = new File("src/test/resources/org/xmlcml/norma/style/recursivegrouper.xsl");
		File groupedFile = new File("target/testgroup/recursiveGroup.xml");
	    SHTMLTransformer.transform(flatFile, styleFile, groupedFile);
	}
	
	@Test
	// http://stackoverflow.com/questions/27406846/nesting-flat-xml-siblings/27407470#27407470 
	public void testRecursiveGroupingStackExample() throws Exception {
		File flatFile = new File("src/test/resources/org/xmlcml/norma/style/stackexample.xml");
		File styleFile = new File("src/test/resources/org/xmlcml/norma/style/recursivegrouperstack.xsl");
		File groupedFile = new File("target/testgroup/stackexample.xml");
	    SHTMLTransformer.transform(flatFile, styleFile, groupedFile);
	}
	
	@Test
	public void testH1H2Grouping() throws Exception {
		File flatFile = new File("src/test/resources/org/xmlcml/norma/style/flatFile.xml");
		File styleFile = new File("src/test/resources/org/xmlcml/norma/style/h1h2grouper.xsl");
		File groupedFile = new File("target/testgroup/h1h2grouper.xml");
	    SHTMLTransformer.transform(flatFile, styleFile, groupedFile);
	}
	
	@Test
	public void testURL() {
		try {
			URL url = new URL("http://www.foo/");
		} catch (Exception e) {
			Assert.fail("shouldn't get here :"+e);
		}
		try {
			URL url = new URL("www.foo/");
			Assert.fail("bad url shoudl be trapped");
		} catch (Exception e) {
			Assert.assertEquals("bad Url", MalformedURLException.class, e.getClass());
		}
	}
}
