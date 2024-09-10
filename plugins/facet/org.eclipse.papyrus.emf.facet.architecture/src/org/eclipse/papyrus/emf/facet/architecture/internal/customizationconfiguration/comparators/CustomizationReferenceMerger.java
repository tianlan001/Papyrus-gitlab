/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Activator;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

/**
 * This class is in charge to provide the validation and the merge of declarde {@link EMFFacetTreeViewerConfiguration}.
 *
 * The algorithm used to do the merge
 * <ol>
 * <li>find all {@link EMFFacetTreeViewerConfiguration#getExtends()} == null, they will be applied first</li>
 * <ul>
 * <li>they only can contains {@link CustomizationReference} with {@link CustomizationReference#getApplicationRule()} instanceof {@link AbsoluteOrder}</li>
 * <li>we check the order is unique</li>
 * </ul>
 * <li>we organize the other {@link EMFFacetTreeViewerConfiguration} to apply them by order of extension</li>
 * <li>from the previous result, we organize their owned {@link CustomizationReference} to apply them by order of extension</li>
 * <ul>
 * <li>we check that each {@link CustomizationReference} is redefined only one time (or zero)</li>
 * <li>we check that each {@link CustomizationReference} is referenced as BEFORE and/or AFTER only one time (or zero)</li>
 * </ul>
 * <li>for each level of extension, we build a new intermediate result:</li>
 * <ul>
 * <li>we apply the redefinitions</li>
 * <li>then we apply the relative order (BEFORE/AFTER)</li>
 * <li>at each step, we do checks</li>
 * </ul>
 * <li>if during one of the described steps, we find a trouble in the model, we will stop the merge process and the {@link Customization} returned by the method {@link #getMergedCustomizations()}
 * will be the last intermediate built result, before we met a problem</li>
 * </ol>
 *
 *
 */
public class CustomizationReferenceMerger implements ICustomizationReferenceMerger {

	/**
	 * The initial input
	 */
	private final Collection<EMFFacetTreeViewerConfiguration> input;


	private final List<Customization> mergedCustomizations = new ArrayList<>();
	/**
	 * The status (warning/error) by EObject
	 */
	private final Map<EObject, List<IStatus>> statusMap = new HashMap<>();


	// public CustomizationReferenceMerger(final Collection<TreeViewerConfiguration> configurations) {
	// this.input = (configurations.stream().filter(EMFFacetTreeViewerConfiguration.class::isInstance).map(EMFFacetTreeViewerConfiguration.class::cast).collect(Collectors.toList()));
	// }


