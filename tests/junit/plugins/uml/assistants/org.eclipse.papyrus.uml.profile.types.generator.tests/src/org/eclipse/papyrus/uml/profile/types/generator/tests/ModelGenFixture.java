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
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.eclipse.papyrus.uml.profile.types.generator.tests.GenOption.INCREMENTAL;
import static org.eclipse.papyrus.uml.profile.types.generator.tests.GenOption.SUPPRESS_SEMANTIC_SUPERTYPE;
import static org.eclipse.uml2.common.util.UML2Util.getValidJavaIdentifier;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy;
import org.eclipse.papyrus.uml.profile.types.generator.ElementTypesGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.papyrus.uml.profile.types.generator.strategy.SimpleDeltaStrategy;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.hamcrest.CoreMatchers;
import org.junit.runner.Description;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * A test fixture for orchestration of model transformations and verification of transformation results.
 */
public class ModelGenFixture extends ResourceSetFixture {
	private static final String UML_ELEMENT_TYPES = "org.eclipse.papyrus.uml.service.types.UMLElementTypeSet";

	private static final String UMLDI_ELEMENT_TYPES = "org.eclipse.papyrus.umldi.service.types.UMLDIElementTypeSet";

	public final String prefix = "org.eclipse.papyrus.test";

	private String baseElementTypesSet = UML_ELEMENT_TYPES;

	private Set<GenOption> genOptions = EnumSet.noneOf(GenOption.class);

	public ModelGenFixture() {
		super();
	}

	public URI getElementTypesResourceURI() {
		URI inputURI = getModelResourceURI();
		String basename = inputURI.lastSegment();
		basename = basename.substring(0, basename.indexOf('.'));

		return inputURI.trimSegments(1).appendSegment(basename).appendFileExtension("elementtypesconfigurations");
	}

	public ElementTypeSetConfiguration getElementTypeSet() {
		Resource resource = getResourceSet().getResource(getElementTypesResourceURI(), true);
		return (ElementTypeSetConfiguration) resource.getContents().get(0);
	}

	static Predicate<NamedElement> named(final String name) {
		return new Predicate<>() {
			@Override
			public boolean apply(NamedElement input) {
				return Objects.equal(name, input.getName());
			}
		};
	}

	public Pair<Stereotype, org.eclipse.uml2.uml.Class> getMetaclassExtension(final String stereotypeName, final String metaclassName) {
		Pair<Stereotype, org.eclipse.uml2.uml.Class> result = null;
		Stereotype stereotype = Iterators.find(Iterators.filter(getModel().eAllContents(), Stereotype.class), named(stereotypeName));
		if (stereotype != null) {
			org.eclipse.uml2.uml.Class metaclass = Iterables.find(stereotype.getAllExtendedMetaclasses(), named(metaclassName));
			if (metaclass != null) {
				result = Pair.of(stereotype, metaclass);
			}
		}

		return result;
	}

	public <C extends ElementTypeConfiguration> C getElementTypeConfiguration(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, Class<C> type) {
		ElementTypeConfiguration result = getElementTypeConfiguration(metaclassExtension);
		assertThat("not a " + type.getSimpleName(), result, instanceOf(type));
		return type.cast(result);
	}

	public ElementTypeConfiguration getElementTypeConfiguration(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getElementTypeConfiguration(getElementTypeID(metaclassExtension, false));
	}

	public ElementTypeConfiguration getElementTypeConfiguration(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, String visualID) {
		return getElementTypeConfiguration(String.format("%s_%s", getElementTypeID(metaclassExtension, false), visualID));
	}

	protected String getElementTypeID(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, boolean initialLower) {
		String metaclassName = getValidJavaIdentifier(metaclassExtension.getValue().getName());
		if (initialLower) {
			metaclassName = StringExtensions.toFirstLower(metaclassName);
		}
		String stereotypeName = getValidJavaIdentifier(metaclassExtension.getKey().getName());
		if (initialLower) {
			stereotypeName = StringExtensions.toFirstLower(stereotypeName);
		}

		return (metaclassExtension.getKey().getExtendedMetaclasses().size() > 1)
				? String.format("%s.%s_%s", prefix, stereotypeName, metaclassName)
				: String.format("%s.%s", prefix, stereotypeName);
	}

