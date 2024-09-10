/*****************************************************************************
 * Copyright (c) 2010, 2014, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 525463
 *   Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : generate less dead or duplicate code
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.MaskManagedFloatingLabelEditPolicy;

/**
 * The Class FloatingNameEditPart. replaced by {@link AbstractFloatingLabelEditPart} with less generated code
 */
@Deprecated
public class FloatingLabelEditPart extends PapyrusLabelEditPart implements NamedStyleProperties, IFloatingLabelEditPart {


	/** The Constant DEFAULT_LABEL_DISPLAYED. */
	private static final boolean DEFAULT_LABEL_DISPLAYED = false;

	/** The isLabelDisplayed Observable */
	private IObservableValue labelDisplayedObservable;

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
	public FloatingLabelEditPart(View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart#createDefaultEditPolicies()
	 *
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL, new MaskManagedFloatingLabelEditPolicy());
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#refreshVisibility()
	 *
	 */
	@Override
	protected void refreshVisibility() {
		super.refreshVisibility();
		// get the value of the CSS property
		boolean isLabelDisplayed = NotationUtils.getBooleanValue((View) getModel(), DISPLAY_FLOATING_LABEL, getDefaultLabelVisibility());

		// Set the visibility of the label
		getFigure().setVisible(isLabelDisplayed);
	}

	/**
	 * Gets the default label displayed value.
	 *
	 * @return the default label displayed value
	 */
	protected boolean getDefaultLabelVisibility() {
		return DEFAULT_LABEL_DISPLAYED;
	}

	/**
	 * Adds listener to handle named Style modifications.
	 */
	@Override
	protected void addNotationalListeners() {
		super.addNotationalListeners();

		labelDisplayedObservable = new CustomBooleanStyleObservableValue((View) getModel(), EMFHelper.resolveEditingDomain(getModel()), DISPLAY_FLOATING_LABEL);
		labelDisplayedObservable.addChangeListener(namedStyleListener);

	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#removeNotationalListeners()
	 *
	 */
	@Override
	protected void removeNotationalListeners() {
		super.removeNotationalListeners();
		labelDisplayedObservable.dispose();
	}

}
