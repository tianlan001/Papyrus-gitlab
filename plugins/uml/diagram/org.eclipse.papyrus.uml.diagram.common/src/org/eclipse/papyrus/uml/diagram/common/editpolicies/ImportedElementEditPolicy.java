/*****************************************************************************
 * Copyright (c) 2014,2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Benoit Maggi (CEA LIST) - Bug 518313
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalReferenceEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.SemanticElementHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.ExternalReferenceGroup;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;

/**
 * UML-specific implementation of the {@link ExternalReferenceEditPolicy}
 *
 * It displays a decorator for elements which are located in another Package, and
 * for inherited properties (displayed in a Classifier Representation which is not
 * the defining classifier)
 *
 * @author Camille Letavernier
 */
public class ImportedElementEditPolicy extends ExternalReferenceEditPolicy {

	@Override
	protected boolean isExternalRef(View diagramElement) {
		if (!super.isExternalRef(diagramElement)) {
			return false;
		}

		if (diagramElement == null) {
			return false;
		}

		View primaryView = SemanticElementHelper.findTopView(diagramElement);
		if (primaryView == null) {
			return false;
		}

		EObject semanticElement = primaryView.getElement();

		if (!(semanticElement instanceof Element)) {
			return false;
		}

		// Special case for properties
		if (semanticElement instanceof Property) {
			return isInheritedProperty(diagramElement);
		}

		// Don't display decorators on Constraints and Comments. Their graphical containment is not meant
		// to match their semantic containment (The relationship is often represented with a link)
		if (semanticElement instanceof Constraint || semanticElement instanceof Comment) {
			return false;
		}

		EObject parentView = primaryView.eContainer();
		if (!(parentView instanceof View)) {
			return false;
		}

		EObject parentSemanticElement = ((View) parentView).getElement();
		if (!(parentSemanticElement instanceof Element)) {
			return false;
		}


		if (StereotypeDisplayUtil.getInstance().isStereotypeView(getView())) {
			return false;
		}

		// Generic case: graphical containment doesn't match the semantic containment (We only check Package containment)
		Element semanticUMLElement = (Element) semanticElement;
		Element parentUMLElement = (Element) parentSemanticElement;

		String papyrusEditorConstant = PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.EXTERNAL_REFERENCE_STRATEGY);
		String externalReferencePreference = org.eclipse.papyrus.infra.gmfdiag.preferences.Activator.getDefault().getPreferenceStore().getString(papyrusEditorConstant);
		switch (externalReferencePreference) {
		case ExternalReferenceGroup.EXTERNAL_REFERENCE_NONE:
			return false;
		case ExternalReferenceGroup.EXTERNAL_REFERENCE_OWNER:
			return semanticUMLElement.getOwner() != parentUMLElement;
		case ExternalReferenceGroup.EXTERNAL_REFERENCE_CONTAINER:
		default:
			if (parentUMLElement instanceof Package) {
				return semanticUMLElement.getNearestPackage() != parentUMLElement;
			}
			return semanticUMLElement.getNearestPackage() != parentUMLElement.getNearestPackage();
		}
	}

	// Display a decorator on inherited properties, when they are displayed in a Classifier
	// Don't display a decorator for properties displayed as white box parts/references (Part with port, Part with structure, ...)
	protected boolean isInheritedProperty(View diagramElement) {
		View primaryView = SemanticElementHelper.findTopView(diagramElement);
		if (primaryView == null) {
			return false;
		}

		View parentView = (View) primaryView.eContainer();
		if (parentView == null) {
			return false;
		}

		EObject semanticElement = SemanticElementHelper.findSemanticElement(diagramElement);
		if (!(semanticElement instanceof Property)) {
			return false;
		}

		Property property = (Property) semanticElement;

		EObject semanticParent = SemanticElementHelper.findSemanticElement(parentView);

		if (semanticParent instanceof Property) { // For parts/ref/ports, never display the decorator
			// semanticParent = ((Property)semanticParent).getType(); //Check the inheritance from the type
			return false;
		}

		if (semanticParent instanceof Classifier) {
			return property.eContainer() != semanticParent;
		} else {
			return true;
		}
	}

}
