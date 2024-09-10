/**
 *  Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.core.architecture.tests;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.papyrus.infra.core.architecture.ADElement;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>AD Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ADElement#getQualifiedName() <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class ADElementTest extends TestCase {

	/**
	 * The fixture for this AD Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ADElement fixture = null;

	/**
	 * Constructs a new AD Element test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ADElementTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this AD Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ADElement fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this AD Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ADElement getFixture() {
		return fixture;
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ADElement#getQualifiedName() <em>Qualified Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ADElement#getQualifiedName()
	 * @generated NOT
	 */
	public void testGetQualifiedName() {
		if (getFixture().eContainer() == null) {
			assertThat(getFixture().getQualifiedName(), is(getFixture().getName()));
		} else {
			assertThat(getFixture().getQualifiedName(), is(((ADElement) getFixture().eContainer()).getQualifiedName() + "::" + getFixture().getName()));
		}
	}
	
	protected Diagnostic validate() {
		return validate(getFixture());
	}
	
	protected Diagnostic validate(int constraintID) {
		return validate(getFixture(), constraintID);
	}
	
	protected static Diagnostic validate(ADElement element) {
		return validate(element, -1);
	}
	
	protected static Diagnostic validate(ADElement element, int constraintID) {
		Diagnostic result = new Diagnostician() {
			@Override
			public Map<Object, Object> createDefaultContext() {
				Map<Object, Object> result = super.createDefaultContext();
				SubstitutionLabelProvider delegateLabels = (SubstitutionLabelProvider) result.get(SubstitutionLabelProvider.class);
				
				result.put(SubstitutionLabelProvider.class, new SubstitutionLabelProvider() {
					
					@Override
					public String getObjectLabel(EObject eObject) {
						return eObject == element ?
								"self"
								: (eObject instanceof ADElement)
									? ((ADElement) eObject).getName()
									: delegateLabels.getObjectLabel(eObject);
					}
					
					@Override
					public String getValueLabel(EDataType eDataType, Object value) {
						return delegateLabels.getValueLabel(eDataType, value);
					}
					
					@Override
					public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
						return delegateLabels.getFeatureLabel(eStructuralFeature);
					}
				});
				result.put(Diagnostician.VALIDATE_RECURSIVELY, false);
				
				return result;
			}
		}.validate(element);
		
		if (constraintID >= 0) {
			result = result.getChildren().stream().filter(d -> d.getCode() == constraintID).findFirst().orElse(BasicDiagnostic.OK_INSTANCE);
		}
		
		return result;
	}

} //ADElementTest
