package org.eclipse.papyrus.uml.extensionpoints.standard;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.uml.extensionpoints.IRegisteredItem;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for elements registered in the platform by Papyrus uml2 extension points
 */
public class RegisteredElementsLabelProvider extends LabelProvider {

	/**
	 * Creates a new instance of the class
	 */
	public RegisteredElementsLabelProvider() {
		super();
	}

	/**
	 * @{inheritedDoc
	 */
	@Override
	public Image getImage(Object element) {
		if (!(element instanceof IRegisteredItem)) {
			return super.getImage(element);
		}
		IRegisteredItem abstractExtensionPoint = (IRegisteredItem) element;
		return abstractExtensionPoint.getImage();
	}

	/**
	 * @{inheritedDoc
	 */
	@Override
	public String getText(Object element) {
		if (!(element instanceof IRegisteredItem)) {
			return super.getText(element);
		}
		IRegisteredItem abstractExtensionPoint = (IRegisteredItem) element;
		return abstractExtensionPoint.getName();
	}
}
