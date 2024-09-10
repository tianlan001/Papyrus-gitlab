/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 490804
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.common.commands.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.Matcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A wrapper for a {@code ResourceSetFixture} that installs a custom UML Factory
 * in its resource set and provides assertions that test exercise of that factory.
 */
public class CustomUMLFactoryFixture implements TestRule {

	private final AbstractModelFixture<?> resourceSet;

	private final List<String> invocations = new ArrayList<>();

	public CustomUMLFactoryFixture(AbstractModelFixture<?> resourceSet) {
		super();

		this.resourceSet = resourceSet;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		Statement myStatement = new Statement() {

			@Override
			public void evaluate() throws Throwable {
				installUMLFactory();
				base.evaluate();
			}
		};

		return resourceSet.apply(myStatement, description);
	}

	public void assertInvocation(Matcher<? super String> matcher) {
		assertThat(invocations, hasItem(matcher));
	}

	public void assertInvocation(String methodName) {
		assertInvocation(is(methodName));
	}

	public void reset() {
		invocations.clear();
	}

	public IStatus execute(IUndoableOperation operation) {
		reset();
		return resourceSet.execute(operation);
	}

	public void execute(Command command) {
		reset();
		resourceSet.execute(command);
	}

	private void installUMLFactory() {
		// Install reflective proxies for the standard package and factory that
		// create reflective proxies for model elements
		ClassLoader cl = getClass().getClassLoader();

		EFactory[] eFactory = { null };
		EPackage[] ePackage = { null };

		ePackage[0] = (EPackage) Proxy.newProxyInstance(cl, new Class[] { UMLPackage.class },
				(__, method, args) -> {
					switch (method.getName()) {
					case "getEFactoryInstance":
					case "getUMLFactory":
						return eFactory[0];
					default:
						return method.invoke(UMLPackage.eINSTANCE, args);
					}
				});
		eFactory[0] = (EFactory) Proxy.newProxyInstance(cl, new Class[] { UMLFactory.class },
				(__, method, args) -> {
					switch (method.getName()) {
					case "getEPackage":
					case "getUMLPackage":
						return ePackage[0];
					case "create":
						EClass eClass = (EClass) args[0];
						EObject realObject = (EObject) method.invoke(UMLFactory.eINSTANCE, args);
						return Proxy.newProxyInstance(cl, new Class[] {
								eClass.getInstanceClass(),
								InternalEObject.class,
						}, (proxy, method1, args1) -> {
							recordInvocation(method1.getName());

							// Be careful to implement equals() and hashCode() as
							// expected by EMF
							switch (method1.getName()) {
							case "equals":
								if (args.length == 1) {
									return proxy == args1[0];
								}
								break;
							case "hashCode":
								if (args.length == 0) {
									return System.identityHashCode(proxy);
								}
								break;
							}
							return method1.invoke(realObject, args1);
						});
					default:
						return method.invoke(UMLFactory.eINSTANCE, args);
					}
				});

		EPackage.Registry registry = resourceSet.getResourceSet().getPackageRegistry();

		// Replace all registrations of the UML package
		for (Map.Entry<String, Object> next : registry.entrySet()) {
			if (next.getValue() instanceof UMLPackage) {
				next.setValue(ePackage[0]);
			}
		}

		// Just to make sure
		registry.put(ePackage[0].getNsURI(), ePackage[0]);
	}

	private void recordInvocation(String methodName) {
		invocations.add(methodName);
	}

}
