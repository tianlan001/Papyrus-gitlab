/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
package org.eclipse.papyrus.uml.properties.xtext.widget;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog;
import org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.papyrus.infra.widgets.validator.AbstractValidator;
import org.eclipse.papyrus.uml.properties.xtext.Activator;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.StyledTextXtextAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProviderWithInit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

/**
 * This class provides a ReferenceValueEditor, with a text field with the xtext
 * completion and syntax instead of the CLabel.
 */
public class UMLXtextReferenceValueEditor extends StyledTextReferenceDialog
		implements IContextElementProvider, SelectionListener {

	/**
	 * The equal character.
	 */
	private static final String EQUAL = "="; //$NON-NLS-1$

	/**
	 * The default value to validate.
	 */
	private static final String DEFAULT_VALIDATE_VALUE = "0"; //$NON-NLS-1$


	/**
	 * The xtext adapter.
	 */
	private StyledTextXtextAdapter xtextAdapter;

	/**
	 * The xtext direct editor configuration used.
	 */
	private DefaultXtextDirectEditorConfiguration configuration;

	/**
	 * The context element adapter.
	 */
	private final ContextElementAdapter contextElementAdapter = new ContextElementAdapter(
			this);

	/**
	 * This allow to manage if the text can be parsed or not.
	 */
	private boolean canParse = false;

	/**
	 * The direct editor configuration class name to use.
	 */
	private String directEditorConfiguration;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed.
	 * @param style
	 *            The style for the widget.
	 */
	public UMLXtextReferenceValueEditor(final Composite parent, final int style) {
		this(parent, style, null);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed.
	 * @param style
	 *            The style for the widget.
	 * @param targetValidator
	 *            The validator used for the styled text.
	 */
	public UMLXtextReferenceValueEditor(final Composite parent, final int style, final AbstractValidator targetValidator) {
		super(parent, style, targetValidator);
		styledTextStringEditor.getText().addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				if (canParse) {
					IParser parser = getParser();
					if (null != xtextAdapter) {
						if (null == xtextAdapter
								|| null == xtextAdapter.getCompletionProposalAdapter()
								|| !xtextAdapter.getCompletionProposalAdapter()
										.delayedIsPopupOpen()) {
							manageParserCommand(parser);

							// Manage the color field and the control decoration
							styledTextStringEditor.notifyListeners(SWT.FocusOut, new Event());
							styledTextStringEditor.changeColorField();
							controlDecoration.hide();
							canParse = false;
						}
					}
				}
			}

			public void focusGained(FocusEvent e) {
				canParse = true;
			}
		});
	}

	/**
	 * This allow to manage the parser command.
	 * 
	 * @param parser
	 *            The parser used.
	 */
	protected void manageParserCommand(final IParser parser) {
		if (null != parser) {
			ICommand command = null;
			if (null != modelProperty
					&& modelProperty.getValueType() instanceof EStructuralFeature) {
				command = parser.getParseCommand(new EObjectAdapter(
						(EStructuralFeature) modelProperty.getValueType()),
						styledTextStringEditor.getText().getText(), 0);
			} else {
				command = parser.getParseCommand(new EObjectAdapter(
						(EObject) getValue()), styledTextStringEditor.getText()
								.getText(),
						0);
			}

			TransactionalEditingDomain domain = TransactionUtil
					.getEditingDomain(getContextElement());
			if (null == domain) {
				// can be null for opaque expression that have been
				// created but have not been added to parent
				// try to get resource set from nested dialog context
				ResourceSet rs = NestedEditingDialogContext.getInstance()
						.getResourceSet();
				domain = TransactionUtil.getEditingDomain(rs);
			}
			if (null != domain) {
				domain.getCommandStack().execute(
						new GMFtoEMFCommandWrapper(command));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#createStyledTextStringEditor(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 */
	@Override
	protected StyledTextStringEditor createStyledTextStringEditor(
			final Composite parent, final String initialValue, final int style) {
		// Change the style to set the singleText to a single line
		int createdStyle = style | SWT.SINGLE;
		return new StyledTextStringEditor(parent, createdStyle, targetValidator) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#createStyledText(org.eclipse.swt.widgets.Composite, java.lang.String, int)
			 */
			public StyledText createStyledText(final Composite parent, final String value,
					final int createdStyle) {
				StyledText txt = new StyledText(parent, createdStyle);
				if (null != labelProvider) {
					txt.setText(labelProvider.getText(getValue()));
				}
				return txt;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#keyPressed(org.eclipse.swt.events.KeyEvent)
			 */
			@Override
			public void keyPressed(final KeyEvent e) {
				// Manage the 'enter' key with 'keyPressed' instead of 'keyReleased'
				// because the proposal window is already closed in the 'keyReleased' method
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					if ((text.getStyle() & SWT.MULTI) == 0) { // Single-line : Enter
						if (e.stateMask == SWT.NONE) {
							notifyChange();
						}
					} else { // Multi-line : Ctrl+Enter
						if (e.stateMask == SWT.CTRL) {
							String str = text.getText();
							if (str.endsWith(StringSelector.LINE_SEPARATOR)) {
								int newLength = str.length() - StringSelector.LINE_SEPARATOR.length();
								text.setText(str.substring(0, newLength));
								text.setSelection(newLength);
							}
							notifyChange();
						}
					}
				}
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#notifyChange()
			 */
			@Override
			protected void notifyChange() {
				// Don't notify if the enter key was for the proposal window
				if (null != xtextAdapter
						&& null != xtextAdapter.getCompletionProposalAdapter()
						&& xtextAdapter.getCompletionProposalAdapter()
								.delayedIsPopupOpen()) {
					return;
				}
				// The 'enter' key was pressed, the parse will be called
				canParse = true;
				super.notifyChange();
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#keyReleased(org.eclipse.swt.events.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				// Nothing
				// Manage the 'enter' key with 'keyPressed' instead of 'keyReleased'
				// because the proposal window is already closed in the 'keyReleased' method
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#getTextToValidate()
			 */
			@Override
			protected String getTextToValidate() {
				// Manage the string to parse (because the XText parser allow a filled name with the value)
				String result = super.getTextToValidate();
				// If the text contains '=', just parse the value after
				if (result.contains(EQUAL) && result.indexOf(EQUAL) != (result.length() - 1)) {
					result = result.substring(result.indexOf(EQUAL) + 1, result.length());
				} else {
					// Try to parse the value as integer if id doesn't contain '='
					try {
						Integer.parseInt(result);
					} catch (NumberFormatException exception) {
						// Set the default value validation to avoid validation error
						result = DEFAULT_VALIDATE_VALUE;
					}
				}
				return result;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#update()
	 */
	@Override
	public void update() {
		super.update();
		updateControls();
	}

	/**
	 * This allow to update the xtext adapter for the styled text.
	 * 
	 * @param styledText
	 *            The styled text which one to adapt the xtext adapter.
	 */
	protected void updateXtextAdapters(final Control styledText) {
		final Object oldObjectToEdit = null != configuration ? configuration
				.getObjectToEdit() : null;

		final DefaultXtextDirectEditorConfiguration newConfiguration = getConfigurationFromSelection();
		// Check if configuration has changed and update adapters
		if (null != newConfiguration && newConfiguration != configuration) {
			if (null != xtextAdapter) {
				xtextAdapter.getFakeResourceContext().getFakeResource()
						.eAdapters().remove(contextElementAdapter);
			}
			configuration = newConfiguration;
			xtextAdapter = new StyledTextXtextAdapter(
					configuration.getInjector());

			EObject semanticElement = (EObject) getValue();
			if (null != semanticElement) {
				newConfiguration.preEditAction(semanticElement);
			}

			xtextAdapter.getFakeResourceContext().getFakeResource().eAdapters()
					.add(contextElementAdapter);
			xtextAdapter.adapt((StyledText) styledText);
		}

		if (null != configuration
				&& configuration.getObjectToEdit() != oldObjectToEdit) {
			IContextElementProvider provider = configuration
					.getContextProvider();
			if (provider instanceof IContextElementProviderWithInit) {
				// update resource, if required by text editor
				if (null != xtextAdapter) {
					((IContextElementProviderWithInit) provider)
							.initResource(xtextAdapter.getFakeResourceContext()
									.getFakeResource());
				}
			}
		}
	}

	/**
	 * This allow to get the xtext direct editor configuration depending on the
	 * value of the styled text.
	 * 
	 * @return The {@link DefaultXtextDirectEditorConfiguration} corresponding.
	 */
	protected DefaultXtextDirectEditorConfiguration getConfigurationFromSelection() {
		if (null != directEditorConfiguration && !directEditorConfiguration.isEmpty()) {
			try {
				// Search the direct editor configuration by the class name filled in XWT file
				final Class<?> directEditorConfigurationClass = Class.forName(directEditorConfiguration);
				if (null != directEditorConfigurationClass) {
					// Create an instance of the found class
					final Object classInstance = directEditorConfigurationClass.newInstance();
					// If the created instance is a direct xtext editor configuration, return the created direct editor configuration
					if (classInstance instanceof DefaultXtextDirectEditorConfiguration) {
						final DefaultXtextDirectEditorConfiguration xtextConfiguration = (DefaultXtextDirectEditorConfiguration) classInstance;

						// Set the correct object to edit as pre edit action
						if (null != modelProperty && getContextElement() instanceof EObject && modelProperty.getValueType() instanceof EStructuralFeature) {
							xtextConfiguration.preEditAction(((EObject) getContextElement())
									.eGet((EStructuralFeature) modelProperty.getValueType()));
						} else {
							xtextConfiguration.preEditAction((EObject) getValue());
						}

						// Return the created direct editor configuration
						return xtextConfiguration;
					}
				}
			} catch (final ClassNotFoundException e) {
				Activator.log.error(e);
			} catch (final InstantiationException e) {
				Activator.log.error(e);
			} catch (final IllegalAccessException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

	/**
	 * Get the parser corresponding to the value.
	 * 
	 * @return The {@link IParser}
	 */
	protected IParser getParser() {
		EObject parentSemanticElement = null;
		final Object contextElement = getContextElement();
		if (null != contextElement && contextElement instanceof EObject) {
			parentSemanticElement = (EObject) contextElement;
		}
		if (null != configuration && null != parentSemanticElement) {
			return configuration.createParser(parentSemanticElement);
		}
		return null;
	}

	/**
	 * {@inheritDoc} Update the xtext adapter.
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#doBinding()
	 */
	@Override
	protected void doBinding() {
		super.doBinding();
		updateXtextAdapters(styledTextStringEditor.getText());
		// Manage the setText of StyledText by a Runnable because the UI is blocker previously
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				styledTextStringEditor.setValue(labelProvider.getText(getValue()));
			}
		});
		updateLabel();
	}

	/**
	 * {@inheritDoc} Dispose the xtext adapter.
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#dispose()
	 *
	 */
	@Override
	public void dispose() {
		// dispose resources to avoid memory leaks
		if (null != styledTextStringEditor) {
			styledTextStringEditor.dispose();
		}
		if (null != xtextAdapter) {
			xtextAdapter.getFakeResourceContext().getFakeResource().eAdapters()
					.remove(contextElementAdapter);
			xtextAdapter.dispose();
			xtextAdapter = null;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider#getContextObject()
	 */
	public EObject getContextObject() {
		final Object value = getValue();
		return value instanceof EObject ? (EObject) value : null;
	}

	/**
	 * Sets the direct editor configuration class name.
	 * 
	 * @param directEditorConfiguration
	 *            The direct editor configuration class name.
	 */
	public void setDirectEditorConfiguration(final String directEditorConfiguration) {
		this.directEditorConfiguration = directEditorConfiguration;
		// React of the direct editor configuration modification (this will happened after the binding call)
		updateXtextAdapters(styledTextStringEditor.getText());
	}
}
