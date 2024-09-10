/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		IBM Corporation - initial API and implementation
 *   	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 424803
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.Hashtable;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Concrete class that implements the <code>IGlobalActionHandlerProvider</code>
 * providing <code>IGlobalActionHandler</code> for all diagram ui based
 * diagrams. This provider is installed with a lowest priority.
 * <p>
 * Returns
 * {@link org.eclipse.papyrus.uml.diagram.common.providers.DiagramWithPrintGlobalActionHandlerExtended}
 * when queried for global action handler.
 *
 * Customization of {@link org.eclipse.gmf.runtime.diagram.ui.printing.providers.DiagramWithPrintGlobalActionHandlerProvider}.
 * 
 * @since 3.0
 *
 */
public final class CustomDiagramWithPrintGlobalActionHandlerProvider extends AbstractGlobalActionHandlerProvider {

	private Hashtable handlerList = new Hashtable();

	/**
	 * Constructor.
	 */
	public CustomDiagramWithPrintGlobalActionHandlerProvider() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGlobalActionHandler getGlobalActionHandler(final IGlobalActionHandlerContext context) {
		/* Create the handler */
		if (!getHandlerList().containsKey(context.getActivePart())) {
			getHandlerList().put(context.getActivePart(),
					new DiagramWithPrintGlobalActionHandlerExtended());

			/*
			 * Register as a part listener so that the cache can be cleared when
			 * the part is disposed
			 */
			context.getActivePart().getSite().getPage().addPartListener(
					new IPartListener() {

						private IWorkbenchPart localPart = context.getActivePart();

						@Override
						public void partActivated(IWorkbenchPart part) {
							// Do nothing
						}

						@Override
						public void partBroughtToTop(IWorkbenchPart part) {
							// Do nothing
						}

						@Override
						public void partClosed(IWorkbenchPart part) {
							/* Remove the cache associated with the part */
							if (part != null && part == localPart && getHandlerList().containsKey(part)) {
								getHandlerList().remove(part);
								localPart.getSite().getPage().removePartListener(this);
								localPart = null;
							}
						}

						@Override
						public void partDeactivated(IWorkbenchPart part) {
							// Do nothing
						}

						@Override
						public void partOpened(IWorkbenchPart part) {
							// Do nothing
						}
					});
		}

		return (DiagramWithPrintGlobalActionHandlerExtended) getHandlerList().get(context.getActivePart());
	}

	/**
	 * Returns the handlerList.
	 *
	 * @return Hashtable.
	 */
	private Hashtable getHandlerList() {
		return handlerList;
	}
}