	protected String getElementTypeID(org.eclipse.uml2.uml.Class metaclass) {
		String metaclassName = getValidJavaIdentifier(metaclass.getName());
		return baseElementTypesSet.equals(UML_ELEMENT_TYPES) ? "org.eclipse.papyrus.uml." + metaclassName
				: baseElementTypesSet.equals(UMLDI_ELEMENT_TYPES) ? "org.eclipse.papyrus.umldi." + metaclassName : baseElementTypesSet.replaceFirst("\\w+$", metaclassName);
	}

	public ElementTypeConfiguration getElementTypeConfiguration(String id) {
		ElementTypeConfiguration result = null;

		for (ElementTypeConfiguration next : getElementTypeSet().getElementTypeConfigurations()) {
			if (id.equals(next.getIdentifier())) {
				result = next;
				break;
			}
		}

		return result;
	}

	public <C extends ElementTypeConfiguration> List<C> getAllElementTypeConfigurations(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, Class<C> type) {
		List<ElementTypeConfiguration> result = getAllElementTypeConfigurations(metaclassExtension);
		assertThat("not " + type.getSimpleName(), result, everyItem(CoreMatchers.<ElementTypeConfiguration> instanceOf(type)));

		@SuppressWarnings("unchecked")
		List<C> resultAsC = (List<C>) result;
		return resultAsC;
	}

	public List<ElementTypeConfiguration> getAllElementTypeConfigurations(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAllElementTypeConfigurations(Pattern.compile(String.format("%s($|_\\w+)", Pattern.quote(getElementTypeID(metaclassExtension, false)))));
	}

	public List<ElementTypeConfiguration> getAllElementTypeConfigurations(Pattern idPattern) {
		List<ElementTypeConfiguration> result = Lists.newArrayListWithExpectedSize(3);

		for (ElementTypeConfiguration next : getElementTypeSet().getElementTypeConfigurations()) {
			if (idPattern.matcher(next.getIdentifier()).find()) {
				result.add(next);
			}
		}

		return result;
	}

	public SpecializationTypeConfiguration assertSpecializationType(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getElementTypeConfiguration(metaclassExtension, SpecializationTypeConfiguration.class);
	}

	public List<SpecializationTypeConfiguration> assertAllSpecializationTypes(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAllElementTypeConfigurations(metaclassExtension, SpecializationTypeConfiguration.class);
	}

	public StereotypeApplicationMatcherConfiguration assertStereotypeMatcher(SpecializationTypeConfiguration specializationType) {
		AbstractMatcherConfiguration result = specializationType.getMatcherConfiguration();
		assertThat("not a stereotype matcher", result, instanceOf(StereotypeApplicationMatcherConfiguration.class));
		return (StereotypeApplicationMatcherConfiguration) result;
	}

	public void assertNoStereotypeMatcher(SpecializationTypeConfiguration specializationType) {
		AbstractMatcherConfiguration matcher = specializationType.getMatcherConfiguration();
		assertThat("has a stereotype matcher", matcher, not(instanceOf(StereotypeApplicationMatcherConfiguration.class)));
	}

	public <C extends AbstractAdviceBindingConfiguration> C getAdviceBindingConfiguration(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, Class<C> type) {
		AbstractAdviceBindingConfiguration result = getAdviceBindingConfiguration(metaclassExtension);
		assertThat("not a " + type.getSimpleName(), result, instanceOf(type));
		return type.cast(result);
	}

	public AbstractAdviceBindingConfiguration getAdviceBindingConfiguration(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAdviceBindingConfiguration(getElementTypeID(metaclassExtension, true));
	}

	public AbstractAdviceBindingConfiguration getAdviceBindingConfiguration(String id) {
		AbstractAdviceBindingConfiguration result = null;

		for (AbstractAdviceBindingConfiguration next : getElementTypeSet().getAllAdviceBindings()) {
			if (id.equals(next.getIdentifier())) {
				result = next;
				break;
			}
		}

		return result;
	}

