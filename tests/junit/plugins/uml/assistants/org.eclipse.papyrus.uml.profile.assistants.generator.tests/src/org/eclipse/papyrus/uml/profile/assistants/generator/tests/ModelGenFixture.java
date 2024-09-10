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

package org.eclipse.papyrus.uml.profile.assistants.generator.tests;

import static com.google.common.collect.Iterables.filter;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.util.AssistantResource;
import org.eclipse.papyrus.uml.profile.assistants.generator.ModelingAssistantsGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xtext.xbase.lib.Pair;
import org.hamcrest.CoreMatchers;

import com.google.common.collect.Lists;

/**
 * A specialization of the element types generation fixture that additionally generates the Diagram Assistants model.
 */
public class ModelGenFixture extends org.eclipse.papyrus.uml.profile.types.generator.tests.ModelGenFixture {
	public ModelGenFixture() {
		super();
	}

	public URI getAssistantsResourceURI() {
		URI inputURI = getModelResourceURI();
		String basename = inputURI.lastSegment();
		basename = basename.substring(0, basename.indexOf('.'));

		return inputURI.trimSegments(1).appendSegment(basename).appendFileExtension(AssistantResource.FILE_EXTENSION);
	}

	public ModelingAssistantProvider getModelingAssistantProvider() {
		Resource resource = getResourceSet().getResource(getAssistantsResourceURI(), true);
		return (ModelingAssistantProvider) resource.getContents().get(0);
	}

	public <A extends Assistant> A getAssistant(Pair<Stereotype, Class> metaclassExtension, int visualID, java.lang.Class<A> type) {
		Assistant result = getAssistant(metaclassExtension, visualID);
		assertThat("not a " + type.getSimpleName(), result, instanceOf(type));
		return type.cast(result);
	}

	public Assistant getAssistant(Pair<Stereotype, Class> metaclassExtension, int visualID) {
		return getAssistant(getElementTypeID(metaclassExtension, visualID));
	}

	protected String getElementTypeID(Pair<Stereotype, Class> metaclassExtension, int visualID) {
		return String.format("%s_%s", getElementTypeID(metaclassExtension, false), visualID);
	}

	protected String getElementTypeID(org.eclipse.uml2.uml.Class metaclass, String visualID) {
		String metaclassID = getElementTypeID(metaclass);
		return (visualID == null) ? metaclassID : String.format("%s_%s", metaclassID, visualID.substring(visualID.indexOf('_')+1));
	}

	public Assistant getAssistant(String id) {
		Assistant result = null;

		for (Assistant next : getModelingAssistantProvider().getAssistants()) {
			if (id.equals(next.getElementTypeID())) {
				result = next;
				break;
			}
		}

		return result;
	}

	public <A extends Assistant> List<A> getAllAssistants(Pair<Stereotype, Class> metaclassExtension, java.lang.Class<A> type) {
		List<Assistant> result = getAllAssistants(metaclassExtension);
		assertThat("not " + type.getSimpleName(), result, everyItem(CoreMatchers.<Assistant> instanceOf(type)));

		@SuppressWarnings("unchecked")
		List<A> resultAsA = (List<A>) result;
		return resultAsA;
	}

	public List<Assistant> getAllAssistants(Pair<Stereotype, Class> metaclassExtension) {
		return getAllAssistants(Pattern.compile(String.format("%s($|_\\w+)", Pattern.quote(getElementTypeID(metaclassExtension, false)))));
	}

	public List<Assistant> getAllAssistants(Pattern idPattern) {
		List<Assistant> result = Lists.newArrayListWithExpectedSize(3);

		for (Assistant next : getModelingAssistantProvider().getAssistants()) {
			if (idPattern.matcher(next.getElementTypeID()).find()) {
				result.add(next);
			}
		}

		return result;
	}

	public PopupAssistant assertPopupAssistant(Pair<Stereotype, Class> metaclassExtension, int visualID) {
		PopupAssistant result = getAssistant(metaclassExtension, visualID, PopupAssistant.class);
		return result;
	}

	public List<PopupAssistant> assertAllPopupAssistants(Pair<Stereotype, Class> metaclassExtension) {
		List<PopupAssistant> result = getAllAssistants(metaclassExtension, PopupAssistant.class);
		return result;
	}

	public ConnectionAssistant assertConnectionAssistant(Pair<Stereotype, Class> metaclassExtension, int visualID) {
		ConnectionAssistant result = getAssistant(metaclassExtension, visualID, ConnectionAssistant.class);
		return result;
	}

	public List<ConnectionAssistant> assertAllConnectionAssistants(Pair<Stereotype, Class> metaclassExtension) {
		List<ConnectionAssistant> result = getAllAssistants(metaclassExtension, ConnectionAssistant.class);
		return result;
	}

	public ElementTypeFilter getMetaclassFilter(Pair<Stereotype, Class> metaclassExtension, String visualID) {
		ElementTypeFilter result = null;
		String id = getElementTypeID(metaclassExtension.getValue(), visualID);

		for (ElementTypeFilter next : filter(getModelingAssistantProvider().getOwnedFilters(), ElementTypeFilter.class)) {
			if (next.getElementTypeID().equals(id)) {
				result = next;
				break;
			}
		}

		return result;
	}

	public List<ElementTypeFilter> getAllMetaclassFilters(Pair<Stereotype, Class> metaclassExtension) {
		return getAllMetaclassFilters(getElementTypeID(metaclassExtension.getValue()) + "_");
	}

	public List<ElementTypeFilter> getAllMetaclassFilters(String idPrefix) {
		List<ElementTypeFilter> result = Lists.newArrayListWithExpectedSize(3);

		for (ElementTypeFilter next : filter(getModelingAssistantProvider().getOwnedFilters(), ElementTypeFilter.class)) {
			if (next.getElementTypeID().startsWith(idPrefix)) {
				result.add(next);
			}
		}

		return result;
	}

	public ElementTypeFilter assertMetaclassFilter(Pair<Stereotype, Class> metaclassExtension, String visualID) {
		ElementTypeFilter result = getMetaclassFilter(metaclassExtension, visualID);
		assertThat(result, notNullValue());
		return result;
	}

	public List<ElementTypeFilter> assertAllMetaclassFilters(Pair<Stereotype, Class> metaclassExtension) {
		List<ElementTypeFilter> result = getAllMetaclassFilters(metaclassExtension);
		assertThat(result, not(isEmpty()));
		return result;
	}

	@Override
	protected void generateModel(Identifiers identifiers) {
		super.generateModel(identifiers);

		generateDiagramAssistants(identifiers);
	}

	protected void generateDiagramAssistants(Identifiers identifiers) {
		ModelingAssistantsGenerator assistantsGenerator = new ModelingAssistantsGenerator(identifiers);
		IStatus status = assistantsGenerator.generate(getModelURI(), getAssistantsResourceURI());
		assertThat(status.getMessage(), status.getSeverity(), lessThan(IStatus.ERROR));
	}
}
