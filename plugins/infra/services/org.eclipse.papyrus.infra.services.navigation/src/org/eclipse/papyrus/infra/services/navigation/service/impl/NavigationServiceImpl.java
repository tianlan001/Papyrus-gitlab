/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.navigation.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.services.navigation.Activator;
import org.eclipse.papyrus.infra.services.navigation.provider.NavigationTargetProvider;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationContributor;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuContributor;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.widgets.util.NavigationTarget;

/**
 * Base implementation of the NavigationService. It is based on the
 * navigationContributor extension point.
 *
 * @author Camille Letavernier
 */
public class NavigationServiceImpl implements NavigationService {

	public static final String NAVIGATION_CONTRIBUTOR_EXTENSION_ID = Activator.PLUGIN_ID + ".navigationContributor";

	public static final String NAVIGATION_MENU_CONTRIBUTOR_EXTENSION_ID = Activator.PLUGIN_ID + ".navigationMenuContributor";

	public static final String NAVIGATION_MENU_EXTENSION_ID = Activator.PLUGIN_ID + ".navigationMenu";

	/**
	 * The isActive property suffix (For preferences)
	 */
	public static final String IS_ACTIVE_KEY = "isActive"; //$NON-NLS-1$

	protected ServicesRegistry registry;

	/**
	 * {@inheritDoc}
	 */
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// Nothing
		this.registry = servicesRegistry;
	}

	/**
	 * {@inheritDoc}
	 */
	public void startService() throws ServiceException {
		createNavigationContributors();
		createNavigationTargetProviders();
		createNavigationMenuContributors();
	}

	protected void createNavigationContributors() {
		navigationContributors = new LinkedList<NavigationContributorDescriptor>();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(NAVIGATION_CONTRIBUTOR_EXTENSION_ID);

		for (IConfigurationElement e : config) {
			if (!"contributor".equals(e.getName())) {
				continue;
			}
			try {
				Object instance = e.createExecutableExtension("contributor");
				if (instance instanceof NavigationContributor) {
					NavigationContributorDescriptor wrapper = new NavigationContributorDescriptor((NavigationContributor) instance);
					wrapper.setId(e.getAttribute("id"));
					wrapper.setLabel(e.getAttribute("label"));
					wrapper.setDescription(e.getAttribute("description"));
					wrapper.init();
					navigationContributors.add(wrapper);
				}
			} catch (Exception ex) {
				Activator.log.warn("Invalid navigation contribution from: " + e.getContributor());
			}
		}
	}

	protected void createNavigationMenuContributors() {
		navigationMenuContributors = new LinkedList<NavigationMenuContributorDescriptor>();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(NAVIGATION_MENU_CONTRIBUTOR_EXTENSION_ID);

		for (IConfigurationElement e : config) {
			if (!"contributor".equals(e.getName())) {
				continue;
			}
			try {
				Object instance = e.createExecutableExtension("contributor");
				if (instance instanceof NavigationMenuContributor) {
					NavigationMenuContributorDescriptor wrapper = new NavigationMenuContributorDescriptor((NavigationMenuContributor) instance);
					wrapper.setId(e.getAttribute("id"));
					wrapper.setLabel(e.getAttribute("label"));
					wrapper.setDescription(e.getAttribute("description"));
					wrapper.init();
					navigationMenuContributors.add(wrapper);
				}
			} catch (Exception ex) {
				Activator.log.warn("Invalid navigation contribution from: " + e.getContributor());
			}
		}
	}

	protected void createNavigationTargetProviders() {
		navigationTargetProviders = new TreeSet<NavigationTargetProvider>();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(NAVIGATION_CONTRIBUTOR_EXTENSION_ID);

		for (IConfigurationElement e : config) {
			if (!"target".equals(e.getName())) {
				continue;
			}
			try {
				Object instance = e.createExecutableExtension("navigationTargetProvider");
				if (instance instanceof NavigationTargetProvider) {
					NavigationTargetProviderDescriptor descriptor = new NavigationTargetProviderDescriptor((NavigationTargetProvider) instance);
					descriptor.setId(e.getAttribute("id"));
					descriptor.setLabel(e.getAttribute("label"));
					descriptor.setDescription(e.getAttribute("description"));
					descriptor.setOrder(Integer.parseInt(e.getAttribute("order")));
					navigationTargetProviders.add(descriptor);
				}
			} catch (Exception ex) {
				Activator.log.warn("Invalid navigation target contribution from: " + e.getContributor());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void disposeService() throws ServiceException {
		this.registry = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> navigableElements = new LinkedList<NavigableElement>();

		for (NavigationContributorDescriptor contributor : navigationContributors) {
			if (contributor.isActive()) {
				navigableElements.addAll(contributor.getNavigableElements(fromElement));
			}
		}

		return navigableElements;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<NavigationMenuButton> getButtons(Object fromElement) {
		List<NavigationMenuButton> buttons = new LinkedList<NavigationMenuButton>();

		for (NavigationMenuContributorDescriptor contributor : navigationMenuContributors) {
			if (contributor.isActive()) {
				buttons.addAll(contributor.getButtons(fromElement));
			}
		}

		return buttons;
	}

	public static class NavigationTargetProviderDescriptor implements NavigationTargetProvider, Comparable<NavigationTargetProviderDescriptor> {

		private NavigationTargetProvider provider;

		private int order;

		private String label;

		private String description;

		private String id;

		public NavigationTargetProviderDescriptor(NavigationTargetProvider provider) {
			this.provider = provider;
		}

		public NavigationTarget getNavigationTarget(ServicesRegistry registry) {
			return provider.getNavigationTarget(registry);
		}

		public int getOrder() {
			return order;
		}

		public String getLabel() {
			return label;
		}

		public String getDescription() {
			return description;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int compareTo(NavigationTargetProviderDescriptor o) {
			if (o == null) {
				return -1;
			}

			if (o.order == order) {
				return 0;
			}

			return o.order < order ? 1 : -1;
		}

	}

	public static class ContributorDescriptor {
		private String label;

		private String description;

		private String id;

		private final IPreferenceStore preferences;

		public ContributorDescriptor() {
			preferences = Activator.getDefault().getPreferenceStore();
		}

		public void init() {
			String isActiveKey = getIsActiveKey(this);
			preferences.setDefault(isActiveKey, true);
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isActive() { /* NavigationContributorWrapper strategy */
			String preferenceKey = getIsActiveKey(this);
			return preferences.getBoolean(preferenceKey);
		}

		public static String getIsActiveKey(ContributorDescriptor strategy) {
			return strategy.getId() + "." + IS_ACTIVE_KEY;
		}
	}

	public static class NavigationContributorDescriptor extends ContributorDescriptor implements NavigationContributor {
		private final NavigationContributor contributor;

		public NavigationContributorDescriptor(NavigationContributor contributor) {
			this.contributor = contributor;
		}

		public List<NavigableElement> getNavigableElements(Object fromElement) {
			if (isActive()) {
				return contributor.getNavigableElements(fromElement);
			} else {
				return Collections.emptyList();
			}
		}
	}

	public static class NavigationMenuContributorDescriptor extends ContributorDescriptor implements NavigationMenuContributor {
		private final NavigationMenuContributor contributor;

		public NavigationMenuContributorDescriptor(NavigationMenuContributor contributor) {
			this.contributor = contributor;
		}

		public List<NavigationMenuButton> getButtons(Object fromElement) {
			if (isActive()) {
				return contributor.getButtons(fromElement);
			} else {
				return Collections.emptyList();
			}
		}
	}

	private List<NavigationContributorDescriptor> navigationContributors;

	private Collection<NavigationTargetProvider> navigationTargetProviders;

	private List<NavigationMenuContributorDescriptor> navigationMenuContributors;

	public List<NavigationContributorDescriptor> getNavigationContributors() {
		return navigationContributors;
	}

	public Collection<NavigationTargetProvider> getNavigationTargetProviders() {
		return navigationTargetProviders;
	}

	public List<NavigationMenuContributorDescriptor> getNavigationMenuContributors() {
		return navigationMenuContributors;
	}

	/**
	 * {@inheritDoc}
	 */
	public NavigationMenu createNavigationList() {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(NAVIGATION_MENU_EXTENSION_ID);

		for (IConfigurationElement e : config) {
			if (!"menu".equals(e.getName())) {
				continue;
			}
			try {
				Object instance = e.createExecutableExtension("menu");
				if (instance instanceof NavigationMenu) {
					return (NavigationMenu) instance;
				}
			} catch (Exception ex) {
				Activator.log.error(ex);
				Activator.log.warn("Invalid navigation menu from: " + e.getContributor());
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void navigate(NavigableElement navigableElement) {
		if (registry == null) {
			throw new IllegalStateException("The navigation service is not initialized");
		}

		for (NavigationTargetProvider provider : getNavigationTargetProviders()) {
			NavigationTarget target = provider.getNavigationTarget(registry);

			if (target == null) {
				continue;
			}

			if (navigableElement.navigate(target)) {
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void navigate(Object element) {
		if (registry == null) {
			throw new IllegalStateException("The navigation service is not initialized");
		}

		for (NavigationTargetProvider provider : getNavigationTargetProviders()) {
			NavigationTarget target = provider.getNavigationTarget(registry);

			if (target == null) {
				continue;
			}

			if (target.revealElement(element)) {
				return;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void navigate(Object element, String providerId) {
		if (registry == null) {
			throw new IllegalStateException("The navigation service is not initialized");
		}

		for (NavigationTargetProvider provider : getNavigationTargetProviders()) {
			if (((NavigationTargetProviderDescriptor) provider).getId().equals(providerId)) {
				NavigationTarget target = provider.getNavigationTarget(registry);

				if (target == null) {
					continue;
				}

				if (target.revealElement(element)) {
					return;
				}
			}
		}
	}
}
