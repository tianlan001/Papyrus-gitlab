/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.nattable.handlers;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.nattable.representation.RepresentationFactory;
import org.eclipse.papyrus.internal.infra.nattable.model.resources.NattableConfigurationResource;
import org.eclipse.papyrus.toolsmiths.nattable.Activator;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.papyrus.uml.properties.profile.ui.dialogs.FileSelectionFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *
 * This handler is active only on a {@link ArchitectureDescriptionLanguage} to ease the registration of an exising {@link TableConfiguration}
 */
public class RegisterTableConfigurationInArchitectureFrameworkHandler extends AbstractHandler {

	/**
	 * The selected description language
	 */
	private WeakReference<ArchitectureDescriptionLanguage> adl;

	/**
	 * The filters used to select a .nattableconfiguration file in the workspace
	 */
	private List<ViewerFilter> filters;

	/**
	 *
	 * Constructor.
	 *
	 */
	public RegisterTableConfigurationInArchitectureFrameworkHandler() {
		this.filters = new ArrayList<>();
		this.filters.add(new FileSelectionFilter(Collections.singletonList(NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION)));
	}

	/**
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// 1. check the selection
		final ArchitectureDescriptionLanguage language = getSelectedArchitectureDescriptionLanguage();
		if (null == language) {
			return null;
		}

		// 2. get the table configuration
		final ResourceSet resourceSet = language.eResource().getResourceSet();// should never be null
		final TableConfiguration tableConfiguration = askForTableConfigurationToReference(resourceSet);
		if (null == tableConfiguration) {
			return null;
		}

		// 3. get the current editor, the editing domain and the label provider
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final EditingDomain domain = activeEditor instanceof IEditingDomainProvider ? ((IEditingDomainProvider) activeEditor).getEditingDomain() : null;

		if (null == domain) {
			return null;
		}

		// 4. get the viewpoints to contribute
		final List<ArchitectureViewpoint> viewpoints = getViewPointsToContribute(language);

		// 5. get the Concerns to contribute
		final ArchitectureDomain architectureDomain = (ArchitectureDomain) language.eContainer();
		final List<Concern> concerns = getConcernContribute(architectureDomain);

		// 6. create the PapyrusTable representation
		final PapyrusTable pTable = createPapyrusTableRepresentation(language, tableConfiguration, concerns);

		// 6. register the PapyrusTable Representation in the model
		final CompoundCommand cc = new CompoundCommand("Register an existing Table Configuration Command"); //$NON-NLS-1$
		cc.append(AddCommand.create(domain, language, ArchitecturePackage.eINSTANCE.getArchitectureDescriptionLanguage_RepresentationKinds(), Collections.singletonList(pTable)));
		for (final ArchitectureViewpoint currentViewpoint : viewpoints) {
			cc.append(AddCommand.create(domain, currentViewpoint, ArchitecturePackage.eINSTANCE.getArchitectureViewpoint_RepresentationKinds(), Collections.singletonList(pTable)));
		}



		cc.append(new AbstractCommand("Set XMI_ID for PapyrusTable") { //$NON-NLS-1$

			@Override
			public void redo() {
				// nothing to do
			}

			@Override
			public void execute() {
				((XMIResource) language.eResource()).setID(pTable, pTable.getId());
			}

			@Override
			protected boolean prepare() {
				return true;
			}
		});


		if (cc.canExecute()) {
			domain.getCommandStack().execute(cc);
		}



		// 7. add the plugin providing the configuration to the dependency of the architecture framework plugin
		// 7.1 get the name of the AF Project
		final URI architectureResourceURI = language.eResource().getURI();
		final String AFProjectName = architectureResourceURI.segment(1);

		// 7.2 get the name of the TableConfiguration Project
		final URI tableResourceURI = tableConfiguration.eResource().getURI();
		final String tableResourceProjectName = tableResourceURI.segment(1);
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();


		IProject AFProject = workspaceRoot.getProject(AFProjectName);
		ManifestEditor mfEditor = null;
		try {
			mfEditor = new ManifestEditor(AFProject);
		} catch (IOException e) {
			Activator.log.error(e);
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		if (null != mfEditor) {
			mfEditor.init();
			mfEditor.addDependency(tableResourceProjectName);
			mfEditor.save();
		}
		return null;

	}


	/**
	 *
	 * @return
	 *         the selected {@link ArchitectureDescriptionLanguage} or <code>null</code>
	 */
	private ArchitectureDescriptionLanguage getSelectedArchitectureDescriptionLanguage() {
		if (null == this.adl) {
			return null;
		}
		return this.adl.get();
	}

