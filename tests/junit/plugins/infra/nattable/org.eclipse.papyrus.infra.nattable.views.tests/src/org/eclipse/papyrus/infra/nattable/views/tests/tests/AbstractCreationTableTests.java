/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.views.tests.tests;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * Abstract class for table creation tests
 */
public abstract class AbstractCreationTableTests extends AbstractPapyrusTest {

	/**
	 * the id of the table view in the UML Architecture
	 */
	public static final String TABLE_VIEW_REPRESENTATION_KIND_ID = "org.eclipse.papyrus.uml.table.view"; //$NON-NLS-1$

	/**
	 * The Papyrus editor fixture
	 */
	@Rule
	public PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The Papyrus editor
	 */
	protected IMultiDiagramEditor papyrusEditor;

	/**
	 * Set Up the tests
	 */
	@Before
	public void setUp() {
		this.papyrusEditor = this.fixture.getEditor();
	}

	/**
	 *
	 * @param representationKindId
	 *            the wanted representationKindId
	 * @param creationContext
	 *            the selection for which we want the representation (
	 * @return
	 */
	protected final TableViewPrototype getViewPrototype(final String representationKindId, final EObject creationContext) {
		Assert.isNotNull(representationKindId);
		TableViewPrototype viewProto = null;
		final Iterator<ViewPrototype> iter = PolicyChecker.getFor(creationContext).getPrototypesFor(creationContext).iterator();
		while (viewProto == null && iter.hasNext()) {
			final ViewPrototype tmp = iter.next();
			if (representationKindId.equals(tmp.getRepresentationKind().getId()) && tmp instanceof TableViewPrototype) {
				viewProto = (TableViewPrototype) tmp;
			}
		}
		return viewProto;
	}

	/**
	 * This method allows to force the thread execution
	 */
	protected final void flushEvent() {
		this.fixture.flushDisplayEvents();
	}

	/**
	 * method called after the test execution to clean up
	 */
	@After
	public void tearDown() {
		this.fixture = null;
		this.papyrusEditor = null;
	}

}
