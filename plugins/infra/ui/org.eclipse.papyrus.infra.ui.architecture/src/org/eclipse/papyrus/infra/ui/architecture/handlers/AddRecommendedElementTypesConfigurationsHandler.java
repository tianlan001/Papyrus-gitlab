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

package org.eclipse.papyrus.infra.ui.architecture.handlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.ui.architecture.messages.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler of the <em>Add Recommended Element Types Configurations</em> command.
 */
public class AddRecommendedElementTypesConfigurationsHandler extends AbstractHandler {

	private static final URI REPRESENTATIONS_ADVICE_URI = URI.createURI("pathmap://PAPYRUS_VIEWPOINTS_POLICY/representations/advice", true); //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		List<ArchitectureContext> contexts = getArchitectureContexts(selection);
		if (!contexts.isEmpty()) {
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(contexts.get(0));
			if (domain != null) {
				Shell parent = HandlerUtil.getActiveShell(event);
				domain.getCommandStack().execute(getAddElementTypeSetConfigurationsCommand(parent, domain, contexts));
			}
		}
		return null;
	}

	private List<ArchitectureContext> getArchitectureContexts(IStructuredSelection selection) {
		return ((List<?>) selection.toList()).stream()
				.filter(ArchitectureContext.class::isInstance)
				.map(ArchitectureContext.class::cast)
				.collect(Collectors.toList());
	}

	private Command getAddElementTypeSetConfigurationsCommand(Shell parent, EditingDomain domain, Collection<? extends ArchitectureContext> contexts) {
		Collection<? extends ElementTypeSetConfiguration> recommendedSets = getRecommendedElementTypesConfigurations(domain);

		AdapterFactory adapterFactory;
		if (domain instanceof AdapterFactoryEditingDomain) {
			adapterFactory = ((AdapterFactoryEditingDomain) domain).getAdapterFactory();
		} else {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}

		ILabelProvider labels = new AdapterFactoryLabelProvider(adapterFactory);
		Collection<?> selectedSets;

		try {
			FeatureEditorDialog dialog = new FeatureEditorDialog(parent, labels, Messages.AddRecommendedElementTypesConfigurationsHandler_1, ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION, List.of(), Messages.AddRecommendedElementTypesConfigurationsHandler_2,
					List.copyOf(recommendedSets), true, true, true);
			if (dialog.open() == Window.OK) {
				selectedSets = dialog.getResult();
			} else {
				selectedSets = List.of();
			}
		} finally {
			if (!(domain instanceof AdapterFactoryEditingDomain)) {
				// We created an adapter factory, so we must dispose it
				((ComposedAdapterFactory) adapterFactory).dispose();
			}
			labels.dispose();
		}

		return contexts.stream().map(ctx -> getAddElementTypeSetConfigurationsCommand(domain, ctx, selectedSets))
				.filter(Objects::nonNull)
				.reduce(Command::chain)
				.orElse(UnexecutableCommand.INSTANCE);
	}

	private Collection<ElementTypeSetConfiguration> getRecommendedElementTypesConfigurations(EditingDomain domain) {
		// TODO: This should be a pluggable registry of recommended Element Types Configurations
		Resource resource = domain.getResourceSet().getResource(REPRESENTATIONS_ADVICE_URI, true);
		return (resource == null) ? List.of() : EcoreUtil.<ElementTypeSetConfiguration> getObjectsByType(resource.getContents(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
	}

	private Command getAddElementTypeSetConfigurationsCommand(EditingDomain domain, ArchitectureContext context, Collection<?> recommendedSets) {
		Set<?> toAdd = new HashSet<>(recommendedSets);
		toAdd.removeAll(context.getElementTypes());
		return toAdd.isEmpty() ? null : AddCommand.create(domain, context, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__ELEMENT_TYPES, toAdd);
	}

}
