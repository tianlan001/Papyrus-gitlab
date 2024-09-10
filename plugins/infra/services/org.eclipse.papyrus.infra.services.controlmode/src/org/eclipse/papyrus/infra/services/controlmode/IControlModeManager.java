/*******************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 *     Christian W. Damus - bug 497865
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode;

import static org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.isCreateShard;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;

/**
 * A control manger is able to compute a command in order to control or uncontrol an element from a {@link ControlModeRequest}.
 *
 * @author adaussy
 *
 */
public interface IControlModeManager {

	/**
	 * Return the command to control an element.
	 *
	 * @param request
	 * @return
	 */
	public ICommand getControlCommand(ControlModeRequest request);


	/**
	 * Return the command to uncontrol an element
	 *
	 * @param request
	 * @return
	 */
	public ICommand getUncontrolCommand(ControlModeRequest request);

	/**
	 * Obtains a command to change only the 'shard' mode of an already
	 * controlled object.
	 *
	 * @param request
	 *            a request, which must be a
	 *            {@link ControlModeRequest#isControlRequest() control} request
	 *            it simply changes the sub-unit structure between
	 *            'sub-model' and 'shard' unit type
	 * 
	 * @return the command to change the 'shard' mode of an object
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code request} is of the wrong
	 *             kind or does not correctly specify the new 'shard' mode
	 *             to configure
	 * 
	 * @since 1.3
	 */
	public default ICommand getShardModeCommand(ControlModeRequest request) {
		// We don't know how to include participants in this

		if (!request.isControlRequest()) {
			throw new IllegalArgumentException("not a control request"); //$NON-NLS-1$
		}
		if (request.getParameter(ControlModeRequestParameters.CREATE_SHARD) == null) {
			throw new IllegalArgumentException("no shard mode parameter in request"); //$NON-NLS-1$
		}
		if (request.getTargetObject() == null) {
			throw new IllegalArgumentException("no target object in request"); //$NON-NLS-1$
		}

		Command baseCommand;
		EObject object = request.getTargetObject();

		try (ShardResourceHelper helper = new ShardResourceHelper(object)) {
			baseCommand = helper.getSetShardCommand(isCreateShard(request));
		}

		// Some components want to get affected files before executing, but the
		// affected-objects list of an AddCommand is null until it is executed
		// and this would cause an NPE in the inference of affected files
		return new EMFtoGMFCommandWrapper(baseCommand) {
			@SuppressWarnings("rawtypes")
			@Override
			public List getAffectedFiles() {
				return Arrays.asList(WorkspaceSynchronizer.getFile(object.eResource()));
			}
		};
	}

	/**
	 * Queries whether the given {@code request} is approved for processing
	 * to construct a command. Any {@code false} result vetoes the request.
	 * 
	 * @param request
	 *            a proposed control or uncontrol request
	 * 
	 * @return a diagnostic result according to the
	 *         {@link IControlCommandApprover#approveRequest(ControlModeRequest)} protocol
	 * 
	 * @since 1.3
	 */
	public default Diagnostic approveRequest(ControlModeRequest request) {
		return Diagnostic.OK_INSTANCE;
	}

	/**
	 * Queries whether the given object can be controlled as an independent sub-model unit.
	 * 
	 * @param objectToControl
	 *            an object to be controlled
	 * 
	 * @return whether an independent sub-model unit is supported for the object,
	 *         according to the {@link IControlCommandApprover#canCreateSubModel(EObject)} protocol
	 * 
	 * @since 1.3
	 */
	public default boolean canCreateSubmodel(EObject objectToControl) {
		return true;
	}
}
