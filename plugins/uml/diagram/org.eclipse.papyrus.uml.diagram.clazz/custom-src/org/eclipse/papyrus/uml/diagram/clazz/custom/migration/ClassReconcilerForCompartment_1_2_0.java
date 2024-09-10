/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - reconciler to add floating label
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.migration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.ReconcilerForCompartment;

/**
 * Diagram Reconciler for compartment from 1.1.0 to 1.2.0
 *
 * @since 3.0
 */
public class ClassReconcilerForCompartment_1_2_0 extends ReconcilerForCompartment {

	private static final String ClassAttributeCompartmentEditPart_VISUAL_ID = "7017"; //$NON-NLS-1$
	private static final String ClassOperationCompartmentEditPart_VISUAL_ID = "7018"; //$NON-NLS-1$
	private static final String ClassNestedClassifierCompartmentEditPart_VISUAL_ID = "7019"; //$NON-NLS-1$
	private static final String ComponentAttributeCompartmentEditPart_VISUAL_ID = "7002"; //$NON-NLS-1$
	private static final String ComponentOperationCompartmentEditPart_VISUAL_ID = "7003"; //$NON-NLS-1$
	private static final String ComponentNestedClassifierCompartmentEditPart_VISUAL_ID = "7004"; //$NON-NLS-1$
	private static final String SignalAttributeCompartmentEditPart_VISUAL_ID = "7005"; //$NON-NLS-1$
	private static final String InterfaceAttributeCompartmentEditPart_VISUAL_ID = "7006"; //$NON-NLS-1$
	private static final String InterfaceOperationCompartmentEditPart_VISUAL_ID = "7007"; //$NON-NLS-1$
	private static final String InterfaceNestedClassifierCompartmentEditPart_VISUAL_ID = "7008"; //$NON-NLS-1$
	private static final String PrimitiveTypeAttributeCompartmentEditPart_VISUAL_ID = "7039"; //$NON-NLS-1$
	private static final String PrimitiveTypeOperationCompartmentEditPart_VISUAL_ID = "7040"; //$NON-NLS-1$
	private static final String DataTypeAttributeCompartmentEditPart_VISUAL_ID = "7020"; //$NON-NLS-1$
	private static final String DataTypeOperationCompartmentEditPart_VISUAL_ID = "7021"; //$NON-NLS-1$
	private static final String ModelPackageableElementCompartmentEditPartTN_VISUAL_ID = "7009"; //$NON-NLS-1$
	private static final String PackagePackageableElementCompartmentEditPart_VISUAL_ID = "7016"; //$NON-NLS-1$
	private static final String EnumerationEnumerationLiteralCompartmentEditPart_VISUAL_ID = "7015"; //$NON-NLS-1$
	private static final String InstanceSpecificationSlotCompartmentEditPart_VISUAL_ID = "7001"; //$NON-NLS-1$
	private static final String AssociationClassAttributeCompartmentEditPart_VISUAL_ID = "7034"; //$NON-NLS-1$
	private static final String AssociationClassOperationCompartmentEditPart_VISUAL_ID = "7036"; //$NON-NLS-1$
	private static final String AssociationClassNestedClassifierCompartmentEditPart_VISUAL_ID = "7037"; //$NON-NLS-1$
	private static final String RedefinableTemplateSignatureTemplateParameterCompartmentEditPart_VISUAL_ID = "7014"; //$NON-NLS-1$
	private static final String TemplateSignatureTemplateParameterCompartmentEditPart_VISUAL_ID = "7038"; //$NON-NLS-1$
	private static final String ClassAttributeCompartmentEditPartCN_VISUAL_ID = "7011"; //$NON-NLS-1$
	private static final String ClassOperationCompartmentEditPartCN_VISUAL_ID = "7012"; //$NON-NLS-1$
	private static final String ClassNestedClassifierCompartmentEditPartCN_VISUAL_ID = "7013"; //$NON-NLS-1$
	private static final String ComponentAttributeCompartmentEditPartCN_VISUAL_ID = "7023"; //$NON-NLS-1$
	private static final String ComponentOperationCompartmentEditPartCN_VISUAL_ID = "7024"; //$NON-NLS-1$
	private static final String ComponentNestedClassifierCompartmentEditPartCN_VISUAL_ID = "7025"; //$NON-NLS-1$
	private static final String SignalAttributeCompartmentEditPartCN_VISUAL_ID = "7026"; //$NON-NLS-1$
	private static final String InterfaceAttributeCompartmentEditPartCN_VISUAL_ID = "7027"; //$NON-NLS-1$
	private static final String InterfaceOperationCompartmentEditPartCN_VISUAL_ID = "7028"; //$NON-NLS-1$
	private static final String InterfaceNestedClassifierCompartmentEditPartCN_VISUAL_ID = "7029"; //$NON-NLS-1$
	private static final String PrimitiveTypeAttributeCompartmentEditPartCN_VISUAL_ID = "7041"; //$NON-NLS-1$
	private static final String PrimitiveTypeOperationCompartmentEditPartCN_VISUAL_ID = "7042"; //$NON-NLS-1$
	private static final String DataTypeAttributeCompartmentEditPartCN_VISUAL_ID = "7032"; //$NON-NLS-1$
	private static final String DataTypeOperationCompartmentEditPartCN_VISUAL_ID = "7033"; //$NON-NLS-1$
	private static final String ModelPackageableElementCompartmentEditPartCN_VISUAL_ID = "7030"; //$NON-NLS-1$
	private static final String PackagePackageableElementCompartmentEditPartCN_VISUAL_ID = "7010"; //$NON-NLS-1$
	private static final String EnumerationEnumerationLiteralCompartmentEditPartCN_VISUAL_ID = "7031"; //$NON-NLS-1$
	private static final String InstanceSpecificationSlotCompartmentEditPartCN_VISUAL_ID = "7035"; //$NON-NLS-1$

