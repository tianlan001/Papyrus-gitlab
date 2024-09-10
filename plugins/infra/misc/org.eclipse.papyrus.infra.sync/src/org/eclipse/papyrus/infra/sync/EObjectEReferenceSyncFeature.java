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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 465416
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

import com.google.common.base.Objects;

/**
 * A synchronization feature for synchronizing {@link EReference}s between {@link EObject}s.
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 */
public abstract class EObjectEReferenceSyncFeature<M extends EObject> extends EStructuralFeatureSyncFeature<M, EObject> {
	/**
	 * Initialized this feature
	 *
	 * @param bucket
	 *            The bucket doing the synchronization
	 */
	public EObjectEReferenceSyncFeature(SyncBucket<M, EObject, Notification> bucket, EReference reference, EReference... more) {
		super(bucket, reference, more);
	}

	@Override
	protected List<? extends EObject> getContents(EObject backend) {
		return new EContentsEList<EObject>(backend, features);
	}

	@Override
	protected EObject getNotifier(EObject backend) {
		return backend;
	}

	@Override
	protected EObject getModelOfNotifier(EObject backendNotifier) {
		return backendNotifier;
	}

	/**
	 * Obtains a command that simply copies the new model element into the same feature of the target.
	 */
	@Override
	protected Command doGetAddCommand(final SyncItem<M, EObject> from, final SyncItem<M, EObject> to, final EObject newSource) {
		EReference reference = getReferencingFeature(from.getBackend(), newSource);
		final EObject copy = EcoreUtil.copy(newSource);

		return new CommandWrapper(reference.isMany()
				? AddCommand.create(getEditingDomain(), to.getBackend(), reference, copy)
				: SetCommand.create(getEditingDomain(), to.getBackend(), reference, copy)) {

			@Override
			public Collection<?> getResult() {
				return Collections.singletonList(copy);
			}
		};
	}

	@Override
	protected Command doGetRemoveCommand(final SyncItem<M, EObject> from, final EObject oldSource, final SyncItem<M, EObject> to, final EObject oldTarget) {
		EReference reference = getReferencingFeature(to.getBackend(), oldTarget);

		return reference.isContainment()
				? createDeleteCommand(oldTarget)
				: reference.isMany()
						? RemoveCommand.create(getEditingDomain(), to.getBackend(), reference, oldTarget)
						: SetCommand.create(getEditingDomain(), to.getBackend(), reference, SetCommand.UNSET_VALUE);
	}

	protected Command createDeleteCommand(EObject object) {
		return DeleteCommand.create(getEditingDomain(), object);
	}

	protected EReference getReferencingFeature(EObject owner, EObject referenced) {
		EReference result = null;

		for (int i = 0; (result == null) && (i < features.length); i++) {
			if (contains(owner, features[i], referenced)) {
				result = (EReference) features[i]; // We only allow references in the constructor
			}
		}

		return result;
	}

	private boolean contains(EObject owner, EStructuralFeature feature, Object value) {
		return FeatureMapUtil.isMany(owner, feature)
				? ((Collection<?>) owner.eGet(feature)).contains(value)
				: Objects.equal(owner.eGet(feature), value);
	}
}
