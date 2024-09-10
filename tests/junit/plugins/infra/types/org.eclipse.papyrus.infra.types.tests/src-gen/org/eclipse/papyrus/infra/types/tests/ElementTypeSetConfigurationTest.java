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

import junit.textui.TestRunner;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Element Type Set Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings() <em>All Advice Bindings</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ElementTypeSetConfigurationTest extends ConfigurationElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ElementTypeSetConfigurationTest.class);
	}

	/**
	 * Constructs a new Element Type Set Configuration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeSetConfigurationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Element Type Set Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ElementTypeSetConfiguration getFixture() {
		return (ElementTypeSetConfiguration)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ElementTypesConfigurationsFactory.eINSTANCE.createElementTypeSetConfiguration());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings() <em>All Advice Bindings</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings()
	 * @generated not
	 */
	public void testGetAllAdviceBindings() {
		AbstractAdviceBindingConfiguration advice1 = ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
		AbstractAdviceBindingConfiguration advice2 = ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
		AbstractAdviceBindingConfiguration advice3 = ElementTypesConfigurationsFactory.eINSTANCE.createAdviceBindingConfiguration();
		ElementTypeConfiguration type1 = ElementTypesConfigurationsFactory.eINSTANCE.createMetamodelTypeConfiguration();
		
		getFixture().getElementTypeConfigurations().add(type1);
		getFixture().getAdviceBindingsConfigurations().add(advice1);
		getFixture().getAdviceBindingsConfigurations().add(advice2);
		type1.getOwnedAdvice().add(advice3);
		
		EList<AbstractAdviceBindingConfiguration> advices = getFixture().getAllAdviceBindings();
		assertThat(advices, hasItems(advice1, advice2, advice3));
		assertThat(advices.size(), is(3));
	}

} //ElementTypeSetConfigurationTest
