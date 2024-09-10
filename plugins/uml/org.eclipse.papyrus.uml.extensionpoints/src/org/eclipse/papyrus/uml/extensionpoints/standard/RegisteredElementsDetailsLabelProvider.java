package org.eclipse.papyrus.uml.extensionpoints.standard;

import org.eclipse.papyrus.uml.extensionpoints.IRegisteredItem;

/**
 * Label provider for Registered elements, for details section in dialogs
 */
public class RegisteredElementsDetailsLabelProvider extends RegisteredElementsLabelProvider {

	/**
	 * @{inheritedDoc
	 */
	@Override
	public String getText(Object element) {
		if (!(element instanceof IRegisteredItem)) {
			return super.getText(element);
		}

		IRegisteredItem item = (IRegisteredItem) element;
		String text = "";
		text += item.getDescription();
		text += " - ";
		text += item.getProvider();
		return text;
	}
}
