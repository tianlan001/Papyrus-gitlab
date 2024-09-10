/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.Hashtable;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * this is the provider is charge to give the handler in order to have the
 * functionnality cut copy paste
 *
 */
public class PapyrusGlobalActionHandlerProvider extends AbstractGlobalActionHandlerProvider {

	/**
	 * List that contains all the IGlobalActionHandlers mapped to the
	 * IWorkbenchParts
	 */
	@SuppressWarnings("rawtypes")
	private Hashtable handlerList = new Hashtable();

	/**
	 * Constructor for DiagramGlobalActionHandlerProvider.
	 */
	public PapyrusGlobalActionHandlerProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.action.global.
	 * IGlobalActionHandlerProvider
	 * #getGlobalActionHandler(org.eclipse.gmf.runtime.common
	 * .ui.services.action.global.IGlobalActionHandlerContext)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IGlobalActionHandler getGlobalActionHandler(final IGlobalActionHandlerContext context) {
		/* create the handler */
		if (!getHandlerList().containsKey(context.getActivePart())) {
			getHandlerList().put(context.getActivePart(), new PapyrusDiagramGlobalActionHandler());
			/*
			 * register as a part listener so that the cache can be cleared when
			 * the part is disposed
			 */
			context.getActivePart().getSite().getPage().addPartListener(new IPartListener() {

				private IWorkbenchPart localPart = context.getActivePart();

				/**
				 * @see org.eclipse.ui.IPartListener#partActivated(IWorkbenchPart)
				 */
				@Override
				public void partActivated(IWorkbenchPart part) {
					// NULL implementation
				}

				/**
				 * @see org.eclipse.ui.IPartListener#partBroughtToTop(IWorkbenchPart)
				 */
				@Override
				public void partBroughtToTop(IWorkbenchPart part) {
					// NULL implementation
				}

				/**
				 * @see org.eclipse.ui.IPartListener#partClosed(IWorkbenchPart)
				 */
				@Override
				public void partClosed(IWorkbenchPart part) {
					/* remove the cache associated with the part */
					if (part != null && part == localPart && getHandlerList().containsKey(part)) {
						getHandlerList().remove(part);
						localPart.getSite().getPage().removePartListener(this);
						localPart = null;
					}
				}

				/**
				 * @see org.eclipse.ui.IPartListener#partDeactivated(IWorkbenchPart)
				 */
				@Override
				public void partDeactivated(IWorkbenchPart part) {
					// NULL implementation
				}

				/**
				 * @see org.eclipse.ui.IPartListener#partOpened(IWorkbenchPart)
				 */
				@Override
				public void partOpened(IWorkbenchPart part) {
					// NULL implementation
				}
			});
		}
		return (DiagramGlobalActionHandler) getHandlerList().get(context.getActivePart());
	}

	/**
	 * Returns the handlerList.
	 *
	 * @return Hashtable
	 */
	@SuppressWarnings("rawtypes")
	private Hashtable getHandlerList() {
		return handlerList;
	}
}
