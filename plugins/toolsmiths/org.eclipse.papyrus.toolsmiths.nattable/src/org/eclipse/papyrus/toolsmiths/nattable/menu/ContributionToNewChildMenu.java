/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.nattable.menu;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.ui.basic.impl.PartImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.PopupMenuImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.menus.SlaveMenuService;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.services.IServiceLocator;

/**
 *
 * This class allows to contribute to the new child menu, using some hack
 */
public class ContributionToNewChildMenu extends ExtensionContributionFactory {

	/**
	 * the id of the contributed command
	 */
	private static final String COMMAND_ID = "org.eclipse.papyrus.toolsmiths.nattable.registerTableConfigurationInAF"; //$NON-NLS-1$

	/**
	 * the icon used for the command
	 */
	private static final String COMMAND_ICON_URI = "platform:/plugin/org.eclipse.papyrus.toolsmiths.nattable/icons/PapyrusTable.gif"; //$NON-NLS-1$

	/**
	 * the file extension for the architecture file
	 */
	private static final String ARCHITECTURE_FILE_EXTENSION = "architecture"; //$NON-NLS-1$

	/**
	 *
	 * @see org.eclipse.ui.menus.AbstractContributionFactory#createContributionItems(org.eclipse.ui.services.IServiceLocator, org.eclipse.ui.menus.IContributionRoot)
	 *
	 * @param serviceLocator
	 * @param additions
	 */

	@Override
	public void createContributionItems(final IServiceLocator serviceLocator, final IContributionRoot additions) {
		if (false == mustContributeToMenu(serviceLocator)) {
			return;
		}
		final MApplication application = getApplication();
		if (null == application) {
			return;
		}

		final MCommand mmCommand = application.getCommand(COMMAND_ID);
		if (null == mmCommand) {
			return;
		}
		final MenuImpl menuImp = getMenuToContribute(serviceLocator);
		if (null == menuImp) {
			return;
		}

		final MHandledMenuItem item = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
		item.setLabel(Messages.ContributionToNewChildMenu_RegisterExitingTableConfigurationMenuItem);
		item.setCommand(mmCommand);

		item.setIconURI(COMMAND_ICON_URI);

		if (menuImp != null) {
			menuImp.getChildren().add(item);
		}
	}

	/**
	 *
	 * @param serviceLocator
	 *            the service locator
	 * @return
	 *         <code>true</code> if the menu must be contributed
	 */
	private boolean mustContributeToMenu(final IServiceLocator serviceLocator) {
		final ISelectionService serv = serviceLocator.getService(ISelectionService.class);
		if (null != serv && serv.getSelection() instanceof IStructuredSelection) {
			final Object first = ((IStructuredSelection) serv.getSelection()).getFirstElement();
			if (first instanceof ArchitectureDescriptionLanguage) {
				final ArchitectureDescriptionLanguage language = (ArchitectureDescriptionLanguage) first;
				if (ARCHITECTURE_FILE_EXTENSION.equals(language.eResource().getURI().fileExtension())) {
					return true;
				}
			}


		}
		return false;
	}

	/**
	 *
	 * @param serviceLocator
	 *            the service locator
	 * @return
	 *         the menu to contribute or <code>null</code> if not found
	 */
	private MenuImpl getMenuToContribute(final IServiceLocator serviceLocator) {
		IMenuService manager = serviceLocator.getService(IMenuService.class);
		if (manager instanceof SlaveMenuService) {
			org.eclipse.e4.ui.model.application.ui.basic.impl.PartImpl model = (PartImpl) ((SlaveMenuService) manager).getModel();
			List<MMenu> menus = model.getMenus();
			for (MMenu m : menus) {
				if (m instanceof PopupMenuImpl) {
					for (MMenuElement current : m.getChildren()) {
						if (null != current.getLabel()) {
							if (current instanceof MenuImpl && current.getLabel().equals("&New Child")) {// it probably don't work in case of internationalization usage //$NON-NLS-1$
								return (MenuImpl) current;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the current {@link MApplication} or <code>null</code> if not found
	 */
	private MApplication getApplication() {
		final IWorkbenchPart activePart = org.eclipse.papyrus.infra.ui.util.WorkbenchPartHelper.getCurrentActiveWorkbenchPart();
		if (null != activePart) {
			final IWorkbenchPartSite site = activePart.getSite();
			if (site instanceof PartSite) {
				final PartSite ps = (PartSite) activePart.getSite();
				if (null != ps) {
					IEclipseContext iec = ps.getContext();
					return iec.get(MApplication.class);
				}
			}
		}
		return null;
	}

}
