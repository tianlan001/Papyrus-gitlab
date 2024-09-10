/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Bug fix
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 482669
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.pde.core.plugin.IPluginModel;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.osgi.framework.Bundle;

/**
 * Selection dialog for icons in bundles
 */
public class BundleIconExplorerDialog extends SelectionStatusDialog {

	/** The asterisk constant */
	protected static final String ASTERISK = "*"; //$NON-NLS-1$

	/** The empty string. */
	protected static final String EMPTY = ""; //$NON-NLS-1$

	/** gif file extension */
	protected static final String GIF_EXTENSION = ".gif"; //$NON-NLS-1$

	/** length of the extension */
	public static final int GIF_EXTENSION_LENGTH = GIF_EXTENSION.length();

	/** protocol for platform plugin URLs */
	protected static final String PLUGIN_PROTOCOL = "platform:/plugin/"; //$NON-NLS-1$

	/** indicates if several icons can be selected at the same time */
	protected final boolean allowMultiple;

	/** list that displays icons */
	protected FilteredList filteredList;

	/** current filter string */
	protected String filter = null;

	/** initial value */
	protected String initialValue;

	/** current displayed bundle name */
	protected String currentBundleName = "org.eclipse.uml2.uml.edit"; //$NON-NLS-1$

	/** the field text */
	private Text text;

	/** the local path */
	private String localPath = EMPTY; // $NON-NLS-1$

	/**
	 * Creates a new Icon Bundle Explorer Dialog
	 *
	 * @param parentShell
	 *            the parent shell for the dialog
	 */
	public BundleIconExplorerDialog(final Shell parentShell, final boolean allowMultiple, final String initialValue, final String bundle) {
		super(parentShell);
		this.allowMultiple = allowMultiple;
		this.initialValue = initialValue;
		this.currentBundleName = bundle;
		setTitle(Messages.BundleIconExplorerDialog_Title);
		setMessage(Messages.BundleIconExplorerDialog_Message);

	}

	/**
	 * Creates a new Icon Bundle Explorer Dialog
	 *
	 * @param parentShell
	 *            the parent shell for the dialog
	 */
	public BundleIconExplorerDialog(final Shell parentShell, final String initialValue) {
		this(parentShell, false, initialValue, Activator.retrieveBundleId(initialValue));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		initializeDialogUnits(composite);

		// creates the message area, as defined in the super class
		createMessageArea(composite);
		createComboArea(composite);
		createFilterText(composite);
		createFilteredList(composite);

		refreshList();

		return composite;
	}

