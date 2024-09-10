/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
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
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.controlmode;


/**
 * This interface will hold all specific parameter used to {@link ControlModeRequest}
 *
 * @author adaussy
 *
 */
public interface ControlModeRequestParameters {

	/**
	 * Parameter used to indicated that the action has been launch from UI
	 */
	public static String IS_UI_ACTION = "org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.IsUIAction"; //$NON-NLS-1$

	/**
	 * Base key for referencing a target resource into the request
	 */
	public static String TARGET_RESOURCE = "org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.TargetResource"; //$NON-NLS-1$

	/**
	 * Base key for referencing a source resource into the request
	 */
	public static String SOURCE_RESOURCE = "org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.SourceResource"; //$NON-NLS-1$

	/**
	 * Key used to store moved diagrams into the request
	 */
	public static String MOVED_OPENABLES = "org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.MovedOpenables"; //$NON-NLS-1$

	/**
	 * Key used to store the 'create a shard resource' option in the control request.
	 * The value is a boolean and its default is {@code false}.
	 * 
	 * @since 1.3
	 */
	public static String CREATE_SHARD = "org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.CreateShard"; //$NON-NLS-1$

	/**
	 * Queries whether a {@code request} is configured to create a 'shard' resource.
	 * 
	 * @param request
	 *            a control mode request
	 * 
	 * @return whether the {@code request} is a control request with the shard option
	 * 
	 * @since 1.3
	 * @see #CREATE_SHARD
	 */
	public static boolean isCreateShard(ControlModeRequest request) {
		return request.isControlRequest() && Boolean.TRUE.equals(request.getParameter(CREATE_SHARD));
	}
}
