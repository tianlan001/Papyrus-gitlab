/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Patrik Nandorf (Ericsson AB) patrik.nandorf@ericsson.com - Bug 425565
 *  Christian W. Damus - bugs 485220, 571713, 571715, 572633
 *  Asma Smaoui  asma.smaoui@cea.fr - bug 528156
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreateRelationshipMenu;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Separator;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestCacheEntries;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.semantic.service.SemanticService;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.SemanticEMFContentProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.ui.providers.DelegatingPapyrusContentProvider;
import org.eclipse.papyrus.infra.ui.providers.ISemanticContentProviderFactory;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * this class contains code to construct menu from a Menu and a selected object
 *
 */
public class CreationMenuFactory {

	private TransactionalEditingDomain editingDomain;

	/** Ensure uniqueness of top-level menu names. */
	private Map<String, Menu> folderMenus = new HashMap<>();

	/**
	 *
	 * Constructor.
	 *
	 * @param editingDomain
	 */
	public CreationMenuFactory(TransactionalEditingDomain editingDomain) {
		super();
		this.editingDomain = editingDomain;
	}

	/**
	 * construct a menu from a folder, this is a recursion
	 *
	 * @param menu
	 *            the current menu
	 * @param folder
	 *            the folder
	 * @param selectedObject
	 *            the current selection
	 * @param adviceCache
	 * @return {@code true} if a new sub-menu has been added. In the case that more content is added
	 *         to an already existing menu, the result will be {@code false} because no
	 *         new sub-menu was created
	 */
	public boolean populateMenu(Menu menu, Folder folder, EObject selectedObject, int index, Map<?, ?> adviceCache) {
		if (selectedObject == null || folder == null || !folder.isVisible() || !filterMatches(folder, selectedObject)) {
			// Nothing to do, here
			return false;
		}

		// If we don't yet have a menu to fill, we will optimistically create it and try to fill it.
		// In the case that we created it and didn't fill it, we need to destroy it to clean up.
		// But if we are reusing an existing menu, then it already has content and so should not
		// be deleted even if we don't end up adding any content to it
		final boolean createdNewMenu;

		org.eclipse.swt.widgets.MenuItem topMenuItem;
		Menu topMenu = folderMenus.get(folder.getLabel());
		if (topMenu != null) {
			// We did not build a new menu
			createdNewMenu = false;

			// And reuse its parent item
			topMenuItem = topMenu.getParentItem();
		} else {
			// We created a new menu
			createdNewMenu = true;

			// create a new parent item and menu
			topMenuItem = new MenuItem(menu, SWT.CASCADE, index);
			topMenuItem.setText(folder.getLabel());

			topMenu = new Menu(topMenuItem);
			topMenuItem.setMenu(topMenu);

			// Stash it for reuse next time
			folderMenus.put(folder.getLabel(), topMenu);
		}

		if (topMenuItem.getImage() == null && folder.getIcon() != null && folder.getIcon().length() > 0) {
			// Give the cascading menu item the first available folder icon
			URI iconURI = EMFHelper.getImageURI(folder, folder.getIcon());
			URL url = null;
			try {
				url = new URL(iconURI.toString());
				ImageDescriptor imgDesc = ImageDescriptor.createFromURL(url);
				topMenuItem.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(imgDesc));
			} catch (MalformedURLException e) {
				// no exception thrown
				Activator.log.debug(String.format("Invalid folder icon URL '%s': %s", folder.getIcon(), e.getMessage()));
			}
		}

