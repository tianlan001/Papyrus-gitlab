/*****************************************************************************
 * Copyright (c) 2016, 2017, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *   Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - Bug 519107 (lazy diagram opening)
 *   Vincent LORENZO (CEA) vincent.lorenzo@cea.fr - bug 551530
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.part;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.SynchronizableGmfDiagramEditor;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.infra.gmfdiag.style.StylePackage;
import org.eclipse.papyrus.infra.internationalization.InternationalizationPackage;
import org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveAndDirtyService;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.contexts.IContextService;

/**
 * Common ancestor of GMF based editors for UML. This class allows to declare
 * stuff commons to all this kind of editors.
 *
 * @author cedric dumoulin
 *
 */
public class UmlGmfDiagramEditor extends SynchronizableGmfDiagramEditor implements IInternationalizationEditor {

	/**
	 * The associated Diagram.
	 */
	private Diagram diagram;

	/**
	 * Object used to synchronize the name of the editor with the name of the
	 * diagram.
	 */
	private PartNameSynchronizer partNameSynchronizer;

	/**
	 * service registry of the backbone
	 */
	private ServicesRegistry servicesRegistry;

	/**
	 * boolean indicating whether the viewer has already been initialized.
	 * Used for lazy initialization (wait, until set Focus is called) to reduce opening time
	 * of Papyrus editor.
	 */
	protected boolean viewerInitialized = false;

	/**
	 * Listener to to the diagram kind
	 *
	 * @since 3.1
	 */
	protected Adapter diagramKindAdapter;

