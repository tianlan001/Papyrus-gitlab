/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 573987, 573986
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.CreateSectionAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.CreateSectionWidgetAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.MoDiscoCopyAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.MoDiscoCutAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.MoDiscoDeleteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.MoDiscoPasteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.ToggleAnnotationsAction;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.ValidationAction;
import org.eclipse.papyrus.views.properties.toolsmiths.messages.Messages;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ActionUtil;

/**
 * The Action bar contributor for the Context Editor
 * Mainly serves as an Adapter for the Ecore actions, as the Ecore actions are
 * not natively compatible with the EMF Facet tree objects
 *
 * @author Camille Letavernier
 */
public class ContextEditorActionBarContributor extends EcoreActionBarContributor {

	private static final String MENU_ID = "org.eclipse.papyrus.views.properties.toolsmiths.editor.menu"; //$NON-NLS-1$
	private int i = 0;

	private final ActionUtil actionUtil;

	/**
	 *
	 * Constructor.
	 *
	 */
	public ContextEditorActionBarContributor() {
		super();

		actionUtil = new ActionUtil(this, super::selectionChanged, super::generateCreateChildActions, super::generateCreateSiblingActions);
		validateAction = new ValidationAction();
	}

	@Override
	protected IMenuManager createSubmenuManager() {
		return new MenuManager(Messages.ContextEditorActionBarContributor_0, MENU_ID);
	}

	@Override
	protected void addGlobalActions(IMenuManager menuManager) {
		super.addGlobalActions(menuManager);
	}

	@Override
	public void menuAboutToShow(IMenuManager menuManager) {
		super.menuAboutToShow(menuManager);
	}

	@Override
	protected Collection<IAction> generateCreateChildActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> result = actionUtil.createNewChildActions(descriptors, selection);

		Object firstElement = ActionUtil.getElement(selection);
		if (firstElement instanceof View) {
			result.addAll(createChildForView(selection, (View) firstElement));
		} else if (firstElement instanceof Section) {
			result.addAll(createChildForSection(selection));
		} else if (firstElement instanceof Tab) {
			removeChildActionsForTab(result);
		} else if (firstElement instanceof Context) {
			removeChildActionsForContext(result);
		}