		boolean addedItems = false; // Track whether we added anything to the menu
		for (org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu currentMenu : folder.getMenu()) {
			if (currentMenu instanceof Separator) {
				constructSeparator(topMenu);
			} else if (currentMenu instanceof Folder) {
				// Maintain uniqueness of nested folders without interference from this level
				Map<String, Menu> folderMenusAtThisLevel = folderMenus;
				folderMenus = new HashMap<>();
				try {
					addedItems |= populateMenu(topMenu, (Folder) currentMenu, selectedObject, topMenu.getItemCount(), adviceCache);
				} finally {
					folderMenus = folderMenusAtThisLevel;
				}
			} else if (currentMenu instanceof CreationMenu && ((CreationMenu) currentMenu).isVisible() && filterMatches(currentMenu, selectedObject)) {
				CreationMenu currentCreationMenu = (CreationMenu) currentMenu;
				EReference reference = null;
				String role = currentCreationMenu.getRole();
				// the role is precised
				if (role != null && !role.isEmpty()) {
					EStructuralFeature feature = selectedObject.eClass().getEStructuralFeature(role);
					if (feature instanceof EReference) {
						reference = (EReference) feature;
						addedItems |= constructMenu(selectedObject, topMenu, currentCreationMenu, reference, adviceCache);
					}
				} else {// no precisison
						// test if all roles must be displayed
					if (currentCreationMenu.isDisplayAllRoles()) {
						addedItems |= constructMenu(selectedObject, topMenu, currentCreationMenu, adviceCache);
					} else {
						addedItems |= constructMenu(selectedObject, topMenu, currentCreationMenu, reference, adviceCache);
					}
				}
			}
		}

		if (createdNewMenu && !addedItems) {
			// If created a new menu and we didn't add anything to it,
			// then it is empty and is of no use, so destroy it
			folderMenus.remove(folder.getLabel());
			topMenu.dispose();
			topMenuItem.dispose();
		}

