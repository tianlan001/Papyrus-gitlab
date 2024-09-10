/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.controlmode.updaters;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ProfileApplication;

/**
 * Common implementation of profile application updaters for loaded and unloaded models.
 */
public abstract class AbstractDecoratorModelUpdater implements IRefactoringStep {
	private final IDecoratorModelUpdaterDelegate delegate;

	public AbstractDecoratorModelUpdater(IDecoratorModelUpdaterDelegate delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public abstract boolean refactor(Resource resource, IProgressMonitor monitor) throws ExecutionException;

	@Override
	public abstract boolean unrefactor(Resource resource, IProgressMonitor monitor) throws ExecutionException;

	Package getDecoratorPackage(Resource resource) {
		return delegate.getDecoratorPackage(resource);
	}

	Package createDecoratorPackage(Resource resource) {
		return delegate.createDecoratorPackage(resource);
	}

	ProfileApplication getProfileApplication(Package package_, URI appliedProfileURI) {
		return delegate.getProfileApplication(package_, appliedProfileURI);
	}

	boolean hasProfileApplication(Package package_, URI appliedProfileURI) {
		return getProfileApplication(package_, appliedProfileURI) != null;
	}

	ProfileApplication addProfileApplication(Package package_, URI profileURI, URI appliedDefinitionURI) {
		return delegate.addProfileApplication(package_, profileURI, appliedDefinitionURI);
	}

}
