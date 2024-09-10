/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.services.controlmode.participants;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;

/**
 * An optional participant protocol for contribution of ancillary changes
 * when the 'shard' mode of a sub-unit resource is changed.
 * 
 * @since 1.3
 */
public interface IShardModeCommandParticipant extends IControlModeParticipant {

	/**
	 * Queries whether I participate in processing of the given {@code request}.
	 *
	 * @param request
	 *            a request, which will always be a
	 *            {@link ControlModeRequest#isControlRequest() control} request by
	 *            by default because it simply changes the sub-unit structure between
	 *            'sub-model' and 'shard' unit type
	 * 
	 * @return whether I should be asked for commands to participate in the
	 *         shard conversion operation
	 */
	boolean providesShardModeCommand(ControlModeRequest request);

	/**
	 * Obtains a command to execute before the basic conversion of the
	 * 'shard' mode is performed.
	 *
	 * @param request
	 *            a request, which will always be a
	 *            {@link ControlModeRequest#isControlRequest() control} request by
	 *            by default because it simply changes the sub-unit structure between
	 *            'sub-model' and 'shard' unit type
	 * 
	 * @return a command, or {@code null} if none is needed
	 */
	default ICommand getPreShardModeCommand(ControlModeRequest request) {
		return null;
	}

	/**
	 * Obtains a command to execute after the basic conversion of the
	 * 'shard' mode is performed.
	 *
	 * @param request
	 *            a request, which will always be a
	 *            {@link ControlModeRequest#isControlRequest() control} request by
	 *            by default because it simply changes the sub-unit structure between
	 *            'sub-model' and 'shard' unit type
	 * 
	 * @return a command, or {@code null} if none is needed
	 */
	default ICommand getPostShardModeCommand(ControlModeRequest request) {
		return null;
	}
}
