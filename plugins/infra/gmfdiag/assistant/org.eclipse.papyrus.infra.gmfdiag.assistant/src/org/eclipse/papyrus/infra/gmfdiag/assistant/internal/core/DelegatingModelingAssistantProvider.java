/*****************************************************************************
 * Copyright (c) 2014, 2015, 2023 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) <vincent.lorenzo@cea.fr> - bug 582023
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantOperation;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;

import com.google.common.collect.Lists;

/**
 * A Modeling Assistant provider that delegates to registered modeled providers.
 */
public class DelegatingModelingAssistantProvider implements IModelingAssistantProvider, IProviderChangeListener {

	private final CopyOnWriteArrayList<IProviderChangeListener> listeners = new CopyOnWriteArrayList<>();

	private final ThreadLocal<List<IModelingAssistantProvider>> currentProviders = new ThreadLocal<>() {
		@Override
		protected List<IModelingAssistantProvider> initialValue() {
			return Lists.newArrayList();
		}
	};

	public DelegatingModelingAssistantProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		// we only expect IModelingAssistantOperation
		if ((false == operation instanceof IModelingAssistantOperation) && ((IModelingAssistantOperation) operation).getContext() != null) {
			return false;
		}
		final IModelingAssistantOperation op = (IModelingAssistantOperation) operation;
		final IAdaptable adaptable = op.getContext();
		final EditPart editPart = adaptable.getAdapter(EditPart.class);
		final Diagram diag = DiagramUtils.getDiagramFrom(editPart);

		// nothing to do on Papyrus-Sirius diagram
		if (diag != null && !DiagramUtils.isPapyrusGMFDiagram(diag)) {
			return false;
		}

		// Query my providers
		List<IModelingAssistantProvider> current = currentProviders.get();
		current.clear();

		for (IModelingAssistantProvider next : ModelingAssistantModelRegistry.getInstance().getModelingAssistantProviders()) {
			if (next.provides(operation)) {
				current.add(next);
			}
		}

		return !current.isEmpty();
	}

	@Override
	public void providerChanged(ProviderChangeEvent event) {
		// Forward to my own listeners
		if (!listeners.isEmpty()) {
			ProviderChangeEvent forward = new ProviderChangeEvent(this);
			for (IProviderChangeListener next : listeners) {
				try {
					next.providerChanged(forward);
				} catch (Exception e) {
					AssistantPlugin.log.error("Uncaught exception in provider change listener", e); //$NON-NLS-1$
				}
			}
		}
	}

	@Override
	public void addProviderChangeListener(IProviderChangeListener listener) {
		listeners.addIfAbsent(listener);
	}

	@Override
	public void removeProviderChangeListener(IProviderChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTypes(String hint, IAdaptable data) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getTypes(hint, data));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(data, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRelTypesOnSource(IAdaptable source) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getRelTypesOnSource(source));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(source, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRelTypesOnTarget(IAdaptable target) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getRelTypesOnTarget(target));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(target, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getRelTypesOnSourceAndTarget(source, target));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(source, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRelTypesForSREOnSource(IAdaptable source) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getRelTypesForSREOnSource(source));
		}

		return filterViewpointExclusions(source, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRelTypesForSREOnTarget(IAdaptable target) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getRelTypesForSREOnTarget(target));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(target, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTypesForSource(IAdaptable target, IElementType relationshipType) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getTypesForSource(target, relationshipType));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(target, result);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getTypesForTarget(source, relationshipType));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(source, result);
	}

	@Override
	public EObject selectExistingElementForSource(IAdaptable target, IElementType relationshipType) {
		EObject result = null;

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result = next.selectExistingElementForSource(target, relationshipType);
			if (result != null) {
				break;
			}
		}
		currentProviders.get().clear();

		return result;
	}

	@Override
	public EObject selectExistingElementForTarget(IAdaptable source, IElementType relationshipType) {
		EObject result = null;

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result = next.selectExistingElementForTarget(source, relationshipType);
			if (result != null) {
				break;
			}
		}
		currentProviders.get().clear();

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTypesForPopupBar(IAdaptable host) {
		List result = Lists.newArrayList();

		for (IModelingAssistantProvider next : currentProviders.get()) {
			result.addAll(next.getTypesForPopupBar(host));
		}
		currentProviders.get().clear();

		return filterViewpointExclusions(host, result);
	}

	protected List<? extends IElementType> filterViewpointExclusions(IAdaptable context, List<? extends IElementType> elementTypes) {
		View view = (context == null) ? null : context.getAdapter(View.class);
		Diagram diagram = (view == null) ? null : DiagramUtils.getContainingDiagram(view);

		if (diagram != null) {
			for (Iterator<? extends IElementType> iter = elementTypes.iterator(); iter.hasNext();) {
				if (!PolicyChecker.getFor(diagram).isInModelingAssistants(diagram, iter.next())) {
					iter.remove();
				}
			}
		} // No diagram context? Nothing to filter

		return elementTypes;
	}
}
