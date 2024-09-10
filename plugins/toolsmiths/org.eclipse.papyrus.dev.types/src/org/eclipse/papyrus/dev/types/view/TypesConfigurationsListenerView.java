/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.view;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.NullElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.dev.types.Activator;
import org.eclipse.papyrus.dev.types.providers.TypesEventContentProvider;
import org.eclipse.papyrus.dev.types.providers.TypesEventLabelProvider;
import org.eclipse.papyrus.dev.types.utils.ElementTypeRegistryUtils;
import org.eclipse.papyrus.dev.types.utils.TypesEventPrinter;
import org.eclipse.papyrus.dev.types.utils.NotifierNullEditHelper;
import org.eclipse.papyrus.infra.types.core.notification.TypesEventsChain;
import org.eclipse.papyrus.infra.types.core.notification.TypesListenersRegistry;
import org.eclipse.papyrus.infra.types.core.notification.ITypesEventsChainListener;
import org.eclipse.papyrus.infra.types.core.notification.events.ITypesEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

public class TypesConfigurationsListenerView extends ViewPart {

	IClientContext context = null;
	FilteredTree tree = null;
	SashForm sash = null;
	Browser browser = null;
	ITypesEventsChainListener listener = null;
	Text limitText = null;

	List<TypesEventsChain> eventsChains = new ArrayList<TypesEventsChain>();

	final private int MAX_SIZE = 1000;


	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, true));
		Button startButton = new Button(parent, SWT.NONE);
		startButton.setText("Start");
		startButton.addMouseListener(new MouseAdapter() {



			@Override
			public void mouseUp(MouseEvent e) {
				if (!Platform.inDebugMode()) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Debug mode", "You must activate debug mode to be able to use this view. ");
				}

				// Dirty work to force the NullElementType edithelper if in debug mode
				if (Platform.inDebugMode()) {

					Field nullElementTypeEditHelperField = ElementTypeRegistryUtils.getNullElementTypeEditHelper();

					if (nullElementTypeEditHelperField != null) {
						// Register fake

						try {
							nullElementTypeEditHelperField.set(NullElementType.getInstance(), new NotifierNullEditHelper());
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						Activator.log.warn("Failed to override NullElementType's EditHelper");
					}

					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Debug mode", "Be careful, as the platform is in debug mode, the NullElementType's EditHelper has been overriden");
				}

				if (listener == null) {
					listener = new ITypesEventsChainListener() {

						@Override
						public void notifyChain(TypesEventsChain chain) {
							int limit;
							String text = limitText.getText();
							try {
								limit = Integer.parseInt(text);
							} catch (NumberFormatException e) {
								limit = MAX_SIZE;
							}
							if (eventsChains.size() >= limit) {
								eventsChains.remove(0);
							}
							eventsChains.add(chain);
							Display.getCurrent().asyncExec(new Runnable() {
								@Override
								public void run() {
									tree.getViewer().refresh();
								}
							});

						}
					};
					TypesListenersRegistry.getInstance().addEventChainListener(listener);
				}
			}

		});
		startButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Button stopButton = new Button(parent, SWT.NONE);
		stopButton.setText("Stop");
		stopButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				TypesListenersRegistry.getInstance().removeEventChainListener(listener);
				listener = null;
			}

		});
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Button clearButton = new Button(parent, SWT.NONE);
		clearButton.setText("Clear");
		clearButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				eventsChains.clear();
				tree.getViewer().refresh();
				browser.setText("");
			}

		});
		clearButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label label = new Label(parent, SWT.NONE);
		label.setText("Limit: ");

		limitText = new Text(parent, SWT.BORDER);
		limitText.setText(MAX_SIZE + "");
		limitText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		sash = new SashForm(parent, SWT.HORIZONTAL | SWT.BORDER);

		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		tree = new FilteredTree(sash, SWT.BORDER, new PatternFilter(), true);
		tree.getViewer().setLabelProvider(new TypesEventLabelProvider());
		tree.getViewer().setContentProvider(new TypesEventContentProvider());
		tree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					Object first = ((IStructuredSelection) event.getSelection()).getFirstElement();
					String result = "";
					if (first instanceof ITypesEvent) {

						Map<String, String> details = TypesEventPrinter.getEventDetails(((ITypesEvent) first));
						result = TypesEventPrinter.printHtmlEvent(details);
					} else if (first instanceof TypesEventsChain) {
						IEditCommandRequest req = ((TypesEventsChain) first).getRequest();
						result = TypesEventPrinter.printHtmRequest(req);
					}
					browser.setText(result);
				}

			}
		});
		tree.getViewer().setInput(eventsChains);

		browser = new Browser(sash, SWT.NONE);


		sash.setWeights(new int[] { 1, 3 });
	}



	@Override
	public void setFocus() {

	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 *
	 */
	@Override
	public void dispose() {
		TypesListenersRegistry.getInstance().removeEventChainListener(listener);
		super.dispose();
	}


}
