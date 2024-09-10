/*
 * Copyright (c) 2014 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 451557
 *
 */
package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;


/**
 * This is the DeleteAction type. Enjoy.
 */
public class DeleteAction extends AbstractRepairAction {

	static final DeleteAction INSTANCE = new DeleteAction();

	private DeleteAction() {
		super(Kind.DELETE);
	}

	public boolean repair(Resource resource, EPackage profileDefinition, Collection<? extends EObject> stereotypeApplications, DiagnosticChain diagnostics, IProgressMonitor monitor) {
		monitor = SubMonitor.convert(monitor, "Deleting stereotype applications...", stereotypeApplications.size());

		for (EObject next : stereotypeApplications) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			destroy(next);
			monitor.worked(1);
		}

		return true;
	}

}
