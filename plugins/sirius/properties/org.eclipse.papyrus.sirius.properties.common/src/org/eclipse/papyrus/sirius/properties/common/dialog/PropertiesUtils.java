/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.common.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.eef.EEFViewDescription;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EEFPage;
import org.eclipse.eef.core.api.EEFView;
import org.eclipse.eef.core.api.EEFViewFactory;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.sirius.properties.common.messages.Messages;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.sirius.common.interpreter.api.VariableManagerFactory;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.properties.AbstractOverrideDescription;
import org.eclipse.sirius.properties.Category;
import org.eclipse.sirius.properties.PageDescription;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.properties.core.api.OverridesProvider;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.sirius.properties.core.api.ViewDescriptionConverter;
import org.eclipse.sirius.properties.core.api.ViewDescriptionPreprocessor;
import org.eclipse.sirius.ui.properties.internal.SiriusUIPropertiesPlugin;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Utils class used to open properties dialog.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class PropertiesUtils {

	/**
	 * The choice of the user. Window.OK or Window.CANCEL.
	 */
	private static int open;

	/**
	 * List of Sirius group activated for a given element.
	 */
	private List<Group> activeSiriusGroup;

	/**
	 * Constructor.
	 */
	public PropertiesUtils() {
		// Nothing to Do
	}

	/**
	 * Open properties popup to create a given EObject.
	 * 
	 * @param contextAdapter  the adapter used to modify model elements
	 * @param target          element with properties to display
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @return <code>Window.OK</code> return code if the user click OK;
	 *         <code>Window.CANCEL</code> otherwise
	 */
	public int displayCreationProperties(EditingContextAdapter contextAdapter, EObject target,
			IVariableManager variableManager, IInterpreter interpreter) {
		return displayProperties(contextAdapter, target, contextAdapter.getEditingDomain().getResourceSet(), variableManager, interpreter,
				Messages.PropertiesDialogTitle_createAction);
	}

	/**
	 * Open properties popup to edit a given EObject.
	 * 
	 * @param contextAdapter  the adapter used to modify model elements
	 * @param target          element with properties to display
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @return <code>Window.OK</code> return code if the user click OK;
	 *         <code>Window.CANCEL</code> otherwise
	 */
	public int displayEditionProperties(EditingContextAdapter contextAdapter, EObject target,
			IVariableManager variableManager, IInterpreter interpreter) {
		return displayProperties(contextAdapter, target, target.eResource().getResourceSet(), variableManager, interpreter,
				Messages.PropertiesDialogTitle_editAction);
	}

	/**
	 * Open properties dialog with the given title to display all features of a
	 * given target.
	 * 
	 * @param contextAdapter  the adapter used to modify model elements
	 * @param target          element with properties to display
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @param acttionTitle    a part of the title of the dialog to display
	 * @return <code>Window.OK</code> return code if the user click OK;
	 *         <code>Window.CANCEL</code> otherwise
	 */
	public int displayProperties(EditingContextAdapter contextAdapter, EObject target, ResourceSet resourceSet, IVariableManager variableManager,
			IInterpreter interpreter, String actionTitle) {
		List<EEFPage> eefPages = this.getPagesToDisplay(contextAdapter, target, resourceSet,
				interpreter);
		if (!eefPages.isEmpty()) {
			Runnable runnable = () -> {
				Shell shell = Display.getCurrent().getActiveShell();
				PropertiesFormDialog formDialog = new PropertiesFormDialog(shell, target, interpreter, variableManager,
						eefPages);
				formDialog.setActionTitle(actionTitle);
				formDialog.create();
				formDialog.getShell().setMinimumSize(600, 200);

				open = formDialog.open();

				if (Window.OK != open) {
					throw new OperationCanceledException();
				}
			};
			boolean isInUiThread = Display.getCurrent() != null && Display.getCurrent().getActiveShell() != null;

			if (isInUiThread) {
				runnable.run();
			} else {
				Display.getDefault().syncExec(runnable);
			}
		} else {
			open = Window.OK;
		}
		return open;
	}

	/**
	 * Method used to get all property page to display for a given element.
	 * 
	 * @param contextAdapter link between editor and properties view,
	 * @param target         element with properties to display,
	 * @return list of properties pages to display.
	 */
	public List<EEFPage> getPagesToDisplay(EditingContextAdapter contextAdapter, EObject target,
			ResourceSet resourceSet, IInterpreter interpreter) {
		List<EEFPage> eefPages = new ArrayList<EEFPage>();

		List<Group> siriusGroup = getActiveSiriusGroup(resourceSet);
		final Map<String, List<AbstractOverrideDescription>> descriptionToOverride = initiateOverrideExtension(
				siriusGroup);
		for (Group group : siriusGroup) {
			Optional<ViewExtensionDescription> optionalDescription = group.getExtensions().stream()
					.filter(ViewExtensionDescription.class::isInstance).map(ViewExtensionDescription.class::cast)
					.findFirst();

			optionalDescription.ifPresent(viewExtensionDescription -> {
				SiriusInputDescriptor input = new SiriusInputDescriptor(target) {
					@Override
					public EObject getSemanticElement() {
						EObject result = super.getSemanticElement();
						if (result == null) {
							return target;
						}
						return result;
					}
				};
				IVariableManager variableManager = createVariableManagerFromDescriptor(input);
				ViewDescriptionPreprocessor preprocessor = new ViewDescriptionPreprocessor(viewExtensionDescription);
				OverridesProvider overridesProvider = new OverridesProvider(null) {
					@Override
					public List<AbstractOverrideDescription> getOverrideDescriptions(EObject eObject) {
						String name = null;
						if (eObject instanceof IdentifiedElement) {
							name = ((IdentifiedElement) eObject).getName();
						}
						List<AbstractOverrideDescription> overrides = descriptionToOverride.get(name);
						if (overrides == null) {
							return new ArrayList<>();
						} else {
							return overrides;
						}
					}
				};

				// @formatter:off
				Optional<ViewExtensionDescription> optionalViewExtensionDescription = preprocessor.convert(interpreter, variableManager, overridesProvider);
				EList<Category> categories = optionalViewExtensionDescription.map(ViewExtensionDescription::getCategories)
							.orElse(new BasicEList<>());
				List<PageDescription> pageDescriptions = categories.stream()
						.map(Category::getPages)
						.flatMap(Collection::stream)
						.collect(Collectors.toList());
				// @formatter:on

				EEFViewDescription eefViewDescription = new ViewDescriptionConverter(pageDescriptions).convert(input);
				Option<Session> optSession = input.getFullContext().getSession();
				EditingContextAdapter editingContextAdapter;
				if (optSession.some()) {
					editingContextAdapter = SiriusUIPropertiesPlugin.getPlugin().getEditingContextAdapter(optSession.get());
				} else {
					editingContextAdapter = contextAdapter;
				}
				EEFView createEEFView = new EEFViewFactory().createEEFView(eefViewDescription, variableManager,
						interpreter, editingContextAdapter, input);
				eefPages.addAll(createEEFView.getPages());
			});
		}

		return eefPages;
	}

	/**
	 * Create custom variable manager used by the interpreter.
	 * 
	 * @return new custom variable manager used by the interpreter.
	 */
	public IVariableManager createVariableManagerFromDescriptor(SiriusInputDescriptor input) {
		IVariableManager variableManager = new VariableManagerFactory().createVariableManager();
		variableManager.put(EEFExpressionUtils.SELF, input.getSemanticElement());
		variableManager.put(EEFExpressionUtils.INPUT, input);
		return variableManager;
	}

	/**
	 * Create a map which associate a description with its overrided description.
	 * 
	 * @param siriusGroup list of VSM groups
	 * 
	 * @return a map which associate a description with its overrided description.
	 */
	public Map<String, List<AbstractOverrideDescription>> initiateOverrideExtension(List<Group> siriusGroup) {
		Map<String, List<AbstractOverrideDescription>> descriptionToOverride = new HashMap<String, List<AbstractOverrideDescription>>();
		for (Group group : siriusGroup) {
			Optional<ViewExtensionDescription> optionalDescription = group.getExtensions().stream()
					.filter(ViewExtensionDescription.class::isInstance).map(ViewExtensionDescription.class::cast)
					.findFirst();
			optionalDescription.ifPresent(viewExtensionDescription -> {
				// @formatter:off
				List<AbstractOverrideDescription> overrides =  viewExtensionDescription.getCategories().stream()
						.map(Category::getOverrides)
						.flatMap(Collection::stream)
						.collect(Collectors.toList());
				// @formatter:on
				for (AbstractOverrideDescription override : overrides) {
					for (@SuppressWarnings("rawtypes")
					EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) override
							.eCrossReferences().iterator(); featureIterator.hasNext(); ) {
						featureIterator.next();
						if ("overrides".equals(featureIterator.feature().getName())) { //$NON-NLS-1$
							Object descriptionOverrided = override.eGet(featureIterator.feature());
							String descriptionName = ((IdentifiedElement) descriptionOverrided).getName();
							List<AbstractOverrideDescription> list = descriptionToOverride.get(descriptionName);
							if (list == null) {
								list = new ArrayList<AbstractOverrideDescription>();
								list.add(override);
								descriptionToOverride.put(descriptionName, list);
							} else {
								list.add(override);
							}
						}
					}
				}
			});
		}
		return descriptionToOverride;
	}

	/**
	 * Get list of VSM group apply on a given EObject.
	 * 
	 * @param target EObject with the properties to display
	 * @return list of VSM group apply on a given EObject.
	 */
	public List<Group> getActiveSiriusGroup(ResourceSet resourceSet) {
		if (activeSiriusGroup == null || activeSiriusGroup.isEmpty()) {
			activeSiriusGroup = new ArrayList<Group>();
			for (Resource resource : resourceSet.getResources()) {
				if (SiriusUtil.DESCRIPTION_MODEL_EXTENSION.equals(resource.getURI().fileExtension())) {
					activeSiriusGroup.addAll(resource.getContents().stream().filter(Group.class::isInstance)
							.map(Group.class::cast).collect(Collectors.toList()));
				}
			}
		}
		return activeSiriusGroup;
	}

	/**
	 * Used to get list of all possible types that can be created for a reference.
	 * 
	 * @param composedAdapterFactory the factory used to create adapters
	 * @param editingContextAdapter  the adapter used to modify model elements
	 * @param eObject                the owner of the reference
	 * @param eReference             the reference to manage for which an object can
	 *                               be created
	 * @return the list of all possible types that can be created for the specified
	 *         reference
	 */
	public List<Object> getAllPossibleTypes(ComposedAdapterFactory composedAdapterFactory,
			EditingContextAdapter editingContextAdapter, EObject eObject, EReference eReference) {
		List<Object> values = new ArrayList<>();
		Adapter adapter = composedAdapterFactory.adapt(eObject, IEditingDomainItemProvider.class);
		if (adapter instanceof IEditingDomainItemProvider) {
			IEditingDomainItemProvider itemProviderAdapter = (IEditingDomainItemProvider) adapter;
			Collection<?> newChildDescriptors = itemProviderAdapter.getNewChildDescriptors(eObject,
					editingContextAdapter.getEditingDomain(), null);
			for (Object newChildDescriptor : newChildDescriptors) {
				if (newChildDescriptor instanceof CommandParameter) {
					CommandParameter commandParameter = (CommandParameter) newChildDescriptor;
					Object value = commandParameter.getValue();
					if (commandParameter.getEReference().equals(eReference) && value instanceof EObject
							&& eReference.getEReferenceType().isSuperTypeOf(((EObject) value).eClass())) {
						values.add(commandParameter.getValue());
					}
				}
			}
		}
		return values;
	}
}
