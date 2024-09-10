/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net -  Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 532838
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.documentation.views;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.properties.ui.widgets.TabbedPropertyTitle;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.ui.preferences.RichtextPreferencePage;
import org.eclipse.papyrus.infra.widgets.util.PapyrusSelectionService;
import org.eclipse.papyrus.uml.tools.commands.ApplyStereotypeCommand;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.commands.SetStereotypeValueCommand;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.papyrus.uml.ui.editors.UMLRichtextEditorWithReferences;
import org.eclipse.papyrus.uml.ui.editors.UMLToolbarConfiguration;
import org.eclipse.papyrus.views.documentation.Activator;
import org.eclipse.papyrus.views.documentation.constants.DocumentationViewConstants;
import org.eclipse.papyrus.views.documentation.messages.Messages;
import org.eclipse.papyrus.views.documentation.widgets.DocumentationResourceEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * View to display documentation of {@link Element}. The first owned comment is associate to this documentation.
 */
public class DocumentationView extends ViewPart {

	/** Property label for the associated feature of documentation profile. */
	private static final String ASSOCIATED_RESOURCE_PROPERTY_LABEL = "associatedResource"; //$NON-NLS-1$

	/** The Stereotype Documentation if used. */
	private static final String DOCUMENT_PROFILE_QUALIFY_NAME = "Documentation::Documentation";//$NON-NLS-1$

	/** The documentation view Id. */
	public static final String ID = "org.eclipse.papyrus.views.documentation.DocumentationView";//$NON-NLS-1$

	/** True if the linking with the editor is active. */
	private boolean linkingEnabled;

	/** The rich text editor. */
	private UMLRichtextEditorWithReferences richtextEditor;

	/** The current comment representing the documentation. */
	private Comment comment;

	/** The owner of the current comment. */
	private Element commentOwner;

	/** The Widget Factory. */
	private TabbedPropertySheetWidgetFactory widgetFactory;

	/** The composite for the rich text editor. */
	private Composite topComposite;

	/** The name title bar. */
	private TabbedPropertyTitle nameTitle;

	/** The label Provider. */
	private ILabelProvider titleLabelProvider = new LabelProviderServiceImpl().getLabelProvider();

	/** THe toolbar configuration. */
	private UMLToolbarConfiguration toolbarConfig;

	/** The last selection. */
	private ISelection lastSelection;

	/** the stack layout. */
	private StackLayout parentStackLayout;

	/** The main composite. */
	private Composite mainComposite;

	/** The composite where is no element available. */
	private Composite noEditorComposite;

	/** The parent Composite. */
	private Composite parent;

	/** The selection Listener. */
	private ISelectionListener selectionListener;

	/** The selection change listener for viwer from property view. */
	private ISelectionChangedListener selectionChangeListener;

	/** The text of the documentation. */
	private String text;

	/** True if the richtext editor is used. */
	protected boolean useRichText;

	/** The stack layout of the editor composite. */
	private StackLayout editorStackLayout;

	/** The editor composite. */
	private Composite editorsComposite;

	/** The string editor for not richtext documentation. */
	private Text stringEditor;

	/** True if the owner is readOnly: no documentation modification. */
	private boolean readOnly;

	/** True if we use the stereotype Documentation::Documentation to comment. */
	private boolean useDocumentationProfile;

	/** The composite for resources view. */
	private Composite resourcesComposite;

	/** The tabFolder composite for stereotyped comment. */
	private CTabFolder tabFolderComposite;

	/** The documentation resources editor */
	private DocumentationResourceEditor resourceTableComposite;

