/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Fedorov (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - members' visibility/access
 *                                 [254532] further distinguish extensions by id, if present 
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.papyrus.gmf.internal.common.Activator;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Please do not blame anyone for the implementation, it will be changed soon
 * FIXME rewrite with SAX/DOM and separate post-processing formatter
 */
public class PluginXMLTextMerger {
	
	private static final String ATTR_POINT = "point"; //$NON-NLS-1$

	private static final String ELEM_EXTENSION = "extension"; //$NON-NLS-1$
	private static final String ELEM_EXTENSION_START = "<" + ELEM_EXTENSION; //$NON-NLS-1$
	private static final String ELEM_EXTENSION_END = "</" + ELEM_EXTENSION + ">"; //$NON-NLS-1$ //$NON-NLS-2$

	private static final String ELEM_PLUGIN = "plugin"; //$NON-NLS-1$
	private static final String ELEM_PLUGIN_END = "</" + ELEM_PLUGIN + ">"; //$NON-NLS-1$ //$NON-NLS-2$

	private static final String COMMENT_START = "<!--"; //$NON-NLS-1$
	private static final String COMMENT_END = "-->"; //$NON-NLS-1$
	
//	private final String myPITarget;
//	private final String myPIAttrName;
//	private final String myPIAttrValue;
	private final String myPITag;

	private SAXParserFactory factory;

	public PluginXMLTextMerger(String piTarget, String piAttrName, String piAttrValue) {
//		this.myPITarget = piTarget;
//		this.myPIAttrName = piAttrName;
//		myPIAttrValue = piAttrValue;
		this.myPITag = MessageFormat.format("<?{0} {1}=\"{2}\"?>", piTarget, piAttrName, piAttrValue); //$NON-NLS-1$
	}

	public boolean isRecognizedDocument(String xml) {
		try {
			final ParsedPluginXML doc = parseDocument(xml);
			return doc != null && doc.getExtensionsStart() > 0 & doc.getExtensionsEnd() >= doc.getExtensionsStart();
		} catch (Exception ex) {
			return false;
		}
	}

	public String process(String oldXML, String newXML) {
		ParsedPluginXML newDoc;
		try {
			newDoc = parseDocument(newXML);
		} catch (Exception e) {
			logException("Generated plugin.xml is invalid. Existing plugin.xml will be kept", e); //$NON-NLS-1$
			return oldXML;
		}
		ParsedPluginXML oldDoc;
		try {
			oldDoc = parseDocument(oldXML);
		} catch (Exception e) {
			logException("Existing plugin.xml is invalid and will be replaced with generated one", e); //$NON-NLS-1$
			return newXML;
		}
		String result = mergeDocuments(oldDoc, newDoc);
		try {
			parseDocument(result);
		} catch (Exception e) {
			logException("Merged plugin.xml is invalid and will be replaced with generated one", e); //$NON-NLS-1$
			return newXML;
		}
		return result;
	}

	private ParsedPluginXML parseDocument(String xml) throws SAXException, ParserConfigurationException, IOException {
		ParsedPluginXML pluginXML = new ParsedPluginXML(xml, myPITag);
		InputSource is = new InputSource(new StringReader(xml));
		getParserFactory().newSAXParser().parse(is, pluginXML);
        return pluginXML;
	}

	private SAXParserFactory getParserFactory() {
		if (factory == null) {
			factory = SAXParserFactory.newInstance();
		}
		return factory;
	}

	private String mergeDocuments(ParsedPluginXML oldDoc, ParsedPluginXML newDoc) {
		StringBuilder result = new StringBuilder();
		int currentPosition = oldDoc.getExtensionsStart() - 1;
		final String oldXML = oldDoc.getXML();
		result.append(oldXML.substring(0, currentPosition));
		final int length = oldXML.length();
		while (currentPosition < length) {
			int key = currentPosition;
			ExtensionDescriptor oldED = oldDoc.getExtensionByStart(key);
			if (oldED == null) {
				result.append(oldXML.charAt(currentPosition));
				currentPosition++;
			} else {
				List<ExtensionDescriptor> newEDs = newDoc.getExtensionsByPoint(oldED.pointName);
				if (oldED.generated) {
					// if there's only one new descriptor, replace
					// if there's more, need to take extension's id into account,
					// but remove oldED anyway, regardless whether there was matching newED or not
					boolean foundMatched = false;
					for (ExtensionDescriptor ed : newEDs) {
						if (oldED.identityMatches(ed)) {
							result.append(ed.getText());
							ed.remove();
							foundMatched = true;
							break;
						}
					}
					if (!foundMatched && newEDs.size() > 0) {
						// copy one of new elements here, in effort to keep old order
						ExtensionDescriptor newED = newEDs.get(0);
						result.append(newED.getText());
						newED.remove();
					}
					currentPosition = oldED.endLine;
					oldED.remove();
				} else {
					//keep
					result.append(oldED.getText());
					currentPosition += oldED.getTextLength();
					oldED.remove();
					for (ExtensionDescriptor newED : newEDs) {
						if (newED.identityMatches(oldED)) {
							newED.remove();
						}
					}
				}
			}
			if (!oldDoc.hasMoreExtensions()  && newDoc.hasMoreExtensions()) {
				boolean sameStartEnd = oldDoc.getExtensionsStart() == oldDoc.getExtensionsEnd();
				boolean afterStart = currentPosition >= oldDoc.getExtensionsStart(); 
				if (afterStart && (sameStartEnd || currentPosition <= oldDoc.getExtensionsEnd())) {
					for (ExtensionDescriptor newED : newDoc.getExtensions()) {
						result.append(newED.getText());
						newED.remove();
						result.append(getPlatformNewLine());
					}
				}
			}
		}
		
		return result.toString();
	}

	protected void logException(String message, Exception e) {
		Activator.logError(message, e);
	}
	
	private static String getPlatformNewLine() {
		return System.getProperties().getProperty("line.separator"); //$NON-NLS-1$
	}

	private static class ParsedPluginXML extends DefaultHandler {
		private final String myXML;
		private String myGeneratedToken;
		private int myPluginEnd;
		
		private final Map<String, List<ExtensionDescriptor>> myPoint2ExtensionsMap;
		private final SortedMap<Integer, ExtensionDescriptor> myStart2ExtensionMap;
		private Iterator<ExtensionDescriptor> myIterator;
		private final int myCachedExtStart;
		
		ParsedPluginXML(String xml, String generatedToken) {
			this.myXML = xml;
			this.myGeneratedToken = generatedToken;
			this.myPoint2ExtensionsMap = new HashMap<String, List<ExtensionDescriptor>>();
			this.myStart2ExtensionMap = new TreeMap<Integer, ExtensionDescriptor>();
			parse(xml);
			myCachedExtStart  = myStart2ExtensionMap.size() > 0 ? myStart2ExtensionMap.firstKey() : myPluginEnd;
		}

		private void parse(String xml) {
			int currentIndex = 0;
			final int length = xml.length();
			this.myPluginEnd = xml.lastIndexOf(ELEM_PLUGIN_END);
			while (currentIndex < length) {
				int extensionStart = getStartIndex(xml, ELEM_EXTENSION_START, currentIndex);
				if (extensionStart == length - 1) {
					break;
				}
				if (isInsideComment(xml, extensionStart)) {
					currentIndex = extensionStart + ELEM_EXTENSION_START.length();
					continue;
				}
				if (!Character.isWhitespace(xml.charAt(extensionStart + ELEM_EXTENSION_START.length()))) {
					// e.g. "<extension-point"
					currentIndex = extensionStart + ELEM_EXTENSION_START.length();
					continue;
				}
				currentIndex = processExtensonBlock(xml, extensionStart);
			}
		}
		
		private int processExtensonBlock(String xml, int fromIndex) {
			int extensionStart = fromIndex;
			int extensionEnd = getStartIndex(xml, ELEM_EXTENSION_END, fromIndex) + ELEM_EXTENSION_END.length();
			while (isInsideComment(xml, extensionEnd)) {
				extensionEnd = getStartIndex(xml, ELEM_EXTENSION_END, extensionEnd) + ELEM_EXTENSION_END.length();
			}
			// look ahead 2 (\n\r and \r\n) chars at most, if they are newline, include them into extension's range
			// this helps to keep user's formatting
			for (int i = 0; i < 2 && extensionEnd < xml.length(); i++) {
				if (xml.charAt(extensionEnd) == '\n' || xml.charAt(extensionEnd) == '\r') {
					// only \r\n or \n\r or single \n constitute a newline, 
					// need to be careful not to treat double \n as a single newline 
					if (i == 0 || xml.charAt(extensionEnd-1) != xml.charAt(extensionEnd)) {
						extensionEnd++;
					}
				} else {
					break;
				}
			}
			boolean isGenerated = isGenerated(xml, extensionStart, extensionEnd);
			ExtensionDescriptor ed = new ExtensionDescriptor(this, extensionStart, extensionEnd, isGenerated);
			myStart2ExtensionMap.put(ed.startLine, ed);
			return extensionEnd;
		}
		
		private boolean isGenerated(String xml, int extensionStart, int extensionEnd) {
			int genStart = getStartIndex(xml, myGeneratedToken, extensionStart);
			while (genStart < extensionEnd) {
				if (!isInsideComment(xml, genStart)) {
					return true;
				}
				genStart = getStartIndex(xml, myGeneratedToken, genStart + myGeneratedToken.length());
			}
			return false;
		}

		private int getStartIndex(String xml, String token, int fromIndex) {
			int commentStart = xml.indexOf(token, fromIndex);
			return (commentStart < 0) ? xml.length()-1 : commentStart;
		}
		
		private boolean isInsideComment(String xml, int fromIndex) {
			int lastOpened = xml.lastIndexOf(COMMENT_START, fromIndex);
			if (lastOpened < 0) {
				return false;
			}
			int lastClosed = xml.lastIndexOf(COMMENT_END, fromIndex);
			if (lastClosed > lastOpened && lastClosed < fromIndex) {
				return false;
			}
			return true;
		}

		// modifiable copy of the list
		List<ExtensionDescriptor> getExtensionsByPoint(String point) {
			final List<ExtensionDescriptor> list = myPoint2ExtensionsMap.get(point);
			return list == null ? Collections.<ExtensionDescriptor>emptyList() : new ArrayList<ExtensionDescriptor>(list);
		}
		
		ExtensionDescriptor getExtensionByStart(int start) {
			return myStart2ExtensionMap.get(start);
		}

		void removeExtension(ExtensionDescriptor ed) {
			myStart2ExtensionMap.remove(ed.startLine);
			List<ExtensionDescriptor> list = myPoint2ExtensionsMap.get(ed.pointName);
			if (list != null) {
				list.remove(ed);
				if (list.size() == 0) {
					myPoint2ExtensionsMap.remove(ed.pointName);
				}
			}
		}

		boolean hasMoreExtensions() {
			return !myStart2ExtensionMap.isEmpty();
		}

		List<ExtensionDescriptor> getExtensions() {
			return new ArrayList<ExtensionDescriptor>(myStart2ExtensionMap.values());
		}
		
		int getExtensionsStart() {
			if (myStart2ExtensionMap.size() > 0) {
				return myStart2ExtensionMap.firstKey();
			}
			return myCachedExtStart;
		}

		int getExtensionsEnd() {
			if (myStart2ExtensionMap.size() > 0) {
				ExtensionDescriptor ed = myStart2ExtensionMap.get(myStart2ExtensionMap.lastKey());
				return ed.endLine;
			}
			return myPluginEnd;
		}
		
		String getXML() {
			return this.myXML;
		}

		@Override
		public void startDocument() throws SAXException {
			myIterator = myStart2ExtensionMap.values().iterator();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (ELEM_EXTENSION.equals(qName)) {
				String pointName = attributes.getValue(ATTR_POINT);
				String identity = attributes.getValue("id"); //$NON-NLS-1$
				if (pointName != null) {
					if (myIterator != null && myIterator.hasNext()) {
						ExtensionDescriptor ed = myIterator.next();
						ed.pointName = pointName;
						ed.identity = identity;
						List<ExtensionDescriptor> list = myPoint2ExtensionsMap.get(ed.pointName);
						if (list == null) {
							list = new LinkedList<ExtensionDescriptor>();
							myPoint2ExtensionsMap.put(ed.pointName, list);
						}
						list.add(ed);
					}
				}
			}
		}

		@Override
		public void endDocument() throws SAXException {
			myIterator = null;
		}

	}
	
	private static class ExtensionDescriptor {
		private final ParsedPluginXML parsedDoc;
		String pointName;
		String identity;
		final boolean generated;
		private final int startLine;
		private final int endLine;
		
		ExtensionDescriptor(ParsedPluginXML parsedPluginXml, int start, int end, boolean isGenerated) {
			parsedDoc = parsedPluginXml;
			startLine = start;
			endLine = end;
			generated = isGenerated;
		}

		String getText() {
			return parsedDoc.getXML().substring(startLine, endLine);
		}
		int getTextLength() {
			return endLine - startLine;
		}
		void remove() {
			parsedDoc.removeExtension(this);
		}
		boolean identityMatches(ExtensionDescriptor other) {
			return identity == null ? other.identity == null : identity.equals(other.identity);
		}
	}
}
