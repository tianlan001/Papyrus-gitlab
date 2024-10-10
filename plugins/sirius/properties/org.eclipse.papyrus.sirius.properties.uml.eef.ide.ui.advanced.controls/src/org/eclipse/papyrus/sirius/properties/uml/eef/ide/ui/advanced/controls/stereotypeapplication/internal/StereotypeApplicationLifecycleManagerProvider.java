/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.stereotypeapplication.internal;

import org.eclipse.eef.EEFControlDescription;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManager;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManagerProvider;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.uml2.uml.Element;

/**
 * The lifecycle manager provider is used to create a lifecycle manager for the
 * stereotype application widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class StereotypeApplicationLifecycleManagerProvider implements IEEFLifecycleManagerProvider {

	@Override
	public boolean canHandle(EEFControlDescription controlDescription) {
		return controlDescription instanceof EEFStereotypeApplicationDescription;
	}

	@Override
	public IEEFLifecycleManager getLifecycleManager(EEFControlDescription controlDescription,
			IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		IEEFLifecycleManager lifecycleManager = null;
		if (controlDescription instanceof EEFStereotypeApplicationDescription) {
			Object selfVariable = variableManager.getVariables().get(EEFExpressionUtils.SELF);
			Element target = null;
			if (selfVariable instanceof Element) {
				target = (Element) selfVariable;
			}
			lifecycleManager = new StereotypeApplicationLifecycleManager((EEFStereotypeApplicationDescription) controlDescription, target, variableManager, interpreter,
					editingContextAdapter);
		}
		return lifecycleManager;
	}

}
