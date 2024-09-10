/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.netT - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

/**
 * This Class is a Singleton that regroups all the Utilities method required in the TStereotype display test.
 * 
 * @author Céline JANSSENS
 *
 */
public class AppliedStereotypeDisplayTestUtils {


	/**
	 * singleton instance
	 */
	private static AppliedStereotypeDisplayTestUtils labelHelper;

	/** Singleton contructor */
	private AppliedStereotypeDisplayTestUtils() {
	}

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static AppliedStereotypeDisplayTestUtils getInstance() {
		if (labelHelper == null) {
			labelHelper = new AppliedStereotypeDisplayTestUtils();
		}
		return labelHelper;
	}

	/**
	 * Retrieve the list of all Label into a mainView.
	 * 
	 * @param mainView
	 *            The view on which the stereotype is applied.
	 * @return the list of all children of type StereotypeLabel
	 */
	public List<View> getAllLabel(View mainView) {
		List<View> labelList = new ArrayList<View>();

		for (Object object : mainView.getChildren()) {
			if (object instanceof View) {
				if (((View) object).getType().equals("StereotypeLabel")) {
					labelList.add((View) object);
				}
			}
		}

		return labelList;

	}


	/**
	 * Retrieve the list of all Brace Compartment into a mainView.
	 * 
	 * @param mainView
	 *            The view on which the stereotype is applied.
	 * @return the list of all children of type StereotypeBrace
	 */
	public List<View> getAllBraceCompartment(View mainView) {
		List<View> braceList = new ArrayList<View>();

		for (Object object : mainView.getChildren()) {
			if (object instanceof View) {
				if (((View) object).getType().equals("StereotypeBrace")) {
					braceList.add((View) object);
				}
			}
		}

		return braceList;

	}

	/**
	 * Retrieve the list of all Compartment into a mainView.
	 * 
	 * @param mainView
	 *            The view on which the stereotype is applied.
	 * @return the list of all children of type StereotypeCompartment"
	 */
	public List<View> getAllCompartment(View mainView) {
		List<View> compartmentList = new ArrayList<View>();

		for (Object object : mainView.getChildren()) {
			if (object instanceof View) {
				if (((View) object).getType().equals("StereotypeCompartment")) {
					compartmentList.add((View) object);
				}
			}
		}

		return compartmentList;

	}

	/**
	 * Retrieve all the Comment of a diagram.
	 * 
	 * @param diagram
	 *            The diagram
	 * @return list of all StereotypeComment View
	 * 
	 * 
	 */
	public List<View> getAllComment(Diagram diagram) {
		List<View> commentList = new ArrayList<View>();

		for (Object object : diagram.getChildren()) {
			if (object instanceof View) {
				if (((View) object).getType().equals("StereotypeComment")) {
					commentList.add((View) object);
				}
			}
		}


		return commentList;
	}

	/**
	 * Retrieve the list of all Comment related to a mainView.
	 * 
	 * @param mainView
	 *            The view on which the stereotype is applied.
	 * @return the list of all children of type StereotypeBrace
	 */
	public List<View> getAllComment(View mainView) {
		List<View> commentList = new ArrayList<View>();

		EObject element = mainView.getElement();
		Assert.assertTrue(element instanceof Element);

		Assert.assertTrue("Main view Container must be a View", mainView.eContainer() instanceof View);
		View container = (View) mainView.eContainer();

		for (Object object : container.getChildren()) {
			if (object instanceof View) {
				if (((View) object).getType().equals("StereotypeComment")) {
					EObject baseElement = NotationUtils.getEObjectValue((View) object, StereotypeDisplayConstant.STEREOTYPE_COMMENT_RELATION_NAME, null);
					if (baseElement instanceof Element && baseElement.equals(element)) {

						commentList.add((View) object);
					}

				}
			}
		}


		return commentList;

	}
}
