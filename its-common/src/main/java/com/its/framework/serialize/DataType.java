package com.its.framework.serialize;

public enum DataType {
    dt_boolean("Boolean"),
    dt_BOOLEAN("BOOLEAN"),
    dt_booleans("Booleans"),
    dt_BOOLEANs("BOOLEANs"),
    dt_byte("Byte"),
    dt_BYTE("BYTE"),
    dt_bytes("Bytes"),
    dt_BYTEs("BYTEs"),
    dt_short("Short"),
    dt_SHORT("SHORT"),
    dt_shorts("Shorts"),
    dt_SHORTs("SHORTs"),
    dt_int("Int"),
    dt_INT("INT"),
    dt_ints("Ints"),
    dt_INTs("INTs"),
    dt_long("Long"),
    dt_LONG("LONG"),
    dt_longs("Longs"),
    dt_LONGs("LONGs"),
    dt_float("Float"),
    dt_FLOAT("FLOAT"),
    dt_floats("Floats"),
    dt_FLOATs("FLOATs"),
    dt_double("Double"),
    dt_DOUBLE("DOUBLE"),
    dt_doubles("Doubles"),
    dt_DOUBLEs("DOUBLEs"),
    dt_char("Char"),
    dt_CHAR("CHAR"),
    dt_chars("Chars"),
    dt_CHARs("CHARs"),
    dt_STRING("String"),
    dt_STRINGs("Strings"),
    dt_DATE("Date"),
    dt_DATEs("Dates"),
    dt_BIG_DECIMAL("BigDecimal"),
    dt_BIG_DECIMALs("BigDecimals"),
    dt_BIT_SET("BitSet"),
    dt_BIT_SETs("BitSets"),
    dt_COLLECTION("Collection"),
    dt_MAP("Map"),
    dt_ARRAY("Array"),
    dt_LARGE_MAP("LargeMap"),
    dt_LARGE_SET("LargeSet"),
    dt_OTHER("Other");

	private String name;

	private DataType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
