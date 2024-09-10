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
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.commands.StereotypePropertyReferenceEdgeUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * The command to reorient stereotype property reference edge.
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceReorientCommand extends EditElementCommand {

	/** The reorient direction. */
	private final int reorientDirection;

	/** The reference owner. */
	private final EObject referenceOwner;

	/** The old end. */
	private final EObject oldEnd;

	/** The new End. */
	private final EObject newEnd;

	/** the edge view. */
	private final View view;

	/** The stereotype qualify name of the stereotype to set. */
	private String sourceStereotypeQualifiedName;

	/** The feature to set. */
	private String featureToSet;

	/**
	 * 
	 * Constructor.
	 *
	 * @param request
	 *            the {@link ReorientReferenceRelationshipRequest}.
	 */
	public StereotypePropertyReferenceReorientCommand(final ReorientReferenceRelationshipRequest request) {
		super(request.getLabel(), null, request);
		reorientDirection = request.getDirection();
		referenceOwner = request.getReferenceOwner();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
		Object value = request.getParameters().get(DefaultSemanticEditPolicy.GRAPHICAL_RECONNECTED_EDGE);
		view = value instanceof View ? (View) value : null;

		if (view != null) {
			EAnnotation eAnnotation = view.getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			if (null != eAnnotation) {
				featureToSet = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
				sourceStereotypeQualifiedName = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
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
		if (null != view && null != featureToSet && null != sourceStereotypeQualifiedName && referenceOwner instanceof Element) {
			if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
				canExecute = canReorientSource();
			} else if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
				canExecute = canReorientTarget();
			}
		}
		return canExecute;
	}

	/**
	 * @return true if the source can be reorient.
	 */
	protected boolean canReorientSource() {
		boolean canReorentSource = false;
		if ((oldEnd instanceof Element && newEnd instanceof Element)) {
			canReorentSource = ElementUtil.hasStereotypeApplied((Element) newEnd, sourceStereotypeQualifiedName);
		}
		return canReorentSource;
	}

	/**
	 * @return true if the target can be reorient.
	 */
	protected boolean canReorientTarget() {
		boolean canReorientTarget = false;
		if (newEnd instanceof Element) {
			// test if the target have the good stereotype
			Stereotype sourceStereotype = ((Element) referenceOwner).getApplicableStereotype(sourceStereotypeQualifiedName);
			Type type = sourceStereotype.getAttribute(featureToSet, null).getType();
			if (type instanceof Stereotype) {
				canReorientTarget = ElementUtil.hasStereotypeApplied((Element) newEnd, type.getQualifiedName());
			} else {
				Stereotype appliedSubstereotype = UMLUtil.getAppliedSubstereotype((Element) referenceOwner, sourceStereotype);
				if (null != appliedSubstereotype) {
					EObject stereotypeApplication = ((Element) referenceOwner).getStereotypeApplication(appliedSubstereotype);
					if (null != stereotypeApplication) {
						EStructuralFeature eStructuralFeature = stereotypeApplication.eClass().getEStructuralFeature(UML2Util.getValidJavaIdentifier(featureToSet));
						if (null != eStructuralFeature) {
							EClassifier metaclass = eStructuralFeature.getEType();
							if (metaclass instanceof EClassifier) {
								canReorientTarget = ((EClassifier) metaclass).isInstance(newEnd);
							}
						}
					}
				}
			}
		}
		return canReorientTarget;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * Reorient the source.
	 *
	 * @return the command result
	 * @throws ExecutionException
	 *             the execution exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected CommandResult reorientSource() throws ExecutionException {
		try {
			Stereotype stereotype = getOldSource().getApplicableStereotype(sourceStereotypeQualifiedName);
			Property attribute = stereotype.getAttribute(featureToSet, null);

			if (1 == attribute.getUpper()) {
				// Gets the target value to set the new source
				Object value = getOldSource().getValue(UMLUtil.getAppliedSubstereotype(getOldSource(), stereotype), featureToSet);

				// Set oldSource: remove reference
				if (noSimilarReferenceEdge()) {
					getOldSource().setValue(UMLUtil.getAppliedSubstereotype(getOldSource(), stereotype), featureToSet, null);
				}

				// Set newSource: add reference
				getNewSource().setValue(UMLUtil.getAppliedSubstereotype(getNewSource(), stereotype), featureToSet, value);
				cleanOtherEdge(getNewSource(), value);
			} else {
				// multireference case
				EObject oldValue;
				Type targetType = attribute.getType();
				if (targetType instanceof Stereotype) {
					oldValue = ElementUtil.getStereotypeApplication((Element) oldEnd, (Stereotype) targetType);
				} else {
					oldValue = oldEnd;
				}

				// if no similar link remove the value from old source feature list.
				if (noSimilarReferenceEdge()) {
					Object oldlist = getOldSource().getValue(UMLUtil.getAppliedSubstereotype(getOldSource(), stereotype), featureToSet);
					if (oldlist instanceof List) {
						((List) oldlist).remove(oldValue);
					}
				}

				Object newlist = getNewSource().getValue(UMLUtil.getAppliedSubstereotype(getNewSource(), stereotype), featureToSet);
				if (newlist instanceof List) {
					((List) newlist).add(oldValue);
				}
			}
		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}

		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @return true if there is no similar reference edge between old source and old target. In case of reorient source.
	 */
	protected boolean noSimilarReferenceEdge() {
		boolean noSimilar = true;
		if (view instanceof Edge) {
			for (Object edge : ViewUtil.getSourceConnections(((Edge) view).getSource())) {
				if (edge instanceof Edge && IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT.equals(((View) edge).getType()) && !edge.equals(view)) {
					EAnnotation eAnnotation = ((EModelElement) edge).getEAnnotation(IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
					String edgeFeatureToSet = eAnnotation.getDetails().get(IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY);
					String edgeStereotypeToSet = eAnnotation.getDetails().get(IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
					if (featureToSet.equals(edgeFeatureToSet) && sourceStereotypeQualifiedName.equals(edgeStereotypeToSet)) {
						EObject sourceElement = ((Edge) edge).getSource().getElement();
						EObject targetElement = ((Edge) edge).getTarget().getElement();
						noSimilar = !((Edge) view).getSource().getElement().equals(sourceElement) || !((Edge) view).getTarget().getElement().equals(targetElement);
					}
				}
			}
		}
		return noSimilar;
	}

	/**
	 * Reorient the target of the edge.
	 * 
	 * @return the command result.
	 * @throws ExecutionException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected CommandResult reorientTarget() throws ExecutionException {
		try {
			// Set oldSource: remove reference
			Stereotype stereotype = getOldSource().getApplicableStereotype(sourceStereotypeQualifiedName);
			Property attribute = stereotype.getAttribute(featureToSet, null);
			Type targetType = attribute.getType();

			EObject value;
			if (targetType instanceof Stereotype) {
				value = ElementUtil.getStereotypeApplication((Element) getNewTarget(), (Stereotype) targetType);
			} else {
				value = getNewTarget();
			}

			if (1 == attribute.getUpper()) {
				// Single reference case
				getOldSource().setValue(UMLUtil.getAppliedSubstereotype(getOldSource(), stereotype), featureToSet, value);
				cleanOtherEdge(getOldSource(), value);
			} else {
				// multi reference case
				EObject oldValue;
				if (targetType instanceof Stereotype) {
					oldValue = ElementUtil.getStereotypeApplication((Element) oldEnd, (Stereotype) targetType);
				} else {
					oldValue = oldEnd;
				}

				Object list = getOldSource().getValue(UMLUtil.getAppliedSubstereotype(getOldSource(), stereotype), featureToSet);
				if (list instanceof List) {
					// Remove old target if there if no similar edge into source and target
					// Add new target
					((List) list).add(value);
					if (noSimilarReferenceEdge()) {
						((List) list).remove(oldValue);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * Clean other edge.
	 *
	 * @param value
	 *            the value
	 * @throws ExecutionException
	 *             the execution exception
	 */
	protected void cleanOtherEdge(final Element source, final Object value) throws ExecutionException {
		ICommand cleanStereotypePropertyReferenceCommand = StereotypePropertyReferenceEdgeUtil.getCleanStereotypePropertyReferenceCommand(source, value, sourceStereotypeQualifiedName, featureToSet, getEditingDomain(), (Edge) view);
		if (null != cleanStereotypePropertyReferenceCommand && cleanStereotypePropertyReferenceCommand.canExecute()) {
			cleanStereotypePropertyReferenceCommand.execute(null, null);
		}
	}

	/**
	 * Gets the old source.
	 *
	 * @return the old source
	 */
	protected Element getOldSource() {
		return (Element) referenceOwner;
	}

	/**
	 * Gets the new source.
	 *
	 * @return the new source
	 */
	protected Element getNewSource() {
		return (Element) newEnd;
	}

	/**
	 * Gets the old target.
	 *
	 * @return the old target
	 */
	protected Element getOldTarget() {
		return (Element) oldEnd;
	}

	/**
	 * Gets the new target.
	 *
	 * @return the new target
	 */
	protected Element getNewTarget() {
		return (Element) newEnd;
	}
}
