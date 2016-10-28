package org.xmlcml.norma.image.ocr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealRange;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.html.HtmlElement;

import nu.xom.Attribute;

public class HOCRTitle {
	
	private static final Logger LOG = Logger.getLogger(HOCRTitle.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String BASELINE = "baseline";
	private static final String BBOX = "bbox";
	private static final String IMAGE = "image";
	private static final String PPAGENO = "ppageno";
	private static final String TEXTANGLE = "textangle";
	private static final String X_WCONF = "x_wconf";
	
	private final static Pattern BASELINE_PATTERN = Pattern.compile("\\s*baseline\\s+(-?\\d*\\.?\\d+)\\s+(-?\\d*\\.?\\d+)\\s*");
	private final static Pattern BBOX_PATTERN = Pattern.compile("\\s*bbox\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*");
	private final static Pattern IMAGE_NAME_PATTERN = Pattern.compile("\\s*image\\s+\"(.*)\"\\s*");
	private final static Pattern PPAGENO_PATTERN = Pattern.compile("\\s*ppageno\\s+(\\d+)\\s*");
	private final static Pattern TEXTANGLE_PATTERN = Pattern.compile("\\s*textangle\\s+(-?\\d*\\.?\\d+)\\s*");
	private final static Pattern X_WCONF_PATTERN = Pattern.compile("\\s*x_wconf\\s+(\\d+)\\s*");

	private String[] fields;
	private String title;
	private Real2 baseline;
	private Real2Range bbox;
	private String imageName;
	private Integer ppageno;
	private Double textangle;
	private Integer xwconf;
	
	public HOCRTitle(String title) {
		this.title = title;
		processTitle();
	}
	
	private void processTitle() {
		fields = title.trim().split("\\s*;\\s*");
		for (String field : fields) {
			if (field.startsWith(BBOX)) {
				bbox = createBBox(field);
			} else if (field.startsWith(IMAGE)) {
				imageName = createImageName(field);
			} else if (field.startsWith(X_WCONF)) {
				xwconf = createXWConf(field);
			} else if (field.startsWith(PPAGENO)) {
				ppageno = createPPageNo(field);
			} else if (field.startsWith(BASELINE)) {
				baseline = createBaseline(field);
			} else if (field.startsWith(TEXTANGLE)) {
				textangle = createTextangle(field);
			} else {
				throw new RuntimeException("unknown title field: "+field);
			}
		}
	}

	private Double createTextangle(String field) {
		Matcher matcher = TEXTANGLE_PATTERN.matcher(field);
		if (matcher.matches()) {
			textangle = new Double(matcher.group(1));
		} else {
			throw new RuntimeException("Cannot parse textangle: "+field);
		}
		return textangle;
	}

	private Integer createPPageNo(String field) {
		Matcher matcher = PPAGENO_PATTERN.matcher(field);
		if (matcher.matches()) {
			ppageno = new Integer(matcher.group(1));
		} else {
			throw new RuntimeException("Cannot parse ppageno: "+field);
		}
		return ppageno;
	}

	private Integer createXWConf(String field) {
		Matcher matcher = X_WCONF_PATTERN.matcher(field);
		if (matcher.matches()) {
			xwconf = new Integer(matcher.group(1));
		} else {
			throw new RuntimeException("Cannot parse xwconf: "+field);
		}
		return xwconf;
	}

	private String createImageName(String field) {
		Matcher matcher = IMAGE_NAME_PATTERN.matcher(field);
		if (matcher.matches()) {
			imageName = matcher.group(1);
		} else {
			throw new RuntimeException("Cannot parse imagename: "+field);
		}
		return imageName;
	}

	private Real2 createBaseline(String field) {
		Matcher matcher = BASELINE_PATTERN.matcher(field);
		if (matcher.matches()) {
			Double x = new Double(matcher.group(1));
			Double y = new Double(matcher.group(2));
			baseline = new Real2(x, y);
		} else {
			throw new RuntimeException("Cannot parse baseline: "+field);
		}
		return baseline;
	}
	
	private Real2Range createBBox(String field) {
		Matcher matcher = BBOX_PATTERN.matcher(field);
		if (matcher.matches()) {
			Integer x0 = new Integer(matcher.group(1));
			Integer y0 = new Integer(matcher.group(2));
			Integer x1 = new Integer(matcher.group(3));
			Integer y1 = new Integer(matcher.group(4));
			bbox = new Real2Range(new RealRange(x0, x1), new RealRange(y0, y1));
		} else {
			throw new RuntimeException("Cannot parse bbox: "+field);
		}
		return bbox;
	}
	
	public Real2Range getBoundingBox() {
		return bbox;
	}

	public static HOCRTitle getHOCRTitle(HtmlElement sp) {
		HOCRTitle hocrTitle = null;
		String title = sp == null ? null : sp.getTitle();
		if (title != null) {
			hocrTitle = new HOCRTitle(title);
		}
		return hocrTitle;
	}

	public Double getTextangle() {
		return textangle;
	}

	public Real2 getBaseline() {
		return baseline;
	}

	public Integer getWConf() {
		return xwconf;
	}

	public void addAttributes(SVGG g) {
		if (baseline != null) {
			g.addAttribute(new Attribute(BASELINE, String.valueOf(baseline)));
		}
		if (textangle != null) {
			g.addAttribute(new Attribute(TEXTANGLE, String.valueOf(textangle)));
		}
		if (xwconf != null) {
			g.addAttribute(new Attribute(X_WCONF, String.valueOf(xwconf)));
		}
	}

}
