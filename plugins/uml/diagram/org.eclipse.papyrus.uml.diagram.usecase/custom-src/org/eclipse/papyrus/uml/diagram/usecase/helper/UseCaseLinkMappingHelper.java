/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.helper;

import java.util.Collection;

import org.eclipse.papyrus.uml.diagram.common.helper.ILinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonSourceUMLSwitch;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonTargetUMLSwitch;
import org.eclipse.uml2.uml.Element;

/**
 * Specialization of the link mapping helper for the Use Case diagram
 *
 * @author eperico
 */
public class UseCaseLinkMappingHelper implements ILinkMappingHelper {

	/**
	 * SingletonHolder is loaded at the first execution of getInstance() method
	 */
	private static class SingletonHolder {

		private final static UseCaseLinkMappingHelper instance = new UseCaseLinkMappingHelper();
	}

	/**
	 * Gets the single instance of ClassLinkMappingHelper.
	 *
	 * @return single instance of ClassLinkMappingHelper
	 */
	public static UseCaseLinkMappingHelper getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * private constructor
	 */
	private UseCaseLinkMappingHelper() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getSource(Element link) {
		return LinkMappingHelper.getSource(link, new CommonSourceUMLSwitch() {

			@Override
			public java.util.Collection<?> caseInclude(org.eclipse.uml2.uml.Include object) {
				return object.getSources();
			};

			@Override
			public java.util.Collection<?> caseExtend(org.eclipse.uml2.uml.Extend object) {
				return object.getSources();
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
			public java.util.Collection<?> caseInclude(org.eclipse.uml2.uml.Include object) {
				return object.getTargets();
			};

			@Override
			public java.util.Collection<?> caseExtend(org.eclipse.uml2.uml.Extend object) {
				return object.getTargets();
			};
		});
	}
}
