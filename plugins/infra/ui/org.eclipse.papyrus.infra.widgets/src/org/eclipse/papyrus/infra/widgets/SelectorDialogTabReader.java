/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net -  Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.swt.graphics.Image;

/**
 * The SelectorDialogTabReader class. It contains all selectorDialogTab contribution.
 * 
 * @since 3.0
 *
 */
public class SelectorDialogTabReader extends RegistryReader {

	/** The name of the constraint attribute. */
	private static final String CONSTRAINT_ATTRIBUTE = "tabConstraint";

	/** The name of the content provider attribute. */
	private static final String CONTENT_PROVIDER_ATTRIBUTE = "contentProvider"; //$NON-NLS-1$

	/** The name of the tab icon attribute. */
	private static final String TAB_ICON_ATTRIBUTE = "tabIcon"; //$NON-NLS-1$

	/** The name of the label provider attribute. */
	private static final String LABEL_PROVIDER_ATTRIBUTE = "labelProvider"; //$NON-NLS-1$

	/** The name of the cdescription attribute. */
	private static final String DESCRIPTION_ATTRIBUTE = "description"; //$NON-NLS-1$

	/** The name of the tab label attribute. */
	private static final String TAB_LABEL_ATTRIBUTE = "tabLabel"; //$NON-NLS-1$

	/** The name of the id attribute. */
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

	/** the instance */
	private static volatile SelectorDialogTabReader ourInstance = null;

	/** the extension point id of tab contribution to read */
	private static final String EXT_PT = "selectorDialogTab"; //$NON-NLS-1$

	/** Tab declaration id. */
	private static final String TAB_DECLARATION = "tabDeclaration"; //$NON-NLS-1$

	/** The list of ids. */
	private List<String> ids = new ArrayList<String>();

	/** The {@link Map} of tabulation names. The id is used as the key. */
	private Map<String, String> tabNames = new HashMap<String, String>();

	/** The {@link Map} of tabulation icons. The id is used as the key. */
	private Map<String, Image> tabIcons = new HashMap<String, Image>();

	/** The {@link Map} of descriptions. The id is used as the key. */
	private Map<String, String> descriptions = new HashMap<String, String>();

	/** The {@link Map} of tabulation label providers. The id is used as the key. */
	private Map<String, ILabelProvider> labelProviders = new HashMap<String, ILabelProvider>();

	/** The {@link Map} of tabulation content providers. The id is used as the key. */
	private Map<String, ITreeContentProvider> contentProviders = new HashMap<String, ITreeContentProvider>();

	/** The {@link Map} of tabulation constraints. The id is used as the key. */
	private Map<String, Constraint> constraints = new HashMap<String, Constraint>();


	/**
	 * @return the {@link Map} of label providers.
	 */
	public Map<String, ILabelProvider> getLabelProviders() {
		return labelProviders;
	}

	/**
	 * @return the {@link Map} of content providers.
	 */
	public Map<String, ITreeContentProvider> getContentProviders() {
		return contentProviders;
	}

	/**
	 * @return the {@link Map} of descriptions.
	 */
	public Map<String, String> getDescriptions() {
		return descriptions;
	}

	/**
	 * @return the {@link Map} of tabulation names
	 */
	public Map<String, String> getTabNames() {
		return tabNames;
	}

	/**
	 * @return the {@link Map} of tabulation icons.
	 */
	public Map<String, Image> getTabIcons() {
		return tabIcons;
	}

	/**
	 * Gets the instance.
	 * 
	 * @return
	 */
	public static final SelectorDialogTabReader getInstance() {
		if (ourInstance == null) {
			synchronized (SelectorDialogTabReader.class) {
				if (ourInstance == null) {
					ourInstance = new SelectorDialogTabReader();
				}
			}
		}
		return ourInstance;
	}

