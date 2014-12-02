package com.doteyplay.game.message.proto;


import java.io.IOException;
import java.io.InputStream;

final public class ProtobufInputStream {

	static final int WIRETYPE_VARINT = 0;
	static final int WIRETYPE_FIXED64 = 1;
	static final int WIRETYPE_LENGTH_DELIMITED = 2;
	static final int WIRETYPE_GROUP_START = 3;
	static final int WIRETYPE_GROUP_END = 4;
	static final int WIRETYPE_FIXED32 = 5;

	static final int TAG_TYPE_BITS = 3;
	static final int TAG_TYPE_MASK = (1 << TAG_TYPE_BITS) - 1;

	public static boolean skipUnknown(final int varint, byte[] data, CurrentCursor cursor) throws IOException {
		switch (getTagWireType(varint)) {
		case WIRETYPE_VARINT:
			readInt64(data, cursor);
			return true;
		case WIRETYPE_FIXED64:
			readFixed64(data, cursor);
			return true;
		case WIRETYPE_LENGTH_DELIMITED:
			skipBytes(data, cursor);
			return true;
		case WIRETYPE_FIXED32:
			readFixed32(data, cursor);
			return true;
		case WIRETYPE_GROUP_START:
			int groupStartTag = getTagFieldNumber(varint);
			int groupEndTag = skipMessage(data, cursor);
			if (groupStartTag != groupEndTag) {
				throw new IOException("invalid end tag");
			}
			return true;
		case WIRETYPE_GROUP_END:
			return false;
		default:
			throw new IOException("invalid wire type");
		}
	}

	public static int skipMessage(byte[] data, CurrentCursor cursor) 	throws IOException {
		while (true) {
			final int varint = readRawVarint32(data, cursor);
			final int tag = getTagFieldNumber(varint);
			if (tag == 0 || !skipUnknown(varint, data, cursor)) {
				return tag;
			}
		}
	}
	
	public static int readEnum(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint32(data, cursor);
	}

	public static byte[] readBytes(byte[] data, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(data, cursor);
		return readRawBytes(size, data, cursor);
	}
	
	private static void skipBytes(byte[] data, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(data, cursor);
		skipRawBytes(size, cursor);
	}
	
	private static void skipRawBytes(final int size, CurrentCursor cursor) throws IOException {
		if (size < 0) {
			throw new IOException("Invalid buffer size");
		}
		cursor.addToPosition(size);
	}

	private static int getTagWireType(final int tag) {
		return tag & TAG_TYPE_MASK;
	}

	public static int readTag(byte[] data, CurrentCursor currentPosition) throws IOException {
		if (isAtEnd(data, currentPosition)) {
			return 0;
		}

		int lastTag = getTagFieldNumber(readRawVarint32(data, currentPosition));
		if (lastTag == 0) {
			// If we actually read zero (or any tag number corresponding to field
			// number zero), that's not a valid tag.
			throw new IOException("invalid tag: 0");
		}
		return lastTag;
	}
	
