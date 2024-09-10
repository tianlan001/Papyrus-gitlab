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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 */
public class UseCaseReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String UseCasePointsEditPartTN_VISUAL_ID = "7009";
	private static final String UseCasePointsInRectangleEditPart_VISUAL_ID = "7010";
	private static final String SubjectComponentUsecasesEditPart_VISUAL_ID = "7011";
	private static final String UseCasePointsInComponentEditPart_VISUAL_ID = "7012";
	private static final String ComponentUsecases2EditPart_VISUAL_ID = "7017";
	private static final String UseCasePointsInPackageEditPart_VISUAL_ID = "7014";
	private static final String ComponentUsecases3EditPart_VISUAL_ID = "7015";
	private static final String PackagePackageableElementCompartment2EditPart_VISUAL_ID = "7016";
	private static final String PackagePackageableElementCompartmentEditPart_VISUAL_ID = "7013";

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			UseCasePointsEditPartTN_VISUAL_ID,
			UseCasePointsInRectangleEditPart_VISUAL_ID,
			SubjectComponentUsecasesEditPart_VISUAL_ID,
			UseCasePointsInComponentEditPart_VISUAL_ID,
			ComponentUsecases2EditPart_VISUAL_ID,
			UseCasePointsInPackageEditPart_VISUAL_ID,
			ComponentUsecases3EditPart_VISUAL_ID,
			PackagePackageableElementCompartment2EditPart_VISUAL_ID,
			PackagePackageableElementCompartmentEditPart_VISUAL_ID);

	/**
	 * Gets the compartments visual id.
	 *
	 * @return the compartments visual id
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment#getCompartmentsVisualID()
	 */
	@Override
	public List<String> getCompartmentsVisualID() {
		return compartmentsVisualID;
	}

}
