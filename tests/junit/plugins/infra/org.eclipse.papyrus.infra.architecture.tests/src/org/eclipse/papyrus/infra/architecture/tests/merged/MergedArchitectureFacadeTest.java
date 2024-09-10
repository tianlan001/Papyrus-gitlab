/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.architecture.tests.merged;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFramework;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureFramework;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureSwitch;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Test suite for the merged architecture façade API: {@link MergedADElement} and its subclasses.
 */
@RunWith(Enclosed.class)
public abstract class MergedArchitectureFacadeTest {

	MergedArchitectureFacadeTest() {
		super();
	}

	//
	// Nested test classes
	//

	protected abstract static class ADElementTest<T extends MergedADElement, M extends ADElement> {

		private ResourceSet rset;

		protected T fixture;
		protected M model;

		protected ADElementTest() {
			super();
		}

		@Test
		public void getName() {
			assertThat("Wrong name in façade", fixture.getName(), is(model.getName()));
		}

		@Test
		public void getQualifiedName() {
			assertThat("Wrong qualified name in façade", fixture.getQualifiedName(), is(model.getQualifiedName()));
		}

		@Test
		public void getDescription() {
			assertThat("Wrong description in façade", fixture.getDescription(), is(model.getDescription()));
		}

		@Test
		public void getId() {
			assertThat("Wrong ID in façade", fixture.getId(), is(model.getId()));
		}

		@Test
		public void getIcon() {
			assertThat("Wrong icon in façade", fixture.getIcon(), is(model.getIcon()));
		}

		//
		// Test framework
		//

		@Before
		public final void loadDomain() {
			rset = new ResourceSetImpl();
			URL url = MergedArchitectureFacadeTest.class.getResource("domain1.architecture");
			URI uri = URI.createURI(url.toExternalForm(), true);
			Resource res = rset.getResource(uri, true);

			ArchitectureDomain domain = (ArchitectureDomain) res.getContents().get(0);
			MergedArchitectureDomain merged = facade(domain);
			initializeFixture(merged, domain);
		}

		private MergedArchitectureDomain facade(ArchitectureDomain domain) {
			MergedArchitectureDomain result = new MergedArchitectureDomain(domain);
			domain.getContexts().forEach(context -> {
				if (context instanceof ArchitectureFramework) {
					facade(result, (ArchitectureFramework) context);
				} else if (context instanceof ArchitectureDescriptionLanguage) {
					facade(result, (ArchitectureDescriptionLanguage) context);
				}
			});
			return result;
		}

		private MergedArchitectureFramework facade(MergedArchitectureDomain domain, ArchitectureFramework framework) {
			MergedArchitectureFramework result = new MergedArchitectureFramework(domain, framework);

			framework.getViewpoints().forEach(viewpoint -> facade(result, viewpoint));

			return result;
		}

		private MergedArchitectureDescriptionLanguage facade(MergedArchitectureDomain domain, ArchitectureDescriptionLanguage adl) {
			MergedArchitectureDescriptionLanguage result = new MergedArchitectureDescriptionLanguage(domain, adl);

			adl.getViewpoints().forEach(viewpoint -> facade(result, viewpoint));

			return result;
		}

		private MergedArchitectureViewpoint facade(MergedArchitectureContext context, ArchitectureViewpoint viewpoint) {
			MergedArchitectureViewpoint result = new MergedArchitectureViewpoint(context, viewpoint);
			return result;
		}

		@After
		public final void unloadDomain() {
			fixture = null;
			model = null;

			EMFHelper.unload(rset);
			rset = null;
		}

