package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGLine;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGPolyline;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGShape;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.svgbuilder.geom.Path2ShapeConverter;


public class SVGDiagram extends SVGG {

	private static final Logger LOG = Logger.getLogger(SVGDiagram.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	protected List<SVGPath> svgPathList;
	protected List<SVGText> svgTextList;
	protected List<SVGTextBox> textBoxList;
	protected List<List<SVGShape>> shapeListList;
	protected SVGG newG;
	protected List<SVGTriangle> triangleList;
	protected List<SVGLine> lineList;
	protected List<SVGArrow> arrowList;
	protected double eps = 2.0; // pixel tolerance
	protected List<SVGRect> rectList;
	protected List<SVGConnector> connectorList;
	protected List<SVGPath> pathList;
	protected List<SVGPolyline> polylineList;
	protected List<SVGRoundedBox> roundedBoxList;
	protected SVGElement rawDiagram;

	public SVGDiagram() {
		super();
		this.addAttribute(new Attribute("class", "diagram"));
	}

	protected void createShapes() {
		triangleList = new ArrayList<SVGTriangle>();
		lineList = new ArrayList<SVGLine>();
		polylineList = new ArrayList<SVGPolyline>();
		rectList = new ArrayList<SVGRect>();
		pathList = new ArrayList<SVGPath>();
		roundedBoxList = new ArrayList<SVGRoundedBox>();
		
		newG = new SVGG();
		for (List<SVGShape> shapeList : shapeListList) {
			for (SVGShape shape : shapeList) {
				shape.detach();
				if (shape instanceof SVGRect) {
					addNewRect((SVGRect)shape);
				} else if (shape instanceof SVGLine) {
					addNewLine((SVGLine)shape);
				} else if (shape instanceof SVGPolyline) {
					SVGPolyline polyline = (SVGPolyline) shape;
					if (polyline.isClosed() && polyline.getLineList().size() == 3) {
						SVGTriangle triangle = new SVGTriangle(polyline);
						addNewTriangle(triangle);
					} else {
						addNewPolyline(polyline);
					}
				} else if (shape instanceof SVGPath) {
					if (shape.getSignature().equals("M")) {
						System.err.println("omitted M");
					} else {
						SVGRoundedBox roundedBox = SVGRoundedBox.createRoundedBox((SVGPath)shape);
						if (roundedBox != null) {
							addNewRoundedBox(roundedBox);
						} else {
							addNewPath((SVGPath)shape);
						}
					}
				} else {
					System.err.println("Unknown shape "+shape);
					shape.setStroke("green");
					shape.setStrokeWidth(2.0);
					newG.appendChild(shape.copy());
				}
			}
		}
		polylineList = SVGPolyline.quadraticMergePolylines(polylineList, eps);
		lineList = SVGLine.normalizeAndMergeAxialLines(lineList, eps);
		LOG.debug("roundedBoxList: "+roundedBoxList.size());
		LOG.debug("paths: "+pathList.size());
		LOG.debug("polylines: "+polylineList.size());
		LOG.debug("lines "+lineList.size());
		LOG.debug("rects "+rectList.size());
		LOG.debug("triangles "+triangleList.size());
	}

	private void addNewPath(SVGPath path) {
		if (!containsPath(pathList, path, eps)) {
			pathList.add(path);
		}
	}

	private boolean containsPath(List<SVGPath> pathList, SVGPath path0, double delta) {
		for (SVGPath path : pathList) {
			if (path.hasEqualCoordinates(path0, delta)) {
				return true;
			}
		}
		return false;
	}

	private void addNewRoundedBox(SVGRoundedBox roundedBox) {
		if (!containsRoundedBox(roundedBoxList, roundedBox, eps)) {
			roundedBoxList.add(roundedBox);
		}
	}

	private boolean containsRoundedBox(List<SVGRoundedBox> roundedBoxList, SVGRoundedBox roundedBox0, double delta) {
		for (SVGRoundedBox roundedBox : roundedBoxList) {
			if (roundedBox.getPath().hasEqualCoordinates(roundedBox0.getPath(), delta)) {
				return true;
			}
		}
		return false;
	}

	private void addNewTriangle(SVGTriangle triangle) {
		if (!containsTriangle(triangleList, triangle, eps)) {
			triangleList.add(triangle);
		}
	}

	private boolean containsTriangle(List<SVGTriangle> triangleList, SVGTriangle triangle0, double delta) {
		for (SVGTriangle triangle : triangleList) {
			if (triangle.hasEqualCoordinates(triangle0, delta)) {
				return true;
			}
		}
		return false;
	}

	private void addNewPolyline(SVGPolyline polyline) {
		if (!containsPolyline(polylineList, polyline, eps)) {
			polylineList.add(polyline);
		}
	}

	private boolean containsPolyline(List<SVGPolyline> polylineList, SVGPolyline polyline0, double delta) {
		for (SVGPolyline polyline : polylineList) {
			if (polyline.hasEqualCoordinates(polyline0, delta)) {
				return true;
			}
		}
		return false;
	}

	private void addNewLine(SVGLine line) {
		if (!containsLine(lineList, line, eps)) {
			lineList.add(line);
		}
	}

	private boolean containsLine(List<SVGLine> lineList, SVGLine line0, double delta) {
		for (SVGLine line : lineList) {
			if (line.hasEqualCoordinates(line0, delta)) {
				return true;
			}
		}
		return false;
	}

	private void addNewRect(SVGRect rect) {
		if (!containsRect(rectList, rect, eps)) {
			rectList.add(rect);
		}
	}

	private boolean containsRect(List<SVGRect> rectList, SVGRect rect0, double delta) {
		for (SVGRect rect : rectList) {
			if (rect.isEqual(rect0, delta)) {
				return true;
			}
		}
		return false;
	}

	protected void createTextBoxes() {
		textBoxList = new ArrayList<SVGTextBox>();
		for (SVGRect rect : rectList) {
			Real2Range rect2R = rect.getBoundingBox();
			SVGTextBox textBox = null;
			for (SVGText text : svgTextList) {
				Real2Range text2R = text.getBoundingBox();
				if (rect2R.includes(text2R)) {
					if (textBox == null) {
						textBox = new SVGTextBox(rect);
						textBoxList.add(textBox);
					}
					text.detach();
					textBox.add(text);
				}
			}
		}
		LOG.debug("textBoxList "+textBoxList.size());
	}

	protected void createArrows(double delta) {
			arrowList = new ArrayList<SVGArrow>();
			LOG.debug("triangles "+triangleList.size());
			for (SVGTriangle triangle : triangleList) {
				LOG.debug("t "+triangle);
			}
			LOG.debug("lines "+lineList.size());
			for (SVGLine line : lineList) {
	//			LOG.debug("l "+line);
			}
			for (SVGTriangle triangle : triangleList) {
				int j = 0;
				for (; j < lineList.size(); j++) {
					SVGLine line = lineList.get(j);
					SVGArrow arrow = SVGArrow.createArrow(line, triangle, delta);
					if (arrow != null) {
						LOG.debug("arrow "+arrow);
						newG.appendChild(arrow);
						line.detach();
						lineList.set(j, null);
						triangle.detach();
						arrowList.add(arrow);
						break;
					}
				}
				lineList.remove(j);
			}
			LOG.debug("created arrows: "+arrowList.size());
		}

	protected List<SVGConnector> findConnectors(double delta) {
		connectorList = new ArrayList<SVGConnector>();
		List<SVGLine> arrowsAndLines = new ArrayList<SVGLine>();
		arrowsAndLines.addAll(arrowList);
		arrowsAndLines.addAll(lineList);
		for (SVGLine line : arrowsAndLines) {
			SVGTextBox headBox = SVGTextBox.getTouchingBox(line.getXY(0), textBoxList, delta);
			if (headBox != null) {
				SVGTextBox tailBox = SVGTextBox.getTouchingBox(line.getXY(1), textBoxList, delta);
				if (tailBox != null) {
					SVGConnector link = new SVGConnector(tailBox, headBox);
					LOG.debug("LINK!! "+link);
					connectorList.add(link);
				}
			}
		}
		return connectorList;
	}

//	protected List<SVGConnector> findConnectingLines(double delta) {
//		List<SVGConnector> lineConnectorList = new ArrayList<SVGConnector>();
//		for (SVGLine line : lineList) {
//			SVGTextBox headBox = SVGTextBox.getTouchingBox(line.getXY(0), textBoxList, delta);
//			if (headBox != null) {
//				SVGTextBox tailBox = SVGTextBox.getTouchingBox(line.getXY(1), textBoxList, delta);
//				if (tailBox != null) {
//					SVGConnector lineConnector = new SVGConnector(tailBox, headBox);
//					LOG.debug("LINK!! "+lineConnector);
//					lineConnectorList.add(lineConnector);
//				}
//			}
//		}
//		return lineConnectorList;
//	}

	public SVGG getNewG() {
		return newG;
	}

	protected void createPathsTextAndShapes() {
		svgPathList = SVGPath.extractPaths(rawDiagram);
		svgTextList = SVGText.extractSelfAndDescendantTexts(rawDiagram);
		Path2ShapeConverter  converter = new Path2ShapeConverter();
		shapeListList = converter.convertPathsToShapesAndSplitAtMoves(svgPathList);
		this.createShapes();
	}


}
