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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.dev.types.providers.ElementTypesContentProvider;
import org.eclipse.papyrus.dev.types.providers.ElementTypesDetailsContentProvider;
import org.eclipse.papyrus.dev.types.providers.ElementTypesDetailsLabelProvider;
import org.eclipse.papyrus.dev.types.providers.ElementTypesLabelProvider;
import org.eclipse.papyrus.infra.types.core.utils.AdviceUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

public class RegistredElementTypesView extends ViewPart {

	FilteredTree detailsFilteredTree = null;
	SashForm sash = null;
	FilteredTree elementTypesFilteredTree = null;
	Combo combo = null;
	ElementTypesDetailsContentProvider elementTypesDetailsContentProvider;


	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));

		combo = new Combo(parent, SWT.NONE);
		final List<String> itemsList = new ArrayList<>();
		List<IClientContext> contexts = new ArrayList<>(ClientContextManager.getInstance().getClientContexts());

		int index = -1;
		int i = 0;
		for (IClientContext context : contexts) {
			itemsList.add(context.getId());
			if (context.getId().equals(ClientContextManager.getDefaultClientContext().getId())) {
				index = i;
			}
			i++;
		}
		String[] items = new String[itemsList.size()];
		items = itemsList.toArray(items);
		combo.setItems(items);
		if (index != -1) {
			combo.select(index);
		}
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = combo.getSelectionIndex();

				String clientContexId = itemsList.get(index);

				if (clientContexId != null) {
					IClientContext clientContex = ClientContextManager.getInstance().getClientContext(clientContexId);
					if (clientContex != null) {
						IElementType[] elementTypes = ElementTypeRegistry.getInstance().getElementTypes(clientContex);
						elementTypesDetailsContentProvider.setContextID(clientContex.getId());
						elementTypesFilteredTree.getViewer().setInput(elementTypes);
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});

		Button exportButton = new Button(parent, SWT.NONE);
		exportButton.setText("Export registry");
		exportButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				String dest = dialog.open();
				if (dest != null) {
					File file = new File(dest);
					if (file != null) {
						BufferedWriter writer = null;
						try {
							writer = new BufferedWriter(new FileWriter(file));

							for (String clientContexId : itemsList) {
								writer.write(clientContexId + "\n");
								IElementType[] elementTypes = ElementTypeRegistry.getInstance().getElementTypes(ClientContextManager.getInstance().getClientContext(clientContexId));
								for (int j = 0; j < elementTypes.length; j++) {
									IElementType elementType = elementTypes[j];
									writer.write("\t" + elementType.getId() + "\n");


									IEditHelperAdvice[] advices = ElementTypeRegistry.getInstance().getEditHelperAdvice(elementType);

									// List<IEditHelperAdvice> advicesList = Arrays.asList(advices);
									// Collections.sort(advicesList, new AdviceComparator(elementType, clientContexId));
									IEditHelperAdvice[] sortedAdvices = AdviceUtil.sort(advices, elementType, clientContexId);
									for (IEditHelperAdvice advice : sortedAdvices) {
										writer.write("\t\t" + advice.getClass().getName() + "\n");
									}
								}
							}

							writer.flush();

						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							try {
								writer.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}
				}
			}
		});

		sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		elementTypesFilteredTree = new FilteredTree(sash, SWT.BORDER, new PatternFilter(), true);
		elementTypesFilteredTree.getViewer().setLabelProvider(new ElementTypesLabelProvider());
		elementTypesFilteredTree.getViewer().setContentProvider(new ElementTypesContentProvider());
		detailsFilteredTree = new FilteredTree(sash, SWT.BORDER, new PatternFilter(), true);
		detailsFilteredTree.getViewer().setLabelProvider(new ElementTypesDetailsLabelProvider());
		elementTypesDetailsContentProvider = new ElementTypesDetailsContentProvider();
		detailsFilteredTree.getViewer().setContentProvider(elementTypesDetailsContentProvider);

		if (index != -1) {
			combo.select(index);
		}

		elementTypesFilteredTree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
					if (selection instanceof IElementType) {
						elementTypesDetailsContentProvider.setTypeID(((IElementType) selection).getId());
						detailsFilteredTree.getViewer().setInput(selection);
					}
				}

			}
		});
	}

	@Override
	public void setFocus() {

	}
}
