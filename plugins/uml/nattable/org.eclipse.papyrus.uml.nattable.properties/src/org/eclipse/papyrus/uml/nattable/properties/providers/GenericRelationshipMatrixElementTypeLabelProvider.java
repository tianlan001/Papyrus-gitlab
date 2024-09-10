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
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.providers;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.uml.nattable.properties.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

/**
 * Custom Label provider for {@link ElementTypeConfiguration}.
 * This label provider has been created to hide that manipulated object are not UML Element or Profile
 *
 */
public class GenericRelationshipMatrixElementTypeLabelProvider implements ILabelProvider {

	/**
	 * some useful string
	 */
	// TODO name of elementTypeSet should use a common pattern
	private static final String UML = "UML"; //$NON-NLS-1$

	private static final String UML_TYPE_SET_NAME = "UMLElementTypeSet";//$NON-NLS-1$

	private static final String SYSML_TYPE_SET_NAME = "elementTypeSetSysML";//$NON-NLS-1$

	private static final String SYSML = "SysML"; //$NON-NLS-1$

	private static final String SYSML_14 = "SysML 1.4"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void addListener(final ILabelProviderListener listener) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {
		// nothing to do
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 *
	 * @param element
	 * @param property
	 * @return
	 */
	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		// nothing to do
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(final Object element) {
		// TODO find a better way to get the image instead of hard code
		URL url = null;
		if (element instanceof ElementTypeSetConfiguration) {
			final String name = ((ElementTypeSetConfiguration) element).getName();
			if (UML_TYPE_SET_NAME.equals(name)) {
				Bundle bundle = Platform.getBundle("org.eclipse.papyrus.uml.architecture"); //$NON-NLS-1$
				url = bundle.getEntry("icons/uml.gif"); //$NON-NLS-1$
			} else if (SYSML_TYPE_SET_NAME.equals(name)) {
				Bundle bundle = Platform.getBundle("org.eclipse.papyrus.sysml.architecture"); //$NON-NLS-1$
				url = bundle.getEntry("icons/sysml.gif"); //$NON-NLS-1$
			} else if (SYSML_14.equals(name) || SYSML.equals(name)) {
				// commented because not in 16x16 for SysML 1.4
				// Bundle bundle = Platform.getBundle("org.eclipse.papyrus.sysml14"); //$NON-NLS-1$
				// url = bundle.getEntry("resources/icons/SysML.gif"); //$NON-NLS-1$ //commented because not in 16x16
				Bundle bundle = Platform.getBundle("org.eclipse.papyrus.sysml.architecture"); //$NON-NLS-1$
				url = bundle.getEntry("icons/sysml.gif"); //$NON-NLS-1$

			}
		}
		if (element instanceof ElementTypeConfiguration) {
			url = getIconURL((ElementTypeConfiguration) element);
			if (null == url) {
				IElementType elementType = null;

				if (element instanceof MetamodelTypeConfiguration) {
					elementType = ElementTypeRegistry.getInstance().getType(((MetamodelTypeConfiguration) element).getIdentifier());
				} else if (element instanceof SpecializationTypeConfiguration) {
					elementType = ElementTypeRegistry.getInstance().getType(((SpecializationTypeConfiguration) element).getIdentifier());
				}
				if (null != elementType) {
					url = getIconURL(elementType);
				}
			}
		}
		Image im = null;
		if (null != url) {
			im = ExtendedImageRegistry.INSTANCE.getImage(url); // $NON-NLS-1$
			if (null == im) {
				Activator.log.warn(NLS.bind("The image located at {0} as not been found", url)); //$NON-NLS-1$
			}
		} else if (element instanceof ElementTypeConfiguration) {// we don't have a clean way currently to define icon for string excepted hard-coding
			Activator.log.warn(NLS.bind("No icon defined for ", element)); //$NON-NLS-1$
		}
		return im;
	}

	/**
	 * Duplicated code from org.eclipse.papyrus.infra.gmfdiag.common.providers.ElementTypeIconProvider
	 * 
	 * @param elementType
	 *            an element type
	 * @return
	 * 		the url of the icon to {@link Display} or <code>null</code> if not found
	 */
	private URL getIconURL(final IElementType elementType) {
		URL result = elementType.getIconURL();

		if ((result == null) && (elementType instanceof ISpecializationType)) {
			ISpecializationType subtype = (ISpecializationType) elementType;
			IElementType[] supertypes = subtype.getSpecializedTypes();
			if (supertypes != null) {
				for (int i = 0; (result == null) && (i < supertypes.length); i++) {
					result = getIconURL(supertypes[i]);
				}
			}
		}

		return result;
	}

	/**
	 * Duplicated code from AbstractElementTypeConfigurationFactory
	 * 
	 * @param elementTypeConfiguration
	 *            an element type configuration
	 * @return
	 * 		the url of the icon to use or <code>null</code> when not found
	 */
	private URL getIconURL(final ElementTypeConfiguration elementTypeConfiguration) {
		// icon associated to the elementType (GUI)
		IconEntry entry = elementTypeConfiguration.getIconEntry();
		URL iconURL = null;
		if (entry != null) {
			iconURL = getURLFromEntry(entry);
		}
		return iconURL;
	}

	/**
	 * Duplicated code from AbstractElementTypeConfigurationFactory
	 * 
	 * @param entry
	 *            an icon entry
	 * @return
	 * 		the url of the icon or <code>null</code> if not found
	 * 
	 */
	private URL getURLFromEntry(final IconEntry entry) {
		Bundle bundle = Platform.getBundle(entry.getBundleId());
		if (bundle == null) {
			Activator.log.warn(NLS.bind("Bundle {0} doesn't exist. I cannot found the expected icon {1}.", entry.getBundleId(), entry.getIconPath())); //$NON-NLS-1$
			return null;
		}
		URL result = bundle.getEntry(entry.getIconPath());
		if (result == null) {
			try {
				result = new URL(entry.getIconPath());
			} catch (MalformedURLException e) {
				Activator.log.error(NLS.bind("The icon path {0} seems invalid, I can't found this icon in the bundle {1}.", entry.getIconPath(), entry.getBundleId()), e); //$NON-NLS-1$
				result = null;
			}
		}
		return result;
	}


	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(final Object element) {
		String returnedName = ""; //$NON-NLS-1$
		if (null != element) {
			returnedName = element.toString();
			if (element instanceof ElementTypeSetConfiguration) {
				returnedName = ((ElementTypeSetConfiguration) element).getName();
			}
			if (element instanceof ElementTypeConfiguration) {
				returnedName = ProviderUtils.getNameToDisplay((ElementTypeConfiguration) element);
			}

			if (UML_TYPE_SET_NAME.equals(returnedName)) {
				returnedName = UML;
			}
			if (SYSML_TYPE_SET_NAME.equals(returnedName)) {
				returnedName = SYSML;
			}
		}
		return returnedName;
	}

}
