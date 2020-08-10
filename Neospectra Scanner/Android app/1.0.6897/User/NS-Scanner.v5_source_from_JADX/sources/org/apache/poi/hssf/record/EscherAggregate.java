package org.apache.poi.hssf.record;

import android.support.p001v4.view.InputDeviceCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ddf.DefaultEscherRecordFactory;
import org.apache.poi.ddf.EscherBoolProperty;
import org.apache.poi.ddf.EscherClientAnchorRecord;
import org.apache.poi.ddf.EscherClientDataRecord;
import org.apache.poi.ddf.EscherContainerRecord;
import org.apache.poi.ddf.EscherDgRecord;
import org.apache.poi.ddf.EscherDggRecord.FileIdCluster;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperties;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.ddf.EscherRecordFactory;
import org.apache.poi.ddf.EscherSerializationListener;
import org.apache.poi.ddf.EscherSimpleProperty;
import org.apache.poi.ddf.EscherSpRecord;
import org.apache.poi.ddf.EscherSpgrRecord;
import org.apache.poi.ddf.EscherTextboxRecord;
import org.apache.poi.hssf.model.AbstractShape;
import org.apache.poi.hssf.model.CommentShape;
import org.apache.poi.hssf.model.ConvertAnchor;
import org.apache.poi.hssf.model.DrawingManager2;
import org.apache.poi.hssf.model.TextboxShape;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFShapeContainer;
import org.apache.poi.hssf.usermodel.HSSFShapeGroup;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

