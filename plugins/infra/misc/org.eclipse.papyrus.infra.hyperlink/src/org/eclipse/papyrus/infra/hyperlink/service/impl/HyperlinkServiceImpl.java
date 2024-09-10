/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.hyperlink.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkService;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkConstants;

/**
 * @author Shuai Li
 *
 */
public class HyperlinkServiceImpl implements HyperlinkService {
	public static final String IS_ACTIVE_KEY = "isActive";
	
	protected ServicesRegistry registry;
	
	private List<HyperlinkContributorDescriptor> hyperlinkContributors;
	
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
		createHyperlinkContributors();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void disposeService() throws ServiceException {
		this.registry = null;
	}
	
	protected void createHyperlinkContributors() {
		hyperlinkContributors = new LinkedList<HyperlinkContributorDescriptor>();

		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(HyperLinkConstants.EXTENSION_ID);

		for (IConfigurationElement e : config) {
			if (!"contributor".equals(e.getName())) {
				continue;
			}
			try {
				Object instance = e.createExecutableExtension("contributor");
				if (instance instanceof HyperlinkContributor) {
					HyperlinkContributorDescriptor wrapper = new HyperlinkContributorDescriptor((HyperlinkContributor) instance);
					wrapper.setId(e.getAttribute("id"));
					wrapper.setLabel(e.getAttribute("label"));
					wrapper.setDescription(e.getAttribute("description"));
					wrapper.init();
					hyperlinkContributors.add(wrapper);
				}
			} catch (Exception ex) {
				Activator.log.warn("Invalid hyperlink contribution from: " + e.getContributor());
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		List<HyperLinkObject> hyperlinks = new LinkedList<HyperLinkObject>();
		HashSet<Object> addedHyperlinks = new HashSet<Object>();
		
		for (HyperlinkContributorDescriptor contributor : hyperlinkContributors) {
			if (contributor.isActive()) {
				List<HyperLinkObject> hyperlinkObjects = contributor.getHyperlinks(fromElement);
				
				for (HyperLinkObject hyperlink : hyperlinkObjects) {
					Object target = null;
					if (hyperlink instanceof HyperLinkSpecificObject) {
						target = ((HyperLinkSpecificObject) hyperlink).getTargetElement();
					} else {
						target = hyperlink.getObject();
					}
					
					if (!addedHyperlinks.contains(target)) {
						addedHyperlinks.add(target);
						hyperlinks.add(hyperlink);
					}
				}
			}
		}
		
		return hyperlinks;
	}
	
	public static abstract class ContributorDescriptor {
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
	
	public static class HyperlinkContributorDescriptor extends ContributorDescriptor implements HyperlinkContributor {
		private final HyperlinkContributor contributor;

		public HyperlinkContributorDescriptor(HyperlinkContributor contributor) {
			super();
			this.contributor = contributor;
		}

		public List<HyperLinkObject> getHyperlinks(Object fromElement) {
			if (isActive()) {
				return contributor.getHyperlinks(fromElement);
			} else {
				return Collections.emptyList();
			}
		}
	}
	
	public List<HyperlinkContributorDescriptor> getHyperlinkContributors() {
		return hyperlinkContributors;
	}
}
