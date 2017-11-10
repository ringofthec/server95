// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: game/java-share/ProBase.proto

package shareserver.network.protos.share;

public final class ProBase {
  private ProBase() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface Msg_G2S_AuthOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string gs_uid = 1;
    /**
     * <code>required string gs_uid = 1;</code>
     */
    boolean hasGsUid();
    /**
     * <code>required string gs_uid = 1;</code>
     */
    java.lang.String getGsUid();
    /**
     * <code>required string gs_uid = 1;</code>
     */
    com.google.protobuf.ByteString
        getGsUidBytes();
  }
  /**
   * Protobuf type {@code shareserver.network.protos.share.Msg_G2S_Auth}
   */
  public static final class Msg_G2S_Auth extends
      com.google.protobuf.GeneratedMessage
      implements Msg_G2S_AuthOrBuilder {
    // Use Msg_G2S_Auth.newBuilder() to construct.
    private Msg_G2S_Auth(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Msg_G2S_Auth(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Msg_G2S_Auth defaultInstance;
    public static Msg_G2S_Auth getDefaultInstance() {
      return defaultInstance;
    }

    public Msg_G2S_Auth getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Msg_G2S_Auth(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              gsUid_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_G2S_Auth_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              shareserver.network.protos.share.ProBase.Msg_G2S_Auth.class, shareserver.network.protos.share.ProBase.Msg_G2S_Auth.Builder.class);
    }

    public static com.google.protobuf.Parser<Msg_G2S_Auth> PARSER =
        new com.google.protobuf.AbstractParser<Msg_G2S_Auth>() {
      public Msg_G2S_Auth parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg_G2S_Auth(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Msg_G2S_Auth> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string gs_uid = 1;
    public static final int GS_UID_FIELD_NUMBER = 1;
    private java.lang.Object gsUid_;
    /**
     * <code>required string gs_uid = 1;</code>
     */
    public boolean hasGsUid() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string gs_uid = 1;</code>
     */
    public java.lang.String getGsUid() {
      java.lang.Object ref = gsUid_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          gsUid_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string gs_uid = 1;</code>
     */
    public com.google.protobuf.ByteString
        getGsUidBytes() {
      java.lang.Object ref = gsUid_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        gsUid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      gsUid_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasGsUid()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getGsUidBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getGsUidBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_G2S_Auth parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(shareserver.network.protos.share.ProBase.Msg_G2S_Auth prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code shareserver.network.protos.share.Msg_G2S_Auth}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements shareserver.network.protos.share.ProBase.Msg_G2S_AuthOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_G2S_Auth_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                shareserver.network.protos.share.ProBase.Msg_G2S_Auth.class, shareserver.network.protos.share.ProBase.Msg_G2S_Auth.Builder.class);
      }

      // Construct using shareserver.network.protos.share.ProBase.Msg_G2S_Auth.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        gsUid_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor;
      }

      public shareserver.network.protos.share.ProBase.Msg_G2S_Auth getDefaultInstanceForType() {
        return shareserver.network.protos.share.ProBase.Msg_G2S_Auth.getDefaultInstance();
      }

      public shareserver.network.protos.share.ProBase.Msg_G2S_Auth build() {
        shareserver.network.protos.share.ProBase.Msg_G2S_Auth result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public shareserver.network.protos.share.ProBase.Msg_G2S_Auth buildPartial() {
        shareserver.network.protos.share.ProBase.Msg_G2S_Auth result = new shareserver.network.protos.share.ProBase.Msg_G2S_Auth(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.gsUid_ = gsUid_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof shareserver.network.protos.share.ProBase.Msg_G2S_Auth) {
          return mergeFrom((shareserver.network.protos.share.ProBase.Msg_G2S_Auth)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(shareserver.network.protos.share.ProBase.Msg_G2S_Auth other) {
        if (other == shareserver.network.protos.share.ProBase.Msg_G2S_Auth.getDefaultInstance()) return this;
        if (other.hasGsUid()) {
          bitField0_ |= 0x00000001;
          gsUid_ = other.gsUid_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasGsUid()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        shareserver.network.protos.share.ProBase.Msg_G2S_Auth parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (shareserver.network.protos.share.ProBase.Msg_G2S_Auth) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string gs_uid = 1;
      private java.lang.Object gsUid_ = "";
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public boolean hasGsUid() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public java.lang.String getGsUid() {
        java.lang.Object ref = gsUid_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          gsUid_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public com.google.protobuf.ByteString
          getGsUidBytes() {
        java.lang.Object ref = gsUid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          gsUid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public Builder setGsUid(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        gsUid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public Builder clearGsUid() {
        bitField0_ = (bitField0_ & ~0x00000001);
        gsUid_ = getDefaultInstance().getGsUid();
        onChanged();
        return this;
      }
      /**
       * <code>required string gs_uid = 1;</code>
       */
      public Builder setGsUidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        gsUid_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:shareserver.network.protos.share.Msg_G2S_Auth)
    }

    static {
      defaultInstance = new Msg_G2S_Auth(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:shareserver.network.protos.share.Msg_G2S_Auth)
  }

  public interface Msg_S2G_AuthedOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code shareserver.network.protos.share.Msg_S2G_Authed}
   */
  public static final class Msg_S2G_Authed extends
      com.google.protobuf.GeneratedMessage
      implements Msg_S2G_AuthedOrBuilder {
    // Use Msg_S2G_Authed.newBuilder() to construct.
    private Msg_S2G_Authed(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Msg_S2G_Authed(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Msg_S2G_Authed defaultInstance;
    public static Msg_S2G_Authed getDefaultInstance() {
      return defaultInstance;
    }

    public Msg_S2G_Authed getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Msg_S2G_Authed(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_S2G_Authed_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              shareserver.network.protos.share.ProBase.Msg_S2G_Authed.class, shareserver.network.protos.share.ProBase.Msg_S2G_Authed.Builder.class);
    }

    public static com.google.protobuf.Parser<Msg_S2G_Authed> PARSER =
        new com.google.protobuf.AbstractParser<Msg_S2G_Authed>() {
      public Msg_S2G_Authed parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg_S2G_Authed(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Msg_S2G_Authed> getParserForType() {
      return PARSER;
    }

    private void initFields() {
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static shareserver.network.protos.share.ProBase.Msg_S2G_Authed parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(shareserver.network.protos.share.ProBase.Msg_S2G_Authed prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code shareserver.network.protos.share.Msg_S2G_Authed}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements shareserver.network.protos.share.ProBase.Msg_S2G_AuthedOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_S2G_Authed_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                shareserver.network.protos.share.ProBase.Msg_S2G_Authed.class, shareserver.network.protos.share.ProBase.Msg_S2G_Authed.Builder.class);
      }

      // Construct using shareserver.network.protos.share.ProBase.Msg_S2G_Authed.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return shareserver.network.protos.share.ProBase.internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor;
      }

      public shareserver.network.protos.share.ProBase.Msg_S2G_Authed getDefaultInstanceForType() {
        return shareserver.network.protos.share.ProBase.Msg_S2G_Authed.getDefaultInstance();
      }

      public shareserver.network.protos.share.ProBase.Msg_S2G_Authed build() {
        shareserver.network.protos.share.ProBase.Msg_S2G_Authed result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public shareserver.network.protos.share.ProBase.Msg_S2G_Authed buildPartial() {
        shareserver.network.protos.share.ProBase.Msg_S2G_Authed result = new shareserver.network.protos.share.ProBase.Msg_S2G_Authed(this);
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof shareserver.network.protos.share.ProBase.Msg_S2G_Authed) {
          return mergeFrom((shareserver.network.protos.share.ProBase.Msg_S2G_Authed)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(shareserver.network.protos.share.ProBase.Msg_S2G_Authed other) {
        if (other == shareserver.network.protos.share.ProBase.Msg_S2G_Authed.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        shareserver.network.protos.share.ProBase.Msg_S2G_Authed parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (shareserver.network.protos.share.ProBase.Msg_S2G_Authed) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      // @@protoc_insertion_point(builder_scope:shareserver.network.protos.share.Msg_S2G_Authed)
    }

    static {
      defaultInstance = new Msg_S2G_Authed(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:shareserver.network.protos.share.Msg_S2G_Authed)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_shareserver_network_protos_share_Msg_G2S_Auth_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_shareserver_network_protos_share_Msg_S2G_Authed_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035game/java-share/ProBase.proto\022 sharese" +
      "rver.network.protos.share\"\036\n\014Msg_G2S_Aut" +
      "h\022\016\n\006gs_uid\030\001 \002(\t\"\020\n\016Msg_S2G_Authed"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_shareserver_network_protos_share_Msg_G2S_Auth_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_shareserver_network_protos_share_Msg_G2S_Auth_descriptor,
              new java.lang.String[] { "GsUid", });
          internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_shareserver_network_protos_share_Msg_S2G_Authed_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_shareserver_network_protos_share_Msg_S2G_Authed_descriptor,
              new java.lang.String[] { });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