public final class EscherAggregate extends AbstractEscherHolderRecord {
    public static final short ST_ACCENTBORDERCALLOUT1 = 50;
    public static final short ST_ACCENTBORDERCALLOUT2 = 51;
    public static final short ST_ACCENTBORDERCALLOUT3 = 52;
    public static final short ST_ACCENTBORDERCALLOUT90 = 181;
    public static final short ST_ACCENTCALLOUT1 = 44;
    public static final short ST_ACCENTCALLOUT2 = 45;
    public static final short ST_ACCENTCALLOUT3 = 46;
    public static final short ST_ACCENTCALLOUT90 = 179;
    public static final short ST_ACTIONBUTTONBACKPREVIOUS = 194;
    public static final short ST_ACTIONBUTTONBEGINNING = 196;
    public static final short ST_ACTIONBUTTONBLANK = 189;
    public static final short ST_ACTIONBUTTONDOCUMENT = 198;
    public static final short ST_ACTIONBUTTONEND = 195;
    public static final short ST_ACTIONBUTTONFORWARDNEXT = 193;
    public static final short ST_ACTIONBUTTONHELP = 191;
    public static final short ST_ACTIONBUTTONHOME = 190;
    public static final short ST_ACTIONBUTTONINFORMATION = 192;
    public static final short ST_ACTIONBUTTONMOVIE = 200;
    public static final short ST_ACTIONBUTTONRETURN = 197;
    public static final short ST_ACTIONBUTTONSOUND = 199;
    public static final short ST_ARC = 19;
    public static final short ST_ARROW = 13;
    public static final short ST_BALLOON = 17;
    public static final short ST_BENTARROW = 91;
    public static final short ST_BENTCONNECTOR2 = 33;
    public static final short ST_BENTCONNECTOR3 = 34;
    public static final short ST_BENTCONNECTOR4 = 35;
    public static final short ST_BENTCONNECTOR5 = 36;
    public static final short ST_BENTUPARROW = 90;
    public static final short ST_BEVEL = 84;
    public static final short ST_BLOCKARC = 95;
    public static final short ST_BORDERCALLOUT1 = 47;
    public static final short ST_BORDERCALLOUT2 = 48;
    public static final short ST_BORDERCALLOUT3 = 49;
    public static final short ST_BORDERCALLOUT90 = 180;
    public static final short ST_BRACEPAIR = 186;
    public static final short ST_BRACKETPAIR = 185;
    public static final short ST_CALLOUT1 = 41;
    public static final short ST_CALLOUT2 = 42;
    public static final short ST_CALLOUT3 = 43;
    public static final short ST_CALLOUT90 = 178;
    public static final short ST_CAN = 22;
    public static final short ST_CHEVRON = 55;
    public static final short ST_CIRCULARARROW = 99;
    public static final short ST_CLOUDCALLOUT = 106;
    public static final short ST_CUBE = 16;
    public static final short ST_CURVEDCONNECTOR2 = 37;
    public static final short ST_CURVEDCONNECTOR3 = 38;
    public static final short ST_CURVEDCONNECTOR4 = 39;
    public static final short ST_CURVEDCONNECTOR5 = 40;
    public static final short ST_CURVEDDOWNARROW = 105;
    public static final short ST_CURVEDLEFTARROW = 103;
    public static final short ST_CURVEDRIGHTARROW = 102;
    public static final short ST_CURVEDUPARROW = 104;
    public static final short ST_DIAMOND = 4;
    public static final short ST_DONUT = 23;
    public static final short ST_DOUBLEWAVE = 188;
    public static final short ST_DOWNARROW = 67;
    public static final short ST_DOWNARROWCALLOUT = 80;
    public static final short ST_ELLIPSE = 3;
    public static final short ST_ELLIPSERIBBON = 107;
    public static final short ST_ELLIPSERIBBON2 = 108;
    public static final short ST_FLOWCHARTALTERNATEPROCESS = 176;
    public static final short ST_FLOWCHARTCOLLATE = 125;
    public static final short ST_FLOWCHARTCONNECTOR = 120;
    public static final short ST_FLOWCHARTDECISION = 110;
    public static final short ST_FLOWCHARTDELAY = 135;
    public static final short ST_FLOWCHARTDISPLAY = 134;
    public static final short ST_FLOWCHARTDOCUMENT = 114;
    public static final short ST_FLOWCHARTEXTRACT = 127;
    public static final short ST_FLOWCHARTINPUTOUTPUT = 111;
    public static final short ST_FLOWCHARTINTERNALSTORAGE = 113;
    public static final short ST_FLOWCHARTMAGNETICDISK = 132;
    public static final short ST_FLOWCHARTMAGNETICDRUM = 133;
    public static final short ST_FLOWCHARTMAGNETICTAPE = 131;
    public static final short ST_FLOWCHARTMANUALINPUT = 118;
    public static final short ST_FLOWCHARTMANUALOPERATION = 119;
    public static final short ST_FLOWCHARTMERGE = 128;
    public static final short ST_FLOWCHARTMULTIDOCUMENT = 115;
    public static final short ST_FLOWCHARTOFFLINESTORAGE = 129;
    public static final short ST_FLOWCHARTOFFPAGECONNECTOR = 177;
    public static final short ST_FLOWCHARTONLINESTORAGE = 130;
    public static final short ST_FLOWCHARTOR = 124;
    public static final short ST_FLOWCHARTPREDEFINEDPROCESS = 112;
    public static final short ST_FLOWCHARTPREPARATION = 117;
    public static final short ST_FLOWCHARTPROCESS = 109;
    public static final short ST_FLOWCHARTPUNCHEDCARD = 121;
    public static final short ST_FLOWCHARTPUNCHEDTAPE = 122;
    public static final short ST_FLOWCHARTSORT = 126;
    public static final short ST_FLOWCHARTSUMMINGJUNCTION = 123;
    public static final short ST_FLOWCHARTTERMINATOR = 116;
    public static final short ST_FOLDEDCORNER = 65;
    public static final short ST_HEART = 74;
    public static final short ST_HEXAGON = 9;
    public static final short ST_HOMEPLATE = 15;
    public static final short ST_HORIZONTALSCROLL = 98;
    public static final short ST_HOSTCONTROL = 201;
    public static final short ST_IRREGULARSEAL1 = 71;
    public static final short ST_IRREGULARSEAL2 = 72;
    public static final short ST_ISOCELESTRIANGLE = 5;
    public static final short ST_LEFTARROW = 66;
    public static final short ST_LEFTARROWCALLOUT = 77;
    public static final short ST_LEFTBRACE = 87;
    public static final short ST_LEFTBRACKET = 85;
    public static final short ST_LEFTRIGHTARROW = 69;
    public static final short ST_LEFTRIGHTARROWCALLOUT = 81;
    public static final short ST_LEFTRIGHTUPARROW = 182;
    public static final short ST_LEFTUPARROW = 89;
    public static final short ST_LIGHTNINGBOLT = 73;
    public static final short ST_LINE = 20;
    public static final short ST_MIN = 0;
    public static final short ST_MOON = 184;
    public static final short ST_NIL = 4095;
    public static final short ST_NOSMOKING = 57;
    public static final short ST_NOTCHEDCIRCULARARROW = 100;
    public static final short ST_NOTCHEDRIGHTARROW = 94;
    public static final short ST_NOT_PRIMATIVE = 0;
    public static final short ST_OCTAGON = 10;
    public static final short ST_PARALLELOGRAM = 7;
    public static final short ST_PENTAGON = 56;
    public static final short ST_PICTUREFRAME = 75;
    public static final short ST_PLAQUE = 21;
    public static final short ST_PLUS = 11;
    public static final short ST_QUADARROW = 76;
    public static final short ST_QUADARROWCALLOUT = 83;
    public static final short ST_RECTANGLE = 1;
    public static final short ST_RIBBON = 53;
    public static final short ST_RIBBON2 = 54;
    public static final short ST_RIGHTARROWCALLOUT = 78;
    public static final short ST_RIGHTBRACE = 88;
    public static final short ST_RIGHTBRACKET = 86;
    public static final short ST_RIGHTTRIANGLE = 6;
    public static final short ST_ROUNDRECTANGLE = 2;
    public static final short ST_SEAL = 18;
    public static final short ST_SEAL16 = 59;
    public static final short ST_SEAL24 = 92;
    public static final short ST_SEAL32 = 60;
    public static final short ST_SEAL4 = 187;
    public static final short ST_SEAL8 = 58;
    public static final short ST_SMILEYFACE = 96;
    public static final short ST_STAR = 12;
    public static final short ST_STRAIGHTCONNECTOR1 = 32;
    public static final short ST_STRIPEDRIGHTARROW = 93;
    public static final short ST_SUN = 183;
    public static final short ST_TEXTARCHDOWNCURVE = 145;
    public static final short ST_TEXTARCHDOWNPOUR = 149;
    public static final short ST_TEXTARCHUPCURVE = 144;
    public static final short ST_TEXTARCHUPPOUR = 148;
    public static final short ST_TEXTBOX = 202;
    public static final short ST_TEXTBUTTONCURVE = 147;
    public static final short ST_TEXTBUTTONPOUR = 151;
    public static final short ST_TEXTCANDOWN = 175;
    public static final short ST_TEXTCANUP = 174;
    public static final short ST_TEXTCASCADEDOWN = 155;
    public static final short ST_TEXTCASCADEUP = 154;
    public static final short ST_TEXTCHEVRON = 140;
    public static final short ST_TEXTCHEVRONINVERTED = 141;
    public static final short ST_TEXTCIRCLECURVE = 146;
    public static final short ST_TEXTCIRCLEPOUR = 150;
    public static final short ST_TEXTCURVE = 27;
    public static final short ST_TEXTCURVEDOWN = 153;
    public static final short ST_TEXTCURVEUP = 152;
    public static final short ST_TEXTDEFLATE = 161;
    public static final short ST_TEXTDEFLATEBOTTOM = 163;
    public static final short ST_TEXTDEFLATEINFLATE = 166;
    public static final short ST_TEXTDEFLATEINFLATEDEFLATE = 167;
    public static final short ST_TEXTDEFLATETOP = 165;
    public static final short ST_TEXTFADEDOWN = 171;
    public static final short ST_TEXTFADELEFT = 169;
    public static final short ST_TEXTFADERIGHT = 168;
    public static final short ST_TEXTFADEUP = 170;
    public static final short ST_TEXTHEXAGON = 26;
    public static final short ST_TEXTINFLATE = 160;
    public static final short ST_TEXTINFLATEBOTTOM = 162;
    public static final short ST_TEXTINFLATETOP = 164;
    public static final short ST_TEXTOCTAGON = 25;
    public static final short ST_TEXTONCURVE = 30;
    public static final short ST_TEXTONRING = 31;
    public static final short ST_TEXTPLAINTEXT = 136;
    public static final short ST_TEXTRING = 29;
    public static final short ST_TEXTRINGINSIDE = 142;
    public static final short ST_TEXTRINGOUTSIDE = 143;
    public static final short ST_TEXTSIMPLE = 24;
    public static final short ST_TEXTSLANTDOWN = 173;
    public static final short ST_TEXTSLANTUP = 172;
    public static final short ST_TEXTSTOP = 137;
    public static final short ST_TEXTTRIANGLE = 138;
    public static final short ST_TEXTTRIANGLEINVERTED = 139;
    public static final short ST_TEXTWAVE = 28;
    public static final short ST_TEXTWAVE1 = 156;
    public static final short ST_TEXTWAVE2 = 157;
    public static final short ST_TEXTWAVE3 = 158;
    public static final short ST_TEXTWAVE4 = 159;
    public static final short ST_THICKARROW = 14;
    public static final short ST_TRAPEZOID = 8;
    public static final short ST_UPARROW = 68;
    public static final short ST_UPARROWCALLOUT = 79;
    public static final short ST_UPDOWNARROW = 70;
    public static final short ST_UPDOWNARROWCALLOUT = 82;
    public static final short ST_UTURNARROW = 101;
    public static final short ST_VERTICALSCROLL = 97;
    public static final short ST_WAVE = 64;
    public static final short ST_WEDGEELLIPSECALLOUT = 63;
    public static final short ST_WEDGERECTCALLOUT = 61;
    public static final short ST_WEDGERRECTCALLOUT = 62;
    private static POILogger log = POILogFactory.getLogger(EscherAggregate.class);
    public static final short sid = 9876;
    private short drawingGroupId;
    private DrawingManager2 drawingManager;
    protected HSSFPatriarch patriarch;
    private Map<EscherRecord, Record> shapeToObj = new HashMap();
    private List tailRec = new ArrayList();

