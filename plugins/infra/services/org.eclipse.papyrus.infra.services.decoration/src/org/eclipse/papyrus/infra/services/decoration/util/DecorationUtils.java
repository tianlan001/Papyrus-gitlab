/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Amine EL KOUHEN (CEA LIST/LIFL) - Amine.Elkouhen@cea.fr
 *  Ansgar Radermacher (CEA LIST) - ansgar.radermacher@cea.fr
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.decoration.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EReferenceTreeElement;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.decoration.DecorationService;
import org.eclipse.papyrus.infra.services.decoration.IDecorationSpecificFunctions;
import org.eclipse.papyrus.infra.services.decoration.IDecorationSpecificFunctions.MarkChildren;


/**
 * The class DecorationUtils.
 */
public class DecorationUtils {

	/**
	 * Current element.
	 */
	protected Object element;

	/**
	 * List of eObjects to evaluate. Either a single object or a list of objects
	 * in case of a link item
	 */
	protected EList<EObject> eObjects;

	/**
	 * Create instance of class DecorationUtils.
	 *
	 * @param element
	 *            the element, must not be <code>null</code>
	 */
	public DecorationUtils(Object element) {
		if (element == null) {
			throw new IllegalArgumentException("The decorated element shall not be null"); //$NON-NLS-1$
		}

		EObject eObject = EMFHelper.getEObject(element);
		eObjects = new BasicEList<>();

		this.element = element;
		if (eObject != null) {
			eObjects.add(eObject);
		} else if (element instanceof EReferenceTreeElement) {
			// for bug 391676
			for (Object child : ((EReferenceTreeElement) element).getReferedEObjectTE()) {
				if (child instanceof EObject) {
					eObjects.add((EObject) child);
				}
			}
		}
	}


	/**
	 * Instantiates a new decoration utils.
	 *
	 * @param eObject
	 *            the eobject, must not be <code>null</code>
	 */
	public DecorationUtils(EObject eObject) {
		if (eObject == null) {
			throw new IllegalArgumentException("The decorated EObject shall not be null"); //$NON-NLS-1$
		}
		eObjects = new BasicEList<>();
		eObjects.add(eObject);
	}

	/**
	 * Gets the list of eObjects associated with the selected elements.
	 */
	public EList<EObject> getEObjects() {
		return eObjects;
	}

	/**
	 * Gets the decorations.
	 *
	 * @param decorationService
	 *            the decoration service
	 * @return the decorations
	 */
	public Map<String, Decoration> getDecorations(DecorationService decorationService) {
		return decorationService.getDecorations();
	}


	/**
	 * Returns a list of decorations for a given UML element. It is a list, since there might be
	 * more than one decoration (e.g. a validation marker and a tracepoint) for this element.
	 *
	 * If current element is a folder or link-item, decorations from childs are propagated.
	 *
	 * @param decorationService
	 *            the decoration service
	 * @param navigateToParents
	 *            the navigate to parents
	 * @return the decoration severity
	 */
	public EList<IPapyrusDecoration> getDecorations(DecorationService decorationService, boolean navigateToParents) {
		Map<String, Decoration> decorations = getDecorations(decorationService);
		// child decorations are organized in a map indexed by the decoration type
		EList<IPapyrusDecoration> foundDecorations = new BasicEList<>();
		Map<String, EList<IPapyrusDecoration>> childDecorationMap = new HashMap<>();
		if (decorations != null) {
			for (Decoration decoration : decorations.values()) {
				EObject eObjectOfDecorator = decoration.getElement();
				if (getEObjects().contains(eObjectOfDecorator)) {
					// decoration is for this element
					if (decoration.getMessage() == null) {
						decoration.setMessage(""); //$NON-NLS-1$
					}
					foundDecorations.add(decoration);
				}
				// check whether a decoration can be found in one the children
				// (technically, we check the parents of a decoration)
				IDecorationSpecificFunctions decoUtil = DecorationSpecificFunctions.getDecorationInterface(decoration.getType());

				if (navigateToParents && (decoUtil != null) && decoUtil.supportsMarkerPropagation() != MarkChildren.NO) {
					MarkChildren markChildren = decoUtil.supportsMarkerPropagation();
					boolean first = true;

					eObjectOfDecorator = eObjectOfDecorator.eContainer();
					while (eObjectOfDecorator != null) {
						if (getEObjects().contains(eObjectOfDecorator)) {
							String type = decoration.getType();
							EList<IPapyrusDecoration> childDecorations = childDecorationMap.get(type);
							if (childDecorations == null) {
								// does not exist yet => create
								childDecorations = new BasicEList<>();
								childDecorationMap.put(type, childDecorations);
							}
							childDecorations.add(decoration);
						}
						// navigate to parents, since parent folder is concerned by error as well
						eObjectOfDecorator = eObjectOfDecorator.eContainer();
						if (markChildren != MarkChildren.ALL) {
							if (!first) {
								break;
							}
						}
						first = false;
					}
				}
			}
		}

		// now process map of children
		for (String type : childDecorationMap.keySet()) {
			EList<IPapyrusDecoration> childDecorations = childDecorationMap.get(type);
			if (childDecorations != null) {
				IDecorationSpecificFunctions decoUtil = DecorationSpecificFunctions.getDecorationInterface(type);
				IPapyrusDecoration propagatedDecoration = decoUtil.markerPropagation(childDecorations);
				if (propagatedDecoration != null) {
					foundDecorations.add(propagatedDecoration);
				}
			}
		}

		return foundDecorations;
	}

	/**
	 * Gets the decoration message.
	 * Caveat: Decoration.getMessageFromDecorations is used instead of this operation
	 *
	 * @param decorationService
	 *            the decoration service
	 * @return the decoration message
	 *
	 *         @deprecated, use {@link Decoration#getMessageFromDecorations(DecorationService, Object)} instead of this method
	 */
	@Deprecated
	public String getDecorationMessage(DecorationService decorationService) {

		Map<String, Decoration> decorations = getDecorations(decorationService);
		if (decorations != null) {
			String message = ""; //$NON-NLS-1$
			for (Decoration decoration : decorations.values()) {
				EObject eObjectOfDecorator = decoration.getElement();
				if (getEObjects().contains(eObjectOfDecorator)) {
					if (message.length() > 0) {
						message += "\n"; //$NON-NLS-1$
					}
					message += "- " + StringUtils.stringWrap(decoration.getMessage(), 100, "\n "); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			return (message.length() > 0) ? message : null;
		}
		return null;
	}
}
