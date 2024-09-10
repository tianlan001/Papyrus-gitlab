/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 419357
 *  
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.diagram.common.dialogs.CreateOrShowExistingLinkDialog;
import org.eclipse.papyrus.uml.diagram.common.messages.Messages;
import org.eclipse.papyrus.uml.diagram.common.util.AssociationUtil;
import org.eclipse.papyrus.uml.diagram.common.util.LinkEndsMapper;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

/**
 *
 * This class provides command to restore existing link instead of create new ones
 *
 *
 *
 * @author Vincent Lorenzo
 *
 */
public class CreateOrShowExistingElementHelper {


	/**
	 * the link helper to use. Will be used in the future
	 */
	protected final ILinkMappingHelper linkMappingHelper;

	/**
	 *
	 * Constructor.
	 *
	 */
	public CreateOrShowExistingElementHelper() {
		this(new ILinkMappingHelper() {

			@Override
			public Collection<?> getTarget(Element link) {
				return LinkMappingHelper.getTarget(link);
			}

			@Override
			public Collection<?> getSource(Element link) {
				return LinkMappingHelper.getSource(link);
			}
		});
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param linkHelper
	 *            the mapping helper to use to find sources and targets
	 */
	public CreateOrShowExistingElementHelper(final ILinkMappingHelper linkHelper) {
		this.linkMappingHelper = linkHelper;
	}

	/**
	 * 
	 * Returns the command to restore or create a element.
	 *
	 * @param req
	 *            the create request
	 * @param defaultCommand
	 *            the default command for this request
	 * @param existingElements
	 *            the list of the link already existing between the source and the target for this kind of link
	 * @return The command to restore or create element.
	 */
	public Command getCreateOrRestoreElementCommand(final CreateRelationshipRequest req, final Command defaultCommand, final IElementType linkElementType) {
		if (showDialogAccordingPreferences() && defaultCommand.canExecute()) {
			final EObject container = req.getContainer();
			if (container instanceof Element) {
				final List<LinkEndsMapper> existingElements = getExistingLinksBetweenSourceAndTarget(req, linkElementType);
				if (existingElements.size() > 0) {
					final String className = getIElementTypeNameToDisplay(linkElementType);
					final String dialogTitle = NLS.bind(Messages.CreateOrShowExistingElementHelper_CreateOrRestoreX, className);
					final String dialogMessage = NLS.bind(Messages.CreateOrShowExistingElementHelper_XBetweenTheseElementAlreadyExists, className);
					CreateOrShowExistingLinkDialog dialog = new CreateOrShowExistingLinkDialog(dialogTitle, dialogMessage, existingElements);
					return new ICommandProxy(getOpenLinkDialogCommand(req, defaultCommand, dialog, existingElements));
				}
			}
		}
		return defaultCommand;
	}

	/**
	 * 
	 * Comparison of two types.
	 *
	 * @param element
	 *            an element
	 * @param elementType
	 *            an element type
	 * @return
	 *         <code>true</code> if the element has the wanted type according to the element type
	 */
	protected boolean hasWantedType(final EObject element, final IElementType elementType) {
		return element.eClass() == elementType.getEClass();
	}

	/**
	 * 
	 * Returns the preferences AND Create the preference if it doesn't yet exist.
	 *
	 * @return
	 *         the preference to know if a dialog must ask to the use if he wants create a new edge or restore an existing edge
	 */
	protected boolean showDialogAccordingPreferences() {
		boolean showDialog = true;
		final IPreferenceStore store = org.eclipse.papyrus.infra.gmfdiag.preferences.Activator.getDefault().getPreferenceStore();
		final String preferenceName = PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.RESTORE_LINK_ELEMENT);

		// If the store contains the preference, return the inverse of the value stored in the preferences.
		final boolean contains = store.contains(preferenceName);
		if (contains) {
			showDialog = !store.getBoolean(preferenceName);
		}
		return showDialog;
	}

