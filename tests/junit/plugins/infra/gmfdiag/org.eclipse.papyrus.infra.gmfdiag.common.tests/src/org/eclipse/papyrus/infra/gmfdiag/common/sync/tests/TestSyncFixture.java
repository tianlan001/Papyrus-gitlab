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

package org.eclipse.papyrus.infra.gmfdiag.common.sync.tests;

import static org.junit.Assert.fail;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ISemanticChildrenStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.ContainerChildrenSyncFeature;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.DiagramEdgesSyncFeature;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.EditPartMasterSlaveSyncBucket;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.EditPartSyncRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.NodePositionSyncFeature;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.NodeSizeSyncFeature;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.SyncStyles;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.ISyncTrigger;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Package;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A container of synchronization configurations for the test suite.
 */
public class TestSyncFixture extends TestWatcher {
	private final PapyrusEditorFixture editor;

	private static PapyrusEditorFixture current;

	private TestSyncFixture(PapyrusEditorFixture editor) {
		super();

		this.editor = editor;
	}

	static TestRule compose(PapyrusEditorFixture editor) {
		return RuleChain.outerRule(new TestSyncFixture(editor)).around(editor);
	}

	@Override
	protected void starting(Description description) {
		current = editor;
	}

	@Override
	protected void finished(Description description) {
		current = null;
	}

	public static class TestClassDiagramSyncTrigger implements ISyncTrigger {
		public TestClassDiagramSyncTrigger() {
			super();
		}

		public boolean isTriggeredOn(Object object) {
			boolean result = false;

			if ((current != null) && (object instanceof DiagramEditPart)) {
				DiagramEditPart diagramEP = (DiagramEditPart) object;
				Diagram diagram = (Diagram) diagramEP.getModel();
				if (diagram.getElement() instanceof org.eclipse.uml2.uml.Package) {
					Resource resource = diagram.eResource();
					result = resource.getResourceSet() == current.getResourceSet();
				}
			}

			return result;
		}

		public ISyncAction trigger(ISyncService syncService, Object object) {
			return new ISyncAction() {

				public IStatus perform(ISyncService syncService, Object object) {
					DiagramEditPart diagram = (DiagramEditPart) object;
					org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) EMFHelper.getEObject(diagram);

					TestDiagramSyncRegistry registry = syncService.getSyncRegistry(TestDiagramSyncRegistry.class);

					switch (SyncStyles.getSyncKind((View) diagram.getModel())) {
					case MASTER:
						SyncBucket<org.eclipse.uml2.uml.Package, EditPart, Notification> bucket = registry.getBucket(package_);
						if (bucket == null) {
							bucket = new TestPackageSyncBucket(package_, diagram);
							registry.register(bucket);
						}
						break;
					case SLAVE:
						registry.synchronize(diagram);
						break;
					case NONE:
						// Pass
						break;
					default:
						fail("Unsupported synchronization kind in test model");
					}
					return null;
				}
			};
		}
	}

	public static class TestDiagramSyncRegistry extends EditPartSyncRegistry<org.eclipse.uml2.uml.Package, EditPart> {
		public TestDiagramSyncRegistry() {
			super();
		}
	}

	static class TestPackageSyncBucket extends EditPartMasterSlaveSyncBucket<org.eclipse.uml2.uml.Package, EditPart> {
		public TestPackageSyncBucket(org.eclipse.uml2.uml.Package model, DiagramEditPart master) {
			super(model, master);

			add(new ClassNodesSyncFeature(this));
			add(new AssociationEdgesSyncFeature(this));
		}
	}

	static class ClassNodesSyncFeature extends ContainerChildrenSyncFeature<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Class, EditPart> {
		public ClassNodesSyncFeature(SyncBucket<Package, EditPart, Notification> bucket) {
			super(bucket);
		}

		@Override
		protected Class<? extends SyncRegistry<org.eclipse.uml2.uml.Class, EditPart, Notification>> getNestedSyncRegistryType() {
			return ClassNodeSyncRegistry.class;
		}

		@Override
		protected Command onTargetAdded(SyncItem<Package, EditPart> from, EObject source, SyncItem<Package, EditPart> to, final EditPart target) {
			org.eclipse.uml2.uml.Class class_ = (org.eclipse.uml2.uml.Class) source;

			final SyncRegistry<org.eclipse.uml2.uml.Class, EditPart, Notification> registry = getNestedSyncRegistry();
			SyncBucket<org.eclipse.uml2.uml.Class, EditPart, Notification> bucket = registry.getBucket(class_);
			if (bucket == null) {
				EditPart master = findChild(from.getBackend(), source);
				bucket = new ClassNodeSyncBucket(class_, master);
				registry.register(bucket);
			}

			return synchronizingWrapper(registry, target, null);
		}

		@Override
		protected Command onTargetRemoved(SyncItem<Package, EditPart> to, EditPart target) {
			SyncRegistry<org.eclipse.uml2.uml.Class, EditPart, Notification> registry = getNestedSyncRegistry();

			// De-synchronize this edit-part
			registry.desynchronize(target);

			return null;
		}
	}

	public static class ClassNodeSyncRegistry extends EditPartSyncRegistry<org.eclipse.uml2.uml.Class, EditPart> {
		public ClassNodeSyncRegistry() {
			super();
		}
	}

	static class ClassNodeSyncBucket extends EditPartMasterSlaveSyncBucket<org.eclipse.uml2.uml.Class, EditPart> {
		public ClassNodeSyncBucket(org.eclipse.uml2.uml.Class model, EditPart master) {
			super(model, master);

			add(new NodePositionSyncFeature<org.eclipse.uml2.uml.Class, EditPart>(this));
			add(new NodeSizeSyncFeature<org.eclipse.uml2.uml.Class, EditPart>(this));
		}
	}

	static class AssociationEdgesSyncFeature extends DiagramEdgesSyncFeature<org.eclipse.uml2.uml.Package, Association, EditPart> {
		private final ISemanticChildrenStrategy semanticChildren = new DefaultUMLSemanticChildrenStrategy();

		public AssociationEdgesSyncFeature(SyncBucket<Package, EditPart, Notification> bucket) {
			super(bucket);
		}

		@Override
		protected Class<? extends SyncRegistry<Association, EditPart, Notification>> getNestedSyncRegistryType() {
			return AssociationEdgeSyncRegistry.class;
		}

		@Override
		protected EObject getSourceElement(EObject connectionElement) {
			// I am only invoked on associations, so this cast is OK
			return (EObject) semanticChildren.getSource(connectionElement);
		}

		@Override
		protected EObject getTargetElement(EObject connectionElement) {
			// I am only invoked on associations, so this cast is OK
			return (EObject) semanticChildren.getTarget(connectionElement);
		}
	}

	public static class AssociationEdgeSyncRegistry extends EditPartSyncRegistry<Association, EditPart> {
		public AssociationEdgeSyncRegistry() {
			super();
		}
	}

}