	/** the listener for change on comment and owner. */
	private Adapter listener = new AdapterImpl() {
		/**
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(final Notification msg) {
			// Refresh name title if name changed
			if (msg.getNotifier().equals(commentOwner) && null != msg.getFeature() && msg.getFeature().equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
				refreshNameTitle();
			} else if (msg.getNotifier().equals(comment) && null != msg.getFeature() && msg.getFeature().equals(UMLPackage.eINSTANCE.getComment_Body()) && !richtextEditor.getText().equals(comment.getBody())) {
				if (null != richtextEditor && !richtextEditor.isDisposed()) {
					refreshEditors();
				}
			}
		}
	};

	/**
	 * Switch between single editor or RichText editor with preferences.
	 */
	IPropertyChangeListener richTextPreferenceListener = event -> {
		if (RichtextPreferencePage.USE_CK_EDITOR == event.getProperty()) {
			useRichText = org.eclipse.papyrus.infra.ui.Activator.getDefault().getPreferenceStore().getBoolean(RichtextPreferencePage.USE_CK_EDITOR);
			if (useRichText) {
				if (null != richtextEditor && !richtextEditor.isDisposed()) {
					richtextEditor.setText(text);
				}
				editorStackLayout.topControl = richtextEditor;
			} else {
				if (null != stringEditor && !stringEditor.isDisposed()) {
					stringEditor.setText(text);
				}
				editorStackLayout.topControl = stringEditor;
			}
			if (null != editorsComposite && !editorsComposite.isDisposed()) {
				editorsComposite.layout(true);
			}
		}
	};

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		this.parent = parent;
		// Create the parent stack layout
		parentStackLayout = new StackLayout();
		parent.setLayout(parentStackLayout);

		CTabItem createTabItemComment = null;
		CTabItem createTabItemRessources = null;
		if (useDocumentationProfile) {
			tabFolderComposite = widgetFactory.createTabFolder(parent, SWT.NONE);

			createTabItemComment = widgetFactory.createTabItem(tabFolderComposite, SWT.NONE);
			createTabItemComment.setText(Messages.DocumentationView_CommentTabLabel);
			createTabItemRessources = widgetFactory.createTabItem(tabFolderComposite, SWT.NONE);
			createTabItemRessources.setText(Messages.DocumentationView_ResourcesTabLabel);
			tabFolderComposite.setSelection(createTabItemComment);
		}

		noEditorComposite = widgetFactory.createComposite(parent, SWT.NONE);
		parentStackLayout.topControl = noEditorComposite;

		// Create the main composite
		createMainComposite(useDocumentationProfile ? tabFolderComposite : parent);

		if (useDocumentationProfile) {
			resourcesComposite = widgetFactory.createComposite(tabFolderComposite);
			GridLayoutFactory.fillDefaults().equalWidth(false).spacing(0, 0).applyTo(resourcesComposite);
			resourceTableComposite = new DocumentationResourceEditor(resourcesComposite);

			createTabItemComment.setControl(mainComposite);
			createTabItemRessources.setControl(resourcesComposite);
		}
		parent.layout(true);

		// Create Action
		createAction();
	}

	/**
	 * Creation view menu actions
	 */
	protected void createAction() {
		final IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		if (null != toolBarManager) {
			toolBarManager.add(createToggleLinkingAction());
		}
	}

	/**
	 * Create the rich text editor into the rich text composite.
	 */
	protected void createRichTextEditor(final Composite parent) {
		toolbarConfig = new UMLToolbarConfiguration();
		toolbarConfig.toolbarCollapsible = true;
		toolbarConfig.removeFormat = false;
		toolbarConfig.toolbarInitialExpanded = Activator.getToolbarInitialExpendedPreference();

		richtextEditor = new UMLRichtextEditorWithReferences(parent, toolbarConfig);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(nameTitle, 0);
		formData.bottom = new FormAttachment(100, 0);
		richtextEditor.setLayoutData(formData);
		richtextEditor.addModifyListener(event -> {
			if (!readOnly && useRichText) {
				text = richtextEditor.getText();
			}
		});

	}

	/**
	 * Save the text in the comment, if no comment exist it will be created.
	 */
	protected void save() {
		if (null != commentOwner) {
			TransactionalEditingDomain editingDomain = getDomain();
			CompoundCommand cc = new CompoundCommand("Edit Documentation");//$NON-NLS-1$

			// get save text command
			if (null != text) {
				// if comment exist
				if (null != comment) {
					if (!text.equals(comment.getBody())) {// And there is modification
						// Set the new value of the comment
						cc.append(new SetCommand(editingDomain, comment, UMLPackage.eINSTANCE.getComment_Body(), text));
					}

				} else if (!text.isEmpty() || (null != resourceTableComposite && !resourceTableComposite.getResources().isEmpty())) {
					// if no comment, create one when we begin to type
					cc.append(getCreateNewCommentCommand());
				}
			}

			// get save resource command
			if (useDocumentationProfile && null != comment && null != resourceTableComposite) {
				SetStereotypeValueRequest requestSetResources = new SetStereotypeValueRequest(editingDomain, getDocumentationStereotype(), comment, ASSOCIATED_RESOURCE_PROPERTY_LABEL, resourceTableComposite.getResources());
				cc.append(new GMFtoEMFCommandWrapper(new SetStereotypeValueCommand(requestSetResources, editingDomain, "")));//$NON-NLS-1$
			}

			// Execute command
			if (null != cc && cc.canExecute()) {
				editingDomain.getCommandStack().execute(cc);
			}

			// Refresh rich text configuration
			if (useRichText) {
				richtextEditor.configureEdition(comment, UMLPackage.eINSTANCE.getComment_Body());
			}
		}
	}


