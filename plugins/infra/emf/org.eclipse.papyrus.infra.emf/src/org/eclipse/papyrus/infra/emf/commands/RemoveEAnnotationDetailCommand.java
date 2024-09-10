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

package org.eclipse.papyrus.infra.emf.commands;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Céline JANSSENS
 *
 */
public class RemoveEAnnotationDetailCommand extends RecordingCommand {

	private EAnnotation eAnnotation;
	private String detailKey;

	/**
	 * Constructor.
	 *
	 */
	public RemoveEAnnotationDetailCommand(TransactionalEditingDomain domain, EAnnotation annotation, String detailKey) {
		super(domain);
		this.eAnnotation = annotation;
		this.detailKey = detailKey;

	}

	/**
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		eAnnotation.getDetails().removeKey(detailKey);

	}

}
