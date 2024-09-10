/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync.internal;

import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.sync.Activator;
import org.eclipse.papyrus.infra.sync.service.AbstractSyncTrigger;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.ISyncTrigger;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Static registry of synchronization trigger plug-in extensions.
 */
public class SyncTriggerRegistry {
	private static final String EXT_PT = Activator.PLUGIN_ID + ".triggers"; //$NON-NLS-1$

	private static final String E_TRIGGER = "syncTrigger"; //$NON-NLS-1$

	private static final String E_ENABLEMENT = "enablement"; //$NON-NLS-1$

	private static final String A_CLASS = "class"; //$NON-NLS-1$

	private static SyncTriggerRegistry INSTANCE = new SyncTriggerRegistry();

	private final List<ISyncTrigger> syncTriggers = Lists.newArrayList();

	private SyncTriggerRegistry() {
		super();

		for (IConfigurationElement next : Platform.getExtensionRegistry().getConfigurationElementsFor(EXT_PT)) {
			if (E_TRIGGER.equals(next.getName())) {
				syncTriggers.add(new SyncTriggerDescriptor(next));
			}
		}
	}

	public static SyncTriggerRegistry getInstance() {
		return INSTANCE;
	}

	public Iterable<ISyncTrigger> getSyncTriggers(final Object object) {
		return Iterables.filter(syncTriggers, new Predicate<ISyncTrigger>() {
			@Override
			public boolean apply(ISyncTrigger input) {
				return input.isTriggeredOn(object);
			}
		});
	}

	//
	// Nested types
	//

	private static class SyncNoop implements ISyncAction {
		static final SyncNoop INSTANCE = new SyncNoop();

		@Override
		public IStatus perform(ISyncService syncService, Object object) {
			return Status.OK_STATUS;
		}
	}

	private static class NullSyncTrigger extends AbstractSyncTrigger {
		static final NullSyncTrigger INSTANCE = new NullSyncTrigger();

		@Override
		public boolean isTriggeredOn(Object object) {
			return false;
		}

		@Override
		public ISyncAction trigger(ISyncService syncService, Object object) {
			return SyncNoop.INSTANCE;
		}
	}

	static class SyncTriggerDescriptor implements ISyncTrigger {
		private final IConfigurationElement config;

		private Expression enablement;

		private ISyncTrigger resolved;

		SyncTriggerDescriptor(IConfigurationElement config) {
			super();

			this.config = config;
			this.enablement = getEnablement(config);
		}

		private Expression getEnablement(IConfigurationElement config) {
			Expression result = null;

			IConfigurationElement[] enablements = config.getChildren(E_ENABLEMENT);
			if (enablements.length > 0) {
				try {
					result = ExpressionConverter.getDefault().perform(enablements[0]);
				} catch (CoreException e) {
					result = Expression.FALSE;
					Activator.log.log(e.getStatus());
				}
			}

			return result;
		}

		@Override
		public boolean isTriggeredOn(Object object) {
			try {
				return (enablement == null)
						? resolve().isTriggeredOn(object)
						: EvaluationResult.TRUE.equals(enablement.evaluate(new EvaluationContext(null, object)));
			} catch (CoreException e) {
				enablement = Expression.FALSE;
				Activator.log.error("Sync trigger enablement expression failed. The trigger is disabled: " + config.getContributor().getName(), e); //$NON-NLS-1$
				return false;
			}
		}

		ISyncTrigger resolve() {
			if (resolved == null) {
				try {
					resolved = (ISyncTrigger) config.createExecutableExtension(A_CLASS);
				} catch (CoreException e) {
					resolved = NullSyncTrigger.INSTANCE;
					Activator.log.error("Failed to create sync trigger. The trigger is disabled: " + config.getContributor().getName(), e); //$NON-NLS-1$
				} catch (ClassCastException e) {
					resolved = NullSyncTrigger.INSTANCE;
					Activator.log.error("Sync trigger does not implement ISyncTrigger interface. The trigger is disabled: " + config.getContributor().getName(), e); //$NON-NLS-1$
				}
			}

			return resolved;
		}

		@Override
		public ISyncAction trigger(ISyncService syncService, Object object) {
			return resolve().trigger(syncService, object);
		}
	}
}
