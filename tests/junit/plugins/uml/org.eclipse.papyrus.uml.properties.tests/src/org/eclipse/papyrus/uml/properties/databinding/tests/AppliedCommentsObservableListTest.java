/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.modelelement.CommentModelElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

/**
 * This allows to test the applied comments observable list.
 */
@SuppressWarnings({ "serial", "nls" })
@PluginResource("model/AppliedCommentsObservableList.di")
public class AppliedCommentsObservableListTest extends AbstractUMLObservableListTest {

	/**
	 * The existing comment to add to the existing class.
	 */
	protected Comment existingComment = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		final Class existingClass = (Class) model.getOwnedMember("Class1");
		Assert.assertNotNull("The class 'Class1' must be available in the model", existingClass);

		return existingClass;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new CommentModelElement((Element) source, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return "appliedComments";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		if (null != existingComment) {
			return new ArrayList<Comment>() {
				{
					add(existingComment);
				}
			};
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		if (null == existingComment) {
			existingComment = model.getOwnedComments().get(0);
			Assert.assertNotNull("A comment should exist in the model", existingComment);
		}
		return existingComment;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableListTest#testAddAll()
	 */
	@Override
	protected boolean testAddAll() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableListTest#testRetainAll()
	 */
	@Override
	protected boolean testRetainAll() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableListTest#testClear()
	 */
	@Override
	protected boolean testClear() {
		return false;
	}

}
