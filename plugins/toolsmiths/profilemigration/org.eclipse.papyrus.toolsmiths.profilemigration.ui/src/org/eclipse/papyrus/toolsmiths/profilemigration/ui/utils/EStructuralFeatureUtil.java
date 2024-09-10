/*****************************************************************************
 * Copyright (c) 2017, 2019 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 549705
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.emf.utils.HistoryUtil;
import org.eclipse.papyrus.infra.properties.ui.providers.FeatureContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.uml.properties.creation.UMLPropertyEditorFactory;
import org.eclipse.papyrus.uml.properties.widgets.EStructuralFeatureEditor;
import org.eclipse.papyrus.uml.tools.providers.UMLContainerContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Util method to use EStructuralFeatureEditor
 */
public class EStructuralFeatureUtil {


	/**
	 * Change displayed values according to the new element
	 *
	 * @param editor
	 * @param stereotypedElement
	 * @param property
	 * @param stereotype
	 *
	 */
	public static void setValueToEditor(EStructuralFeatureEditor editor, Element stereotypedElement, Property property, Stereotype stereotype) {
		EObject stereotypeApplication = stereotypedElement.getStereotypeApplication(stereotype);
		EStructuralFeature structuralFeature = getFeature(stereotypeApplication, property);
		editor.setProviders(new UMLContentProvider(stereotypeApplication, structuralFeature, stereotype), new UMLLabelProvider());
		if (structuralFeature instanceof EReference) {
			editor.setValueFactory(getUMLPropertyEditorFactory(stereotypeApplication, (EReference) structuralFeature));
		}

		editor.setFeatureToEdit(property.getName(), structuralFeature, stereotypedElement, stereotypeApplication);

	}

	/**
	 * Get the structural feature according to the stereotype application and the property
	 *
	 * @param stereotypeApplication
	 * @return
	 */
	private static EStructuralFeature getFeature(EObject stereotypeApplication, Property property) {
		return stereotypeApplication.eClass().getEStructuralFeature(UML2Util.getValidJavaIdentifier(property.getName()));
	}

	/**
	 * Get an UMLPropertyEditorFactory
	 *
	 * @param stereotypeApplication
	 * @param reference
	 * @return
	 */
	private static UMLPropertyEditorFactory getUMLPropertyEditorFactory(EObject stereotypeApplication, EReference reference) {
		UMLPropertyEditorFactory factory = new UMLPropertyEditorFactory(reference);
		EClass type = reference.getEReferenceType();

		factory.setContainerLabelProvider(new UMLFilteredLabelProvider());
		factory.setReferenceLabelProvider(new EMFLabelProvider());

		ITreeContentProvider contentProvider = new UMLContainerContentProvider(stereotypeApplication, reference);

		EMFGraphicalContentProvider provider = ProviderHelper.encapsulateProvider(contentProvider, stereotypeApplication.eResource().getResourceSet(), HistoryUtil.getHistoryID(stereotypeApplication, reference, "container")); //$NON-NLS-1$

		factory.setContainerContentProvider(provider);
		factory.setReferenceContentProvider(new FeatureContentProvider(type));

		return factory;
	}

}
