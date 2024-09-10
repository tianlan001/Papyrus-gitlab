/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 402525, 430077, 443497
 *  Christian W. Damus - bug 511942
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.creation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.EClassNameComparator;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.internal.ui.messages.Messages;
import org.eclipse.papyrus.infra.properties.ui.providers.CreateInFeatureContentProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


/**
 * A ReferenceFactory used to instantiate and edit EObjects.
 * The specified nsUri and ClassName are used to instantiate the EObject.
 * The matching {@link org.eclipse.papyrus.infra.properties.contexts.View}s from all
 * applied {@link org.eclipse.papyrus.infra.properties.contexts.Context}s are used to
 * display the right form to edit the EObject.
 *
 * If no EClass is specified, a list of all concrete subclasses of {@link #type} will be displayed before the instantiation.
 *
 * @author Camille Letavernier
 */
public class EcorePropertyEditorFactory extends PropertyEditorFactory {

	/**
	 * The (abstract) EClass to instantiate
	 */
	protected EClass type;

	/**
	 * The (concrete) EClass to instantiate
	 * Should be a subclass of {@link #type}
	 */
	protected EClass eClass;

	/**
	 * The Namespace URI of the (concrete) EClass to instantiate
	 */
	protected String nsUri;

	/**
	 * The name of the (concrete) EClass to instantiate
	 */
	protected String className;

	/**
	 * The reference in which the object will be set.
	 */
	protected EReference referenceIn;

	/**
	 * The ContentProvider for browsing potential container EObjects
	 */
	protected IStaticContentProvider containerContentProvider;

	/**
	 * The ContentProvider for browsing potential containment EReferences.
	 * The input of this content provider is the object selected through the
	 * containerContentProvider
	 */
	protected CreateInFeatureContentProvider referenceContentProvider;

	/**
	 * The LabelProvider for displaying potential container EObjects
	 */
	protected ILabelProvider containerLabelProvider;

	/**
	 * The LabelProvider for displaying potential containment EReferences
	 */
	protected ILabelProvider referenceLabelProvider;

	/**
	 * Store information about where each object should be added on validation
	 */
	protected Map<EObject, CreateIn> createIn = new HashMap<EObject, CreateIn>();

	/**
	 * used to get image to display in the creation menu
	 * 
	 * @since 2.0
	 */
	protected LabelProviderService labelProviderService;

	/**
	 *
	 * Constructor.
	 *
	 * The factory will be able to instantiate the given EClass
	 *
	 * @param type
	 *            The type of EClass to instantiate when creating new EObjects.
	 */
	public EcorePropertyEditorFactory(EReference referenceIn) {
		if (referenceIn == null) {
			throw new IllegalArgumentException("The referenceIn parameter must be set"); //$NON-NLS-1$
		}

		this.referenceIn = referenceIn;
		this.type = referenceIn.getEReferenceType();
	}

	/**
	 * @return the nsUri of the EClass used by this factory to instantiate new EObjects
	 * @see #getClassName
	 */
	public String getNsUri() {
		return nsUri;
	}

	/**
	 * @return the className of the EClass used by this factory to instantiate new EObjects
	 * @see #getNsUri()
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the nsUri of the EClass used by this factory to instantiate new EObjects
	 *
	 * @param nsUri
	 * @see #getClassName
	 */
	public void setNsUri(String nsUri) {
		this.nsUri = nsUri;
		checkEClass();
	}

	/**
	 * Sets the className of the EClass used by this factory to instantiate new EObjects
	 *
	 * @param className
	 * @see #getNsUri()
	 */
	public void setClassName(String className) {
		this.className = className;
		checkEClass();
	}

	private void checkEClass() {
		if (nsUri != null && className != null) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri);
			if (ePackage == null) {
				Activator.log.warn("Cannot find the EPackage corresponding to URI " + nsUri); //$NON-NLS-1$
				return;
			}
			eClass = (EClass) ePackage.getEClassifier(className);
			if (eClass == null) {
				Activator.log.warn("Cannot find the EClass " + className + " in the package " + nsUri); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canCreateObject() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object createObject(Control widget, Object context) {
		Object result;

		final ResourceSet previous = NestedEditingDialogContext.getInstance().push(context);

		try {
			result = doCreateObject(widget, context);
		} finally {
			NestedEditingDialogContext.getInstance().pop(previous);
		}

		return result;
	}

	protected Object doCreateObject(Control widget, Object context) {
		Object instance;

		if (referenceIn.isContainment()) {
			instance = simpleCreateObject(widget);
		} else {
			instance = createObjectInDifferentContainer(widget);
		}

		return createObject(widget, context, instance);
	}

	@Override
	protected Object doEdit(final Control widget, final Object source, final Set<View> views, final String dialogTitle) {
		Object result;

		try {
			NestedEditingDialogContext.getInstance().enter();
			try {
				result = getOperationExecutor(source).execute(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return basicDoEdit(widget, source, views, dialogTitle);
					}
				}, dialogTitle);
			} finally {
				NestedEditingDialogContext.getInstance().exit();
			}
		} catch (OperationCanceledException e) {
			if (!NestedEditingDialogContext.getInstance().isNested()) {
				// Propagate to the caller if not in a nested edit dialog
				throw e;
			}

			// Let the client think that there's nothing needing to be done, that the referenced object
			// was edited "in place" instead of being replaced by some other object
			result = null;
		}

