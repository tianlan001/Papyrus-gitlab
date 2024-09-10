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
package org.eclipse.papyrus.uml.diagram.deployment.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 *
 * @since 3.0
 */
public class DeploymentReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String ModelPackageableElementCompartmentEditPart_VISUAL_ID = "51";
	private static final String PackagePackageableElementCompartmentEditPart_VISUAL_ID = "38";
	private static final String DeviceCompositeCompartmentEditPart_VISUAL_ID = "17";
	private static final String ExecutionEnvironmentCompositeCompartmentEditPart_VISUAL_ID = "18";
	private static final String NodeCompositeCompartmentEditPart_VISUAL_ID = "19";
	private static final String ArtifactCompositeCompartmentEditPart_VISUAL_ID = "26";
	private static final String ModelPackageableElementCompartmentEditPartCN_VISUAL_ID = "52";
	private static final String PackagePackageableElementCompartmentEditPartCN_VISUAL_ID = "39";
	private static final String DeviceCompositeCompartmentEditPartCN_VISUAL_ID = "30";
	private static final String ExecutionEnvironmentCompositeCompartmentEditPartCN_VISUAL_ID = "31";
	private static final String NodeCompositeCompartmentEditPartCN_VISUAL_ID = "32";
	private static final String ArtifactCompositeCompartmentEditPartCN_VISUAL_ID = "33";
	private static final String ArtifactCompositeCompartmentEditPartACN_VISUAL_ID = "34";

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			ModelPackageableElementCompartmentEditPart_VISUAL_ID,
			PackagePackageableElementCompartmentEditPart_VISUAL_ID,
			DeviceCompositeCompartmentEditPart_VISUAL_ID,
			ExecutionEnvironmentCompositeCompartmentEditPart_VISUAL_ID,
			NodeCompositeCompartmentEditPart_VISUAL_ID,
			ArtifactCompositeCompartmentEditPart_VISUAL_ID,
			ModelPackageableElementCompartmentEditPartCN_VISUAL_ID,
			PackagePackageableElementCompartmentEditPartCN_VISUAL_ID,
			DeviceCompositeCompartmentEditPartCN_VISUAL_ID,
			ExecutionEnvironmentCompositeCompartmentEditPartCN_VISUAL_ID,
			NodeCompositeCompartmentEditPartCN_VISUAL_ID,
			ArtifactCompositeCompartmentEditPartCN_VISUAL_ID,
			ArtifactCompositeCompartmentEditPartACN_VISUAL_ID);

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
