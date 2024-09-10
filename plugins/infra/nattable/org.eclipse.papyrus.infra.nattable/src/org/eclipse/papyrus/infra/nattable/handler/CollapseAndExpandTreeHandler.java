/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;

/**
 * @author Vincent Lorenzo
 *
 */
public class CollapseAndExpandTreeHandler extends AbstractParametricTreeTableHandler {

	/** the name of the parameter used by this handler */
	public static final String COLLAPSED_DEPTH_ARG_NAME = "treeAction"; //$NON-NLS-1$

	private CollapseAndExpandActionsEnum actionId;

	/**
	 * Constructor.
	 *
	 * @param parameterId
	 */
	public CollapseAndExpandTreeHandler() {
		super(COLLAPSED_DEPTH_ARG_NAME);
	}


	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param arg0
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		getTreeNattableModelManager().doCollapseExpandAction(this.actionId, null);
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = this.actionId != null && !getFullSelectedRowsIndex(evaluationContext).isEmpty();
		}
		return calculatedValue;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractParametricTreeTableHandler#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 *
	 * @param config
	 * @param propertyName
	 * @param data
	 * @throws CoreException
	 */
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		super.setInitializationData(config, propertyName, data);
		this.actionId = CollapseAndExpandActionsEnum.valueOf(getParameterValue());
	}
}
