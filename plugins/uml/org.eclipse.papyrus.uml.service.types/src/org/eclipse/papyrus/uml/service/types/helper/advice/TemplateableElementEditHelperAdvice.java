/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.RedefinableTemplateSignature;
import org.eclipse.uml2.uml.TemplateableElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.collect.Iterables;

/**
 * Advice for the editing of {@link TemplateableElement}s.
 */
public class TemplateableElementEditHelperAdvice extends AbstractEditHelperAdvice {

	public TemplateableElementEditHelperAdvice() {
		super();
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		boolean result = true;

		// Only Classifiers can own RedefinableTemplateSignatures because RedefinableTemplateSignature::classifier
		// is a required subset of TemplateSignature::template, which subsets Element::owner
		if (request instanceof CreateElementRequest) {
			result = approveCreateRequest((CreateElementRequest) request);
		} else if (request instanceof MoveRequest) {
			result = approveMoveRequest((MoveRequest) request);
		} else if (request instanceof SetRequest) {
			result = approveSetRequest((SetRequest) request);
		}

		return result && basicApproveRequest(request);
	}

	protected final boolean basicApproveRequest(IEditCommandRequest request) {
		return super.approveRequest(request);
	}

	/**
	 * Deny {@code request} to create a {@link RedefinableTemplateSignature} in a non-{@link Classifier}.
	 */
	protected boolean approveCreateRequest(CreateElementRequest request) {
		boolean result = true;

		if (request.getElementType().getEClass() == UMLPackage.Literals.REDEFINABLE_TEMPLATE_SIGNATURE) {
			EObject container = request.getContainer();

			// Can't deny if the container isn't yet created
			result = (container == null) || (UMLPackage.Literals.CLASSIFIER.isInstance(container));
		}

		return result;
	}

	/**
	 * Deny {@code request} to move a {@link RedefinableTemplateSignature} into a non-{@link Classifier}.
	 */
	protected boolean approveMoveRequest(MoveRequest request) {
		boolean result = true;

		if (!(request.getTargetContainer() instanceof Classifier)) {
			for (EObject next : Iterables.filter(request.getElementsToMove().keySet(), EObject.class)) {
				if (UMLPackage.Literals.REDEFINABLE_TEMPLATE_SIGNATURE.isInstance(next)) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Deny {@code request} to set a {@link RedefinableTemplateSignature} as the {@link TemplateableElement#getOwnedTemplateSignature() ownedTempalteSignature}
	 * of a non-{@link Classifier}.
	 */
	protected boolean approveSetRequest(SetRequest request) {
		boolean result = true;

		if (!(request.getElementToEdit() instanceof Classifier) && (request.getFeature() == UMLPackage.Literals.TEMPLATEABLE_ELEMENT__OWNED_TEMPLATE_SIGNATURE)) {
			result = (request.getValue() == null) || !UMLPackage.Literals.REDEFINABLE_TEMPLATE_SIGNATURE.isInstance(request.getValue());
		}

		return result;
	}
}
