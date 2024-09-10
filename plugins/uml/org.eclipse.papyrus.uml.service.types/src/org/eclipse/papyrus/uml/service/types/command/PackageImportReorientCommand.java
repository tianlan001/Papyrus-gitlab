/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 * 
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;

/**
 * <pre>
 * Re-orient command for {@link PackageImport} elements.
 * </pre>
 */
public class PackageImportReorientCommand extends AbstractDirectedRelationshipReorientCommand<PackageImport, Namespace, Package> {

	public PackageImportReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	protected boolean canReorientSource() {
		if (!(oldEnd instanceof Namespace && newEnd instanceof Namespace)) {
			return false;
		}
		if (!(getLink().eContainer() instanceof Namespace)) {
			return false;
		}
		return true;
	}

	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof Package && newEnd instanceof Package)) {
			return false;
		}
		if (!(getLink().eContainer() instanceof Package)) {
			return false;
		}
		return true;
	}

	protected CommandResult reorientSource() throws ExecutionException {
		getOldSource().getPackageImports().remove(getLink());
		getNewSource().getPackageImports().add(getLink());
		return CommandResult.newOKCommandResult(getLink());
	}

	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setImportedPackage(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}
}
