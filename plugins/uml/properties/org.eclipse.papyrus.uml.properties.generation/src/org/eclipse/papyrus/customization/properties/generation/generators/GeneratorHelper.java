/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Christian W. Damus - bugs 482927, 573986
 *  Vincent Lorenzo (CEA LIST) - bug 520271
 *
 *****************************************************************************/

package org.eclipse.papyrus.customization.properties.generation.generators;

import static org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations.setLayoutGeneratorClassName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.eclipse.papyrus.customization.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.UiFactory;
import org.eclipse.papyrus.infra.properties.ui.ValueAttribute;
import org.eclipse.papyrus.infra.properties.ui.runtime.IConfigurationManager;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;

/**
 * Utilities to assist <em>Properties Context</em> model generation.
 */
public class GeneratorHelper {

	private final IConfigurationManager configManager = PropertiesRuntime.getConfigurationManager();

	private final IGenerator generator;
	private final ILayoutGenerator layout;

	public GeneratorHelper(IGenerator generator, ILayoutGenerator layout) {
		super();

		this.generator = generator;
		this.layout = layout;
	}

	/**
	 * Generate the sections of the given {@code view} into the specified {@code tab} of the {@code context} model.
	 *
	 * @param context
	 *            the <em>Properties Context</em> model
	 * @param tab
	 *            a tab into which to generate sections
	 * @param view
	 *            a view for which to generate sections
	 * @param propertySelectionPredicate
	 *            a predicate that tests whether a property of some multiplicity is to be generated in the section
	 *
	 * @return the generated sections
	 */
	public List<Section> generateLayout(Context context, Tab tab, View view, BiPredicate<? super Property, ? super Integer> propertySelectionPredicate) {
		layout.setGenerator(generator);

		List<PropertyEditor> editors = new LinkedList<>();
		// the list of properties for the current view
		final List<Property> properties = new ArrayList<>();

		Set<DataContextElement> elements = getAllContextElements(view.getDatacontexts());
		for (DataContextElement element : elements) {
			for (Property property : element.getProperties()) {
				if (propertySelectionPredicate.test(property, view.getElementMultiplicity())) {
					properties.add(property);
				}
			}
		}

		// Annotate the data context to record the layout generator used
		Collection<DataContextRoot> dataContexts = getRoots(elements);
		ClassLoaderHelper.getURI(layout.getClass()).ifSuccess(uri -> dataContexts.forEach(dc -> setLayoutGeneratorClassName(dc, uri.toString())));

		final List<Property> tmpProperties = new ArrayList<>(properties);
		for (Property p1 : tmpProperties) {
			// Bug 519090
			// we remove all redefined properties from the list
			properties.removeAll(p1.getRedefinedProperties());
		}

		// we create the editor
		for (Property property : properties) {
			PropertyEditor editor = UiFactory.eINSTANCE.createPropertyEditor();
			editor.setProperty(property);
			editor.setWidgetType(configManager.getDefaultEditorType(property));
			editors.add(editor);
			ValueAttribute input = UiFactory.eINSTANCE.createValueAttribute();
			input.setName("input"); //$NON-NLS-1$
			input.setValue("{Binding}"); //$NON-NLS-1$
			editor.getAttributes().add(input);
		}

		List<Section> generatedSections = layout.layoutElements(editors, view);
		tab.getSections().addAll(generatedSections);
		view.getSections().addAll(generatedSections);

		return generatedSections;
	}

	private Set<DataContextElement> getAllContextElements(Collection<DataContextElement> source) {
		Set<DataContextElement> result = new HashSet<>();
		for (DataContextElement element : source) {
			getAllContextElements(element, result);
		}
		return result;
	}

	private void getAllContextElements(DataContextElement source, Set<DataContextElement> result) {
		if (result.contains(source)) {
			return;
		}

		result.add(source);
		for (DataContextElement element : source.getSupertypes()) {
			getAllContextElements(element, result);
		}
	}

	private Collection<DataContextRoot> getRoots(Collection<? extends DataContextElement> elements) {
		return elements.stream().map(GeneratorHelper::getRoot).filter(Objects::nonNull).distinct().collect(Collectors.toList());
	}

	public static DataContextRoot getRoot(DataContextElement element) {
		DataContextRoot result = null;

		for (DataContextElement dc = element; dc != null && result == null; dc = dc.getPackage()) {
			if (dc instanceof DataContextRoot) {
				result = (DataContextRoot) dc;
			}
		}

		return result;
	}

}
