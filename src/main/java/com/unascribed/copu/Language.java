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

import java.util.HashMap;
import java.util.Set;

/**
 * Since the compiler won't always run in Minecraft, it needs its own localization solution.
 * Welcome to my compiler localization. Default keys are defined for en_US, but you can re-
 * define keys at any time.
 *
 */
public class Language {
	private static Language enUS = createEnUS();
	private static Language current = enUS;
	
	private HashMap<String, String> localizationKeys = new HashMap<>();
	
	public Language() {}
	
	public Set<String> allKeys() {
		return localizationKeys.keySet();
	}
	
	
	/**
	 * Defines the localized String that a localization key will translate to.
	 * @param key The localization key
	 * @param newValue A localized string
	 */
	public void define(String key, String newValue) {
		localizationKeys.put(key, newValue);
	}
	
	/**
	 * Grab a localized String from a localization key.
	 * @param key A key, such as "err.notEnoughArgs"
	 * @return A localized String in this language.
	 */
	public String localize(String key) {
		if (!localizationKeys.containsKey(key)) return key;
		return localizationKeys.get(key);
	}
	
	protected static Language createEnUS() {
		Language en = new Language();
		
		//All the default keys. This could get messy.
		en.define("err.validate.notEnoughArgs", "Not enough arguments.");
		en.define("err.validate.tooManyArgs",   "Too many arguments.");
		en.define("err.validate.destOperandRequired", "This argument only accepts certain registers (R0-R7, F0-F3, PG0-PG1)"); //and X and Y >:)
		en.define("err.validate.argTooLarge", "Argument is too large.");
		en.define("err.validate.mustBeImmediate", "Argument must be an immediate value.");
		en.define("err.assembler.wrongType", "Type mismatch in operand.");
		en.define("err.assembler.wrongPacking.4", "Cannot pack this data into a 4-bit operand.");
		en.define("err.assembler.wrongPacking.12", "Cannot pack this data into a 12-bit operand.");
		en.define("err.assembler.wrongPacking.32", "Cannot pack this data into a 32-bit operand.");
		return en;
	}
	
	public static Language getCurrent() { return current; }
	public static void setCurrent(Language lang) { current = lang; }
}
