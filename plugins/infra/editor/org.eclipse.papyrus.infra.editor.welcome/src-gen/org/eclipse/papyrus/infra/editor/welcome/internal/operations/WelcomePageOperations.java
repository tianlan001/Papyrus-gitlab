/**
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.editor.welcome.internal.operations;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;
import org.eclipse.uml2.common.util.DerivedSubsetEObjectEList;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Page</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections() <em>Get Visible Sections</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String) <em>Get Section</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int) <em>Get Sash Column</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int) <em>Get Sash Row</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WelcomePageOperations {
	/**
	 * This must be updated whenever the corresponding constant in the
	 * WelcomePageImpl class is regenerated.
	 */
	protected static final int[] VISIBLE_SECTION_ESUPERSETS = new int[] { WelcomePackage.WELCOME_PAGE__SECTION };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomePageOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static EList<WelcomeSection> getVisibleSections(WelcomePage welcomePage) {
		return new VisibleSectionsList((InternalEObject) welcomePage,
				WelcomePackage.WELCOME_PAGE__VISIBLE_SECTION, VISIBLE_SECTION_ESUPERSETS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static WelcomeSection getSection(WelcomePage welcomePage, String identifier) {
		return welcomePage.getSections().stream()
				.filter(s -> s.isIdentifiedBy(identifier))
				.findFirst()
				.orElse(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static SashColumn getSashColumn(WelcomePage welcomePage, int index) {
		SashColumn result = null;
		EList<SashColumn> columns = welcomePage.getSashColumns();

		if ((index >= 0) && (index < columns.size())) {
			result = columns.get(index);
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static SashRow getSashRow(WelcomePage welcomePage, int column, int row) {
		SashColumn sashColumn = welcomePage.getSashColumn(column);
		return (sashColumn == null) ? null : sashColumn.getSashRow(row);
	}

	//
	// Nested types
	//

	private static class VisibleSectionsList extends DerivedSubsetEObjectEList<WelcomeSection> {

		protected VisibleSectionsList(InternalEObject owner, int featureID, int[] sourceFeatureIDs) {
			super(WelcomeSection.class, owner, featureID, sourceFeatureIDs);
		}

		@Override
		public List<WelcomeSection> basicList() {
			return new VisibleSectionsList(owner, featureID, sourceFeatureIDs) {

				@Override
				public ListIterator<WelcomeSection> listIterator(int index) {
					return basicListIterator(index);
				}
			};
		}

		@Override
		protected boolean isIncluded(EStructuralFeature feature) {
			// Require always isIncluded(Object)
			return false;
		}

		@Override
		protected boolean isIncluded(Object object) {
			return super.isIncluded(object) && !((WelcomeSection) object).isHidden();
		}

		@Override
		protected WelcomeSection validate(int index, WelcomeSection object) {
			WelcomeSection result = super.validate(index, object);

			if (object.isHidden()) {
				throw new IllegalArgumentException(String.valueOf(object));
			}

			return result;
		}

	}

} // WelcomePageOperations
