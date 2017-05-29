package com.unascribed.copu;

import com.unascribed.copu.compiler.AssembleError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.StreamSupport;

import com.unascribed.copu.compiler.Assembler;
import com.unascribed.copu.undefined.VMError;

public class Copu {
	/*
	public static byte[] testProgram = {
			0x02, 0x11, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, //ADD R0, R0, R0
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //NOP
	 (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //HALT
			0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //JMP IMM[0]
	};*/
	
	public static String[] testPreCompile = {
			"MOV R0, 1",
			"ADD R1, R0",
			"ADD R0, 1",
			"JL R0, 11, MEM[8]",
			"MOV R0, 0",
			"RET"
	};
	
	public static void main(String[] args) {
		if (args.length==0) {
			System.out.println("Usage: copu {compile|run} {inputfile}");
			return;
		}
		
		if (args[0].toLowerCase().equals("compile")) {
			if (args.length<2) {
				System.out.println("Missing input-file parameter.");
				System.out.println("Usage: copu compile {inputfile}");
				return;
			}
			
			try {
				String[] inFile = getFileAsStrings(args[1]);
				System.out.println("Compiling "+args[1]+"...");
				byte[] outProgram = compile(inFile);
				
				String outFileName = args[1];
				if (args.length>2) {
					outFileName = args[2];
				} else if (outFileName.toLowerCase().endsWith(".asm")) {
					outFileName = outFileName.substring(0,outFileName.length()-4) + ".bin";
				} else {
					outFileName = outFileName + ".bin";
				}
				
				try {
					FileOutputStream out = new FileOutputStream(new File(outFileName));
					out.write(outProgram);
					out.flush();
					out.close();
					System.out.println("Generated "+outProgram.length+" bytes successfully.");
				} catch (IOException ex) {
					System.out.println("Couldn't write the output file.");
					ex.printStackTrace();
				}
				
			} catch (FileNotFoundException | NoSuchFileException ex) {
				System.out.println("Can't find file \""+new File(args[1]).getAbsolutePath()+"\".");
			} catch (IOException ex) {
				System.out.println("Couldn't read the input file.");
				ex.printStackTrace();
			} catch (AssembleError cex) {
				cex.printStackTrace();
			}
			return;
		}
		
		if (args[0].toLowerCase().equals("run")) {
			if (args.length<2) {
				System.out.println("Missing input-file parameter.");
				System.out.println("Usage: copu run {inputfile}");
				return;
			}
			
			String inFileName = args[1];
			if (inFileName.toLowerCase().endsWith(".asm")) {
				//Compile to memory and run
				System.out.println("Compiling "+args[1]+"...");
				String[] inFile;
				try {
					inFile = getFileAsStrings(args[1]);
					byte[] outProgram = compile(inFile);
					System.out.println("Generated "+outProgram.length+" bytes successfully. Running...");
					run(outProgram);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AssembleError e) {
					System.out.println("Error on line "+e.getLine()+":");
					e.printStackTrace();
				}
			} else if (inFileName.toLowerCase().endsWith(".bin")) {
				//Load directly and run
				try {
					byte[] program = getFileAsBytes(inFileName);
					run(program);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				System.out.println(inFileName+" is not a .asm source file or a .bin program file.");
			}
			
			return;
		}
	}
	
	private static byte[] compile(String[] program) throws AssembleError {
		Assembler compiler = new Assembler();
		compiler.parse(program);
		return compiler.toByteArray();
	}
	
	private static String[] getFileAsStrings(String path) throws IOException {
		return Files.readAllLines(Paths.get(path)).toArray(new String[0]);
	}
	
	private static byte[] getFileAsBytes(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}
	
	private static void run(byte[] program) {
		VirtualMachine vm = new VirtualMachine();
		vm.loadProgram(program);
		
		long startMillis = System.currentTimeMillis();
		try {
			while(true) {
				vm.runCycle();
			}
		} catch (VMError err) {
			System.out.println(err.getMessage());
		}
		long elapsed = System.currentTimeMillis() - startMillis;
		System.out.println("Total machine cycles:"+vm.getCycleCount()+" elapsed:"+elapsed+"msec");
		
		System.out.println("REGISTER FILE DUMP");
		for(Register reg : vm.registers().table) {
			System.out.print(Integer.toHexString(reg.get())+"\t");
		}
		System.out.println();
	}
}