		@SuppressWarnings("unchecked")
		private void initializeFixture(MergedArchitectureDomain merged, ArchitectureDomain domain) {
			model = findModel(domain);

			fixture = (T) new MergedArchitectureSwitch<MergedADElement>() {

				@Override
				public MergedADElement caseADElement(MergedADElement object) {
					MergedADElement result = null;

					if (object.getAdapter(ADElement.class) == model) {
						result = object;
					}

					return result;
				}

				MergedADElement search(Collection<? extends MergedADElement> elements) {
					return elements.stream().map(this::doSwitch).filter(Objects::nonNull).findAny().orElse(null);
				}

				@Override
				public MergedADElement caseArchitectureDomain(MergedArchitectureDomain object) {
					MergedADElement result = defaultCase(object);

					if (result == null) {
						result = search(object.getContexts());
					}

					return result;
				}

				@Override
				public MergedADElement caseArchitectureContext(MergedArchitectureContext object) {
					MergedADElement result = defaultCase(object);

					if (result == null) {
						result = search(object.getViewpoints());
					}

					return result;
				}

			}.doSwitch(merged);

			assertThat("Failed to initialize test fixture", fixture, notNullValue());
		}

		protected abstract M findModel(ArchitectureDomain domain);

	}

	public static class DomainTest extends ADElementTest<MergedArchitectureDomain, ArchitectureDomain> {

		public DomainTest() {
			super();
		}

		@Override
		protected ArchitectureDomain findModel(ArchitectureDomain domain) {
			return domain;
		}

		@Test
		public void getStakeholders() {
			Assume.assumeThat("No stakeholders to test", model.getStakeholders(), not(isEmpty()));
			assertThat("Wrong stakeholders", fixture.getStakeholders(), is(model.getStakeholders()));
		}

		@Test
		public void getConcerns() {
			Assume.assumeThat("No concerns to test", model.getConcerns(), not(isEmpty()));
			assertThat("Wrong concerns", fixture.getConcerns(), is(model.getConcerns()));
		}

		@Test
		public void getParent() {
			assertThat("Domain should not have a parent", fixture.getParent(), nullValue());
		}

	}

	public static abstract class ContextTest<T extends MergedArchitectureContext, M extends ArchitectureContext> extends ADElementTest<T, M> {

		public ContextTest() {
			super();
		}

		@Test
		public void getCreationCommandClassName() {
			assertThat("Context has wrong creation command class name", fixture.getCreationCommandClassName(), is(model.getCreationCommandClass()));
		}

		@Test
		public void getConversionCommandClassName() {
			assertThat("Context has wrong conversion command class name", fixture.getConversionCommandClassName(), is(model.getConversionCommandClass()));
		}

		@Test
		public void getExtensionPrefix() {
			assertThat("Context has wrong extension prefix", fixture.getExtensionPrefix(), is(model.getExtensionPrefix()));
		}

		@Test
		public void getElementTypes() {
			assertThat("Context has wrong element types", fixture.getElementTypes(), is(model.getElementTypes()));
		}

		@Test
		public void getDomain() {
			assertThat("Context has no domain", fixture.getDomain(), notNullValue());
			assertThat("Context has wrong domain", fixture.getDomain().getAdapter(ArchitectureDomain.class), is(model.getDomain()));
			assertThat("Context not in domain's contexts", fixture.getDomain().getContexts(), hasItem(fixture));
		}

		@Test
		public void getParent() {
			assumeThat("Context has no domain", fixture.getDomain(), notNullValue());
			assertThat("Context has no parent", fixture.getParent(), notNullValue());
			assertThat("Context has wrong parent", fixture.getParent(), is(fixture.getDomain()));
		}

		@Test
		public void getDefaultViewpoints() {
			assumeThat("Context has no default viewpoints", model.getDefaultViewpoints(), not(isEmpty()));
			assertThat("Context should have default viewpoints", fixture.getDefaultViewpoints(), not(isEmpty()));
			assertThat("Default viewpoint not owned by context", fixture.getViewpoints(),
					hasItems(fixture.getDefaultViewpoints().toArray(MergedArchitectureViewpoint[]::new)));
		}

	}

	public static class DescriptionLanguageTest extends ContextTest<MergedArchitectureDescriptionLanguage, ArchitectureDescriptionLanguage> {

		public DescriptionLanguageTest() {
			super();
		}

		@Override
		protected ArchitectureDescriptionLanguage findModel(ArchitectureDomain domain) {
			return (ArchitectureDescriptionLanguage) domain.getContexts().get(0);
		}

