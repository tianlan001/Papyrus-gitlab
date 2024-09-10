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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.stereotype.edition.provider;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayDiagramReconciler_1_2_0;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies.StereotypeEAnnotationLabelMigrationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies.StereotypeEAnnotationNestedMigrationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies.StereotypeEAnnotationPropertiesMigrationEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;

/**
 * @author Céline JANSSENS
 *
 * @deprecated Replaced by {@link StereotypeDisplayDiagramReconciler_1_2_0}
 */
@Deprecated
public class StereotypeDisplayMigrationEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {



	/**
	 * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 *
	 */
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
		if (!(epOperation.getEditPart() instanceof GraphicalEditPart) && !(epOperation.getEditPart() instanceof ConnectionEditPart)) {
			return false;
		}

		EditPart gep = epOperation.getEditPart();

		// Only the EditPart with an Stereotype EAnnotation into its View.
		try {
			if (ServiceUtilsForEditPart.getInstance().getServiceRegistry(gep) != null) {
				if (gep.getModel() instanceof View) {
					if (StereotypeMigrationHelper.getInstance().hasStereotypeEAnnotation((View) gep.getModel())) {
						if (UMLUtil.resolveUMLElement(gep) != null) {
							return true;
						}
					}
				}
			}
		} catch (ServiceException e) {
			return false;
		}

		return false;


	}



	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider#createEditPolicies(org.eclipse.gef.EditPart)
	 *
	 */
	public void createEditPolicies(EditPart editPart) {


		if (editPart.getModel() instanceof View) {
			if (StereotypeMigrationHelper.getInstance().hasStereotypeEAnnotation((View) editPart.getModel())) {

				// Main policy for Stereotype Label Migration

				if (UMLUtil.resolveUMLElement(editPart) != null) {
					editPart.installEditPolicy(StereotypeEAnnotationLabelMigrationEditPolicy.LABEL, new StereotypeEAnnotationLabelMigrationEditPolicy());
					if (!(editPart instanceof UMLCompartmentEditPart)) {
						editPart.installEditPolicy(StereotypeEAnnotationPropertiesMigrationEditPolicy.LABEL, new StereotypeEAnnotationPropertiesMigrationEditPolicy());
					}
				}


				// Policy for Stereotype Property and Label Migration of Element into Compartment (Property, Operation, Nested element, Enumeration Item,...)
				if (editPart instanceof UMLCompartmentEditPart) {
					if (UMLUtil.resolveUMLElement(editPart) != null) {
						editPart.installEditPolicy(StereotypeEAnnotationPropertiesMigrationEditPolicy.LABEL, new StereotypeEAnnotationNestedMigrationEditPolicy());
					}
				}

			}
		}

	}

}
