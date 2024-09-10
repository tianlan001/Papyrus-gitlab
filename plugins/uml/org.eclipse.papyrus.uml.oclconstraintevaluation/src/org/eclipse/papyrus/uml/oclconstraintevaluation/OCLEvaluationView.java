/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.oclconstraintevaluation;



import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.internal.utilities.PivotUtilInternal;
import org.eclipse.ocl.pivot.uml.UMLOCL;
import org.eclipse.ocl.pivot.utilities.EnvironmentFactory;
import org.eclipse.ocl.pivot.utilities.MetamodelManager;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.OCLHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * this is console that display the result of a OCL Constraint. The pattern Singleton has been applied
 *
 */
public class OCLEvaluationView extends ViewPart {

	private Text textViewer;

	/**
	 * ID
	 */
	public static String ID = "org.eclipse.papyrus.uml.oclconstraintevaluation.OCLEvaluationView";

	protected MetamodelManager metamodelManager = null;

	/**
	 *
	 * Constructor.
	 *
	 */
	public OCLEvaluationView() {
		super();
	}

	@Override
	public void setFocus() {
		textViewer.setFocus();
	}

	@Override
	public void createPartControl(Composite parent) {
		textViewer = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textViewer.setEditable(false);
		textViewer.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	/**
	 * this method comes from the org.eclipse.ocl.examples.xtext.console.OCLConsolePage written by
	 *
	 * @param contextObject
	 * @return the metamodelManager
	 */
	protected MetamodelManager getMetamodelManager(EObject contextObject) {
		EnvironmentFactory environmentFactory = PivotUtilInternal.findEnvironmentFactory(contextObject);
		if (environmentFactory != null) {
			return environmentFactory.getMetamodelManager();
		}
		return UMLOCL.newInstance().getMetamodelManager();
	}



	/**
	 * allow to compute the constraint written in the string in the context of the EObject
	 *
	 * @param contextObject
	 *            the context of the constraint
	 * @param expression
	 *            the constraint
	 */
	public void compute(EObject contextObject, String expression) {
		// initialize the context of an evaluation of the OCL expression
		MetamodelManager metamodelManager = getMetamodelManager(contextObject);
		EnvironmentFactory environmentFactory = metamodelManager.getEnvironmentFactory();
		OCL ocl = OCL.newInstance(environmentFactory);
		org.eclipse.ocl.pivot.Class contextType = ocl.getContextType(contextObject);
		try {
			OCLHelper oclHelper = ocl.createOCLHelper(contextType);
			ExpressionInOCL createQuery = oclHelper.createQuery(expression);
			Object evaluate = ocl.evaluate(contextObject, createQuery);
			String print = evaluate.toString();

			// display the value
			textViewer.setText("value = " + print);
		} catch (Exception ex) {
			textViewer.setText("\nERROR " + ex);
		}

	}
}