    public EscherAggregate(DrawingManager2 drawingManager2) {
        this.drawingManager = drawingManager2;
    }

    public short getSid() {
        return sid;
    }

    public String toString() {
        String nl = System.getProperty("line.separtor");
        StringBuffer result = new StringBuffer();
        result.append('[');
        result.append(getRecordName());
        StringBuilder sb = new StringBuilder();
        sb.append(']');
        sb.append(nl);
        result.append(sb.toString());
        for (EscherRecord escherRecord : getEscherRecords()) {
            result.append(escherRecord.toString());
        }
        result.append("[/");
        result.append(getRecordName());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(']');
        sb2.append(nl);
        result.append(sb2.toString());
        return result.toString();
    }

    public static EscherAggregate createAggregate(List records, int locFirstDrawingRecord, DrawingManager2 drawingManager2) {
        final List<EscherRecord> shapeRecords = new ArrayList<>();
        EscherRecordFactory recordFactory = new DefaultEscherRecordFactory() {
            public EscherRecord createRecord(byte[] data, int offset) {
                EscherRecord r = super.createRecord(data, offset);
                if (r.getRecordId() == -4079 || r.getRecordId() == -4083) {
                    shapeRecords.add(r);
                }
                return r;
            }
        };
        EscherAggregate agg = new EscherAggregate(drawingManager2);
        int pos = 0;
        int loc = locFirstDrawingRecord;
        int dataSize = 0;
        while (loc + 1 < records.size() && sid(records, loc) == 236 && isObjectRecord(records, loc + 1)) {
            dataSize += ((DrawingRecord) records.get(loc)).getData().length;
            loc += 2;
        }
        byte[] buffer = new byte[dataSize];
        int offset = 0;
        int loc2 = locFirstDrawingRecord;
        while (loc2 + 1 < records.size() && sid(records, loc2) == 236 && isObjectRecord(records, loc2 + 1)) {
            DrawingRecord drawingRecord = (DrawingRecord) records.get(loc2);
            System.arraycopy(drawingRecord.getData(), 0, buffer, offset, drawingRecord.getData().length);
            offset += drawingRecord.getData().length;
            loc2 += 2;
        }
        while (pos < dataSize) {
            EscherRecord r = recordFactory.createRecord(buffer, pos);
            int bytesRead = r.fillFields(buffer, pos, recordFactory);
            agg.addEscherRecord(r);
            pos += bytesRead;
        }
        int loc3 = locFirstDrawingRecord;
        int shapeIndex = 0;
        agg.shapeToObj = new HashMap();
        while (loc3 + 1 < records.size() && sid(records, loc3) == 236 && isObjectRecord(records, loc3 + 1)) {
            int shapeIndex2 = shapeIndex + 1;
            agg.shapeToObj.put(shapeRecords.get(shapeIndex), (Record) records.get(loc3 + 1));
            loc3 += 2;
            shapeIndex = shapeIndex2;
        }
        return agg;
    }

