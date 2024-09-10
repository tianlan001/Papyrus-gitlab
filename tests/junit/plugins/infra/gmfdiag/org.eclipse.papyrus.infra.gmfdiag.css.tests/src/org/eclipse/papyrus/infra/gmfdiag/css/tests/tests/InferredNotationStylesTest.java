/*****************************************************************************
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
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.CanonicalStyle;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.Classifier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;

@PluginResource("resources/model/inferredNotationStylesTest/model.di")
public class InferredNotationStylesTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	private Diagram diagram;

	private View fooClass;

	private View unsynchronizedClass;

	private View synchronizedEnumeration;

	@Test
	public void canonicalStyleInferred() {
		assertCanonical(requireCanonicalStyle(fooClass));
		assertCanonical(requireCanonicalStyle(synchronizedEnumeration));
		assertNoncanonical(requireCanonicalStyle(unsynchronizedClass));
	}

	@Test
	public void canonicalStyleNotInferred() {
		refuseCanonicalStyle(diagram);
	}

	@Test
	public void overrideInferredStyle() {
		setCanonical(fooClass, false);
		setCanonical(unsynchronizedClass, true);

		assertNoncanonical(requireCanonicalStyle(fooClass));
		assertCanonical(requireCanonicalStyle(unsynchronizedClass));
	}

	//
	// Test framework
	//

	@Before
	public void gatherViews() throws Exception {
		NotationModel notation = (NotationModel) model.getResourceSet().getModel(NotationModel.MODEL_ID);
		diagram = notation.getDiagram("main");

		for (View next : Iterables.filter(diagram.getChildren(), View.class)) {
			if (next.getElement() instanceof Classifier) {
				Classifier classifier = (Classifier) next.getElement();
				switch (classifier.getName()) {
				case "Foo":
					fooClass = next;
					break;
				case "Unsynchronized":
					unsynchronizedClass = next;
					break;
				case "Synchronized":
					synchronizedEnumeration = next;
					break;
				default:
					// Pass
					break;
				}
			}
		}
	}

	CanonicalStyle requireCanonicalStyle(View view) {
		CanonicalStyle result = (CanonicalStyle) view.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
		assertThat("No canonical style is inferred", result, notNullValue());
		return result;
	}

	CanonicalStyle refuseCanonicalStyle(View view) {
		CanonicalStyle result = (CanonicalStyle) view.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
		if (result != null) {
			if (result.eContainer() == null) {
				fail("A canonical style is inferred");
			} else {
				fail("A canonical style is explicitly set");
			}
		}
		return result;
	}

	void assertCanonical(CanonicalStyle style) {
		assertThat("View is not canonical", style.isCanonical(), is(true));
	}

	void assertNoncanonical(CanonicalStyle style) {
		assertThat("View is canonical", style.isCanonical(), is(false));
	}

	void setCanonical(View view, boolean canonical) {
		final CanonicalStyle oldStyle = Iterables.getFirst(Iterables.filter(view.getStyles(), CanonicalStyle.class), null);

		EditingDomain domain = model.getEditingDomain();
		Command command = null;
		if ((oldStyle != null) && (oldStyle.eContainer() == view)) {
			command = SetCommand.create(domain, oldStyle, NotationPackage.Literals.CANONICAL_STYLE__CANONICAL, canonical);
		} else {
			// The force-value annotation must find the style already attached to the view when we set the style attribute
			final CanonicalStyle newStyle = NotationFactory.eINSTANCE.createCanonicalStyle();
			command = AddCommand.create(domain, view, NotationPackage.Literals.VIEW__STYLES, newStyle);
			command = command.chain(SetCommand.create(domain, newStyle, NotationPackage.Literals.CANONICAL_STYLE__CANONICAL, canonical));
		}
		domain.getCommandStack().execute(command);
	}
}