	/**
	 * Refresh the content of the
	 */
	@SuppressWarnings("static-access")
	protected void refreshList() {
		// check selection
		currentBundleName = text.getText().trim();
		Bundle bundle = Platform.getBundle(currentBundleName);
		if (bundle == null) {
			Activator.getDefault().log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.BundleIconExplorerDialog_CantFindBundleIdMessage + currentBundleName));
			return;
		}
		Enumeration<URL> e = bundle.findEntries(EMPTY, ASTERISK + GIF_EXTENSION, true); // $NON-NLS-1$ //$NON-NLS-2$
		List<ImageProxy> selectedProxy = new ArrayList<ImageProxy>();
		List<ImageProxy> images = new ArrayList<ImageProxy>();
		if (e == null) {
			return;
		}
		while (e.hasMoreElements()) {
			ImageProxy proxy = new ImageProxy(e.nextElement());
			if (proxy.isDisplayed()) {
				images.add(proxy);
				// check if the proxy corresponds to the initialValue
				if (proxy.isInitial()) {
					selectedProxy.add(proxy);
				}
			}
		}
		filteredList.setElements(images.toArray());

		// select objects
		if (!selectedProxy.isEmpty()) {
			filteredList.setSelection(selectedProxy.toArray());
		}
	}

	/**
	 * Creates an area where users can select bundles where icons should be selected
	 *
	 * @param composite
	 *            the parent composite of the controls created in this area
	 */
	protected void createComboArea(final Composite composite) {
		Composite parent = new Composite(composite, SWT.NONE);
		GridData data = new GridData();
		data.grabExcessVerticalSpace = false;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		parent.setLayoutData(data);
		parent.setFont(parent.getFont());

		GridLayout layout = new GridLayout(3, false);
		parent.setLayout(layout);

		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.BundleIconExplorerDialog_Bundle);

		text = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
		data = new GridData();
		data.grabExcessVerticalSpace = false;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		text.setLayoutData(data);

		text.setText(currentBundleName);

		Button selectBundleButton = new Button(parent, SWT.NONE);
		selectBundleButton.setText("...");//$NON-NLS-1$
		selectBundleButton.addMouseListener(new MouseAdapter() {
			/**
			 * @{inheritDoc}
			 * @see org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse.swt.events.MouseEvent)
			 */
			@Override
			public void mouseUp(MouseEvent e) {
				handleManageBundlesButtonPressed();
			}
		});
	}

	/**
	 * Handles action when user press the Manage bundle button in the combo area
	 */
	protected void handleManageBundlesButtonPressed() {
		// open a dialog
		BundleExplorerDialog dialog = new BundleExplorerDialog(getParentShell(), false, PluginRegistry.getActiveModels(true));
		if (Window.OK == dialog.open()) {
			text.setText(((IPluginModel) dialog.getFirstResult()).getPlugin().getId());
			refreshList();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void computeResult() {
		computeIconPath();
		List<Object> proxies = Arrays.asList(getSelectedElements());
		List<String> results = new ArrayList<String>(proxies.size());
		for (Object proxy : proxies) {
			results.add(((ImageProxy) proxy).getPluginPath());
		}
		setResult(results);
	}

	/**
	 * Returns an array of the currently selected elements.
	 * To be called within or after open().
	 *
	 * @return returns an array of the currently selected elements.
	 */
	protected Object[] getSelectedElements() {
		Assert.isNotNull(filteredList);
		return filteredList.getSelection();
	}

	/**
	 * Creates an area where a filter can be entered. This filter will restrict the list of available icons.
	 *
	 * @param parent
	 *            the parent composite where to create the filter text
	 * @return the created text area
	 */
	protected void createFilterText(final Composite parent) {
		// Create the filter composite
		final StringWithClearEditor filterText = new StringWithClearEditor(parent, SWT.BORDER);

		filterText.setValue(filter);

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event e) {
				filteredList.setFilter(ASTERISK + filterText.getValue());
			}
		};
		filterText.getText().addListener(SWT.Modify, listener);

		filterText.getText().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (SWT.ARROW_DOWN == e.keyCode) {
					filteredList.setFocus();
				}
			}
		});
	}

	/**
	 * Creates a filtered list.
	 *
	 * @param parent
	 *            the parent composite.
	 * @return returns the filtered list widget.
	 */
	protected FilteredList createFilteredList(final Composite parent) {
		int flags = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | (allowMultiple ? SWT.MULTI : SWT.SINGLE);

		FilteredList list = new FilteredList(parent, flags, new BundleIconLabelProvider(), true, true, true);

		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(60);
		data.heightHint = convertHeightInCharsToPixels(18);
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		list.setLayoutData(data);
		list.setFont(parent.getFont());
		list.setFilter((null == filter ? EMPTY : filter)); // $NON-NLS-1$

		filteredList = list;
		return list;
	}

	/**
	 * Returns the bundle identifier for the current image
	 *
	 * @return the bundle identifier for the current image
	 */
	public String getCurrentBundleName() {
		return currentBundleName;
	}

	/** compute the icon path */
	private void computeIconPath() {
		List<Object> proxies = Arrays.asList(getSelectedElements());
		if (proxies.size() == 1) {
			localPath = ((ImageProxy) proxies.get(0)).getLocalPath();
		}
	}

	/**
	 * Returns the path to the icon in the bundle
	 *
	 * @return the path to the icon in the bundle
	 */
	public String getIconPath() {
		return localPath;
	}

	/**
	 * label provider for the icons in Bundle
	 */
	public class BundleIconLabelProvider extends LabelProvider {

		/**
		 * Creates a new BundleIconLabelProvider.
		 */
		public BundleIconLabelProvider() {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Image getImage(final Object element) {
			if (element instanceof ImageProxy) {
				return ((ImageProxy) element).getImage();
			}
			return super.getImage(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getText(final Object element) {
			if (element instanceof ImageProxy) {
				return ((ImageProxy) element).getText();
			}
			return super.getText(element);
		}
	}

	/**
	 * Proxy for images
	 */
	protected class ImageProxy {

		/** proxied image */
		private final Image image;

		/** full plugin path */
		private String path;

		/** local path inside the plugin */
		private String localPath;

		/** local path inside the plugin */
		private String fileName;

		/**
		 * Creates an Image Proxy
		 *
		 * @param url
		 *            the url of the image to proxy
		 */
		public ImageProxy(final URL url) {
			localPath = url.getPath();
			path = PLUGIN_PROTOCOL + getCurrentBundleName() + localPath;
			image = Activator.getImageFromKey(path);
			int index = localPath.lastIndexOf('/');
			if (index > 0 && index < localPath.length()) {
				fileName = localPath.substring(index + 1, localPath.length() - GIF_EXTENSION_LENGTH);
			} else {
				fileName = Messages.BundleIconExplorerDialog_UnknownFileName;
			}
		}

		/**
		 * Checks if this proxy corresponds to the initial value
		 *
		 * @return <code>true</code> if this is the initial value proxy
		 */
		public boolean isInitial() {
			return initialValue.equals(path);
		}

		/**
		 * Returns the real image
		 *
		 * @return the real image
		 */
		public Image getImage() {
			return image;
		}

		/**
		 * Returns <code>true</code> if this image is correct
		 *
		 * @return <code>true</code> if this image is correct
		 */
		public boolean isDisplayed() {
			Rectangle bounds = image.getBounds();
			if (bounds.height == 16 && bounds.width == 16) {
				return true;
			}
			return false;
		}

		/**
		 * Returns the text to display
		 *
		 * @return the text to display
		 */
		public String getText() {
			return fileName;
		}

		/**
		 * @return the plugin path.
		 */
		public String getPluginPath() {
			return path;
		}


		/**
		 * Returns the path to the icon in the bundle
		 *
		 * @return the path to the icon in the bundle
		 */
		public String getLocalPath() {
			return localPath;
		}
	}
}
