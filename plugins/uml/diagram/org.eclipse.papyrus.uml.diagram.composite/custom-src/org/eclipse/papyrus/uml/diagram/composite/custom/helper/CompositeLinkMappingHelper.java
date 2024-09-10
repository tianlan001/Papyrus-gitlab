/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.helper;

import java.util.Collection;

import org.eclipse.papyrus.uml.diagram.common.helper.ILinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonSourceUMLSwitch;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonTargetUMLSwitch;
import org.eclipse.uml2.uml.Element;

/**
 * This helper is used to retrieve Link ends for use in drag and drop behavior
 */
public class CompositeLinkMappingHelper implements ILinkMappingHelper {

	/**
	 * Singleton management
	 */
	private static class SingletonHolder {

		/**
		 * Singleton instance
		 */
		private final static CompositeLinkMappingHelper instance = new CompositeLinkMappingHelper();
	}

	/**
	 * Get singleton instance
	 *
	 * @return the instance
	 */
	public static CompositeLinkMappingHelper getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * private constructor
	 */
	private CompositeLinkMappingHelper() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getSource(Element link) {
		return LinkMappingHelper.getSource(link, new CommonSourceUMLSwitch() {

			@Override
			public java.util.Collection<?> caseDependency(org.eclipse.uml2.uml.Dependency object) {
				return object.getSources();
			};

			@Override
			public java.util.Collection<?> caseConnector(org.eclipse.uml2.uml.Connector object) {
				return object.getEnds();
			};
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getTarget(Element link) {
		return LinkMappingHelper.getTarget(link, new CommonTargetUMLSwitch() {

			@Override
			public java.util.Collection<?> caseDependency(org.eclipse.uml2.uml.Dependency object) {
				return object.getTargets();
			};

			@Override
			public java.util.Collection<?> caseConnector(org.eclipse.uml2.uml.Connector object) {
				return object.getEnds();
			};
		});
	}

}
