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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.paste;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.paste.IPastePostAction;
import org.eclipse.papyrus.uml.nattable.utils.Constants;
import org.eclipse.papyrus.uml.tools.utils.CustomElementOperations;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 *
 * @author Vincent Lorenzo
 *         This post action allows to apply stereotypes on the pasted element
 */
public class ApplyStereotypePastePostAction implements IPastePostAction {


	/**
	 * the key to found the stereotypes to add to the resources
	 */
	private static final String STEREOTYPES_APPLICATIONS_TO_ADD_TO_RESOURCE = "stereotypesApplicationsToAddToResource"; //$NON-NLS-1$

	/**
	 *
	 * @param postActionId
	 * @see org.eclipse.papyrus.infra.nattable.paste.IPastePostAction#accept(java.lang.String)
	 *
	 * @return
	 */
	@Override
	public boolean accept(final String postActionId) {
		return postActionId != null && postActionId.startsWith(Constants.POST_ACTION_APPLY_STEREOTYPE_PREFIX);
	}

	/**
	 *
	 * @param ste
	 *            a stereotype
	 * @param steApp
	 *            its stereotype application
	 * @return
	 *         a mapping between the stereotype and its emf instanciation
	 */
	private Map<Property, EStructuralFeature> getMapping(final Stereotype ste, final EObject steApp) {
		Map<Property, EStructuralFeature> map = new HashMap<Property, EStructuralFeature>();
		Map<String, EStructuralFeature> features = new HashMap<String, EStructuralFeature>();
		for (final EStructuralFeature f : steApp.eClass().getEAllStructuralFeatures()) {
			features.put(f.getName(), f);
		}

		for (final Property current : StereotypeUtil.getAllStereotypePropertiesWithoutBaseProperties(ste)) {
			EStructuralFeature f = features.get(UML2Util.getValidJavaIdentifier(current.getName()));
			Assert.isNotNull(f);
			map.put(current, f);
		}
		return map;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.paste.IPastePostAction#doPostAction(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager, java.lang.String, java.lang.Object, java.util.Map)
	 *
	 * @param tableManager
	 * @param postAction
	 * @param editedElement
	 * @param sharedMap
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doPostAction(INattableModelManager tableManager, String postAction, Object editedElement, Map<Object, Object> sharedMap) {
		// TODO : we must do verification before to apply stereotypes on an element : extened metaclass + stereotype already apply or not, + profile apply on the model
		if (editedElement instanceof Element && !(editedElement instanceof Stereotype) && tableManager.getTable().getContext() instanceof Element) {
			final Element elementToStereotype = (Element) editedElement;
			final Element tableContext = (Element) tableManager.getTable().getContext();
			boolean alreadyApplied = false;
			final String stereotypeQN = postAction.replace(Constants.POST_ACTION_APPLY_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$
			alreadyApplied = elementToStereotype.eResource() != null && elementToStereotype.getAppliedStereotype(stereotypeQN) != null;
			if (!alreadyApplied) {
				Stereotype findStereotype = null;
				
				// Search the stereotype corresponding to the stereotype qualified name
				final Iterator<Profile> appliedProfilesIterator = tableContext.getNearestPackage().getAllAppliedProfiles().iterator();
				while(appliedProfilesIterator.hasNext() && null == findStereotype){
					final Profile existingAppliedProfile = appliedProfilesIterator.next();

					// Bug 488082 : Loop on all stereotypes (check in sub packages)
					final Iterator<Stereotype> stereotypesIterator = StereotypeUtil.getAllStereotypes(existingAppliedProfile).iterator();
					while(stereotypesIterator.hasNext() && null == findStereotype){
						final Stereotype ownedStereotype = stereotypesIterator.next();
						if(ownedStereotype.getQualifiedName().equals(stereotypeQN)){
							findStereotype = ownedStereotype;
						}
					}
				}

				if (findStereotype != null) {
					final EObject stereotypeApplication = CustomElementOperations.applyStereotype(tableContext.getNearestPackage(), elementToStereotype, findStereotype);
					final Collection<EObject> steAppList;
					if (sharedMap.containsKey(STEREOTYPES_APPLICATIONS_TO_ADD_TO_RESOURCE)) {
						steAppList = (Collection<EObject>) sharedMap.get(STEREOTYPES_APPLICATIONS_TO_ADD_TO_RESOURCE);
					} else {
						steAppList = new ArrayList<EObject>();
						sharedMap.put(STEREOTYPES_APPLICATIONS_TO_ADD_TO_RESOURCE, steAppList);
					}
					steAppList.add(stereotypeApplication);

					// build the mapping between the stereotype and its emf instanciation
					Map<Property, EStructuralFeature> mapping = getMapping(findStereotype, stereotypeApplication);
					final List<StereotypeApplicationStructure> struct;
					if (sharedMap.containsKey(elementToStereotype)) {
						struct = (List<StereotypeApplicationStructure>) sharedMap.get(elementToStereotype);
					} else {
						struct = new ArrayList<StereotypeApplicationStructure>();
						sharedMap.put(elementToStereotype, struct);
					}
					// create the structure used to store this mapping
					for (final Entry<Property, EStructuralFeature> current : mapping.entrySet()) {
						final StereotypeApplicationStructure structure = new StereotypeApplicationStructure(findStereotype, stereotypeApplication, current.getKey(), current.getValue());
						struct.add(structure);
					}
				}
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.paste.IPastePostAction#concludePostAction(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager, java.lang.String, java.util.Map)
	 *
	 * @param tableManager
	 * @param postActionId
	 * @param sharedMap
	 */
	@Override
	public void concludePostAction(final INattableModelManager tableManager, final String postActionId, final Map<Object, Object> sharedMap) {
		@SuppressWarnings("unchecked")
		// must always work!
		final Collection<EObject> value = (Collection<EObject>) sharedMap.get(STEREOTYPES_APPLICATIONS_TO_ADD_TO_RESOURCE);
		final Map<Resource, List<EObject>> resourcesAndSteApp = new HashMap<Resource, List<EObject>>();

		Iterator<EObject> iter = value.iterator();
		while (iter.hasNext()) {
			final EObject current = iter.next();
			final Resource res = getResourceToStoreStereotypeApplication(tableManager, current);
			final List<EObject> toAdd;
			if (resourcesAndSteApp.containsKey(res)) {
				toAdd = resourcesAndSteApp.get(res);
			} else {
				toAdd = new ArrayList<EObject>();
				resourcesAndSteApp.put(res, toAdd);
			}
			toAdd.add(current);
		}

		for (final Entry<Resource, List<EObject>> entry : resourcesAndSteApp.entrySet()) {
			entry.getKey().getContents().addAll(entry.getValue());
		}
	}

	/**
	 *
	 * @param tableManager
	 *            the table manager
	 * @param stereotypeApplication
	 *            the stereotype application
	 * @return
	 *         the resource to use to store the steretoype application
	 */
	private Resource getResourceToStoreStereotypeApplication(final INattableModelManager tableManager, final EObject stereotypeApplication) {
		return tableManager.getTable().getContext().eResource();
	}

	public static final List<Stereotype> getApplicableStereotypes(final Element el, final Element context, final Resource resource) {
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>();
		stereotypes.addAll(CustomElementOperations.getApplicableStereotypes(el, context.getNearestPackage()));
		return stereotypes;
	}



	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.paste.IPastePostAction#getAvailablePostActionIds(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager, java.lang.Object)
	 *
	 * @param tableManager
	 * @param object
	 * @return
	 */
	@Override
	public Collection<String> getAvailablePostActionIds(final INattableModelManager tableManager, final Object object) {
		final Collection<String> postActions = new ArrayList<String>();
		if (object instanceof EClass && UMLPackage.eINSTANCE.getEClassifiers().contains(object)) {
			final EClass eClass = (EClass) object;
			final Element instance = (Element) eClass.getEPackage().getEFactoryInstance().create(eClass);

			List<Stereotype> applicableStereotypes = getApplicableStereotypes(instance, (Element) tableManager.getTable().getContext(), tableManager.getTable().getContext().eResource());
			for (Stereotype stereotype : applicableStereotypes) {
				postActions.add(Constants.POST_ACTION_APPLY_STEREOTYPE_PREFIX + stereotype.getQualifiedName());
			}
		}
		return postActions;
	}
}
