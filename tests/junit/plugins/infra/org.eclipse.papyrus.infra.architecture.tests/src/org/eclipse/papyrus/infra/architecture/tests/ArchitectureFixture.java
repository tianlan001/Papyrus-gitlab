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
 *  Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThanOrEqual;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.architecture.tests.merged.MergedArchitectureDomainTest;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureResourceFactory;
import org.eclipse.papyrus.infra.core.architecture.util.MergeTraceAdapter;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;

/**
 * JUnit rule to manage architecture resources in tests.
 */
public class ArchitectureFixture extends TestWatcher {

	private ResourceSet resourceSet;
	private Optional<String> domainName;
	private ListMultimap<String, ArchitectureDomain> domains;

	public ArchitectureFixture() {
		super();
	}

	public ArchitectureDomain getArchitectureDomain(String name) {
		List<ArchitectureDomain> result = domains.get(name);
		assertThat("No such architecture domain: " + name, result, not(isEmpty()));
		assertThat("Not an uniquely named source architecture domain: " + name, result.size(), lessThanOrEqual(1));
		return result.get(0);
	}

	public MergedArchitectureDomain mergeDomain() {
		assertThat("Test not annotated with required domain name", domainName.isPresent(), is(true));
		MergedArchitectureDomain result = mergeDomains().get(domainName.get());
		assertThat("Test domain not in the merge result: " + domainName.get(), result, notNullValue());
		return result;
	}

	public Map<String, MergedArchitectureDomain> mergeDomains() {
		List<MergedArchitectureDomain> merged = MergedArchitectureDomainTest.merge(domains.values().toArray(ArchitectureDomain[]::new));
		return merged.stream().collect(Collectors.toMap(MergedADElement::getName, Function.identity()));
	}

	public <T extends ADElement> T rename(ArchitectureDomain domain, Class<T> type, String oldName, String newName) {
		Predicate<EObject> filter = type::isInstance;
		filter = filter.and(e -> oldName.equals(e.eGet(ArchitecturePackage.Literals.AD_ELEMENT__NAME)));

		T result = (T) Iterators2.stream(EcoreUtil.<EObject> getAllContents(Set.of(domain))).filter(filter)
				.findAny().map(type::cast).orElse(null);
		assertThat("Object not found to rename: " + oldName, result, notNullValue());

		result.setName(newName);

		return result;
	}

	public boolean tracesTo(ADElement element, ADElement source) {
		MergeTraceAdapter traces = MergeTraceAdapter.getMergeTraces(element);
		return traces != null && traces.tracesTo(element, source);
	}
	
	public boolean tracesTo(MergedADElement element, ADElement source) {
		return tracesTo(element.getAdapter(ADElement.class), source);
	}
	
	public <T> T get(Collection<? extends T> elements, String name) {
		return elements.stream().filter(e -> name.equals(getName(e))).findAny()
				.orElseThrow(() -> new AssertionError("no such element: " + name));
	}
	
	private static String getName(Object element) {
		return element instanceof ADElement
				? ((ADElement) element).getName()
						: element instanceof MergedADElement
						? ((MergedADElement) element).getName()
								: null;
	}
	
	//
	// JUnit rule lifecycle
	//

	@Override
	protected void starting(Description description) {
		// Ensure registration of the packages we use
		resourceSet = new ResourceSetImpl();

		if (!EcorePlugin.IS_ECLIPSE_RUNNING) {
			// Ensure registration of the packages we use
			ArchitecturePackage.eINSTANCE.getADElement();
			RepresentationPackage.eINSTANCE.getPapyrusDiagram();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("architecture", new ArchitectureResourceFactory());
		}

		Class<?> testClass = JUnitUtils.getTestClass(description);
		JUnitUtils.getAnnotationsByType(description, ArchitectureResource.class).stream()
				.map(ArchitectureResource::value)
				.map(name -> {
					URL url = testClass.getResource(name + ".architecture");
					assertThat("No such test resource: " + name, url, notNullValue());
					return url;
				})
				.map(url -> URI.createURI(url.toExternalForm()))
				.forEach(uri -> resourceSet.getResource(uri, true));

		EcoreUtil.resolveAll(resourceSet);

		domains = resourceSet.getResources().stream()
				.map(Resource::getContents)
				.flatMap(Collection::stream)
				.filter(ArchitectureDomain.class::isInstance).map(ArchitectureDomain.class::cast)
				.collect(Multimaps.toMultimap(ADElement::getName, Function.identity(),
						MultimapBuilder.linkedHashKeys().arrayListValues()::build));

		domainName = Optional.ofNullable(JUnitUtils.getAnnotation(description, DomainName.class)).map(DomainName::value);

		super.starting(description);
	}

	@Override
	protected void finished(Description description) {
		super.finished(description);

		EMFHelper.unload(resourceSet);
		domains.clear();
	}

}
