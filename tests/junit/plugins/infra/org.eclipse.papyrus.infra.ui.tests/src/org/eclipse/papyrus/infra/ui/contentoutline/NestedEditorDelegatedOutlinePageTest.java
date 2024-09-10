/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.contentoutline;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.eclipse.papyrus.infra.ui.contentoutline.NestedEditorDelegatedOutlinePage;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.ui.part.PageSite;
import org.junit.Test;


/**
 * Test the {@link NestedEditorDelegatedOutlinePage} class
 */
public class NestedEditorDelegatedOutlinePageTest extends AbstractPapyrusTest {

	@Test
	public void testExistingMethod() {
		// usage of java reflexive API to get a field. check this field is still present.
		Field parentSiteField = null;
		try {
			parentSiteField = PageSite.class.getDeclaredField("parentSite");
			parentSiteField.setAccessible(true);
		} catch (SecurityException e) {
			fail(e.getLocalizedMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getLocalizedMessage());
		}

		assertNotNull("Impossible to find parent site field", parentSiteField);
	}

}
