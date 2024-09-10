/*******************************************************************************
 * Copyright (c) 2015, 2020 Christian W. Damus and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Bros (Mia-Software) - Bug 366567 - [Releng] Tool to update rmaps
 *     Camille Letavernier (CEA LIST) - camille.letavernier@cea.fr - Generalize to handle POMs
 *     Christian W. Damus (CEA) - Add support for updating Oomph setup models
 *     Christian W. Damus - Support updating of multiple selected files
 *     Camille Letavernier (CEA LIST) - Move the behavior from a Handler to a dedicated class
 *
 *******************************************************************************/
package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.cbi.p2repo.aggregator.Aggregation;
import org.eclipse.cbi.p2repo.aggregator.AggregatorPackage;
import org.eclipse.cbi.p2repo.aggregator.transformer.TransformationManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.releng.tools.internal.Activator;
import org.eclipse.papyrus.releng.tools.internal.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.osgi.framework.Bundle;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Camille Letavernier
 *
 */
public class UpdateDependencies {
	public void updateDependencies(List<IFile> filesToUpdate, Shell activeShell) {
		IFile aggregationBuildFile = null;
		boolean cancelled = false;

		List<IFile> updated = Lists.newArrayListWithExpectedSize(4);

		try {
			if (!filesToUpdate.isEmpty()) {
				List<IFile> aggregationBuildFiles = findAggregationBuildFiles();
				aggregationBuildFile = chooseAggregationBuildFile(aggregationBuildFiles, activeShell);
				if (aggregationBuildFile == null) {
					cancelled = true;
				} else {
					Aggregation aggregation = loadAggregationModel(aggregationBuildFile);
					if (aggregation != null) {
						Map<Object, Object> context = Maps.newHashMap();
						for (IFile file : filesToUpdate) {
							if (updateFile(file, aggregation, activeShell, context)) {
								updated.add(file);
							}
						}
					}
				}
			}

		} catch (OperationCanceledException e) {
			cancelled = true;
		} catch (Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error", e)); //$NON-NLS-1$
			MessageDialog.openError(activeShell, Messages.UpdateRMapAction_error, e.getLocalizedMessage());
		}

		if (updated.isEmpty()) {
			// Don't waste the user's attention on this if he cancelled
			if (!cancelled) {
				MessageDialog.openInformation(activeShell, "No Files Updated", "No files were updated for new dependencies.");
			}
		} else {
			String fileList = Joiner.on(", ").join(Iterables.transform(updated, new Function<IFile, IPath>() {
				@Override
				public IPath apply(IFile input) {
					return input.getFullPath();
				}
			}));
			MessageDialog.openInformation(activeShell, Messages.UpdateRMapAction_mapWasUpdatedTitle, NLS.bind(Messages.UpdateRMapAction_mapWasUpdated, fileList, aggregationBuildFile.getFullPath().toString()));
		}
	}