	/**
	 *
	 * @param resourceSet
	 *            the resourceSet to use to load an existing table configuration
	 * @return
	 *         the selected TableConfiguration, or <code>null</code> in case of failure
	 */
	private TableConfiguration askForTableConfigurationToReference(final ResourceSet resourceSet) {
		final IFile[] ifile = WorkspaceResourceDialog.openFileSelection(Display.getCurrent().getActiveShell(), Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_AddExistingTableConfiguration,
				Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_SelectTheTableconfiguration, false, new IFile[] {}, filters);
		if (ifile.length == 1) {
			final IFile file = ifile[0];
			final String projectName = file.getProject().getName();

			// 1.we look for the file uri in platform:/plugin instead of platform:/resource to avoid ../.. in the final edited file
			final String pathInTheProject = file.getProjectRelativePath().toString();
			URI uri = URI.createPlatformPluginURI(projectName, true);
			uri = getCompleteURI(uri, pathInTheProject);
			final Resource res;
			if (resourceSet.getURIConverter().exists(uri, null)) {
				res = resourceSet.getResource(uri, true);
			} else {
				// 2. we use the local file to be in platform:/resource. In this case, the user need to fix the saved uri after the file edition
				uri = URI.createPlatformResourceURI(projectName, true);
				uri = getCompleteURI(uri, pathInTheProject);
				res = resourceSet.getResource(uri, true);
			}
			final Object root = res.getContents().get(0);
			if (root instanceof TableConfiguration) {
				return (TableConfiguration) root;
			}
		}
		return null;
	}

	/**
	 *
	 * @param initialProjectURI
	 *            the initial projectURI
	 * @param pathInTheProject
	 *            the path of the file in the project (including the file itself)
	 * @return
	 *         the built URI
	 */
	protected URI getCompleteURI(final URI initialProjectURI, final String pathInTheProject) {
		String[] path = pathInTheProject.split("/"); //$NON-NLS-1$
		URI completURI = initialProjectURI;
		for (int i = 0; i < path.length - 1; i++) {
			completURI = completURI.appendSegment(path[i]);
		}
		final String fileName = path[path.length - 1].split("\\.")[0]; //$NON-NLS-1$
		completURI = completURI.appendSegment(fileName);
		completURI = completURI.appendFileExtension(NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION);
		return completURI;
	}

	/**
	 *
	 * @param language
	 *            the {@link ArchitectureDescriptionLanguage} to contribute
	 * @return
	 *         the list of {@link ArchitectureViewpoint} available for it
	 */
	private List<ArchitectureViewpoint> getViewPointsToContribute(final ArchitectureDescriptionLanguage language) {
		Assert.isNotNull(language);
		final List<ArchitectureViewpoint> selectedViewpoints = new ArrayList<>();
		if (language.getViewpoints().size() == 1) {
			selectedViewpoints.add(language.getViewpoints().get(0));
		} else if (language.getViewpoints().size() > 1) {
			// we open a dialog to select the good one
			final ILabelProvider labelProvider = new AdapterFactoryLabelProvider(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
			final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault().getActiveShell(), labelProvider, new ArchitectureViewpointContentProvider());
			dialog.setTitle(Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_SelectViewPointsToContribute);
			dialog.setMessage(Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_SeveralViewpointsAreAvailable);
			dialog.setAllowMultiple(true);
			dialog.setInput(language);
			if (Dialog.OK == dialog.open()) {
				for (Object current : dialog.getResult()) {
					if (current instanceof ArchitectureViewpoint) {
						selectedViewpoints.add((ArchitectureViewpoint) current);
					}
				}
			}
		}
		return selectedViewpoints;
	}

	/**
	 *
	 * @param domain
	 *            the {@link ArchitectureDomain} to contribute
	 * @return
	 *         the list of {@link Concern} available for it
	 */
	private List<Concern> getConcernContribute(final ArchitectureDomain domain) {
		Assert.isNotNull(domain);
		final List<Concern> selectedViewpoints = new ArrayList<>();
		if (domain.getConcerns().size() == 1) {
			selectedViewpoints.add(domain.getConcerns().get(0));
		} else if (domain.getConcerns().size() > 1) {
			// we open a dialog to select the good one
			final ILabelProvider labelProvider = new AdapterFactoryLabelProvider(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
			final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault().getActiveShell(), labelProvider, new ConcernsContentProvider());
			dialog.setTitle(Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_SelectConcernsToContribute);
			dialog.setMessage(Messages.RegisterTableConfigurationInArchitectureFrameworkHandler_SeveralConcernsAreAvailable);
			dialog.setAllowMultiple(true);
			dialog.setInput(domain);
			if (Dialog.OK == dialog.open()) {
				for (Object current : dialog.getResult()) {
					if (current instanceof Concern) {
						selectedViewpoints.add((Concern) current);
					}
				}
			}
		}
		return selectedViewpoints;
	}


