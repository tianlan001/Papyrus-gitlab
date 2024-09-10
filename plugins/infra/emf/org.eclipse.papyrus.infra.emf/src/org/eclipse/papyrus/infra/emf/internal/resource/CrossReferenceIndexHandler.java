/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.emf.internal.resource;

import static org.eclipse.papyrus.infra.tools.util.TypeUtils.as;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

/**
 * XML parsing handler for extraction of resource cross-reference topology.
 */
public class CrossReferenceIndexHandler extends DefaultHandler {
	private final URI fileURI;

	private final boolean annotationOnly;

	private Set<String> crossReferences = Sets.newHashSet();
	private XMIElement shard;
	private Set<String> subunits = Sets.newHashSet();

	// The (optional) parent references in the annotation
	private Set<String> parents = Sets.newHashSet();

	private BiMap<String, String> namespacePrefixes = HashBiMap.create();

	private String xmiContainerQName;
	private String xmiTypeQName;
	private String eAnnotationSourceName;
	private String eAnnotationReferencesName;

	private XMIElement top;

	/**
	 * Initializes me.
	 *
	 * @param fileURI
	 *            the URI of the XMI file that I am parsing
	 */
	public CrossReferenceIndexHandler(final URI fileURI) {
		this(fileURI, false);
	}

	/**
	 * Initializes me.
	 *
	 * @param fileURI
	 *            the URI of the XMI file that I am parsing
	 * @param annotationOnly
	 *            whether we stop parsing as soon as the shard annotation has been processed
	 */
	public CrossReferenceIndexHandler(URI fileURI, boolean annotationOnly) {
		this.fileURI = fileURI;
		this.annotationOnly = annotationOnly;
	}

	public URI getFileURI() {
		return fileURI;
	}

	public Set<String> getCrossReferences() {
		return crossReferences;
	}

	public boolean isShard() {
		return shard != null;
	}

	public Set<String> getSubunits() {
		return subunits;
	}

	public Set<String> getParents() {
		return parents;
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		namespacePrefixes.put(prefix, uri);

		if ("xmi".equals(prefix)) { //$NON-NLS-1$
			xmiTypeQName = qname(prefix, "type"); //$NON-NLS-1$
			xmiContainerQName = qname(prefix, "XMI"); //$NON-NLS-1$
			eAnnotationSourceName = "source"; //$NON-NLS-1$
			eAnnotationReferencesName = "references"; //$NON-NLS-1$
		}
	}

	protected final String qname(String prefix, String name) {
		StringBuilder buf = new StringBuilder(prefix.length() + name.length() + 1);
		return buf.append(prefix).append(':').append(name).toString();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		push(qName, attributes);

		handleXMIElement(top, attributes);
	}

	protected final void push(String qName, Attributes attributes) {
		top = new XMIElement(qName, attributes);
	}

	protected final XMIElement pop() {
		XMIElement result = top;
		if (top != null) {
			top = top.parent;
		}

		return result;
	}

	protected void handleXMIElement(XMIElement element, Attributes attributes) throws SAXException {
		if (element.getHREF() != null) {
			URI xref = element.getHREF().trimFragment();

			// Don't index internal references
			if (!xref.equals(fileURI)) {
				if (element.isContainment()) {
					// Cross-resource containment is a sub-unit relationship
					subunits.add(xref.toString());
				} else if (isShard() && (element.parent == shard) && element.isRole(eAnnotationReferencesName)) {
					// Handle shard parent resource reference. This is
					// *not* a regular cross-resource reference
					parents.add(xref.toString());
				} else {
					// Regular cross-resource reference
					crossReferences.add(xref.toString());
				}
			}
		} else if (element.isAnnotation()) {
			String source = attributes.getValue(eAnnotationSourceName);
			if (AbstractCrossReferenceIndex.SHARD_ANNOTATION_SOURCE.equals(source)) {
				// This is a shard
				shard = element;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		XMIElement ended = pop();

		if (annotationOnly && isShard() && (ended == shard)) {
			// We have finished with shard linkage
			throw new StopParsing();
		}
	}

	//
	// Nested types
	//

	protected final class XMIElement {
		final XMIElement parent;

		final String type;
		final String role;
		final String href;

		private EClass eclass;

		XMIElement(String qName, Attributes attributes) {
			parent = top;

			if ((parent == null) || parent.isXMIContainer()) {
				// It's actually a type name
				this.role = null;
				this.type = qName;
			} else {
				this.role = qName;
				this.type = attributes.getValue(xmiTypeQName);
			}

			this.href = attributes.getValue("href"); //$NON-NLS-1$
		}

		/** Am I the {@code xmi:XMI} container? */
		boolean isXMIContainer() {
			return (role == null) && ((type == null) || type.equals(xmiContainerQName));
		}

		boolean isRoot() {
			return (parent == null) || parent.isXMIContainer();
		}

		boolean isRole(String roleName) {
			return roleName.equals(role);
		}

		URI getHREF() {
			return Strings.isNullOrEmpty(href) ? null : URI.createURI(href).resolve(fileURI);
		}

		boolean isAnnotation() {
			return getEClass() == EcorePackage.Literals.EANNOTATION;
		}

		boolean isContainment() {
			boolean result = false;

			if (!isRoot()) {
				EStructuralFeature feature = parent.getFeature(this.role);
				result = (feature instanceof EReference)
						&& ((EReference) feature).isContainment();
			}

			return result;
		}

		EStructuralFeature getFeature(String role) {
			EClass eclass = getEClass();

			return (eclass == null) ? null : eclass.getEStructuralFeature(role);
		}

		EClass getEClass() {
			if (eclass == null) {
				if (type != null) {
					Iterator<String> parts = Splitter.on(':').split(type).iterator();
					String ns = namespacePrefixes.get(parts.next());
					if (ns != null) {
						EPackage epackage = EPackage.Registry.INSTANCE.getEPackage(ns);
						if (epackage != null) {
							eclass = as(epackage.getEClassifier(parts.next()), EClass.class);
						}
					}
				} else if (parent != null) {
					EClass parentEClass = parent.getEClass();
					if (parentEClass != null) {
						EReference ref = as(parentEClass.getEStructuralFeature(role), EReference.class);
						if (ref != null) {
							eclass = ref.getEReferenceType();
						}
					}
				}
			}

			return eclass;
		}
	}
}