	public static List<IFile> findAggregationBuildFiles() throws CoreException {
		List<IFile> aggregationBuildFiles = new ArrayList<>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (!project.isOpen()) {
				continue;
			}
			IResource[] members = project.members();
			for (IResource resource : members) {
				if (resource.getType() == IResource.FILE && resource.getName().endsWith(".aggr")) { //$NON-NLS-1$
					aggregationBuildFiles.add((IFile) resource);
				}
			}
		}
		return aggregationBuildFiles;
	}

	public static IFile chooseAggregationBuildFile(final List<IFile> aggregationBuildFiles, Shell activeShell) {
		if (aggregationBuildFiles.size() == 0) {
			MessageDialog.openWarning(activeShell, Messages.UpdateRMapAction_noBuildModelFound, Messages.UpdateRMapAction_noBuildModelFoundLong);
			return null;
		}
		if (aggregationBuildFiles.size() == 1) {
			return aggregationBuildFiles.get(0);
		}
		LabelProvider labelProvider = new LabelProvider() {

			@Override
			public String getText(final Object element) {
				if (element instanceof IFile) {
					IFile file = (IFile) element;
					return file.getProject().getName() + "/" + file.getName(); //$NON-NLS-1$
				}
				return super.getText(element);
			}
		};

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(activeShell, labelProvider);
		dialog.setTitle(Messages.UpdateRMapAction_chooseBuildModel);
		dialog.setMessage(Messages.UpdateRMapAction_chooseBuildModelLong);
		dialog.setElements(aggregationBuildFiles.toArray());
		dialog.open();
		return (IFile) dialog.getFirstResult();
	}

	protected static Aggregation loadAggregationModel(IFile aggregationBuildFile) throws CoreException {
		Aggregation result = null;

		// make sure the EPackage is initialized
		AggregatorPackage.eINSTANCE.getEFactoryInstance();
		URI uri = URI.createPlatformResourceURI(aggregationBuildFile.getFullPath().toString(), true);

		final ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = null;
		try {
			// with the latest version of the metamodel
			resource = resourceSet.getResource(uri, true);
			resource.load(null);
		} catch (Exception e) {
			// with an older version of the metamodel
			try {
				TransformationManager transformationManager = new TransformationManager(uri);
				resource = transformationManager.transformResource(true);
			} catch (Exception e1) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error loading b3aggr. Make sure you have the latest version of B3 installed. : " + e.getLocalizedMessage(), e1)); //$NON-NLS-1$
			}
		}

		if (resource.getContents().size() == 0) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The b3aggr resource is empty")); //$NON-NLS-1$
		}

		EObject root = resource.getContents().get(0);
		if (root instanceof Aggregation) {
			result = (Aggregation) root;
		} else {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The b3aggr resource does not contain an aggregation model")); //$NON-NLS-1$
		}

		return result;
	}

	protected boolean updateFile(IFile selectedFile, Aggregation aggregationModel, Shell activeShell, Map<Object, Object> context) throws CoreException {
		boolean result = false;

		List<DependencyUpdater<?>> updaters = findDependencyUpdater(selectedFile);
		for (DependencyUpdater<?> updater : updaters) {
			updater.updateDocument(activeShell, selectedFile, aggregationModel.getAllContributions(true), context);
			result = true;
		}

		return result;
	}

	protected List<DependencyUpdater<?>> findDependencyUpdater(IFile mapFile) throws CoreException {
		final String path = "org/eclipse/papyrus/releng/tools/internal/popup/actions/"; //$NON-NLS-1$

		Bundle bundle = Activator.getDefault().getBundle();

		// Try dev mode, first
		Enumeration<URL> urls = bundle.findEntries("bin/" + path, "*.class", false);
		if (urls == null) {
			// Deployed mode
			urls = bundle.findEntries(path, "*.class", false);
		}

		List<DependencyUpdater<?>> updaters = new LinkedList<>();

		while (urls.hasMoreElements()) {
			URL classURL = urls.nextElement();
			URI classURI = URI.createURI(classURL.toExternalForm(), true);

			try {
				String className = classURI.trimFileExtension().lastSegment();

				if (!"DependencyUpdater".equals(className) && !"XMLDependencyUpdater".equals(className) && className.endsWith("Updater")) {
					Class<? extends DependencyUpdater> updaterClass = bundle.loadClass(path.replace('/', '.') + className).asSubclass(DependencyUpdater.class);
					if (!Modifier.isAbstract(updaterClass.getModifiers())) {
						DependencyUpdater<?> updater = updaterClass.newInstance();
						if (updater.canUpdate(mapFile)) {
							updaters.add(updater);
						}
					}
				}
			} catch (ClassNotFoundException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "No such class: " + classURI.lastSegment(), e));
			} catch (IllegalAccessException | InstantiationException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to instantiated " + classURI.lastSegment(), e));
			} catch (Throwable t) { // Classes with missing optional dependencies. Simple Warning
				Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Failed to instantiate " + classURI.lastSegment(), t));
			}
		}

		return updaters;
	}
}