	/**
	 * Constructor.
	 */
	private SelectorDialogTabReader() {
		super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_PT);
		readRegistry();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.plugin.RegistryReader#readElement(org.eclipse.core.runtime.IConfigurationElement, boolean)
	 */
	@Override
	protected boolean readElement(final IConfigurationElement element, final boolean add) {
		if (!TAB_DECLARATION.equals(element.getName())) {
			return false;
		}

		String id = element.getAttribute(ID_ATTRIBUTE);
		if (!checkNotEmpty(id)) {
			logMissingAttribute(element, ID_ATTRIBUTE);
			return false;
		}

		if (add) {
			String tabName = element.getAttribute(TAB_LABEL_ATTRIBUTE);
			if (!checkNotEmpty(tabName)) {
				logMissingAttribute(element, TAB_LABEL_ATTRIBUTE);
				return false;
			}

			String description = element.getAttribute(DESCRIPTION_ATTRIBUTE);

			// load label provider
			String labelProviderName = element.getAttribute(LABEL_PROVIDER_ATTRIBUTE);
			if (!checkNotEmpty(labelProviderName)) {
				logMissingAttribute(element, LABEL_PROVIDER_ATTRIBUTE);
				return false;
			}

			String iconPath = element.getAttribute(TAB_ICON_ATTRIBUTE);
			Image icon = null;
			if (checkNotEmpty(iconPath)) {
				icon = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(element.getContributor().getName(), iconPath);
			}
			ILabelProvider labelProvider = null;

			try {
				labelProvider = (ILabelProvider) element.createExecutableExtension(LABEL_PROVIDER_ATTRIBUTE);
			} catch (CoreException e) {
				logError(element, "Failed to instantiate label provider"); //$NON-NLS-1$
				return false;
			}

			// load content provider
			String contentProviderName = element.getAttribute(CONTENT_PROVIDER_ATTRIBUTE);
			if (!checkNotEmpty(contentProviderName)) {
				logMissingAttribute(element, CONTENT_PROVIDER_ATTRIBUTE);
				return false;
			}

			ITreeContentProvider contentProvider = null;
			try {
				contentProvider = (ITreeContentProvider) element.createExecutableExtension(CONTENT_PROVIDER_ATTRIBUTE);
			} catch (CoreException e) {
				logError(element, "Failed to instantiate content provider"); //$NON-NLS-1$
				return false;
			}

			String tabConstraintName = element.getAttribute(CONSTRAINT_ATTRIBUTE);
			Constraint constraint = null;
			if (checkNotEmpty(tabConstraintName)) {
				try {
					constraint = (Constraint) element.createExecutableExtension(CONSTRAINT_ATTRIBUTE);
				} catch (CoreException e) {
					logError(element, "Failed to instantiate constraint"); //$NON-NLS-1$
					return false;
				}
			}

			addTabContribution(id, tabName, icon, description, labelProvider, contentProvider, constraint);
		} else

		{
			removeTabContribution(id);
		}

		return true;
	}

	/**
	 * Add a new tab contribution
	 * 
	 * @param tabName
	 *            The tabulation name.
	 * @param description
	 *            The description.
	 * @param labelProvider
	 *            The label provider.
	 * @param contentProvider
	 *            The content provider.
	 * @param constraint
	 *            The constraint.
	 */
	protected void addTabContribution(final String id, final String tabName, final Image icon, final String description, final ILabelProvider labelProvider, final ITreeContentProvider contentProvider, Constraint constraint) {
		ids.add(id);
		tabNames.put(id, tabName);

		if (null != icon) {
			tabIcons.put(id, icon);
		}

		if (null != description) {
			descriptions.put(id, description);
		}
		labelProviders.put(id, labelProvider);
		contentProviders.put(id, contentProvider);

		if (null != constraint) {
			getConstraints().put(id, constraint);
		}
	}

	/**
	 * Remove a tabulation contribution from the registry.
	 * 
	 * @param id
	 *            The id of the contribution to remove.(must not be null).
	 */
	protected void removeTabContribution(final String id) {
		tabNames.remove(id);
		descriptions.remove(id);
		labelProviders.remove(id);
		contentProviders.remove(id);
		ids.remove(id);
	}

	/**
	 * Check if the attribute is empty.
	 * 
	 * @param attribute
	 *            the attribute to check.
	 * @return true if not empty
	 */
	private static boolean checkNotEmpty(final String attribute) {
		return (attribute != null) && (attribute.length() != 0);
	}

	/**
	 * @return the list of tabulations ids.
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @return the constraints.
	 */
	public Map<String, Constraint> getConstraints() {
		return constraints;
	}

}
