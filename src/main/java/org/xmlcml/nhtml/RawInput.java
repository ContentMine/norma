package org.xmlcml.nhtml;

/** a container for raw input.
 * 
 * Roughly follows the types in InputType
 * 
 * @author pm286
 *
 */
public class RawInput implements HasInputType {

	private byte[] rawBytes;
	private InputType inputType;

	public RawInput(byte[] rawBytes) {
		this.rawBytes = rawBytes;
	}

	public InputType getInputType() {
		return this.inputType;
	}

	public byte[] getRawBytes() {
		return rawBytes;
	}

	
}
