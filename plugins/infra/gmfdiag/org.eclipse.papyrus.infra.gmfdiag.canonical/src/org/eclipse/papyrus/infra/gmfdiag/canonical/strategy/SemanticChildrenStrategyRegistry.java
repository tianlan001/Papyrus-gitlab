/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *  Christian W. Damus - bug 477384
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy.PapyrusCanonicalEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.canonical.internal.Activator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

/**
 * This the registry of to get the class that can give info about childreen of an editpart. this is a singleton.
 */
public class SemanticChildrenStrategyRegistry {

	private final List<SemanticChildrenStrategyRegistration> strategies;
	private final Multimap<Class<?>, SemanticChildrenStrategyRegistration> strategiesByEditPart = ArrayListMultimap.create();
	private final List<DefaultSemanticChildrenStrategyRegistration> defaultStrategies;
	private final List<VisualChildrenStrategyRegistration> visualChildrenStrategies;
	private final Multimap<Class<?>, VisualChildrenStrategyRegistration> visualChildrenStrategiesByEditPart = ArrayListMultimap.create();
	@SuppressWarnings("deprecation")
	private final List<CreationTargetStrategyRegistration> creationTargetStrategies;

	private static SemanticChildrenStrategyRegistry INSTANCE = new SemanticChildrenStrategyRegistry();

	public static SemanticChildrenStrategyRegistry getInstance() {
		return INSTANCE;
	}

	private SemanticChildrenStrategyRegistry() {
		super();

		List<SemanticChildrenStrategyRegistration> strategies = Lists.newArrayList();
		List<DefaultSemanticChildrenStrategyRegistration> defaultStrategies = Lists.newArrayListWithExpectedSize(1);
		@SuppressWarnings("deprecation")
		List<CreationTargetStrategyRegistration> creationTargetStrategies = Lists.newArrayList();
		List<VisualChildrenStrategyRegistration> visualChildrenStrategies = Lists.newArrayList();

		// Reading data from plugins
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID + ".strategies"); //$NON-NLS-1$
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement config = configElements[i];

			try {
				switch (config.getName()) {
				case "semanticChildrenStrategy": //$NON-NLS-1$
					strategies.add(new SemanticChildrenStrategyRegistration(config));
					break;
				case "defaultSemanticChildrenStrategy": //$NON-NLS-1$
					defaultStrategies.add(new DefaultSemanticChildrenStrategyRegistration(config));
					break;
				case "creationTargetStrategy": //$NON-NLS-1$
					@SuppressWarnings("deprecation")
					CreationTargetStrategyRegistration creationReg = new CreationTargetStrategyRegistration(config);
					creationTargetStrategies.add(creationReg);
					break;
				case "visualChildrenStrategy": //$NON-NLS-1$
					visualChildrenStrategies.add(new VisualChildrenStrategyRegistration(config));
					break;
				default:
					Activator.log.warn(String.format("Unrecognized configuration element <%s> from plug-in %s", config.getName(), config.getContributor().getName())); //$NON-NLS-1$
					break;
				}
			} catch (Exception e) {
				Activator.log.error("Error loading extension from plug-in " + config.getContributor().getName(), e); //$NON-NLS-1$
			}
		}

		// Sort from highest to lowest priority
		this.strategies = Ordering.natural().reverse().immutableSortedCopy(strategies);
		this.defaultStrategies = Ordering.natural().reverse().immutableSortedCopy(defaultStrategies);
		this.creationTargetStrategies = Ordering.natural().reverse().immutableSortedCopy(creationTargetStrategies);
		this.visualChildrenStrategies = Ordering.natural().reverse().immutableSortedCopy(visualChildrenStrategies);
	}

	/**
	 * Queries the best semantic children strategy matching an {@code editPart}.
	 * 
	 * @param editPart
	 *            an edit part in a diagram
	 * 
	 * @return the {@link ISemanticChildrenStrategy} or {@code null} if no applicable strategy is registered
	 */
	public ISemanticChildrenStrategy getSemanticChildrenStrategy(EditPart editPart) {
		ISemanticChildrenStrategy result = getStrategy(editPart, strategiesByEditPart, strategies);

		if (result == null) {
			// Look for a default.
			for (DefaultSemanticChildrenStrategyRegistration next : defaultStrategies) {
				if (next.isApplicableTo(editPart) && (next.getExtension() != null)) {
					result = next.getExtension();
					break;
				}
			}
		}

		return result;
	}

	private static <T, R extends EditPartBasedRegistration<T, R>> T getStrategy(EditPart editPart, Multimap<Class<?>, R> byEditPart, List<R> registrations) {
		Class<?> key = editPart.getClass();
		R reg = null;

		if (!byEditPart.containsKey(key)) {
			for (R next : registrations) {
				if (next.matchesEditPartType(key)) {
					// Add all of these
					byEditPart.put(key, next);

					// But only take the first matching
					if ((reg == null) && next.isApplicableTo(editPart) && (next.getExtension() != null)) {
						reg = next;
					}
				}
			}
		} else {
			for (R next : byEditPart.get(key)) {
				if (next.isApplicableTo(editPart) && (next.getExtension() != null)) {
					reg = next;
					break;
				}
			}
		}

		return (reg == null) ? null : reg.getExtension();
	}

	private static <T, R extends Registration<T, R>> T getStrategy(EditPart editPart, List<R> registrations) {
		R reg = null;

		for (R next : registrations) {
			if (next.isApplicableTo(editPart)) {
				reg = next;
				break;
			}
		}

		return (reg == null) ? null : reg.getExtension();
	}

	/**
	 * Queries the best creation target strategy matching an {@code editPart}.
	 * 
	 * @param editPart
	 *            an edit part in a diagram
	 * 
	 * @return the {@link ICreationTargetStrategy} or {@code null} if no applicable strategy is registered
	 * 
	 * @deprecated As of Neon, this strategy is no longer needed because the {@link PapyrusCanonicalEditPolicy}
	 *             no longer uses the drag-and-drop infrastructure to create views of existing elements in the diagram.
	 */
	@Deprecated
	public ICreationTargetStrategy getCreationTargetStrategy(EditPart editPart) {
		return getStrategy(editPart, creationTargetStrategies);
	}

	/**
	 * Queries the best visual children strategy matching an {@code editPart}.
	 * 
	 * @param editPart
	 *            an edit part in a diagram
	 * 
	 * @return the {@link IVisualChildrenStrategy} or {@code null} if no applicable strategy is registered
	 */
	public IVisualChildrenStrategy getVisualChildrenStrategy(EditPart editPart) {
		return getStrategy(editPart, visualChildrenStrategiesByEditPart, visualChildrenStrategies);
	}
}
