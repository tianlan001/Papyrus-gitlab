/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 501833
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalSorter;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.PlatformUIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * @author Vincent Lorenzo
 * 
 *         This class allows to build a StyledText widget allowing the completion
 *
 */
public class StringEditorWithCompletionWrapper implements ISetPapyrusConverter {

	private static final String CONTENT_ASSIST_COMMAND_ID = "org.eclipse.ui.edit.text.contentAssist.proposals"; //$NON-NLS-1$

	/**
	 * the parser used to convert object to string and string to object
	 */
	public IPapyrusConverter parser;

	/**
	 * boolean indicating if the content assist popup is opened
	 */
	private boolean delayedIsOpen = false;

	/**
	 * the text viewer
	 */
	private TextViewer textViewer;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            the parent to use to create the TextWidget
	 * @param helper
	 *            the helper to use to find the elements by their name
	 */
	public StringEditorWithCompletionWrapper(Composite parent, IPapyrusConverter parser) {
		setPapyrusConverter(parser);
		buildControls(parent, SWT.NONE);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            the parent to use to create the TextWidget
	 * @param helper
	 *            the helper to use to find the elements by their name
	 */
	public StringEditorWithCompletionWrapper(Composite parent, int style, IPapyrusConverter parser) {
		setPapyrusConverter(parser);
		buildControls(parent, style);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            the parent to use to create the TextWidget
	 * @param helper
	 *            the helper to use to find the elements by their name
	 */
	public StringEditorWithCompletionWrapper(Composite parent, int style) {
		buildControls(parent, style);
	}


	/**
	 * 
	 * @return
	 * 		the text viewer used
	 */
	public TextViewer getTextViewer() {
		return this.textViewer;
	}

	/**
	 * 
	 * @return
	 * 		the styled text used or <code>null</code>
	 */
	public StyledText getTextWidget() {
		if (this.textViewer != null) {
			return this.textViewer.getTextWidget();
		}
		return null;
	}


	/**
	 * 
	 * @return
	 * 		<code>true</code> if the content assist is currently opened
	 */
	public boolean isContentAssistOpened() {
		return delayedIsOpen;
	}

	private ContentAssistant assistant;

	private IContentAssistProcessor processor;

	private void buildControls(Composite parent, int style) {
		// setLayout(new FillLayout());
		textViewer = new TextViewer(parent, SWT.SINGLE | SWT.V_SCROLL | style);

		textViewer.setDocument(new Document());

		this.assistant = new ContentAssistant();


		if (parser != null) {
			this.processor = parser.getCompletionProcessor(null);
			assistant.setContentAssistProcessor(this.processor, IDocument.DEFAULT_CONTENT_TYPE);
		}

		// Manage the sorter for the completion proposal
		assistant.setSorter(new ICompletionProposalSorter() {

			@Override
			public int compare(final ICompletionProposal p1, final ICompletionProposal p2) {
				return p1.getDisplayString().compareTo(p2.getDisplayString());
			}
		});

		assistant.install(textViewer);
		assistant.addCompletionListener(new ICompletionListener() {

			@Override
			public void selectionChanged(ICompletionProposal proposal, boolean smartToggle) {

			}

			@Override
			public void assistSessionStarted(ContentAssistEvent event) {
				// reset open status asynchronously.
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						delayedIsOpen = true;
					}
				});
			}

			@Override
			public void assistSessionEnded(ContentAssistEvent event) {
				// reset open status asynchronously.
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						delayedIsOpen = false;
					}
				});

			}
		});

		// Hook up the user's preferred key binding for content assist
		PlatformUIUtils.handleCommand(textViewer.getControl(), CONTENT_ASSIST_COMMAND_ID,
				"CTRL+SPACE", //$NON-NLS-1$ // The default on all platforms
				assistant::showPossibleCompletions);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter#setPapyrusConverter(org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter)
	 *
	 * @param parser
	 */
	@Override
	public void setPapyrusConverter(IPapyrusConverter parser) {
		this.parser = parser;
		if (parser != null && assistant != null && processor == null) {
			this.processor = parser.getCompletionProcessor(null);
			assistant.setContentAssistProcessor(this.processor, IDocument.DEFAULT_CONTENT_TYPE);
		}
	}
}
