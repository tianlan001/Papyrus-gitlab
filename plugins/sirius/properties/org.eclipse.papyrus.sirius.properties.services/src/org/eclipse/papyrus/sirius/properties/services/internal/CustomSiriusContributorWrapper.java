/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.services.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.eef.ide.ui.internal.preferences.EEFPreferences;
import org.eclipse.eef.ide.ui.internal.widgets.EEFTextLifecycleManager.ConflictResolutionMode;
import org.eclipse.eef.properties.ui.api.AbstractEEFTabbedPropertySheetPageContributorWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.papyrus.infra.properties.ui.providers.PropertiesHeaderLabelProvider;
import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.LiteralSpecification;
import org.eclipse.uml2.uml.MultiplicityElement;

/**
 * The workbench part requesting the use of the property page.
 * This wrapper is also used to display title of Properties view when user selects an element from ModelExplorer view or from Sirius/Papyrus representation.
 *
 * @author Jessy Mallet <jessy.mallet@obeo.fr>
 */
@SuppressWarnings("restriction")
public class CustomSiriusContributorWrapper extends AbstractEEFTabbedPropertySheetPageContributorWrapper {

	/**
	 * List of semantic elements with properties to display.
	 */
	private List<EObject> currentElements;

	/**
	 * List of previous semantic elements with properties displayed.
	 */
	private List<EObject> formerElements;

	/**
	 * Constructor.
	 */
	public CustomSiriusContributorWrapper() {
		super(null, (String) null);
	}

	/**
	 * Constructor.
	 *
	 * @param realContributor
	 *            the original contributor object.
	 * @param contributorIds
	 *            the list of contributor ids.
	 */
	public CustomSiriusContributorWrapper(Object realContributor, List<String> contributorIds) {
		super(realContributor, contributorIds);
		initEEFProperties();
	}

	/**
	 * Constructor.
	 *
	 * @param realContributor
	 *            the original contributor object.
	 * @param contributorId
	 *            the contributor id.
	 */
	public CustomSiriusContributorWrapper(Object realContributor, String contributorId) {
		super(realContributor, contributorId);
		initEEFProperties();
	}

	@Override
	public void updateFormTitle(Form form, ISelection selection) {
		if (form.isDisposed()) {
			return;
		}
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			@SuppressWarnings("unchecked")
			List<Object> list = structuredSelection.toList();
			List<EObject> semanticElements = new ArrayList<>();
			SiriusInputDescriptor inputDescriptor = null;
			if (!list.isEmpty()) {
				inputDescriptor = new SiriusInputDescriptor(list.get(0), list);
				semanticElements.addAll(inputDescriptor.getSemanticElements());
				if (semanticElements.isEmpty() && list.size() == 1) {
					if (list.get(0) instanceof EObjectTreeElementImpl && ((EObjectTreeElementImpl) list.get(0)).getEObject() != null) {
						EObjectTreeElementImpl eObjectTreeElementImpl = (EObjectTreeElementImpl) list.get(0);
						semanticElements.add(eObjectTreeElementImpl.getEObject());
					}
				}
			}

			cleanInterpreter(inputDescriptor, semanticElements);
			if (!semanticElements.isEmpty()) {
				Object firstImage = null;
				String text = ""; //$NON-NLS-1$
				ILabelProvider provider = new PropertiesHeaderLabelProvider();
				if (provider != null) {
					for (int i = 0; i < semanticElements.size(); i++) {
						EObject semantic = semanticElements.get(i);
						if (semantic != null) {
							if (i == 0) {
								firstImage = provider.getImage(semantic);
								text = provider.getText(semantic);
							} else {
								text = text.concat(", ".concat(provider.getText(semantic))); //$NON-NLS-1$
							}
						}
					}
				}
				if (semanticElements.size() != 1) {
					firstImage = null;
				}
				form.setText(text);
				form.setImage(ExtendedImageRegistry.INSTANCE.getImage(firstImage));
			} else {
				form.setText(""); //$NON-NLS-1$
				form.setImage(null);
			}
		}

	}

	/**
	 * Update interpreter for live validation.
	 * Some variables have been added in interpreter after live validation of {@link LiteralSpecification}
	 * and {@link MultiplicityElement}.
	 * These variables should be cleaned to avoid reuse on an other element of similar type or if the user
	 * display again properties of the same element.
	 * 
	 * @param interpreter
	 *            Sirius interpreter used to evaluate AQL expression from VSM,
	 * @param semanticElements
	 *            list of selected element with properties to display.
	 */
	private void cleanInterpreter(SiriusInputDescriptor inputDescriptor, List<EObject> semanticElements) {
		currentElements = semanticElements;
		if (currentElements != formerElements) {
			if (currentElements != null && formerElements != null && !currentElements.isEmpty() && !formerElements.isEmpty()) {
				EObject obj1 = currentElements.get(0);
				EObject obj2 = formerElements.get(0);
				if (!obj2.equals(obj1)) {
					formerElements = currentElements;
					if (interpreterVariablesHasBeenDefined(obj1) || interpreterVariablesHasBeenDefined(obj2)) {
						SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(inputDescriptor);
					}
				}
			} else {
				formerElements = currentElements;
			}
		}
	}

	private boolean interpreterVariablesHasBeenDefined(EObject obj1) {
		return obj1 instanceof LiteralSpecification || obj1 instanceof MultiplicityElement || obj1 instanceof Association;
	}

	/**
	 * Override the EEF Preference "ConflictResolutionMode" to use USE_MODEL_VERSION instead of
	 * ASK_USER to avoid having an EEF configuration popup.
	 * This popup appears when a value entered by the user is different from the one used in the
	 * model. This popup allows the user to choose the value to use, but the value to use must
	 * always be the one used in the model, it is not necessary to ask the user and this popup
	 * should disappear.
	 */
	private void initEEFProperties() {
		EEFPreferences.setTextConflictResolutionMode(ConflictResolutionMode.USE_MODEL_VERSION);
	}
}
