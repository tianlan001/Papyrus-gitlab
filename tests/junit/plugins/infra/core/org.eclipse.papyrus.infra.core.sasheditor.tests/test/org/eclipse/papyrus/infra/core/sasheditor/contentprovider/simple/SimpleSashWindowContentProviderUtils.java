/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple;

import java.util.Map;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.editor.MessagePartModel;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.NotFoundException;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.Page;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.PagesModelException;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.PanelTerm;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.WindowTerm;
import org.eclipse.papyrus.infra.core.sasheditor.tests.utils.ISashWindowsContentProviderTestUtils;

/**
 * Class providing some utilities to check the {@link SimpleSashWindowsContentProvider} class.
 * <br>
 * This utilities allow to: populate a contentProvider, compare the contentProvider structure
 * query the contentProvider structure.
 * 
 * @author cedric dumoulin
 *
 */
public class SimpleSashWindowContentProviderUtils implements ISashWindowsContentProviderTestUtils {

	/**
	 * ContentProvider used .
	 */
	protected SimpleSashWindowsContentProvider contentProvider;
	
	/**
	 * Constructor.
	 * Create a default internal {@link SimpleSashWindowsContentProvider};
	 * @param modelMngr
	 */
	public SimpleSashWindowContentProviderUtils() {
		this.contentProvider = new SimpleSashWindowsContentProvider();
	}

	/**
	 * Constructor.
	 *
	 * @param modelMngr
	 */
	public SimpleSashWindowContentProviderUtils(SimpleSashWindowsContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}


	
	/**
	 * @return the contentProvider
	 */
	public ISashWindowsContentProvider getIContentProvider() {
		return contentProvider;
	}


	/**
	 * Check if the sashModel is conformed to the specified query.
	 * @param query
	 * @throws QueryException 
	 */
	public void assertConform(IModelExp query) throws PagesModelException {
		
		Object first;
		if( query instanceof WindowTerm) {
			first = getFirstWindowModel();
		}
		else if( query instanceof PanelTerm) {
			first = getFirstPanelModel();
		}
		else {
			throw new PagesModelException("Don't know how to get the model associated to the first term of the expression (" + query.toString() + ")");
		}
		
		CheckVisitor visitor = new CheckVisitor();
		query.accept(visitor, first);
	}

	/**
	 * Create an internal model conform to the specified query.
	 * Any previous model is disguarded.
	 * 
	 * This method should only be called once on a {@link SimpleSashWindowsContentProvider}.
	 * 
	 * @param query Should be a subtype PanelTerm (Folder, HSash, VSash)
	 * @throws QueryException 
	 * 
	 * TODO Ensure that the method can only be called once. For example,
	 * let this class extends {@link SimpleSashWindowsContentProvider}, and the constructor
	 * call this method.
	 */
	public void createModel(IModelExp query) throws PagesModelException {
		
		Object first ;
		if( query instanceof PanelTerm) {
			first = getFirstWindowModel();
		}
		else {
			throw new PagesModelException("Don't know how to get the model associated to the first term of the expression (" + query.toString() + ")");
		}
		
		// Create a surrounding WindowTerm
		WindowTerm windowTerm = new WindowTerm((PanelTerm)query);
		
		CreateModelInSimpleContentProviderVisitor visitor = new CreateModelInSimpleContentProviderVisitor(contentProvider);
		windowTerm.accept(visitor, first);
	}

	/**
	 * Create a new page (i.e. its model) in the associated contentProvider.
	 * 
	 * @param page The page name to create.
	 * @return The model of the created page
	 * @throws PagesModelException
	 */
	public IPageModel createNewPage(Page page) throws PagesModelException {

		IPageModel newPageModel = new MessagePartModel( page.getName(), page.getName());
		contentProvider.addPage(newPageModel);	
		return newPageModel;
	}

	/**
	 * Create a new page (i.e. its model) in the associated contentProvider.
	 * 
	 * The new page model is created in the currently selected folder ({@link SimpleSashWindowsContentProvider#getCurrentTabFolder()}.
	 * @param page The page name to create.
	 * @return The model of the created page
	 * @throws PagesModelException
	 */
	public IPageModel createNewPage(String pageName) throws PagesModelException {

		IPageModel newPageModel = new MessagePartModel( pageName, pageName);
		contentProvider.addPage(newPageModel);	
		return newPageModel;
	}

	/**
	 * Remove the specified Page model if it exist.
	 * The {@link IPageModel} to remove is lookked up by its page name.
	 * 
	 * @param pageName Name of the page to remove.
	 * 
	 * @throws PagesModelException
	 */
	public void removePage( String pageName) throws PagesModelException {

		IPageModel page = lookupPageByName(pageName);
			
		contentProvider.removePage(page);
	}
	
