/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.stereotypeproperty;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IShapeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;

/**
 * @author flefevre
 *
 */
public class StereoUtil {
	
	View initialView;
	View viewAppliedStereotypeCompartmentNotation;
	View viewAppliedStereotypeLabelNotation;
	View viewAppliedStereotypeBraceNotation;
	View shapeCompartmentView;
	
	public StereoUtil(View initialView){
		this.initialView= initialView;
		for (int i = 0; i < initialView.getTransientChildren().size(); i++) {
			View view = (View) initialView.getTransientChildren().get(i);
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE)) {
				viewAppliedStereotypeCompartmentNotation = view;
			}
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE)) {
				viewAppliedStereotypeBraceNotation = view;
			}
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE)) {
				viewAppliedStereotypeLabelNotation = view;
			}
			if (view.getType().equals(IShapeCompartmentEditPart.VIEW_TYPE)) {
				shapeCompartmentView = view;
			}
		}
	}

	/**
	 * @return the viewAppliedStereotypeCompartmentNotation
	 */
	public View getViewAppliedStereotypeCompartmentNotation() {
		return viewAppliedStereotypeCompartmentNotation;
	}

	/**
	 * @return the viewAppliedStereotypeLabelNotation
	 */
	public View getViewAppliedStereotypeLabelNotation() {
		return viewAppliedStereotypeLabelNotation;
	}

	/**
	 * @return the viewAppliedStereotypeBraceNotation
	 */
	public View getViewAppliedStereotypeBraceNotation() {
		return viewAppliedStereotypeBraceNotation;
	}

	/**
	 * @return the shapeCompartmentView
	 */
	public View getShapeCompartmentView() {
		return shapeCompartmentView;
	}
	
	
	public static View getViewAppliedStereotypeCompartmentNotation2(View initialView){
		return getView(initialView, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);
	}
	
	public static View getViewAppliedStereotypeLabelNotation2(View initialView){
		return getView(initialView, StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE);
	}
	
	public static View getViewAppliedStereotypeBraceNotation2(View initialView){
		return getView(initialView, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
	}
	
	public static View getViewShapeCompartment2(View initialView){
		return getView(initialView, IShapeCompartmentEditPart.VIEW_TYPE);
	}
	
	private static View getView(View initialView, String constant){
		for (int i = 0; i < initialView.getTransientChildren().size(); i++) {
			View view = (View) initialView.getTransientChildren().get(i);
			if (view.getType().equals(constant)) {
				return view;
			}
		}
		return null;
	}
}
