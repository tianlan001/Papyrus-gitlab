/*****************************************************************************
 * Copyright (c) 2012, 2017, 2023 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 417095, 515806
 *  Ansgar Radermacher - bug 582492, removed unused javax.inject import
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.nattable.messages.messages"; //$NON-NLS-1$

	public static String CellManagerFactory_CellManagerNotFound;

	public static String ChooseCategoriesSelectionWidget_EditAlias;

	public static String ChooseCategoriesSelectionWidget_EnterAliasToUseForTheCategory;

	public static String CompositeAxisManager_DestroyAxisCommand;

	public static String AbstractAxisManager_InputDialogMessage;

	public static String AbstractAxisManager_InputDialogTitle;

	public static String AbstractCreateNattableEditorHandler_EnterTheNameForTheNewTable;

	public static String AbstractCreateNattableEditorHandler_PapyrusTableCreation;

	public static String AbstractPasteInSelectionNattableCommandProvider_AnExceptionOccuredDuringThePaste;

	public static String AbstractPasteInSelectionNattableCommandProvider_ElementCantBeAdded;

	public static String AbstractPasteInSelectionNattableCommandProvider_identifierNotFoundInSelection;

	public static String AbstractPasteInSelectionNattableCommandProvider_identifierNotFoundInSelectionSoTheObjectWasCreated;

	public static String AbstractPasteInSelectionNattableCommandProvider_MultipleSelectedRowsCorrespondingForIdentifier;

	public static String AbstractPasteInSelectionNattableCommandProvider_NonEditableCellTriedToBeOverwrited;

	public static String AbstractPasteInSelectionNattableCommandProvider_notyetimplemented;

	public static String AbstractPasteInSelectionNattableCommandProvider_readrowsexceedsexistingrows;

	public static String AbstractPasteInSelectionNattableCommandProvider_readcolumnsexceedsexistingcolumns;

	public static String AbstractPasteInSelectionNattableCommandProvider_TheAxisUsedAsIdentifierNotAvailable;

	public static String AbstractPasteInSelectionNattableCommandProvider_TheCommandCantBeExecuted;

	public static String AbstractPasteInSelectionNattableCommandProvider_ThePasteHasBeenDoneWithSomeProblems;

	public static String AbstractPasteInSelectionNattableCommandProvider_ThePasteHasNotBeenDoneBecauseOfSomeProblems;

	public static String AbstractPasteImportInNattableManager_PasteColumnsError;

	public static String AbstractPasteImportInNattableManager_PasteNotYetManaged;

	public static String AbstractPasteImportInNattableManager_PasteRowsError;

	public static String AbstractPasteImportInsertInNattableManager_column;

	public static String AbstractPasteImportInsertInNattableManager_EnablementStatusForPasteInTheTable;

	public static String AbstractPasteImportInsertInNattableManager_INVERTED_MESSAGE;

	public static String AbstractPasteImportInsertInNattableManager_NoPasteConfiguration;

	public static String AbstractPasteImportInsertInNattableManager_NoPasteConfiguration2;

	public static String AbstractPasteImportInsertInNattableManager_NoPasteConfigurationNeeded;

	public static String AbstractPasteImportInsertInNattableManager_NumberOfColumnsAreEquals;

	public static String AbstractPasteImportInsertInNattableManager_NumberOfColumnsAreNotEquals;

	public static String AbstractPasteImportInsertInNattableManager_row;

	public static String AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasBeenSet;

	public static String AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasNotBeenSet;

	public static String AbstractPasteImportInsertInNattableManager_PasteConfigurationFeatureHasNotBeenSet;

	public static String AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasNotTheContainmentFeatureDefinedForThePaste;

	public static String AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasTheContainmentFeatureDefinedForThePaste;

	public static String AbstractPasteImportInsertInNattableManager_TheElementTypeIsUnknown;

	public static String AbstractPasteImportInsertInNattableManager_ThereIsNoElementIdDefinedInThePasteConfiguration;

	public static String AbstractPasteImportInsertInNattableManager_ThereIsNoPasteConfgurationForATreeFillingConfiguration;

	public static String AbstractPasteImportInsertInNattableManager_TheTableCanCreateElement;

	public static String AbstractPasteImportInsertInNattableManager_TheTableCantCreateElement;

	public static String AbstractPasteImportInsertInNattableManager_TheTableDoesntHaveColumns;

	public static String AbstractPasteImportInsertInNattableManager_TheTableHasAConfigurationToPaste;

	public static String AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteColumns;

	public static String AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteRows;

	public static String AbstractPasteImportInsertInNattableManager_YouHaveMoreThan1Category;

	public static String AbstractPasteImportInsertInNattableManager_WhatAreYouPasting;

	public static String AbstractInsertImportInNattableManager_MoreThanOnePasteConfigurationByDepth;

	public static String AbstractSaveCurrentAxisProvidersHandler_0;

	public static String AbstractSaveCurrentAxisProvidersHandler_1;

	public static String AbstractSaveCurrentAxisProvidersHandler_2;

	public static String AbstractSaveCurrentAxisProvidersHandler_4;

	public static String AbstractSaveCurrentAxisProvidersHandler_5;

	public static String AbstractTableHandler_CurrentEditorCantBeFound;

	public static String AxisManagerFactory_AxisManagerClassCantBeLoaded;

	public static String AxisManagerFactory_TheClassCantBeInstanciated;

	public static String CompositeAxisManager_AddAxisCommand;

	public static String ConfigurePastePage_firstColumnTitle;

	public static String ConfigurePastePage_secondColumnTitle;

	public static String ConfigurePastePage_thirdColumnTitle;

	public static String ConfigurePastePage_fourthColumnTitle;

	public static String ConfigureTableCategoriesWizard_ConfigureCategoriesAndPaste;

	public static String ConfigureTableCategoriesWizard_Depth;

	public static String ConfigureTableCategoriesWizard_DepthFilledByUser;

	/**
	 * @since 3.0
	 */
	public static String ContextFeatureContentProvider_CaseSensitiveLabel;

	/**
	 * @since 3.0
	 */
	public static String ContextFeatureContentProvider_CaseSensitiveTooltip;

	/**
	 * @since 3.0
	 */
	public static String ContextFeatureContentProvider_FilterTooltip;

	public static String DeleteNatTableContextAdvice_DestroyNattableCommand;

	public static String EditConfiguration_ConfigurationFactoryNotFound;

	public static String EditConfiguration_ConfigurationNotFound;

	public static String EditConfiguration_DeclarationNotYetSupported;

	public static String EditConfiguration_FactoryHandlesElementButDoesntProvideEditor;

	public static String EObjectManager_AddAxisElement;

	public static String InsertInNattableManager_InsertColumnsError;

	public static String InsertInNattableManager_InsertNotYetManaged;

	public static String InsertInNattableManager_InsertRowsError;

	public static String NattableConfigurationRegistry_ConfigurationNotFound;

	public static String NattableConfigurationRegistry_NoTesterForThisConfiguration;

	public static String NattableConfigurationRegistry_NoTypeForAConfiguration;

	public static String NattableConfigurationRegistry_ResourceEmpty;

	public static String NattableConfigurationRegistry_SeveralConfigurationsWithTheSameType;

	public static String NattableConfigurationRegistry_TableConfigurationNotFound;

	public static String NattableConfigurationRegistry_TesterNotFound;

	public static String NattableConfigurationRegistry_TesterNotManager;

	public static String NattableModelManager_ActionNotYetSupported;

	public static String NattableModelManager_AddColumnCommand;

	public static String NattableModelManager_AddRowCommand;

	public static String NattableModelManager_AtLeastOfOneTheAxisManagerMustBeAMaster;

	public static String NattableModelManager_CreateDestroyAxis;

	public static String NattableModelManager_DisableTheAutomaticAdditionOfColumnsWhenARowIsAdded;

	public static String NattableModelManager_DisableTheAutomaticAdditionOfRowsWhenAColumnIsAdded;

	public static String NattableModelManager_DisconnectAxisManagerCheckBoxMessage;

	public static String NattableModelManager_DisconnectAxisManagerCheckBoxTooltip;

	public static String NattableModelManager_DisconnectAxisManagerInformationDialogMessage;

	public static String NattableModelManager_DisconnectAxisManagerInformationDialogTitle;

	public static String NattableModelManager_DisconnectColumnAxisManager;

	public static String NattableModelManager_DisconnectColumnAxisManagerMessageInInformationDialog;

	public static String NattableModelManager_DisconnectThisAxisManager;

	public static String NattableModelManager_EditingDomainNotFound;

	public static String NattableModelManager_SelectColumns;

	public static String NattableModelManager_SelectRows;

	public static String NattableModelManager_ServiceRegistryNotFound;

	public static String NattableModelManager_SwitchLinesAndColumns;

	public static String NattableModelManager_TheCheckBoxHasNotBeenCheckedToAvoidAutomaticColumnAddition;

	public static String NattableModelManager_TheCheckBoxHasNotBeenCheckedToAvoidAutomaticRowAddition;

	public static String PrintTableHandler_PrintCantBeDone;

	public static String PrintTableHandler_TablePrint;

	public static String ProblemLabelProvider_StringsValuesCanBeResolved;

	public static String TableLabelProvider_ErrorGettingIconForTable;

	public static String TableTesterRegistry_SeveralTesterAreRegisteredWithTheSameId;

	public static String TableTesterRegistry_TheClassCantBeLoaded;

	public static String TextDelimiter_DoubleQuote;

	public static String TextDelimiter_Quote;

	public static String ICellManager_NotAvailable;

	public static String ImportCSVConfigurationPage_SelectTheTextDelimiter;

	public static String ImportCSVConfigurationPage_Separators;

	public static String ImportCSVConfigurationPage_TheCellSeparatorIsNotDefined;

	public static String ImportCSVConfigurationPage_TheCellSeparatorMustBeExcatlyOneChar;

	public static String ImportFilePage_BeSureThatYourImportFileHasTheSameNumberOfColumns;

	public static String ImportFilePage_SelectTheFileToImport;

	public static String ImportFilePage_TheImportedElementWillBeAddedTo;

	public static String ImportFilePage_YourFileWillBeImported;

	public static String ImportTableErrorPage_PleaseOpenAPapyrusEditor;

	public static String ImportTableWizard_ConfigureImport;

	public static String ImportTableWizard_ImportTable;

	public static String ImportTableWizard_ImportTableError;

	public static String ImportTableWizard_ImportTableFromFileInPapyrusModel;

	public static String AbstractUMLTableEFacetEditor_UMLTableCreationErrorMessage;

	public static String ColumnsToShowDialog_AdditionalFeatures;

	public static String ColumnsToShowDialog_AdvancedMode;

	public static String ColumnsToShowDialog_default_columns;

	public static String ColumnsToShowDialog_DeselectAll;

	public static String ColumnsToShowDialog_DeselectAllAvailableAdditionalFeatures;

	public static String ColumnsToShowDialog_DeselecteAllAvailableFeatures;

	public static String ColumnsToShowDialog_DeselectedAll;

	public static String ColumnsToShowDialog_DirectFeatures;

	public static String ColumnsToShowDialog_SelecColumnsToShow;

	public static String ColumnsToShowDialog_SelectAll;

	public static String ColumnsToShowDialog_SelectAllAvailableAdditionalFeatures;

	public static String ColumnsToShowDialog_SelectAllAvailablesFeatures;

	public static String ColumnsToShowDialog_ShowAllPossibilities;

	public static String LoadCurrentAxisProvidersDialog_0;

	public static String LoadCurrentAxisProvidersDialog_1;

	public static String LoadCurrentAxisProvidersDialog_2;

	public static String LoadCurrentAxisProvidersDialog_3;

	public static String PapyrusFillHandleDragMode_CopyCommandName;

	public static String PapyrusFillHandleDragMode_DecrementCommandName;

	public static String PapyrusFillHandleDragMode_DecrementPrefixCommandName;

	public static String PapyrusFillHandleDragMode_DecrementSuffixCommandName;

	public static String PapyrusFillHandleDragMode_IncrementCommandName;

	public static String PapyrusFillHandleDragMode_IncrementPrefixCommandName;

	public static String PapyrusFillHandleDragMode_IncrementSuffixCommandName;

	public static String PapyrusPopupMenuAction_ShowCategoriesOnDepth;

	public static String PasteConfigurationUtils_ContainementFeatureIsNotAReference;

	public static String PasteConfigurationUtils_ContainmentFeatureIsNotAReferenceContainment;

	public static String PasteConfigurationUtils_ContainmentFeatureIsNull;

	public static String PasteConfigurationUtils_CreatesElementsAreNotCompatibleWithContainmentFeature;

	public static String PasteConfigurationUtils_ElementIdNotDefined;

	public static String PasteConfigurationUtils_ElementTypeCantBeFound;

	public static String PasteConfigurationUtils_PasteConfigurationIsConsistent;

	public static String PasteEObjectAxisInTableCommandProvider_AddingElementToTheTable;

	public static String PasteEObjectAxisInTableCommandProvider_AddRowsCommandName;

	public static String PasteEObjectAxisInTableCommandProvider_CommandCreationHasBeenCancelled;

	public static String PasteEObjectAxisInTableCommandProvider_CreatingAnumberXonY;

	public static String PasteEObjectAxisInTableCommandProvider_DoingAdditionalActions;

	public static String PasteEObjectAxisInTableCommandProvider_FinishingThePaste;

	public static String PasteEObjectAxisInTableCommandProvider_LinkingReferencesToTheModel;

	public static String PasteEObjectAxisInTableCommandProvider_PasteAction;

	public static String PasteEObjectAxisInTableCommandProvider_PasteColumns;

	public static String PasteEObjectAxisInTableCommandProvider_PasteFromStringCommand;

	public static String PasteEObjectAxisInTableCommandProvider_PasteInTableCommandName;

	public static String PasteEObjectAxisInTableCommandProvider_PasteRows;

	public static String PasteEObjectTreeAxisInNatTableCommandProvider_CantPasteColumnsInTreeTable;

	public static String PasteImportHandler_EmptyClipboardString;

	public static String PasteImportStatusDialog_ImportPasteDialogTitle;

	public static String PasteImportStatusDialog_PasteConfigurationMessage;

	public static String PasteInPapyrusTableCommandProvider_0;

	public static String PasteInPapyrusTableCommandProvider_1;

	public static String PasteInPapyrusTableCommandProvider_2;

	public static String PasteInPapyrusTableCommandProvider_3;

	public static String PasteInPapyrusTableCommandProvider_Pasting;

	public static String PasteInPapyrusTableCommandProvider_PastingInTable;

	public static String PasteInPapyrusTableCommandProvider_ProblemsToSetPropertyValue;

	public static String PasteInPapyrusTableCommandProvider_ProblemToApplyStereotype;

	public static String PasteInPapyrusTableCommandProvider_ProblemToSetStereotypeValue;

	public static String PasteInPapyrusTableCommandProvider_RequiredStereotypeCantBeFound;

	public static String PasteInPapyrusTableCommandProvider_RequiredStereotypeNotApplied;

	public static String PasteInPapyrusTableCommandProvider_StereotypePropertyCantBeResolved;

	public static String PasteInPapyrusTableCommandProvider_TheEnumerationLiteralCantBeFound;

	public static String PasteInPapyrusTableCommandProvider_TheStereotypeCantBeApplied;

	public static String PasteInPapyrusTableCommandProvider_TheTextCantBeMappedOnAnExistingElement;

	public static String PasteInTableHandler_PasteCancelled;

	public static String PasteInTableHandler_PasteCreation;

	public static String PasteInTableHandler_PasteError;

	public static String PasteInTableHandler_PasteInformation;

	public static String PasteInTableHandler_PasteWarning;

	public static String PasteInTableHandler_ThePasteCommandCantBeExecuted;

	public static String PasteSeparator_Comma;

	public static String PasteSeparator_Other;

	public static String PasteSeparator_Semicolon;

	public static String PasteSeparator_Space;

	public static String PasteSeparator_Tabulation;

	public static String SelectCategoriesWizardPage_SelectCategoriesPage;

	public static String SelectCategoriesWizardPage_SelectCategoriesToListenInTheTreeTable;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_ExportTableDialogTitle;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_SelectOutputDirLabel;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_ImageNameLabel;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_BrowseFileSystem;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_BrowseWorkSpace;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_SelectOutputFormatLabel;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_ContainerSelectionDialogTitle;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_SelectTreeActionLabel;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_OverrideConfirmMessasgeDialogMessage;

	/**
	 * @since 3.0
	 */
	public static String ExportTableDialog_OverrideConfirmMessasgeDialogText;

	public static String MatrixRelationshipDirectionLabelProvider_None;

	public static String MatrixRelationshipDirectionLabelProvider_RowToColumn;

	public static String MatrixRelationshipDirectionLabelProvider_ColumnToRow;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_ColumAsOwner;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_ColumnsOwnerAsOwner;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_Default;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_Other;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_RowAsOwner;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_RowsOwnerAsOwner;

	/**
	 * @since 6.0
	 */
	public static String MatrixRelationshipOwnerStrategyLabelProvider_TableRootElement;

	/**
	 * @since 4.0
	 */
	public static String CellPreferenceInitializer_NotAvailable;

	/**
	 * @since 4.0
	 */
	public static String CellPreferencePage_UnsupportedColumnCellLabel;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