	/**
	 * Constructor.
	 *
	 * @param servicesRegistry
	 * @param diagram
	 * @throws ServiceException
	 */
	public UmlGmfDiagramEditor(ServicesRegistry servicesRegistry, Diagram diagram) throws ServiceException {
		super(true);
		this.diagram = diagram;
		this.servicesRegistry = servicesRegistry;
		// Install synchronizer
		partNameSynchronizer = new PartNameSynchronizer(diagram);

		// Need to manage the part label synchronizer for the table labels
		LabelInternationalizationUtils.managePartLabelSynchronizer(diagram, this);

		// Register this part to the ISaveAndDirtyService.
		// This will allows to be notified of saveAs events, and the isDirty
		// flag will be taken into
		// account.
		ISaveAndDirtyService saveAndDirtyService = servicesRegistry.getService(ISaveAndDirtyService.class);
		saveAndDirtyService.registerIsaveablePart(this);
		viewerInitialized = false;

		// add a listener to the diagram kind id
		diagramKindAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getNewValue() instanceof PapyrusDiagramStyle) {
					((PapyrusDiagramStyle) msg.getNewValue()).eAdapters().add(diagramKindAdapter);
				} else if (msg.getOldValue() instanceof PapyrusDiagramStyle) {
					((PapyrusDiagramStyle) msg.getOldValue()).eAdapters().remove(diagramKindAdapter);
				}
				if (StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE__DIAGRAM_KIND_ID.equals(msg.getFeature()) ||
						msg.getNewValue() instanceof PapyrusDiagramStyle ||
						msg.getOldValue() instanceof PapyrusDiagramStyle) {
					// reload the editor's two viewers
					if (getEditDomain().getPaletteViewer() != null) {
						getEditDomain().setPaletteRoot(createPaletteRoot(null));
					}
					if (getGraphicalViewer() != null) {
						getGraphicalViewer().setContents(diagram);
					}

				}
			}
		};

		diagram.eAdapters().add(diagramKindAdapter);
		PapyrusDiagramStyle style = DiagramUtils.getPapyrusDiagramStyle(diagram);
		if (style != null) {
			style.eAdapters().add(diagramKindAdapter);
		}
	}

	/**
	 * Override to initialize viewer, if it gets in focus
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.SynchronizableGmfDiagramEditor#setFocus()
	 */
	@Override
	public void setFocus() {
		if (!viewerInitialized) {
			BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

				@Override
				public void run() {
					doInitializeGraphicalViewer();
					viewerInitialized = true;
				}
			});
		}
		super.setFocus();
	}

	/**
	 * Initialize the graphical viewer (calls superclass)
	 */
	protected void doInitializeGraphicalViewer() {
		super.initializeGraphicalViewer();
	}

	@Override
	protected void initializeGraphicalViewer() {
		// do nothing to enable a lazy initialization.
		// initialization is done, if setFocus is called.
	}

	/**
	 * Avoid NPE, if viewer has not been initialized yet (method is called on dispose)
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#persistViewerSettings()
	 */
	@Override
	public void persistViewerSettings() {
		if (getDiagramEditPart() != null) {
			super.persistViewerSettings();
		}
	}

	/**
	 * Dispose services used in this part.
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor#dispose()
	 *
	 */
	@Override
	public void dispose() {
		// remove the listener to the diagram kind id
		if (diagramKindAdapter != null) {
			PapyrusDiagramStyle style = DiagramUtils.getPapyrusDiagramStyle(getDiagram());
			if (style != null) {
				style.eAdapters().remove(diagramKindAdapter);
			}
			getDiagram().eAdapters().remove(diagramKindAdapter);
			diagramKindAdapter = null;
		}

		this.setUndoContext(new IUndoContext() {

			@Override
			public String getLabel() {
				return "Disposed undo context";
			}

			@Override
			public boolean matches(IUndoContext context) {
				return false;
			}

		}); // Avoid disposing the shared UndoContext when this nestedEditor is dispose
		// Super.dispose() will try to dispose the IUndoContext

		super.dispose();

		ISaveAndDirtyService saveAndDirtyService;
		try {
			saveAndDirtyService = servicesRegistry.getService(ISaveAndDirtyService.class);
			saveAndDirtyService.removeIsaveablePart(this);

		} catch (ServiceException e) {
			// the service can't be found. Maybe it is already disposed.
			// Do nothing
		}

		partNameSynchronizer = null;
		diagram = null;
		servicesRegistry = null;
	}

	@Override
	public Object getAdapter(Class type) {
		if (type == ServicesRegistry.class) {
			return servicesRegistry;
		}
		return super.getAdapter(type);
	}

	/**
	 *
	 * @return the backbone service registry. it cannot return null.
	 */
	public ServicesRegistry getServicesRegistry() {
		return servicesRegistry;
	}

	/**
	 * Set the associated Diagram.
	 */
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
		partNameSynchronizer.setDiagram(diagram);
	}

	/**
	 * Get the associated Diagram
	 */
	@Override
	public Diagram getDiagram() {
		return diagram;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#getKeyHandler()
	 *
	 * @return
	 */
	@Override
	protected KeyHandler getKeyHandler() {
		return new KeyHandler();// we remove all keybinding provided by GMF
	}

	/**
	 * A class taking in charge the synchronization of the partName and the
	 * diagram name. When diagram name change, the other is automatically
	 * updated.
	 *
	 * @author cedric dumoulin
	 *
	 */
	public class PartNameSynchronizer {

		Diagram diagram;

		/**
		 * Listener on diagram name change.
		 */
		private Adapter diagramNameListener = new Adapter() {

			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getFeatureID(Diagram.class) == NotationPackage.DIAGRAM__NAME && notification.getNotifier() == diagram) {
					setPartName(LabelInternationalization.getInstance().getDiagramLabel(diagram));
				} else if (notification.getFeature() == InternationalizationPackage.eINSTANCE.getInternationalizationEntry_Value() && notification.getNotifier() == diagram) {
					setPartName(LabelInternationalization.getInstance().getDiagramLabel(diagram));
				}

			}

			@Override
			public Notifier getTarget() {
				return null;
			}

			@Override
			public void setTarget(Notifier newTarget) {
			}

			@Override
			public boolean isAdapterForType(Object type) {
				return false;
			}

		};

		/**
		 *
		 * Constructor.
		 *
		 * @param diagram
		 */
		PartNameSynchronizer(Diagram diagram) {
			setDiagram(diagram);
		}

		/**
		 * Change the associated diagram.
		 *
		 * @param diagram
		 */
		public void setDiagram(Diagram diagram) {
			// Remove from old diagram, if any
			if (this.diagram != null) {
				diagram.eAdapters().remove(diagramNameListener);
			}

			// Set new Diagram
			this.diagram = diagram;
			// Set editor name
			setPartName(LabelInternationalization.getInstance().getDiagramLabel(diagram));
			// Listen to name change
			diagram.eAdapters().add(diagramNameListener);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		IContextService contextService = getSite().getService(IContextService.class);
		// FIXME : before Eclipse Juno, this line was not necessary
		// see bug 367816 and bug 382218
		contextService.activateContext("org.eclipse.gmf.runtime.diagram.ui.diagramContext"); //$NON-NLS-1$
		super.createPartControl(parent);

	}

	/**
	 * @see org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor#modifyPartName(java.lang.String)
	 *
	 * @param name
	 */
	@Override
	public void modifyPartName(final String name) {
		setPartName(name);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor#refreshEditorPart()
	 */
	@Override
	public void refreshEditorPart() {
		if (null == getDiagramEditPart()) {
			return;// see bug 551530
		}

		DiagramHelper.forceRefresh(getDiagramEditPart());
	}
}
