/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.emf.facet.custom.ui.internal.DecoratingCustomizedLabelProvider;
import org.eclipse.papyrus.emf.facet.custom.ui.internal.ResolvingCustomizedLabelProvider;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * The customization editor's label provider. Based on the EMF Facet
 * customizable label provider,
 *
 * @author Camille Letavernier
 */
public class ContextLabelProvider extends ResolvingCustomizedLabelProvider {

	/**
	 * Constructor.
	 */
	public ContextLabelProvider() {
		super(new DecoratingCustomizedLabelProvider(Activator.getDefault().getCustomizationManager()));
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof EObjectTreeElement) && element instanceof EObject) {
			try {
				// Method method = CustomizationManager.class.getDeclaredMethod("getAppearanceConfiguration");//$NON-NLS-1$
				// method.setAccessible(true);
				// AppearanceConfiguration result = (AppearanceConfiguration)method.invoke(Activator.getDefault().getCustomizationManager());
				// ITreeElement treeElement = new ModelElementItem((EObject)element, null, result);

				// Customized label provider can be applied directly on element non useful to create a EObjectTreeElement
				String text = super.getText(element);
				return text;
			} catch (SecurityException ex) {
				Activator.log.error(ex);
			}
			// catch (NoSuchMethodException ex) {
			// Activator.log.error(ex);
			// } catch (IllegalArgumentException ex) {
			// Activator.log.error(ex);
			// } catch (IllegalAccessException ex) {
			// Activator.log.error(ex);
			// } catch (InvocationTargetException ex) {
			// Activator.log.error(ex);
			// }
		}
		if (element != null) {
			return super.getText(element);
		} else {
			return "";
		}
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof EObjectTreeElement) && element instanceof EObject) {
			try {
				// Method method = CustomizationManager.class.getDeclaredMethod("getAppearanceConfiguration");//$NON-NLS-1$
				// method.setAccessible(true);
				// AppearanceConfiguration result = (AppearanceConfiguration)method.invoke(Activator.getDefault().getCustomizationManager());
				// ITreeElement treeElement = new ModelElementItem((EObject)element, null, result);

				// Customized label provider can be applied directly on element non useful to create a EObjectTreeElement
				Image image = super.getImage(element);
				return image;
			} catch (SecurityException ex) {
				Activator.log.error(ex);
			}
			// catch (NoSuchMethodException ex) {
			// Activator.log.error(ex);
			// } catch (IllegalArgumentException ex) {
			// Activator.log.error(ex);
			// } catch (IllegalAccessException ex) {
			// Activator.log.error(ex);
			// } catch (InvocationTargetException ex) {
			// Activator.log.error(ex);
			// }
		}
		return super.getImage(element);
	}

}
