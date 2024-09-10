/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, Christian W. Damus, and others.
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

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture.at;
import static org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture.sized;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.StateInvariant;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests specifically for lifeline coverage of newly created interaction
 * fragments, per <a href="http://eclip.se/530201">bug 530201</a>.
 * 
 * @author Christian W. Damus
 * @see <a href="http://eclip.se/530201">bug 530201</a>
 */
@ActiveDiagram("sequence")
@PluginResource("resource/bugs/bug530201.di")
public class LifelineCoverageRegressionTest extends AbstractPapyrusTest {
	private static final Dimension DEFAULT_SIZE = null;

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	/**
	 * Initializes me.
	 */
	public LifelineCoverageRegressionTest() {
		super();
	}

	/**
	 * Verify the lifeline coverage of an {@link InteractionUse}.
	 */
	@Test
	public void createInteractionUse() {
		testScenario(UMLElementTypes.InteractionUse_Shape);
	}

	void testScenario(IElementType type) {
		testScenario(type, ep -> ep.getAdapter(InteractionFragment.class));
	}

	void testScenario(IElementType type, Function<? super EditPart, ? extends InteractionFragment> fragmentExtractor) {
		EditPart fragmentEP = editor.createShape(type, at(20, 100), sized(360, 60));

		InteractionFragment fragment = fragmentExtractor.apply(fragmentEP);
		assertThat(fragment, covers("a", "b")); // Not lifeline 'c'
	}

	/**
	 * Verify the lifeline coverage of a {@link CombinedFragment}.
	 */
	@Test
	public void createCombinedFragment() {
		testScenario(UMLElementTypes.CombinedFragment_Shape);
	}

	/**
	 * Verify the lifeline coverage of the initial {@link InteractionOperand}
	 * of a new {@link CombinedFragment}.
	 */
	@Test
	public void createCombinedFragment_operand() {
		testScenario(UMLElementTypes.CombinedFragment_Shape,
				ep -> ep.getAdapter(CombinedFragment.class).getOperands().get(0));
	}

	/**
	 * Verify the lifeline coverage of a {@link StateInvariant}. Note that the EMF-based
	 * UML2 API cannot effect the redefinition of {@link InteractionFragment#getCovereds() covered}
	 * as a single-valued property, so the tooling enforces single coverage.
	 */
	@Test
	public void createStateInvariant() {
		EditPart fragmentEP = editor.createShape(UMLElementTypes.StateInvariant_Shape,
				at(100, 100), DEFAULT_SIZE);

		InteractionFragment fragment = fragmentEP.getAdapter(InteractionFragment.class);
		assertThat(fragment, covers("a")); // Neither lifeline 'b' nor 'c'
	}

	//
	// Test framework
	//

	Matcher<InteractionFragment> covers(String... lifelineName) {
		Set<String> lifelineNames = new HashSet<>(Arrays.asList(lifelineName));

		return new FeatureMatcher<InteractionFragment, Set<String>>(equalTo(lifelineNames), "covered lifelines", "covers") {
			@Override
			protected Set<String> featureValueOf(InteractionFragment actual) {
				return actual.getCovereds().stream()
						.map(NamedElement::getName)
						.filter(Objects::nonNull)
						.collect(Collectors.toSet());
			}
		};
	}
}