		return result;
	}

	protected final Object basicDoEdit(Control widget, Object source, Set<View> views, String dialogTitle) {
		return super.doEdit(widget, source, views, dialogTitle);
	}

	@Override
	protected void handleEditCancelled(Control widget, Object source) {
		throw new OperationCanceledException();
	}

	protected EObject simpleCreateObject(Control widget) {
		EClass eClass = chooseEClass(widget);
		if (eClass == null) {
			return null;
		}

		EFactory eFactory;
		ResourceSet rset = NestedEditingDialogContext.getInstance().getResourceSet();
		if (rset == null) {
			eFactory = eClass.getEPackage().getEFactoryInstance();
		} else {
			eFactory = rset.getPackageRegistry().getEFactory(eClass.getEPackage().getNsURI());
			if (eFactory == null) {
				eFactory = eClass.getEPackage().getEFactoryInstance();
			}
		}

		EObject instance = eFactory.create(eClass);
		return instance;
	}

	protected EObject createObjectInDifferentContainer(Control widget) {
		EObject instance = simpleCreateObject(widget);
		if (instance == null) {
			return null;
		}

		// Try to get the current resource set for a wide scope of places to put a new element
		Object containerInput = NestedEditingDialogContext.getInstance().getResourceSet();
		if (containerInput == null) {
			// Only have the object that we've created for context
			containerInput = instance;
		}

		containerContentProvider.inputChanged(null, null, containerInput);
		referenceContentProvider.setType(instance.eClass());
		CreateInDialog dialog = new CreateInDialog(widget.getShell(), instance);
		dialog.setProviders(containerContentProvider, referenceContentProvider, containerLabelProvider, referenceLabelProvider);
		dialog.setTitle(getCreationDialogTitle());
		int result = dialog.open();
		if (result != Window.OK) {
			return null;
		}
		CreateIn createIn = new CreateIn();
		createIn.createInObject = dialog.getContainer();
		createIn.createInReference = dialog.getContainmentReference();
		this.createIn.put(instance, createIn);

		return instance;
	}

	/**
	 * Gets the EClass to instantiate
	 * If the {@link #eClass} has been specified, then it is returned.
	 * Otherwise, displays a list of all valid concrete EClasses that
	 * are subtypes of {@link #type}, from which the user can choose
	 * the one to instantiate.
	 *
	 * @param widget
	 *            The control used to open a selection list (if more than one EClass
	 *            can be instantiated)
	 * @return
	 * 		The EClass to instantiate
	 */
	protected EClass chooseEClass(Control widget) {
		if (eClass != null) {
			return eClass;
		}

		List<EClass> availableClasses = getAvailableEClasses();
		Collections.sort(availableClasses, new EClassNameComparator());
		if (availableClasses.isEmpty()) {
			return null;
		}

		if (availableClasses.size() == 1) {
			this.className = availableClasses.get(0).getName();
			return availableClasses.get(0);
		}

		final Menu menu = new Menu(widget);
		for (EClass eClass : availableClasses) {
			final MenuItem item = new MenuItem(menu, SWT.NONE);
			item.setText(getText(eClass));
			item.setImage(getImage(eClass));
			item.setData("eClass", eClass); //$NON-NLS-1$
			item.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					EcorePropertyEditorFactory.this.eClass = (EClass) item.getData("eClass"); //$NON-NLS-1$
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// Nothing
				}

			});
		}

		menu.setVisible(true);

		// The menu is blocking the thread
		Display display = widget.getDisplay();
		while (menu.isVisible()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Throwable ex) {
				Activator.log.error(ex);
			}
		}
		if (!display.isDisposed()) {
			display.update();
		}

		EClass eClass = this.eClass;
		if (eClass != null) {
			className = eClass.getName();
		}
		this.eClass = null;

		return eClass;
	}

	/**
	 * 
	 * @param eClass
	 *            an eclass
	 * @return
	 * 		the image to display in the creation menu
	 * @since 2.0
	 * 
	 */
	protected Image getImage(final EClass eClass) {
		if (null == this.labelProviderService) {
			this.labelProviderService = new LabelProviderServiceImpl();
		}
		final ILabelProvider provider = this.labelProviderService.getLabelProvider(eClass);
		if (null != provider) {
			return provider.getImage(eClass);
		}
		return null;
	}

	/**
	 * 
	 * @param eClass
	 *            an eclass
	 * @return
	 * 		the text to display
	 * @since 2.0
	 * 
	 */
	protected String getText(final EClass eClass) {
		return eClass.getName();
	}


	/**
	 * @return
	 * 		The list of {@link EClass} that can be instantiated.
	 *         This is the list of all concrete subclasses of {@link #type}
	 */
	protected List<EClass> getAvailableEClasses() {
		List<EClass> availableEClasses = EMFHelper.getSubclassesOf(type, true);
		return availableEClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		if (!referenceIn.isContainment()) {
			for (Object objectToValidate : objectsToValidate) {
				// We add the object to the containment reference
				// They will be automatically added to the edited reference
				// (referenceIn) after this method returns
				CreateIn creationInformation = this.createIn.get(objectToValidate);
				if (creationInformation != null) {
					creationInformation.createInObject.eSet(creationInformation.createInReference, objectToValidate);
				} else {
					Activator.log.warn("Unknown object : " + objectToValidate);
				}
			}
		}

		return objectsToValidate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCreationDialogTitle() {
		return Messages.EcorePropertyEditorFactory_CreateANew + className;
	}

	@Override
	public String getEditionDialogTitle(Object objectToEdit) {
		if (objectToEdit instanceof EObject) {
			return "Edit " + ((EObject) objectToEdit).eClass().getName();
		}
		return super.getEditionDialogTitle(objectToEdit);
	}

	/**
	 * @return
	 * 		The EClass that will be instantiated, or null if this hasn't been forced
	 */
	public EClass getEClass() {
		return eClass;
	}

	protected class CreateIn {

		/**
		 * The (containment) reference in which the object will be created
		 * May be the same or different from {@link #referenceIn}
		 */
		public EReference createInReference;

		/**
		 * The (container) EObject in which the object will be created
		 */
		public EObject createInObject;
	}

	/**
	 * Sets the same label provider for both #referenceLabelProvider
	 * and #containerLabelProvider
	 *
	 * @param labelProvider
	 */
	public void setLabelProvider(ILabelProvider labelProvider) {
		setContainerLabelProvider(labelProvider);
		setReferenceLabelProvider(labelProvider);
	}

	public void setReferenceLabelProvider(ILabelProvider labelProvider) {
		this.referenceLabelProvider = labelProvider;
	}

	public void setContainerLabelProvider(ILabelProvider labelProvider) {
		this.containerLabelProvider = labelProvider;
	}

	public void setContainerContentProvider(IStaticContentProvider contentProvider) {
		this.containerContentProvider = contentProvider;
	}

	public void setReferenceContentProvider(CreateInFeatureContentProvider contentProvider) {
		this.referenceContentProvider = contentProvider;
	}

	@Override
	protected CreationContext getCreationContext(Object element) {
		return (element instanceof EObject) ? getCreationContext((EObject) element, true) : super.getCreationContext(element);
	}

	/**
	 * Gets the creation context providing the element in which a {@code modelElement} is being created.
	 *
	 * @param modelElement
	 *            a model element that is currently being created and probably is, therefore, not yet attached to the model
	 * @param demandCreate
	 *            whether to create the context and implicitly attach it if it is not already attached. This is only appropriate in the case that the {@code modelElement} is the element in which context we are creating new elements
	 *
	 * @return the creation context, or {@code null} if none is currently attached and we did not elect to create it on demand
	 */
	public static CreationContext getCreationContext(EObject modelElement, boolean demandCreate) {
		class EObjectCreationContext extends AdapterImpl implements CreationContext {

			private EObject context;

			private List<Object> createdElements = new ArrayList<>(2); // Anticipate small depth of dialog nesting

			EObjectCreationContext(EObject context) {
				this.context = context;
				context.eAdapters().add(this);
			}

			@Override
			public boolean isAdapterForType(Object type) {
				return type == CreationContext.class;
			}

			@Override
			public Object getCreationContextElement() {
				return context;
			}

			@Override
			public void popCreatedElement(Object newElement) {
				if (createdElements.remove(newElement)) {
					((Notifier) newElement).eAdapters().remove(this);

					if (createdElements.isEmpty()) {
						// Don't need this context adapter any more
						context.eAdapters().remove(this);
					}
				}
			}

			@Override
			public void pushCreatedElement(Object newElement) {
				createdElements.add(newElement);
				((Notifier) newElement).eAdapters().add(this);
			}
		}

		CreationContext result = (CreationContext) EcoreUtil.getExistingAdapter(modelElement, CreationContext.class);
		if ((result == null) && demandCreate) {
			result = new EObjectCreationContext(modelElement);
		}

		return result;
	}
}
