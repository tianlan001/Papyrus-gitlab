/*******************************************************************************
 * Copyright (c) 2005, 2009 committers of openArchitectureWare and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     committers of openArchitectureWare - initial API and implementation
 *     Christian W. Damus - adapt for UML resources
 *******************************************************************************/

package org.eclipse.papyrus.mwe2.utils.components;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.utils.AbstractEMFWorkflowComponent;
import org.eclipse.emf.mwe.utils.Writer;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

/**
 * An alternative to the core resource {@link Writer} component that provides
 * UML-appropriate behaviour.
 */
public class UMLWriter extends AbstractEMFWorkflowComponent {

	private boolean platformSchemeAware = true;

	private boolean cloneSlotContents = false;

	private boolean ignoreEmptySlot = false;

	private boolean useSameResource = false;

	private boolean xmiIdentifiers = false;

	// Priority according to the order of insertion
	private Map<Matcher, String> xmiIdentifierFilters = Maps.newLinkedHashMap();

	@Override
	public String getComponentName() {
		return UMLWriter.class.getSimpleName();
	}

	@Override
	public String getLogMessage() {
		return "Writing UML model to " + getUri();
	}

	public boolean isPlatformSchemeAware() {
		return platformSchemeAware;
	}

	public void setPlatformSchemeAware(boolean platformSchemeAware) {
		this.platformSchemeAware = platformSchemeAware;
	}

	public boolean isIgnoreEmptySlot() {
		return ignoreEmptySlot;
	}

	public void setIgnoreEmptySlot(boolean ignoreEmptySlot) {
		this.ignoreEmptySlot = ignoreEmptySlot;
	}

	public boolean isCloneSlotContents() {
		return cloneSlotContents;
	}

	public void setCloneSlotContents(final boolean b) {
		this.cloneSlotContents = b;
	}

	public boolean isUseSameResource() {
		return useSameResource;
	}

	public void setUseSameResource(boolean useSameResource) {
		this.useSameResource = useSameResource;
	}

	public boolean isXmiIdentifiers() {
		return xmiIdentifiers;
	}

	public void setXmiIdentifiers(boolean setXMIIdentifiers) {
		this.xmiIdentifiers = setXMIIdentifiers;
	}

	public void addXmiIdentifierFilters(Pair<String, String> filter, Pair<String, String>... more) {
		setXmiIdentifiers(true); // XMI identifiers are implicit if we're filtering them

		xmiIdentifierFilters.put(Pattern.compile(filter.getKey()).matcher(""), filter.getValue());
		for (int i = 0; i < more.length; i++) {
			xmiIdentifierFilters.put(Pattern.compile(more[i].getKey()).matcher(""), more[i].getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void invokeInternal(final WorkflowContext ctx, final ProgressMonitor monitor, final Issues issues) {
		Object slotContent = ctx.get(getModelSlot());
		if (slotContent == null) {
			if (isIgnoreEmptySlot()) {
				issues.addWarning(this, "slot '" + getModelSlot() + "' is empty. Not writing anything.");
			} else {
				issues.addError(this, "slot '" + getModelSlot() + "' is empty.");
			}
			return;
		}
		if (!((slotContent instanceof Collection<?>) || (slotContent instanceof EObject))) {
			issues.addError(this, "slot '" + getModelSlot() + "' neither contains an EList nor an EObject",
					slotContent, null, null);
			return;
		}

		if (slotContent instanceof EObject) {
			final EObject sc = (EObject) slotContent;
			if (isCloneSlotContents()) {
				if (sc.eResource() == null) {
					issues.addWarning(this, "model in slot '" + getModelSlot()
							+ "' is not yet associated with a resource; cloning it is most likely an error!");
				} else {
					final EcoreUtil.Copier copier = new EcoreUtil.Copier();
					final EObject copy = copier.copy(sc);
					copier.copyReferences();
					slotContent = copy;
				}
			} else {
				if ((sc.eResource() != null) && !isUseSameResource()) {
					issues.addWarning(this, "the element in slot '" + getModelSlot()
							+ "' is already contained in a resource and will be taken out of that resource!");
				}
			}
		}

		getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);

		final URI uri = URI.createURI(getUri(), true);
		final Resource r;
		final EObject sc = (slotContent instanceof Collection<?>)
				? (EObject) ((Collection<?>) slotContent).iterator().next()
				: (EObject) slotContent;
		if ((sc.eResource() != null) && isUseSameResource()) {
			// Just save this resource
			r = sc.eResource();
			r.setURI(uri);
		} else {
			r = getResourceSet().createResource(uri);

			if (slotContent instanceof Collection<?>) {
				r.getContents().addAll((Collection<EObject>) slotContent);
			} else {
				r.getContents().add((EObject) slotContent);
			}

			addStereotypes(r);
		}

		write(r, issues);
	}

	protected void addStereotypes(Resource r) {
		for (TreeIterator<EObject> iter = EcoreUtil.getAllContents(ImmutableList.copyOf(r.getContents())); iter.hasNext();) {
			EObject next = iter.next();

			if (next instanceof Element) {
				r.getContents().addAll(((Element) next).getStereotypeApplications());
			} else {
				iter.prune();
			}
		}
	}

	private void write(final Resource r, final Issues issues) {
		try {
			if (isXmiIdentifiers()) {
				if (!(r instanceof XMLResource)) {
					issues.addWarning(this, "resource is not an XML resource; cannot set XMI identifiers", r);
				} else if (!xmiIdentifierFilters.isEmpty()) {
					setXMIIdentifiers((XMLResource) r, xmiIdentifierFilters);
				} else {
					setXMIIdentifiers((XMLResource) r);
				}
			}

			final Map<String, Object> options = new HashMap<String, Object>();

			if (isPlatformSchemeAware()) {
				options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());
			}

			r.save(options);
		} catch (final IOException e) {
			throw new WorkflowInterruptedException("Problems writing xmi file to " + getUri() + " : " + e.getMessage());
		}
	}

	protected void setXMIIdentifiers(XMLResource resource) {
		for (Iterator<InternalEObject> iter = Iterators.filter(resource.getAllContents(), InternalEObject.class); iter.hasNext();) {
			InternalEObject next = iter.next();
			resource.setID(next, UML2Util.getXMIIdentifier(next));
		}
	}

	protected void setXMIIdentifiers(XMLResource resource, Map<Matcher, String> filters) {
		for (Iterator<InternalEObject> iter = Iterators.filter(resource.getAllContents(), InternalEObject.class); iter.hasNext();) {
			InternalEObject next = iter.next();

			String id = filter(resource, filters, next);
			if (id != null) {
				// Can use the filtered ID
				resource.setID(next, id);
			}
		}
	}

	private String filter(XMLResource resource, Map<Matcher, String> filters, InternalEObject object) {
		String id = UML2Util.getXMIIdentifier(object);
		String result = id;

		for (Map.Entry<Matcher, String> filter : filters.entrySet()) {
			Matcher m = filter.getKey();
			m.reset(id);
			if (m.find()) {
				String filtered = m.replaceFirst(filter.getValue());
				EObject collision = resource.getEObject(filtered);
				if ((collision == null) || (collision == object)) {
					result = filtered;
					break;
				}
			}
		}

		if (result.equals(id)) {
			// Check this for a collision, too, because filtering could introduce duplication
			EObject collision = resource.getEObject(result);
			int counter = 0;
			while ((collision != null) && (collision != object)) {
				result = id + '$' + ++counter;
				collision = resource.getEObject(result);
			}
		}

		return result;
	}
}
