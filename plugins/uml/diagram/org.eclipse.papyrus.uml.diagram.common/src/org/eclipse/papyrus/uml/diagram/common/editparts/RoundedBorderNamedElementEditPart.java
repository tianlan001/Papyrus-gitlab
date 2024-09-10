/*****************************************************************************
 * Copyright (c) 2010, 2015, 2021, 2022 CEA LIST, ARTAL and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Initial API and Implementation
 *   Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : generate less dead for common methods
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Bug 580838
 *****************************************************************************/


package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomIntStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableList;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.PapyrusRoundedEditPartHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PortPositionEnum;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AllowResizeAffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PortResizableEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;


/**
 * The Class RoundedBorderNamedElementEditPart.
 */
public abstract class RoundedBorderNamedElementEditPart extends BorderNamedElementEditPart implements NamedStyleProperties {

	/** The Constant DEFAULT_BORDER_STYLE. */
	private static final int DEFAULT_BORDER_STYLE = Graphics.LINE_SOLID;

	/** The Constant DEFAULT_CORNER_HEIGHT. */
	private static final int DEFAULT_CORNER_HEIGHT = 0;

	/** The Constant DEFAULT_CORNER_WIDTH. */
	private static final int DEFAULT_CORNER_WIDTH = 0;

	/** The Constant DEFAULT_CUTOM_DASH. */
	private static final int[] DEFAULT_CUTOM_DASH = new int[] { 5, 5 };

	/** The Constant DEFAULT_FLOATING_LABEL_OFFSET_HEIGHT. */
	private static final int DEFAULT_FLOATING_LABEL_OFFSET_HEIGHT = 0;

	/** The Constant DEFAULT_FLOATING_LABEL_OFFSET_WIDTH. */
	private static final int DEFAULT_FLOATING_LABEL_OFFSET_WIDTH = 0;

	/** The Constant DEFAULT_IS_FLOATING_LABEL_CONSTRAINED. */
	private static final boolean DEFAULT_IS_FLOATING_LABEL_CONSTRAINED = false;

	/** The Constant DEFAULT_IS_OVAL. */
	private static final boolean DEFAULT_IS_OVAL = false;

	/** The Constant DEFAULT_USE_ORIGINAL_COLORS. */
	private static final boolean DEFAULT_USE_ORIGINAL_COLORS = true;

	/** The Constant DEFAULT_HAS_HEADER. */
	private static final boolean DEFAULT_HAS_HEADER = false;

	/** The Constant DEFAULT_PORT_POSITION_VALUE. */
	private static final String DEFAULT_PORT_POSITION_VALUE = PortPositionEnum.ONLINE.toString();

	/** the namedStyle Listener */
	IChangeListener namedStyleListener = new IChangeListener() {

		@Override
		public void handleChange(ChangeEvent event) {
			refresh();

		}

	};

	/** The ovalObservable */
	private IObservableValue ovalObservable;

	/** The radius height Observable */
	private IObservableValue radiusObservableHeight;

	/** The radius width Observable */
	private IObservableValue radiusObservableWidth;

	/** The customDash Observable */
	private IObservableList customDashObservable;

	/** The shadowWidth Observable */
	private IObservableValue shadowWidthObservable;

	/** The shadowColor Observable */
	private IObservableValue shadowColorObservable;

	/** The port position Observable */
	private IObservableValue positionObservable;

	/** The border style observable. */
	private IObservableValue borderStyleObservable;

	/**
	 * Constructor.
	 *
	 * @param view
	 *            the view
	 */
	public RoundedBorderNamedElementEditPart(View view) {
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
		// edit policy for the resize behavior
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new PortResizableEditPolicy());
		installEditPolicy(AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE, new AllowResizeAffixedNodeAlignmentEditPolicy());
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
	 * Gets the default cutom dash.
	 *
	 * @return the default cutom dash
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
	 * Gets the default use original colors.
	 *
	 * @return the default use original colors
	 */
	private boolean getDefaultUseOriginalColors() {
		return DEFAULT_USE_ORIGINAL_COLORS;
	}

