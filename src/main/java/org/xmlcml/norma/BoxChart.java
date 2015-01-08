package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
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

public class BoxChart {
	
	private static final Logger LOG = Logger.getLogger(BoxChart.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private SVGElement rawChart;
	private List<SVGPath> svgPathList;
	private List<SVGText> svgTextList;
	private List<SVGTextBox> textBoxList;
	private List<List<SVGShape>> shapeListList;
	private SVGG newG;
	private List<SVGTriangle> triangleList;
	private List<SVGLine> lineList;
	private List<SVGArrow> arrowList;
	private double delta = 2.0;
	private List<SVGRect> rectList;
	private List<Connector> connectorList;
	private List<SVGPath> pathList;
	private List<SVGPolyline> polylineList;
	private List<SVGRoundedBox> roundedBoxList;

	public BoxChart(SVGElement chart) {
		this.rawChart = chart;
	}

	public void createChart() {
		svgPathList = SVGPath.extractPaths(rawChart);
		svgTextList = SVGText.extractSelfAndDescendantTexts(rawChart);
		Path2ShapeConverter  converter = new Path2ShapeConverter();
		shapeListList = converter.convertPathsToShapesAndSplitAtMoves(svgPathList);
		this.createShapes();
		this.createTextBoxes();
		this.createArrows(delta);
		this.findConnectors(delta);
		for (Connector link : connectorList) {
			System.err.println("=================== \n"+link.toString()+"\n===================");
		}
	}
	
	private void createShapes() {
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
					System.err.println(shape);
					shape.setStroke("green");
					shape.setStrokeWidth(2.0);
					newG.appendChild(shape.copy());
				}
			}
		}
		LOG.debug("roundedBoxList: "+roundedBoxList.size());
		LOG.debug("paths: "+pathList.size());
		for (SVGPath path : pathList) {
			LOG.debug("> "+path.getSignature());
		}

	}

	private void addNewPath(SVGPath path) {
		if (!containsPath(pathList, path, delta)) {
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
		if (!containsRoundedBox(roundedBoxList, roundedBox, delta)) {
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
		if (!containsTriangle(triangleList, triangle, delta)) {
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
		if (!containsPolyline(polylineList, polyline, delta)) {
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
		if (!containsLine(lineList, line, delta)) {
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
		if (!containsRect(rectList, rect, delta)) {
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

	private void createTextBoxes() {
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
	
	private void createArrows(double delta) {
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
	
	private List<Connector> findConnectors(double delta) {
		connectorList = new ArrayList<Connector>();
		List<SVGLine> arrowsAndLines = new ArrayList<SVGLine>();
		arrowsAndLines.addAll(arrowList);
		arrowsAndLines.addAll(lineList);
		for (SVGLine line : arrowsAndLines) {
			SVGTextBox headBox = SVGTextBox.getTouchingBox(line.getXY(0), textBoxList, delta);
			if (headBox != null) {
				SVGTextBox tailBox = SVGTextBox.getTouchingBox(line.getXY(1), textBoxList, delta);
				if (tailBox != null) {
					Connector link = new Connector(tailBox, headBox);
					LOG.debug("LINK!! "+link);
					connectorList.add(link);
				}
			}
		}
		return connectorList;
	}

	private List<Connector> findConnectingLines(double delta) {
		List<Connector> lineConnectorList = new ArrayList<Connector>();
		for (SVGLine line : lineList) {
			SVGTextBox headBox = SVGTextBox.getTouchingBox(line.getXY(0), textBoxList, delta);
			if (headBox != null) {
				SVGTextBox tailBox = SVGTextBox.getTouchingBox(line.getXY(1), textBoxList, delta);
				if (tailBox != null) {
					Connector lineConnector = new Connector(tailBox, headBox);
					LOG.debug("LINK!! "+lineConnector);
					lineConnectorList.add(lineConnector);
				}
			}
		}
		return lineConnectorList;
	}



	public SVGG getNewG() {
		return newG;
	}
	
}
