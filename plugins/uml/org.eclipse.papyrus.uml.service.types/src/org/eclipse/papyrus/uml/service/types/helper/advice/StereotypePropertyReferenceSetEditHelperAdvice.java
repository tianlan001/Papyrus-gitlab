/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.types.core.commands.StereotypePropertyReferenceEdgeUtil;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * The {@link AbstractEditHelperAdvice} for stereotype property reference edge set.
 *
 * @author Mickael ADAM
 * @since 3.1
 */
public class StereotypePropertyReferenceSetEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 */
	@Override
	protected ICommand getAfterSetCommand(final SetRequest request) {
		// Delete reference link in case of set of feature. Element to edit is the source of reference link.
		EObject element = request.getElementToEdit();
		View view = StereotypePropertyReferenceEdgeUtil.findViewFromStereotype(element);
		TransactionalEditingDomain editingDomain = request.getEditingDomain();

		if (view != null) {
			EStructuralFeature structuralFeature = request.getFeature();
			final Object value = request.getValue();

			if (structuralFeature instanceof EReference) {
				String featureName = structuralFeature.getName();

				Stereotype stereotype = UMLUtil.getStereotype(element);
				if (null != stereotype) {
					String stereotypeQualifyName = stereotype.getQualifiedName();
					if (null != stereotypeQualifyName && null != featureName) {

						CompositeCommand command = new CompositeCommand("Clear stereotype property reference links");//$NON-NLS-1$

						for (Object edge : ViewUtil.getSourceConnections(view)) {
							if (edge instanceof Edge && STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT.equals(((View) edge).getType())) {
								Edge edgeToDestroy = null;
								if (!StereotypePropertyReferenceEdgeUtil.checkNotOrphanStereotypePropertyReferenceEdgeNotYetSet((Edge) edge, stereotypeQualifyName, featureName, value)) {
									edgeToDestroy = (Edge) edge;
								}

								if (edgeToDestroy != null) {
									DestroyElementRequest destroy = new DestroyElementRequest(editingDomain, edgeToDestroy, false);
									Object eHelperContext = destroy.getEditHelperContext();
									IElementType context = ElementTypeRegistry.getInstance().getElementType(eHelperContext);
									if (context != null) {
										ICommand result = context.getEditCommand(destroy);
										if (result != null) {
											command.add(result);
										}
									}
								}
							}
						}

						return command.isEmpty() ? null : command;
					}
				}
			}
		}
		return null;
	}

}
