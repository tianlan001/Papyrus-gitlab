/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;

/**
 * Configure command factory that set a {@link EStructuralFeature} of the new element.
 */
public class ConfigureFeatureCommandFactory extends AbstractConfigureCommandFactory {

	/** The feature to set */
	private EStructuralFeature feature;

	/** The feature value */
	private Object value;

	/** Constructor */
	public ConfigureFeatureCommandFactory(EStructuralFeature feature, Object value) {
		super();
		this.feature = feature;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigureElementCommand create(ConfigureRequest request) {

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

				EObject eObject = getElementToEdit();
				eObject.eSet(feature, value);

				return CommandResult.newOKCommandResult(eObject);
			}

		};
	}
}
