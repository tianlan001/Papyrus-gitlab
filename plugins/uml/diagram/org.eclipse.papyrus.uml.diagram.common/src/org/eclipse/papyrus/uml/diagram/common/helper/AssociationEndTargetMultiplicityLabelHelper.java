package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Map;

public class AssociationEndTargetMultiplicityLabelHelper extends AssociationEndTargetLabelHelper {

	private static AssociationEndTargetMultiplicityLabelHelper labelHelper;

	public static AssociationEndTargetMultiplicityLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new AssociationEndTargetMultiplicityLabelHelper();
		}
		return labelHelper;
	}

	@Override
	protected void setupMasks(Map<String, String> masks) {
		// masks list is empty for the single multiplicity symbol
	}
}
