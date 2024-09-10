/*****************************************************************************

 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.draw2d.Graphics;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomIntStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableList;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.PapyrusRoundedEditPartHelper;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AllowResizeAffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;

/**
 * The Class RoundedCompartmentEditPart.
 */
public abstract class RoundedCompartmentEditPart extends NamedElementEditPart {

	/** The Constant DEFAULT_HAS_HEADER. True if has a header, as interaction figure */
	private static final boolean DEFAULT_HAS_HEADER = false;

	/** The Constant DEFAULT_BORDER_STYLE. */
	private static final int DEFAULT_BORDER_STYLE = Graphics.LINE_SOLID;

	/** The Constant DEFAULT_CORNER_HEIGHT. */
	private static final int DEFAULT_CORNER_HEIGHT = 0;

	/** The Constant DEFAULT_CORNER_WIDTH. */
	private static final int DEFAULT_CORNER_WIDTH = 0;

	private static final int[] DEFAULT_CUTOM_DASH = new int[] { 5, 5 };

	/** The Constant DEFAULT_FLOATING_LABEL_OFFSET_HEIGHT. */
	private static final int DEFAULT_FLOATING_LABEL_OFFSET_HEIGHT = 0;

	/** The Constant DEFAULT_FLOATING_LABEL_OFFSET_WIDTH. */
	private static final int DEFAULT_FLOATING_LABEL_OFFSET_WIDTH = 0;

	/** The Constant DEFAULT_IS_FLOATING_LABEL_CONSTRAINED. */
	private static final boolean DEFAULT_IS_FLOATING_LABEL_CONSTRAINED = false;

	/** The Constant DEFAULT_IS_OVAL. */
	private static final boolean DEFAULT_IS_OVAL = false;

	/** The Constant DEFAULT_IS_PACKAGE. */
	private static final boolean DEFAULT_IS_PACKAGE = false;

	/** The Constant DEFAULT_USE_ORIGINAL_COLORS. */
	private static final boolean DEFAULT_USE_ORIGINAL_COLORS = true;

	/** The Constant DEFAULT_SHADOW_WIDTH. */
	private static final int DEFAULT_SHADOW_WIDTH = 4;

	/**
	 * The namedStyle Listener
	 * 
	 * @since 3.0
	 */
	protected IChangeListener namedStyleListener = new IChangeListener() {

		@Override
		public void handleChange(ChangeEvent event) {
			refresh();

		}

	};

	/** The oval Observable */
	private IObservableValue ovalObservable;

	/** The borderStyle Observable */
	private IObservableValue borderStyleObservable;

	/** The hasHeader Observable */
	private IObservableValue hasHeaderObservable;

	/** The radiusHeight Observable */
	private IObservableValue radiusObservableHeight;

	/** The radiusWidth Observable */
	private IObservableValue radiusObservableWidth;

	/** The customDash Observable */
	private IObservableList customDashObservable;

	/** The isPackage Observable */
	private IObservableValue isPackageObservable;

	/** The shadowWidth Observable */
	private IObservableValue shadowWidthObservable;

	/** The shadowColor Observable */
	private IObservableValue shadowColorObservable;

	/** The alignment Observable */
	private IObservableValue alignmentObservable;

	/** The hasHeader Observable */
	private IObservableValue isFloatingLabelContrainedObservable;

	/** The radiusHeight Observable */
	private IObservableValue floatingLabelOffsetObservableHeight;

	/** The radiusWidth Observable */
	private IObservableValue floatingLabelOffsetObservableWidth;

	/** The nameBackgroundColor Observable */
	private IObservableValue nameBackgroundColorObservable;

