package org.xmlcml.norma;

/** a container for raw input.
 * 
 * Roughly follows the types in InputType
 * 
 * @author pm286
 *
 */
public class RawInput implements HasInputType {

	private byte[] rawBytes;
	private InputFormat inputType;

	public RawInput(byte[] rawBytes) {
		this.rawBytes = rawBytes;
	}

	public InputFormat getInputType() {
		return this.inputType;
	}

	public byte[] getRawBytes() {
		return rawBytes;
	}

	
}
