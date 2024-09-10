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

import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * Quick fix for a {@link DataContextRoot} whose source package has moved to another URI.
 * It asks the user for the new location of the resource and updates all matching
 * source trace annotations accordingly.
 */
public class FindNewPackageLocation {

	Command fix(EditingDomain domain, DataContextRoot dataContext, IMarker marker) {
		// Assume there's nothing to fix until we determine otherwise
		Command result = UnexecutableCommand.INSTANCE;

		URI originalURI = getOriginalURI(dataContext);
		if (originalURI != null) {
			IFile newFile = browseForNewFile(marker, dataContext, originalURI);
			URI newURI = (newFile != null) ? URI.createPlatformResourceURI(newFile.getFullPath().toString(), true) : null;

			if (newURI != null) {
				result = createReplaceURIsCommand(domain, dataContext.eResource(), originalURI, newURI);
			}
		}

		return result;
	}

	private URI getOriginalURI(Annotatable element) {
		URI result = ContextAnnotations.getSourceModelURI(element);
		if (result != null) {
			result = result.trimFragment();
		}
		return result;
	}

	private Command createReplaceURIsCommand(EditingDomain domain, Resource resource, URI oldURI, URI newURI) {
		return Iterators2.stream(Iterators2.filter(EcoreUtil.getAllProperContents(resource, false), Annotatable.class))
				.filter(annotatable -> tracesTo(annotatable, oldURI))
				.map(annotatable -> updateSourceURI(domain, annotatable, newURI))
				.collect(Collectors.reducing(Command::chain))
				.orElse(UnexecutableCommand.INSTANCE);
	}

	private boolean tracesTo(Annotatable annotatable, URI resourceURI) {
		URI oldURI = ContextAnnotations.getSourceModelURI(annotatable);
		return oldURI != null && oldURI.trimFragment().equals(resourceURI);
	}

	private Command updateSourceURI(EditingDomain domain, Annotatable annotatable, URI newResourceURI) {
		// We know a priori that this annotation exists, otherwise we wouldn't be updating it
		return annotatable.getAnnotation(ContextAnnotations.ANNOTATION_SOURCE).getDetails().stream()
				.filter(entry -> ContextAnnotations.DETAIL_MODEL.equals(entry.getKey()))
				.findAny()
				.map(entry -> SetCommand.create(domain, entry, EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY__VALUE,
						newResourceURI.appendFragment(URI.createURI(entry.getValue()).fragment()).toString()))
				.orElse(IdentityCommand.INSTANCE);
	}

	private IFile browseForNewFile(IMarker marker, DataContextRoot dataContext, URI originalURI) {
		IFile result = null;

		IWorkspace workspace = marker.getResource().getWorkspace();
		String fileExtension = originalURI.fileExtension();

		Shell parentShell = EditorHelper.getActiveShell();
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(parentShell, false, workspace.getRoot(), IResource.FILE) {
			{
				setInitialPattern("**"); //$NON-NLS-1$
				setTitle(Messages.FindNewPackageLocation_0);
				String dataContextLabel = dataContext.getLabel();
				if (dataContextLabel == null || dataContextLabel.isBlank()) {
					dataContextLabel = dataContext.getName();
				}
				setMessage(NLS.bind(Messages.FindNewPackageLocation_1, dataContextLabel));
			}

			@Override
			protected ItemsFilter createFilter() {
				ResourceFilter delegate = (ResourceFilter) super.createFilter();

				return new ResourceFilter(workspace.getRoot(), delegate.isShowDerived(), IResource.FILE) {

					@Override
					public boolean isConsistentItem(Object item) {
						return delegate.isConsistentItem(item);
					}

					@Override
					public boolean matchItem(Object item) {
						return delegate.matchItem(item) && hasCorrectExtension(item);
					}

					@Override
					public int getMatchRule() {
						return delegate.getMatchRule();
					}

					@Override
					public boolean isCamelCasePattern() {
						return delegate.isCamelCasePattern();
					}

					@Override
					public String getPattern() {
						return delegate.getPattern();
					}

					@Override
					public boolean matchesRawNamePattern(Object item) {
						return delegate.matchesRawNamePattern(item);
					}

					@Override
					public boolean isSubFilter(ItemsFilter filter) {
						return delegate.isSubFilter(filter);
					}

					@Override
					public boolean equalsFilter(ItemsFilter filter) {
						return delegate.equalsFilter(filter);
					}

					boolean hasCorrectExtension(Object item) {
						return item instanceof IFile && fileExtension.equals(((IFile) item).getFullPath().getFileExtension());
					}

				};
			}
		};

		if (dialog.open() == Window.OK) {
			result = (IFile) dialog.getFirstResult();
		}

		return result;
	}

}
