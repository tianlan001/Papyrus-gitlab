/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr -  Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.editors.SelectionEditor;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.Messages;
import org.eclipse.papyrus.uml.diagram.common.util.Util;
import org.eclipse.papyrus.uml.diagram.common.util.Visitor;
import org.eclipse.papyrus.uml.tools.commands.DestroyDependencyWithoutSupplierCommand;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.edit.providers.ImageItemProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;


/**
 * This class provides a Dialog to manage easily the provided and the required Interfaces for a {@link Port}.
 */
public class InterfaceManagerDialog extends SelectionDialog implements IPortInterfaceManagerDialog {

	/** the selected port. */
	private Port port;

	/** the list of the provided interfaces. */
	private List<Object> providedInterfaces;

	/** the list of the required interfaces. */
	private List<Object> requiredInterfaces;

	/** the {@link ReferenceSelector} for the provided interfaces. */
	private ReferenceSelector providedSelector;

	/** the {@link ReferenceSelector} for the required interfaces. */
	private ReferenceSelector requiredSelector;

	/** the Editor for the provided interfaces. */
	private SelectionEditorWrapper providedEditor;

	/** the Editor for the required interfaces. */
	private SelectionEditorWrapper requiredEditor;

	/** In this map, we link the {@link Interface} with their {@link Usage} This map is used when we want remove an Interface from a Usage. */
	private Map<Interface, List<Usage>> requiredInterfacesMappedWithUsage = new HashMap<>();

	/**
	 * In this map, we link the {@link Interface} with their {@link Realization} This map is used when we want remove an Interface from a Realization.
	 */
	private Map<Interface, List<Realization>> providedInterfacesMappedWithRealization = new HashMap<>();

	/** the label provider for selector. */
	private ILabelProvider selectorLabelProvider = new SelectorLabelProvider();

	/** the label provider for the create new element dialog. */
	private final ILabelProvider newElementLabelProvider = new CreateElementLabelProvider();

	/** the domain used for the commands. */
	private TransactionalEditingDomain domain;

	/** the model. */
	private Package model = null;

	/** this command contains the list of the command to create the new Interfaces. */
	private CompoundCommand interfaceCreationCommand = new CompoundCommand("Create Interfaces Commands"); //$NON-NLS-1$

	/** This list contains representation of the Interface before their creation. */
	private List<NewElementRepresentation> interfaceRepresentations;

	/** boolean used to know if the {@link Port} is typed with an {@link Interface}. */
	private boolean typedWithInterface;

	/** path for the warning icons. */
	private static final String ICON_WARNING = "/icons/warning.gif"; //$NON-NLS-1$

	/**
	 * This list contains the names used for the new elements.
	 * We need to remember these names are used in order don't suggest them when we create other new elements
	 */
	private List<String> forbiddenNames;


	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param port
	 *            the {@link Port} for which we want manage the Interfaces
	 */
	public InterfaceManagerDialog(Shell parentShell, Port port) {
		super(parentShell);
		this.setTitle(Messages.InterfaceManagerDialog_Title);
		this.port = port;
		Assert.isTrue(this.port.getType() instanceof Classifier);
		this.typedWithInterface = (this.port.getType() instanceof Interface);
	}

