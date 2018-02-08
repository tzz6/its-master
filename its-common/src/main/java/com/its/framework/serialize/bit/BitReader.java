package com.its.framework.serialize.bit;

public final class BitReader {
	private byte[] buffer;
	private int currentByte;
	private int bitCount;
	private int position;

	public BitReader(byte[] buffer) {
		setBuffer(buffer, buffer.length);
	}

	public BitReader(byte[] buffer, int count) {
		setBuffer(buffer, count);
	}

	public final void setBuffer(byte[] buffer, int count) {
		this.buffer = buffer;
		this.bitCount = 0;
		this.position = (count - 1);
		this.currentByte = (buffer[this.position] & 0xFF);
	}

	public final int read() {
		int bit = this.currentByte & 0x1;

		if (++this.bitCount == 8) {
			if (--this.position >= 0) {
				this.currentByte = (this.buffer[this.position] & 0xFF);
			}
			this.bitCount = 0;
		} else {
			this.currentByte >>= 1;
		}

		return bit;
	}
}
