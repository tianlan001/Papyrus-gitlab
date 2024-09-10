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

package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

public class RegionEditHelper extends ElementEditHelper {

	/**
	 * Replace container in request on {@link Package} if base element is {@link Region}
	 */
	@Override
	protected ICommand getCreateCommand(CreateElementRequest req) {
		CreateElementRequest request = isRegionCompartment(req) ? getCreateInPackageRequest(req) : req;
		return super.getCreateCommand(request);
	}

	protected boolean isRegionCompartment(CreateElementRequest req) {
		IElementType type = req.getElementType();
		return type.getEClass() != null && UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(type.getEClass());
	}

	protected CreateElementRequest getCreateInPackageRequest(CreateElementRequest baseReq) {
		CreateElementRequest req = new CreateElementRequest(baseReq.getEditingDomain(), baseReq.getElementType());
		req.addParameters(baseReq.getParameters());

		EReference feature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
		Region region = (Region) baseReq.getContainer();
		
		req.setContainer(region.getNearestPackage());
		req.setContainmentFeature(feature);
		
		return req;
	}
	
}
