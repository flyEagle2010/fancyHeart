package com.doteyplay.game.message.proto;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

final public class ProtobufOutputStream {

	static final int WIRETYPE_VARINT = 0;
	static final int WIRETYPE_FIXED64 = 1;
	static final int WIRETYPE_LENGTH_DELIMITED = 2;
	static final int WIRETYPE_START_GROUP = 3;
	static final int WIRETYPE_END_GROUP = 4;
	static final int WIRETYPE_FIXED32 = 5;

	static final int TAG_TYPE_BITS = 3;
	static final int TAG_TYPE_MASK = (1 << TAG_TYPE_BITS) - 1;

	static final int LITTLE_ENDIAN_64_SIZE = 8;
	public static final int LITTLE_ENDIAN_32_SIZE = 4;

	public static int writeRepeatedDouble(final int fieldNumber, final List<Double> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeDouble(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedInt32(final int fieldNumber, final List<Integer> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeInt32(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedInt64(final int fieldNumber, final List<Long> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeInt64(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedUint32(final int fieldNumber, final List<Integer> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeUint32(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedUint64(final int fieldNumber, final List<Long> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeUint64(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedSint32(final int fieldNumber, final List<Integer> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeSint32(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedSint64(final int fieldNumber, final List<Long> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeSint64(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedFixed32(final int fieldNumber, final List<Integer> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeFixed32(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedFixed64(final int fieldNumber, final List<Long> value, byte[] buffer, int currentPosition) {
		if (value.isEmpty()) {
			return currentPosition;
		}
		int result = currentPosition;
		for (int i = 0; i < value.size(); i++) {
			result = writeFixed64(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedSfixed32(final int fieldNumber, final List<Integer> value, byte[] buffer, int position) {
		if (value.isEmpty()) {
			return position;
		}
		int result = position;
		for (int i = 0; i < value.size(); i++) {
			result = writeSfixed32(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedSfixed64(final int fieldNumber, final List<Long> value, byte[] buffer, int position) {
		if (value.isEmpty()) {
			return position;
		}
		int result = position;
		for (int i = 0; i < value.size(); i++) {
			result = writeSfixed64(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedFloat(final int fieldNumber, final List<Float> value, byte[] buffer, int position) {
		if (value.isEmpty()) {
			return position;
		}
		int result = position;
		for (int i = 0; i < value.size(); i++) {
			result = writeFloat(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedBool(final int fieldNumber, final List<Boolean> value, byte[] buffer, int position) {
		if (value.isEmpty()) {
			return position;
		}
		int result = position;
		for (int i = 0; i < value.size(); i++) {
			result = writeBool(fieldNumber, value.get(i), buffer, result);
		}
		return result;
	}

	public static int writeRepeatedBytes(final int fieldNumber, final byte[] value, byte[] buffer, int position) {
		return writeBytes(fieldNumber, value, buffer, position);
	}

	public static int writeDouble(final int fieldNumber, final double value, byte[] output, int currentPosition) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED64, output, currentPosition);
		return writeDoubleNoTag(value, output, result);
	}
	
	public static void writeDouble(final int fieldNumber, final double value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED64,os);
		writeDoubleNoTag(value, os);
	}

	public static int writeTag(final int fieldNumber, final int wireType, byte[] buffer, int position) {
		return writeRawVarint32(makeTag(fieldNumber, wireType), buffer, position);
	}

	public static void writeTag(final int fieldNumber, final int wireType, OutputStream os) throws IOException {
		writeRawVarint32(makeTag(fieldNumber, wireType), os);
	}

	public static void writeMessageTag(final int fieldNumber, OutputStream baos) throws IOException {
		writeRawVarint32(makeTag(fieldNumber, WIRETYPE_LENGTH_DELIMITED), baos);
	}

	public static int writeFloat(final int fieldNumber, final float value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED32, buffer, position);
		return writeFloatNoTag(value, buffer, result);
	}

	public static void writeFloat(final int fieldNumber, final float value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED32, os);
		writeFloatNoTag(value, os);
	}
	
	public static int writeUint64(final int fieldNumber, final long value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeUint64NoTag(value, buffer, result);
	}

	public static void writeUint64(final int fieldNumber, final long value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeUint64NoTag(value, os);
	}

	public static int writeInt64(final int fieldNumber, final long value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeInt64NoTag(value, buffer, result);
	}

	public static void writeInt64(final int fieldNumber, final long value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeInt64NoTag(value, os);
	}

	public static int writeInt32(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeInt32NoTag(value, buffer, result);
	}

	public static void writeInt32(int fieldNumber, int bb, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeInt32NoTag(bb, os);
	}

	public static int writeFixed64(final int fieldNumber, final long value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED64, buffer, position);
		return writeFixed64NoTag(value, buffer, result);
	}

	public static void writeFixed64(final int fieldNumber, final long value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED64, os);
		writeFixed64NoTag(value, os);
	}

	public static int writeFixed32(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED32, buffer, position);
		return writeFixed32NoTag(value, buffer, result);
	}

	public static void writeFixed32(final int fieldNumber, final int value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED32, os);
		writeFixed32NoTag(value, os);
	}

	public static int writeBool(final int fieldNumber, final boolean value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeBoolNoTag(value, buffer, result);
	}
	
	public static void writeBool(final int fieldNumber, final boolean value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeBoolNoTag(value, os);
	}

	public static void writeString(final int fieldNumber, final String value, OutputStream baos) throws IOException {
		final byte[] bytes = value.getBytes("UTF-8");
		int size = computeTagSize(fieldNumber);
		size += computeRawVarint32Size(bytes.length);
		byte[] buffer = new byte[size];
		int position = writeTag(fieldNumber, WIRETYPE_LENGTH_DELIMITED, buffer, 0);
		writeRawVarint32(bytes.length, buffer, position);
		baos.write(buffer);
		baos.write(bytes);
		baos.flush();
	}

	public static int writeString(final int fieldNumber, final byte[] value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_LENGTH_DELIMITED, buffer, position);
		result = writeRawVarint32(value.length, buffer, result);
		return writeRawBytes(value, buffer, result);
	}

	public static int writeBytes(final int fieldNumber, final byte[] value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_LENGTH_DELIMITED, buffer, position);
		result = writeRawVarint32(value.length, buffer, result);
		result = writeRawBytes(value, buffer, result);
		return result;
	}
	
	public static void writeBytes(final int fieldNumber, final byte[] value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_LENGTH_DELIMITED, os);
		writeRawVarint32(value.length, os);
		writeRawBytes(value, os);
	}

	public static int computeBytesSize(final int fieldNumber, final byte[] value) {
		int result = computeTagSize(fieldNumber);
		result += computeRawVarint32Size(value.length);
		result += value.length;
		return result;
	}

	public static int writeUint32(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeUint32NoTag(value, buffer, result);
	}

	public static void writeUint32(final int fieldNumber, final int value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeUint32NoTag(value, os);
	}

	public static int writeSfixed32(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED32, buffer, position);
		return writeSfixed32NoTag(value, buffer, result);
	}

	public static void writeSfixed32(final int fieldNumber, final int value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED32, os);
		writeSfixed32NoTag(value, os);
	}

	public static int writeSfixed64(final int fieldNumber, final long value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_FIXED64, buffer, position);
		return writeSfixed64NoTag(value, buffer, result);
	}
	
	public static void writeSfixed64(final int fieldNumber, final long value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_FIXED64, os);
		writeSfixed64NoTag(value, os);
	}

	public static int writeSint32(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeSint32NoTag(value, buffer, result);
	}

	public static void writeSint32(final int fieldNumber, final int value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeSint32NoTag(value, os);
	}

	public static int writeSint64(final int fieldNumber, final long value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeSint64NoTag(value, buffer, result);
	}

	public static void writeSint64(final int fieldNumber, final long value, OutputStream os) throws IOException {
		writeTag(fieldNumber, WIRETYPE_VARINT, os);
		writeSint64NoTag(value, os);
	}

	public static int writeDoubleNoTag(final double value, byte[] buffer, int position) {
		return writeRawLittleEndian64(Double.doubleToRawLongBits(value), buffer, position);
	}
	
	public static void writeDoubleNoTag(final double value, OutputStream os) throws IOException {
		writeRawLittleEndian64(Double.doubleToRawLongBits(value), os);
	}

	public static int writeFloatNoTag(final float value, byte[] buffer, int position) {
		return writeRawLittleEndian32(Float.floatToRawIntBits(value), buffer, position);
	}
	
	public static void writeFloatNoTag(final float value, OutputStream os) throws IOException {
		writeRawLittleEndian32(Float.floatToRawIntBits(value), os);
	}

	public static int writeUint64NoTag(final long value, byte[] buffer, int position) {
		return writeRawVarint64(value, buffer, position);
	}

	public static void writeUint64NoTag(final long value, OutputStream os) throws IOException {
		writeRawVarint64(value, os);
	}

	public static int writeInt64NoTag(final long value, byte[] buffer, int position) {
		return writeRawVarint64(value, buffer, position);
	}

	public static void writeInt64NoTag(final long value, OutputStream os) throws IOException {
		writeRawVarint64(value, os);
	}

	public static int writeInt32NoTag(final int value, byte[] buffer, int position) {
		if (value >= 0) {
			return writeRawVarint32(value, buffer, position);
		} else {
			// Must sign-extend.
			return writeRawVarint64(value, buffer, position);
		}
	}

	public static void writeInt32NoTag(final int value, OutputStream os) throws IOException {
		if (value >= 0) {
			writeRawVarint32(value, os);
		} else {
			// Must sign-extend.
			writeRawVarint64(value, os);
		}
	}

	public static int writeFixed64NoTag(final long value, byte[] buffer, int position) {
		return writeRawLittleEndian64(value, buffer, position);
	}

	public static void writeFixed64NoTag(final long value, OutputStream os) throws IOException {
		writeRawLittleEndian64(value, os);
	}

	public static int writeFixed32NoTag(final int value, byte[] buffer, int position) {
		return writeRawLittleEndian32(value, buffer, position);
	}

	public static void writeFixed32NoTag(final int value, OutputStream os) throws IOException {
		writeRawLittleEndian32(value, os);
	}

	public static int writeBoolNoTag(final boolean value, byte[] buffer, int position) {
		return writeRawByte(value ? 1 : 0, buffer, position);
	}
	
	public static void writeBoolNoTag(final boolean value, OutputStream os) throws IOException {
		writeRawByte(value ? 1 : 0, os);
	}

	public static int writeStringNoTag(final String value, byte[] buffer, int position) throws IOException {
		// Unfortunately there does not appear to be any way to tell Java to encode
		// UTF-8 directly into our buffer, so we have to let it create its own byte
		// array and then copy.
		final byte[] bytes = value.getBytes("UTF-8");
		int result = writeRawVarint32(bytes.length, buffer, position);
		return writeRawBytes(bytes, buffer, result);
	}

	public static int writeRawBytes(final byte[] value, byte[] buffer, int position) {
		return writeRawBytes(value, 0, value.length, buffer, position);
	}
	
	public static void writeRawBytes(final byte[] value, OutputStream os) throws IOException {
		writeRawBytes(value, 0, value.length, os);
	}

	public static int writeRawBytes(final byte[] value, int offset, int length, byte[] buffer, int position) {
		if (buffer.length - position >= length) {
			System.arraycopy(value, offset, buffer, position, length);
		}
		return position + length;
	}
	
	public static void writeRawBytes(final byte[] value, int offset, int length, OutputStream os) throws IOException {
		os.write(value, offset, length);
	}

	public static int writeUint32NoTag(final int value, byte[] buffer, int position) {
		return writeRawVarint32(value, buffer, position);
	}

	public static void writeUint32NoTag(final int value, OutputStream os) throws IOException {
		writeRawVarint32(value, os);
	}

	public static int writeSfixed32NoTag(final int value, byte[] buffer, int position) {
		return writeRawLittleEndian32(value, buffer, position);
	}

	public static void writeSfixed32NoTag(final int value, OutputStream os) throws IOException {
		writeRawLittleEndian32(value, os);
	}

	public static int writeSfixed64NoTag(final long value, byte[] buffer, int position) {
		return writeRawLittleEndian64(value, buffer, position);
	}

	public static void writeSfixed64NoTag(final long value, OutputStream os) throws IOException {
		writeRawLittleEndian64(value, os);
	}
	
	public static int writeSint32NoTag(final int value, byte[] buffer, int position) {
		return writeRawVarint32(encodeZigZag32(value), buffer, position);
	}

	public static void writeSint32NoTag(final int value, OutputStream os) throws IOException {
		writeRawVarint32(encodeZigZag32(value), os);
	}

	public static int writeSint64NoTag(final long value, byte[] buffer, int position) {
		return writeRawVarint64(encodeZigZag64(value), buffer, position);
	}

	public static void writeSint64NoTag(final long value, OutputStream os) throws IOException {
		writeRawVarint64(encodeZigZag64(value), os);
	}

	public static int writeRawVarint32(int value, byte[] buffer, int position) {
		int result = position;
		while (true) {
			if ((value & ~0x7F) == 0) {
				result = writeRawByte(value, buffer, result);
				return result;
			} else {
				result = writeRawByte((value & 0x7F) | 0x80, buffer, result);
				value >>>= 7;
			}
		}
	}

	public static void writeRawVarint32(int value, OutputStream baos) throws IOException {
		while (true) {
			if ((value & ~0x7F) == 0) {
				writeRawByte(value, baos);
				return;
			} else {
				writeRawByte((value & 0x7F) | 0x80, baos);
				value >>>= 7;
			}
		}
	}

	public static void writeRawByte(final int value, OutputStream baos) throws IOException {
		writeRawByte((byte) value, baos);
	}

	public static void writeRawByte(final byte value, OutputStream baos) throws IOException {
		baos.write(value);
	}

	public static int writeRawVarint64(long value, byte[] buffer, int position) {
		int result = position;
		while (true) {
			if ((value & ~0x7FL) == 0) {
				result = writeRawByte((int) value, buffer, result);
				return result;
			} else {
				result = writeRawByte(((int) value & 0x7F) | 0x80, buffer, result);
				value >>>= 7;
			}
		}
	}

	public static void writeRawVarint64(long value, OutputStream os) throws IOException {
		while (true) {
			if ((value & ~0x7FL) == 0) {
				writeRawByte((int) value, os);
				return;
			} else {
				writeRawByte(((int) value & 0x7F) | 0x80, os);
				value >>>= 7;
			}
		}
	}

	public static int writeEnum(final int fieldNumber, final int value, byte[] buffer, int position) {
		int result = writeTag(fieldNumber, WIRETYPE_VARINT, buffer, position);
		return writeEnumNoTag(value, buffer, result);
	}

	public static void writeEnum(final int fieldNumber, final int value, OutputStream baos) throws IOException {
		writeRawVarint32(makeTag(fieldNumber, WIRETYPE_VARINT), baos);
		writeRawVarint32(value, baos);
	}

	public static int writeEnumNoTag(final int value, byte[] buffer, int position) {
		return writeRawVarint32(value, buffer, position);
	}

	public static int writeRawLittleEndian32(final int value, byte[] buffer, int position) {
		int result = writeRawByte((value) & 0xFF, buffer, position);
		result = writeRawByte((value >> 8) & 0xFF, buffer, result);
		result = writeRawByte((value >> 16) & 0xFF, buffer, result);
		result = writeRawByte((value >> 24) & 0xFF, buffer, result);
		return result;
	}

	public static void writeRawLittleEndian32(final int value, OutputStream os) throws IOException {
		writeRawByte((value) & 0xFF, os);
		writeRawByte((value >> 8) & 0xFF, os);
		writeRawByte((value >> 16) & 0xFF, os);
		writeRawByte((value >> 24) & 0xFF, os);
	}

	public static int encodeZigZag32(final int n) {
		// Note:  the right-shift must be arithmetic
		return (n << 1) ^ (n >> 31);
	}

	private static long encodeZigZag64(final long n) {
		// Note:  the right-shift must be arithmetic
		return (n << 1) ^ (n >> 63);
	}

	public static int makeTag(final int fieldNumber, final int wireType) {
		return (fieldNumber << TAG_TYPE_BITS) | wireType;
	}

	public static int writeRawLittleEndian64(final long value, byte[] buffer, int position) {
		int result = writeRawByte((int) (value) & 0xFF, buffer, position);
		result = writeRawByte((int) (value >> 8) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 16) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 24) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 32) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 40) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 48) & 0xFF, buffer, result);
		result = writeRawByte((int) (value >> 56) & 0xFF, buffer, result);
		return result;
	}

	public static void writeRawLittleEndian64(final long value, OutputStream os) throws IOException {
		writeRawByte((int) (value) & 0xFF, os);
		writeRawByte((int) (value >> 8) & 0xFF, os);
		writeRawByte((int) (value >> 16) & 0xFF, os);
		writeRawByte((int) (value >> 24) & 0xFF, os);
		writeRawByte((int) (value >> 32) & 0xFF, os);
		writeRawByte((int) (value >> 40) & 0xFF, os);
		writeRawByte((int) (value >> 48) & 0xFF, os);
		writeRawByte((int) (value >> 56) & 0xFF, os);
	}

	public static int writeRawByte(final byte value, byte[] buffer, int position) {
		if (position == buffer.length) {
			throw new ArrayIndexOutOfBoundsException("can't write more than buffer expects");
		}

		buffer[position] = value;
		return position + 1;
	}

	public static int writeRawByte(final int value, byte[] buffer, int position) {
		return writeRawByte((byte) value, buffer, position);
	}

	public static int spaceLeft(byte[] output, int position) {
		return output.length - position;
	}

	public static void checkNoSpaceLeft(byte[] output, int position) {
		if (spaceLeft(output, position) != 0) {
			throw new IllegalStateException("Did not write as much data as expected.");
		}
	}

	public static int computeDoubleSize(final int fieldNumber, final double value) {
		return computeTagSize(fieldNumber) + computeDoubleSizeNoTag(value);
	}

	public static int computeFloatSize(final int fieldNumber, final float value) {
		return computeTagSize(fieldNumber) + computeFloatSizeNoTag(value);
	}

	public static int computeTagSize(final int fieldNumber) {
		return computeRawVarint32Size(makeTag(fieldNumber, 0));
	}

	public static int computeRawVarint32Size(final int value) {
		if ((value & (0xffffffff << 7)) == 0)
			return 1;
		if ((value & (0xffffffff << 14)) == 0)
			return 2;
		if ((value & (0xffffffff << 21)) == 0)
			return 3;
		if ((value & (0xffffffff << 28)) == 0)
			return 4;
		return 5;
	}

	public static int computeUint64Size(final int fieldNumber, final long value) {
		return computeTagSize(fieldNumber) + computeUint64SizeNoTag(value);
	}

	public static int computeInt64Size(final int fieldNumber, final long value) {
		return computeTagSize(fieldNumber) + computeInt64SizeNoTag(value);
	}

	public static int computeInt32Size(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeInt32SizeNoTag(value);
	}

	public static int computeFixed64Size(final int fieldNumber, final long value) {
		return computeTagSize(fieldNumber) + computeFixed64SizeNoTag(value);
	}

	public static int computeFixed32Size(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeFixed32SizeNoTag(value);
	}

	public static int computeBoolSize(final int fieldNumber, final boolean value) {
		return computeTagSize(fieldNumber) + computeBoolSizeNoTag(value);
	}

	public static int computeStringSize(final int fieldNumber, final String value) {
		return computeTagSize(fieldNumber) + computeStringSizeNoTag(value);
	}

	public static int computeUint32Size(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeUint32SizeNoTag(value);
	}

	public static int computeEnumSize(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeEnumSizeNoTag(value);
	}

	public static int computeSfixed32Size(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeSfixed32SizeNoTag(value);
	}

	public static int computeSfixed64Size(final int fieldNumber, final long value) {
		return computeTagSize(fieldNumber) + computeSfixed64SizeNoTag(value);
	}

	public static int computeSint32Size(final int fieldNumber, final int value) {
		return computeTagSize(fieldNumber) + computeSint32SizeNoTag(value);
	}

	public static int computeSint64Size(final int fieldNumber, final long value) {
		return computeTagSize(fieldNumber) + computeSint64SizeNoTag(value);
	}

	public static int computeDoubleSizeNoTag(final double value) {
		return LITTLE_ENDIAN_64_SIZE;
	}

	public static int computeFloatSizeNoTag(final float value) {
		return LITTLE_ENDIAN_32_SIZE;
	}

	public static int computeUint64SizeNoTag(final long value) {
		return computeRawVarint64Size(value);
	}

	public static int computeInt64SizeNoTag(final long value) {
		return computeRawVarint64Size(value);
	}

	public static int computeRawVarint64Size(final long value) {
		if ((value & (0xffffffffffffffffL << 7)) == 0)
			return 1;
		if ((value & (0xffffffffffffffffL << 14)) == 0)
			return 2;
		if ((value & (0xffffffffffffffffL << 21)) == 0)
			return 3;
		if ((value & (0xffffffffffffffffL << 28)) == 0)
			return 4;
		if ((value & (0xffffffffffffffffL << 35)) == 0)
			return 5;
		if ((value & (0xffffffffffffffffL << 42)) == 0)
			return 6;
		if ((value & (0xffffffffffffffffL << 49)) == 0)
			return 7;
		if ((value & (0xffffffffffffffffL << 56)) == 0)
			return 8;
		if ((value & (0xffffffffffffffffL << 63)) == 0)
			return 9;
		return 10;
	}

	public static int computeInt32SizeNoTag(final int value) {
		if (value >= 0) {
			return computeRawVarint32Size(value);
		} else {
			// Must sign-extend.
			return 10;
		}
	}

	public static int computeFixed64SizeNoTag(final long value) {
		return LITTLE_ENDIAN_64_SIZE;
	}

	public static int computeFixed32SizeNoTag(final int value) {
		return LITTLE_ENDIAN_32_SIZE;
	}

	public static int computeBoolSizeNoTag(final boolean value) {
		return 1;
	}

	public static int computeStringSizeNoTag(final String value) {
		try {
			final byte[] bytes = value.getBytes("UTF-8");
			return computeRawVarint32Size(bytes.length) + bytes.length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported.", e);
		}
	}

	public static int computeUint32SizeNoTag(final int value) {
		return computeRawVarint32Size(value);
	}

	public static int computeEnumSizeNoTag(final int value) {
		return computeRawVarint32Size(value);
	}

	public static int computeSfixed32SizeNoTag(final int value) {
		return LITTLE_ENDIAN_32_SIZE;
	}

	public static int computeSfixed64SizeNoTag(final long value) {
		return LITTLE_ENDIAN_64_SIZE;
	}

	public static int computeSint32SizeNoTag(final int value) {
		return computeRawVarint32Size(encodeZigZag32(value));
	}

	public static int computeSint64SizeNoTag(final long value) {
		return computeRawVarint64Size(encodeZigZag64(value));
	}
	
	private ProtobufOutputStream() {
		//do nothing
	}

}
