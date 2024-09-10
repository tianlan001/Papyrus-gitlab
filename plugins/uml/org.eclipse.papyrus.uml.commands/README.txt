/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

This plugin is created to regroup actions which can be used in : 
   - oep.uml.modelexplorer
   - oep.%DSML%.modelexplorer
   
for example, the handler RenameNamedElementHandler works with UML, 
but if we choose distribute a Papyrus for DSML version, 
we can exclude oep.uml.modelexplorer, nevertheless, we need to Rename UML Element.

So this plugin was created to avoid this kind of problem.