/*****************************************************************************
 * Copyright (c) 2016, 2020 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Bug 568500
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.resource;

import static org.eclipse.papyrus.infra.emf.internal.resource.AbstractCrossReferenceIndex.SHARD_ANNOTATION_SOURCE;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * A convenience wrapper for {@link EObject}s and/or {@link Resource}s that
 * are dependent "shard" units of a Papyrus model. A shard helper must
 * always be {@linkplain #close() closed} after it is no longer needed,
 * because it attaches adapters to the model.
 *
 * @since 2.1
 */
public class ShardResourceHelper implements AutoCloseable {

	private final Resource resource;
	private final EObject object;

	private boolean closed;
	private boolean initialized;

	private EAnnotation annotation;
	private Adapter annotationAdapter;

	/**
	 * Initializes me on a shard {@code resource} that is expected to contain
	 * only one root element (it doesn't store multiple distinct sub-trees
	 * of the model).
	 *
	 * @param resource
	 *            a "resource" resource
	 *
	 * @see #ShardResourceHelper(EObject)
	 */
	public ShardResourceHelper(Resource resource) {
		this(resource, null);
	}

	/**
	 * Initializes me on an {@code element} in a shard resource that uniquely
	 * identifies a sub-tree of potentially more than one stored in the resource.
	 * If there is any possibility that a resource stores multiple sub-trees,
	 * prefer this constructor over {@linkplain #ShardResourceHelper(Resource) the other}.
	 *
	 * @param element
	 *            an element in a "resource" resource
	 */
	public ShardResourceHelper(EObject element) {
		this(element.eResource(), element);
	}

	private ShardResourceHelper(Resource resource, EObject object) {
		super();

		this.resource = resource;
		this.object = object;
	}

	/**
	 * Is my resource a shard?
	 *
	 * @return whether my resource is a shard of its parent
	 */
	public boolean isShard() {
		return getAnnotation() != null;
	}

	/**
	 * Changes my resource from a shard to an independent controlled unit, or vice-versa.
	 * In the context of an editor and/or editing-domain, it is usually more appropriate
	 * to use the {@link #getSetShardCommand(boolean)} API for manipulation by command.
	 *
	 * @param isShard
	 *            whether my resource should be a shard. If it already matches
	 *            this state, then do nothing
	 *
	 * @see #getSetShardCommand(boolean)
	 */
	public void setShard(boolean isShard) {
		checkClosed();

		if (isShard != isShard()) {
			if (getAnnotation() != null) {
				// We are un-sharding
				EcoreUtil.remove(getAnnotation());
			} else {
				// We are sharding
				EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				annotation.setSource(SHARD_ANNOTATION_SOURCE);
				Notifier annotationOwner;

				EObject shardElement = getShardElement();
				if (shardElement instanceof EModelElement) {
					// Add it to the shard element
					((EModelElement) shardElement).getEAnnotations().add(annotation);
					annotationOwner = shardElement;
				} else if (shardElement != null) {
					// Add it after the shard element
					int index = resource.getContents().indexOf(shardElement) + 1;
					resource.getContents().add(index, annotation);
					annotationOwner = resource;
				} else {
					// Try to add it after the principal model object
					resource.getContents().add(Math.min(1, resource.getContents().size()), annotation);
					annotationOwner = resource;
				}

				// In any case, the parent is the resource storing the element's container
				if ((shardElement != null) && (shardElement.eContainer() != null)) {
					annotation.getReferences().add(shardElement.eContainer());
				}

				setAnnotation(annotation);
				attachAnnotationAdapter(annotationOwner);
			}
		}
	}

	/**
	 * Finds the element that is the root of the particular sub-tree stored in
	 * this resource, from the context provided by the client.
	 *
	 * @return the shard root element as best determined from the context, or
	 *         {@code null} in the worst case that the resource is empty
	 */
	private EObject getShardElement() {
		checkClosed();

		EObject result = null;

		if (object != null) {
			// Find the object in its content tree that is a root of our resource
			for (result = object; result != null; result = result.eContainer()) {
				InternalEObject internal = (InternalEObject) result;
				if (internal.eDirectResource() == resource) {
					// Found it
					break;
				}
			}
		}

		if ((result == null) && !resource.getContents().isEmpty()) {
			// Just take the first element as the shard element
			result = resource.getContents().get(0);
		}

		return result;
	}

