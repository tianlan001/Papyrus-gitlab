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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.applynamedstyle;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.command.AbstractContextFreeCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;

/**
 * The apply named style command.
 *
 * @since 5.0
 */
public class PapyrusApplyNamedStyleCommand extends AbstractContextFreeCommand {

	/** The editing domain. */
	private TransactionalEditingDomain editingDomain;

	/** The element to be applied the named style. */
	private StyledElement element;

	/** The named style string. */
	private String namedStyle;

	/**
	 * Constructor.
	 *
	 * @param element
	 *            The element to be applied the named style
	 * @param namedStyle
	 *            The named style string
	 */
	public PapyrusApplyNamedStyleCommand(final TransactionalEditingDomain editingDomain, final StyledElement element, final String namedStyle) {
		this.editingDomain = editingDomain;
		this.element = element;
		this.namedStyle = namedStyle;
	}

	/**
	 * @return The editing domain
	 */
	public TransactionalEditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	/**
	 * @return The element to be applied the named style
	 */
	public StyledElement getElement() {
		return this.element;
	}

	/**
	 * @return The named style string
	 */
	public String getNamedStyle() {
		return this.namedStyle;
	}
}
