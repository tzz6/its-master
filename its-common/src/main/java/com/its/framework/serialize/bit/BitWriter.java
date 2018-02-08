package com.its.framework.serialize.bit;

import java.util.Arrays;

public final class BitWriter {
	private byte[] buffer;
	private int currentByte;
	private int bitCount;
	private int position;

	public BitWriter() {
		initialize();
	}

	public final void write1() {
		this.currentByte >>= 1;
		this.currentByte |= 128;
		check8Bit();
	}

	public final void write0() {
		this.currentByte >>= 1;
		check8Bit();
	}

	public final boolean hasBits() {
		return (this.bitCount > 0) || (this.position > -1);
	}

	public final byte[] toBytes() {
		if (this.bitCount > 0) {
			this.currentByte >>= 8 - this.bitCount;
			if (++this.position >= this.buffer.length) {
				this.buffer = Arrays.copyOf(this.buffer, this.buffer.length + 1);
			}
			this.buffer[this.position] = (byte) this.currentByte;
		}

		int byteCount = this.position + 1;
		if (byteCount != this.buffer.length) {
			this.buffer = Arrays.copyOf(this.buffer, byteCount);
		}

		if (byteCount == 1) {
			return this.buffer;
		}

		int half = byteCount / 2;
		for (int i = 0; i < half; i++) {
			byte tmp = this.buffer[this.position];
			this.buffer[this.position] = this.buffer[i];
			this.buffer[i] = tmp;
			this.position -= 1;
		}
		return this.buffer;
	}

	public final void initialize() {
		this.buffer = new byte[8];
		this.currentByte = 0;
		this.position = -1;
		this.bitCount = 0;
	}

	private final void check8Bit() {
		if (++this.bitCount == 8) {
			if (++this.position >= this.buffer.length) {
				this.buffer = Arrays.copyOf(this.buffer, this.buffer.length * 2);
			}

			this.buffer[this.position] = (byte) this.currentByte;
			this.currentByte = 0;
			this.bitCount = 0;
		}
	}
}
