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

package org.eclipse.papyrus.uml.modelrepair.internal.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.services.markerlistener.IPapyrusMarker;
import org.eclipse.papyrus.uml.modelrepair.Activator;
import org.eclipse.papyrus.uml.modelrepair.internal.stereotypes.StereotypeApplicationRepairSnippet;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.FutureCallback;

/**
 * Quick fix for Stereotype Repair problem markers. It runs repair on the entire {@link ModelSet} and
 * wipes all of the related problem markers.
 */
public class StereotypeRepairResolutionGenerator implements IMarkerResolutionGenerator {

	private final Resolution resolution = new Resolution();

	public StereotypeRepairResolutionGenerator() {
		super();
	}

	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] { resolution };
	}

	//
	// Nested types
	//

	private static class Resolution implements IMarkerResolution2 {

		public String getLabel() {
			return "Repair Stereotype Applications";
		}

		public String getDescription() {
			return "Runs Stereotype Application Repair on the entire model, interactively resolving all broken stereotype problems.";
		}

		public Image getImage() {
			return null;
		}

		public void run(final IMarker marker) {
			IWorkbenchWindow window =
					Activator.getDefault().getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = (window == null) ? null : window.getActivePage();
			final ModelSet modelSet = (page == null) ? null : getModelSet(page, marker.getResource());

			if (modelSet != null) {
				// Get the editor's repair snippet, which is interactive with the user
				StereotypeApplicationRepairSnippet repair = StereotypeApplicationRepairSnippet.getInstance(modelSet);
				if (repair != null) {
					repair.repairAsync(modelSet, new FutureCallback<IStatus>() {

						public void onSuccess(IStatus result) {
							try {
								deleteMarkers(modelSet, marker.getType(), marker.getAttribute(IPapyrusMarker.SOURCE));
							} catch (Exception e) {
								Activator.log.error(e);
							}
						}

						public void onFailure(Throwable t) {
							// pass
						}
					});
				}
			}
		}

		ModelSet getModelSet(IWorkbenchPage page, IResource resource) {
			ModelSet result = null;

			if (resource instanceof IFile) {
				IEditorInput input = new FileEditorInput((IFile) resource);
				IEditorReference[] refs = page.findEditors(input, null, IWorkbenchPage.MATCH_INPUT);
				for (int i = 0; (result == null) && (i < refs.length); i++) {
					ServicesRegistry registry = AdapterUtils.adapt(refs[i].getEditor(true), ServicesRegistry.class, null);
					if (registry != null) {
						try {
							result = ServiceUtils.getInstance().getModelSet(registry);
						} catch (ServiceException e) {
							// Fine, then there's nothing to do
						}
					}
				}
			}

			return result;
		}

		void deleteMarkers(ModelSet modelSet, final String markerType, final Object source) throws CoreException {
			for (Resource resource : modelSet.getResources()) {
				final IFile file = ResourceUtils.getFile(resource);
				if (file != null) {
					file.getWorkspace().run(new IWorkspaceRunnable() {

						public void run(IProgressMonitor monitor) throws CoreException {
							IMarker[] markers = file.findMarkers(markerType, false, 0);
							for (IMarker next : markers) {
								if (Objects.equal(next.getAttribute(IPapyrusMarker.SOURCE), source)) {
									next.delete();
								}
							}
						}
					}, file, IWorkspace.AVOID_UPDATE, null);
				}
			}
		}
	}
}
