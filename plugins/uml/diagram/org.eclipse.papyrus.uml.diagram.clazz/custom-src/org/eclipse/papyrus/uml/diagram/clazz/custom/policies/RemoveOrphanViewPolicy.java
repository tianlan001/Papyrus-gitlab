/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelPackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelPackageableElementCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackagePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.RedefinableTemplateSignatureTemplateParameterCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TemplateSignatureTemplateParameterCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.OrphanViewPolicy;

/**
 * this policy is used to suppress orphan node view in GMF view the policy to remove orphan
 * connection is more complex. It is dependent of the diagram. see remove OrphanConnectionView
 * policy in clazzdiagram
 *
 * @deprecated since 3.1
 */
@Deprecated
public class RemoveOrphanViewPolicy extends OrphanViewPolicy {

	public String[] notOrphanNode = {
			AssociationClassAttributeCompartmentEditPart.VISUAL_ID,
			AssociationClassNestedClassifierCompartmentEditPart.VISUAL_ID,
			AssociationClassOperationCompartmentEditPart.VISUAL_ID,
			ClassAttributeCompartmentEditPart.VISUAL_ID,
			ClassAttributeCompartmentEditPartCN.VISUAL_ID,
			ClassNestedClassifierCompartmentEditPart.VISUAL_ID,
			ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID,
			ClassOperationCompartmentEditPart.VISUAL_ID,
			ClassOperationCompartmentEditPartCN.VISUAL_ID,
			ComponentAttributeCompartmentEditPart.VISUAL_ID,
			ComponentAttributeCompartmentEditPartCN.VISUAL_ID,
			ComponentNestedClassifierCompartmentEditPart.VISUAL_ID,
			ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID,
			ComponentOperationCompartmentEditPart.VISUAL_ID,
			ComponentOperationCompartmentEditPartCN.VISUAL_ID,
			DataTypeAttributeCompartmentEditPart.VISUAL_ID,
			DataTypeAttributeCompartmentEditPartCN.VISUAL_ID,
			DataTypeOperationCompartmentEditPart.VISUAL_ID,
			DataTypeOperationCompartmentEditPartCN.VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID,
			EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID,
			InstanceSpecificationSlotCompartmentEditPart.VISUAL_ID,
			InstanceSpecificationSlotCompartmentEditPartCN.VISUAL_ID,
			InterfaceAttributeCompartmentEditPart.VISUAL_ID,
			InterfaceAttributeCompartmentEditPartCN.VISUAL_ID,
			InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID,
			InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID,
			InterfaceOperationCompartmentEditPart.VISUAL_ID,
			InterfaceOperationCompartmentEditPartCN.VISUAL_ID,
			ModelPackageableElementCompartmentEditPartCN.VISUAL_ID,
			ModelPackageableElementCompartmentEditPartTN.VISUAL_ID,
			PackagePackageableElementCompartmentEditPart.VISUAL_ID,
			PackagePackageableElementCompartmentEditPartCN.VISUAL_ID,
			PrimitiveTypeAttributeCompartmentEditPart.VISUAL_ID,
			PrimitiveTypeAttributeCompartmentEditPartCN.VISUAL_ID,
			PrimitiveTypeOperationCompartmentEditPart.VISUAL_ID,
			PrimitiveTypeOperationCompartmentEditPartCN.VISUAL_ID,
			RedefinableTemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID,
			SignalAttributeCompartmentEditPart.VISUAL_ID,
			SignalAttributeCompartmentEditPartCN.VISUAL_ID,
			TemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID,
	};

	public RemoveOrphanViewPolicy() {
		super();
		init(notOrphanNode);
	}
}
