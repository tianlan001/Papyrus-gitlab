/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.uml.modelexplorer.Activator;
import org.eclipse.papyrus.uml.modelexplorer.preferences.CustomizableLabelPreferences;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelStylersEnum;
import org.eclipse.papyrus.uml.tools.providers.CustomizableDelegatingItemLabelProvider;
import org.eclipse.uml2.uml.Element;

/**
 * A query to get the Name as StyledString. The label is styled with preferences.
 * @since 2.0
 */
public class GetComplexStyledName implements IJavaQuery2<Element, StyledString> {

	/** the label provider */
	private static final CustomizableDelegatingItemLabelProvider labelProvider = new CustomizableDelegatingItemLabelProvider();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 */
	public StyledString evaluate(final Element source, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		ParameterValue parameterValue = parameterValues.getParameterValueByName("eObject"); //$NON-NLS-1$
		if (parameterValue.getValue() instanceof EStructuralFeature) {
			return new StyledString(((EStructuralFeature) parameterValue.getValue()).getName());
		}

		labelProvider.setStylesList(getTypes(), getStylers());

		return labelProvider.getStyledText(source);
	}

	/**
	 * @return the types list to display from the preference store.
	 */
	protected List<String> getTypes() {
		String[] types = Activator.getDefault().getPreferenceStore().getString(CustomizableLabelPreferences.CUSTOMIZED_TYPES).split(" ");//$NON-NLS-1$
		List<String> typesList = Arrays.asList(types);
		return typesList;
	}

	/**
	 * @return the stylers list to display from the preference store.
	 */
	protected List<Styler> getStylers() {
		String[] styles = Activator.getDefault().getPreferenceStore().getString(CustomizableLabelPreferences.CUSTOMIZED_STYLES).split(" ");//$NON-NLS-1$
		List<Styler> stylesList = new ArrayList<>();
		for (int i = 0; i < styles.length; i++) {
			stylesList.add(LabelStylersEnum.getByLiteral(styles[i]).getStyler());
		}
		return stylesList;
	}

}
