/*
 * MIT License
 *
 * Copyright (c) 2017 Falkreon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.unascribed.copu;

import com.unascribed.copu.undefined.VMKernelPanic;
import com.unascribed.copu.undefined.VMPageFault;

public class VirtualMachine {
	private boolean ok = true;
	private RegisterFile registers = new RegisterFile();
	private MemoryPage[] localDescriptorTable = new MemoryPage[16];
	
	public RegisterFile registers() {
		return registers;
	}
	public Register getRegister(int id) throws VMKernelPanic {
		if (id<0 || id>=registers.table.length) throw new VMKernelPanic("Tried to access invalid register:"+id);
		return registers.table[id];
	}
	
	public MemoryPage getPage(int descriptor) throws VMPageFault {
		if (descriptor<0 || descriptor>=localDescriptorTable.length) throw new VMPageFault("Invalid page descriptor "+Integer.toHexString(descriptor));
		
		MemoryPage result = localDescriptorTable[descriptor];
		if (result==null) {
			//TODO: This is the sort of error that typically bricks machines before they can bluescreen. This would be a FANTASTIC opportunity for undefined behavior.
			throw new VMPageFault("");
		}
		return result;
	}
}