	/**
	 * 
	 * Returns the name of the element type.
	 *
	 * @param elementType
	 *            an element type
	 * @return
	 *         the element type to display in the dialog
	 */
	protected String getIElementTypeNameToDisplay(final IElementType elementType) {
		return elementType.getEClass().getName();
	}

	/**
	 * 
	 * Returns the value of parameter "navigable" of the request.
	 * 
	 * @param source
	 *            If the member tested is the source.
	 * @param request
	 *            The creation relationship request.
	 * @return The value "navigable" of the member tested.
	 */
	private boolean getExpectedNavigable(final boolean source, final CreateRelationshipRequest request) {
		Object isAffects = null;
		boolean expectedNavigable = false;

		// Get the parameter "affects Target" to check if the target is navigable
		if (!source) {
			isAffects = request.getParameter(RequestParameterConstants.AFFECTS_TARGET);

			if (null != isAffects && isAffects.equals(true)) {
				expectedNavigable = true;
			}
		}

		return expectedNavigable;
	}

	/**
	 * 
	 * Returns the existing links between two objects.
	 * 
	 * @param request
	 *            the request to create the element
	 * @param wantedEClass
	 * @return
	 *         a list of {@link EdgeEndsMapper} referencing the existing links between the source and the target
	 */
	protected List<LinkEndsMapper> getExistingLinksBetweenSourceAndTarget(final CreateRelationshipRequest request, final IElementType wantedElementType) {
		final List<LinkEndsMapper> existingElement = new ArrayList<LinkEndsMapper>();
		final EClass wantedEClass = wantedElementType.getEClass();

		if (UMLPackage.eINSTANCE.getAssociation().equals(wantedEClass)) {
			for (final Element current : ((Element) request.getContainer()).getOwnedElements()) {
				if (hasWantedType(current, wantedElementType)) {
					List<?> sources = new ArrayList<>(this.linkMappingHelper.getSource(current));
					List<?> targets = new ArrayList<>(this.linkMappingHelper.getTarget(current));

					EObject sourceRequest = request.getSource();
					EObject targetRequest = request.getTarget();

					if (sources.contains(sourceRequest) && targets.contains(targetRequest)) {
						boolean identicalAssociation = true;

						// Check if association is reflexive
						if (sourceRequest.equals(targetRequest)) {
							identicalAssociation = false;
							Property firstEnd = AssociationUtil.getInitialTargetFirstEnd((Association) current);
							Property secondEnd = AssociationUtil.getInitialSourceSecondEnd((Association) current);
							// Compare memebers ends of the current association
							if (null != firstEnd && null != secondEnd) {
								if (firstEnd.getType().equals(secondEnd.getType())) {
									// Check with the firstEnd = source and the secondEnd = target
									boolean checkSourceAndTarget = checkReflexiveAssociation(request, firstEnd, secondEnd);
									// If it's not identical, check firstEnd = target and secondEnd = source.
									if (!checkSourceAndTarget) {
										checkSourceAndTarget = checkReflexiveAssociation(request, secondEnd, firstEnd);
									}
									if (checkSourceAndTarget) {
										identicalAssociation = true;
									}
								}
							}
						} else {
							final Iterator<Property> iterator = ((Association) current).getMemberEnds().iterator();

							while (iterator.hasNext() && identicalAssociation) {
								final Property property = iterator.next();
								final boolean isSource = sourceRequest.equals(property.getType());

								boolean expectedNavigable = getExpectedNavigable(isSource, request);

								if (!AssociationUtil.isIdenticalProperties(expectedNavigable, AggregationKind.NONE_LITERAL, property)) {
									identicalAssociation = false;
								}
							}
						}

						if (identicalAssociation) {
							existingElement.add(new LinkEndsMapper(current, sources, null, null));
						}
					}
				}
			}
		} else if (UMLPackage.eINSTANCE.getTransition().equals(wantedEClass)) {
			for (final Element current : ((Region) request.getContainer()).getTransitions()) {
				if (hasWantedType(current, wantedElementType)) {
					final Collection<?> sources = this.linkMappingHelper.getSource(current);
					final Collection<?> targets = this.linkMappingHelper.getTarget(current);
					if (sources.contains(request.getSource()) && targets.contains(request.getTarget())) {
						existingElement.add(new LinkEndsMapper(current, null, sources, targets));
					}
				}
			}
		} else {
			for (final Element current : ((Element) request.getContainer()).getOwnedElements()) {
				if (hasWantedType(current, wantedElementType)) {
					final Collection<?> sources = this.linkMappingHelper.getSource(current);
					final Collection<?> targets = this.linkMappingHelper.getTarget(current);
					if (sources.contains(request.getSource()) && targets.contains(request.getTarget())) {
						if (UMLPackage.eINSTANCE.getConnector().equals(wantedEClass)) {
							existingElement.add(new LinkEndsMapper(current, sources, null, null));
						} else {
							existingElement.add(new LinkEndsMapper(current, null, sources, targets));
						}
					}
				}
			}
		}
		return existingElement;
	}

