package com.unascribed.copu.microcode;

import com.unascribed.copu.MemoryPage;
import com.unascribed.copu.VirtualMachine;
import com.unascribed.copu.undefined.VMError;

public class OpmodeDirectAddress extends Opmode {

	@Override
	public int get12(VirtualMachine vm, int operand) throws VMError {
		//pppp p..r rrrr
		int pageRegisterNum = (operand >> 7) & 0b0001_1111;
		int ofsRegisterNum = operand &  0b0001_1111;
		
		int pageDescriptor = vm.getRegister(pageRegisterNum).get();
		int ofs = vm.getRegister(ofsRegisterNum).get();
		MemoryPage page = vm.getPage(pageDescriptor);
		return page.get(ofs);
	}

	@Override
	public int get32(VirtualMachine vm, int operand) throws VMError {
		//pppp p...'.... ....'.... ....'...r rrrr
		int pageRegisterNum = (operand >> 27) & 0b0001_1111;
		int ofsRegisterNum = operand &  0b0001_1111;
		
		int pageDescriptor = vm.getRegister(pageRegisterNum).get();
		int ofs = vm.getRegister(ofsRegisterNum).get();
		MemoryPage page = vm.getPage(pageDescriptor);
		return page.get(ofs);
	}

	@Override
	public void put12(VirtualMachine vm, int operand, int data) throws VMError {
		//pppp p..r rrrr
		int pageRegisterNum = (operand >> 7) & 0b0001_1111;
		int ofsRegisterNum = operand &  0b0001_1111;
		
		int pageDescriptor = vm.getRegister(pageRegisterNum).get();
		int ofs = vm.getRegister(ofsRegisterNum).get();
		MemoryPage page = vm.getPage(pageDescriptor);
		page.set(ofs, data);
	}

	@Override
	public void put32(VirtualMachine vm, int operand, int data) throws VMError {
		//pppp p...'.... ....'.... ....'...r rrrr
		int pageRegisterNum = (operand >> 27) & 0b0001_1111;
		int ofsRegisterNum = operand &  0b0001_1111;
		
		int pageDescriptor = vm.getRegister(pageRegisterNum).get();
		int ofs = vm.getRegister(ofsRegisterNum).get();
		MemoryPage page = vm.getPage(pageDescriptor);
		page.set(ofs, data);
	}

}