	/**
	 * Get element in the model.
	 * Each model element whose corresponding model query part has a name is added to the result map.
	 * The element is then accessible with the name set in the query part.
	 * 
	 * @param query
	 * @return
	 * @throws QueryException
	 */
	public Map<String, Object> queryModel(IModelExp query) throws PagesModelException {
		
		Object first;
		if( query instanceof WindowTerm) {
			first = getFirstWindowModel();
		}
		else if( query instanceof PanelTerm) {
			first = getFirstPanelModel();
		}
		else {
			throw new PagesModelException("Don't know how to get the model associated to the first term of the expression (" + query.toString() + ")");
		}
		
		QueryVisitor visitor = new QueryVisitor();
		query.accept(visitor, first);
		
		return visitor.getResult();
	}

	/**
	 * Lookup a page by its name.
	 * Only check elements of type {@link MessagePartModel} .
	 * 
	 * @param modelName
	 * @return
	 * @throws NotFoundException
	 */
	public IPageModel lookupPageByName( String modelName ) throws NotFoundException {
		if( modelName == null) {
			throw new NotFoundException("Null name not allowed.");
		}
		
		AbstractModel root = getFirstPanelModel();
		Object result = lookupModelByName(modelName, root, IPageModel.class);
		if( result == null) {
			throw new NotFoundException(modelName);
		}
		return (IPageModel)result;
	}
	
	/**
	 * Get a model element by its name.
	 * Only check elements of type {@link MessagePartModel} and {@link TabFolderModelWithName}.
	 * 
	 * @param modelName
	 * @return
	 * @throws NotFoundException
	 */
	public Object lookupModelByName( String modelName ) throws NotFoundException {
		AbstractModel root = getFirstPanelModel();
		Object result = lookupModelByName(modelName, root, Object.class);
		if( result == null) {
			throw new NotFoundException(modelName);
		}
		return result;
	}
	
	/**
	 * @param modelName
	 * @param from
	 * @return
	 */
	private Object lookupModelByName(String modelName, Object from, Class expectedType) {
		if( from instanceof TabFolderModel) {
			TabFolderModel tabFolderModel = (TabFolderModel)from;
			return lookupModelByName( modelName, tabFolderModel, expectedType);
		}
		else {
			// This is a sash
			SashPanelModel sashPanelModel = (SashPanelModel)from;
			return lookupModelByName( modelName, sashPanelModel, expectedType);
		}
	}

	/**
	 * @param modelName
	 * @param sashPanelModel
	 * @return
	 */
	private Object lookupModelByName(String modelName, SashPanelModel sashPanelModel, Class expectedType) {

		for( Object model : sashPanelModel.getChildren()) {
			Object result = lookupModelByName( modelName, model, expectedType);
			if(result != null) {
				return result;
			}
		}

		// not found
		return null;
	}

	/**
	 * @param modelName
	 * @param tabFolderModel
	 * @return found model, or null
	 */
	private Object lookupModelByName(String modelName, TabFolderModel tabFolderModel, Class expectedType) {
		

		// First check the folder name
		if( expectedType.isInstance(tabFolderModel) && tabFolderModel instanceof TabFolderModelWithName ) {
			TabFolderModelWithName tabFolderModelWithName = (TabFolderModelWithName)tabFolderModel;
			if(modelName.equals(tabFolderModelWithName.getName()) ) {
				return tabFolderModel;
			}
		}
		// Lookup in each tabs in folder
		for( IPageModel cur : tabFolderModel.getChildren()) {
			// Check type and name
			if( expectedType.isInstance(cur) && cur instanceof MessagePartModel ) {
				MessagePartModel messagePartModel = (MessagePartModel)cur;
				if(modelName.equals(messagePartModel.getTabTitle()) ) {
					return messagePartModel;
				}
			}
			
		}
		// Not found
		return null;
	}

	/**
	 * Get the model of the first the first window (in actual implementation their is only one window).
	 * @return
	 */
	private RootModel getFirstWindowModel() {
		// Silly method to get the RootModel
		return (RootModel)((AbstractPanelModel)contentProvider.getRootModel()).getParent();
	}

	/**
	 * Get the panel of the first window (in actual implementation their is only one window).
	 * @return
	 */
	private AbstractPanelModel getFirstPanelModel() {
		// In this implementation, the root is always of type AbstractPanelModel
		return (AbstractPanelModel)contentProvider.getRootModel();
	}

	/**
	 * @param string
	 */
	public void setActivePage(String string) {
		
		
	}
	
}