	public <C extends AbstractAdviceBindingConfiguration> List<C> getAllAdviceBindingConfigurations(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension, Class<C> type) {
		List<AbstractAdviceBindingConfiguration> result = getAllAdviceBindingConfigurations(metaclassExtension);
		assertThat("not " + type.getSimpleName(), result, everyItem(CoreMatchers.<AbstractAdviceBindingConfiguration> instanceOf(type)));

		@SuppressWarnings("unchecked")
		List<C> resultAsC = (List<C>) result;
		return resultAsC;
	}

	public List<AbstractAdviceBindingConfiguration> getAllAdviceBindingConfigurations(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAllAdviceBindingConfigurations(Pattern.compile(String.format("%s($|_\\w+)", Pattern.quote(getElementTypeID(metaclassExtension, true)))));
	}

	public List<AbstractAdviceBindingConfiguration> getAllAdviceBindingConfigurations(Pattern idPattern) {
		List<AbstractAdviceBindingConfiguration> result = Lists.newArrayListWithExpectedSize(3);

		for (AbstractAdviceBindingConfiguration next : getElementTypeSet().getAdviceBindingsConfigurations()) {
			if (idPattern.matcher(next.getIdentifier()).find()) {
				result.add(next);
			}
		}

		return result;
	}

	public StereotypeMatcherAdviceConfiguration assertStereotypeMatcherAdvice(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAdviceBindingConfiguration(metaclassExtension, StereotypeMatcherAdviceConfiguration.class);
	}

	public List<ApplyStereotypeAdviceConfiguration> assertAllApplyStereotypeAdvices(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		return getAllAdviceBindingConfigurations(metaclassExtension, ApplyStereotypeAdviceConfiguration.class);
	}

	public void assertNoApplyStereotypeAdvice(Pair<Stereotype, org.eclipse.uml2.uml.Class> metaclassExtension) {
		List<ApplyStereotypeAdviceConfiguration> advice = getAllAdviceBindingConfigurations(metaclassExtension, ApplyStereotypeAdviceConfiguration.class);
		assertThat("has apply-stereotype advice", advice, isEmpty());
	}

	/**
	 * Extends the inherited method to run the profile-to-elementtypes transformation.
	 *
	 * @see #generateElementTypesConfiguration()
	 */
	@Override
	protected void starting(Description description) {
		super.starting(description);

		// Get some configuration details
		BaseElementTypes basedOn = JUnitUtils.getAnnotation(description, BaseElementTypes.class);
		if (basedOn != null) {
			baseElementTypesSet = basedOn.value();
		}

		GenOptions options = JUnitUtils.getAnnotation(description, GenOptions.class);
		if (options != null) {
			genOptions = EnumSet.copyOf(Arrays.asList(options.value()));
		}

		// Ensure the registration of the element types that we need
		ElementTypeSetConfigurationRegistry.getInstance();

		generateModel();
	}

	private void generateModel() {
		Identifiers identifiers = new Identifiers();
		identifiers.setPrefix(prefix);
		identifiers.setBaseElementTypesSet(getBaseElementTypesSet());
		identifiers.setSuppressSemanticSuperElementTypes(genOptions.contains(SUPPRESS_SEMANTIC_SUPERTYPE));

		generateModel(identifiers);
	}

	protected void generateModel(Identifiers identifiers) {
		generateElementTypesConfiguration(identifiers);
	}

	protected String getBaseElementTypesSet() {
		return baseElementTypesSet;
	}

	private void generateElementTypesConfiguration(Identifiers identifiers) {
		IStatus status;
		if (genOptions.contains(INCREMENTAL)) {
			DeltaStrategy.Diff diff = new SimpleDeltaStrategy().findDiffs((Profile) getModel(), getElementTypeSet());
			ElementTypesGenerator elementTypesGenerator = new ElementTypesGenerator(identifiers, diff);
			status = elementTypesGenerator.generate(getModelURI(), getElementTypesResourceURI());
		} else {
			ElementTypesGenerator elementTypesGenerator = new ElementTypesGenerator(identifiers);
			status = elementTypesGenerator.generate(getModelURI(), getElementTypesResourceURI());
		}
		assertThat(status.getMessage(), status.getSeverity(), lessThan(IStatus.ERROR));
	}
}