		return result;
	}

	@Override
	protected Collection<IAction> generateCreateSiblingActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> result = actionUtil.createNewSiblingActions(descriptors, selection);

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			if (sSelection.size() == 1) {
				Object firstElement = sSelection.getFirstElement();
				if (firstElement instanceof Section) {
					removeSiblingActionsForSection(result);
					createSiblingActionsForSection(sSelection, (Section) firstElement);
				} else if (firstElement instanceof View || firstElement instanceof Tab) {
					removeSiblingActionsForViewAndTab(result);
				}
			}
		}
		return result;
	}

	/**
	 * Remove the unused actions from the Tab's create child section
	 *
	 * @param actions
	 *            The actions generated by the Ecore action bar
	 */
	protected void removeChildActionsForTab(Collection<IAction> actions) {
		Iterator<IAction> iterator = actions.iterator();
		while (iterator.hasNext()) {
			IAction action = iterator.next();
			if (action instanceof CreateChildAction) {
				CreateChildAction createChildAction = (CreateChildAction) action;
				if (createChildAction.getText().equals("Section")) { // It's the only relevant property we have access to... //$NON-NLS-1$
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Remove the unused actions from the Context's create child section
	 *
	 * @param actions
	 *            The actions generated by the Ecore action bar
	 */
	protected void removeChildActionsForContext(Collection<IAction> actions) {
		Iterator<IAction> iterator = actions.iterator();
		while (iterator.hasNext()) {
			IAction action = iterator.next();
			if (action instanceof CreateChildAction) {
				CreateChildAction createChildAction = (CreateChildAction) action;
				if (createChildAction.getText().equals("Data Context Root")) { // It's the only relevant property we have access to... //$NON-NLS-1$
					iterator.remove();
				}
				if (!ToggleAnnotationsAction.showAnnotations && createChildAction.getText().equals("Annotation")) { //$NON-NLS-1$
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Remove the unused actions from the Tab and View's create sibling section
	 *
	 * @param actions
	 *            The actions generated by the Ecore action bar
	 */
	protected void removeSiblingActionsForViewAndTab(Collection<IAction> actions) {
		Iterator<IAction> iterator = actions.iterator();
		while (iterator.hasNext()) {
			IAction action = iterator.next();
			if (action instanceof CreateSiblingAction) {
				CreateSiblingAction createSiblingAction = (CreateSiblingAction) action;
				if (createSiblingAction.getText().equals("Data Context Root")) { // It's the only relevant property we have access to... //$NON-NLS-1$
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Remove the unused actions from the Section's create sibling section
	 *
	 * @param actions
	 *            The actions generated by the Ecore action bar
	 */
	protected void removeSiblingActionsForSection(Collection<IAction> actions) {
		Iterator<IAction> iterator = actions.iterator();
		while (iterator.hasNext()) {
			IAction action = iterator.next();
			if (action instanceof CreateSiblingAction) {
				CreateSiblingAction createSiblingAction = (CreateSiblingAction) action;
				if (createSiblingAction.getText().equals("Section")) { // It's the only relevant property we have access to... //$NON-NLS-1$
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Adds new actions in the Section's create sibling section
	 *
	 * @param selection
	 *            The current selection
	 * @param section
	 *            The section for which we want to add new actions
	 * @return
	 *         The list of newly created {@link IAction}s
	 */
	protected Collection<IAction> createSiblingActionsForSection(ISelection selection, Section section) {
		Collection<IAction> actions = new LinkedList<>();

		// TODO : We need to retrieve the view owning the section. It is only possible with an access to the
		// ITreeElements, which we don't have here. Find a way to retrieve it.

		// String sectionName = getSectionName(view.getContext());
		// String sectionFile = getSectionFile(sectionName);
		// IAction action = new CreateSectionAction(selection, sectionName, sectionFile);
		// actions.add(action);

		return actions;
	}

	/**
	 * Adds new actions in the View's create child section
	 *
	 * @param selection
	 *            The current selection
	 * @param view
	 *            The View for which we want to add new actions
	 * @return
	 *         The list of newly created {@link IAction}s
	 */
	protected Collection<IAction> createChildForView(ISelection selection, View view) {
		Collection<IAction> actions = new LinkedList<>();
		if (view.getContext() == null) {
			return actions;
		}

		String sectionName = getSectionName(view.getContext());
		String sectionFile = getSectionFile(sectionName);
		IAction action = new CreateSectionAction(selection, sectionName, sectionFile);
		actions.add(action);

		return actions;
	}

	/**
	 * Generate a name for a new section in the given context
	 *
	 * @param context
	 *            The context in which the new section will be created
	 * @return
	 *         The generated name (Which should be unique in the given context)
	 */
	protected String getSectionName(Context context) {
		String name;
		while (true) {
			name = "Section " + i; //$NON-NLS-1$
			if (isValidName(name, context)) {
				return name;
			}
			i++;
		}
	}

	/**
	 * Tests if a section name is valid
	 *
	 * @param sectionName
	 *            The name to test
	 * @param context
	 *            The Context in which the section name will be used
	 * @return
	 *         True is the name is a valid section name
	 */
	protected boolean isValidName(String sectionName, Context context) {
		for (Tab tab : context.getTabs()) {
			for (Section section : tab.getSections()) {
				if (section.getName().equals(sectionName)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Return the path of the section file from a section name
	 *
	 * @param sectionName
	 *            The name of the section
	 * @return
	 *         The path to the section's XWT file
	 */
	protected String getSectionFile(String sectionName) {
		return "ui/" + sectionName + ".xwt"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Adds new actions to the Section's create child section
	 *
	 * @param selection
	 *            The current seleection
	 * @return
	 *         The newly created {@link IAction}s
	 */
	protected Collection<IAction> createChildForSection(ISelection selection) {
		Collection<IAction> actions = new LinkedList<>();

		actions.add(new CreateSectionWidgetAction(selection));

		return actions;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		actionUtil.selectionChanged(event);
	}

	@Override
	protected DeleteAction createDeleteAction() {
		return new MoDiscoDeleteAction(removeAllReferencesOnDelete());
	}

	@Override
	protected boolean removeAllReferencesOnDelete() {
		return true; // Our "delete" action scans for references only in context resources, not *.xwt files
		// (Which cannot contain references to the deleted element, and are really slow to load)
	}

	@Override
	protected CutAction createCutAction() {
		return new MoDiscoCutAction();
	}

	@Override
	protected CopyAction createCopyAction() {
		return new MoDiscoCopyAction();
	}

	@Override
	protected PasteAction createPasteAction() {
		return new MoDiscoPasteAction();
	}

}