	/**
	 * the input list of configurations ( {@link EMFFacetTreeViewerConfiguration}
	 */
	public CustomizationReferenceMerger(final Collection<EMFFacetTreeViewerConfiguration> references) {
		this.input = references;
	}

	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger#doValidationAndMerge()
	 *
	 * @return
	 */
	@Override
	public boolean doValidationAndMerge() {
		this.statusMap.clear();
		this.mergedCustomizations.clear();

		if (this.input == null || this.input.isEmpty()) {
			// we consider all is valid
			return isValid();
		}
		// create the intermediate list of customization to apply
		List<CustomizationReference> intermediateState = new ArrayList<>();

		// 0. check input
		checkInput();

		// 1. we organize the configuration by level of application
		final List<List<EMFFacetTreeViewerConfiguration>> configurationToMergeByLevel = new ArrayList<>();
		final Collection<EMFFacetTreeViewerConfiguration> roots = new ArrayList<>();
		if (isValid()) {
			configurationToMergeByLevel.addAll(organizeEMFFacetTreeViewerConfigurations(this.input));
			// we take the roots and we remove them from the list
			roots.addAll(configurationToMergeByLevel.remove(0));
			// 2. check root definition: only absolute order are allowed
			checkRootsDefinitions(roots);

			// 3. find all absolute order and build it
			if (isValid()) {
				final List<CustomizationReference> allAbsoluteOrder = new ArrayList<>();
				for (final EMFFacetTreeViewerConfiguration current : roots) {
					allAbsoluteOrder.addAll(current.getCustomizationReferences());
				}
				// 4. find others absolute order in all others EMFFacetTreeViewerConfiguration
				final Collection<EMFFacetTreeViewerConfiguration> absoluteOrderLocation = new ArrayList<>(this.input);
				absoluteOrderLocation.removeAll(roots);
				allAbsoluteOrder.addAll(getAllAbsoluteOrder(absoluteOrderLocation));

				// 5. check unicity of absolute order
				checkAbsoluteOrderUnitity(allAbsoluteOrder);

				if (isValid()) {
					// 6. sort all absolute order declaration
					intermediateState.addAll(allAbsoluteOrder);
					Collections.sort(intermediateState, new CustomizationReferenceAbsoluteOrderComparator());

					// ---------------at this point, we finished to manage the absolute order---------------------------

					// ---------------no we will manage the redefines and BEFORE/AFTER level by level


					// 7. build the list CustomizationReference to apply level by level
					List<List<CustomizationReference>> customizationReferenceByLevel = organizeCustomizationReferences(configurationToMergeByLevel);

					// 8. remove all absolute order (already managed in a previous steps) from the previous list
					excludeAbsoluteOrder(customizationReferenceByLevel);

					// 9. check redefines unicity
					checkRedefines(customizationReferenceByLevel);

					// 10. check all before/after unicity
					if (isValid()) {
						checkBeforeAfter(customizationReferenceByLevel);

						// 10. so here, all possibles checks have been done, we compose the final list of CustomizationReference
						if (isValid()) {
							for (final List<CustomizationReference> referencesList : customizationReferenceByLevel) {
								final List<CustomizationReference> newState = mergeCustomization(Collections.unmodifiableList(intermediateState), referencesList);
								if (isValid()) {
									intermediateState = newState;
								} else {
									break;
								}
							}
						}
					}
				}
			}
		}





		// 11. we build the list of merged customization
		for (final CustomizationReference current : intermediateState) {
			this.mergedCustomizations.add(current.getReferencedCustomization());
		}
		return isValid();
	}

	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger#getStatus()
	 *
	 * @return
	 */
	@Override
	public Map<EObject, IStatus> getStatus() {
		final Map<EObject, IStatus> resultingStatus = new HashMap<>();
		for (final Entry<EObject, List<IStatus>> entry : this.statusMap.entrySet()) {
			if (entry.getValue().size() > 1) {
				final MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, CustomizationMergeErrorCode.MULTI_STATUS_CODE, "Several error on me");
				for (final IStatus current : entry.getValue()) {
					multiStatus.add(current);
				}
				resultingStatus.put(entry.getKey(), multiStatus);
			} else {
				resultingStatus.put(entry.getKey(), entry.getValue().get(0));
			}
		}
		return resultingStatus;
	}

	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger#getMergedCustomizations()
	 *
	 * @return
	 */
	@Override
	public List<Customization> getMergedCustomizations() {
		return this.mergedCustomizations;
	}

	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger#isValid()
	 *
	 * @return
	 */
	@Override
	public boolean isValid() {
		return this.statusMap.isEmpty();
	}

	/**
	 * The the input consistency
	 */
	private void checkInput() {
		for (EMFFacetTreeViewerConfiguration current : this.input) {
			if (current.getExtends() != null && !this.input.contains(current.getExtends())) {
				createErrorStatus(current, CustomizationMergeErrorCode.CHECK_ERROR__REFERENCE_OUTSIDE_OF_ADL,
						NLS.bind("I'm extending a configuration which is not available in the current {0}.", ArchitecturePackage.eINSTANCE.getArchitectureDescriptionLanguage().getName()));
			}
		}

		// check loop
		for (EMFFacetTreeViewerConfiguration current : this.input) {
			List<EMFFacetTreeViewerConfiguration> alreadyCrossed = new ArrayList<>();
			EMFFacetTreeViewerConfiguration tmp = current;
			while (tmp != null) {
				if (alreadyCrossed.contains(tmp)) {
					createErrorStatus(current, CustomizationMergeErrorCode.CHECK_ERROR__EXTENDS_INFINITE_LOOP,
							NLS.bind("There is an infinite loop with with the extends declaration.", null));
					break;
				}
				alreadyCrossed.add(tmp);
				tmp = tmp.getExtends();
			}

		}
	}

	/**
	 *
	 * @param eobjectInError
	 *            the object on which we attach the error
	 * @param errorCode
	 *            the errorCode (code are defined in {@link CustomizationMergeErrorCode})
	 * @param message
	 *            the message for the associated error
	 */
	private void createErrorStatus(final EObject eobjectInError, final int errorCode, final String message) {
		final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorCode, message, null);
		List<IStatus> list = statusMap.get(eobjectInError);
		if (list == null) {
			list = new ArrayList<>();
			this.statusMap.put(eobjectInError, list);
		}
		list.add(status);
	}

	/**
	 * This method checks that roots only contains AbsoluteOrder as application rule for the CustomizationReference
	 *
	 * @param roots
	 *            the roots to check
	 */
	private void checkRootsDefinitions(final Collection<EMFFacetTreeViewerConfiguration> roots) {
		for (final EMFFacetTreeViewerConfiguration current : roots) {
			for (CustomizationReference custoRef : current.getCustomizationReferences()) {
				final IApplicationRule rule = custoRef.getApplicationRule();
				if (false == rule instanceof AbsoluteOrder) {
					Collection<String> args = new ArrayList<>();
					args.add(CustomizationConfigurationPackage.eINSTANCE.getEMFFacetTreeViewerConfiguration().getName());
					args.add(CustomizationConfigurationPackage.eINSTANCE.getCustomizationReference().getName());
					args.add(CustomizationConfigurationPackage.eINSTANCE.getAbsoluteOrder().getName());
					createErrorStatus(custoRef,
							CustomizationMergeErrorCode.CHECK_ERROR__NOT_ONLY_USE_ABSOLUTE_ORDER_IN_ROOTS,
							NLS.bind("In the root {0}, the {0} must only use {1} to define customization", args.toArray())); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * This method extract all {@link CustomizationReference} defines with an {@link AbsoluteOrder} from the input
	 *
	 * @return
	 *         all {@link CustomizationReference} defined with {@link AbsoluteOrder}
	 */
	private List<CustomizationReference> getAllAbsoluteOrder(final Collection<EMFFacetTreeViewerConfiguration> input) {
		final List<CustomizationReference> absoluteOrder = new ArrayList<>();
		for (final EMFFacetTreeViewerConfiguration current : input) {
			for (CustomizationReference tmp : current.getCustomizationReferences()) {
				if (tmp.getApplicationRule() instanceof AbsoluteOrder) {
					absoluteOrder.add(tmp);
				}
			}
		}
		return absoluteOrder;
	}

	/**
	 * This method checks that there is not several {@link CustomizationReference} declared with the same order
	 *
	 * @param allAbsoluteOrder
	 *            all {@link CustomizationReference} declared as absolute orderF
	 */
	private void checkAbsoluteOrderUnitity(List<CustomizationReference> allAbsoluteOrder) {
		final Map<Integer, List<CustomizationReference>> order = new HashMap<>();
		for (final CustomizationReference custoRef : allAbsoluteOrder) {
			final AbsoluteOrder absOrder = (AbsoluteOrder) custoRef.getApplicationRule();
			final Integer intOrder = Integer.valueOf(absOrder.getOrder());
			List<CustomizationReference> list = order.get(intOrder);
			if (list == null) {
				list = new ArrayList<>();
				order.put(intOrder, list);
			}
			list.add(custoRef);
		}

		for (final Entry<Integer, List<CustomizationReference>> current : order.entrySet()) {
			if (current.getValue().size() > 1) {
				for (final CustomizationReference custoRef : current.getValue()) {
					createErrorStatus(custoRef, CustomizationMergeErrorCode.CHECK_ERROR__DUPLICATE_ABSOLUTE_ORDER, NLS.bind("Another customization is already defined with the priority {0}", current.getKey())); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 *
	 * @param input
	 *            the input list of {@link EMFFacetTreeViewerConfiguration}}
	 * @return
	 *         an ordained list of collections of {@link EMFFacetTreeViewerConfiguration}. This collection is ordered by level of application :
	 *         <ul>
	 *         <li>list of {@link EMFFacetTreeViewerConfiguration}at index 0 are the roots (they don't extends another {@link EMFFacetTreeViewerConfiguration}</li>
	 *         <li>list of {@link EMFFacetTreeViewerConfiguration}at index 1 are the extension of the root, and so on .</li>
	 *         </ul>
	 *
	 */
	private List<List<EMFFacetTreeViewerConfiguration>> organizeEMFFacetTreeViewerConfigurations(final Collection<EMFFacetTreeViewerConfiguration> input) {
		final Map<EMFFacetTreeViewerConfiguration, List<EMFFacetTreeViewerConfiguration>> extendedByMap = new HashMap<>();
		final List<EMFFacetTreeViewerConfiguration> extendsNothing = new ArrayList<>();
		for (final EMFFacetTreeViewerConfiguration current : input) {
			final EMFFacetTreeViewerConfiguration extends_ = current.getExtends();
			if (extends_ == null) {
				extendsNothing.add(current);
			} else {
				List<EMFFacetTreeViewerConfiguration> list = extendedByMap.get(extends_);
				if (list == null) {
					list = new ArrayList<>();
					extendedByMap.put(extends_, list);
				}
				list.add(current);
			}
		}


		final List<List<EMFFacetTreeViewerConfiguration>> extension = new ArrayList<>();
		extension.add(extendsNothing);

		int index = 0;
		while (extendedByMap.size() > 0) {
			List<EMFFacetTreeViewerConfiguration> nextLevelExtension = new ArrayList<>();
			for (final EMFFacetTreeViewerConfiguration current : extension.get(index)) {
				if (extendedByMap.get(current) != null) {
					nextLevelExtension.addAll(extendedByMap.get(current));
					extendedByMap.remove(current);
				}
			}
			extension.add(nextLevelExtension);
			index++;
		}
		return extension;
	}

	/**
	 * @param intermediateState
	 * @param referencesList
	 * @return
	 */
	private List<CustomizationReference> mergeCustomization(final List<CustomizationReference> intermediateState, final List<CustomizationReference> referencesList) {
		final List<CustomizationReference> workingList = new ArrayList<>(intermediateState);
		// 1. manage redefine
		final List<CustomizationReference> beforeAfter = new ArrayList<>();

		for (final CustomizationReference current : referencesList) {
			final IApplicationRule rule = current.getApplicationRule();
			if (rule instanceof Redefinition) {
				final CustomizationReference redefinedCustomization = ((Redefinition) rule).getRedefinedCustomizationReference();
				int index = workingList.indexOf(redefinedCustomization);
				if (index >= 0) {
					workingList.set(index, current);
				} else {
					createErrorStatus(rule, CustomizationMergeErrorCode.MERGE_ERROR__REDEFINE_NOT_FOUND,
							NLS.bind("The redefined Customization '{0}' has not be found by the merge process.", redefinedCustomization.getReferencedCustomization().getName())); //$NON-NLS-1$
				}

			} else if (rule instanceof RelativeOrder) {
				// we use the iteration to build the list of before after in the same time
				beforeAfter.add(current);
			}
		}

		for (final CustomizationReference current : beforeAfter) {
			final RelativeOrder rule = (RelativeOrder) current.getApplicationRule();
			final CustomizationReference relativeRef = rule.getRelativeCustomizationReference();
			int index = workingList.indexOf(relativeRef);
			final Location location = rule.getLocation();
			if (index >= 0) {
				if (location == Location.BEFORE) {
					workingList.add(index, current);
				} else {
					index++;
					workingList.add(index, current);
				}
			} else {
				int errorCode = location == Location.BEFORE ? CustomizationMergeErrorCode.MERGE_ERROR__BEFORE_NOT_FOUND : CustomizationMergeErrorCode.MERGE_ERROR__AFTER_NOT_FOUND;
				createErrorStatus(rule, errorCode, NLS.bind("The relative customization '{0} has not been found by the merge process", relativeRef.getReferencedCustomization().getName())); //$NON-NLS-1$
			}
		}
		return workingList;
	}


	/**
	 * This method checks that each {@link CustomizationReference} is redefines only one time
	 *
	 * @param customizationReferenceByLevel
	 *            the collection of collection of {@link CustomizationReference}, organized by level
	 */
	private void checkRedefines(List<List<CustomizationReference>> customizationReferenceByLevel) {
		final Map<CustomizationReference, List<CustomizationReference>> redefinitionMap = new HashMap<>();
		for (final List<CustomizationReference> cusRefList : customizationReferenceByLevel) {
			for (final CustomizationReference custoRef : cusRefList) {
				final IApplicationRule rule = custoRef.getApplicationRule();
				if (rule instanceof Redefinition) {
					final Redefinition redefinition = (Redefinition) rule;
					final CustomizationReference referencedCusto = redefinition.getRedefinedCustomizationReference();
					List<CustomizationReference> list = redefinitionMap.get(referencedCusto);
					if (list == null) {
						list = new ArrayList<>();
						redefinitionMap.put(referencedCusto, list);
					}
					list.add(custoRef);
				}
			}
		}

		for (final Entry<CustomizationReference, List<CustomizationReference>> entrySet : redefinitionMap.entrySet()) {
			if (entrySet.getValue().size() != 1) {
				createErrorStatus(entrySet.getKey(), CustomizationMergeErrorCode.CHECK_ERROR__TOO_MANY_REDEFINES_FOR_ME,
						NLS.bind("{0}: I'm redefined {1} timeinstead of 1 time.", entrySet.getKey().getReferencedCustomization().getName(), entrySet.getValue().size())); //$NON-NLS-1$
				for (final CustomizationReference ref : entrySet.getValue()) {
					createErrorStatus(ref, CustomizationMergeErrorCode.CHECK_ERROR__EXTRA_REDEFINES,
							NLS.bind("I'm not alone to redefine the Customization {0}.", //$NON-NLS-1$
									entrySet.getKey().getReferencedCustomization().getName()));
				}
			}
		}
	}

	/**
	 * This method checks that there is only one {@link CustomizationReference} defines with BEFORE for a given {@link CustomizationReference}
	 * and checks that there is only one {@link CustomizationReference} defines with AFTER for a given {@link CustomizationReference}
	 *
	 * @param customizationReferenceByLevel
	 *            the collection of collection of {@link CustomizationReference}, organized by level
	 */
	private void checkBeforeAfter(List<List<CustomizationReference>> customizationReferenceByLevel) {
		final Map<CustomizationReference, List<CustomizationReference>> before = new HashMap<>();
		final Map<CustomizationReference, List<CustomizationReference>> after = new HashMap<>();
		for (final List<CustomizationReference> cusRefList : customizationReferenceByLevel) {
			for (final CustomizationReference custoRef : cusRefList) {
				final IApplicationRule rule = custoRef.getApplicationRule();
				if (rule instanceof RelativeOrder) {
					final RelativeOrder order = (RelativeOrder) rule;
					final CustomizationReference referencedCusto = order.getRelativeCustomizationReference();
					if (order.getLocation() == Location.BEFORE) {
						List<CustomizationReference> list = before.get(referencedCusto);
						if (list == null) {
							list = new ArrayList<>();
							before.put(referencedCusto, list);
						}
						list.add(custoRef);
					} else {// after case
						List<CustomizationReference> list = after.get(referencedCusto);
						if (list == null) {
							list = new ArrayList<>();
							after.put(referencedCusto, list);
						}
						list.add(custoRef);
					}
				}
			}
		}

		for (final Entry<CustomizationReference, List<CustomizationReference>> entrySet : before.entrySet()) {
			if (entrySet.getValue().size() != 1) {
				createErrorStatus(entrySet.getKey(), CustomizationMergeErrorCode.CHECK_ERROR__TOO_MANY_BEFORE_FOR_ME,
						NLS.bind("{0} : the {1} relative location is used more than 1 time to insert a Customization before me.", entrySet.getKey().getReferencedCustomization().getName(), Location.BEFORE.getName())); //$NON-NLS-1$
				for (final CustomizationReference ref : entrySet.getValue()) {
					createErrorStatus(ref, CustomizationMergeErrorCode.CHECK_ERROR__EXTRA_BEFORE, NLS.bind("I'm not alone to insert a Customization before {0}.", entrySet.getKey().getReferencedCustomization().getName())); //$NON-NLS-1$
				}
			}
		}

		for (final Entry<CustomizationReference, List<CustomizationReference>> entrySet : after.entrySet()) {
			if (entrySet.getValue().size() != 1) {
				createErrorStatus(entrySet.getKey(), CustomizationMergeErrorCode.CHECK_ERROR__TOO_MANY_AFTER_FOR_ME,
						NLS.bind("{0} : the {1} relative location is used more than 1 time to insert a Customization after me.", entrySet.getKey().getReferencedCustomization().getName(), Location.AFTER.getName())); //$NON-NLS-1$
				for (final CustomizationReference ref : entrySet.getValue()) {
					createErrorStatus(ref, CustomizationMergeErrorCode.CHECK_ERROR__EXTRA_AFTER, NLS.bind("I'm not alone to insert a Customization after {0}.", entrySet.getKey().getReferencedCustomization().getName())); //$NON-NLS-1$
				}
			}
		}

	}


	/**
	 * @param customizationReferenceByLevel
	 * @return
	 */
	private void excludeAbsoluteOrder(List<List<CustomizationReference>> customizationReferenceByLevel) {
		final Iterator<List<CustomizationReference>> iterOnList = customizationReferenceByLevel.iterator();
		while (iterOnList.hasNext()) {
			final ListIterator<CustomizationReference> custoRefIter = iterOnList.next().listIterator();
			while (custoRefIter.hasNext()) {
				final CustomizationReference custoRef = custoRefIter.next();
				if (custoRef.getApplicationRule() instanceof AbsoluteOrder) {
					custoRefIter.remove();
				}
			}
		}
	}


	/**
	 * @param configurationToMergeByLevel
	 * @return
	 */
	private List<List<CustomizationReference>> organizeCustomizationReferences(final List<List<EMFFacetTreeViewerConfiguration>> configurationToMergeByLevel) {
		List<List<CustomizationReference>> customizationReferenceByLevel = new ArrayList<>();
		for (final List<EMFFacetTreeViewerConfiguration> current : configurationToMergeByLevel) {
			final List<CustomizationReference> refs = new ArrayList<>();
			for (EMFFacetTreeViewerConfiguration conf : current) {
				refs.addAll(conf.getCustomizationReferences());
			}
			customizationReferenceByLevel.add(refs);
		}
		return customizationReferenceByLevel;
	}
}
