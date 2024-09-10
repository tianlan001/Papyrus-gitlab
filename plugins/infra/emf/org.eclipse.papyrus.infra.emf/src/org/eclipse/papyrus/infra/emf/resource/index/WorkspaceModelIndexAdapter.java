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

package org.eclipse.papyrus.infra.emf.resource.index;

/**
 * A convenience superclass for selective call-back implementation in {@link WorkspaceModelIndex} listeners.
 */
public class WorkspaceModelIndexAdapter implements IWorkspaceModelIndexListener {

	public WorkspaceModelIndexAdapter() {
		super();
	}

	@Override
	public void indexAboutToRecalculate(WorkspaceModelIndexEvent event) {
		indexAboutToCalculateOrRecalculate(event);
	}

	@Override
	public void indexRecalculated(WorkspaceModelIndexEvent event) {
		indexCalculatedOrRecalculated(event);
	}

	@Override
	public void indexAboutToCalculate(WorkspaceModelIndexEvent event) {
		indexAboutToCalculateOrRecalculate(event);
	}

	@Override
	public void indexCalculated(WorkspaceModelIndexEvent event) {
		indexCalculatedOrRecalculated(event);
	}

	@Override
	public void indexFailed(WorkspaceModelIndexEvent event) {
		// Pass
	}

	protected void indexAboutToCalculateOrRecalculate(WorkspaceModelIndexEvent event) {
		// Pass
	}

	protected void indexCalculatedOrRecalculated(WorkspaceModelIndexEvent event) {
		// Pass
	}

}
