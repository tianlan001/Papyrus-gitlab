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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.export.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.FileOutputStreamProvider;
import org.eclipse.nebula.widgets.nattable.export.ILayerExporter;
import org.eclipse.nebula.widgets.nattable.export.IOutputStreamProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is used to export a NatTable to a simple file.
 * 
 * @since 2.0
 */
public class PapyrusFileExporter implements ILayerExporter {

	/**
	 * The IOutputStreamProvider that is used to create new OutputStreams on
	 * beginning new export operations.
	 */
	private final IOutputStreamProvider outputStreamProvider;
	
	/**
	 * The file name if the output stream provider is not used.
	 */
	private final String fileName;
	
	/**
	 * Creates a new ExcelExporter using a FileOutputStreamProvider with default
	 * values.
	 */
	public PapyrusFileExporter() {
		this(new FileOutputStreamProvider("table_export.txt", //$NON-NLS-1$
				new String[] { "Text (*.txt)" }, new String[] { "*.txt" })); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Constructor.
	 *
	 * @param fileName The file name if the output stream provider is not used.
	 */
	public PapyrusFileExporter(final String fileName) {
		this(null, fileName);
	}
	
	/**
	 * Creates a new ExcelExporter that uses the given IOutputStreamProvider for
	 * retrieving the OutputStream to write the export to.
	 *
	 * @param outputStreamProvider
	 *            The IOutputStreamProvider that is used to retrieve the
	 *            OutputStream to write the export to.
	 */
	public PapyrusFileExporter(final IOutputStreamProvider outputStreamProvider) {
		this(outputStreamProvider, null);
	}

	/**
	 * Creates a new ExcelExporter that uses the given IOutputStreamProvider for
	 * retrieving the OutputStream to write the export to.
	 *
	 * @param outputStreamProvider
	 *            The IOutputStreamProvider that is used to retrieve the
	 *            OutputStream to write the export to.
	 * @param fileName The file name if the output stream provider is not used.
	 */
	private PapyrusFileExporter(final IOutputStreamProvider outputStreamProvider, final String fileName) {
		this.outputStreamProvider = outputStreamProvider;
		this.fileName = fileName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#getOutputStream(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public OutputStream getOutputStream(final Shell shell) {
		OutputStream result = null;
		if(null != this.fileName){
			try {
				result = new PrintStream(this.fileName);
			} catch (final FileNotFoundException e) {
				Activator.log.error(e);
			}
		}else{
			result = this.outputStreamProvider.getOutputStream(shell);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportBegin(java.io.OutputStream)
	 */
	@Override
	public void exportBegin(final OutputStream outputStream) throws IOException {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportEnd(java.io.OutputStream)
	 */
	@Override
	public void exportEnd(final OutputStream outputStream) throws IOException {
		outputStream.flush();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportLayerBegin(java.io.OutputStream, java.lang.String)
	 */
	@Override
	public void exportLayerBegin(final OutputStream outputStream, final String layerName) throws IOException {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportLayerEnd(java.io.OutputStream, java.lang.String)
	 */
	@Override
	public void exportLayerEnd(final OutputStream outputStream, final String layerName) throws IOException {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportRowBegin(java.io.OutputStream, int)
	 */
	@Override
	public void exportRowBegin(final OutputStream outputStream, final int rowPosition) throws IOException {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportRowEnd(java.io.OutputStream, int)
	 */
	@Override
	public void exportRowEnd(final OutputStream outputStream, final int rowPosition) throws IOException {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#exportCell(java.io.OutputStream, java.lang.Object, org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	public void exportCell(final OutputStream outputStream,
			final Object exportDisplayValue, final ILayerCell cell,
			final IConfigRegistry configRegistry) throws IOException {
		
		outputStream.write(asBytes(exportDisplayValue != null ? exportDisplayValue.toString() : "")); //$NON-NLS-1$
		
		// add a tab if this is not the last cell
		if(cell.getColumnPosition() < cell.getLayer().getColumnCount()-1){
			outputStream.write(asBytes("\t")); //$NON-NLS-1$
		}else{
			// add a line end if this is the last column and not the last row
			if(cell.getRowPosition() < cell.getLayer().getRowCount()-1){
				// add a line end if this is the last column
				outputStream.write(asBytes("\n")); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Convert the string as bytes.
	 * 
	 * @param string the string to convert as bytes.
	 * @return Th string converted.
	 */
	private byte[] asBytes(final String string) {
		return string.getBytes();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.export.ILayerExporter#getResult()
	 */
	@Override
	public Object getResult() {
		Object result = null;
		if(null != this.fileName){
			result = this.fileName;
		}else{
			result = this.outputStreamProvider.getResult();
		}
		return result;
	}
}
