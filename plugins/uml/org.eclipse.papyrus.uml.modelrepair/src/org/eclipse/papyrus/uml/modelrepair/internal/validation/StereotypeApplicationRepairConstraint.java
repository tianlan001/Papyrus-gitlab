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

package org.eclipse.papyrus.uml.modelrepair.internal.validation;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.uml.modelrepair.internal.stereotypes.IStereotypeOrphanGroup;
import org.eclipse.papyrus.uml.modelrepair.internal.stereotypes.StereotypeApplicationRepairSnippet;
import org.eclipse.papyrus.uml.modelrepair.internal.stereotypes.ZombieStereotypesDescriptor;
import org.eclipse.papyrus.uml.modelrepair.ui.IZombieStereotypePresenter;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * A validation constraint that runs stereotype repair analysis on the stereotypes loaded in the model if and only if validation
 * started at the root package.
 */
public class StereotypeApplicationRepairConstraint extends AbstractModelConstraint {

	/**
	 * Initializes me.
	 */
	public StereotypeApplicationRepairConstraint() {
		super();
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		ModelSet modelSet = getModelSetContext(ctx);
		if (modelSet != null) {
			return validate(ctx, modelSet);
		}
		return null;
	}

	protected IStatus validate(IValidationContext vctx, ModelSet modelSet) {
		IStatus result;

		ValidationPresenter presenter = new ValidationPresenter(vctx);

		StereotypeApplicationRepairSnippet repair = new StereotypeApplicationRepairSnippet(Functions.constant(presenter));
		repair.start(modelSet);
		try {
			result = presenter.aggregateResults();
		} finally {
			repair.dispose(modelSet);
		}

		return result;
	}

	protected ModelSet getModelSetContext(IValidationContext context) {
		ModelSet result = null;

		// Have we done this before?
		if (context.getCurrentConstraintData() == null) {
			EObject target = context.getTarget();
			Resource resource = ((InternalEObject) target).eDirectResource();

			// Is the object a resource root?
			if (resource != null) {
				// Is it the root of the entire model?
				if ((target instanceof Package) && (target.eContainer() == null) && (resource.getResourceSet() instanceof ModelSet)) {
					result = (ModelSet) resource.getResourceSet();

					// And don't do this again because we're scanning the whole resource set
					context.putCurrentConstraintData(result);
				}
			}
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * A zombie stereotype presenter that simply gathers the problems found by stereotype repair analysis.
	 * It is synchronous in its processing and does not effect any repairs at all.
	 */
	private static final class ValidationPresenter implements IZombieStereotypePresenter {
		private final List<IConstraintStatus> problems = Lists.newArrayList();
		private final IValidationContext ctx;
		private final int severity;
		private final int statusCode;

		ValidationPresenter(IValidationContext ctx) {
			super();

			this.ctx = ctx;

			IConstraintDescriptor desc = ConstraintRegistry.getInstance().getDescriptor(ctx.getCurrentConstraintId());
			this.severity = desc.getSeverity().toIStatusSeverity();
			this.statusCode = desc.getStatusCode();
		}

		public void dispose() {
			// Nothing to dispose
		}

		public Function<? super EPackage, Profile> getDynamicProfileSupplier() {
			// We don't actually repair anything but only gather problems
			return Functions.constant(null);
		}

		public void addZombies(ZombieStereotypesDescriptor zombies) {
			for (IAdaptable schema : zombies.getZombieSchemas()) {
				Optional<IStereotypeOrphanGroup> orphans = AdapterUtils.adapt(schema, IStereotypeOrphanGroup.class);
				if (orphans.isPresent()) {
					reportOrphans(zombies.getZombies(schema));
				} else {
					reportBroken(zombies.getZombies(schema));
				}
			}
		}

		protected void reportOrphans(Iterable<? extends EObject> stereotypes) {
			for (EObject next : stereotypes) {
				Stereotype stereotype = UMLUtil.getStereotype(next);
				String name = (stereotype == null) ? next.eClass().getName() : stereotype.getName();
				problems.add(ConstraintStatus.createStatus(ctx, next, null, severity, statusCode, "Stereotype \"{0}\" application is detached from its base UML element.", name));
			}
		}

		protected void reportBroken(Iterable<? extends EObject> stereotypes) {
			for (EObject next : stereotypes) {
				String name = next.eClass().getName();
				problems.add(ConstraintStatus.createStatus(ctx, next, null, severity, statusCode, "Stereotype \"{0}\" application is inconsistent with applied profile(s).", name));
			}
		}

		public void asyncAddZombies(Runnable runnable) {
			// We don't need to support asynchonous execution
			runnable.run();
		}

		public boolean isPending() {
			// Our execution is purely synchronous
			return false;
		}

		public void awaitPending(boolean expected) throws InterruptedException {
			// Our execution is purely synchronous
		}

		public void onPendingDone(Runnable runnable) {
			// Our execution is purely synchronous
			runnable.run();
		}

		IStatus aggregateResults() {
			return problems.isEmpty() ? null : ConstraintStatus.createMultiStatus(ctx, problems);
		}
	}
}
