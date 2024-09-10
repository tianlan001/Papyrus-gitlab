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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.quickfix;

import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PACKAGE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ELEMENT;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.getObjectToReference;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.customization.properties.generation.extensionpoint.LayoutExtensionPoint;
import org.eclipse.papyrus.customization.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.command.ContextDeleteCommand;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;
import org.eclipse.ui.IMarkerResolution;

import com.google.common.collect.Lists;

/**
 * A factory for quick fixes that add or remove data-context elements, with or without
 * associated views and sections. It is not called a "generator" because it does not
 * implement the Eclipse marker-resolution generator API.
 */
class DataContextElementResolutionFactory extends AbstractModelEditResolutionFactory {

	DataContextElementResolutionFactory(int problemID) {
		super(problemID);
	}

	@Override
	protected Iterable<IMarkerResolution> createResolutions() {
		switch (getProblemID()) {
		case MISSING_DATA_CONTEXT_PACKAGE:
			return List.copyOf(Lists.asList(addDataContextPackage(), addDataContextPackageAndViews()));
		case MISSING_DATA_CONTEXT_ELEMENT:
			return List.copyOf(Lists.asList(addDataContextElement(), addDataContextElementAndViews()));
		case OBSOLETE_DATA_CONTEXT_ELEMENT:
			return List.of(removeDataContextElement());
		default:
			return List.of();
		}
	}

	IMarkerResolution addDataContextPackage() {
		return createResolution(Messages.DataContextElementResolutionFactory_6, Messages.DataContextElementResolutionFactory_7,
				DataContextPackage.class, this::createAddDataContextPackageCommand);
	}

	IMarkerResolution[] addDataContextPackageAndViews() {
		return getLayoutGeneratorClass().map(this::instantiateLayout)
				.map(Stream::of)
				.orElseGet(() -> new LayoutExtensionPoint().getGenerators().stream())
				.map(this::addDataContextPackageAndViews)
				.toArray(IMarkerResolution[]::new);
	}

	private IMarkerResolution addDataContextPackageAndViews(ILayoutGenerator generator) {
		return createResolution(NLS.bind(Messages.DataContextElementResolutionFactory_8, generator.getName()),
				NLS.bind(Messages.DataContextElementResolutionFactory_9, generator.getName()),
				DataContextPackage.class, (domain, package_) -> createDataContextPackageAndViewsCommand(domain, package_, generator));
	}

	private Command createAddDataContextPackageCommand(EditingDomain domain, DataContextPackage package_) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourcePackage -> createDataContextPackage(domain, sourcePackage))
				.map(nested -> AddCommand.create(domain, package_, ContextsPackage.Literals.DATA_CONTEXT_PACKAGE__ELEMENTS, nested))
				.orElse(null);
	}

	private Command createDataContextPackageAndViewsCommand(EditingDomain domain, DataContextPackage package_, ILayoutGenerator layout) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourcePackage -> createDataContextPackage(domain, sourcePackage))
				.map(nested -> {
					CompoundCommand result = new CompoundCommand();
					result.append(AddCommand.create(domain, package_, ContextsPackage.Literals.DATA_CONTEXT_PACKAGE__ELEMENTS, nested));

					createCreateViewCommands(domain, getContext(package_), nested, layout).forEach(result::append);

					return result.unwrap();
				})
				.orElse(null);
	}

	IMarkerResolution addDataContextElement() {
		return createResolution(Messages.DataContextElementResolutionFactory_0, Messages.DataContextElementResolutionFactory_1,
				DataContextPackage.class, this::createAddDataContextElementCommand);
	}

	IMarkerResolution[] addDataContextElementAndViews() {
		return getLayoutGeneratorClass().map(this::instantiateLayout)
				.map(Stream::of)
				.orElseGet(() -> new LayoutExtensionPoint().getGenerators().stream())
				.map(this::addDataContextElementAndViews)
				.toArray(IMarkerResolution[]::new);
	}

	private IMarkerResolution addDataContextElementAndViews(ILayoutGenerator generator) {
		return createResolution(NLS.bind(Messages.DataContextElementResolutionFactory_2, generator.getName()),
				NLS.bind(Messages.DataContextElementResolutionFactory_3, generator.getName()),
				DataContextPackage.class, (domain, package_) -> createDataContextElementAndViewsCommand(domain, package_, generator));
	}

	private Command createAddDataContextElementCommand(EditingDomain domain, DataContextPackage package_) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourceClass -> createDataContextElement(domain, sourceClass))
				.map(element -> AddCommand.create(domain, package_, ContextsPackage.Literals.DATA_CONTEXT_PACKAGE__ELEMENTS, element))
				.orElse(null);
	}

	private Command createDataContextElementAndViewsCommand(EditingDomain domain, DataContextPackage package_, ILayoutGenerator layout) {
		return getObjectToReference(getMarker(), EObject.class, domain)
				.map(sourceClass -> createDataContextElement(domain, sourceClass))
				.map(element -> {
					CompoundCommand result = new CompoundCommand();
					result.append(AddCommand.create(domain, package_, ContextsPackage.Literals.DATA_CONTEXT_PACKAGE__ELEMENTS, element));

					createCreateViewCommands(domain, getContext(package_), element, layout).forEach(result::append);

					return result.unwrap();
				})
				.orElse(null);
	}

	/**
	 * Create views for and lay out sections for the given {@code element} in a properties {@code context}.
	 *
	 * @param domain
	 *            the EMF editing domain in which to create commands
	 * @param context
	 *            the properties context model
	 * @param element
	 *            the element for which to create views. This may be a {@link DataContextPackage}, in which case
	 *            views are created recursively for contained elements
	 * @param layout
	 *            the layout generator to use to generate the sections
	 *
	 * @return the stream (possibly empty) of commands to append
	 */
	private Stream<Command> createCreateViewCommands(EditingDomain domain, Context context, DataContextElement element, ILayoutGenerator layout) {
		Stream<Command> result;

		if (element instanceof DataContextPackage) {
			result = ((DataContextPackage) element).getElements().stream()
					.flatMap(child -> createCreateViewCommands(domain, context, child, layout));
		} else {
			result = Stream.of(createView(context, element, 1), createView(context, element, -1))
					.map(view -> createLayoutCommand(domain, layout, context, element, view));
		}

		return result;
	}

	IMarkerResolution removeDataContextElement() {
		return createResolution(Messages.DataContextElementResolutionFactory_4, Messages.DataContextElementResolutionFactory_5,
				DataContextElement.class, this::createDeleteDataContextElementCommand);
	}

	private Command createDeleteDataContextElementCommand(EditingDomain domain, DataContextElement element) {
		DataContextPackage package_ = element.getPackage();
		return Optional.ofNullable(package_).map(container -> {
			CompoundCommand result = new CompoundCommand();

			// Same command as the editor uses
			result.append(ContextDeleteCommand.create(domain, element));

			// Find associated views and sections and delete them
			Stream.concat(PropertiesCache.getInstance(element).getViews(element).stream(),
					PropertiesCache.getInstance(element).getSections(element).stream())
					// The editor uses RemoveCommand instead of DeleteCommand, so we do, also
					.map(viewOrSection -> RemoveCommand.create(domain, viewOrSection))
					.forEach(result::append);

			return result.unwrap();
		}).orElse(null);
	}

}
