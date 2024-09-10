/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *   Maged Elaasar - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550535
 *   Christian W. Damus - bug 570486
 *
 */
package org.eclipse.papyrus.infra.ui.architecture.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.architecture.commands.CloseEditorsForViewpointsCommand;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * A command handler for changing architecture viewpoints in a model set
 *
 * @since 1.0
 */
public class ChangeArchitectureViewpointsHandler extends CompoundContributionItem {

	// the label provider for architecture viewpoints
	private ILabelProvider provider;

	public ChangeArchitectureViewpointsHandler() {
		ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		provider = new AdapterFactoryLabelProvider(composedAdapterFactory);
	}

	@Override
	public void dispose() {
		provider.dispose();

		super.dispose();
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		final EObject selection = getSelection();
		if (selection == null) {
			return new IContributionItem[0];
		}
		ResourceSet resourceSet = selection.eResource().getResourceSet();
		if (!(resourceSet instanceof ModelSet)) {
			return new IContributionItem[0];
		}
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils((ModelSet) resourceSet);
		MergedArchitectureContext context = helper.getArchitectureContext();
		final Set<String> viewpointIds = new HashSet<>(helper.getArchitectureViewpointIds());

		List<IContributionItem> items = new ArrayList<>();
		for (MergedArchitectureViewpoint viewpoint : context.getViewpoints()) {
			ImageDescriptor desc = ExtendedImageRegistry.getInstance().getImageDescriptor(provider.getImage(viewpoint));
			items.add(new ActionContributionItem(new Action(provider.getText(viewpoint), desc) {
				{
					setChecked(viewpointIds.contains(viewpoint.getId()));
				}

				@Override
				public void run() {
					final Collection<MergedArchitectureViewpoint> viewpoints = helper.getArchitectureViewpoints();
					CompoundCommand cc = new CompoundCommand();
					if (!isChecked()) {
						viewpointIds.remove(viewpoint.getId());
						viewpoints.remove(viewpoint);
					} else {
						viewpointIds.add(viewpoint.getId());
						viewpoints.add(viewpoint);
					}
					TransactionalEditingDomain ted = helper.getModelSet().getTransactionalEditingDomain();
					// More than set the architecture viewpoints used, close needed editors
					// It is needed to be the first action to avoid opened dialog for viewpoints selection
					cc.append(new CloseEditorsForViewpointsCommand(viewpoints.stream().filter(viewpoint -> viewpointIds.contains(viewpoint.getId())).collect(Collectors.toList())));
					cc.append(helper.switchArchitectureViewpointIds(viewpointIds.toArray(new String[0])));
					ted.getCommandStack().execute(cc);
				}
			}));
		}
		return items.toArray(new IContributionItem[0]);
	}

	/**
	 * Gets the current selection as an EObject
	 *
	 * @return The current selection, or <code>null</code> if it is not an EObject
	 */
	protected EObject getSelection() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		ISelection selection = window.getSelectionService().getSelection();
		if (selection == null) {
			return null;
		}
		if (selection.isEmpty()) {
			return null;
		}
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection struct = (IStructuredSelection) selection;
			Object obj = struct.getFirstElement();
			return EMFHelper.getEObject(obj);
		}
		return null;
	}
}
