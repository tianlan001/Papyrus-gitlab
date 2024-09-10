/*****************************************************************************
 * Copyright (c) 2008, 2016, 2021-2023 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - manage models by URI, not IFile (CDO)
 *  Christian W. Damus (CEA) - bug 410346
 *  Christian W. Damus (CEA) - bug 431953 (pre-requisite refactoring of ModelSet service start-up)
 *  Christian W. Damus (CEA) - bug 437217
 *  Christian W. Damus - bugs 469464, 469188, 485220, 496299
 *  Pauline DEVILLE (CEA LIST) - bug 571948
 *  Vincent LORENZO (CEA) - vincent.lorenzo@cea.fr - bug 581073
 *  Patrick Tessier (CEA LIST) - bug 562218
 *  Vincent Lorenzo (CEA LIST) - bug 581653
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.editor;

import static org.eclipse.papyrus.infra.core.Activator.log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.core.language.ILanguageChangeListener;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.language.LanguageChangeEvent;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IContentChangedListener;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.DiSashModelManager;
import org.eclipse.papyrus.infra.core.sasheditor.editor.AbstractMultiPageSashEditor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceMultiException;
import org.eclipse.papyrus.infra.core.services.ServiceStartKind;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.api.IModelSetService;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.api.services.IStatusService;
import org.eclipse.papyrus.infra.ui.contentoutline.ContentOutlineRegistry;
import org.eclipse.papyrus.infra.ui.editor.IReloadableEditor.DirtyPolicy;
import org.eclipse.papyrus.infra.ui.editor.reload.EditorReloadEvent;
import org.eclipse.papyrus.infra.ui.editor.reload.IEditorReloadListener;
import org.eclipse.papyrus.infra.ui.internal.services.status.BeginStatusEvent;
import org.eclipse.papyrus.infra.ui.internal.services.status.EndStatusEvent;
import org.eclipse.papyrus.infra.ui.internal.services.status.StepStatusEvent;
import org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent;
import org.eclipse.papyrus.infra.ui.lifecycleevents.IEditorInputChangedListener;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveAndDirtyService;
import org.eclipse.papyrus.infra.ui.messages.Messages;
import org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor.ActionBarContributorRegistry;
import org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor.CoreComposedActionBarContributor;
import org.eclipse.papyrus.infra.ui.services.EditorLifecycleManager;
import org.eclipse.papyrus.infra.ui.services.internal.EditorLifecycleManagerImpl;
import org.eclipse.papyrus.infra.ui.services.internal.InternalEditorLifecycleManager;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Multi diagram editor allowing to plug various kind of editors. Editors are
 * registered with the help of the Eclipse extension mechanism. This
 * implementation allows to register editors and context separately. An editor
 * should specify which context it need to run. This multi diagram editor allows
 * to show editor side by side in one or more sash windows.
 *
 * The real implementation for the generic type T of SashMultiPageEditorPart is
 * actually di2.Diagram
 *
 * @author cedric dumoulin
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 * @author <a href="mailto:thomas.szadel@atosorigin.com">Thomas Szadel</a>
 *         Refactoring.
 *
 * @since 1.2
 */
public class CoreMultiDiagramEditor extends AbstractMultiPageSashEditor implements IMultiDiagramEditor, ITabbedPropertySheetPageContributor, IGotoMarker, IEditingDomainProvider {

	/** ContentOutline registry */
	private ContentOutlineRegistry contentOutlineRegistry;

	/** Services registry. Used to get registered services */
	private ServicesRegistry servicesRegistry;

	/**
	 * ActionBarContributor Registry. Allows to get an ActionBar by its Id. The
	 * registry is initialized from the Eclipse extension mechanism.
	 */
	private ActionBarContributorRegistry actionBarContributorRegistry;

	/** SashModelMngr to add pages */
	protected DiSashModelManager sashModelMngr;

	/**
	 * Service used to maintain the dirty state and to perform save and saveAs.
	 */
	protected ISaveAndDirtyService saveAndDirtyService;

	private final List<IPropertySheetPage> propertiesPages = new LinkedList<>();

	private final List<Runnable> closeActions = new ArrayList<>();

	private boolean needsRefresh;

	private IModelSetService modelSetService;

	private IServiceRegistryIndexer serviceRegistryIndexer;

	/**
	 * Listener on {@link ISaveAndDirtyService#addInputChangedListener(IEditorInputChangedListener)}
	 */
	private static class EditorInputChangedListener implements IEditorInputChangedListener {

		private CoreMultiDiagramEditor editor;

		public EditorInputChangedListener(CoreMultiDiagramEditor editor) {
			this.editor = editor;
		}

		/**
		 * This method is called when the editor input is changed from the
		 * ISaveAndDirtyService.
		 *
		 * @see org.eclipse.papyrus.infra.ui.lifecycleevents.IEditorInputChangedListener#editorInputChanged(org.eclipse.ui.part.FileEditorInput)
		 *
		 * @param fileEditorInput
		 */
		@Override
		public void editorInputChanged(FileEditorInput fileEditorInput) {
			// Change the editor input.
			editor.setInputWithNotify(fileEditorInput);
			editor.setPartName(fileEditorInput.getName());
		}