	public static int readTag(InputStream is, CurrentCursor cursor) throws IOException {
		if( cursor.getCurrentPosition() == cursor.getProcessUpToPosition() ) {
			return 0;
		}
		int tagId = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			return 0;
		}
		int lastTag = getTagFieldNumber(tagId);
		if (lastTag == 0) {
			// If we actually read zero (or any tag number corresponding to field
			// number zero), that's not a valid tag.
			throw new IOException("invalid tag: 0");
		}
		return lastTag;
	}

	public static int readInt32(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint32(data, cursor);
	}

	public static long readInt64(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint64(data, cursor);
	}

	public static long readRawVarint64(byte[] data, CurrentCursor cursor) throws IOException {
		int shift = 0;
		long result = 0;
		while (shift < 64) {
			final byte b = readRawByte(data, cursor);
			result |= (long) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				return result;
			}
			shift += 7;
		}
		throw new IOException("malformed varint64");
	}

	public static int readUint32(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint32(data, cursor);
	}

	public static int readRawVarint32(byte[] data, CurrentCursor currentPosition) throws IOException {
		byte tmp = readRawByte(data, currentPosition);
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = readRawByte(data, currentPosition)) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = readRawByte(data, currentPosition)) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = readRawByte(data, currentPosition)) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = readRawByte(data, currentPosition)) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (readRawByte(data, currentPosition) >= 0) {
								return result;
							}
						}
						throw new IOException("malformed varint32");
					}
				}
			}
		}
		return result;
	}
	
	public static int readRawVarint32(InputStream is, CurrentCursor currentPosition) throws IOException {
		byte tmp = readRawByte(is, currentPosition);
		if( currentPosition.isEndOfStreamReached() ) {
			return tmp;
		}
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = readRawByte(is, currentPosition)) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = readRawByte(is, currentPosition)) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = readRawByte(is, currentPosition)) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = readRawByte(is, currentPosition)) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (readRawByte(is, currentPosition) >= 0) {
								return result;
							}
						}
						throw new IOException("malformed varint32");
					}
				}
			}
		}
		return result;
	}	
	
	public static int readRawVarint32(InputStream is) throws IOException {
		byte tmp = readRawByte(is);
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = readRawByte(is)) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = readRawByte(is)) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = readRawByte(is)) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = readRawByte(is)) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (readRawByte(is) >= 0) {
								return result;
							}
						}
						throw new IOException("malformed varint32");
					}
				}
			}
		}
		return result;
	}

	public static byte readRawByte(byte[] data, CurrentCursor currentPosition) {
		byte result = data[currentPosition.getCurrentPosition()];
		currentPosition.addToPosition(1);
		return result;
	}
	
	public static byte readRawByte(InputStream is, CurrentCursor currentPosition) throws IOException {
		int result = is.read();
		if( result == -1 ) {
			currentPosition.setEndOfStreamReached(true);
		} else {
			currentPosition.addToPosition(1);
		}
		return (byte)result;
	}	
	
	public static byte readRawByte(InputStream is) throws IOException {
		return (byte)is.read();
	}

	public static boolean isAtEnd(byte[] data, CurrentCursor cursor) {
		return cursor.getCurrentPosition() == data.length || cursor.getCurrentPosition() == cursor.getProcessUpToPosition();
	}

	public static boolean isAtEnd(CurrentCursor cursor) {
		return cursor.isEndOfStreamReached();
	}

	public static int getTagFieldNumber(final int varint) {
		return varint >>> TAG_TYPE_BITS;
	}

	private static int decodeZigZag32(final int n) {
		return (n >>> 1) ^ -(n & 1);
	}

	private static long decodeZigZag64(final long n) {
		return (n >>> 1) ^ -(n & 1);
	}

	private ProtobufInputStream() {
		//do nothing
	}

	public static long readUint64(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint64(data, cursor);
	}

	public static int readSint32(byte[] data, CurrentCursor cursor) throws IOException {
		return decodeZigZag32(readRawVarint32(data, cursor));
	}

	public static long readSint64(byte[] data, CurrentCursor cursor) throws IOException {
		return decodeZigZag64(readRawVarint64(data, cursor));
	}

	public static int readFixed32(byte[] data, CurrentCursor cursor) {
		return readRawLittleEndian32(data, cursor);
	}

	public static int readRawLittleEndian32(byte[] data, CurrentCursor cursor) {
		final byte b1 = readRawByte(data, cursor);
		final byte b2 = readRawByte(data, cursor);
		final byte b3 = readRawByte(data, cursor);
		final byte b4 = readRawByte(data, cursor);
		return (((int) b1 & 0xff)) | (((int) b2 & 0xff) << 8) | (((int) b3 & 0xff) << 16) | (((int) b4 & 0xff) << 24);
	}

	public static long readFixed64(byte[] data, CurrentCursor cursor) {
		return readRawLittleEndian64(data, cursor);
	}

	public static long readRawLittleEndian64(byte[] data, CurrentCursor cursor) {
		final byte b1 = readRawByte(data, cursor);
		final byte b2 = readRawByte(data, cursor);
		final byte b3 = readRawByte(data, cursor);
		final byte b4 = readRawByte(data, cursor);
		final byte b5 = readRawByte(data, cursor);
		final byte b6 = readRawByte(data, cursor);
		final byte b7 = readRawByte(data, cursor);
		final byte b8 = readRawByte(data, cursor);
		return (((long) b1 & 0xff)) | (((long) b2 & 0xff) << 8) | (((long) b3 & 0xff) << 16) | (((long) b4 & 0xff) << 24) | (((long) b5 & 0xff) << 32) | (((long) b6 & 0xff) << 40) | (((long) b7 & 0xff) << 48) | (((long) b8 & 0xff) << 56);
	}

	public static int readSfixed32(byte[] data, CurrentCursor cursor) {
		return readRawLittleEndian32(data, cursor);
	}

	public static long readSfixed64(byte[] data, CurrentCursor cursor) {
		return readRawLittleEndian64(data, cursor);
	}

	public static float readFloat(byte[] data, CurrentCursor cursor) {
		return Float.intBitsToFloat(readRawLittleEndian32(data, cursor));
	}

	public static double readDouble(byte[] data, CurrentCursor cursor) {
		return Double.longBitsToDouble(readRawLittleEndian64(data, cursor));
	}

	public static boolean readBool(byte[] data, CurrentCursor cursor) throws IOException {
		return readRawVarint32(data, cursor) != 0;
	}

	public static String readString(byte[] data, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(data, cursor);
		if (size <= (data.length - cursor.getCurrentPosition()) && size > 0) {
			// Fast path:  We already have the bytes in a contiguous buffer, so
			//   just copy directly from it.
			final String result = new String(data, cursor.getCurrentPosition(), size, "UTF-8");
			cursor.addToPosition(size);
			return result;
		} else {
			// Slow path:  Build a byte array first then copy it.
			return new String(readRawBytes(size, data, cursor), "UTF-8");
		}
	}

	public static byte[] readRawBytes(final int size, byte[] data, CurrentCursor cursor) throws IOException {
		if (size < 0) {
			throw new IOException("Invalid buffer size");
		}

		// We have all the bytes we need already.
		final byte[] bytes = new byte[size];
		System.arraycopy(data, cursor.getCurrentPosition(), bytes, 0, size);
		cursor.addToPosition(size);
		return bytes;
	}

	public static boolean skipUnknown(int varint, InputStream is, CurrentCursor cursor) throws IOException {
		switch (getTagWireType(varint)) {
		case WIRETYPE_VARINT:
			skipInt64(is, cursor);
			return true;
		case WIRETYPE_FIXED64:
			skipFixed64(is, cursor);
			return true;
		case WIRETYPE_LENGTH_DELIMITED:
			skipBytes(is, cursor);
			return true;
		case WIRETYPE_FIXED32:
			skipFixed32(is, cursor);
			return true;
		case WIRETYPE_GROUP_START:
			int groupStartTag = getTagFieldNumber(varint);
			int groupEndTag = skipMessage(is, cursor);
			if (groupStartTag != groupEndTag) {
				throw new IOException("invalid end tag");
			}
			return true;
		case WIRETYPE_GROUP_END:
			return false;
		default:
			throw new IOException("invalid wire type:" + getTagWireType(varint));
		}
	}

	public static int skipMessage(InputStream is, CurrentCursor cursor) throws IOException {
		while (true) {
			int varint = readRawVarint32(is, cursor);
			int tag = getTagFieldNumber(varint);
			if (tag == 0 || !skipUnknown(varint, is, cursor)) {
				return tag;
			}
		}
	}
	
	private static void skipBytes(InputStream is, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(is, cursor);
		skipBytes(size, is, cursor);
	}

	private static void skipInt64(InputStream is, CurrentCursor cursor) throws IOException {
		skipRawVarint64(is, cursor);
	}
	
	private static void skipRawVarint64(InputStream is, CurrentCursor cursor) throws IOException {
		int shift = 0;
		while (shift < 64) {
			final byte b = readRawByte(is, cursor);
			if ((b & 0x80) == 0) {
				return;
			}
			shift += 7;
		}
		throw new IOException("malformed varint64");
	}

	private static void skipFixed64(InputStream is, CurrentCursor cursor) throws IOException {
		skipRawLittleEndian64(is, cursor);
	}

	private static void skipRawLittleEndian64(InputStream is, CurrentCursor cursor) throws IOException {
		skipBytes(8, is, cursor);
	}
	
	private static void skipBytes(int count, InputStream is, CurrentCursor cursor) throws IOException {
		long actualSkipped = is.skip(count);
		if( actualSkipped != 0 && actualSkipped != count ) {
			throw new IOException("invalid bytes skipped. Expected: " + count + " skipped: " + actualSkipped);
		}
		cursor.addToPosition(count);
	}
	
	private static void skipFixed32(InputStream is, CurrentCursor cursor) throws IOException {
		skipRawLittleEndian32(is, cursor);
	}
	
	private static void skipRawLittleEndian32(InputStream is, CurrentCursor cursor) throws IOException {
		skipBytes(4, is, cursor);
	}

	public static int readInt32(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed int32");
		}
		return result;
	}

	public static long readInt64(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawVarint64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed int64");
		}
		return result;
	}

	private static long readRawVarint64(InputStream is, CurrentCursor cursor) throws IOException {
		int shift = 0;
		long result = 0;
		while (shift < 64) {
			final byte b = readRawByte(is, cursor);
			result |= (long) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				return result;
			}
			shift += 7;
		}
		throw new IOException("malformed varint64");
	}

	public static int readUint32(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Uint32");
		}
		return result;
	}

	public static long readUint64(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawVarint64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Uint64");
		}
		return result;
	}

	public static int readSint32(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Sint32");
		}
		return decodeZigZag32(result);
	}

	public static long readSint64(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawVarint64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Sint64");
		}
		return decodeZigZag64(result);
	}

	public static int readFixed32(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawLittleEndian32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Fixed32");
		}
		return result;
	}

	private static int readRawLittleEndian32(InputStream is, CurrentCursor cursor) throws IOException {
		final byte b1 = readRawByte(is, cursor);
		final byte b2 = readRawByte(is, cursor);
		final byte b3 = readRawByte(is, cursor);
		final byte b4 = readRawByte(is, cursor);
		return (((int) b1 & 0xff)) | (((int) b2 & 0xff) << 8) | (((int) b3 & 0xff) << 16) | (((int) b4 & 0xff) << 24);
	}

	public static long readFixed64(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawLittleEndian64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Fixed64");
		}
		return result;
	}

	private static long readRawLittleEndian64(InputStream is, CurrentCursor cursor) throws IOException {
		final byte b1 = readRawByte(is, cursor);
		final byte b2 = readRawByte(is, cursor);
		final byte b3 = readRawByte(is, cursor);
		final byte b4 = readRawByte(is, cursor);
		final byte b5 = readRawByte(is, cursor);
		final byte b6 = readRawByte(is, cursor);
		final byte b7 = readRawByte(is, cursor);
		final byte b8 = readRawByte(is, cursor);
		return (((long) b1 & 0xff)) | (((long) b2 & 0xff) << 8) | (((long) b3 & 0xff) << 16) | (((long) b4 & 0xff) << 24) | (((long) b5 & 0xff) << 32) | (((long) b6 & 0xff) << 40) | (((long) b7 & 0xff) << 48) | (((long) b8 & 0xff) << 56);
	}

	public static int readSfixed32(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawLittleEndian32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Sfixed32");
		}
		return result;
	}

	public static long readSfixed64(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawLittleEndian64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Sfixed64");
		}
		return result;
	}

	public static float readFloat(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawLittleEndian32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Float");
		}
		return Float.intBitsToFloat(result);
	}

	public static double readDouble(InputStream is, CurrentCursor cursor) throws IOException {
		long result = readRawLittleEndian64(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Double");
		}
		return Double.longBitsToDouble(result);
	}

	public static boolean readBool(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Bool");
		}
		return  result!= 0;
	}

	public static String readString(InputStream is, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed String");
		}
		String result = new String(readRawBytes(size, is, cursor), "UTF-8");
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed String");
		}
		return result;
	}
	
	private static byte[] readRawBytes(final int size, InputStream is, CurrentCursor cursor) throws IOException {
		if (size < 0) {
			throw new IOException("Invalid buffer size");
		}
		byte[] bytes = new byte[size];
		int totalRead = 0;
		int remainedToRead = size;
		int bytesRead = -1;
		while( (bytesRead = is.read(bytes, totalRead, Math.min(remainedToRead, size))) != -1 && remainedToRead != 0 ) {
			totalRead += bytesRead;
			remainedToRead = size - totalRead;
		}
		if( totalRead != size ) {
			throw new IOException("invalid amount of bytes read. Expected: " + size + " read: " + bytesRead);
		}
		cursor.addToPosition(size);
		return bytes;
	}

	public static byte[] readBytes(InputStream is, CurrentCursor cursor) throws IOException {
		final int size = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Bytes");
		}
		byte[] result = readRawBytes(size, is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Bytes");
		}
		return result;
	}

	public static int readEnum(InputStream is, CurrentCursor cursor) throws IOException {
		int result = readRawVarint32(is, cursor);
		if( cursor.isEndOfStreamReached() ) {
			throw new IOException("unexpected end of stream. malformed Enum");
		}
		return result;
	}

}
