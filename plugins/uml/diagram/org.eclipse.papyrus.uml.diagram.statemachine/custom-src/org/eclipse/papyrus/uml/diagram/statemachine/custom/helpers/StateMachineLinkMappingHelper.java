/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers;

import java.util.Collection;

import org.eclipse.papyrus.uml.diagram.common.helper.ILinkMappingHelper;
import org.eclipse.uml2.uml.Element;

/**
 * The StateMAchineLinkMappingHelper is specialization of the link mapping
 * helper for the StateMachine diagram
 */

public class StateMachineLinkMappingHelper implements ILinkMappingHelper {

	/**
	 * SingletonHolder is loaded at the first execution of getInstance() method
	 */
	private static class SingletonHolder {

		private static final StateMachineLinkMappingHelper instance = new StateMachineLinkMappingHelper();
	}

	/**
	 * Gets the single instance of StateMachineLinkMappingHelper.
	 *
	 * @return single instance of StateMachineLinkMappingHelper
	 */
	public static StateMachineLinkMappingHelper getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * private constructor
	 */
	private StateMachineLinkMappingHelper() {
		// do nothing
	}

	@Override
	public Collection<?> getSource(Element link) {
		return null;
	}

	@Override
	public Collection<?> getTarget(Element link) {
		return null;
	}

}