	/** The compartments visual id. */
	private List<String> compartmentsVisualID = Arrays.asList(
			ClassAttributeCompartmentEditPart_VISUAL_ID,
			ClassOperationCompartmentEditPart_VISUAL_ID,
			ClassNestedClassifierCompartmentEditPart_VISUAL_ID,
			ComponentAttributeCompartmentEditPart_VISUAL_ID,
			ComponentOperationCompartmentEditPart_VISUAL_ID,
			ComponentNestedClassifierCompartmentEditPart_VISUAL_ID,
			SignalAttributeCompartmentEditPart_VISUAL_ID,
			InterfaceAttributeCompartmentEditPart_VISUAL_ID,
			InterfaceOperationCompartmentEditPart_VISUAL_ID,
			InterfaceNestedClassifierCompartmentEditPart_VISUAL_ID,
			PrimitiveTypeAttributeCompartmentEditPart_VISUAL_ID,
			PrimitiveTypeOperationCompartmentEditPart_VISUAL_ID,
			DataTypeAttributeCompartmentEditPart_VISUAL_ID,
			DataTypeOperationCompartmentEditPart_VISUAL_ID,
			ModelPackageableElementCompartmentEditPartTN_VISUAL_ID,
			PackagePackageableElementCompartmentEditPart_VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPart_VISUAL_ID,
			InstanceSpecificationSlotCompartmentEditPart_VISUAL_ID,
			AssociationClassAttributeCompartmentEditPart_VISUAL_ID,
			AssociationClassOperationCompartmentEditPart_VISUAL_ID,
			AssociationClassNestedClassifierCompartmentEditPart_VISUAL_ID,
			RedefinableTemplateSignatureTemplateParameterCompartmentEditPart_VISUAL_ID,
			TemplateSignatureTemplateParameterCompartmentEditPart_VISUAL_ID,
			ClassAttributeCompartmentEditPartCN_VISUAL_ID,
			ClassOperationCompartmentEditPartCN_VISUAL_ID,
			ClassNestedClassifierCompartmentEditPartCN_VISUAL_ID,
			ComponentAttributeCompartmentEditPartCN_VISUAL_ID,
			ComponentOperationCompartmentEditPartCN_VISUAL_ID,
			ComponentNestedClassifierCompartmentEditPartCN_VISUAL_ID,
			SignalAttributeCompartmentEditPartCN_VISUAL_ID,
			InterfaceAttributeCompartmentEditPartCN_VISUAL_ID,
			InterfaceOperationCompartmentEditPartCN_VISUAL_ID,
			InterfaceNestedClassifierCompartmentEditPartCN_VISUAL_ID,
			PrimitiveTypeAttributeCompartmentEditPartCN_VISUAL_ID,
			PrimitiveTypeOperationCompartmentEditPartCN_VISUAL_ID,
			DataTypeAttributeCompartmentEditPartCN_VISUAL_ID,
			DataTypeOperationCompartmentEditPartCN_VISUAL_ID,
			ModelPackageableElementCompartmentEditPartCN_VISUAL_ID,
			PackagePackageableElementCompartmentEditPartCN_VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPartCN_VISUAL_ID,
			InstanceSpecificationSlotCompartmentEditPartCN_VISUAL_ID);

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