	/**
	 * Compare two memberEnds of a reflexive association.
	 * 
	 * @param request
	 *            The CreateRelationshipRequest.
	 * @param firstProperty
	 *            The first property of the current association.
	 * @param secondProperty
	 *            The second property of the current association.
	 * @return
	 */
	private boolean checkReflexiveAssociation(CreateRelationshipRequest request, Property firstProperty, Property secondProperty) {
		boolean expectedNavigable = getExpectedNavigable(true, request);
		if (!AssociationUtil.isIdenticalProperties(expectedNavigable, AggregationKind.NONE_LITERAL, firstProperty)) {
			return false;
		}

		expectedNavigable = getExpectedNavigable(false, request);
		if (!AssociationUtil.isIdenticalProperties(expectedNavigable, AggregationKind.NONE_LITERAL, secondProperty)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * Returns the open link dialog.
	 *
	 * @param request
	 *            the request (can't be <code>null</code>)
	 * @param defaultCommand
	 *            the default command to use to create the semantic element (can't be <code>null</code>)
	 * @param dialog
	 *            the dialog to open (can't be <code>null</code>)
	 * @param existingEObject
	 *            the list of the existing objects (can't be null, neither empty
	 * @return
	 *         the command to open the dialog AND do the selected action
	 */
	public static final ICommand getOpenLinkDialogCommand(final CreateRelationshipRequest request, final Command defaultCommand, final CreateOrShowExistingLinkDialog dialog, final List<LinkEndsMapper> existingEObject) {
		final AbstractTransactionalCommand compoundCommand = new AbstractTransactionalCommand(request.getEditingDomain(), "", null) { //$NON-NLS-1$

			/**
			 *
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 *
			 * @param monitor
			 * @param info
			 * @return
			 * @throws ExecutionException
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				int result = dialog.open();
				switch (result) {
				case CreateOrShowExistingLinkDialog.CREATE:
					// we execute the default command
					defaultCommand.execute();
					return CommandResult.newOKCommandResult(request.getNewElement());
				case CreateOrShowExistingLinkDialog.RESTORE_SELECTED_LINK:
					final EObject selectedElement = dialog.getResult();
					if (selectedElement != null) {// must always be true
						request.setNewElement(selectedElement);
						return CommandResult.newOKCommandResult(selectedElement);
					} else {
						return CommandResult.newErrorCommandResult("The selected element in the dialog is null"); //$NON-NLS-1$
					}
				default:
					return CommandResult.newCancelledCommandResult();
				}
			}

			/**
			 *
			 * @see org.eclipse.core.commands.operations.AbstractOperation#canExecute()
			 *
			 * @return
			 */
			@Override
			public boolean canExecute() {
				return defaultCommand.canExecute();
			}
		};
		return compoundCommand;
	}

	/**
	 *
	 * @return
	 *         the link mapping helper to use
	 */
	public ILinkMappingHelper getLinkMappingHelper() {
		return linkMappingHelper;
	}
}