	/**
	 * @return the editing domain of the comment owner.
	 */
	private TransactionalEditingDomain getDomain() {
		return TransactionUtil.getEditingDomain(commentOwner);
	}


	/**
	 * Create the new comment and return the command.
	 */
	protected CompoundCommand getCreateNewCommentCommand() {

		TransactionalEditingDomain editingDomain = getDomain();

		// create comment
		comment = UMLFactory.eINSTANCE.createComment();
		comment.setBody(text);

		CompoundCommand cc = new CompoundCommand("Create new Comment");//$NON-NLS-1$

		// Add it to the owner
		cc.append(new AddCommand(editingDomain, commentOwner, UMLPackage.eINSTANCE.getElement_OwnedComment(), comment));
		if (useDocumentationProfile) {

			Stereotype documentationStereotype = getDocumentationStereotype();
			if (null != documentationStereotype) {
				ApplyStereotypeCommand applyStereotypeCommand = new ApplyStereotypeCommand(comment, documentationStereotype, editingDomain);
				cc.append(applyStereotypeCommand);
			} else {
				// If null the profile is not applied: inform the user to apply it
				MessageDialog.openInformation(getSite().getShell(), Messages.DocumentationView_notProfileAppliedDialogTitle,
						Messages.DocumentationView_notProfileAppliedDialogMessage);
			}
		}
		return cc;
	}

	/**
	 * @return The stereotype Documentation::Documentation
	 */
	public Stereotype getDocumentationStereotype() {

		Stereotype docStereotype = null;
		if (null != commentOwner) {
			org.eclipse.uml2.uml.Package package_ = commentOwner.getNearestPackage();
			if (null != package_) {

				for (Iterator<ProfileApplication> iterator = package_.getAllProfileApplications().iterator(); null == docStereotype && iterator.hasNext();) {
					ProfileApplication profileApplication = iterator.next();
					Profile appliedProfile = profileApplication.getAppliedProfile();

					if (null != appliedProfile) {

						for (Iterator<Stereotype> iterator2 = appliedProfile.allApplicableStereotypes().iterator(); null == docStereotype && iterator2.hasNext();) {
							Stereotype stereotype = iterator2.next();
							ENamedElement appliedDefinition = profileApplication.getAppliedDefinition(stereotype);

							if (appliedDefinition instanceof EClass
									&& !((EClass) appliedDefinition).isAbstract()
									&& DOCUMENT_PROFILE_QUALIFY_NAME.equals(stereotype.getQualifiedName())) {

								// Stereotype found
								docStereotype = stereotype;
							}
						}
					}
				}
			}
		}
		return docStereotype;
	}


	/**
	 * Create the title composite
	 */
	protected void createEditorTitle() {
		nameTitle = new TabbedPropertyTitle(topComposite, widgetFactory);

		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		nameTitle.setLayoutData(data);
	}

	/**
	 * Create the rich Text main composite.
	 */
	protected void createTopComposite(final Composite parent) {

		topComposite = widgetFactory.createComposite(parent, SWT.NONE);
		topComposite.setLayout(new FormLayout());
		{
			FormData formData = new FormData();
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.top = new FormAttachment(0, 0);
			formData.bottom = new FormAttachment(100, 0);
			topComposite.setLayoutData(formData);
		}

		GridDataFactory.fillDefaults().grab(true, true).applyTo(topComposite);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);

		linkingEnabled = Activator.getDefault().getToogleLinkingSetting();
		useRichText = Activator.getDefault().getUseRichTextSetting();
		useDocumentationProfile = Activator.getUseDocumentationProfilePreference();

		widgetFactory = new TabbedPropertySheetWidgetFactory();

		selectionListener = createSelectionListener();
		site.getPage().addSelectionListener(selectionListener);

		// add selection change listener in papyrus selection service
		selectionChangeListener = createSelectionChangedListener();
		PapyrusSelectionService.getInstance().addSelectionChangedListener(selectionChangeListener);

		// Add listener on page to save at view focus lost
		getViewSite().getPage().addPartListener(new SaveAtPartFocusLostListener());

