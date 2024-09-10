/*****************************************************************************
* Copyright (c) 2021 CEA LIST, ARTAL
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Etienne ALLOGO (ARTAL) - Initial API and implementation
*   Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : generate less dead or duplicate code
*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.MaskManagedFloatingLabelEditPolicy;

/**
 * An abstract class to implement a Floating Label EditPart
 *
 * @since 5.0
 */
public abstract class AbstractFloatingLabelEditPart extends AbstractExternalLabelEditPart implements NamedStyleProperties, IFloatingLabelEditPart {


	/** The Constant DEFAULT_LABEL_DISPLAYED. */
	private static final boolean DEFAULT_LABEL_DISPLAYED = false;

	/** The isLabelDisplayed Observable */
	private IObservableValue<?> labelDisplayedObservable;

	/** The namedStyle Listener */
	private IChangeListener namedStyleListener = new IChangeListener() {

		@Override
		public void handleChange(ChangeEvent event) {
			refresh();
		}

	};

	/**
	 * Instantiates a new floating label edit part.
	 *
	 * @param view
	 *            the view
	 */
	public AbstractFloatingLabelEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL, new MaskManagedFloatingLabelEditPolicy());
	}

	@Override
	protected void refreshVisibility() {
		super.refreshVisibility();
		// get the value of the CSS property
		boolean isLabelDisplayed = NotationUtils.getBooleanValue((View) getModel(), DISPLAY_FLOATING_LABEL, getDefaultLabelVisibility());

		// Set the visibility of the label
		getFigure().setVisible(isLabelDisplayed);
	}

	protected boolean getDefaultLabelVisibility() {
		return DEFAULT_LABEL_DISPLAYED;
	}

	@Override
	protected void addNotationalListeners() {
		super.addNotationalListeners();

		labelDisplayedObservable = new CustomBooleanStyleObservableValue((View) getModel(), EMFHelper.resolveEditingDomain(getModel()), DISPLAY_FLOATING_LABEL);
		labelDisplayedObservable.addChangeListener(namedStyleListener);

	}

	@Override
	protected void removeNotationalListeners() {
		super.removeNotationalListeners();
		labelDisplayedObservable.dispose();
	}

}