	/**
	 * Refresh visuals.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		PapyrusRoundedEditPartHelper.refreshRadius(this, getDefaultCornerWidth(), getDefaultCornerHeight());
		PapyrusRoundedEditPartHelper.refreshOval(this, getDefaultIsOvalValue());
		PapyrusRoundedEditPartHelper.refreshBorderStyle(this, getDefaultBorderStyle(), getDefaultCutomDash());
		PapyrusRoundedEditPartHelper.refreshShadowColor(this, getDefaultShadowColor());
		refreshPortPosition();
		super.refreshVisuals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getLineType() {
		return PapyrusRoundedEditPartHelper.getNotationBorderStyle(this, getDefaultBorderStyle());
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

		customDashObservable = new CustomStringStyleObservableList(view, domain, LINE_CUSTOM_VALUE);
		customDashObservable.addChangeListener(namedStyleListener);

		shadowWidthObservable = new CustomIntStyleObservableValue(view, domain, SHADOW_WIDTH);
		shadowWidthObservable.addChangeListener(namedStyleListener);

		shadowColorObservable = new CustomStringStyleObservableValue(view, domain, SHADOW_COLOR);
		shadowColorObservable.addChangeListener(namedStyleListener);

		positionObservable = new CustomStringStyleObservableValue(view, domain, PORT_POSITION);
		positionObservable.addChangeListener(namedStyleListener);

		borderStyleObservable = new CustomStringStyleObservableValue(view, domain, BORDER_STYLE);
		borderStyleObservable.addChangeListener(namedStyleListener);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#removeNotationalListeners()
	 *
	 */
	@Override
	protected void removeNotationalListeners() {
		super.removeNotationalListeners();
		radiusObservableWidth.dispose();
		radiusObservableHeight.dispose();
		ovalObservable.dispose();
		customDashObservable.dispose();
		shadowWidthObservable.dispose();
		shadowColorObservable.dispose();
		positionObservable.dispose();
		borderStyleObservable.dispose();
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
	 * Refresh the port position.
	 */
	private void refreshPortPosition() {
		if (getPrimaryShape() instanceof IRoundedRectangleFigure) {
			if (getModel() instanceof View) {
				if (getParent() instanceof AbstractBorderedShapeEditPart) {

					Object constraint = ((AbstractBorderedShapeEditPart) getParent()).getBorderedFigure().getBorderItemContainer().getLayoutManager().getConstraint(getFigure());

					if (constraint instanceof PortPositionLocator) {
						PortPositionLocator portLocator = (PortPositionLocator) constraint;
						String position = NotationUtils.getStringValue((View) getModel(), PORT_POSITION, getDefaultPortPosition());
						portLocator.setPortPosition(position);
					}
				}
			}
		}
	}

	/**
	 * get the default Port Position(can be inside, outside or onLine).
	 *
	 * @return the default port position
	 */
	protected String getDefaultPortPosition() {
		return DEFAULT_PORT_POSITION_VALUE;
	}

	/**
	 * Gets the default has header.
	 *
	 * @return the default has header
	 */
	protected boolean getDefaultHasHeader() {
		return DEFAULT_HAS_HEADER;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.BorderNamedElementEditPart#handleNotificationEvent(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);

		// relocate the figure in case of resize.
		Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getSize_Width().equals(feature) |
				NotationPackage.eINSTANCE.getSize_Height().equals(feature)) {
			final IBorderItemLocator locator = getBorderItemLocator();
			if (locator != null) {
				locator.relocate(getFigure());
			}
		}
	}

	/**
	 *
	 * @return the figure that represent the shape, this class is generated by the GMF tooling
	 */
	@Override
	protected abstract IFigure createNodePlate();

	/**
	 *
	 * @return the figure that represent the shape, this class is generated by the GMF tooling
	 */
	@Override
	protected abstract IFigure createNodeShape();

	/**
	 * this method installs the content pane in the node shape to add compartment for example
	 *
	 * @param nodeShape
	 * @return the figure that is the the node shape
	 */
	@Override
	protected abstract IFigure setupContentPane(IFigure nodeShape);

}
