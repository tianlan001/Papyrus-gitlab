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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.helper;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.papyrus.uml.diagram.common.helper.ILinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonSourceUMLSwitch;
import org.eclipse.papyrus.uml.diagram.common.helper.LinkMappingHelper.CommonTargetUMLSwitch;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ObjectFlow;

/**
 * Specialization of the link mapping helper for the Activity diagram
 */
public class ActivityLinkMappingHelper implements ILinkMappingHelper {

	/**
	 * SingletonHolder is loaded at the first execution of getInstance() method
	 */
	private static class SingletonHolder {

		private final static ActivityLinkMappingHelper instance = new ActivityLinkMappingHelper();
	}

	/**
	 * Gets the single instance of ClassLinkMappingHelper.
	 *
	 * @return single instance of ClassLinkMappingHelper
	 */
	public static ActivityLinkMappingHelper getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * private constructor
	 */
	private ActivityLinkMappingHelper() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getSource(Element link) {
		return LinkMappingHelper.getSource(link, new CommonSourceUMLSwitch() {

			@Override
			public Collection<?> caseActivityEdge(ActivityEdge object) {
				return Collections.singleton(object.getSource());
			}

			@Override
			public Collection<?> caseObjectFlow(ObjectFlow object) {
				return caseActivityEdge(object);
			}

			@Override
			public Collection<?> caseControlFlow(ControlFlow object) {
				return caseActivityEdge(object);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getTarget(Element link) {
		return LinkMappingHelper.getTarget(link, new CommonTargetUMLSwitch() {

			@Override
			public Collection<?> caseActivityEdge(ActivityEdge object) {
				return Collections.singleton(object.getTarget());
			}

			@Override
			public Collection<?> caseObjectFlow(ObjectFlow object) {
				return caseActivityEdge(object);
			}

			@Override
			public Collection<?> caseControlFlow(ControlFlow object) {
				return caseActivityEdge(object);
			}
		});
	}
}
