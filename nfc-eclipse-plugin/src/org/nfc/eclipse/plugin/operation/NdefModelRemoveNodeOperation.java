/***************************************************************************
 *
 * This file is part of the NFC Eclipse Plugin project at
 * http://code.google.com/p/nfc-eclipse-plugin/
 *
 * Copyright (C) 2012 by Thomas Rorvik Skjolberg.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ****************************************************************************/

package org.nfc.eclipse.plugin.operation;

import org.nfc.eclipse.plugin.NdefRecordFactory;
import org.nfc.eclipse.plugin.model.NdefRecordModelNode;
import org.nfc.eclipse.plugin.model.NdefRecordModelParent;
import org.nfc.eclipse.plugin.model.NdefRecordModelRecord;
import org.nfctools.ndef.Record;


public class NdefModelRemoveNodeOperation implements NdefModelOperation {

	private NdefRecordModelParent parent;
	private NdefRecordModelNode child;
	
	private int index;

	public NdefModelRemoveNodeOperation(NdefRecordModelParent parent, NdefRecordModelNode child) {
		this.parent = parent;
		this.child = child;
		
		this.index = child.getParentIndex();
		
		initialize();
	}
	
	public void initialize() {
	}

	@Override
	public void execute() {
		Record record = parent.getRecord();
		if(record != null) {
			if(child instanceof NdefRecordModelRecord) {
				NdefRecordFactory.disconnect(record, child.getRecord());
			}
		}

		parent.remove(index);
	}

	@Override
	public void revoke() {
		Record record = parent.getRecord();
		if(record != null) {
			if(child instanceof NdefRecordModelRecord) {
				NdefRecordFactory.connect(record, child.getRecord());
			}
		}
		
		parent.insert(child, index);
	}

}
