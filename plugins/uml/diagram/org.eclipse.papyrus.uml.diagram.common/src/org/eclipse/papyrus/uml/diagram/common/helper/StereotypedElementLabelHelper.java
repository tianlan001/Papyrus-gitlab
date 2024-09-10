/*****************************************************************************
 * Copyright (c) 2009,2020 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - update getImage() : test if element is null
 *  Ibtihel KHEMIR (CEA LIST)  ibtihel.khemir@cea.fr - Bug 568492
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.uml.appearance.helper.AppliedStereotypeHelper;
import org.eclipse.papyrus.uml.appearance.helper.UMLVisualInformationPapyrusConstant;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Helper class for labels for elements that can have stereotypes
 */
public abstract class StereotypedElementLabelHelper {

	private final static String EMPTY_STRING = "";//$NON-NLS-1$

	private final static String SPACE = " "; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	public Element getUMLElement(GraphicalEditPart editPart) {
		return UMLUtil.resolveUMLElement(editPart);
	}

	/**
	 * Parses the string containing the complete definition of properties to be
	 * displayed, and generates a map.
	 *
	 * @param editPart
	 *            the edit part for which the label is edited
	 * @param stereotypesToDisplay
	 *            the list of stereotypes to display
	 * @param stereotypesPropertiesToDisplay
	 *            the properties of stereotypes to display
	 * @return a map. The keys are the name of displayed stereotypes, the
	 *         corresponding data is a collection of its properties to be
	 *         displayed
	 */
	protected Map<String, List<String>> parseStereotypeProperties(GraphicalEditPart editPart, String stereotypesToDisplay, String stereotypesPropertiesToDisplay) {
		Map<String, List<String>> propertiesMap = new HashMap<>();
		if (stereotypesPropertiesToDisplay != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(stereotypesPropertiesToDisplay, UMLVisualInformationPapyrusConstant.STEREOTYPE_PROPERTIES_LIST_SEPARATOR);
			while (stringTokenizer.hasMoreTokens()) {
				String propertyName = stringTokenizer.nextToken();
				// retrieve the name of the stereotype for this property
				String stereotypeName = propertyName.substring(0, propertyName.lastIndexOf(".")); // stereotypequalifiedName.propertyname //$NON-NLS-1$
				if (!propertiesMap.containsKey(stereotypeName)) {
					List<String> propertiesForStereotype = new ArrayList<>();
					propertiesMap.put(stereotypeName, propertiesForStereotype);
				}
				propertiesMap.get(stereotypeName).add(propertyName.substring(propertyName.lastIndexOf(".") + 1, propertyName.length())); //$NON-NLS-1$
			}
		}
		return propertiesMap;
	}

	/**
	 * Returns the image to be displayed for the applied stereotypes.
	 *
	 * @return the image that represents the first applied stereotype or <code>null</code> if no image has to be displayed
	 */
	public Collection<Image> stereotypeIconsToDisplay(GraphicalEditPart editPart) {
		String stereotypespresentationKind = AppliedStereotypeHelper.getAppliedStereotypePresentationKind((View) editPart.getModel());
		if (stereotypespresentationKind == null) {
			return null;
		}
		if (stereotypespresentationKind.equals(UMLVisualInformationPapyrusConstant.ICON_STEREOTYPE_PRESENTATION) || stereotypespresentationKind.equals(UMLVisualInformationPapyrusConstant.TEXT_ICON_STEREOTYPE_PRESENTATION)) {
			// retrieve the first stereotype in the list of displayed stereotype
			Collection<Stereotype> stereotypes = new ArrayList<>();
			Iterator<Stereotype> appliedStereotypes = getUMLElement(editPart).getAppliedStereotypes().iterator();
			while (appliedStereotypes.hasNext()) {
				Stereotype appliedStereotype = appliedStereotypes.next();
				if (editPart.getModel() instanceof View
						&& StereotypeDisplayUtil.getInstance().getStereotypeLabel(((View) editPart.getModel()), appliedStereotype).isVisible()) {
					stereotypes.add(appliedStereotype);
				}
			}
			return Activator.getIconElements(getUMLElement(editPart), stereotypes, false);
		}
		return new ArrayList<>();
	}