	/**
	 * Obtains a command to change my resource from a shard to an independent
	 * controlled unit, or vice-versa.
	 *
	 * @param isShard
	 *            whether my resource should be a shard. If it already matches
	 *            this state, then the resulting command will have no effect
	 *
	 * @return the set-shard command
	 *
	 * @see #setShard(boolean)
	 */
	public Command getSetShardCommand(boolean isShard) {
		Command result;

		if (isShard() == isShard) {
			result = IdentityCommand.INSTANCE;
		} else if (getAnnotation() != null) {
			// Delete the annotation
			EAnnotation annotation = getAnnotation();
			if (annotation.getEModelElement() != null) {
				result = RemoveCommand.create(EMFHelper.resolveEditingDomain(annotation),
						annotation.getEModelElement(),
						EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS,
						annotation);
			} else {
				result = new RemoveCommand(EMFHelper.resolveEditingDomain(resource),
						resource.getContents(),
						annotation);
			}

			result = new CommandWrapper(
					"Toggle Submodel",
					"Toggle the ability to open the resource as an independent sub-model unit",
					result);
		} else {
			// Create the annotation
			EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
			annotation.setSource(SHARD_ANNOTATION_SOURCE);

			EditingDomain domain;
			EObject shardElement = getShardElement();
			Notifier annotationOwner;

			if (shardElement instanceof EModelElement) {
				// Add it to the shard element
				domain = EMFHelper.resolveEditingDomain(shardElement);
				result = AddCommand.create(domain, shardElement,
						EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS,
						annotation);
				annotationOwner = shardElement;
			} else if (shardElement != null) {
				// Add it after the shard element
				int index = resource.getContents().indexOf(shardElement) + 1;
				domain = EMFHelper.resolveEditingDomain(shardElement);
				result = new AddCommand(domain, resource.getContents(), annotation, index);
				annotationOwner = resource;
			} else {
				// Try to add it after the principal model object
				domain = EMFHelper.resolveEditingDomain(resource);
				int index = Math.min(1, resource.getContents().size());
				result = new AddCommand(domain, resource.getContents(), annotation, index);
				annotationOwner = resource;
			}

			// In any case, the parent is the resource storing the element's container
			if ((shardElement != null) && (shardElement.eContainer() != null)) {
				result = result.chain(AddCommand.create(domain, annotation,
						EcorePackage.Literals.EANNOTATION__REFERENCES,
						shardElement.eContainer()));
			}

			// Ensure attachment of the adapter on first execution and record the
			// annotation, if not already closed
			result = new CommandWrapper(
					"Toggle Submodel",
					"Toggle the ability to open the resource as an independent sub-model unit",
					result) {

				@Override
				public void execute() {
					super.execute();

					if (!ShardResourceHelper.this.isClosed()) {
						setAnnotation(annotation);
						attachAnnotationAdapter(annotationOwner);
					}
				}
			};
		}

		return result;
	}

	/**
	 * Closes me, ensuring at least that any adapter I have attached to the model
	 * that retains me is detached. Once I have been closed, I cannot be used
	 * any longer.
	 */
	@Override
	public void close() {
		closed = true;

		doClose();
	}

	protected void doClose() {
		clearAnnotation();
		detachAnnotationAdapter();
	}

	/**
	 * Queries whether I have been {@linkplain #close() closed}.
	 *
	 * @return whether I have been closed
	 */
	public final boolean isClosed() {
		return closed;
	}

	protected final void checkClosed() {
		if (isClosed()) {
			throw new IllegalStateException("closed"); //$NON-NLS-1$
		}
	}

	private EAnnotation getAnnotation() {
		checkClosed();

		if (!initialized) {
			setAnnotation(findAnnotation());
			initialized = true;
		}

		return annotation;
	}

	private EAnnotation findAnnotation() {
		EAnnotation result = null;

		if (null != resource && !resource.getContents().isEmpty()) {
			EObject shardElement = getShardElement();
			Notifier annotationOwner;

			if (shardElement instanceof EModelElement) {
				result = ((EModelElement) shardElement).getEAnnotation(SHARD_ANNOTATION_SOURCE);
				annotationOwner = shardElement;
			} else {
				// Maybe it's just in the resource?
				List<EObject> contents = resource.getContents();
				annotationOwner = resource;

				if (shardElement != null) {
					int index = contents.indexOf(shardElement) + 1;
					if (index < contents.size()) {
						EAnnotation maybe = TypeUtils.as(contents.get(index), EAnnotation.class);
						if ((maybe != null) && SHARD_ANNOTATION_SOURCE.equals(maybe.getSource())) {
							// That's it
							result = maybe;
						}
					}
				}

				if ((result == null) && (object == null)) {
					// If we don't have a specific sub-tree in mind, look for any
					// shard annotation
					result = contents.stream()
							.filter(EAnnotation.class::isInstance).map(EAnnotation.class::cast)
							.filter(a -> SHARD_ANNOTATION_SOURCE.equals(a.getSource()))
							.findFirst().orElse(null);
				}
			}

			attachAnnotationAdapter(annotationOwner);
		}

		return result;
	}

	private void clearAnnotation() {
		initialized = false;
		setAnnotation(null);
	}

	private void setAnnotation(EAnnotation annotation) {
		this.annotation = annotation;
	}

	private void attachAnnotationAdapter(Notifier annotationOwner) {
		// If we still have the annotation, then it's still attached
		if (annotationAdapter == null) {
			annotationAdapter = new AdapterImpl() {
				@Override
				public void notifyChanged(Notification msg) {
					if (msg.getEventType() == Notification.REMOVING_ADAPTER) {
						// My target was unloaded
						clearAnnotation();
					} else if ((msg.getFeature() == EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS)
							|| ((msg.getNotifier() == resource) && (msg.getFeatureID(Resource.class) == Resource.RESOURCE__CONTENTS))) {

						// Annotation of the model element or resource changed
						boolean clear = false;

						switch (msg.getEventType()) {
						case Notification.SET:
						case Notification.UNSET:
						case Notification.REMOVE:
							clear = (msg.getOldValue() == getAnnotation());
							break;
						case Notification.ADD:
						case Notification.ADD_MANY:
							// If we don't have an annotation, we'll try to find it
							clear = getAnnotation() == null;
							break;
						case Notification.REMOVE_MANY:
							clear = ((Collection<?>) msg.getOldValue()).contains(getAnnotation());
							break;
						}

						if (clear) {
							// In case the annotation moved or was replaced,
							// we'll compute it again on-the-fly
							clearAnnotation();
						}
					}
				}
			};

			annotationOwner.eAdapters().add(annotationAdapter);
		}
	}

	private void detachAnnotationAdapter() {
		if (annotationAdapter != null) {
			Adapter adapter = annotationAdapter;
			annotationAdapter = null;
			if (adapter.getTarget() != null) {
				adapter.getTarget().eAdapters().remove(adapter);
			}
		}
	}
}
