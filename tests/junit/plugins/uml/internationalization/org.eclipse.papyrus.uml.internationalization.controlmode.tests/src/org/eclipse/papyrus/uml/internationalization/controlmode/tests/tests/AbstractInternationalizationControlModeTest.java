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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.controlmode.tests.tests;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.ParameterValuesException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.controlmode.commands.ControlModeCommandParameterValues;
import org.eclipse.papyrus.infra.services.controlmode.handler.ControlCommandHandler;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.internationalization.tests.tests.AbstractUMLInternationalizationTest;
import org.eclipse.papyrus.views.modelexplorer.DecoratingLabelProviderWTooltips;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;

/**
 * The abstract class for the internationalization control mode tests.
 */
@SuppressWarnings("nls")
public abstract class AbstractInternationalizationControlModeTest extends AbstractUMLInternationalizationTest {

	/** The existing diagram. */
	protected Diagram diagram;

	/** The existing table. */
	protected Table table;

	/**
	 * Constructor.
	 */
	public AbstractInternationalizationControlModeTest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.internationalization.tests.tests.AbstractUMLInternationalizationTest#initTest()
	 */
	@Override
	public void initTest() throws Exception {
		fixture.flushDisplayEvents();

		model = (Model) fixture.getModel();
		Assert.assertNotNull("The model cannot be null", model);

		labelProvider = (DecoratingLabelProviderWTooltips) fixture.getModelExplorerView().getCommonViewer()
				.getLabelProvider();

		modelPackage = (Package) model.getOwnedMember(PACKAGE_NAME);
		Assert.assertNotNull("The package cannot be null", modelPackage);
		Assert.assertEquals("Package is not the one Expected", PACKAGE_NAME, modelPackage.getName());

		modelClass = (Class) modelPackage.getOwnedMember(CLASS_NAME);
		Assert.assertNotNull("The class cannot be null", modelClass);
		Assert.assertEquals("Class is not the one Expected", CLASS_NAME, modelClass.getName());

		modelOperation = (Operation) modelClass.getOwnedMember(OPERATION_NAME);
		Assert.assertNotNull("The operation cannot be null", modelOperation);
		Assert.assertEquals("Operation is not the one Expected", OPERATION_NAME, modelOperation.getName());

		modelParameter = (Parameter) modelOperation.getOwnedMember(PARAMETER_NAME);
		Assert.assertNotNull("The parameter cannot be null", modelParameter);
		Assert.assertEquals("Parameter is not the one Expected", PARAMETER_NAME, modelParameter.getName());

		modelProperty = (Property) modelClass.getOwnedMember(PROPERTY_NAME);
		Assert.assertNotNull("The property cannot be null", modelProperty);
		Assert.assertEquals("Property is not the one Expected", PROPERTY_NAME, modelProperty.getName());

		modelInterface = (Interface) modelPackage.getOwnedMember(INTERFACE_NAME);
		Assert.assertNotNull("The interface cannot be null", modelInterface);
		Assert.assertEquals("Interface is not the one Expected", INTERFACE_NAME, modelInterface.getName());

		modelEnumeration = (Enumeration) modelInterface.getOwnedMember(ENUMERATION_NAME);
		Assert.assertNotNull("The enumeration cannot be null", modelEnumeration);
		Assert.assertEquals("Enumeration is not the one Expected", ENUMERATION_NAME, modelEnumeration.getName());

		for (Object object : fixture.getPageManager().allPages()) {
			if (object instanceof Table) {
				table = (Table) object;
			} else if (object instanceof Diagram) {
				diagram = (Diagram) object;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.internationalization.tests.tests.AbstractUMLInternationalizationTest#checkNoLabels()
	 */
	@Override
	public void checkNoLabels() throws Exception {
		super.checkNoLabels();

		Assert.assertEquals("The root element label is not the expected one.", "Diagram ClassDiagram",
				labelProvider.getText(diagram));

		Assert.assertEquals("The class label is not the expected one.", "Table GenericTable0",
				labelProvider.getText(table));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.internationalization.tests.tests.AbstractUMLInternationalizationTest#checkFrenchLabels()
	 */
	@Override
	public void checkFrenchLabels() throws Exception {
		super.checkFrenchLabels();

		Assert.assertEquals("The diagram label is not the expected one.", "Diagram MonDiagrammeDeClasse",
				labelProvider.getText(diagram));

		Assert.assertEquals("The table label is not the expected one.", "Table MaTableGenerique",
				labelProvider.getText(table));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.internationalization.tests.tests.AbstractUMLInternationalizationTest#checkEnglishLabels()
	 */
	@Override
	public void checkEnglishLabels() throws Exception {
		super.checkEnglishLabels();

		Assert.assertEquals("The root element label is not the expected one.", "Diagram MyClassDiagram",
				labelProvider.getText(diagram));

		Assert.assertEquals("The class label is not the expected one.", "Table MyGenericTable",
				labelProvider.getText(table));
	}

	/**
	 * This allows to create the submodel for the element in parameter with the
	 * name of the fragment.
	 * 
	 * @param model
	 *            The UML model.
	 * @param element
	 *            The element to control.
	 * @param fixture
	 *            The fixture managed.
	 * @return The resource created.
	 * @throws Exception
	 *             The exception can be caught by the control operation.
	 */
	protected void control(final Model model, final PackageableElement element, final PapyrusEditorFixture fixture)
			throws Exception {
		desactivateDialog();
		final ControlModeAssertion controlAssertion = new ControlModeAssertion((Model) model,
				fixture.getModelExplorerView()) {

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) fixture.getResourceSet()).getAssociatedResource(controlElement,
						DiModel.DI_FILE_EXTENSION, false);
				assertNotNull(resource);
			}

			@Override
			protected Iterable<? extends PackageableElement> getElementsToSelectForControl() {
				HashSet<PackageableElement> hashSet = new HashSet<PackageableElement>(1);
				hashSet.add(element);
				return hashSet;
			}

			@Override
			protected void save() {
				fixture.saveAll();
			}
		};
		controlAssertion.testControl();
	}

	/**
	 * This allows to desactivate the control mode dialog.
	 * 
	 * @throws NotDefinedException
	 * @throws ParameterValuesException
	 */
	protected void desactivateDialog() throws NotDefinedException, ParameterValuesException {
		IParameter dialogParameter = HandlerUtils.getCommand(ControlModeAssertion.COMMAND_ID)
				.getParameter(ControlCommandHandler.CONTROLMODE_USE_DIALOG_PARAMETER);
		ControlModeCommandParameterValues controlModePlatformValues = (ControlModeCommandParameterValues) dialogParameter
				.getValues();
		controlModePlatformValues.put("showDialog", false);
	}

	/**
	 * This allows to execute a GMF command.
	 * 
	 * @param command
	 *            The GMF command to execute.
	 */
	protected void execute(final ICommand command) {
		fixture.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
	}

	/**
	 * This allows to undo the last command done.
	 */
	protected void undo() {
		fixture.getEditingDomain().getCommandStack().undo();
	}

	/**
	 * This allows to redo the last command undoable.
	 */
	protected void redo() {
		fixture.getEditingDomain().getCommandStack().redo();
	}

}