	/**
	 *
	 * @param language
	 *            the {@link ArchitectureDescriptionLanguage} to contribute
	 * @param tConfiguration
	 *            the {@link TableConfiguration} to register
	 * @param concerns
	 *            the {@link Concern}s to reference
	 * @return
	 *         the {@link PapyrusTable} allowing to register the wanted {@link TableConfiguration}
	 */
	private PapyrusTable createPapyrusTableRepresentation(final ArchitectureDescriptionLanguage language, final TableConfiguration tConfiguration, final List<Concern> concerns) {
		// 1. create the Papyrus Table
		final PapyrusTable pTable = RepresentationFactory.eINSTANCE.createPapyrusTable();

		// 1.1 init some obvious fields
		pTable.setDescription(tConfiguration.getDescription());
		pTable.setIcon(tConfiguration.getIconPath());
		pTable.setConfiguration(tConfiguration);
		pTable.getConcerns().addAll(concerns);

		// 2.we create the name fo the PapyrusTable with this pattern : languageName + split on upper case of the table configuration name
		final StringBuilder builder = new StringBuilder(language.getName());
		String[] camelCaseWords = tConfiguration.getName().split("(?=[A-Z])"); //$NON-NLS-1$
		for (String current : camelCaseWords) {
			builder.append(" "); //$NON-NLS-1$
			builder.append(current);

		}

		pTable.setName(builder.toString());


		// 3. the implementationID it the type of the table configuration
		pTable.setImplementationID(tConfiguration.getType());


		// 4. we create the ID for the PapyrusTable. We decided to use this pattern: the architecture framework plugin name + '.' + table + '.' tableType
		final URI languageResourceURI = language.eResource().getURI();
		List<String> segList = languageResourceURI.segmentsList();
		Assert.isTrue(segList.size() >= 2);
		Assert.isTrue("resource".equals(segList.get(0))); // we are working with a platform:/resource file, so the first segment is "resource" //$NON-NLS-1$
		final String pluginName = segList.get(1);
		final StringBuilder iDBuilder = new StringBuilder(pluginName);
		iDBuilder.append("."); //$NON-NLS-1$
		iDBuilder.append("table"); //$NON-NLS-1$
		iDBuilder.append("."); //$NON-NLS-1$
		iDBuilder.append(tConfiguration.getType());
		pTable.setId(iDBuilder.toString());


		return pTable;

	}

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext) evaluationContext;
			Object selectionTmp = context.getVariable("selection"); //$NON-NLS-1$
			if (selectionTmp instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) selectionTmp;
				if (selection.size() == 1 && selection.getFirstElement() instanceof ArchitectureDescriptionLanguage) {
					this.adl = new WeakReference<>((ArchitectureDescriptionLanguage) selection.getFirstElement());
					setBaseEnabled(true);
					return;
				}
			}
		}
		setBaseEnabled(false);
	}

	/**
	 *
	 * @author Vincent LORENZO
	 *         This class allows to get all {@link ArchitectureViewpoint} from a {@link ArchitectureDescriptionLanguage}
	 *
	 */
	private static class ArchitectureViewpointContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof ArchitectureDescriptionLanguage) {
				return ((ArchitectureDescriptionLanguage) inputElement).getViewpoints().toArray();
			}
			return new Object[] {};
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof ArchitectureDescriptionLanguage) {
				return ((ArchitectureDescriptionLanguage) parentElement).getViewpoints().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			// nothing to do
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof ArchitectureDescriptionLanguage) {
				return ((ArchitectureDescriptionLanguage) element).getViewpoints().size() > 0;
			}
			return false;
		}

	}

	/**
	 *
	 * @author Vincent LORENZO
	 *         This class allows to get all {@link Concerns} from a {@link ArchitectureDomain}
	 *
	 */
	private static class ConcernsContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof ArchitectureDomain) {
				return ((ArchitectureDomain) inputElement).getConcerns().toArray();
			}
			return new Object[] {};
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof ArchitectureDomain) {
				return ((ArchitectureDomain) parentElement).getConcerns().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			// nothing to do
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof ArchitectureDomain) {
				return ((ArchitectureDomain) element).getConcerns().size() > 0;
			}
			return false;
		}

	}
}