	/**
	 * Instantiates a new rounded compartment edit part.
	 *
	 * @param view
	 *            the view
	 */
	public RoundedCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * Creates the default edit policies.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#createDefaultEditPolicies()
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		// Install Edit Policy to Hide/show compartment, in particular for the symbol compartment
		installEditPolicy(ShowHideCompartmentEditPolicy.SHOW_HIDE_COMPARTMENT_POLICY, new ShowHideCompartmentEditPolicy());
		installEditPolicy(AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE, new AllowResizeAffixedNodeAlignmentEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#installEditPolicy(java.lang.Object, org.eclipse.gef.EditPolicy)
	 */
	@Override
	public void installEditPolicy(Object key, EditPolicy editPolicy) {
		if (AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE.equals(key)) {
			if (editPolicy instanceof AllowResizeAffixedNodeAlignmentEditPolicy)
				super.installEditPolicy(key, editPolicy);
		} else
			super.installEditPolicy(key, editPolicy);
	}

	/**
	 * Refresh visuals.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		// Refresh all visual settings for the Rounded Compartment Figure child
		PapyrusRoundedEditPartHelper.refreshRadius(this, getDefaultCornerWidth(), getDefaultCornerHeight());
		PapyrusRoundedEditPartHelper.refreshOval(this, getDefaultIsOvalValue());
		PapyrusRoundedEditPartHelper.refreshFloatingName(this, getDefaultIsFloatingNameConstrained(), getDefaultFloatingLabelOffsetWidth(), getDefaultFloatingLabelOffsetHeight());
		PapyrusRoundedEditPartHelper.refreshBorderStyle(this, getDefaultBorderStyle(), getDefaultCutomDash());
		PapyrusRoundedEditPartHelper.refreshHasHeader(this, getDefaultHasHeader());
		PapyrusRoundedEditPartHelper.refreshShadowWidth(this, getDefaultShadowWidth());
		PapyrusRoundedEditPartHelper.refreshPackage(this, getDefaultIsPackage());
		PapyrusRoundedEditPartHelper.refreshShadowColor(this, getDefaultShadowColor());
		PapyrusRoundedEditPartHelper.refreshNameLabelColor(this, getNameLabelbackgroundColor());
		super.refreshVisuals();
	}

	/**
	 * Get the line type of the shape.
	 * 
	 * @return the line type.
	 */
	@Override
	protected int getLineType() {
		return PapyrusRoundedEditPartHelper.getNotationBorderStyle(this, getDefaultBorderStyle());
	}

	/**
	 * Gets the name label color.
	 *
	 * @return the name label background color
	 */
	protected String getNameLabelbackgroundColor() {
		return null;
	}

	/**
	 * Adds listener to handle named Style modifications.
	 */
	@Override
	protected void addNotationalListeners() {
		super.addNotationalListeners();

		View view = (View) getModel();
		EditingDomain domain = EMFHelper.resolveEditingDomain(view);

		radiusObservableWidth = new CustomIntStyleObservableValue(view, domain, RADIUS_WIDTH);
		radiusObservableWidth.addChangeListener(namedStyleListener);

		radiusObservableHeight = new CustomIntStyleObservableValue(view, domain, RADIUS_HEIGHT);
		radiusObservableHeight.addChangeListener(namedStyleListener);

		ovalObservable = new CustomBooleanStyleObservableValue(view, domain, IS_OVAL);
		ovalObservable.addChangeListener(namedStyleListener);

		borderStyleObservable = new CustomStringStyleObservableValue(view, domain, BORDER_STYLE);
		borderStyleObservable.addChangeListener(namedStyleListener);

		customDashObservable = new CustomStringStyleObservableList(view, domain, LINE_CUSTOM_VALUE);
		customDashObservable.addChangeListener(namedStyleListener);

		hasHeaderObservable = new CustomBooleanStyleObservableValue(view, domain, DISPLAY_HEADER);
		hasHeaderObservable.addChangeListener(namedStyleListener);

		isPackageObservable = new CustomBooleanStyleObservableValue(view, domain, IS_PACKAGE);
		isPackageObservable.addChangeListener(namedStyleListener);

		shadowWidthObservable = new CustomIntStyleObservableValue(view, domain, SHADOW_WIDTH);
		shadowWidthObservable.addChangeListener(namedStyleListener);

		shadowColorObservable = new CustomStringStyleObservableValue(view, domain, SHADOW_COLOR);
		shadowColorObservable.addChangeListener(namedStyleListener);

		nameBackgroundColorObservable = new CustomStringStyleObservableValue(view, domain, NAME_BACKGROUND_COLOR);
		nameBackgroundColorObservable.addChangeListener(namedStyleListener);

		alignmentObservable = new CustomStringStyleObservableValue(view, domain, TEXT_ALIGNMENT);
		alignmentObservable.addChangeListener(namedStyleListener);

		floatingLabelOffsetObservableHeight = new CustomIntStyleObservableValue(view, domain, FLOATING_LABEL_OFFSET_HEIGHT);
		floatingLabelOffsetObservableHeight.addChangeListener(namedStyleListener);

		floatingLabelOffsetObservableWidth = new CustomIntStyleObservableValue(view, domain, FLOATING_LABEL_OFFSET_WIDTH);
		floatingLabelOffsetObservableWidth.addChangeListener(namedStyleListener);

		isFloatingLabelContrainedObservable = new CustomBooleanStyleObservableValue(view, domain, FLOATING_LABEL_CONSTRAINED);
		isFloatingLabelContrainedObservable.addChangeListener(namedStyleListener);
	}

	/**
	 * Removes the notational listeners.
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#removeNotationalListeners()
	 */
	@Override
	protected void removeNotationalListeners() {
		super.removeNotationalListeners();
		radiusObservableWidth.dispose();
		radiusObservableHeight.dispose();
		ovalObservable.dispose();
		borderStyleObservable.dispose();
		customDashObservable.dispose();
		hasHeaderObservable.dispose();
		isPackageObservable.dispose();
		shadowWidthObservable.dispose();
		shadowColorObservable.dispose();
		nameBackgroundColorObservable.dispose();
		alignmentObservable.dispose();
		floatingLabelOffsetObservableHeight.dispose();
		floatingLabelOffsetObservableWidth.dispose();
		isFloatingLabelContrainedObservable.dispose();
	}

	/**
	 * Gets the default shadow color.
	 *
	 * @return the default shadow color
	 */
	private String getDefaultShadowColor() {
		// When null the color is the same that the border line.
		return null;
	}

	/**
	 * Gets the default is package.
	 *
	 * @return the default is package
	 */
	protected boolean getDefaultIsPackage() {
		return DEFAULT_IS_PACKAGE;
	}

	/**
	 * Gets the default shadow width.
	 *
	 * @return the default shadow width
	 */
	private int getDefaultShadowWidth() {
		return DEFAULT_SHADOW_WIDTH;
	}

	/**
	 * Gets the default border style.
	 *
	 * @return the default border style
	 */
	protected int getDefaultBorderStyle() {
		return DEFAULT_BORDER_STYLE;
	}

	/**
	 * Gets the default corner height value.
	 *
	 * @return the default corner height value
	 */
	protected int getDefaultCornerHeight() {
		return DEFAULT_CORNER_HEIGHT;
	}

	/**
	 * Gets the default corner width value.
	 *
	 * @return the default corner width value
	 */
	protected int getDefaultCornerWidth() {
		return DEFAULT_CORNER_WIDTH;
	}

	/**
	 * Gets the default custom dash.
	 *
	 * @return the default custom dash
	 */
	private int[] getDefaultCutomDash() {
		return DEFAULT_CUTOM_DASH;
	}

	/**
	 * Gets the default floating name offset height.
	 *
	 * @return the default floating name offset height
	 */
	protected int getDefaultFloatingLabelOffsetHeight() {
		return DEFAULT_FLOATING_LABEL_OFFSET_HEIGHT;
	}

	/**
	 * Gets the default floating name offset width.
	 *
	 * @return the default floating name offset width
	 */
	protected int getDefaultFloatingLabelOffsetWidth() {
		return DEFAULT_FLOATING_LABEL_OFFSET_WIDTH;
	}

	/**
	 * Gets the default is name constrained.
	 *
	 * @return the default is name constrained
	 */
	protected boolean getDefaultIsFloatingNameConstrained() {
		return DEFAULT_IS_FLOATING_LABEL_CONSTRAINED;
	}

	/**
	 * Gets the default is oval value.
	 *
	 * @return the default is oval value
	 */
	protected boolean getDefaultIsOvalValue() {
		return DEFAULT_IS_OVAL;
	}

	/**
	 * Gets the default setting of use original colors.
	 *
	 * @return the default use original colors
	 */
	protected boolean getDefaultUseOriginalColors() {
		return DEFAULT_USE_ORIGINAL_COLORS;
	}

	/**
	 * Gets the default has header.
	 *
	 * @return the default has header
	 */
	protected boolean getDefaultHasHeader() {
		return DEFAULT_HAS_HEADER;
	}
}