	/**
	 * Creates the dialog.
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		init();
	}



	/**
	 * This method initialize some fields of the class.
	 */
	protected void init() {

		this.forbiddenNames = new ArrayList<>();
		this.interfaceRepresentations = new ArrayList<>();
		EList<Namespace> namespaces = port.allNamespaces();
		Element el = namespaces.get(namespaces.size() - 1);
		if (el instanceof Package) {
			model = (Package) el;
		} else {
			model = el.getNearestPackage();
		}

		// fill the hashmap to link Interfaces with Usage and InterfaceRealization
		buildProvidedInterfaces();
		buildRequiredInterfaces();

		// we build the list of the provided interfaces
		this.providedInterfaces = new ArrayList<>();
		for (Interface current : this.port.getProvideds()) {
			this.providedInterfaces.add(current);
		}
		// when the port is typed by an Interface, we don't propose this interface in the possible Provided Interfaces list.
		if (typedWithInterface) {
			this.providedInterfaces.remove(this.port.getType());
		}

		// we build the list of the required interfaces
		this.requiredInterfaces = new ArrayList<>();
		for (Interface current : this.port.getRequireds()) {
			this.requiredInterfaces.add(current);
		}

		// initialize the transactional editing domain
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = activePage.getActiveEditor();
		if (editor instanceof IMultiDiagramEditor) {
			try {
				this.domain = ServiceUtils.getInstance().getTransactionalEditingDomain(((IMultiDiagramEditor) editor).getServicesRegistry());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		// selectors creation
		createSelectors();
		// editors creation
		createEditors();

	}

	/**
	 * This method fills the map {@link #requiredInterfacesMappedWithUsage}.
	 */
	protected void buildRequiredInterfaces() {
		List<Usage> allUsage = ElementUtil.getInstancesFilteredByType(this.model, Usage.class, null);
		List<Classifier> classifiers = new ArrayList<>();
		classifiers.add((Classifier) this.port.getType());

		classifiers.addAll(Util.getAllSuperClasses(null, (Classifier) this.port.getType()));

		for (Object current : allUsage) {
			Usage usage = (Usage) current;
			for (Classifier classifier : classifiers) {// we test if the current Usage is linked with the type of the Port
				if (usage.getClients().contains(classifier)) {// this Usage concerns the current port
					for (EObject target : usage.getSuppliers()) {
						if (this.port.getRequireds().contains(target)) {
							List<Usage> usages = requiredInterfacesMappedWithUsage.get(target);
							if (usages != null) {
								usages.add(usage);
							} else {
								usages = new ArrayList<>();
								usages.add(usage);
								this.requiredInterfacesMappedWithUsage.put((Interface) target, usages);
							}
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * This method fills the map {@link #providedInterfacesMappedWithRealization}.
	 */
	protected void buildProvidedInterfaces() {
		List<Realization> allRealization = ElementUtil.getInstancesFilteredByType(this.model, Realization.class, null);
		List<Classifier> classifiers = new ArrayList<>();
		classifiers.add((Classifier) this.port.getType());

		classifiers.addAll(Util.getAllSuperClasses(null, (Classifier) this.port.getType()));

		for (Object current : allRealization) {
			Realization realization = (Realization) current;
			for (Classifier classifier : classifiers) {// we test if the current Realization is linked with the type of the Port
				if (realization.getClients().contains(classifier)) {// this Realization concerns the current port
					for (EObject target : realization.getSuppliers()) {
						if (this.port.getProvideds().contains(target)) {
							List<Realization> realizations = providedInterfacesMappedWithRealization.get(target);
							if (realizations != null) {
								realizations.add(realization);
							} else {
								realizations = new ArrayList<>();
								realizations.add(realization);
								this.providedInterfacesMappedWithRealization.put((Interface) target, realizations);
							}
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * This method creates the selectors {@link #providedSelector} and {@link #requiredSelector}.
	 */
	protected void createSelectors() {
		// the provided selector
		this.providedSelector = new ReferenceSelector(true);
		this.providedSelector.setLabelProvider(selectorLabelProvider);
		this.providedSelector.setContentProvider(new AbstractStaticContentProvider() {

			/**
			 *
			 * @see org.eclipse.papyrus.editors.providers.IStaticContentProvider#getElements()
			 *
			 * @return
			 */
			@Override
			public Object[] getElements() {
				List<Object> tmp = new ArrayList<>();
				tmp.addAll(getAllAvailableInterfaces(model));
				if (typedWithInterface) {
					tmp.remove(port.getType());
				}
				// we add the new interfaces which are not yet created
				tmp.addAll(interfaceRepresentations);
				return tmp.toArray();
			}
		});
		// the required selector
		this.requiredSelector = new ReferenceSelector(true);
		this.requiredSelector.setLabelProvider(selectorLabelProvider);
		this.requiredSelector.setContentProvider(new AbstractStaticContentProvider() {

			/**
			 *
			 * @see org.eclipse.papyrus.editors.providers.IStaticContentProvider#getElements()
			 *
			 * @return
			 */
			@Override
			public Object[] getElements() {
				List<Object> tmp = new ArrayList<>();
				tmp.addAll(getAllAvailableInterfaces(model));

				// we add the new interfaces which are not yet created
				tmp.addAll(interfaceRepresentations);
				return tmp.toArray();
			}
		});
	}

	/**
	 * This method create the 2 Editors {@link #providedEditor} and {@link #requiredEditor} and provides the listener for the button of these editors.
	 */
	protected void createEditors() {
		Composite parent = (Composite) getDialogArea();
		CreateButtonListener listener = new CreateButtonListener();
		// we set a specific message if the port is typed with an Interface
		if (typedWithInterface) {
			Composite messageSection = new Composite(parent, SWT.BORDER);
			CLabel label = new CLabel(messageSection, SWT.NONE);

			label.setText(Messages.InterfaceManagerDialog_ThePortIsTypedWithAnInterfaceWarningMessage);
			Image errorImage = Activator.getPluginIconImage(Activator.ID, ICON_WARNING);
			label.setImage(errorImage);
			GridLayout layout = new GridLayout(1, true);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			messageSection.setLayout(layout);
			messageSection.setLayoutData(data);
			messageSection.setEnabled(false);
		}
		this.providedEditor = createEditor(parent, providedSelector, Messages.InterfaceManagerDialog_SelectedTheProvidedInterfaces, false, listener, this.providedInterfaces);
		this.requiredEditor = createEditor(parent, requiredSelector, Messages.InterfaceManagerDialog_SelectTheRequiredInterfaces, true, listener, this.requiredInterfaces);

		this.providedEditor.create();
		this.requiredEditor.create();

		GridLayout layout = (GridLayout) parent.getLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.providedEditor.setLayoutData(data);
		this.requiredEditor.setLayoutData(data);
		getShell().setSize(950, 700);
		getShell().layout();
	}

	/**
	 * Creates a {@link SelectionEditor} and returns it.
	 *
	 * @param parent
	 *            the composite parent
	 * @param selector
	 *            the selector used by the created editor
	 * @param message
	 *            the message to display in the created editor
	 * @param withCreateButton
	 *            indicates if the editor provides an additional button for a specific action
	 * @param listener
	 *            the listener for the button
	 * @param initialSelection
	 *            the initial selection for this editor
	 * @return the created {@link SelectionEditor}
	 */
	protected SelectionEditorWrapper createEditor(Composite parent, IElementSelector selector, String message, boolean withCreateButton, SelectionListener listener, List<Object> initialSelection) {
		SelectionEditor editor = new SelectionEditor(parent, selector, selectorLabelProvider, initialSelection);
		editor.setMessage(message);
		editor.setAdditionalButton(withCreateButton, Messages.InterfaceManagerDialog_CreateNewInterface, listener);
		return new SelectionEditorWrapper(editor, editor.getClass());
	}

	/**
	 * Returns all the available Interfaces.
	 *
	 * @param pack
	 *            the pack
	 * @return all the available Interfaces
	 *         //TODO add the interfaces of the package import!
	 */
	protected List<Interface> getAllAvailableInterfaces(Package pack) {

		// Set ensure there is not duplicated interface
		Set<Interface> otherInterfaces = new HashSet<>();

		List<Element> interfaces = Visitor.getOwnedAndImportedElement(pack, Interface.class);
		for (Namespace namespace : Visitor.getOwnedAndImportedNamespaces(pack)) {
			interfaces.addAll(Visitor.getOwnedAndImportedElement(namespace, Interface.class));

		}

		for (Element element : interfaces) {
			otherInterfaces.add((Interface) element);
		}

		return Arrays.asList(otherInterfaces.toArray(new Interface[otherInterfaces.size()]));
	}


	/**
	 * Gets the command.
	 *
	 * @return the command
	 * @see org.eclipse.papyrus.uml.diagram.common.dialogs.IPortInterfaceManagerDialog#getCommand()
	 */
	@Override
	public Command getCommand() {
		CompoundCommand command = new CompoundCommand("Command to manage interfaces on Port"); //$NON-NLS-1$
		if (!interfaceCreationCommand.isEmpty()) {
			command.add(interfaceCreationCommand);
		}
		Command cmd = getCommandsToCreateElements();
		if (cmd.canExecute()) {
			command.add(cmd);
		}
		cmd = getCommandToRemoveReferencedInterfaces();
		if (cmd.canExecute()) {
			command.add(cmd);
		}
		return command;
	}

	/**
	 * Returns the command to remove referenced Interfaces in Usage and InterfaceRealization.
	 *
	 * @return The command to remove referenced Interfaces the Usage and InterfaceRealization.
	 *         Moreover, we add a command to destroy Usage and InterfaceRealization when they have any Suppliers after this action
	 */
	protected Command getCommandToRemoveReferencedInterfaces() {
		CompoundCommand command = new CompoundCommand("Command to destroy Usage and InterfaceRealization"); //$NON-NLS-1$

		Set<Dependency> relationshipToDestroy = new HashSet<>();
		// commands to destroy Usage
		for (Object current : this.requiredEditor.getElementToRemove()) {
			// we get all usage associated with this interface
			List<Usage> usages = requiredInterfacesMappedWithUsage.get(current);
			for (Usage currentUsage : usages) {
				// we remove the reference to the Interface in the Usage
				DestroyReferenceRequest request = new DestroyReferenceRequest(domain, currentUsage, UMLPackage.eINSTANCE.getDependency_Supplier(), (EObject) current, false);
				Command cmd = getCommand(request);
				if (cmd.canExecute()) {
					command.add(cmd);
					relationshipToDestroy.add(currentUsage);
				}
			}
		}

		// commands to destroy InterfaceRealization
		for (Object current : this.providedEditor.getElementToRemove()) {
			// we get all usage associated with this interface
			List<Realization> realizations = providedInterfacesMappedWithRealization.get(current);
			for (Realization currentRealization : realizations) {
				// we remove the reference to the Interface in the Usage
				DestroyReferenceRequest request = new DestroyReferenceRequest(domain, currentRealization, UMLPackage.eINSTANCE.getDependency_Supplier(), (EObject) current, false);
				Command cmd = getCommand(request);
				if (cmd.canExecute()) {
					command.add(cmd);
					relationshipToDestroy.add(currentRealization);
				}
			}
		}
		if (!relationshipToDestroy.isEmpty()) {
			command.add(new ICommandProxy(new DestroyDependencyWithoutSupplierCommand(this.domain, relationshipToDestroy, getCommandProvider())));
			return command;
		}

		// nothing to do
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the command to create new Usage and new InterfaceRealization.
	 *
	 * @return the command to create new Usage and new InterfaceRealization
	 */
	protected Command getCommandsToCreateElements() {
		CompoundCommand command = new CompoundCommand("Command to create Usage and InterfaceRealization"); //$NON-NLS-1$
		// commands for Usage Creation
		for (Object current : this.requiredEditor.getElementToAdd()) {
			if (current instanceof EObject) {
				CreateRelationshipRequest request = new CreateRelationshipRequest(this.port.getType().eContainer(), this.port.getType(), (EObject) current, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Usage"));
				Command cmd = getCommand(request);
				if (cmd.canExecute()) {
					command.add(cmd);
				}
			} else {// its an NewElementRepresentation

				CreateUsageCommand cmd = new CreateUsageCommand(this.domain, this.port.getType().eContainer(), this.port.getType(), (NewElementRepresentation) current, null);
				if (cmd.canExecute()) {
					command.add(new ICommandProxy(cmd));
				}
			}
		}

		// commands for InterfaceRealization Creation
		for (Object current : this.providedEditor.getElementToAdd()) {
			if (current instanceof EObject) {
				CreateRelationshipRequest request = new CreateRelationshipRequest(this.port.getType(), this.port.getType(), (EObject) current, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.InterfaceRealization"));
				Command cmd = getCommand(request);
				if (cmd.canExecute()) {
					command.add(cmd);
				}
			} else {// its an NewElementRepresentation
				CreateInterfaceRealizationCommand cmd = new CreateInterfaceRealizationCommand(this.domain, this.port.getType(), this.port.getType(), (NewElementRepresentation) current, null);
				if (cmd.canExecute()) {
					command.add(new ICommandProxy(cmd));
				}
			}
		}
		if (!command.isEmpty()) {
			return command;
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Return the command corresponding to the request.
	 *
	 * @param req
	 *            a request
	 * @return the command corresponding to the request
	 */
	protected Command getCommand(AbstractEditCommandRequest req) {
		IElementEditService provider = getCommandProvider();
		if (provider != null) {
			ICommand setCommand = provider.getEditCommand(req);
			if (setCommand != null) {
				return new ICommandProxy(setCommand.reduce());
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Gets the command provider.
	 *
	 * @return the command provider
	 */
	private IElementEditService getCommandProvider() {
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(model);
		return provider;
	}



	/**
	 * Gets the selector label provider.
	 *
	 * @return the selector label provider
	 */
	public ILabelProvider getSelectorLabelProvider() {
		return selectorLabelProvider;
	}


	/**
	 * Sets the selector label provider.
	 *
	 * @param selectorLabelProvider
	 *            the new selector label provider
	 */
	public void setSelectorLabelProvider(ILabelProvider selectorLabelProvider) {
		this.selectorLabelProvider = selectorLabelProvider;
	}



	/**
	 * Gets the provided editor.
	 *
	 * @return the provided editor
	 */
	public SelectionEditorWrapper getProvidedEditor() {
		return providedEditor;
	}


	/**
	 * Gets the required editor.
	 *
	 * @return the required editor
	 */
	public SelectionEditorWrapper getRequiredEditor() {
		return requiredEditor;
	}


	/**
	 * This listener is used to know when the user clicks on the Button to create a new {@link Interface}.
	 *
	 * @see CreateButtonEvent
	 */
	public class CreateButtonListener implements SelectionListener {

		/**
		 * Widget selected.
		 *
		 * @param e
		 *            the e
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {

			CreateUmlElementDialog dialog = new CreateUmlElementDialog(getShell(), domain, newElementLabelProvider, new CustomContentProvider(), model, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Interface"));
			dialog.setTitle(Messages.InterfaceManagerDialog_CreateANewInterface);
			dialog.setMessage(Messages.InterfaceManagerDialog_SelectTheParentOfThisNewInterface);
			dialog.setHelpAvailable(false);
			dialog.setForbiddenNames(forbiddenNames);
			List<Object> input = new ArrayList<>();
			input.add(model);
			dialog.setInput(input);
			dialog.setValidator(new ParentInterfaceValidator());
			if (dialog.open() == Window.OK) {
				interfaceCreationCommand.add(dialog.getCommand());
				Object[] res = dialog.getResult();
				for (Object current : res) {
					if (current instanceof NewElementRepresentation) {
						interfaceRepresentations.add((NewElementRepresentation) current);
						forbiddenNames.add(((NewElementRepresentation) current).getName());
					}
				}
				providedSelector.refresh();
				requiredSelector.refresh();
			}
		}

		/**
		 * Widget default selected.
		 *
		 * @param e
		 *            the e
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	/**
	 * This class provides a validator for the selection of the parent of the new Interface.
	 */
	public class ParentInterfaceValidator implements ISelectionStatusValidator {

		/**
		 * Selection has to be a Classifier or an Interface to allow creation.
		 *
		 * @param selection
		 *            the selection
		 * @return the i status
		 * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
		 */
		@Override
		public IStatus validate(Object[] selection) {

			IStatus status = new Status(IStatus.ERROR, Activator.ID, Messages.NoSelectionFound);
			if (selection.length >= 1) {
				for (int i = 0; i < selection.length; i++) {
					if (selection[i] instanceof Package || selection[i] instanceof Classifier || selection[i] instanceof NewElementRepresentation) {
						status = new Status(IStatus.OK, Activator.ID, Messages.SelectionValidated);
					} else {
						status = new Status(IStatus.ERROR, Activator.ID, Messages.InterfaceManagerDialog_SelectionHasToBeAPackageOrAClassifier);
						break;
					}
				}
			}
			return status;
		}
	}

	/**
	 *
	 * This class provides a CustomLabelProvider for this dialog.
	 * This class manages the {@link NewElementRepresentation} and consider them like Interfaces
	 *
	 *
	 */
	public class SelectorLabelProvider extends CreateElementLabelProvider {

		/**
		 * Gets the text.
		 *
		 * @param element
		 *            the element
		 * @return the text
		 * @see org.eclipse.papyrus.uml.diagram.common.providers.EditorLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof NewElementRepresentation) {
				return ((NewElementRepresentation) element).getQualifiedName();
			} else if (element instanceof NamedElement) {
				return ((NamedElement) element).getQualifiedName();
			}
			return super.getText(element);
		}

	}

	/**
	 * The Class CreateElementLabelProvider.
	 */
	public class CreateElementLabelProvider extends UMLLabelProvider {

		/**
		 * Gets the image.
		 *
		 * @param element
		 *            the element
		 * @return the image
		 * @see org.eclipse.papyrus.uml.diagram.common.providers.EditorLabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			if (element instanceof NewElementRepresentation) {

				ImageItemProvider imageProvider = new ImageItemProvider(new UMLItemProviderAdapterFactory());
				Object obj = imageProvider.getImage(((NewElementRepresentation) element).getEObject());
				return org.eclipse.papyrus.uml.tools.Activator.getDefault().getImageForUMLMetaclass(UMLPackage.eINSTANCE.getInterface());
			}
			return super.getImage(element);
		}

		/**
		 * Gets the text.
		 *
		 * @param element
		 *            the element
		 * @return the text
		 * @see org.eclipse.papyrus.uml.diagram.common.providers.EditorLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof NewElementRepresentation) {
				return ((NewElementRepresentation) element).getName();
			}
			return super.getText(element);
		}

	}

	/**
	 *
	 * This content provider is used in this dialog. It returns the available element in the model + the future element which are {@link NewElementRepresentation}
	 *
	 *
	 */
	public class CustomContentProvider implements ITreeContentProvider {

		/**
		 * Dispose.
		 *
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
		}

		/**
		 * Input changed.
		 *
		 * @param viewer
		 *            the viewer
		 * @param oldInput
		 *            the old input
		 * @param newInput
		 *            the new input
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		/**
		 * Gets the elements.
		 *
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List<?>) {
				return ((List<?>) inputElement).toArray();
			}
			return new Object[0];
		}

		/**
		 * Gets the children.
		 *
		 * @param parentElement
		 *            the parent element
		 * @return the children
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object parentElement) {
			List<Object> children = new ArrayList<>();
			List<?> tmp = null;
			if (parentElement instanceof Package) {
				tmp = ((Package) parentElement).getOwnedMembers();
			} else if (parentElement instanceof Classifier) {
				tmp = ((Classifier) parentElement).getOwnedMembers();
			}
			if (tmp != null) {
				for (Object current : tmp) {
					if (current instanceof Classifier || current instanceof Package) {
						children.add(current);
					}
				}
			}
			// we add the future Interfaces to this selection
			for (NewElementRepresentation current : interfaceRepresentations) {
				if (current.getParent() == parentElement) {
					children.add(current);
				}
			}
			return children.toArray();
		}

		/**
		 * Gets the parent.
		 *
		 * @param element
		 *            the element
		 * @return the parent
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		@Override
		public Object getParent(Object element) {
			if (element == model) {
				return model;
			} else if (element instanceof NamedElement) {
				return ((NamedElement) element).eContainer();
			}
			return null;
		}

		/**
		 * Checks for children.
		 *
		 * @param element
		 *            the element
		 * @return true, if successful
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length != 0;
		}

	}

	/**
	 * This command is used to create a New Usage, when its target (an Interface) doesn't exist when the command is created
	 * (but this Interface will exist when the command will be executed).
	 */
	public class CreateUsageCommand extends AbstractTransactionalCommand {

		/** The container for the new Usage. */
		private EObject container;

		/** the source of this Usage. */
		private EObject source;

		/** the target : the representation of the new interface. */
		private NewElementRepresentation target;

		/**
		 *
		 * Constructor.
		 *
		 * @param domain
		 *            the domain
		 * @param container
		 *            the container for the Usage
		 * @param source
		 *            the source for the Usage
		 * @param target
		 *            the target for the Usage
		 * @param affectedFiles
		 *            the affected files
		 */
		public CreateUsageCommand(TransactionalEditingDomain domain, EObject container, EObject source, NewElementRepresentation target, List<?> affectedFiles) {
			super(domain, "Create Usage Command", affectedFiles); //$NON-NLS-1$
			this.container = container;
			this.source = source;
			this.target = target;
		}

		/**
		 * Do execute with result.
		 *
		 * @param monitor
		 *            the monitor
		 * @param info
		 *            the info
		 * @return the command result
		 * @throws ExecutionException
		 *             the execution exception
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			CreateRelationshipRequest request = new CreateRelationshipRequest(domain, container, source, target.getEObject(), ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Usage"));
			Command command = getCommand(request);
			if (command.canExecute()) {
				command.execute();
			}
			return CommandResult.newOKCommandResult(request.getNewElement());
		}

	}

	/**
	 * This command is used to create a New InterfaceRealition, when its target (an Interface) doesn't exist when the command is created
	 * (but this Interface will exist when the command will be executed).
	 */
	public class CreateInterfaceRealizationCommand extends AbstractTransactionalCommand {

		/** The container for the new InterfaceRealization. */
		private EObject container;

		/** The source for the new InterfaceRealization. */
		private EObject source;

		/** the target : the representation of the new interface. */
		private NewElementRepresentation target;

		/**
		 *
		 * Constructor.
		 *
		 * @param domain
		 *            the domain
		 * @param container
		 *            the container for the InterfaceRealization
		 * @param source
		 *            the source for the InterfaceRealization
		 * @param target
		 *            the target for the InterfaceRealization
		 * @param affectedFiles
		 *            the affected files
		 */
		public CreateInterfaceRealizationCommand(TransactionalEditingDomain domain, EObject container, EObject source, NewElementRepresentation target, List<?> affectedFiles) {
			super(domain, "Create InterfaceRealization Command", affectedFiles); //$NON-NLS-1$
			this.container = container;
			this.source = source;
			this.target = target;
		}

		/**
		 * Do execute with result.
		 *
		 * @param monitor
		 *            the monitor
		 * @param info
		 *            the info
		 * @return the command result
		 * @throws ExecutionException
		 *             the execution exception
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

			CreateRelationshipRequest request = new CreateRelationshipRequest(domain, container, source, target.getEObject(), ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.InterfaceRealization"));
			Command command = getCommand(request);
			if (command.canExecute()) {
				command.execute();
			}
			return CommandResult.newOKCommandResult(request.getNewElement());
		}

	}
}