	/**
	 * get Stereotype String to display
	 *
	 * @return the list of stereotypes to display
	 */
	public String stereotypesToDisplay(GraphicalEditPart editPart) {
		View view = (View) editPart.getModel();
		// retrieve all stereotypes to be displayed
		// try to display stereotype properties
		String stereotypesToDisplay = StereotypeDisplayUtil.getInstance().getStereotypeTextToDisplay(view);
		String stereotypesPropertiesToDisplay = StereotypeDisplayUtil.getInstance().getStereotypePropertiesInBrace(view);


		String display = EMPTY_STRING;

		if (stereotypesToDisplay != null && !stereotypesToDisplay.equals(EMPTY_STRING)) {
			display += stereotypesToDisplay + StereotypeDisplayConstant.STEREOTYPE_PROPERTIES_SEPARATOR;
		}
		if (stereotypesPropertiesToDisplay != null && !stereotypesPropertiesToDisplay.equals(EMPTY_STRING)) {

			display += StereotypeDisplayConstant.BRACE_LEFT + stereotypesPropertiesToDisplay + StereotypeDisplayConstant.BRACE_RIGHT + SPACE;
		}

		return display;

	}


	/**
	 * Refreshes the label of the figure associated to the specified edit part
	 *
	 * @param editPart
	 *            the edit part managing the refreshed figure
	 */
	public void refreshEditPartDisplay(GraphicalEditPart editPart) {
		IFigure figure = editPart.getFigure();
		// computes the icon to be displayed
		final Collection<Image> imageToDisplay = stereotypeIconsToDisplay(editPart);
		// should check if edit part has to display the element icon or not
		if (AppearanceHelper.showElementIcon((View) editPart.getModel())) {
			imageToDisplay.add(getImage(editPart));
		}
		// for each element in the list of stereotype icon, adds it to the icons
		// list of the
		// wrapping label
		// problem (RS - CEA LIST): more icons were displayed before refresh:
		// has to clean
		// problem 2 (RS - CEA LIST): no method to know how many icons were
		// displayed => should fix
		// a max number ?!
		// solution: set all images to null, and then add the correct icons
		int i = 0;
		if (figure instanceof WrappingLabel) {
			while (((WrappingLabel) figure).getIcon(i) != null) {
				((WrappingLabel) figure).setIcon(null, i);
				i++;
			}
			i = 0;
			for (Image image : imageToDisplay) {
				((WrappingLabel) figure).setIcon(image, i);
				i++;
			}
			((WrappingLabel) figure).setText(labelToDisplay(editPart));
		}
	}

	/**
	 * Computes the label to be displayed for the property
	 */
	protected String labelToDisplay(GraphicalEditPart editPart) {
		StringBuffer buffer = new StringBuffer();
		// computes the label for the stereotype (horizontal presentation)
		buffer.append(stereotypesToDisplay(editPart));
		// computes the string label to be displayed
		buffer.append(elementLabel(editPart));
		// buffer.append(PropertyUtil.getCustomLabel(getUMLElement(), 0));
		return buffer.toString();
	}

	/**
	 * Computes the label corresponding to the semantic element
	 *
	 * @param editPart
	 *            the graphical part managing the semantic element
	 * @return the string corresponding to the display of the semantic element
	 */
	protected abstract String elementLabel(GraphicalEditPart editPart);

	/**
	 * Returns the image for the element
	 *
	 * @param editPart
	 *            the edit part that displays the element
	 * @return the image
	 */
	public Image getImage(GraphicalEditPart editPart) {
		Element element = getUMLElement(editPart);
		String key = null;
		if (element instanceof NamedElement) {
			StringBuilder builder = new StringBuilder();
			if (((NamedElement) element).getName() == null) {
				builder.append(element.getClass().getName());
			} else {
				builder.append(((NamedElement) element).getName());
			}
			builder.append(NamedElementUtil.QUALIFIED_NAME_SEPARATOR);
			builder.append(((NamedElement) element).getVisibility());
			key = builder.toString();
		} else if (element != null) {
			key = element.getClass().getName();
		} else {
			return null;
		}
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		Image image = imageRegistry.get(key);
		ImageDescriptor descriptor = null;
		if (image == null) {
			AdapterFactory factory = Activator.getDefault().getItemProvidersAdapterFactory();
			IItemLabelProvider labelProvider = (IItemLabelProvider) factory.adapt(getUMLElement(editPart), IItemLabelProvider.class);
			if (labelProvider != null) {
				descriptor = ExtendedImageRegistry.getInstance().getImageDescriptor(labelProvider.getImage(getUMLElement(editPart)));
			}
			if (descriptor == null) {
				descriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			imageRegistry.put(key, descriptor);
			image = imageRegistry.get(key);
		}
		return image;
	}
}
