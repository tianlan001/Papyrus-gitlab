/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.junit.Rule;
import org.junit.Test;

/**
 * Message Occurrence Specification test
 */
public class TestMessageOccurrenceSpecification_477463 extends AbstractPapyrusTest {

	@Rule
	public final ModelSetFixture modelSet = new ModelSetFixture();

	public TestMessageOccurrenceSpecification_477463() {
		super();
	}

	@Test
	@PluginResource("resource/bug477463/bug477463.di")
	public void testMessageOccurrenceSpecification() {
		org.eclipse.uml2.uml.Package rootModel = modelSet.getModel();
		assertEquals("Model should contains root element.", 1, rootModel.getPackagedElements().size());
		assertTrue("Root element should be Interaction.", rootModel.getPackagedElements().get(0) instanceof Interaction);
		final Interaction interaction = (Interaction) rootModel.getPackagedElements().get(0);
		final EList<InteractionFragment> fragments = interaction.getFragments();
		MessageOccurrenceSpecification sendInteractionFragment = findMessageOccureOccurrenceSpecification(fragments, "MessageSend");
		assertNotNull(sendInteractionFragment);
		MessageOccurrenceSpecification recieveInteractionFragment = findMessageOccureOccurrenceSpecification(fragments, "MessageRecv");
		assertNotNull(recieveInteractionFragment);
		assertEquals("Model should contains just one message.", 1, interaction.getMessages().size());
		Message message = (Message) interaction.getMessages().get(0);
		MessageEnd sendMsgEnd = message.getSendEvent();
		assertTrue("Send message end should be MessageOccurrenceSpecification.", sendMsgEnd instanceof MessageOccurrenceSpecification);
		assertEquals("Send interaction fragment should be equals send message end.", sendInteractionFragment, sendMsgEnd);
		MessageEnd recieveMsgEnd = message.getReceiveEvent();
		assertTrue("Recieve message end should be MessageOccurrenceSpecification.", recieveMsgEnd instanceof MessageOccurrenceSpecification);
		assertEquals("Recieve interaction fragment should be equals send message end.", recieveInteractionFragment, recieveMsgEnd);
	}

	private MessageOccurrenceSpecification findMessageOccureOccurrenceSpecification(EList<InteractionFragment> fragments, String name) {
		for (final InteractionFragment fragment : fragments) {
			if (false == fragment instanceof MessageOccurrenceSpecification) {
				continue;
			}
			MessageOccurrenceSpecification result = (MessageOccurrenceSpecification) fragment;
			if (name.equalsIgnoreCase(result.getName())) {
				return result;
			}
		}
		return null;
	}
}
