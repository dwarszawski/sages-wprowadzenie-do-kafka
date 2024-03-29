/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.sages.schema;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

/** Custom Fixed Type */
@org.apache.avro.specific.AvroGenerated
public class CustomFixedType extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 1650187240349479181L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CustomFixedType\",\"namespace\":\"com.sages.schema\",\"doc\":\"Custom Fixed Type\",\"fields\":[{\"name\":\"md5\",\"type\":{\"type\":\"fixed\",\"name\":\"MD5\",\"size\":32}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CustomFixedType> ENCODER =
      new BinaryMessageEncoder<CustomFixedType>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CustomFixedType> DECODER =
      new BinaryMessageDecoder<CustomFixedType>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CustomFixedType> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CustomFixedType> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CustomFixedType> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CustomFixedType>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CustomFixedType to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CustomFixedType from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CustomFixedType instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CustomFixedType fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private com.sages.schema.MD5 md5;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CustomFixedType() {}

  /**
   * All-args constructor.
   * @param md5 The new value for md5
   */
  public CustomFixedType(com.sages.schema.MD5 md5) {
    this.md5 = md5;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return md5;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: md5 = (com.sages.schema.MD5)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'md5' field.
   * @return The value of the 'md5' field.
   */
  public com.sages.schema.MD5 getMd5() {
    return md5;
  }


  /**
   * Sets the value of the 'md5' field.
   * @param value the value to set.
   */
  public void setMd5(com.sages.schema.MD5 value) {
    this.md5 = value;
  }

  /**
   * Creates a new CustomFixedType RecordBuilder.
   * @return A new CustomFixedType RecordBuilder
   */
  public static com.sages.schema.CustomFixedType.Builder newBuilder() {
    return new com.sages.schema.CustomFixedType.Builder();
  }

  /**
   * Creates a new CustomFixedType RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CustomFixedType RecordBuilder
   */
  public static com.sages.schema.CustomFixedType.Builder newBuilder(com.sages.schema.CustomFixedType.Builder other) {
    if (other == null) {
      return new com.sages.schema.CustomFixedType.Builder();
    } else {
      return new com.sages.schema.CustomFixedType.Builder(other);
    }
  }

  /**
   * Creates a new CustomFixedType RecordBuilder by copying an existing CustomFixedType instance.
   * @param other The existing instance to copy.
   * @return A new CustomFixedType RecordBuilder
   */
  public static com.sages.schema.CustomFixedType.Builder newBuilder(com.sages.schema.CustomFixedType other) {
    if (other == null) {
      return new com.sages.schema.CustomFixedType.Builder();
    } else {
      return new com.sages.schema.CustomFixedType.Builder(other);
    }
  }

  /**
   * RecordBuilder for CustomFixedType instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CustomFixedType>
    implements org.apache.avro.data.RecordBuilder<CustomFixedType> {

    private com.sages.schema.MD5 md5;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.sages.schema.CustomFixedType.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.md5)) {
        this.md5 = data().deepCopy(fields()[0].schema(), other.md5);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing CustomFixedType instance
     * @param other The existing instance to copy.
     */
    private Builder(com.sages.schema.CustomFixedType other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.md5)) {
        this.md5 = data().deepCopy(fields()[0].schema(), other.md5);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'md5' field.
      * @return The value.
      */
    public com.sages.schema.MD5 getMd5() {
      return md5;
    }


    /**
      * Sets the value of the 'md5' field.
      * @param value The value of 'md5'.
      * @return This builder.
      */
    public com.sages.schema.CustomFixedType.Builder setMd5(com.sages.schema.MD5 value) {
      validate(fields()[0], value);
      this.md5 = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'md5' field has been set.
      * @return True if the 'md5' field has been set, false otherwise.
      */
    public boolean hasMd5() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'md5' field.
      * @return This builder.
      */
    public com.sages.schema.CustomFixedType.Builder clearMd5() {
      md5 = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CustomFixedType build() {
      try {
        CustomFixedType record = new CustomFixedType();
        record.md5 = fieldSetFlags()[0] ? this.md5 : (com.sages.schema.MD5) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CustomFixedType>
    WRITER$ = (org.apache.avro.io.DatumWriter<CustomFixedType>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CustomFixedType>
    READER$ = (org.apache.avro.io.DatumReader<CustomFixedType>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeFixed(this.md5.bytes(), 0, 32);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      if (this.md5 == null) {
        this.md5 = new com.sages.schema.MD5();
      }
      in.readFixed(this.md5.bytes(), 0, 32);

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          if (this.md5 == null) {
            this.md5 = new com.sages.schema.MD5();
          }
          in.readFixed(this.md5.bytes(), 0, 32);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










