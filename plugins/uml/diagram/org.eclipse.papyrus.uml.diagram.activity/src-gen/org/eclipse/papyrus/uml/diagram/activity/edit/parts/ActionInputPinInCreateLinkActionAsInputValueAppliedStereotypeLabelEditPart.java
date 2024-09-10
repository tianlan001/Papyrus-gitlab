/**
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.activity.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.parsers.ParserUtil;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editparts.AbstractWrappingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeExternalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AppliedStereotypeWrappingLabelFigure;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart extends AbstractWrappingLabelEditPart implements ITextAwareEditPart, IBorderItemEditPart {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "ActionInputPin_CreateLinkActionInputStereotypeLabel"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	static {
		registerSnapBackPosition(UMLVisualIDRegistry.getType(org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID), new Point(0, 0));
	}

	/**
	 * @generated
	 */
	public ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(AppliedStereotypeLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY, new AppliedStereotypeExternalNodeEditPolicy());
	}

	/**
	 * @generated
	 */
	@Override
	protected Image getLabelIcon() {
		// not use element icon
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser() {
		if (parser == null) {
			parser = ParserUtil.getParser(UMLElementTypes.ActionInputPin_CreateLinkActionInputShape, getParserElement(), this, VISUAL_ID);
		}
		return parser;
	}

	/**
	 * @generated
	 */
	@Override
	protected IFigure createFigure() {
		IFigure label = createFigurePrim();
		defaultText = getLabelTextHelper(label);
		return label;
	}

	/**
	 * @generated
	 */
	protected IFigure createFigurePrim() {
		return new AppliedStereotypeWrappingLabelFigure();
	}

}
