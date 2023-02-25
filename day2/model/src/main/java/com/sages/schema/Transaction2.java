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

/** Bank Transaction (logical types) */
@org.apache.avro.specific.AvroGenerated
public class Transaction2 extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7151926863176919945L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Transaction2\",\"namespace\":\"com.sages.schema\",\"doc\":\"Bank Transaction (logical types)\",\"fields\":[{\"name\":\"transactionId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"transactionDate\",\"type\":{\"type\":\"int\",\"logicalType\":\"date\"}},{\"name\":\"transactionDateTime\",\"type\":{\"type\":\"int\",\"logicalType\":\"time-millis\"}},{\"name\":\"transactionTimeStamp\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"amount\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":20,\"scale\":5}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();
static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.DateConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimeMillisConversion());
  }

  private static final BinaryMessageEncoder<Transaction2> ENCODER =
      new BinaryMessageEncoder<Transaction2>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Transaction2> DECODER =
      new BinaryMessageDecoder<Transaction2>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Transaction2> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Transaction2> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Transaction2> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Transaction2>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Transaction2 to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Transaction2 from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Transaction2 instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Transaction2 fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence transactionId;
   private java.time.LocalDate transactionDate;
   private java.time.LocalTime transactionDateTime;
   private java.time.Instant transactionTimeStamp;
   private java.nio.ByteBuffer amount;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Transaction2() {}

  /**
   * All-args constructor.
   * @param transactionId The new value for transactionId
   * @param transactionDate The new value for transactionDate
   * @param transactionDateTime The new value for transactionDateTime
   * @param transactionTimeStamp The new value for transactionTimeStamp
   * @param amount The new value for amount
   */
  public Transaction2(java.lang.CharSequence transactionId, java.time.LocalDate transactionDate, java.time.LocalTime transactionDateTime, java.time.Instant transactionTimeStamp, java.nio.ByteBuffer amount) {
    this.transactionId = transactionId;
    this.transactionDate = transactionDate;
    this.transactionDateTime = transactionDateTime.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.transactionTimeStamp = transactionTimeStamp.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.amount = amount;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return transactionId;
    case 1: return transactionDate;
    case 2: return transactionDateTime;
    case 3: return transactionTimeStamp;
    case 4: return amount;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      new org.apache.avro.data.TimeConversions.DateConversion(),
      new org.apache.avro.data.TimeConversions.TimeMillisConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null,
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: transactionId = (java.lang.CharSequence)value$; break;
    case 1: transactionDate = (java.time.LocalDate)value$; break;
    case 2: transactionDateTime = (java.time.LocalTime)value$; break;
    case 3: transactionTimeStamp = (java.time.Instant)value$; break;
    case 4: amount = (java.nio.ByteBuffer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'transactionId' field.
   * @return The value of the 'transactionId' field.
   */
  public java.lang.CharSequence getTransactionId() {
    return transactionId;
  }


  /**
   * Sets the value of the 'transactionId' field.
   * @param value the value to set.
   */
  public void setTransactionId(java.lang.CharSequence value) {
    this.transactionId = value;
  }

  /**
   * Gets the value of the 'transactionDate' field.
   * @return The value of the 'transactionDate' field.
   */
  public java.time.LocalDate getTransactionDate() {
    return transactionDate;
  }


  /**
   * Sets the value of the 'transactionDate' field.
   * @param value the value to set.
   */
  public void setTransactionDate(java.time.LocalDate value) {
    this.transactionDate = value;
  }

  /**
   * Gets the value of the 'transactionDateTime' field.
   * @return The value of the 'transactionDateTime' field.
   */
  public java.time.LocalTime getTransactionDateTime() {
    return transactionDateTime;
  }


  /**
   * Sets the value of the 'transactionDateTime' field.
   * @param value the value to set.
   */
  public void setTransactionDateTime(java.time.LocalTime value) {
    this.transactionDateTime = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Gets the value of the 'transactionTimeStamp' field.
   * @return The value of the 'transactionTimeStamp' field.
   */
  public java.time.Instant getTransactionTimeStamp() {
    return transactionTimeStamp;
  }


  /**
   * Sets the value of the 'transactionTimeStamp' field.
   * @param value the value to set.
   */
  public void setTransactionTimeStamp(java.time.Instant value) {
    this.transactionTimeStamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public java.nio.ByteBuffer getAmount() {
    return amount;
  }


  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(java.nio.ByteBuffer value) {
    this.amount = value;
  }

  /**
   * Creates a new Transaction2 RecordBuilder.
   * @return A new Transaction2 RecordBuilder
   */
  public static com.sages.schema.Transaction2.Builder newBuilder() {
    return new com.sages.schema.Transaction2.Builder();
  }

  /**
   * Creates a new Transaction2 RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Transaction2 RecordBuilder
   */
  public static com.sages.schema.Transaction2.Builder newBuilder(com.sages.schema.Transaction2.Builder other) {
    if (other == null) {
      return new com.sages.schema.Transaction2.Builder();
    } else {
      return new com.sages.schema.Transaction2.Builder(other);
    }
  }

  /**
   * Creates a new Transaction2 RecordBuilder by copying an existing Transaction2 instance.
   * @param other The existing instance to copy.
   * @return A new Transaction2 RecordBuilder
   */
  public static com.sages.schema.Transaction2.Builder newBuilder(com.sages.schema.Transaction2 other) {
    if (other == null) {
      return new com.sages.schema.Transaction2.Builder();
    } else {
      return new com.sages.schema.Transaction2.Builder(other);
    }
  }

  /**
   * RecordBuilder for Transaction2 instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Transaction2>
    implements org.apache.avro.data.RecordBuilder<Transaction2> {

    private java.lang.CharSequence transactionId;
    private java.time.LocalDate transactionDate;
    private java.time.LocalTime transactionDateTime;
    private java.time.Instant transactionTimeStamp;
    private java.nio.ByteBuffer amount;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.sages.schema.Transaction2.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[0].schema(), other.transactionId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.transactionDate)) {
        this.transactionDate = data().deepCopy(fields()[1].schema(), other.transactionDate);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.transactionDateTime)) {
        this.transactionDateTime = data().deepCopy(fields()[2].schema(), other.transactionDateTime);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.transactionTimeStamp)) {
        this.transactionTimeStamp = data().deepCopy(fields()[3].schema(), other.transactionTimeStamp);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.amount)) {
        this.amount = data().deepCopy(fields()[4].schema(), other.amount);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
    }

    /**
     * Creates a Builder by copying an existing Transaction2 instance
     * @param other The existing instance to copy.
     */
    private Builder(com.sages.schema.Transaction2 other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[0].schema(), other.transactionId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.transactionDate)) {
        this.transactionDate = data().deepCopy(fields()[1].schema(), other.transactionDate);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.transactionDateTime)) {
        this.transactionDateTime = data().deepCopy(fields()[2].schema(), other.transactionDateTime);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.transactionTimeStamp)) {
        this.transactionTimeStamp = data().deepCopy(fields()[3].schema(), other.transactionTimeStamp);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.amount)) {
        this.amount = data().deepCopy(fields()[4].schema(), other.amount);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'transactionId' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransactionId() {
      return transactionId;
    }


    /**
      * Sets the value of the 'transactionId' field.
      * @param value The value of 'transactionId'.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder setTransactionId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.transactionId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionId' field has been set.
      * @return True if the 'transactionId' field has been set, false otherwise.
      */
    public boolean hasTransactionId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'transactionId' field.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder clearTransactionId() {
      transactionId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionDate' field.
      * @return The value.
      */
    public java.time.LocalDate getTransactionDate() {
      return transactionDate;
    }


    /**
      * Sets the value of the 'transactionDate' field.
      * @param value The value of 'transactionDate'.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder setTransactionDate(java.time.LocalDate value) {
      validate(fields()[1], value);
      this.transactionDate = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionDate' field has been set.
      * @return True if the 'transactionDate' field has been set, false otherwise.
      */
    public boolean hasTransactionDate() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'transactionDate' field.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder clearTransactionDate() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionDateTime' field.
      * @return The value.
      */
    public java.time.LocalTime getTransactionDateTime() {
      return transactionDateTime;
    }


    /**
      * Sets the value of the 'transactionDateTime' field.
      * @param value The value of 'transactionDateTime'.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder setTransactionDateTime(java.time.LocalTime value) {
      validate(fields()[2], value);
      this.transactionDateTime = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionDateTime' field has been set.
      * @return True if the 'transactionDateTime' field has been set, false otherwise.
      */
    public boolean hasTransactionDateTime() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'transactionDateTime' field.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder clearTransactionDateTime() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionTimeStamp' field.
      * @return The value.
      */
    public java.time.Instant getTransactionTimeStamp() {
      return transactionTimeStamp;
    }


    /**
      * Sets the value of the 'transactionTimeStamp' field.
      * @param value The value of 'transactionTimeStamp'.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder setTransactionTimeStamp(java.time.Instant value) {
      validate(fields()[3], value);
      this.transactionTimeStamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionTimeStamp' field has been set.
      * @return True if the 'transactionTimeStamp' field has been set, false otherwise.
      */
    public boolean hasTransactionTimeStamp() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'transactionTimeStamp' field.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder clearTransactionTimeStamp() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getAmount() {
      return amount;
    }


    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder setAmount(java.nio.ByteBuffer value) {
      validate(fields()[4], value);
      this.amount = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public com.sages.schema.Transaction2.Builder clearAmount() {
      amount = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Transaction2 build() {
      try {
        Transaction2 record = new Transaction2();
        record.transactionId = fieldSetFlags()[0] ? this.transactionId : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.transactionDate = fieldSetFlags()[1] ? this.transactionDate : (java.time.LocalDate) defaultValue(fields()[1]);
        record.transactionDateTime = fieldSetFlags()[2] ? this.transactionDateTime : (java.time.LocalTime) defaultValue(fields()[2]);
        record.transactionTimeStamp = fieldSetFlags()[3] ? this.transactionTimeStamp : (java.time.Instant) defaultValue(fields()[3]);
        record.amount = fieldSetFlags()[4] ? this.amount : (java.nio.ByteBuffer) defaultValue(fields()[4]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Transaction2>
    WRITER$ = (org.apache.avro.io.DatumWriter<Transaction2>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Transaction2>
    READER$ = (org.apache.avro.io.DatumReader<Transaction2>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}









