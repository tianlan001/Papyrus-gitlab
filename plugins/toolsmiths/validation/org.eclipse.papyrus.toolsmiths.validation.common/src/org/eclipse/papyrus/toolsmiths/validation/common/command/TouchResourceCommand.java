/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.command;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

import java.util.stream.Collector;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;

/**
 * A command that marks a resource as modified so that it should be written
 * to disk with changes that are implied by other means than editing of its contents.
 * That may include, for example, the need to update HREFs for refactorings of
 * objects in other resources.
 */
public class TouchResourceCommand extends AbstractOverrideableCommand {

	private Resource resource;
	private boolean wasModified;

	public TouchResourceCommand(EditingDomain domain, Resource resource) {
		super(domain, Messages.TouchResourceCommand_0, Messages.TouchResourceCommand_1);

		this.resource = resource;
	}

	@Override
	public void doDispose() {
		resource = null;

		super.doDispose();
	}

	@Override
	protected boolean prepare() {
		return resource != null;
	}

	@Override
	public void doExecute() {
		wasModified = resource.isModified();
		if (!wasModified) {
			resource.setModified(true);
		}
	}

	@Override
	public void doUndo() {
		resource.setModified(wasModified);
	}

	@Override
	public void doRedo() {
		if (!wasModified) {
			resource.setModified(true);
		}
	}

	/**
	 * Collect a stream of resources into a command that touches them.
	 *
	 * @param domain
	 *            the contextual editing domain in which to create the touch commands
	 *
	 * @return the touch commands collected, perhaps in a {@link CompoundCommand}
	 */
	public static Collector<Resource, ?, Command> toTouchCommand(EditingDomain domain) {
		return collectingAndThen(
				filtering(not(Resource::isModified),
						mapping(resource -> (Command) new TouchResourceCommand(domain, resource),
								reducing(Command::chain))),
				optional -> optional.orElse(IdentityCommand.INSTANCE));
	}

}
