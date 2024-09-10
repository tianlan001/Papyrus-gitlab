/*****************************************************************************
 * Copyright (c) 2014, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.resource.index;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

/**
 * Framework for SAX parsing handlers for indexing UML resources.
 */
public abstract class AbstractUMLIndexHandler extends DefaultHandler {
	protected final URI fileURI;
	private final Map<String, Map<String, String>> packageToProfileApplications = Maps.newHashMap();

	private String umlNamespace;
	private String umlPrefix;
	private String xmiType;
	private String xmiID;

	private Set<String> packageTypes;
	private String eAnnotations;
	private String references;

	private UMLElement top;
	private int ignore;

	protected UMLElement currentPackage;

	private Await await = new Await();

	/**
	 * Initializes me.
	 *
	 * @param fileURI
	 *            the URI of the UML file that I am parsing
	 */
	public AbstractUMLIndexHandler(final URI fileURI) {
		this.fileURI = fileURI;
	}

	public URI getFileURI() {
		return fileURI;
	}

	public Map<String, Map<String, String>> getProfileApplicationsByPackage() {
		return packageToProfileApplications;
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		if (uri.startsWith(XMIResource.XMI_NAMESPACE_PREFIX) || uri.startsWith(XMIResource.XMI_2_4_NAMESPACE_PREFIX)) {
			xmiType = qname(prefix, "type"); //$NON-NLS-1$
			xmiID = qname(prefix, "id"); //$NON-NLS-1$
		} else if (EPackage.Registry.INSTANCE.getEPackage(uri) == UMLPackage.eINSTANCE) {
			umlNamespace = uri;
			umlPrefix = prefix;

			initializeUMLElementNames();
		}
	}

	protected void initializeUMLElementNames() {
		packageTypes = ImmutableSet.of(umlElement("Package"), umlElement("Model"), umlElement("Profile")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		eAnnotations = "eAnnotations"; //$NON-NLS-1$
		references = "references"; //$NON-NLS-1$
	}

	protected final String umlElement(String name) {
		return qname(umlPrefix, name);
	}

	protected final String qname(String prefix, String name) {
		StringBuilder buf = new StringBuilder(prefix.length() + name.length() + 1);
		return buf.append(prefix).append(':').append(name).toString();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (umlNamespace.equals(uri) || (top != null)) {
			// skip over annotations
			if (!ignore(qName, attributes)) {
				push(qName, attributes);
				handleUMLElement(top, attributes);
			}
		}
	}

	protected boolean ignore(String qName, Attributes attributes) {
		boolean result = false;

		if (attributes != null) { // Starting an element
			result = (ignore > 0) || (eAnnotations.equals(qName) && !UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI.equals(attributes.getValue("source"))); //$NON-NLS-1$
			if (result) {
				ignore++;
			}
		} else { // Ending an element
			result = (ignore > 0);
			if (result) {
				ignore--;
			}
		}

		return result;
	}

	protected final void push(String qName, Attributes attributes) {
		top = new UMLElement(qName, attributes);
	}

	protected final UMLElement pop() {
		UMLElement result = top;
		if (top != null) {
			top = top.parent;
		}
		return result;
	}

	protected void handleUMLElement(UMLElement element, Attributes attributes) throws SAXException {
		if (element.isPackage() && (element.getHREF() == null)) {
			// An href is a reference to a package, not a package in our element hierarchy
			currentPackage = element;
			enterPackage(currentPackage, attributes);
		}

		if (!doHandleUMLElement(element, attributes)) {
			if (element.isA(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI)) {
				// It's the applied profile definition annotation
				await.push(references);
			} else if (await.isAwaiting(element)) {
				if (element.isRole(references)) {
					handleAnnotationReferences(element);
				} else {
					handleAwaitedElement(element);
				}

				await.pop();
			}
		}
	}

	protected void enterPackage(UMLElement package_, Attributes attributes) {
		// Pass
	}

	protected void exitPackage(UMLElement package_) {
		// Pass
	}

	protected abstract boolean doHandleUMLElement(UMLElement element, Attributes attributes);

	protected final void await(String elementName) {
		await.push(elementName);
	}

	protected void handleAnnotationReferences(UMLElement references) {
		// Pass
	}

	protected abstract void handleAwaitedElement(UMLElement element);

	protected abstract void summarize();

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!ignore(qName, null)) {
			if (await.stopAt(pop())) {
				await.pop();
			}

			if (top != null) {
				UMLElement newPackage = top.nearestPackage();
				if ((newPackage != currentPackage) && (currentPackage != null)) {
					exitPackage(currentPackage);
				}
				currentPackage = newPackage;
			}

			if (top == null) {
				// We're done with UML content
				summarize();
				throw new OperationCanceledException();
			}
		}
	}

	//
	// Nested types
	//

	protected final class UMLElement {
		final UMLElement parent;

		final String role;
		final String type;
		final String id;
		final String href;

		UMLElement(String qName, Attributes attributes) {
			parent = top;

			String type;
			if (qName.equals(eAnnotations)) {
				// "type" of an annotation is its source
				type = attributes.getValue("source"); //$NON-NLS-1$
			} else {
				type = attributes.getValue(xmiType);
				if (Strings.isNullOrEmpty(type)) {
					type = qName;
				}
			}

			this.role = qName;
			this.type = type;
			this.id = attributes.getValue(xmiID);
			this.href = attributes.getValue("href"); //$NON-NLS-1$
		}

		boolean isPackage() {
			return packageTypes.contains(type);
		}

		boolean isRole(String roleName) {
			return roleName.equals(role);
		}

		boolean isA(String xmiType) {
			return xmiType.equals(type);
		}

		URI getHREF() {
			return Strings.isNullOrEmpty(href) ? null : URI.createURI(href).resolve(fileURI);
		}

		UMLElement nearestPackage() {
			for (UMLElement next = this; next != null; next = next.parent) {
				if (next.isPackage()) {
					return next;
				}
			}
			return null;
		}
	}

	private final class Await {
		final Await parent = await;

		final String awaiting;
		final UMLElement limit;

		Await() {
			this(null);
		}

		private Await(String awaiting) {
			this.awaiting = awaiting;
			this.limit = top;
		}

		boolean isRoot() {
			return parent == null;
		}

		boolean isAwaiting(UMLElement element) {
			return !isRoot() && element.isRole(awaiting);
		}

		boolean stopAt(UMLElement element) {
			return !isRoot() && (limit == element);
		}

		Await push(String elementName) {
			Await result = new Await(elementName);
			await = result;
			return result;
		}

		void pop() {
			if (!isRoot()) {
				await = parent;
			}
		}
	}

	protected static final class URIPair {
		public URI first;
		public URI second;
	}
}