    public int serialize(int offset, byte[] data) {
        int startOffset;
        byte[] bArr = data;
        convertUserModelToRecords();
        List<EscherRecord> records = getEscherRecords();
        byte[] buffer = new byte[getEscherRecordSize(records)];
        final List spEndingOffsets = new ArrayList();
        final List shapes = new ArrayList();
        int pos = 0;
        for (EscherRecord e : records) {
            pos += e.serialize(pos, buffer, new EscherSerializationListener() {
                public void beforeRecordSerialize(int offset, short recordId, EscherRecord record) {
                }

                public void afterRecordSerialize(int offset, short recordId, int size, EscherRecord record) {
                    if (recordId == -4079 || recordId == -4083) {
                        spEndingOffsets.add(Integer.valueOf(offset));
                        shapes.add(record);
                    }
                }
            });
        }
        int i = 0;
        shapes.add(0, null);
        spEndingOffsets.add(0, null);
        int i2 = 1;
        int pos2 = offset;
        int i3 = 1;
        while (i3 < shapes.size()) {
            int endOffset = ((Integer) spEndingOffsets.get(i3)).intValue() - i2;
            if (i3 == i2) {
                startOffset = 0;
            } else {
                startOffset = ((Integer) spEndingOffsets.get(i3 - 1)).intValue();
            }
            DrawingRecord drawing = new DrawingRecord();
            byte[] drawingData = new byte[((endOffset - startOffset) + i2)];
            System.arraycopy(buffer, startOffset, drawingData, i, drawingData.length);
            drawing.setData(drawingData);
            int pos3 = pos2 + drawing.serialize(pos2, bArr);
            pos2 = pos3 + ((Record) this.shapeToObj.get(shapes.get(i3))).serialize(pos3, bArr);
            i3++;
            i2 = 1;
            i = 0;
        }
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= this.tailRec.size()) {
                break;
            }
            pos2 += ((Record) this.tailRec.get(i5)).serialize(pos2, bArr);
            i4 = i5 + 1;
        }
        int bytesWritten = pos2 - offset;
        if (bytesWritten == getRecordSize()) {
            return bytesWritten;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(bytesWritten);
        sb.append(" bytes written but getRecordSize() reports ");
        sb.append(getRecordSize());
        throw new RecordFormatException(sb.toString());
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<org.apache.poi.ddf.EscherRecord>, for r4v0, types: [java.util.List, java.util.List<org.apache.poi.ddf.EscherRecord>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getEscherRecordSize(java.util.List<org.apache.poi.ddf.EscherRecord> r4) {
        /*
            r3 = this;
            r0 = 0
            java.util.Iterator r1 = r4.iterator()
        L_0x0005:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0017
            java.lang.Object r2 = r1.next()
            org.apache.poi.ddf.EscherRecord r2 = (org.apache.poi.ddf.EscherRecord) r2
            int r2 = r2.getRecordSize()
            int r0 = r0 + r2
            goto L_0x0005
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.record.EscherAggregate.getEscherRecordSize(java.util.List):int");
    }

    public int getRecordSize() {
        convertUserModelToRecords();
        int drawingRecordSize = (this.shapeToObj.size() * 4) + getEscherRecordSize(getEscherRecords());
        int objRecordSize = 0;
        for (Record r : this.shapeToObj.values()) {
            objRecordSize += r.getRecordSize();
        }
        int tailRecordSize = 0;
        for (Record r2 : this.tailRec) {
            tailRecordSize += r2.getRecordSize();
        }
        return drawingRecordSize + objRecordSize + tailRecordSize;
    }

    /* access modifiers changed from: 0000 */
    public Object associateShapeToObjRecord(EscherRecord r, ObjRecord objRecord) {
        return this.shapeToObj.put(r, objRecord);
    }

    public HSSFPatriarch getPatriarch() {
        return this.patriarch;
    }

    public void setPatriarch(HSSFPatriarch patriarch2) {
        this.patriarch = patriarch2;
    }

    public void convertRecordsToUserModel() {
        EscherContainerRecord topContainer;
        if (this.patriarch == null) {
            throw new IllegalStateException("Must call setPatriarch() first");
        }
        EscherContainerRecord topContainer2 = getEscherContainer();
        if (topContainer2 != null) {
            int i = 0;
            EscherContainerRecord topContainer3 = (EscherContainerRecord) topContainer2.getChildContainers().get(0);
            List tcc = topContainer3.getChildContainers();
            if (tcc.size() == 0) {
                throw new IllegalStateException("No child escher containers at the point that should hold the patriach data, and one container per top level shape!");
            }
            EscherSpgrRecord spgr = null;
            Iterator<EscherRecord> it = ((EscherContainerRecord) tcc.get(0)).getChildIterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                EscherRecord r = (EscherRecord) it.next();
                if (r instanceof EscherSpgrRecord) {
                    spgr = (EscherSpgrRecord) r;
                    break;
                }
            }
            if (spgr != null) {
                this.patriarch.setCoordinates(spgr.getRectX1(), spgr.getRectY1(), spgr.getRectX2(), spgr.getRectY2());
            }
            int i2 = 1;
            HSSFShape hSSFShape = null;
            while (i2 < tcc.size()) {
                EscherContainerRecord shapeContainer = (EscherContainerRecord) tcc.get(i2);
                if (shapeContainer.getRecordId() == -4093) {
                    if (shapeContainer.getChildRecords().size() > 0) {
                        HSSFShapeGroup group = new HSSFShapeGroup(hSSFShape, new HSSFClientAnchor());
                        this.patriarch.getChildren().add(group);
                        convertRecordsToUserModel((EscherContainerRecord) shapeContainer.getChild(i), group);
                    } else {
                        log.log(POILogger.WARN, (Object) "Found drawing group without children.");
                    }
                    topContainer = topContainer3;
                } else if (shapeContainer.getRecordId() == -4092) {
                    int type = shapeContainer.getChildById(EscherSpRecord.RECORD_ID).getOptions() >> 4;
                    if (type == 75) {
                        EscherSimpleProperty prop = (EscherSimpleProperty) getEscherProperty((EscherOptRecord) getEscherChild(shapeContainer, -4085), 260);
                        if (prop == null) {
                            log.log(POILogger.WARN, (Object) "Picture index for picture shape not found.");
                        } else {
                            int pictureIndex = prop.getPropertyValue();
                            EscherClientAnchorRecord anchorRecord = (EscherClientAnchorRecord) getEscherChild(shapeContainer, -4080);
                            HSSFClientAnchor anchor = new HSSFClientAnchor();
                            anchor.setCol1(anchorRecord.getCol1());
                            anchor.setCol2(anchorRecord.getCol2());
                            anchor.setDx1(anchorRecord.getDx1());
                            anchor.setDx2(anchorRecord.getDx2());
                            anchor.setDy1(anchorRecord.getDy1());
                            anchor.setDy2(anchorRecord.getDy2());
                            anchor.setRow1(anchorRecord.getRow1());
                            anchor.setRow2(anchorRecord.getRow2());
                            topContainer = topContainer3;
                            HSSFPicture picture = new HSSFPicture(null, anchor);
                            picture.setPictureIndex(pictureIndex);
                            this.patriarch.getChildren().add(picture);
                        }
                    } else if (type != 202) {
                        POILogger pOILogger = log;
                        int i3 = POILogger.WARN;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unhandled shape type: ");
                        sb.append(type);
                        pOILogger.log(i3, (Object) sb.toString());
                    } else {
                        HSSFTextbox box = new HSSFTextbox(null, new HSSFClientAnchor());
                        this.patriarch.getChildren().add(box);
                        convertRecordsToUserModel(shapeContainer, box);
                    }
                    topContainer = topContainer3;
                } else {
                    topContainer = topContainer3;
                    log.log(POILogger.WARN, (Object) "Unexpected record id of shape group.");
                }
                i2++;
                topContainer3 = topContainer;
                i = 0;
                hSSFShape = null;
            }
            this.drawingManager.getDgg().setFileIdClusters(new FileIdCluster[0]);
        }
    }

    private void convertRecordsToUserModel(EscherContainerRecord shapeContainer, Object model) {
        Iterator<EscherRecord> it = shapeContainer.getChildIterator();
        while (it.hasNext()) {
            EscherRecord r = (EscherRecord) it.next();
            if (r instanceof EscherSpgrRecord) {
                EscherSpgrRecord spgr = (EscherSpgrRecord) r;
                if (model instanceof HSSFShapeGroup) {
                    ((HSSFShapeGroup) model).setCoordinates(spgr.getRectX1(), spgr.getRectY1(), spgr.getRectX2(), spgr.getRectY2());
                } else {
                    throw new IllegalStateException("Got top level anchor but not processing a group");
                }
            } else if (r instanceof EscherClientAnchorRecord) {
                EscherClientAnchorRecord car = (EscherClientAnchorRecord) r;
                if (model instanceof HSSFShape) {
                    HSSFShape g = (HSSFShape) model;
                    g.getAnchor().setDx1(car.getDx1());
                    g.getAnchor().setDx2(car.getDx2());
                    g.getAnchor().setDy1(car.getDy1());
                    g.getAnchor().setDy2(car.getDy2());
                } else {
                    throw new IllegalStateException("Got top level anchor but not processing a group or shape");
                }
            } else if (r instanceof EscherTextboxRecord) {
                EscherTextboxRecord escherTextboxRecord = (EscherTextboxRecord) r;
            } else if (!(r instanceof EscherSpRecord)) {
                boolean z = r instanceof EscherOptRecord;
            }
        }
    }

    public void clear() {
        clearEscherRecords();
        this.shapeToObj.clear();
    }

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "ESCHERAGGREGATE";
    }

    private static boolean isObjectRecord(List records, int loc) {
        return sid(records, loc) == 93 || sid(records, loc) == 438;
    }

    private void convertUserModelToRecords() {
        if (this.patriarch != null) {
            this.shapeToObj.clear();
            this.tailRec.clear();
            clearEscherRecords();
            if (this.patriarch.getChildren().size() != 0) {
                convertPatriarch(this.patriarch);
                EscherContainerRecord spgrContainer = null;
                Iterator<EscherRecord> iter = ((EscherContainerRecord) getEscherRecord(0)).getChildIterator();
                while (iter.hasNext()) {
                    EscherRecord child = (EscherRecord) iter.next();
                    if (child.getRecordId() == -4093) {
                        spgrContainer = (EscherContainerRecord) child;
                    }
                }
                convertShapes(this.patriarch, spgrContainer, this.shapeToObj);
                this.patriarch = null;
            }
        }
    }

    private void convertShapes(HSSFShapeContainer parent, EscherContainerRecord escherParent, Map shapeToObj2) {
        if (escherParent == null) {
            throw new IllegalArgumentException("Parent record required");
        }
        for (HSSFShape shape : parent.getChildren()) {
            if (shape instanceof HSSFShapeGroup) {
                convertGroup((HSSFShapeGroup) shape, escherParent, shapeToObj2);
            } else {
                AbstractShape shapeModel = AbstractShape.createShape(shape, this.drawingManager.allocateShapeId(this.drawingGroupId));
                shapeToObj2.put(findClientData(shapeModel.getSpContainer()), shapeModel.getObjRecord());
                if (shapeModel instanceof TextboxShape) {
                    shapeToObj2.put(((TextboxShape) shapeModel).getEscherTextbox(), ((TextboxShape) shapeModel).getTextObjectRecord());
                    if (shapeModel instanceof CommentShape) {
                        this.tailRec.add(((CommentShape) shapeModel).getNoteRecord());
                    }
                }
                escherParent.addChildRecord(shapeModel.getSpContainer());
            }
        }
    }

    private void convertGroup(HSSFShapeGroup shape, EscherContainerRecord escherParent, Map shapeToObj2) {
        EscherContainerRecord spgrContainer = new EscherContainerRecord();
        EscherContainerRecord spContainer = new EscherContainerRecord();
        EscherSpgrRecord spgr = new EscherSpgrRecord();
        EscherSpRecord sp = new EscherSpRecord();
        EscherOptRecord opt = new EscherOptRecord();
        EscherClientDataRecord clientData = new EscherClientDataRecord();
        spgrContainer.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        spgrContainer.setOptions(15);
        spContainer.setRecordId(EscherContainerRecord.SP_CONTAINER);
        spContainer.setOptions(15);
        spgr.setRecordId(EscherSpgrRecord.RECORD_ID);
        spgr.setOptions(1);
        spgr.setRectX1(shape.getX1());
        spgr.setRectY1(shape.getY1());
        spgr.setRectX2(shape.getX2());
        spgr.setRectY2(shape.getY2());
        sp.setRecordId(EscherSpRecord.RECORD_ID);
        sp.setOptions(2);
        int shapeId = this.drawingManager.allocateShapeId(this.drawingGroupId);
        sp.setShapeId(shapeId);
        if (shape.getAnchor() instanceof HSSFClientAnchor) {
            sp.setFlags(InputDeviceCompat.SOURCE_DPAD);
        } else {
            sp.setFlags(515);
        }
        opt.setRecordId(EscherOptRecord.RECORD_ID);
        opt.setOptions(35);
        opt.addEscherProperty(new EscherBoolProperty(127, 262148));
        opt.addEscherProperty(new EscherBoolProperty(EscherProperties.GROUPSHAPE__PRINT, 524288));
        EscherRecord anchor = ConvertAnchor.createAnchor(shape.getAnchor());
        clientData.setRecordId(EscherClientDataRecord.RECORD_ID);
        clientData.setOptions(0);
        spgrContainer.addChildRecord(spContainer);
        spContainer.addChildRecord(spgr);
        spContainer.addChildRecord(sp);
        spContainer.addChildRecord(opt);
        spContainer.addChildRecord(anchor);
        spContainer.addChildRecord(clientData);
        ObjRecord obj = new ObjRecord();
        CommonObjectDataSubRecord cmo = new CommonObjectDataSubRecord();
        cmo.setObjectType(0);
        cmo.setObjectId(shapeId);
        cmo.setLocked(true);
        cmo.setPrintable(true);
        cmo.setAutofill(true);
        cmo.setAutoline(true);
        GroupMarkerSubRecord gmo = new GroupMarkerSubRecord();
        EndSubRecord end = new EndSubRecord();
        obj.addSubRecord(cmo);
        obj.addSubRecord(gmo);
        obj.addSubRecord(end);
        shapeToObj2.put(clientData, obj);
        escherParent.addChildRecord(spgrContainer);
        convertShapes(shape, spgrContainer, shapeToObj2);
    }

    private EscherRecord findClientData(EscherContainerRecord spContainer) {
        Iterator<EscherRecord> iterator = spContainer.getChildIterator();
        while (iterator.hasNext()) {
            EscherRecord r = (EscherRecord) iterator.next();
            if (r.getRecordId() == -4079) {
                return r;
            }
        }
        throw new IllegalArgumentException("Can not find client data record");
    }

    private void convertPatriarch(HSSFPatriarch patriarch2) {
        EscherContainerRecord dgContainer = new EscherContainerRecord();
        EscherContainerRecord spgrContainer = new EscherContainerRecord();
        EscherContainerRecord spContainer1 = new EscherContainerRecord();
        EscherSpgrRecord spgr = new EscherSpgrRecord();
        EscherSpRecord sp1 = new EscherSpRecord();
        dgContainer.setRecordId(EscherContainerRecord.DG_CONTAINER);
        dgContainer.setOptions(15);
        EscherDgRecord dg = this.drawingManager.createDgRecord();
        this.drawingGroupId = dg.getDrawingGroupId();
        spgrContainer.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        spgrContainer.setOptions(15);
        spContainer1.setRecordId(EscherContainerRecord.SP_CONTAINER);
        spContainer1.setOptions(15);
        spgr.setRecordId(EscherSpgrRecord.RECORD_ID);
        spgr.setOptions(1);
        spgr.setRectX1(patriarch2.getX1());
        spgr.setRectY1(patriarch2.getY1());
        spgr.setRectX2(patriarch2.getX2());
        spgr.setRectY2(patriarch2.getY2());
        sp1.setRecordId(EscherSpRecord.RECORD_ID);
        sp1.setOptions(2);
        sp1.setShapeId(this.drawingManager.allocateShapeId(dg.getDrawingGroupId()));
        sp1.setFlags(5);
        dgContainer.addChildRecord(dg);
        dgContainer.addChildRecord(spgrContainer);
        spgrContainer.addChildRecord(spContainer1);
        spContainer1.addChildRecord(spgr);
        spContainer1.addChildRecord(sp1);
        addEscherRecord(dgContainer);
    }

    private static short sid(List records, int loc) {
        return ((Record) records.get(loc)).getSid();
    }

    private static EscherRecord getEscherChild(EscherContainerRecord owner, int recordId) {
        for (EscherRecord escherRecord : owner.getChildRecords()) {
            if (escherRecord.getRecordId() == recordId) {
                return escherRecord;
            }
        }
        return null;
    }

    private static EscherProperty getEscherProperty(EscherOptRecord opt, int propId) {
        if (opt != null) {
            for (EscherProperty prop : opt.getEscherProperties()) {
                if (prop.getPropertyNumber() == propId) {
                    return prop;
                }
            }
        }
        return null;
    }
}
