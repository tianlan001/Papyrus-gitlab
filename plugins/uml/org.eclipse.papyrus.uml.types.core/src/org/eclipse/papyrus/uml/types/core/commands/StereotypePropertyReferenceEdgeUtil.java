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

package org.eclipse.papyrus.uml.types.core.commands;

import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * The Util Class stereotype property reference edge.
 *
 * @author Mickael ADAM
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeUtil {

	public static boolean checkNotOrphanStereotypePropertyReferenceEdgeNotYetSet(final Edge edge, final String stereotypeToSet, final String featureToSet, final Object newFeatureValue) {
		boolean notOrphan = false;
		if (null != edge) {
			EAnnotation eAnnotation = ((View) edge).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			if (null != eAnnotation) {
				String stereotypeQNAnnotation = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
				String featureToSetAnnotation = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
				// Test annotation
				if (null != stereotypeQNAnnotation && null != featureToSetAnnotation) {

					EObject targetElement = edge.getTarget().getElement();
					EObject sourceElement = edge.getSource().getElement();

					// test source and target
					if (sourceElement instanceof Element && targetElement instanceof Element) {
						// test source compatibility
						if (checkStereotypePropertyReferenceEdgeSourceCompatibility((Element) sourceElement, stereotypeQNAnnotation)) {
							// test set target
							if (featureToSet.equals(featureToSetAnnotation) && stereotypeToSet.equals(stereotypeQNAnnotation)) {
								notOrphan = checkStereotypePropertyReferenceEdgeTargetSet((Element) sourceElement, (Element) targetElement, stereotypeQNAnnotation, featureToSetAnnotation, newFeatureValue);
							} else {
								notOrphan = checkStereotypePropertyReferenceEdgeTargetSet((Element) sourceElement, (Element) targetElement, stereotypeQNAnnotation, featureToSetAnnotation);
							}
						}
					}
				}
			}
		}
		return notOrphan;
	}

	/**
	 * Check if stereotype property reference edge is not orphan.
	 *
	 * @param edge
	 *            the edge to test
	 * @return true, if successful
	 */
	public static boolean checkNotOrphanStereotypePropertyReferenceEdge(final Edge edge) {
		boolean notOrphan = false;
		if (null != edge) {
			EAnnotation eAnnotation = ((View) edge).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			if (null != eAnnotation) {
				String stereotypeQNAnnotation = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
				String featureToSetAnnotation = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
				// Test annotation
				if (null != stereotypeQNAnnotation && null != featureToSetAnnotation) {

					EObject targetElement = edge.getTarget().getElement();
					EObject sourceElement = edge.getSource().getElement();

					// test source and target
					if (sourceElement instanceof Element && targetElement instanceof Element) {
						// test source compability
						if (checkStereotypePropertyReferenceEdgeSourceCompatibility((Element) sourceElement, stereotypeQNAnnotation)) {
							// test set target
							notOrphan = checkStereotypePropertyReferenceEdgeTargetSet((Element) sourceElement, (Element) targetElement, stereotypeQNAnnotation, featureToSetAnnotation);
						}
					}
				}
			}
		}
		return notOrphan;
	}

	/**
	 * Check stereotype property reference edge target set.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param targetElement
	 *            the target element
	 * @param stereotypeQualifiedName
	 *            the stereotype qualify name
	 * @param featureToSet
	 *            the feature to set
	 * @param newFeatureValue
	 *            the new feature value
	 * @return true, if successful
	 */
	public static boolean checkStereotypePropertyReferenceEdgeTargetSet(final Element sourceElement, final Element targetElement, final String stereotypeQualifiedName, final String featureToSet, final Object newFeatureValue) {

		boolean targetOk = false;
		if (null != sourceElement && null != targetElement) {
			Stereotype sourceStereotype = sourceElement.getApplicableStereotype(stereotypeQualifiedName);
			Property attribute = sourceStereotype.getAttribute(featureToSet, null);
			if (null != attribute && null != newFeatureValue) {
				Type targetType = attribute.getType();
				EObject targetValue = null;

				if (targetType instanceof Stereotype) {
					// feature as stereotype reference
					if (ElementUtil.hasStereotypeApplied(targetElement, targetType.getQualifiedName())) {
						// The edge target stereotype application must be equals to the source feature value stereotype application
						EObject targetStereotypeApplication = targetElement.getStereotypeApplication(UMLUtil.getAppliedSubstereotype(targetElement, (Stereotype) targetType));
						targetValue = targetStereotypeApplication;
					}
				} else {
					targetValue = targetElement;
				}

				if (newFeatureValue instanceof List) {
					targetOk = ((List<?>) newFeatureValue).contains(targetValue);
				} else {
					targetOk = newFeatureValue.equals(targetValue);
				}
			}
		}
		return targetOk;
	}

	/**
	 * Check if the target element is set in the stereotype's feature of the source element.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param targetElement
	 *            the target element
	 * @param stereotypeQualifiedName
	 *            the stereotype qualify name
	 * @param featureToSet
	 *            the feature to set
	 * @return true, if successful
	 */
	public static boolean checkStereotypePropertyReferenceEdgeTargetSet(final Element sourceElement, final Element targetElement, final String stereotypeQualifiedName, final String featureToSet) {
		boolean targetOk = false;
		if (null != sourceElement && null != targetElement) {
			Stereotype sourceStereotype = sourceElement.getApplicableStereotype(stereotypeQualifiedName);
			Stereotype appliedSubstereotype = UMLUtil.getAppliedSubstereotype(sourceElement, sourceStereotype);
			Object sourceFeatureValue = sourceElement.getValue(appliedSubstereotype, featureToSet);
			Property attribute = sourceStereotype.getAttribute(featureToSet, null);
			if (null != attribute && null != sourceFeatureValue) {
				Type targetType = attribute.getType();
				EObject targetValue = null;

				if (targetType instanceof Stereotype) {
					// feature as stereotype reference
					if (ElementUtil.hasStereotypeApplied(targetElement, targetType.getQualifiedName())) {
						// The edge target stereotype application must be equals to the source feature value stereotype application
						EObject targetStereotypeApplication = targetElement.getStereotypeApplication(UMLUtil.getAppliedSubstereotype(targetElement, (Stereotype) targetType));
						targetValue = targetStereotypeApplication;
					}
				} else {
					targetValue = targetElement;
				}

				if (sourceFeatureValue instanceof List) {
					targetOk = ((List<?>) sourceFeatureValue).contains(targetValue);
				} else {
					targetOk = sourceFeatureValue.equals(targetValue);
				}
			}
		}
		return targetOk;
	}

	/**
	 * Check stereotype property reference edge target compatibility.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param targetElement
	 *            the target element
	 * @param stereotypeQualifiedName
	 *            the stereotype qualify name
	 * @param featureToSet
	 *            the feature to set
	 * @return true, if successful
	 */
	public static boolean checkStereotypePropertyReferenceEdgeTargetCompatibility(final Element sourceElement, final Element targetElement, final String stereotypeQualifiedName, final String featureToSet) {
		boolean compatible = false;
		if (null != sourceElement && null != targetElement) {
			// test if the target have the good stereotype
			Stereotype sourceStereotype = sourceElement.getApplicableStereotype(stereotypeQualifiedName);
			Type type = sourceStereotype.getAttribute(featureToSet, null).getType();
			if (type instanceof Stereotype) {
				compatible = ElementUtil.hasStereotypeApplied(targetElement, type.getQualifiedName());
			} else {
				Stereotype appliedSubstereotype = UMLUtil.getAppliedSubstereotype(sourceElement, sourceStereotype);
				if (null != appliedSubstereotype) {
					EObject stereotypeApplication = (sourceElement.getStereotypeApplication(appliedSubstereotype));
					if (null != stereotypeApplication) {
						EStructuralFeature eStructuralFeature = stereotypeApplication.eClass().getEStructuralFeature(UML2Util.getValidJavaIdentifier(featureToSet));
						if (null != eStructuralFeature) {
							EClassifier metaclass = eStructuralFeature.getEType();
							if (metaclass instanceof EClassifier) {
								compatible = metaclass.isInstance(targetElement);
							}
						}
					}
				}
			}
		}
		return compatible;
	}

	/**
	 * Check stereotype property reference edge source compatibility.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param stereotypeQualifiedName
	 *            the stereotype qualify name
	 * @return true, if successful
	 */
	public static boolean checkStereotypePropertyReferenceEdgeSourceCompatibility(final Element sourceElement, final String stereotypeQualifiedName) {
		boolean compatible = false;
		if (null != sourceElement) {
			compatible = ElementUtil.hasStereotypeApplied(sourceElement, stereotypeQualifiedName);
		}
		return compatible;
	}


	/**
	 * Find the view from stereotype application.
	 *
	 * @param stereotype
	 *            the stereotype
	 * @return the find view
	 */
	public static View findViewFromStereotype(final EObject stereotype) {
		View view = null;
		if (stereotype != null) {
			Element baseElement = org.eclipse.uml2.uml.util.UMLUtil.getBaseElement(stereotype);
			view = FindViewFromElement(baseElement);
		}
		return view;
	}

	/**
	 * Find view from element.
	 *
	 * @param element
	 *            the element
	 * @return the find view
	 */
	public static View FindViewFromElement(final Element element) {
		View view = null;
		if (null != element) {
			Collection<Setting> settings = CacheAdapter.getInstance().getNonNavigableInverseReferences(element);

			for (Setting ref : settings) {
				if (NotationPackage.eINSTANCE.getView_Element().equals(ref.getEStructuralFeature())) {
					view = (View) ref.getEObject();
				}
			}
		}
		return view;
	}

	/**
	 * Get the clean stereotype property reference edge command.
	 *
	 * @param request
	 *            the request
	 * @param stereotypeToSet
	 *            the stereotype to set
	 * @param featureToSet
	 *            the feature to set
	 * @return the clean stereotype property reference edge command
	 */
	public static ICommand getCleanStereotypePropertyReferenceEdgeCommand(final CreateRelationshipRequest request, final String stereotypeToSet, final String featureToSet) {
		// Delete reference Edge in case of set of feature. Element to edit is the source of reference edge.
		EObject source = request.getSource();
		TransactionalEditingDomain editingDomain = request.getEditingDomain();
		final Object target = request.getTarget();
		return getCleanStereotypePropertyReferenceCommand(source, target, stereotypeToSet, featureToSet, editingDomain, null);

	}

	/**
	 * Get the clean stereotype property reference command.
	 *
	 * @param source
	 *            the source
	 * @param newTargetValue
	 *            the new target value
	 * @param stereotypeToSet
	 *            the stereotype to set
	 * @param featureToSet
	 *            the feature to set
	 * @param editingDomain
	 *            the editing domain
	 * @return the clean stereotype property reference command
	 */
	public static ICommand getCleanStereotypePropertyReferenceCommand(final EObject source, final Object newTargetValue, final String stereotypeToSet, final String featureToSet, TransactionalEditingDomain editingDomain, final Edge movedEdge) {
		if (source instanceof Element) {
			View view = StereotypePropertyReferenceEdgeUtil.FindViewFromElement((Element) source);

			if (view != null) {
				Stereotype applicableStereotypeToSet = ((Element) source).getApplicableStereotype(stereotypeToSet);
				Stereotype actualStereotype = UMLUtil.getAppliedSubstereotype((Element) source, applicableStereotypeToSet);

				if (null != actualStereotype) {
					// Get the value which will be set
					Property attribute = applicableStereotypeToSet.getAttribute(featureToSet, null);
					if (null != attribute) {
						Type targetType = attribute.getType();
						Object value = null;
						if (targetType instanceof Stereotype && newTargetValue instanceof Element) {
							value = ((Element) newTargetValue).getStereotypeApplication(UMLUtil.getAppliedSubstereotype((Element) newTargetValue, actualStereotype));
						} else {
							value = newTargetValue;
						}

						String stereotypeQualifiedName = actualStereotype.getQualifiedName();
						if (null != stereotypeQualifiedName && null != featureToSet) {

							CompositeCommand command = new CompositeCommand("Clear stereotype property reference edges");//$NON-NLS-1$

							for (Object edge : ViewUtil.getSourceConnections(view)) {
								if (edge instanceof Edge && STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT.equals(((View) edge).getType())) {
									if (null == movedEdge || (null != movedEdge && !edge.equals(movedEdge))) {
										if (!StereotypePropertyReferenceEdgeUtil.checkNotOrphanStereotypePropertyReferenceEdgeNotYetSet((Edge) edge, stereotypeQualifiedName, featureToSet, value)) {
											command.add(new DeleteCommand(editingDomain, (Edge) edge));
										}
									}
								}
							}

							return command.isEmpty() ? null : command;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the clean stereotype property reference edge command.
	 *
	 * @param request
	 *            the {@link ReorientReferenceRelationshipRequest} request
	 * @return the clean stereotype property reference edge command
	 */
	public static ICommand getCleanStereotypePropertyReferenceEdgeCommand(final ReorientReferenceRelationshipRequest request) {
		int reorientDirection = request.getDirection();
		EObject newEnd = request.getNewRelationshipEnd();
		Object value = request.getParameters().get(DefaultSemanticEditPolicy.GRAPHICAL_RECONNECTED_EDGE);
		Edge view = value instanceof Edge ? (Edge) value : null;
		TransactionalEditingDomain editingDomain = request.getEditingDomain();

		if (null != view) {
			EAnnotation eAnnotation = view.getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			String featureToSet = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
			String stereotypeToSet = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
			EObject target = null;
			if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
				target = newEnd;

				CompositeCommand command = new CompositeCommand("Clear stereotype property reference edges");//$NON-NLS-1$

				for (Object edge : ViewUtil.getSourceConnections(view.getSource())) {
					if (edge instanceof Edge && STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT.equals(((View) edge).getType()) && !edge.equals(view)) {
						if (!StereotypePropertyReferenceEdgeUtil.checkNotOrphanStereotypePropertyReferenceEdgeNotYetSet((Edge) edge, stereotypeToSet, featureToSet, target)) {
							command.add(new DeleteCommand(editingDomain, (Edge) edge));
						}
					}
				}
				return command.isEmpty() ? null : command;
			}
		}
		return null;
	}

	/**
	 * Checks if the object is an stereotype property reference edge.
	 * 
	 * @param object
	 *            the object to test
	 */
	public static boolean isStereotypePropertyReferenceEdge(final Object object) {
		View view = object instanceof View ? (View) object : null;
		if (null != view) {
			EAnnotation eAnnotation = view.getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			if (null != eAnnotation) {
				return true;
			}
		}
		return false;
	}

}
