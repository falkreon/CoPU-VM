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

package com.unascribed.copu.microcode;

public class HardwareLimits {
	public static final int PIPELINE_DEPTH = 4;
	
	//These values are fairly sane and realistic, and also quite subject to change.
	public static final int COST_FPU = 5;
	public static final int COST_FPU_TRIG = 80;
	public static final int COST_MODULE_CALL = 200; //Assuming it's from ROM. A fast disk would be in the thousands of cycles.
	public static final int COST_BRANCH_STALL = PIPELINE_DEPTH;
	public static final int COST_MEMORY_ACCESS_BYTE = 1;
	public static final int COST_MEMORY_ACCESS_WORD = 1;
	
	public static final int STACK_DEPTH = 64;
}
