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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.commands;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * This allows to add a detail into the annotation.
 * @since 3.0
 */
public class AddEAnnotationDetailCommand extends RecordingCommand {

	/**
	 * The owner annotation.
	 */
	private EAnnotation eAnnotation;

	/**
	 * The key to add.
	 */
	private String detailKey;

	/**
	 * The value of the detail key.
	 */
	private String value;

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            The editing domain.
	 * @param annotation
	 *            The owner annotation.
	 * @param detailKey
	 *            The key to add.
	 * @param value
	 *            The value of the detail key.
	 */
	public AddEAnnotationDetailCommand(final TransactionalEditingDomain domain, final EAnnotation annotation, final String detailKey, final String value) {
		super(domain);
		this.eAnnotation = annotation;
		this.detailKey = detailKey;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 */
	@Override
	protected void doExecute() {
		eAnnotation.getDetails().put(detailKey, value);
	}

}
