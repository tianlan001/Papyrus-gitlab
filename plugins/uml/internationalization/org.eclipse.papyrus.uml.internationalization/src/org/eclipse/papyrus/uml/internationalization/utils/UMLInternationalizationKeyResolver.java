/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.internationalization.InternationalizationEntry;
import org.eclipse.papyrus.infra.internationalization.utils.InternationalizationKeyResolver;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLQualifiedNameUtils;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionUtils;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * The internationalization key resolver for the UML elements.
 */
public class UMLInternationalizationKeyResolver extends InternationalizationKeyResolver {

	/**
	 * The qualified name separator replacement into properties file.
	 */
	private static final String QUALIFIED_NAME_SEPARATOR_REPLACEMENT = "__"; //$NON-NLS-1$

	/**
	 * The map of NamedElement by qualified names.
	 */
	private Map<String, NamedElement> elementsForQN;

	/**
	 * Constructor.
	 */
	public UMLInternationalizationKeyResolver() {
		elementsForQN = new HashMap<String, NamedElement>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.utils.InternationalizationKeyResolver#createInternationalizationEntryByKey(java.lang.String,
	 *      org.eclipse.papyrus.infra.core.resource.ModelSet,
	 *      org.eclipse.emf.common.util.URI)
	 */
	@Override
	public InternationalizationEntry createInternationalizationEntryByKey(final String key,
			final ResourceSet resourceSet, final URI uri) {
		InternationalizationEntry entry = super.createInternationalizationEntryByKey(key, resourceSet, uri);

		Object entryKey = entry.getKey();
		if (entryKey instanceof String) {
			if (((String) entryKey).startsWith(LABEL_PREFIX)) {
				entryKey = ((String) entryKey).substring(LABEL_PREFIX.length());
			}
			final String qualifiedName = ((String) entryKey).replaceAll(QUALIFIED_NAME_SEPARATOR_REPLACEMENT,
					NamedElementUtil.QUALIFIED_NAME_SEPARATOR); // $NON-NLS-1$

			// Compute all the qualified names of the UML resource if not already done
			if (elementsForQN.isEmpty()) {
				final Resource umlResource = resourceSet
						.getResource(uri.trimFileExtension().appendFileExtension(UMLResource.FILE_EXTENSION), true);
				if (null != umlResource && null != umlResource.getContents() && !umlResource.getContents().isEmpty()) {
					computeElementsNames(umlResource);
				}
			}

			final NamedElement namedElement = elementsForQN.get(qualifiedName);
			if (null != namedElement) {
				entry.setKey(namedElement);
			}
		}

		return entry;
	}

	/**
	 * This allows to compute all NamedElement in a UML Resource.
	 * 
	 * @param resource
	 *            The UML Resource.
	 */
	private void computeElementsNames(final Resource resource) {
		final TreeIterator<EObject> contents = EcoreUtil.getAllProperContents(resource, false);
		while (contents.hasNext()) {
			final EObject eObject = contents.next();

			if (eObject instanceof NamedElement) {
				elementsForQN.put(((NamedElement) eObject).getQualifiedName(), (NamedElement) eObject);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.utils.InternationalizationKeyResolver#getKey(org.eclipse.papyrus.infra.internationalization.InternationalizationEntry)
	 */
	@Override
	public String getKey(final InternationalizationEntry entry) {
		final StringBuilder stringBuilder = new StringBuilder();
		if (entry.getKey() instanceof NamedElement) {

			if (!(entry.getKey() instanceof Stereotype)) {
				stringBuilder.append(LABEL_PREFIX);
			}

			final Resource umlResource = ((NamedElement) entry.getKey()).eResource();

			Namespace superElementInSameResource = null;

			if (null != umlResource) {
				for (final EObject eObjectContent : umlResource.getContents()) {
					if (eObjectContent instanceof Namespace
							&& EcoreUtil.isAncestor(eObjectContent, (NamedElement) entry.getKey())) {
						superElementInSameResource = (Namespace) eObjectContent;
					}
				}
			}

			// Use the shortest qualified name for the control mode if needed
			boolean shortestQualifiedNameUsed = false;

			if (null != superElementInSameResource && null != superElementInSameResource.eContainer()) {
				List<String> shortestQualifiedNames = NameResolutionUtils.getShortestQualifiedNames(
						(NamedElement) entry.getKey(), (Namespace) superElementInSameResource.eContainer(), false);
				if (!shortestQualifiedNames.isEmpty()) {
					stringBuilder.append(shortestQualifiedNames.get(0).replaceAll(
							NamedElementUtil.QUALIFIED_NAME_SEPARATOR, QUALIFIED_NAME_SEPARATOR_REPLACEMENT));
					shortestQualifiedNameUsed = true;
				}
			}
			if (!shortestQualifiedNameUsed) {
				stringBuilder.append(UMLQualifiedNameUtils.getQualifiedName((NamedElement) entry.getKey(), QUALIFIED_NAME_SEPARATOR_REPLACEMENT));
			}
		}
		return 0 != stringBuilder.length() ? stringBuilder.toString() : super.getKey(entry);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.internationalization.utils.InternationalizationKeyResolver#dispose()
	 */
	@Override
	public void dispose() {
		elementsForQN.clear();
		super.dispose();
	}

}
