/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.creation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test non-directed association creation.
 */
@SuppressWarnings("nls")
public class CreateAssociationTest extends AbstractCreateAssociationTest {

	/**
	 * The element id corresponding to the non-directed association.
	 */
	private static final String ELEMENT_TYPE_ID = "org.eclipse.papyrus.uml.AssociationNonDirected";


	/**
	 * Test if the creation of association following SysML requirements.
	 * 
	 * @throws InterruptedException
	 *             The InterruptedException caught exception.
	 * @throws RollbackException
	 *             The RollbackException caught exception.
	 */
	@Test
	public void testCreation() throws InterruptedException, RollbackException {

		final IElementType elementType = ElementTypeRegistry.getInstance().getType(ELEMENT_TYPE_ID);
		final IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(rootModel);

		// Create a correct association creation command
		final CreateRelationshipRequest validRequest = new CreateRelationshipRequest(testClass, testClass2, elementType);
		final ICommand editCommand = commandService.getEditCommand(validRequest);

		// Check if this is possible to execute the command and execute it
		Assert.assertTrue("The command for creating an assocatiation should be executable", editCommand.canExecute());
		transactionalEditingDomain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(editCommand));

		// Check the result
		final Object result = editCommand.getCommandResult().getReturnValue();
		Assert.assertTrue("The result should be a PackageImport", result instanceof Association);

		final Association association = (Association) result;
		final EList<Property> memberEnds = association.getMemberEnds();

		Assert.assertEquals("The Association must contains 2 owned ends", memberEnds.size(), 2);
		Assert.assertEquals("The first Association member end must not have aggregation kind", AggregationKind.NONE_LITERAL, memberEnds.get(0).getAggregation());
		Assert.assertEquals("The first Association member end must be not navigable", false, memberEnds.get(0).isNavigable());
		Assert.assertEquals("The first Association member end must be the association", association, memberEnds.get(0).eContainer());
		Assert.assertEquals("The second Association member end must not have aggregation kind", AggregationKind.NONE_LITERAL, memberEnds.get(1).getAggregation());
		Assert.assertEquals("The second Association member end must be not navigable", false, memberEnds.get(1).isNavigable());
		Assert.assertEquals("The second Association member end must be the association", association, memberEnds.get(1).eContainer());
	}


}
