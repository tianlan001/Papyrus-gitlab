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

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;

/**
 * An optional participant protocol for approval of {@link ControlModeRequest}s
 * before an attempt is made to execute them.
 * 
 * @since 1.3
 */
public interface IControlCommandApprover extends IControlModeParticipant {
	/**
	 * Queries whether the given {@code request} is approved for processing
	 * to construct a command. Any {@linkplain Diagnostic#ERROR error} result
	 * vetoes the request.
	 * 
	 * @param request
	 *            a proposed control or uncontrol request
	 * 
	 * @return a diagnostic indicating approval ({@link Diagnostic#OK}) or
	 *         not (usually {@link Diagnostic#ERROR}). A {@link DiagnosticChain} may
	 *         be returned to report multiple problems
	 */
	default Diagnostic approveRequest(ControlModeRequest request) {
		return Diagnostic.OK_INSTANCE;
	}

	/**
	 * Queries whether it should be permitted to create a sub-model resource
	 * (independently openable) for the given object. If any approver returns
	 * {@code false}, then creating a sub-model is vetoed.
	 * 
	 * @param objectToControl
	 *            an object to be controlled
	 * 
	 * @return whether it may be created as a sub-model
	 */
	default boolean canCreateSubModel(EObject objectToControl) {
		return true;
	}
}