		// Add listener on richtext preference
		org.eclipse.papyrus.infra.ui.Activator.getDefault().getPreferenceStore().addPropertyChangeListener(richTextPreferenceListener);
	}

	/**
	 * To create the selection listener to check the selection changed on the
	 * editor.
	 *
	 * @return The ISelectionListener.
	 */
	private ISelectionListener createSelectionListener() {
		return (part, selection) -> {
			if (!(part instanceof DocumentationView)) {
				if (linkingEnabled && (null == lastSelection || !lastSelection.equals(selection))) {
					lastSelection = selection;
					setSelectedElement(selection);
				}
			}
		};
	}

	/**
	 * To create the selection listener to check the selection changed on the
	 * editor.
	 *
	 * @return The ISelectionListener.
	 */
	private ISelectionChangedListener createSelectionChangedListener() {
		return (event) -> {
			ISelection selection = event.getSelection();
			if (linkingEnabled && (null == lastSelection || !lastSelection.equals(selection))) {
				lastSelection = selection;
				setSelectedElement(selection);
			}
		};
	}

	/**
	 * Set the selection element from a {@link ISelection}.
	 */
	public void setSelectedElement(final ISelection selection) {
		if (selection instanceof IStructuredSelection && null != parent && !parent.isDisposed()) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (1 == structuredSelection.size()) {
				Object firstElement = structuredSelection.getFirstElement();
				if (!(firstElement instanceof String)) {// In case of selection in resources tab
					setSelectedElement(UMLUtil.resolveUMLElement(firstElement));
				}
			}
		}
	}

	/**
	 * Create the main composite which have to appear when an element is selected.
	 */
	protected void createMainComposite(final Composite parent) {
		mainComposite = widgetFactory.createPlainComposite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(mainComposite);

		// create the top composite containing the title and editors
		createTopComposite(mainComposite);

		// create title
		createEditorTitle();

		// Create the editor composite containing the richtext editor and the string editor
		createEditorsComposite();

		// create the richtext editor
		createRichTextEditor(editorsComposite);

		// create the simple string editor
		createStringEditorComposite();

		editorsComposite.layout(true);

		editorStackLayout.topControl = useRichText ? richtextEditor : stringEditor;
	}

	/**
	 * Create the string editor composite.
	 */
	protected void createStringEditorComposite() {
		stringEditor = widgetFactory.createText(editorsComposite, "", SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);//$NON-NLS-1$
		stringEditor.addModifyListener(event -> {
			if (!readOnly) {
				text = stringEditor.getText();
			}
		});
	}

	/**
	 * Create the editors composite.
	 */
	protected void createEditorsComposite() {
		editorsComposite = widgetFactory.createComposite(topComposite);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(nameTitle, 0);
		formData.bottom = new FormAttachment(100, 0);
		editorsComposite.setLayoutData(formData);
		editorStackLayout = new StackLayout();
		editorsComposite.setLayout(editorStackLayout);
	}

	/**
	 * Set the selection element from a {@link Element}.
	 */
	protected void setSelectedElement(final Element element) {
		// switch between composite
		if (null != element) {
			parentStackLayout.topControl = useDocumentationProfile ? tabFolderComposite : mainComposite;
		} else {
			parentStackLayout.topControl = noEditorComposite;
		}

		// remove listener in previous element
		removeListener();
		// set the new owner
		commentOwner = element;
		// reset comment
		comment = null;

		boolean isReadOnly = true;
		// set the comment and install eAdapter
		if (null != commentOwner) {
			commentOwner.eAdapters().add(listener);

			EList<Comment> ownedComments = commentOwner.getOwnedComments();
			if (!ownedComments.isEmpty()) {
				comment = getDocumentationComment(ownedComments);
				if (null != comment) {
					comment.eAdapters().add(listener);
				}
			}
			// Check if the owner is a read only Element
			if (null != commentOwner.eResource()) {
				TransactionalEditingDomain editingDomain = getDomain();
				if (null != editingDomain) {
					isReadOnly = editingDomain.isReadOnly(commentOwner.eResource());
				}
			}
		}

		setReadOnly(isReadOnly);

		// refresh title and rich text editor
		refreshNameTitle();
		refreshEditors();
		refreshResources();

		parent.layout(true);

	}

	/**
	 * Refresh the resources composite.
	 */
	@SuppressWarnings("unchecked")
	protected void refreshResources() {
		List<String> resources = new ArrayList<>();
		if (null != resourceTableComposite) {
			if (useDocumentationProfile && null != comment) {
				Stereotype appliedStereotype = comment.getAppliedStereotype(DOCUMENT_PROFILE_QUALIFY_NAME);
				if (null != appliedStereotype) {
					Object value = comment.getValue(appliedStereotype, ASSOCIATED_RESOURCE_PROPERTY_LABEL);
					if (value instanceof List) {
						resources.addAll((List<String>) value);
					}
				}
			}
			resourceTableComposite.setResources(resources);
			resourceTableComposite.refresh();
		}
	}

	/**
	 * Gets the comment used for the documentation;
	 *
	 * @param ownedComments
	 *            The owned comments of an {@link Element}.
	 */
	protected Comment getDocumentationComment(final EList<Comment> ownedComments) {
		Comment docComment = null;
		useDocumentationProfile = Activator.getUseDocumentationProfilePreference();
		if (useDocumentationProfile) {
			for (Iterator<Comment> iterator = ownedComments.iterator(); iterator.hasNext() && null == docComment;) {
				Comment comment = iterator.next();
				if (null != comment.getAppliedStereotype(DOCUMENT_PROFILE_QUALIFY_NAME)) {
					docComment = comment;
				}
			}
		} else {
			docComment = ownedComments.get(0);
		}
		return docComment;
	}


	/**
	 * @param readOnly:
	 *            true if the comment's owner is read only.
	 */
	protected void setReadOnly(final boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Refresh the rich text editor.
	 */
	protected void refreshEditors() {
		if (null != comment && null != comment.getBody()) {
			text = comment.getBody();
		} else {
			text = ""; //$NON-NLS-1$
		}

		// // Refresh rich text text editor
		if (useRichText && null != richtextEditor && !richtextEditor.isDisposed()) {
			richtextEditor.configureEdition(comment, null != comment ? UMLPackage.eINSTANCE.getComment_Body() : null);
			try {
				richtextEditor.setText(text);
				richtextEditor.setEditable(!readOnly);
			} catch (Exception e) {
				// do nothing : web browser exception
			}
		}

		// Refresh simple string text editor
		if (null != stringEditor && !stringEditor.isDisposed()) {
			stringEditor.setText(text);
			stringEditor.setEditable(!readOnly);
		}
	}

	/**
	 * Refresh the name title header.
	 */
	protected void refreshNameTitle() {
		// Refresh Title
		if (null != nameTitle && !nameTitle.isDisposed()) {
			nameTitle.setTitle(titleLabelProvider.getText(commentOwner), titleLabelProvider.getImage(commentOwner));
		}
	}

	/**
	 * Remove eAdapters on comment and comment owner.
	 */
	protected void removeListener() {
		if (null != commentOwner) {
			commentOwner.eAdapters().remove(listener);
		}
		if (null != comment) {
			comment.eAdapters().remove(listener);
		}
	}


	/**
	 * Creates the 'Link with Editor' action.
	 */
	protected IAction createToggleLinkingAction() {
		final IAction toggleLinkingAction = new Action(Messages.DocumentationView_SyncActionTooltip, SWT.TOGGLE) {
			@Override
			public void run() {
				linkingEnabled = isChecked();
				Activator.getDefault().setToggleEditorSetting(linkingEnabled);
				if (linkingEnabled && null != lastSelection) {
					setSelectedElement(lastSelection);
				}
			}
		};

		toggleLinkingAction.setChecked(linkingEnabled);
		toggleLinkingAction.setImageDescriptor(Activator.imageDescriptorFromPlugin(DocumentationViewConstants.PLUGIN_ORG_ECLIPSE_UI, DocumentationViewConstants.ICON_LINK_WITH_EDITOR));

		return toggleLinkingAction;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (null != richtextEditor) {
			try {
				richtextEditor.setEditable(!readOnly);
			} catch (Exception e) {
				// do nothing : web browser exception
			}
			richtextEditor.setFocus();
		} else {
			parent.setFocus();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		// Remove the modify listener
		removeListener();

		// Remove the selection listener.
		if (null != selectionListener) {
			getSite().getPage().removeSelectionListener(selectionListener);
		}
		if (null != selectionChangeListener) {
			PapyrusSelectionService.getInstance().removeSelectionChangedListener(selectionChangeListener);
		}
		if (null != richTextPreferenceListener) {
			org.eclipse.papyrus.infra.ui.Activator.getDefault().getPreferenceStore().removePropertyChangeListener(richTextPreferenceListener);
		}
		super.dispose();
	}

	/**
	 * Save the text at focus lost of the view.
	 */
	private final class SaveAtPartFocusLostListener implements IPartListener {
		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
		 */
		@Override
		public void partOpened(final IWorkbenchPart part) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}<br>
		 * Override to save the text at focus lost.
		 *
		 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
		 */
		@Override
		public void partDeactivated(final IWorkbenchPart part) {
			if (part.equals(DocumentationView.this)) {
				save();
			}
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
		 */
		@Override
		public void partClosed(final IWorkbenchPart part) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
		 */
		@Override
		public void partBroughtToTop(final IWorkbenchPart part) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
		 */
		@Override
		public void partActivated(final IWorkbenchPart part) {
			// Do nothing
		}
	}

}
