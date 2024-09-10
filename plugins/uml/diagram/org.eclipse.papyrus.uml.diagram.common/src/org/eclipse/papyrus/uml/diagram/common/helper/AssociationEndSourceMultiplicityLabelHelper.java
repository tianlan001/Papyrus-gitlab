package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Map;

public class AssociationEndSourceMultiplicityLabelHelper extends AssociationEndSourceLabelHelper {

	private static AssociationEndSourceMultiplicityLabelHelper labelHelper;

	public static AssociationEndSourceMultiplicityLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new AssociationEndSourceMultiplicityLabelHelper();
		}
		return labelHelper;
	}

	@Override
	protected void setupMasks(Map<String, String> masks) {
		// masks list is empty for the single multiplicity symbol
	}
}
