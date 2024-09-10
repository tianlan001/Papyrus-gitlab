/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) <patrick.tessier@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.example.custo.cyber;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.commands.IModelCreationCommand;
import org.eclipse.papyrus.uml.diagram.common.commands.ModelCreationCommandBase;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;

public class SOFTModelCreation extends ModelCreationCommandBase implements IModelCreationCommand {

	@Override
	protected EObject createRootElement() {
		return UMLFactory.eINSTANCE.createModel();
	}

	@Override
	protected void initializeModel(EObject owner) {
		super.initializeModel(owner);
		Profile myProfileTest = (Profile) PackageUtil.loadPackage(URI.createURI("pathmap://PROFILE_CYBER/soft.profile.uml"), owner.eResource().getResourceSet()); //$NON-NLS-1$
		if (myProfileTest != null) {
			PackageUtil.applyProfile((Package) owner, myProfileTest, true);
		}
	}
}

