package org.apache.poi.ddf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

public class EscherOptRecord extends EscherRecord {
    public static final String RECORD_DESCRIPTION = "msofbtOPT";
    public static final short RECORD_ID = -4085;
    private List properties = new ArrayList();

    public int fillFields(byte[] data, int offset, EscherRecordFactory recordFactory) {
        int bytesRemaining = readHeader(data, offset);
        this.properties = new EscherPropertyFactory().createProperties(data, offset + 8, getInstance());
        return bytesRemaining + 8;
    }

    public int serialize(int offset, byte[] data, EscherSerializationListener listener) {
        listener.beforeRecordSerialize(offset, getRecordId(), this);
        LittleEndian.putShort(data, offset, getOptions());
        LittleEndian.putShort(data, offset + 2, getRecordId());
        LittleEndian.putInt(data, offset + 4, getPropertiesSize());
        int pos = offset + 8;
        for (EscherProperty escherProperty : this.properties) {
            pos += escherProperty.serializeSimplePart(data, pos);
        }
        for (EscherProperty escherProperty2 : this.properties) {
            pos += escherProperty2.serializeComplexPart(data, pos);
        }
        listener.afterRecordSerialize(pos, getRecordId(), pos - offset, this);
        return pos - offset;
    }

    public int getRecordSize() {
        return getPropertiesSize() + 8;
    }

    public short getOptions() {
        setOptions((short) ((this.properties.size() << 4) | 3));
        return super.getOptions();
    }

    public String getRecordName() {
        return "Opt";
    }

    private int getPropertiesSize() {
        int totalSize = 0;
        for (EscherProperty escherProperty : this.properties) {
            totalSize += escherProperty.getPropertySize();
        }
        return totalSize;
    }

    public String toString() {
        String nl = System.getProperty("line.separator");
        StringBuffer propertiesBuf = new StringBuffer();
        for (Object obj : this.properties) {
            StringBuilder sb = new StringBuilder();
            sb.append("    ");
            sb.append(obj.toString());
            sb.append(nl);
            propertiesBuf.append(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("org.apache.poi.ddf.EscherOptRecord:");
        sb2.append(nl);
        sb2.append("  isContainer: ");
        sb2.append(isContainerRecord());
        sb2.append(nl);
        sb2.append("  options: 0x");
        sb2.append(HexDump.toHex(getOptions()));
        sb2.append(nl);
        sb2.append("  recordId: 0x");
        sb2.append(HexDump.toHex(getRecordId()));
        sb2.append(nl);
        sb2.append("  numchildren: ");
        sb2.append(getChildRecords().size());
        sb2.append(nl);
        sb2.append("  properties:");
        sb2.append(nl);
        sb2.append(propertiesBuf.toString());
        return sb2.toString();
    }

    public List getEscherProperties() {
        return this.properties;
    }

    public EscherProperty getEscherProperty(int index) {
        return (EscherProperty) this.properties.get(index);
    }

    public void addEscherProperty(EscherProperty prop) {
        this.properties.add(prop);
    }

    public void sortProperties() {
        Collections.sort(this.properties, new Comparator() {
            public int compare(Object o1, Object o2) {
                return Short.valueOf(((EscherProperty) o1).getPropertyNumber()).compareTo(Short.valueOf(((EscherProperty) o2).getPropertyNumber()));
            }
        });
    }
}
