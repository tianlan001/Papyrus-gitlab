/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Christian W. Damus - adapted from GEF for bug 469188
 *******************************************************************************/
package org.eclipse.papyrus.infra.ui.dnd;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * A local transfer carrying a single object being dragged. Subclasses should maintain a
 * single instance of their Transfer and provide a static method to obtain that
 * instance.
 * 
 * @since 1.2
 */
public abstract class PapyrusTransfer<T> extends ByteArrayTransfer {

	private final Class<? extends T> objectType;
	private final String typeName;
	private final int typeID;

	private Reference<T> object;
	private long startTime;

	protected PapyrusTransfer(Class<? extends T> objectType) {
		super();

		this.objectType = objectType;

		typeName = String.format("%s:%x:%x", getClass().getSimpleName(), hashCode(), System.currentTimeMillis());
		typeID = registerType(typeName);
	}

	@Override
	public final int hashCode() {
		return System.identityHashCode(this);
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * mulitple running copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#javaToNative(Object, TransferData)
	 */
	@Override
	public void javaToNative(Object object, TransferData transferData) {
		setObject(objectType.cast(object));
		startTime = System.currentTimeMillis();
		if (transferData != null)
			super.javaToNative(String.valueOf(startTime).getBytes(),
					transferData);
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * mulitple running. copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#nativeToJava(TransferData)
	 */
	@Override
	public Object nativeToJava(TransferData transferData) {
		byte bytes[] = (byte[]) super.nativeToJava(transferData);
		if (bytes == null) {
			return null;
		}
		long startTime = Long.parseLong(new String(bytes));
		return (this.startTime == startTime) ? getObject() : null;
	}

	/**
	 * Obtains the object being dragged.
	 */
	public T getObject() {
		return (object == null) ? null : objectType.cast(object.get());
	}

	/**
	 * Sets the object being dragged.
	 */
	public void setObject(T object) {
		this.object = new WeakReference<>(object);
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { typeID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { typeName };
	}

}
