/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.creation.EcorePropertyEditorFactory;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Regression test cases for the {@link EcorePropertyEditorFactory} class.
 */
public class EcorePropertyEditorFactoryTest {

	public EcorePropertyEditorFactoryTest() {
		super();
	}

	@SuppressWarnings("serial")
	@Test
	public void createWithLocalFactory() {
		EcorePropertyEditorFactory editorFactory = new EcorePropertyEditorFactory(UMLPackage.Literals.NAMESPACE__OWNED_RULE) {
			@Override
			protected EClass chooseEClass(Control widget) {
				// Force the choice
				return UMLPackage.Literals.CONSTRAINT;
			}

			@Override
			protected Object createObject(Control widget, Object context, Object source) {
				// Don't show a dialog
				return source;
			}
		};

		ResourceSet rset = new ResourceSetImpl();
		try {
			Resource resource = rset.createResource(URI.createURI("bogus://test.uml"));
			org.eclipse.uml2.uml.Package package_ = UMLFactory.eINSTANCE.createPackage();
			resource.getContents().add(package_);

			List<EClass> created = new ArrayList<>();

			EFactory custom = (EFactory) Proxy.newProxyInstance(UMLPackage.class.getClassLoader(), new Class<?>[] { EFactory.class },
					(proxy, method, args) -> {
						switch (method.getName()) {
						case "create":
							EClass eClass = (EClass) args[0];
							created.add(eClass);
							return UMLFactory.eINSTANCE.create(eClass);
						default:
							return method.invoke(UMLFactory.eINSTANCE, args);
						}
					});
			rset.setPackageRegistry(new EPackageRegistryImpl(rset.getPackageRegistry()) {
				@Override
				protected EFactory delegatedGetEFactory(String nsURI) {
					return UMLPackage.eNS_URI.equals(nsURI) ? custom : super.delegatedGetEFactory(nsURI);
				}
			});
			editorFactory.createObject(null, package_);

			assertThat("Custom factory not invoked", created, hasItem(UMLPackage.Literals.CONSTRAINT));
		} finally {
			EMFHelper.unload(rset);
		}
	}
}