		@Test
		public void getCreationCommandClass() {
			assumeThat("Context has no creation command class name", model.getCreationCommandClass(), notNullValue());

			try {
				assertThat("Context has wrong creation command class", fixture.getCreationCommandClass(), sameInstance(CreateTestModelCommand.class));
			} catch (ClassNotFoundException e) {
				fail("Creation command class not resolved: " + e.getMessage());
			}
		}

		@Test
		public void getConversionCommandClass() {
			assumeThat("Context has no conversion command class name", model.getConversionCommandClass(), notNullValue());

			try {
				assertThat("Context has wrong conversion command class", fixture.getConversionCommandClass(), sameInstance(ConvertTestModelCommand.class));
			} catch (ClassNotFoundException e) {
				fail("Conversion command class not resolved: " + e.getMessage());
			}
		}

		@Test
		public void getMetamodel() {
			assertThat("Context has wrong metamodel", fixture.getMetamodel(), is(model.getMetamodel()));
		}

		@Test
		public void getProfiles() {
			assertThat("Context has wrong element profiles", fixture.getProfiles(), is(model.getProfiles()));
		}

		@Test
		public void getRepresentationKinds() {
			assumeThat("No representation kinds to test", model.getRepresentationKinds(), not(isEmpty()));
			assertThat("Wrong concerns", fixture.getRepresentationKinds(), is(model.getRepresentationKinds()));
		}

	}

	public static class FrameworkTest extends ContextTest<MergedArchitectureFramework, ArchitectureFramework> {

		public FrameworkTest() {
			super();
		}

		@Override
		protected ArchitectureFramework findModel(ArchitectureDomain domain) {
			return (ArchitectureFramework) domain.getContexts().get(1);
		}

		@Test
		public void getCreationCommandClass() {
			assumeThat("Context has no creation command class name", model.getCreationCommandClass(), notNullValue());

			try {
				assertThat("Context has wrong creation command class", fixture.getCreationCommandClass(), sameInstance(CreateTestFrameworkModelCommand.class));
			} catch (ClassNotFoundException e) {
				fail("Creation command class not resolved: " + e.getMessage());
			}
		}

		@Test
		public void getConversionCommandClass() {
			assumeThat("Context has no conversion command class name", model.getConversionCommandClass(), notNullValue());

			try {
				assertThat("Context has wrong conversion command class", fixture.getConversionCommandClass(), sameInstance(ConvertTestFrameworkModelCommand.class));
			} catch (ClassNotFoundException e) {
				fail("Conversion command class not resolved: " + e.getMessage());
			}
		}

	}

	public static class ViewpointTest extends ADElementTest<MergedArchitectureViewpoint, ArchitectureViewpoint> {

		public ViewpointTest() {
			super();
		}

		@Override
		protected ArchitectureViewpoint findModel(ArchitectureDomain domain) {
			return domain.getContexts().get(0).getViewpoints().get(0);
		}

		@Test
		public void getConcerns() {
			assertThat("Viewpoint has wrong concerns", fixture.getConcerns(), is(model.getConcerns()));
		}

		@Test
		public void getRepresentationKinds() {
			assertThat("Viewpoint has wrong representation kinds", fixture.getRepresentationKinds(), is(model.getRepresentationKinds()));
		}

		@Test
		public void getContext() {
			assertThat("Viewpoint has no context", fixture.getContext(), notNullValue());
			assertThat("Viewpoint has wrong context", fixture.getContext().getAdapter(ArchitectureContext.class), is(model.getContext()));
			assertThat("Viewpoint not in context's viewpoints", fixture.getContext().getViewpoints(), hasItem(fixture));
		}

		@Test
		public void getParent() {
			assumeThat("Viewpoint has no context", fixture.getContext(), notNullValue());
			assertThat("Viewpoint has no parent", fixture.getParent(), notNullValue());
			assertThat("Viewpoint has wrong parent", fixture.getParent(), is(fixture.getContext()));
		}

	}

}
