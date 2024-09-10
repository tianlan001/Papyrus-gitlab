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

import junit.framework.TestCase;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.papyrus.infra.types.ConfigurationElement;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Configuration Element</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public abstract class ConfigurationElementTest extends TestCase {

	/**
	 * The fixture for this Configuration Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationElement fixture = null;

	/**
	 * Constructs a new Configuration Element test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationElementTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Configuration Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ConfigurationElement fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Configuration Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationElement getFixture() {
		return fixture;
	}

	protected static Matcher<Diagnostic> isError() {
		return hasSeverity(Diagnostic.ERROR);
	}

	protected static Matcher<Diagnostic> isWarning() {
		return hasSeverity(Diagnostic.WARNING);
	}
	
	protected static Matcher<Diagnostic> hasSeverity(int severity) {
		String severityName;
		
		switch (severity) {
		case Diagnostic.INFO:
			severityName = "info";
			break;
		case Diagnostic.WARNING:
			severityName = "warning";
			break;
		case Diagnostic.ERROR:
			severityName = "error";
			break;
		case Diagnostic.CANCEL:
			severityName = "cancel";
			break;
		default:
			severityName = "no";
			break;
		}
		
		return new CustomTypeSafeMatcher<Diagnostic>(String.format("has %s severity", severityName)) {
			@Override
			protected boolean matchesSafely(Diagnostic item) {
				return item.getSeverity() == severity;
			}
		};
	}
	
} //ConfigurationElementTest
