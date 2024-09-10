/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.papyrus.infra.types.AdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Element Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedAdvice() <em>Owned Advice</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class ElementTypeConfigurationTest extends ConfigurationElementTest {

	/**
	 * Constructs a new Element Type Configuration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfigurationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Element Type Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ElementTypeConfiguration getFixture() {
		return (ElementTypeConfiguration)fixture;
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedAdvice() <em>Owned Advice</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedAdvice()
	 * @generated NOT
	 */
	public void testGetOwnedAdvice() {
		AdviceBindingConfiguration advice1 = ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
		AdviceBindingConfiguration advice2 = ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
		
		assertThat(getFixture().getOwnedAdvice().size(), is(0));
		
		// Add to the superset
		getFixture().getOwnedConfigurations().add(advice1);
		
		assertThat(getFixture().getOwnedAdvice(), is(List.of(advice1)));
		
		// Add to the subset
		getFixture().getOwnedAdvice().add(advice2);
		
		assertThat(getFixture().getOwnedAdvice(), is(List.of(advice1, advice2)));
	}

} //ElementTypeConfigurationTest
