/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.profile.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueSelectionDialog;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.profile.Activator;
import org.eclipse.papyrus.uml.domain.services.create.CreationStatus;
import org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreator;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.tools.providers.UMLMetaclassContentProvider;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services specific to the Profile Diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class ProfileDiagramServices extends AbstractDiagramServices {


	/**
	 * Copied from org.eclipse.papyrus.uml.diagram.profile.custom.commands.CustomSemanticCreateCommand.
	 * This service opens the dialog for selecting the metaclasses,<br />
	 * add them in the imported element list of the given profile and create the corresponding nodes in the diagram.
	 * 
	 * @param profile
	 *            the current profile in which we want to add metaclasses.
	 * @param containerView
	 *            the container view in which the metaclasses will be created.
	 */
	public void importMetaClass(Profile profile, DSemanticDecorator containerView) {
		List<? extends PackageableElement> metaClassesToAdd = selectNewMetaclasses(profile);
		if (!metaClassesToAdd.isEmpty()) {
			this.addMetaclass(profile, metaClassesToAdd, containerView);
			metaClassesToAdd.forEach(metaClass -> {
				if (!isMetaClassAlreadyRepresented(containerView, metaClass)) {
					this.createMetaclassNode(profile, containerView, metaClass);
				}
			});
		}
	}

	private List<? extends PackageableElement> selectNewMetaclasses(Profile profile) {
		List<? extends PackageableElement> addedMetaClasses = new ArrayList<>();
		ReferenceSelector selector = new ReferenceSelector(true);
		selector.setContentProvider(new UMLMetaclassContentProvider(profile));
		LabelProviderService serv = null;
		try {
			serv = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, profile);
		} catch (ServiceException e) {
			Activator.log.error("Label Provider Service not found", e); //$NON-NLS-1$
		}
		ILabelProvider labelProvider = serv.getLabelProvider();
		selector.setLabelProvider(labelProvider);
		// we don't set initial selection to allows to draw new instance of imported metaclass
		final List<EObject> alreadyImportedElement = new ArrayList<>();
		final MultipleValueSelectionDialog dialog = new MultipleValueSelectionDialog(Display.getDefault().getActiveShell(), selector, "Select Metaclass", true, false, -1); //$NON-NLS-1$
		dialog.setContextElement(profile);
		dialog.setLabelProvider(labelProvider);
		dialog.setInitialElementSelections(alreadyImportedElement);

		if (dialog.open() == IStatus.OK) {
			Object[] selectedMetaclass = dialog.getResult();
			addedMetaClasses = Arrays.stream(selectedMetaclass).filter(PackageableElement.class::isInstance).map(PackageableElement.class::cast).collect(Collectors.toList());
		}
		return addedMetaClasses;
	}

	private void addMetaclass(Profile profile, List<? extends PackageableElement> metaClassesToAdd, DSemanticDecorator containerView) {
		ElementCreator elementCreator = new ElementCreator(new ElementConfigurer(), new ElementFeatureModifier(getECrossReferenceAdapter(profile), new EditableChecker()));

		// ----Obtain the actual imported metaclasses
		EList<org.eclipse.uml2.uml.Class> metaclassSet = profile.getReferencedMetaclasses();

		// we add the new metaclasses
		for (int i = 0; i < metaClassesToAdd.size(); i++) {
			// we need to import the metaclass
			if (!metaclassSet.contains(metaClassesToAdd.get(i))) {

				PackageableElement importedMetaClass = metaClassesToAdd.get(i);
				ElementImport added = null;

				// we need make the import only if it doesn't exist yet!
				EList<ElementImport> list = profile.getElementImports();

				// We search if this metaclass is not imported yet
				for (int iter = 0; iter < list.size(); iter++) {
					if (list.get(iter) != null) {
						if (list.get(iter).getImportedElement() != null) {
							if (importedMetaClass.getQualifiedName().equals(list.get(iter).getImportedElement().getQualifiedName())) {
								added = list.get(iter);// iterator.previous();
								break;
							}
						}
					}
				}
				if (added == null) {
					CreationStatus creationStatus = elementCreator.create(profile, UMLPackage.eINSTANCE.getElementImport().getName(), UMLPackage.eINSTANCE.getProfile_MetaclassReference().getName());
					added = Optional.ofNullable(creationStatus.getElement())
							.filter(ElementImport.class::isInstance)//
							.map(ElementImport.class::cast)//
							.orElse(null);//
					if (added != null) {
						added.setImportedElement(importedMetaClass);
						added.setAlias(importedMetaClass.getName());
					}
				}

			} else {
				// the metaclass is already imported, nothing to do here!
			}
		}
	}

	private void createMetaclassNode(Profile profile, DSemanticDecorator containerView, PackageableElement metaClass) {
		final Session session = SessionManager.INSTANCE.getSession(profile);
		createView(metaClass, containerView, session, "aql:containerView"); //$NON-NLS-1$
	}

	/**
	 * Checks if the given metaClass is represented in the given containerView.
	 * 
	 * @param containerView
	 *            The Sirius container view (diagram or nodeContainer)
	 * @param metaClass
	 *            the metaClass to look for the node.
	 * @return true if a node representing the metaClass already exist. Otherwise false.
	 */
	private boolean isMetaClassAlreadyRepresented(DSemanticDecorator containerView, PackageableElement metaClass) {
		List<DDiagramElement> dDiagramElements = List.of();
		if (containerView instanceof DSemanticDiagram) {
			dDiagramElements = ((DSemanticDiagram) containerView).getOwnedDiagramElements();
		} else if (containerView instanceof DNodeContainer) {
			dDiagramElements = ((DNodeContainer) containerView).getOwnedDiagramElements();
		}
		Optional<DDiagramElement> optionalExistingDiagramElement = dDiagramElements.stream()
				.filter(element -> element.getTarget().equals(metaClass))//
				.findFirst();//
		return optionalExistingDiagramElement.isPresent();
	}
}