		// If we reused the menu, we didn't create a new one, so don't report
		// back to the caller that we added a new top menu
		return createdNewMenu && addedItems;
	}

	/**
	 * Constructs a separator in the {@link Menu}.
	 */
	protected void constructSeparator(Menu topMenu) {
		new MenuItem(topMenu, SWT.SEPARATOR);
	}

	/**
	 * Checks if the optional filter of the menu is matching or not the selected EObject
	 *
	 * @param menu
	 *            the menu to filter
	 * @param selectedObject
	 *            the object on which the menu is opened
	 * @return <code>true</code> if there is no filter or if the filter is matching the EObject
	 */
	protected boolean filterMatches(org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu menu, EObject selectedObject) {
		Filter filter = menu.getFilter();
		if (filter == null) {
			return true;
		}
		return filter.matches(selectedObject);
	}

	/**
	 * create menu by displaying if possible different roles
	 *
	 * @param selectedObject
	 *            the current object
	 * @param menu
	 *            the current menu in creation
	 * @param currentCreationMenu
	 * @param adviceCache
	 * @return true if sub-menu has been created
	 */
	protected boolean constructMenu(EObject selectedObject, Menu menu, CreationMenu currentCreationMenu, Map<?, ?> adviceCache) {
		IClientContext context = null;
		try {
			context = TypeContext.getContext(selectedObject);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		if (context != null) {

			// find the destination owner
			final EObject target = ElementEditServiceUtils.getTargetFromContext(editingDomain, selectedObject,
					buildRequest(null, selectedObject, currentCreationMenu, adviceCache, context));
			if (target == null) {
				return false;
			}

			// find the feature between children and owner
			ArrayList<EStructuralFeature> possibleEFeatures = getEreferences(target, currentCreationMenu, context);

			if (possibleEFeatures.size() == 1) {
				Command cmd = buildCommand(null, target, currentCreationMenu, adviceCache, context);
				if (cmd.canExecute()) {
					MenuItem item = new MenuItem(menu, SWT.NONE);
					fillIcon(currentCreationMenu, item, context);
					item.setEnabled(true);
					item.setText(currentCreationMenu.getLabel());
					item.addSelectionListener(new CreationMenuListener(cmd, editingDomain));
					return true;
				}
				return false;
			} else if (possibleEFeatures.size() > 1) {
				org.eclipse.swt.widgets.MenuItem topMenuItem = new MenuItem(menu, SWT.CASCADE);
				topMenuItem.setText(currentCreationMenu.getLabel());
				fillIcon(currentCreationMenu, topMenuItem, context);
				Menu topMenu = new Menu(menu);
				topMenuItem.setMenu(topMenu);
				for (EStructuralFeature eStructuralFeature : possibleEFeatures) {

					Command cmd = buildCommand((EReference) eStructuralFeature, target, currentCreationMenu, adviceCache, context);
					if (cmd.canExecute()) {
						MenuItem item = new MenuItem(topMenu, SWT.NONE);
						fillIcon(currentCreationMenu, item, context);
						item.setEnabled(true);
						item.setText("As " + eStructuralFeature.getName());
						item.addSelectionListener(new CreationMenuListener(cmd, editingDomain));
					}

				}
				if (topMenu.getItemCount() == 0) {
					topMenu.dispose();
					topMenuItem.dispose();
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * display an icon from a specified URL or from Element type
	 *
	 * @param currentCreationMenu
	 * @param item
	 * @since 3.0
	 */
	protected void fillIcon(CreationMenu currentCreationMenu, MenuItem item, IClientContext context) {
		if (currentCreationMenu.getIcon() != null && !currentCreationMenu.getIcon().isBlank()) {
			URI iconURI = EMFHelper.getImageURI(currentCreationMenu, currentCreationMenu.getIcon());
			URL url;
			try {
				url = new URL(iconURI.toString());
				ImageDescriptor imgDesc = ImageDescriptor.createFromURL(url);
				item.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(imgDesc));
			} catch (MalformedURLException e) {
				// no icon found
				Activator.log.debug(String.format("Invalid creation menu icon URL '%s': %s", currentCreationMenu.getIcon(), e.getMessage()));
			}
		} else {
			createIconFromElementType(currentCreationMenu, item, context);
		}
	}

	/**
	 * it is used in order calculate all roles that can play an element to another
	 *
	 * @param selectedObject
	 * @param currentCreationMenu
	 * @return return the list of Ereference that can be calculated
	 * @since 3.0
	 */
	protected ArrayList<EStructuralFeature> getEreferences(EObject selectedObject, CreationMenu currentCreationMenu, IClientContext context) {
		ArrayList<EStructuralFeature> possibleEFeatures = new ArrayList<>();
		EList<EStructuralFeature> featureList = selectedObject.eClass().getEAllStructuralFeatures();
		Iterator<EStructuralFeature> iterator = featureList.iterator();
		while (iterator.hasNext()) {
			EStructuralFeature eStructuralFeature = iterator.next();
			if (eStructuralFeature instanceof EReference) {
				EReference ref = (EReference) eStructuralFeature;
				if (ref.isContainment()) {
					IElementType menuType = getElementType(currentCreationMenu.getElementType(), context);
					if (menuType != null && isSubClass(ref.getEType(), menuType.getEClass())) {
						possibleEFeatures.add(eStructuralFeature);
					}
				}
			}
		}
		return possibleEFeatures;
	}

	/**
	 * Test if a possibleSub eclass is a sub eclass
	 *
	 * @param aclass
	 *            , cannot be null
	 * @param possibleSubClasse
	 *            , cannot be null
	 * @return true if possible eclass is a subtype of a eclass or false
	 */
	protected boolean isSubClass(EClassifier aclass, EClass possibleSubClasse) {
		if (aclass.equals(possibleSubClasse)) {
			return true;
		}
		EList<EClass> superTypeList = possibleSubClasse.getEAllSuperTypes();
		if (superTypeList.contains(aclass)) {
			return true;
		}
		return false;
	}


	/**
	 * associate the icon from the element type
	 *
	 * @param currentCreationMenu
	 * @param item
	 *            the current menu
	 * @since 3.0
	 */
	protected void createIconFromElementType(CreationMenu currentCreationMenu, MenuItem item, IClientContext context) {
		IElementType elementType = getElementType(currentCreationMenu.getElementType(), context);
		if (elementType != null) {
			URL iconURL = elementType.getIconURL();
			if (iconURL != null) {
				ImageDescriptor imgDesc = ImageDescriptor.createFromURL(iconURL);
				item.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(imgDesc));
			}
		}
	}

	/**
	 * create a submenu
	 *
	 * @param selectedObject
	 *            the selected object
	 * @param topMenu
	 *            the menu when will add menus
	 * @param currentCreationMenu
	 * @param reference
	 *            the role of the new element
	 * @param adviceCache
	 * @return true if the menu can be created
	 */
	protected boolean constructMenu(EObject selectedObject, Menu topMenu, CreationMenu currentCreationMenu, EReference reference, Map<?, ?> adviceCache) {
		IClientContext context = null;
		try {
			context = TypeContext.getContext(selectedObject);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		if (context != null) {
			boolean oneDisplayedMenu = false;
			Command cmd = buildCommand(reference, selectedObject, currentCreationMenu, adviceCache, context);
			if (cmd.canExecute()) {
				oneDisplayedMenu = true;
				MenuItem item = new MenuItem(topMenu, SWT.NONE);
				fillIcon(currentCreationMenu, item, context);
				item.setEnabled(true);
				item.setText(currentCreationMenu.getLabel());
				item.addSelectionListener(new CreationMenuListener(cmd, editingDomain));
			}
			return oneDisplayedMenu;
		}
		return false;
	}

	/**
	 * get the IelementType from a EReference with context check
	 *
	 * @param elementType
	 *            the string that represents the element type
	 * @return the element type or null
	 * @since 3.0
	 *
	 */
	protected IElementType getElementType(ElementTypeConfiguration elementTypeConfiguration, IClientContext context) {
		IElementType type = ElementTypeRegistry.getInstance().getType(elementTypeConfiguration.getIdentifier());

		return (type != null && context.includes(type)) ? type : null;

	}

	/**
	 * Construct a command of creation
	 *
	 * @param reference
	 *            the role of the element that will be created (maybe null)
	 * @param container
	 *            the container of the created elements
	 * @param adviceCache
	 *
	 * @return a command that can be executed by the domain
	 * @since 3.0
	 */
	protected Command buildCommand(EReference reference, EObject container, CreationMenu creationMenu, Map<?, ?> adviceCache, IClientContext context) {


		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(container, context);
		if (provider == null) {
			return UnexecutableCommand.INSTANCE;
		}

		IElementType elementType = getElementType(creationMenu.getElementType(), context);
		if (elementType == null) {
			return UnexecutableCommand.INSTANCE;
		}


		ICommand createGMFCommand = null;
		if (creationMenu instanceof CreateRelationshipMenu) {

			IElementEditService serviceProvider = ElementEditServiceUtils.getCommandProvider(elementType, context);
			TreeSelectorDialog dialog = getTargetTreeSelectorDialog(container, serviceProvider, editingDomain, reference, container, elementType);
			if (dialog != null) {
				createGMFCommand = new SetTargetAndRelationshipCommand(this.editingDomain, "Create " + elementType.getDisplayName(), serviceProvider, reference, container, elementType, dialog);
			}

		} else {
			CreateElementRequest request = buildRequest(reference, container, creationMenu, adviceCache, context);
			if (request != null) {
				createGMFCommand = provider.getEditCommand(request);
			} else {
				return UnexecutableCommand.INSTANCE;
			}

		}

		if (createGMFCommand != null) {
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(createGMFCommand);
			return emfCommand;
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @param adviceCache
	 * @return
	 *         the creation request to use in this handler
	 * @since 3.0
	 */
	protected CreateElementRequest buildRequest(EReference reference, EObject container, CreationMenu creationMenu, Map<?, ?> adviceCache, IClientContext context) {
		IElementType elementtype = getElementType(creationMenu.getElementType(), context);

		CreateElementRequest request = null;
		if (reference == null) {
			if (creationMenu instanceof CreateRelationshipMenu) {
				request = new CreateRelationshipRequest(editingDomain, null, container, null, elementtype);
			} else {
				request = new CreateElementRequest(editingDomain, container, elementtype);
			}
		} else {
			if (creationMenu instanceof CreateRelationshipMenu) {
				request = new CreateRelationshipRequest(editingDomain, null, container, null, elementtype, reference);
			} else {
				request = new CreateElementRequest(editingDomain, container, elementtype, reference);
			}
		}
		request.setParameter(RequestCacheEntries.Cache_Maps, adviceCache);
		return request;
	}

	/**
	 *
	 * @return
	 *         the creation request to use in this handler
	 * @since 3.0
	 */
	protected CreateElementRequest buildRequest(EReference reference, EObject container, CreationMenu creationMenu, IClientContext context) {
		IElementType elementtype = getElementType(creationMenu.getElementType(), context);

		if (elementtype != null) {
			if (reference == null) {
				if (creationMenu instanceof CreateRelationshipMenu) {
					CreateRelationshipRequest createRelationshipRequest = new CreateRelationshipRequest(editingDomain, null, container, null, elementtype);
					return createRelationshipRequest;
				} else {
					return new CreateElementRequest(editingDomain, container, elementtype);
				}
			} else {
				if (creationMenu instanceof CreateRelationshipMenu) {
					CreateRelationshipRequest createRelationshipRequest = new CreateRelationshipRequest(editingDomain, null, container, null, elementtype, reference);
					return createRelationshipRequest;
				} else {
					return new CreateElementRequest(editingDomain, container, elementtype, reference);
				}
			}
		} else {
			return null;
		}

	}

	/**
	 * Creates a dialog for selecting the target element
	 *
	 * @param eobject
	 * @param provider
	 * @param ted
	 * @param reference
	 * @param container
	 * @param et
	 * @return
	 */
	protected TreeSelectorDialog getTargetTreeSelectorDialog(EObject eobject, final IElementEditService provider,
			final TransactionalEditingDomain ted,
			final EReference reference,
			final EObject container,
			final IElementType et) {
		ILabelProvider labelProvider = null;
		try {
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, eobject).getLabelProvider();
		} catch (Exception ex) {
			Activator.log.error("Impossible to get a label provider from object " + eobject, ex);
			return null;
		}

		SemanticService semanticService = null;

		try {
			semanticService = ServiceUtilsForEObject.getInstance().getService(SemanticService.class, eobject);
		} catch (Exception e) {
			Activator.log.error("Could not get the SemanticService for " + eobject, e);
			return null;
		}

		// Get the primary language-specific content provider
		IModel[] models = semanticService.getSemanticIModels();
		Optional<ISemanticContentProviderFactory> factory = Stream.of(models)
				.map(m -> m.getAdapter(ISemanticContentProviderFactory.class))
				.filter(Objects::nonNull)
				.reduce(ISemanticContentProviderFactory::compose);

		ResourceSet rs = eobject.eResource().getResourceSet();
		ITreeContentProvider delegate = factory.orElse(SemanticEMFContentProvider::new)
				.createSemanticContentProvider(rs);

		ITreeContentProvider contentProvider = new DelegatingPapyrusContentProvider(delegate) {
			@Override
			public boolean isValidValue(Object element) {
				if (element == null) {
					return false;
				}

				EObject eobject = EMFHelper.getEObject(element);

				CreateElementRequest buildRequest = null;
				if (reference == null) {
					buildRequest = new CreateRelationshipRequest(ted, null, container, eobject, et);
				} else {
					buildRequest = new CreateRelationshipRequest(ted, null, container, eobject, et);
				}

				ICommand createGMFCommand = provider.getEditCommand(buildRequest);
				if (createGMFCommand == null) {
					return false;
				}
				boolean canExecute = createGMFCommand.canExecute();
				return canExecute;
			}
		};


		EMFGraphicalContentProvider graphicalContentProvider = ProviderHelper.encapsulateProvider(contentProvider, rs, "target");

		TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getDefault().getActiveShell());
		dialog.setContentProvider(graphicalContentProvider);
		dialog.setLabelProvider(labelProvider);
		dialog.setMessage("Choose the target element");
		dialog.setTitle("Target Element Selection");
		dialog.setInput(org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer(eobject));
		return dialog;
	}
}
