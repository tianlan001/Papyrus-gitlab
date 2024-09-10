/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.common.strategy.paste;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.AbstractPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.DefaultPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrus.infra.internationalization.InternationalizationEntry;
import org.eclipse.papyrus.infra.internationalization.utils.commands.InternationalizationPasteCommand;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;

/**
 * Offer a strategy for the internationalization of pasted objects.
 * 
 * @since 3.0
 */
public class InternationalizationPasteStrategy extends AbstractPasteStrategy implements IPasteStrategy {

	/**
	 * The instance.
	 */
	private static IPasteStrategy instance = new InternationalizationPasteStrategy();

	/**
	 * Constructor.
	 */
	public InternationalizationPasteStrategy() {
		// Do nothing
	}

	/**
	 * Gets the single instance of StereotypePasteStrategy.
	 *
	 * @return single instance of StereotypePasteStrategy
	 */
	public static IPasteStrategy getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Internationalization Strategy"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Copy Internationalization elements"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getID()
	 */
	@Override
	public String getID() {
		return new StringBuilder(Activator.ID).append(".InternationalizationStrategy").toString(); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#dependsOn()
	 */
	@Override
	public IPasteStrategy dependsOn() {
		return DefaultPasteStrategy.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getSemanticCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard)
	 */
	@Override
	public Command getSemanticCommand(final EditingDomain domain, final EObject targetOwner, final PapyrusClipboard<Object> papyrusClipboard) {
		final CompoundCommand compoundCommand = new CompoundCommand("Internationalization paste objects"); //$NON-NLS-1$

		Map<EObject, Object> sourceToCopy = papyrusClipboard.getSourceToInternalClipboard();

		for (final Entry<EObject, Object> sourceToCopyEntry : sourceToCopy.entrySet()) {
			final EObject source = sourceToCopyEntry.getKey();
			final EObject target = papyrusClipboard.getTragetCopyFromInternalClipboardCopy(sourceToCopyEntry.getValue());

			final Set<Locale> localesForSource = LabelInternationalizationUtils.getAvailableLocales(source.eResource());

			// Modify all the loaded properties files for copied object
			for (final Locale currentLocale : localesForSource) {
				final InternationalizationEntry internationalizationEntry = LabelInternationalizationUtils.getInternationalizationEntry(source, source, currentLocale);
				if (null != internationalizationEntry) {
					compoundCommand.append(new InternationalizationPasteCommand((TransactionalEditingDomain) domain, target, internationalizationEntry.getValue(), currentLocale));
				}
			}
		}
		// An empty can't be executed
		return !compoundCommand.getCommandList().isEmpty() ? compoundCommand : null;
	}

}