		/**
		 * The isDirty flag has changed, reflect its new value
		 *
		 * @see org.eclipse.papyrus.infra.ui.lifecycleevents.IEditorInputChangedListener#isDirtyChanged()
		 *
		 */
		@Override
		public void isDirtyChanged() {

			// Run it in async way.
			editor.getSite().getShell().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					// editor can be null if this object has been finalized, but
					// still queued in the asyncExec queue.
					// This can happen if the editor is disposed, but some run still in
					// the exec queue.
					// When the method is executed asynchronously, the object is already finalized, and so
					// editor is null.
					if (editor == null) {
						return;
					}
					editor.firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			});
		}

		public void dispose() {
			this.editor = null;
		}
	}

	protected EditorInputChangedListener editorInputChangedListener;

	private TransactionalEditingDomain transactionalEditingDomain;

	/**
	 * Object managing models lifeCycle.
	 */
	protected ModelSet resourceSet;

	/**
	 * Cached event that can be reused.
	 */
	protected DoSaveEvent lifeCycleEvent;

	private class ContentChangedListener implements IContentChangedListener {

		/**
		 * Called when the content is changed. RefreshTabs.
		 */
		@Override
		public void contentChanged(ContentEvent event) {
			scheduleRefresh();
		}
	}

	/**
	 * A listener on model change events.
	 */
	private ContentChangedListener contentChangedListener;

	/**
	 * Undo context used to have the same undo context in all Papyrus related
	 * views and editors. TODO : move away, use a version independent of GMF,
	 * add a listener that will add the context to all commands modifying
	 * attached Resources (==> linked to ModelSet ?)
	 */
	private IUndoContext undoContext;

	/**
	 * Editor reload listeners.
	 */
	private CopyOnWriteArrayList<IEditorReloadListener> reloadListeners = new CopyOnWriteArrayList<>();

	/**
	 * A pending reload operation (awaiting next activation of the editor).
	 */
	private final AtomicReference<DeferredReload> pendingReload = new AtomicReference<>();

	/**
	 * The OSGI service which manage the display of the progression
	 */
	private IStatusService loadingStatusService;

	public CoreMultiDiagramEditor() {
		super();

		addSelfReloadListener();

		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference<IStatusService> statusServiceRef = bundleContext.getServiceReference(IStatusService.class);
		loadingStatusService = bundleContext.getService(statusServiceRef);
		// get the OSGI context
		final ServiceReference<IModelSetService> serviceRef = bundleContext.getServiceReference(IModelSetService.class);
		modelSetService = bundleContext.getService(serviceRef);
		final ServiceReference<IServiceRegistryIndexer> serviceregistryIndexerRef = bundleContext.getServiceReference(IServiceRegistryIndexer.class);
		serviceRegistryIndexer = bundleContext.getService(serviceregistryIndexerRef);

	}

	/**
	 * Get the contentOutlineRegistry. Create it if needed.
	 *
	 * @return the contentOutlineRegistry
	 */
	protected ContentOutlineRegistry getContentOutlineRegistry() {
		if (contentOutlineRegistry == null) {
			createContentOutlineRegistry();
		}

		return contentOutlineRegistry;
	}

	/**
	 * Create the contentOutlineRegistry.
	 */
	private void createContentOutlineRegistry() {
		contentOutlineRegistry = new ContentOutlineRegistry(this, Activator.PLUGIN_ID);
	}

	/**
	 * Returns the service registry associated to the editor.
	 *
	 * @return the servicesRegistry The registry.
	 */
	@Override
	public ServicesRegistry getServicesRegistry() {
		if (servicesRegistry == null) {
			servicesRegistry = createServicesRegistry();
		}
		return servicesRegistry;
	}

	/**
	 * Create the ServicesRegistry.
	 *
	 * @return
	 */
	private ServicesRegistry createServicesRegistry() {
		return serviceRegistryIndexer.createServiceRegistryAttachTo(this);
	}

	/**
	 * Do nothing as we create the provider before any calls to this method.
	 * Should not be called by subclasses.
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.AbstractMultiPageSashEditor#createPageProvider()
	 */
	@Override
	protected ISashWindowsContentProvider createPageProvider() {
		throw new UnsupportedOperationException("Not implemented. Should not be called as the ContentProvider is already initialized."); //$NON-NLS-1$
	}

	/**
	 * Create the pageContentProvider.
	 *
	 * Removed since 0.10.0
	 *
	 * @param pageFactory
	 * @param diResource
	 *            Resource used to load/save the SashModel.
	 *
	 *
	 */
	// protected ISashWindowsContentProvider createPageProvider(IPageModelFactory pageFactory, Resource diResource, TransactionalEditingDomain editingDomain) {
	//
	// sashModelMngr = new TransactionalDiSashModelMngr(pageFactory, diResource, editingDomain);
	//
	// ISashWindowsContentProvider pageProvider = sashModelMngr.getISashWindowsContentProvider();
	//
	// return pageProvider;
	// }

	/**
	 * Get The {@link IPageMngr} used to add, open, remove or close a diagram in
	 * the SashWindow. This method is available as soon as the {@link CoreMultiDiagramEditor#init(IEditorSite, IEditorInput)} method is
	 * called.
	 *
	 * @return
	 */
	protected IPageManager getIPageManager() throws IllegalStateException {
		try {
			return sashModelMngr.getIPageManager();
		} catch (Exception e) {
			throw new IllegalStateException("Method should be called after CoreMultiDiagramEditor#init(IEditorSite, IEditorInput) is called"); //$NON-NLS-1$
		}
	}

	/**
	 * Get the ActionBarContributorRegistry. Creates it if necessary.
	 *
	 * @return
	 */
	protected ActionBarContributorRegistry getActionBarContributorRegistry() {
		if (actionBarContributorRegistry == null) {

			// Try to got it from CoreComposedActionBarContributor
			// Get it from the contributor.
			IEditorActionBarContributor contributor = getEditorSite().getActionBarContributor();
			if (contributor instanceof CoreComposedActionBarContributor) {
				log.debug(getClass().getSimpleName() + " - ActionBarContributorRegistry loaded from CoreComposedActionBarContributor."); //$NON-NLS-1$
				actionBarContributorRegistry = ((CoreComposedActionBarContributor) contributor).getActionBarContributorRegistry();

			} else {
				// Create a registry.
				log.debug(getClass().getSimpleName() + " - create an ActionBarContributorRegistry."); //$NON-NLS-1$
				actionBarContributorRegistry = createActionBarContributorRegistry();
			}
		}

		return actionBarContributorRegistry;
	}

	/**
	 * Create the ActionBarContributorRegistry.
	 *
	 * @return
	 */
	private ActionBarContributorRegistry createActionBarContributorRegistry() {
		return new ActionBarContributorRegistry(Activator.PLUGIN_ID);
	}

	/**
	 *
	 *
	 * @param adapter
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {

		if (ServicesRegistry.class == adapter) {
			return getServicesRegistry();
		}

		if (IPageManager.class == adapter) {
			return getIPageManager();
		}

		if (IPropertySheetPage.class == adapter) {
			// Do not test if tabbedPropertySheetPage is null before calling new
			// this is managed by Eclipse which only call current method when
			// necessary
			return getPropertySheetPage();
		}

		// Add a viewer
		if (IContentOutlinePage.class == adapter) {
			try {
				ContentOutlineRegistry outlineRegistry = getContentOutlineRegistry();
				if (outlineRegistry == null) {
					return null;
				}
				IContentOutlinePage contentOutline = outlineRegistry.getContentOutline();
				if (contentOutline != null) {
					return contentOutline;
				}
			} catch (BackboneException e) {
				// Ignore: There is not registered outline.
			}
		}

		if (EditingDomain.class == adapter || TransactionalEditingDomain.class == adapter) {
			return transactionalEditingDomain;
		}

		/*
		 * Return context used for undo/redo. All papyrus views should use this
		 * context. The prefer way to get this is to use undoContext =
		 * servicesRegistry.getService(IUndoContext.class);
		 */
		if (IUndoContext.class == adapter) {
			return undoContext;
		}

		// EMF requirements
		if (IEditingDomainProvider.class == adapter) {
			return this;
		}

		if (adapter == ISelection.class) {
			return getSite().getSelectionProvider().getSelection();
		}

		if (adapter == IReloadableEditor.class) {
			return createReloadAdapter();
		}

		return super.getAdapter(adapter);
	}

	/**
	 * Init the editor.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		loadingStatusService.trigger(new BeginStatusEvent(Messages.CoreMultiDiagramEditor_StatisDialog_Title, Messages.CoreMultiDiagramEditor_StatisDialog_LoadingPapyrusMessage, 4));
		try {
			// Init super
			super.init(site, input);

			// Set editor name
			setPartName(input.getName());
			initContents();
		} catch (Exception e) {
			if (loadingStatusService != null) {
				loadingStatusService.trigger(new EndStatusEvent());
			}
			throw e;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		try {
			loadingStatusService.trigger(new StepStatusEvent(Messages.CoreMultiDiagramEditor_StatisDialog_CreatePartControlMessage));
			super.createPartControl(parent);

			// Fire the PreDisplay event synchronously, so that listeners can continue
			// setting up the UI before the contents are actually rendered fully
			getLifecycleManager().firePreDisplay(this);

			// Fire the PostDisplay event asynchronously, to leave time to the Eclipse
			// framework to actually display the contents of the editor
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					// Because we are asynchronous, the editor may already have been disposed
					// (Especially in the case of tests running in the UI Thread)
					try {
						if (servicesRegistry == null) {
							return;
						}
						getLifecycleManager().firePostDisplay(CoreMultiDiagramEditor.this);
					} finally {
						loadingStatusService.trigger(new EndStatusEvent());
					}
				}
			});
		} catch (Exception e) {
			if (loadingStatusService != null) {
				loadingStatusService.trigger(new EndStatusEvent());
			}
			throw e;
		}
	}

	protected void loadModelAndServices() throws PartInitException {
		// Create ServicesRegistry and register services
		servicesRegistry = createServicesRegistry();

		// Add itself as a service
		servicesRegistry.add(IMultiDiagramEditor.class, 1, this);

		// Create lifeCycle event provider and the event that is used when the editor fire a save event.
		// lifeCycleEventsProvider = new LifeCycleEventsProvider();
		// lifeCycleEvent = new DoSaveEvent(servicesRegistry, this);
		// servicesRegistry.add(ILifeCycleEventsProvider.class, 1, lifeCycleEventsProvider);

		// register services
		servicesRegistry.add(ActionBarContributorRegistry.class, 1, getActionBarContributorRegistry());
		// servicesRegistry.add(TransactionalEditingDomain.class, 1, transactionalEditingDomain);
		// servicesRegistry.add(DiResourceSet.class, 1, resourceSet);

		// Create and initalize editor icons service
		// PageIconsRegistry pageIconsRegistry = new PageIconsRegistry();
		// PluggableEditorFactoryReader editorReader = new PluggableEditorFactoryReader(Activator.PLUGIN_ID);
		// editorReader.populate(pageIconsRegistry);
		// servicesRegistry.add(IPageIconsRegistry.class, 1, pageIconsRegistry);


		// Create PageModelRegistry requested by content provider.
		// Also populate it from extensions.
		// PageModelFactoryRegistry pageModelRegistry = new PageModelFactoryRegistry();
		// editorReader.populate(pageModelRegistry, servicesRegistry);

		// TODO : create appropriate Resource for the contentProvider, and pass it here.
		// This will allow to remove the old sash stuff.
		// setContentProvider(createPageProvider(pageModelRegistry, resourceSet.getDiResource(), transactionalEditingDomain));
		// servicesRegistry.add(ISashWindowsContentProvider.class, 1, getContentProvider());
		// servicesRegistry.add(IPageMngr.class, 1, getIPageMngr());

		// register a basic label provider
		// adapter factory used by EMF objects
		AdapterFactory factory = null;
		try {
			EditingDomain domain = ServiceUtils.getInstance().getTransactionalEditingDomain(servicesRegistry);
			if (domain instanceof AdapterFactoryEditingDomain) {
				// Use the adapter factory already provided by this editing domain
				factory = ((AdapterFactoryEditingDomain) domain).getAdapterFactory();
			}
		} catch (ServiceException e) {
			// OK, there's no editing domain. That's fine
		}

		if (factory == null) {
			// Must create a new adapter factory
			factory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}

		/** label provider for EMF objects */
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(factory) {

			/**
			 * This implements {@link ILabelProvider}.getText by forwarding it
			 * to an object that implements {@link IItemLabelProvider#getText
			 * IItemLabelProvider.getText}
			 */
			@Override
			public String getText(Object object) {
				// Get the adapter from the factory.
				//
				IItemLabelProvider itemLabelProvider = (IItemLabelProvider) adapterFactory.adapt(object, IItemLabelProvider.class);
				if (object instanceof EObject) {
					if (((EObject) object).eIsProxy()) {
						return "Proxy - " + object; //$NON-NLS-1$
					}
				}
				return itemLabelProvider != null ? itemLabelProvider.getText(object) : object == null ? "" : object.toString(); //$NON-NLS-1$
			}
		};
		servicesRegistry.add(ILabelProvider.class, 1, labelProvider);

		EditorLifecycleManager lifecycleManager = new EditorLifecycleManagerImpl();
		servicesRegistry.add(EditorLifecycleManager.class, 1, lifecycleManager, ServiceStartKind.LAZY);

		// Start servicesRegistry
		URI uri = EditorUtils.getResourceURI(getEditorInput());

		try {
			// Start the ModelSet first, and load if from the specified File.
			// Also start me so that I may be retrieved from the registry by other services
			List<Class<?>> servicesToStart = new ArrayList<>(1);
			servicesToStart.add(ModelSet.class);
			servicesToStart.add(IMultiDiagramEditor.class);

			servicesRegistry.startServicesByClassKeys(servicesToStart);

			resourceSet = modelSetService.loadModelSet(uri, servicesRegistry);

			serviceRegistryIndexer.associateServiceRegistry(resourceSet, servicesRegistry);


			// start remaining services
			servicesRegistry.startRegistry();

			// In case of a shard
			String name = java.net.URLDecoder.decode(uri.lastSegment(), "UTF-8"); //$NON-NLS-1$
			if (!name.equals(getPartName())) {
				setPartName(name);
			}
		} catch (ServiceException e) {
			log.error(e);
			// throw new PartInitException("could not initialize services", e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}

		// Get required services

		try {
			transactionalEditingDomain = servicesRegistry.getService(TransactionalEditingDomain.class);
			sashModelMngr = servicesRegistry.getService(DiSashModelManager.class);

			saveAndDirtyService = servicesRegistry.getService(ISaveAndDirtyService.class);
			undoContext = servicesRegistry.getService(IUndoContext.class);

			servicesRegistry.getService(ILanguageService.class).addLanguageChangeListener(createLanguageChangeListener());
		} catch (ServiceException e) {
			log.error("A required service is missing.", e); //$NON-NLS-1$
			// if one of the services above fail to start, the editor can't run
			// => stop
			throw new PartInitException("could not initialize services", e); //$NON-NLS-1$
		}


		// Listen on input changed from the ISaveAndDirtyService
		editorInputChangedListener = new EditorInputChangedListener(this);
		saveAndDirtyService.addInputChangedListener(editorInputChangedListener);
		getLifecycleManager().firePostInit(this);
	}

	private ILanguageChangeListener createLanguageChangeListener() {
		return new ILanguageChangeListener() {

			@Override
			public void languagesChanged(LanguageChangeEvent event) {
				// Re-load the editor if languages changed, because new ModelSet configurations may be required
				if (event.getType() == LanguageChangeEvent.ADDED) {
					new UIJob(getSite().getShell().getDisplay(), NLS.bind("Reload editor {0}", getTitle())) { //$NON-NLS-1$

						@Override
						public IStatus runInUIThread(IProgressMonitor monitor) {
							IStatus result = Status.OK_STATUS;
							monitor = SubMonitor.convert(monitor, IProgressMonitor.UNKNOWN);

							try {
								ISashWindowsContainer container = getISashWindowsContainer();
								if ((container != null) && !container.isDisposed()) {
									IReloadableEditor.ReloadReason reason = IReloadableEditor.ReloadReason.RESOURCES_CHANGED;

									DirtyPolicy dirtyPolicy = DirtyPolicy.getDefault();
									try {
										IReloadableEditor.Adapter.getAdapter(CoreMultiDiagramEditor.this).reloadEditor(resourceSet.getResources(), reason, dirtyPolicy);
									} catch (CoreException e) {
										result = e.getStatus();
									}
								}
							} finally {
								monitor.done();
							}

							return result;
						}
					}.schedule();
				}
			}
		};
	}

	private InternalEditorLifecycleManager getLifecycleManager() {
		// I've been disposed
		if (servicesRegistry == null) {
			return null;
		}
		try {
			return (InternalEditorLifecycleManager) servicesRegistry.getService(EditorLifecycleManager.class);
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}
		return null;
	}

	protected void loadNestedEditors() throws PartInitException {
		ISashWindowsContentProvider contentProvider = null;
		try {
			contentProvider = servicesRegistry.getService(ISashWindowsContentProvider.class);
		} catch (ServiceException ex) {
			log.error("A required service is missing.", ex); //$NON-NLS-1$
			// if one of the services above fail to start, the editor can't run
			// => stop
			throw new PartInitException("could not initialize services", ex); //$NON-NLS-1$
		}

		// Set the content provider providing editors.
		setContentProvider(contentProvider);

		// Listen on contentProvider changes
		if (contentChangedListener == null) {
			contentChangedListener = new ContentChangedListener();
		}
		sashModelMngr.getSashModelContentChangedProvider().addListener(contentChangedListener);

		IEditorInput input = getEditorInput();

		if (input instanceof IPapyrusPageInput) {
			IPapyrusPageInput papyrusPageInput = (IPapyrusPageInput) input;
			final IPageManager pageManager = getIPageManager();

			if (papyrusPageInput.closeOtherPages()) {
				pageManager.closeAllOpenedPages();
			}

			for (URI pageIdentifierURI : papyrusPageInput.getPages()) {
				final EObject pageIdentifier = resourceSet.getEObject(pageIdentifierURI, true);
				if (!pageManager.allPages().contains(pageIdentifier)) {
					Activator.log.warn("The object " + pageIdentifier + " does not reference an existing page"); //$NON-NLS-1$ //$NON-NLS-2$
					continue;
				}

				if (pageManager.isOpen(pageIdentifier)) {
					pageManager.selectPage(pageIdentifier);
				} else {
					pageManager.openPage(pageIdentifier);
				}
			}
		}
	}

	protected void warnUser(ModelMultiException e) {
		Activator.log.error(e);
		MessageDialog.openError(getSite().getShell(), "Error", String.format("Your model is corrupted, invalid links have been found :\n" + "%s" + "It is recommended to fix it before editing it", e.getMessage())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * Activate this editor. Called after the SWT.control is created.
	 */
	@Override
	protected void activate() {
		super.activate();

		initFolderTabMenus();

		try {
			// Register ISashWindowsContainer as service
			// Should be done only once the container is ready.
			getServicesRegistry().add(ISashWindowsContainer.class, 1, getISashWindowsContainer());
			getServicesRegistry().startServicesByClassKeys(ISashWindowsContainer.class);
			// Let the IPageMngr use the ISashWindowsContainer to discover current folder
			// This should be done after SashWindowContainer initialization.
			// DiSashModelManager sashModelManager = getServicesRegistry().getService(DiSashModelManager.class);
			sashModelMngr.setCurrentFolderAndPageMngr(getISashWindowsContainer());

		} catch (ServiceException e) {
			log.error(e);
		}

	}

	/**
	 * Init the contextual menu shown in the folder tabs. This popup menu is
	 * contributed by the help of Eclipse extensions, using the Commands
	 * framework. I.e, to add a menu item, create a menu, a command and an
	 * handler in the extension.
	 */
	protected void initFolderTabMenus() {
		ISashWindowsContainer container = getISashWindowsContainer();

		// TODO : use a constant
		MenuManager menuManager = new MenuManager("tabmenu"); //$NON-NLS-1$
		menuManager.add(new Separator("tabcommands")); //$NON-NLS-1$
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		container.setFolderTabMenuManager(menuManager);

		// TODO : use a constant
		getSite().registerContextMenu("org.eclipse.papyrus.infra.core.editor.ui.tabmenu", menuManager, getSite().getSelectionProvider()); //$NON-NLS-1$

	}

	/**
	 * Overrides getPropertySheetPage.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor#getPropertySheetPage()
	 */
	public IPropertySheetPage getPropertySheetPage() {
		IPropertySheetPage propertyPage = null;
		// 1. get the service
		final IPropertySheetPageProviderService provider = getPropertySheetPageProviderService();
		if (provider != null) {
			// 2. get the property page
			propertyPage = provider.createPropertySheetPage(this, getEditorSite().getId()); // call implies to get value "org.eclipse.papyrus.infra.core.papyrusEditor" and requires to find the editor in the Property Service
		}
		if (propertyPage != null) {
			propertiesPages.add(propertyPage);
		}
		return propertyPage;
	}

	/**
	 * This method returns the {@link IPropertySheetPage}
	 *
	 * @return
	 *         the property sheet page provider service
	 */
	private IPropertySheetPageProviderService getPropertySheetPageProviderService() {
		// 1. get the bundle context
		final BundleContext bc = Activator.getDefault().getBundle().getBundleContext();
		// 2. find the property page service
		final ServiceReference<IPropertySheetPageProviderService> servreg = bc.getServiceReference(IPropertySheetPageProviderService.class);
		if (servreg != null) {
			final IPropertySheetPageProviderService provider = bc.getService(servreg);
			if (provider != null) {
				return provider;
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		// 1. get the service
		final IPropertySheetPageProviderService provider = getPropertySheetPageProviderService();
		if (provider != null) {
			// 2. dispose each property page
			for (IPropertySheetPage propertySheetPage : this.propertiesPages) {
				provider.dispose(propertySheetPage);
			}
		}

		propertiesPages.clear();

		// Forget the outline page(s)
		contentOutlineRegistry = null;

		super.dispose();

		if (this.serviceRegistryIndexer != null) {
			if (this.resourceSet != null) {
				serviceRegistryIndexer.getServiceRegistryMap().remove(this.resourceSet);
			}
			serviceRegistryIndexer.getServiceRegistryMap().remove(this);
			serviceRegistryIndexer = null;
		}
	}

	private IReloadableEditor createReloadAdapter() {

		return new IReloadableEditor() {

			@Override
			public void reloadEditor(Collection<? extends Resource> triggeringResources, ReloadReason reason, DirtyPolicy dirtyPolicy) throws CoreException {
				// Attempt to re-load, later
				pendingReload.set(new DeferredReload(triggeringResources, reason, dirtyPolicy));

				// If I am already active, then do it now. Or, if we're not going to ask the user about it, also do it now
				IWorkbenchPage page = getSite().getPage();
				if ((page.getActiveEditor() == CoreMultiDiagramEditor.this) || (dirtyPolicy != DirtyPolicy.PROMPT_TO_SAVE)) {
					pendingReload.get().reload();
				}
			}

			@Override
			public void addEditorReloadListener(IEditorReloadListener listener) {
				reloadListeners.addIfAbsent(listener);
			}

			@Override
			public void removeEditorReloadListener(IEditorReloadListener listener) {
				reloadListeners.remove(listener);
			}
		};
	}

	private void addSelfReloadListener() {
		createReloadAdapter().addEditorReloadListener(new IEditorReloadListener() {

			@Override
			public void editorAboutToReload(EditorReloadEvent event) {
				event.putContext(new MultiDiagramEditorSelectionContext(event.getEditor()));
			}

			@Override
			public void editorReloaded(EditorReloadEvent event) {
				((MultiDiagramEditorSelectionContext) event.getContext()).restore(event.getEditor());
			}
		});
	}

	/**
	 * Register an action to be run when I am closed. Any number of such actions may
	 * be added. note that close actions also run on re-load, which behaves to all
	 * outward appearances like a close and re-open.
	 *
	 * @param closeAction
	 *            an action to run when I am closed
	 */
	public void onClose(Runnable closeAction) {
		closeActions.add(closeAction);
	}

	@Override
	protected void deactivate() {
		// Make sure we close the progress dialog if it is deactivate because an exception has been catch (Bug 571948)
		if (loadingStatusService != null) {
			loadingStatusService.trigger(new EndStatusEvent());
		}

		getLifecycleManager().fireBeforeClose(this);
		if (sashModelMngr != null) {
			sashModelMngr.getSashModelContentChangedProvider().removeListener(contentChangedListener);
		}

		super.deactivate();

		// dispose available service
		if (servicesRegistry != null) {
			try {
				servicesRegistry.disposeRegistry();
				servicesRegistry = null;
			} catch (ServiceMultiException e) {
				log.error(e);
			}
		}

		if (contentChangedListener != null) {
			this.contentChangedListener = null;
		}

		if (editorInputChangedListener != null) {
			this.editorInputChangedListener.dispose();
			this.editorInputChangedListener = null;
		}

		for (Runnable next : closeActions) {
			try {
				next.run();
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in close action", e); //$NON-NLS-1$
			}
		}
		closeActions.clear();

		transactionalEditingDomain = null;
		serviceRegistryIndexer.getServiceRegistryMap().remove(this.resourceSet);
		resourceSet = null;
		undoContext = null;
		saveAndDirtyService = null;
		sashModelMngr = null;
	}

	void initContents() throws PartInitException {
		loadingStatusService.trigger(new StepStatusEvent(Messages.CoreMultiDiagramEditor_StatisDialog_LoadServicesAndModelMessage));
		loadModelAndServices();
		loadingStatusService.trigger(new StepStatusEvent(Messages.CoreMultiDiagramEditor_StatisDialog_LoadNestedEditorMessage));
		loadNestedEditors();
	}

	@Override
	public void setFocus() {
		super.setFocus();

		DeferredReload reload = pendingReload.get();
		if (reload != null) {
			reload.reload();
		}
	}

	private void doReload() throws CoreException {
		final IWorkbenchPage page = getSite().getPage();
		final IWorkbenchPart activePart = page.getActivePart();
		final IEditorPart activeEditor = page.getActiveEditor();

		final Iterable<? extends IEditorReloadListener> listeners = ImmutableList.copyOf(reloadListeners);
		final EditorReloadEvent event = new EditorReloadEvent(CoreMultiDiagramEditor.this);

		try {
			event.dispatchEditorAboutToReload(listeners);

			deactivate();

			initContents();

			activate();

			// My self-listener will be first, to ensure that the pages are all restored before dependents run
			event.dispatchEditorReloaded(listeners);
		} finally {
			event.dispose();

			// Ensure that the editor previously active is active again (if it still exists)
			if ((activeEditor != null) && page.isPartVisible(activeEditor)) {
				page.activate(activeEditor);
			}

			// Ensure that the part previously active is active again (if it still exists and is not the active editor)
			if ((activePart != null) && (activePart != activeEditor) && page.isPartVisible(activePart)) {
				page.activate(activePart);
			}
		}

	}

	/**
	 * Overrides doSave.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {

		saveAndDirtyService.doSave(monitor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		// May happen if the editor has not yet been initialized. In this case, the editor cannot be dirty, so we simply return false.
		// Bug 410286: The isDirty() method can also be called /after/ the editor has been disposed. Most likely an Eclipse bug?
		if (saveAndDirtyService == null) {
			return false;
		}
		return saveAndDirtyService.isDirty();
	}

	/**
	 * Overrides doSaveAs.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {

		saveAndDirtyService.doSaveAs();
	}

	/**
	 * Overrides isSaveAsAllowed.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Overrides getContributorId.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	@Override
	public String getContributorId() {
		// return Activator.PLUGIN_ID;
		return "TreeOutlinePage"; //$NON-NLS-1$

	}

	// implements IDiagramWorkbenchPart to restore GMF standard behavior
	// and delegate to the activeEditor

	/**
	 * Overrides getDiagram.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart#getDiagram()
	 */
	// public org.eclipse.gmf.runtime.notation.Diagram getDiagram() {
	// IEditorPart activeEditor = getActiveEditor();
	// if(activeEditor instanceof DiagramEditor) {
	// return ((DiagramEditor)activeEditor).getDiagram();
	// } else {
	// return null;
	// }
	// }

	/**
	 * This method is called from a GMF diagram. It should only be called from GMF diagram code. Normally, the Diagram under the Mouse is a GMF
	 * Diagram. The active Diagram can be another Diagram, not
	 * under the mouse. This is a GMF issue.
	 */
	// public DiagramEditPart getDiagramEditPart() {
	//
	// // Get the editor under the mouse
	// // IEditorPart activeEditor = rootContainer.getEditorUnderMouse();
	// IEditorPart activeEditor = getActiveEditor();
	// if(activeEditor == null) {
	// return null;
	// }
	// // IEditorPart activeEditor = getActiveEditor();
	// if(activeEditor instanceof DiagramEditor) {
	// return ((DiagramEditor)activeEditor).getDiagramEditPart();
	// } else {
	// // This case should never happen.
	// // Return null, as the GMF runtime now support it (since 093009)
	// return null;
	// }
	// }

	/**
	 * Overrides getDiagramGraphicalViewer.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart#getDiagramGraphicalViewer()
	 */
	// public IDiagramGraphicalViewer getDiagramGraphicalViewer() {
	// IEditorPart activeEditor = getActiveEditor();
	// if(activeEditor instanceof DiagramEditor) {
	// return ((DiagramEditor)activeEditor).getDiagramGraphicalViewer();
	// } else {
	// return null;
	// }
	// }

	/**
	 * Overrides getEditingDomain.
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	@Override
	public EditingDomain getEditingDomain() {
		return transactionalEditingDomain;
	}

	/**
	 * Throws an UnsupportedOperationException.
	 *
	 * @see org.eclipse.papyrus.infra.core.editor.IMultiDiagramEditor#getDiagramEditDomain()
	 */
	// public DiagramEditDomain getDiagramEditDomain() {
	// throw new UnsupportedOperationException("Not implemented. Should not be called.");
	// }


	/**
	 * Change the editor input.<BR>
	 * <U>Note</U>: that method should be called within the UI-Thread.
	 *
	 * @see org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor#setEditorInput(org.eclipse.ui.IEditorInput)
	 *
	 * @param newInput
	 *            The new input
	 * @deprecated Not used anymore
	 */

	@Override
	@Deprecated
	public void setEditorInput(IEditorInput newInput) {
		setInputWithNotify(newInput);
		setPartName(newInput.getName());
	}

	@Override
	@Deprecated
	public void gotoMarker(IMarker marker) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchPage page = wb.getActiveWorkbenchWindow().getActivePage();
		boolean first = true;
		for (IViewReference view : page.getViewReferences()) {
			// no longer restrict to model explorer (see bug 387578)
			IWorkbenchPart part = view.getPart(false);
			if (part instanceof IGotoMarker) {
				// activate first view implementing the IGotoMarker interface
				if (first) {
					page.activate(view.getPart(false));
					first = false;
				}
				((IGotoMarker) part).gotoMarker(marker);
			}
		}
	}

	protected void scheduleRefresh() {
		needsRefresh = true;
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				refreshTabs();
			}
		});
	}

	@Override
	protected void refreshTabs() {
		if (!needsRefresh) {
			return;
		}
		needsRefresh = false;
		super.refreshTabs();
	}

	@Override
	public synchronized IEditorPart getActiveEditor() {
		refreshTabs();
		return super.getActiveEditor();
	}

	private final class DeferredReload extends IReloadableEditor.Adapter {

		private final Collection<? extends Resource> triggeringResources;

		private final ReloadReason reason;

		private final DirtyPolicy dirtyPolicy;

		DeferredReload(Collection<? extends Resource> triggeringResources, ReloadReason reason, DirtyPolicy dirtyPolicy) {
			super(CoreMultiDiagramEditor.this);

			this.triggeringResources = ImmutableSet.copyOf(triggeringResources);
			this.reason = reason;
			this.dirtyPolicy = dirtyPolicy;
		}

		void reload() {
			try {
				reloadEditor(triggeringResources, reason, dirtyPolicy);
			} catch (CoreException e) {
				// Failed to properly unload/load in place, so just close
				getSite().getPage().closeEditor(CoreMultiDiagramEditor.this, false);

				StatusManager.getManager().handle(e.getStatus(), StatusManager.LOG | StatusManager.SHOW);
			}
		}

		@Override
		public void reloadEditor(Collection<? extends Resource> triggeringResources, ReloadReason reason, DirtyPolicy dirtyPolicy) throws CoreException {
			if (!pendingReload.compareAndSet(this, null)) {
				return;
			}

			final DirtyPolicy action = dirtyPolicy.resolve(CoreMultiDiagramEditor.this, triggeringResources, reason);

			if ((action == DirtyPolicy.SAVE) && isDirty()) {
				doSave(new NullProgressMonitor());
			}

			switch (action) {
			case SAVE:
			case DO_NOT_SAVE:
				if (reason.shouldReload(triggeringResources)) {
					// Attempt to re-load
					doReload();
				} else {
					// Just close 'er down
					getSite().getPage().closeEditor(CoreMultiDiagramEditor.this, false);
				}
				break;
			case IGNORE:
				// Pass
				break;
			default:
				throw new IllegalArgumentException("Invalid resolution of editor re-load dirty policy: " + action); //$NON-NLS-1$
			}
		}
	}
}
