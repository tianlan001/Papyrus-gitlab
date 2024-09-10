/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.ListIterator;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

/**
 * This allow to manage th execution of command if not already executed.
 */
public class ExtendedCompoundCommand extends CompoundCommand {

	public ExtendedCompoundCommand(String label){
	    super(label);
	  }
	
	/**
	 * @see org.eclipse.emf.common.command.CompoundCommand#execute()
	 *
	 */
	@Override
	public void execute() {
		for (ListIterator<Command> commands = commandList.listIterator(); commands.hasNext(); ) 
	    {
	      try
	      {
	        Command command = commands.next();
	        if(!(command instanceof GMFtoEMFCommandWrapper)){
	        	command.execute();
	        }else if(null == ((GMFtoEMFCommandWrapper)command).getGMFCommand().getCommandResult()){
	        	command.execute();
	        }
	      }
	      catch (RuntimeException exception)
	      {
	        // Skip over the command that threw the exception.
	        //
	        commands.previous();

	        try
	        {
	          // Iterate back over the executed commands to undo them.
	          //
	          while (commands.hasPrevious())
	          {
	            Command command = commands.previous();
	            if (command.canUndo())
	            {
	              command.undo();
	            }
	            else
	            {
	              break;
	            }
	          }
	        }
	        catch (RuntimeException nestedException)
	        {
	          CommonPlugin.INSTANCE.log
	            (new WrappedException
	              (CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), nestedException).fillInStackTrace());
	        }

	        throw exception;
	      }
	    }
	}
	
}
