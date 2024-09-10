/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.commands.StereotypePropertyReferenceEdgeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * Command to destroy reference from Stereotype property reference link.
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceDestroyCommand extends EditElementCommand {

	/** The view which is destroy. */
	private final View view;

	/** The stereotypeToSet attribute of the link. */
	private String stereotypeToSet;

	/** The feature attribute of the link. */
	private String featureToSet;

	/** The referencedObject. */
	private final EObject referencedObject;

	/** The container of the feature. */
	private final EObject container;

	/**
	 * Constructor.
	 *
	 * @param request
	 *            The DestroyReferenceRequest request.
	 */
	public StereotypePropertyReferenceDestroyCommand(final DestroyReferenceRequest request) {
		super(request.getLabel(), null, request);
		referencedObject = request.getReferencedObject();
		container = request.getContainer();

		Object value = request.getParameters().get(RequestParameterConstants.AFFECTED_VIEW);
		view = value instanceof View ? (View) value : null;

		if (null != view) {
			EAnnotation eAnnotation = view.getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			if (null != eAnnotation) {
				featureToSet = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
				stereotypeToSet = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		boolean canExecute = false;
		if (null != view && null != featureToSet && null != stereotypeToSet) {
			canExecute = referencedObject instanceof Element && container instanceof Element;
		}
		return canExecute;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		return dereferenceTarget();
	}

	/**
	 * Dereference the target in the source property.
	 * 
	 * @return The command result.
	 * @throws ExecutionException
	 */
	protected CommandResult dereferenceTarget() throws ExecutionException {
		try {
			Stereotype stereotype = getContainer().getApplicableStereotype(stereotypeToSet);
			Stereotype appliedSubstereotype = UMLUtil.getAppliedSubstereotype(getContainer(), stereotype);

			if (null != stereotype) {
				Property attribute = stereotype.getAttribute(featureToSet, null);
				if (1 == attribute.getUpper()) {
					// Single reference case
					getContainer().setValue(appliedSubstereotype, featureToSet, null);
					cleanOtherEdge(null);
				} else {
					Object list = getContainer().getValue(appliedSubstereotype, featureToSet);
					Type targetType = attribute.getType();
					// Gets
					Object value = null;
					if (targetType instanceof Stereotype) {
						value = ElementUtil.getStereotypeApplication((Element) getReferencedObject(), (Stereotype) targetType);
					} else {
						value = getReferencedObject();
					}
					if (list instanceof List) {
						((List) list).remove(value);
					}
					cleanOtherEdge(list);
				}
			}

		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}

		return CommandResult.newOKCommandResult(getContainer());
	}

	/**
	 * @throws ExecutionException
	 */
	protected void cleanOtherEdge(final Object value) throws ExecutionException {
		ICommand cleanStereotypePropertyReferenceCommand = StereotypePropertyReferenceEdgeUtil.getCleanStereotypePropertyReferenceCommand(container, value, stereotypeToSet, featureToSet, getEditingDomain(), null);
		if (null != cleanStereotypePropertyReferenceCommand && cleanStereotypePropertyReferenceCommand.canExecute()) {
			cleanStereotypePropertyReferenceCommand.execute(null, null);
		}
	}

	/**
	 * @return the referencedObject
	 */
	public Element getReferencedObject() {
		return (Element) referencedObject;
	}

	/**
	 * @return the container
	 */
	public Element getContainer() {
		return (Element) container;
	}


}
