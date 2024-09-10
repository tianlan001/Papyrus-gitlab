/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - bug 570486
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.tests;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureFramework;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for the Architecture models.
 */
public class ArchitectureMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private ArchitectureMatchers() {
		super();
	}

	public static Matcher<MergedADElement> mADLNamed(String name) {
		return named(MergedArchitectureDescriptionLanguage.class, name);
	}

	public static Matcher<MergedADElement> mAFNamed(String name) {
		return named(MergedArchitectureFramework.class, name);
	}

	public static Matcher<MergedADElement> mViewpointNamed(String name) {
		return named(MergedArchitectureViewpoint.class, name);
	}
	
	public static Matcher<ADElement> diagramNamed(String name) {
		return named(RepresentationPackage.Literals.PAPYRUS_DIAGRAM, name);
	}

	public static <T extends ADElement> Matcher<T> named(EClass eClass, String name) {
		return both(ArchitectureMatchers.<T> ofClass(eClass)).and(named(name));
	}

	public static <T extends ADElement> Matcher<T> ofClass(EClass eClass) {
		if (!ArchitecturePackage.Literals.AD_ELEMENT.isSuperTypeOf(eClass)) {
			throw new IllegalArgumentException("eClass does not conform to ADElement");
		}

		return new FeatureMatcher<T, EClass>(is(eClass), "eClass", "eClass") {
			@Override
			protected EClass featureValueOf(T actual) {
				return actual.eClass();
			}
		};
	}

	public static <T extends ADElement> Matcher<T> named(String name) {
		return new FeatureMatcher<T, String>(is(name), "name", "name") {
			@Override
			protected String featureValueOf(T actual) {
				return actual.getName();
			}
		};
	}

	public static <T extends MergedADElement> Matcher<T> named(Class<?> class_, String name) {
		return both(ArchitectureMatchers.<T> ofClass(class_)).and(mnamed(name));
	}

	public static <T extends MergedADElement>  Matcher<T> ofClass(Class<?> class_) {
		if (!MergedADElement.class.isAssignableFrom(class_)) {
			throw new IllegalArgumentException("class does not conform to MergedADElement");
		}

		return new FeatureMatcher<T, Class<?>>(sameInstance(class_), "class", "class") {
			@Override
			protected Class<?> featureValueOf(T actual) {
				return actual.getClass();
			}
		};
	}

	public static <T extends MergedADElement> Matcher<T> mnamed(String name) {
		return new FeatureMatcher<T, String>(is(name), "name", "name") {
			@Override
			protected String featureValueOf(T actual) {
				return actual.getName();
			}
		};
	}

}
