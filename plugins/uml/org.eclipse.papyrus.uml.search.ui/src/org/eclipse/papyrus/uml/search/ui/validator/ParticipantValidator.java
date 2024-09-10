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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 *
 * A generic implementation of participant validator that works on EMF basis
 *
 */
public class ParticipantValidator implements IParticipantValidator {

	private static ParticipantValidator instance = null;

	private ParticipantValidator() {
		super();
	}

	public static final ParticipantValidator getInstance() {

		if (ParticipantValidator.instance == null) {
			synchronized (ParticipantValidator.class) {
				if (ParticipantValidator.instance == null) {
					ParticipantValidator.instance = new ParticipantValidator();
				}
			}
		}
		return ParticipantValidator.instance;
	}

	public Collection<EObject> getParticipants(EObject root, Object[] participantsTypes) {

		List<Object> participantsTypesList = Arrays.asList(participantsTypes);
		List<EObject> results = new ArrayList<EObject>();



		// ... and all its content
		TreeIterator<EObject> it = root.eAllContents();
		while (it.hasNext()) {
			EObject modelElement = it.next();

			// Check that metaclass of this element is a supported metaclass
			@SuppressWarnings("unused") EClass e = modelElement.eClass();
			if (participantsTypesList.contains(modelElement.eClass())) {
				results.add(modelElement);
			}
		}

		return results;
	}


	public Collection<EObject> getParticipantsStereotype(EList<EObject> contents, Object[] participantsTypes) {

		List<Object> participantsTypesList = Arrays.asList(participantsTypes);
		List<EObject> results = new ArrayList<EObject>();

		// ... and all its content

		for (EObject content : contents) {
			if (!(content instanceof Model)) {
				Element umlElement = UMLUtil.getBaseElement(content);

				if (umlElement instanceof Element) {
					for (Object participantStereotype : participantsTypes) {
						if (participantStereotype instanceof Stereotype) {
							if (StereotypeUtil.isApplied((Element) umlElement, ((Stereotype) participantStereotype).getQualifiedName())) {
								results.add(umlElement);
								break;
							}
						}
					}
				}
			}

			/**
			 * Keep old version for performance comparison
			 */
			//			TreeIterator<EObject> it = root.eAllContents();
			//			while (it.hasNext()) {
			//				EObject modelElement = it.next();
			//				if (modelElement instanceof Element) {
			//					for (Object participantStereotype : participantsTypes) {
			//						if (participantStereotype instanceof Stereotype) {
			//							if (StereotypeUtil.isApplied((Element) modelElement, ((Stereotype) participantStereotype).getQualifiedName())) {
			//								results.add(modelElement);
			//								break;
			//							}
			//						}
			//					}
			//
			//					/**
			//					 * EcoreUtil.equals is slow which has a big impact when searching in models of more than 100 elements.
			//					 * The temporary solution may raise a bug in the future.
			//					 */
			//					//if (EcoreUtil.equals(appliedStereotype, (EObject) participantStereotype)) {
			//				}
			//			}

		}
		return results;
	}
}
