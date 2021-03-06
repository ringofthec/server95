// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: game/cs-java-share/ProDebug.proto

package gameserver.network.protos.game;

public final class ProDebug {
  private ProDebug() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface Msg_G2C_GetDebugFileOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code gameserver.network.protos.game.Msg_G2C_GetDebugFile}
   *
   * <pre>
   * 获取客户端错误日志文件
   * </pre>
   */
  public static final class Msg_G2C_GetDebugFile extends
      com.google.protobuf.GeneratedMessage
      implements Msg_G2C_GetDebugFileOrBuilder {
    // Use Msg_G2C_GetDebugFile.newBuilder() to construct.
    private Msg_G2C_GetDebugFile(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Msg_G2C_GetDebugFile(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Msg_G2C_GetDebugFile defaultInstance;
    public static Msg_G2C_GetDebugFile getDefaultInstance() {
      return defaultInstance;
    }

    public Msg_G2C_GetDebugFile getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Msg_G2C_GetDebugFile(
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
      return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.class, gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.Builder.class);
    }

    public static com.google.protobuf.Parser<Msg_G2C_GetDebugFile> PARSER =
        new com.google.protobuf.AbstractParser<Msg_G2C_GetDebugFile>() {
      public Msg_G2C_GetDebugFile parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg_G2C_GetDebugFile(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Msg_G2C_GetDebugFile> getParserForType() {
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

    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile prototype) {
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
     * Protobuf type {@code gameserver.network.protos.game.Msg_G2C_GetDebugFile}
     *
     * <pre>
     * 获取客户端错误日志文件
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFileOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.class, gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.Builder.class);
      }

      // Construct using gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.newBuilder()
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
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor;
      }

      public gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile getDefaultInstanceForType() {
        return gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.getDefaultInstance();
      }

      public gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile build() {
        gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile buildPartial() {
        gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile result = new gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile(this);
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile) {
          return mergeFrom((gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile other) {
        if (other == gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile.getDefaultInstance()) return this;
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
        gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      // @@protoc_insertion_point(builder_scope:gameserver.network.protos.game.Msg_G2C_GetDebugFile)
    }

    static {
      defaultInstance = new Msg_G2C_GetDebugFile(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:gameserver.network.protos.game.Msg_G2C_GetDebugFile)
  }

  public interface Msg_S2G_OpenFunctionOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required bool opening = 1;
    /**
     * <code>required bool opening = 1;</code>
     *
     * <pre>
     * 1 打开  2 关闭
     * </pre>
     */
    boolean hasOpening();
    /**
     * <code>required bool opening = 1;</code>
     *
     * <pre>
     * 1 打开  2 关闭
     * </pre>
     */
    boolean getOpening();

    // repeated int32 functionId = 2;
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    java.util.List<java.lang.Integer> getFunctionIdList();
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    int getFunctionIdCount();
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    int getFunctionId(int index);
  }
  /**
   * Protobuf type {@code gameserver.network.protos.game.Msg_S2G_OpenFunction}
   *
   * <pre>
   * 打开和关闭功能
   * </pre>
   */
  public static final class Msg_S2G_OpenFunction extends
      com.google.protobuf.GeneratedMessage
      implements Msg_S2G_OpenFunctionOrBuilder {
    // Use Msg_S2G_OpenFunction.newBuilder() to construct.
    private Msg_S2G_OpenFunction(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Msg_S2G_OpenFunction(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Msg_S2G_OpenFunction defaultInstance;
    public static Msg_S2G_OpenFunction getDefaultInstance() {
      return defaultInstance;
    }

    public Msg_S2G_OpenFunction getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Msg_S2G_OpenFunction(
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
            case 8: {
              bitField0_ |= 0x00000001;
              opening_ = input.readBool();
              break;
            }
            case 16: {
              if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
                functionId_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000002;
              }
              functionId_.add(input.readInt32());
              break;
            }
            case 18: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000002) == 0x00000002) && input.getBytesUntilLimit() > 0) {
                functionId_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000002;
              }
              while (input.getBytesUntilLimit() > 0) {
                functionId_.add(input.readInt32());
              }
              input.popLimit(limit);
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
        if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
          functionId_ = java.util.Collections.unmodifiableList(functionId_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.class, gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.Builder.class);
    }

    public static com.google.protobuf.Parser<Msg_S2G_OpenFunction> PARSER =
        new com.google.protobuf.AbstractParser<Msg_S2G_OpenFunction>() {
      public Msg_S2G_OpenFunction parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg_S2G_OpenFunction(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Msg_S2G_OpenFunction> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required bool opening = 1;
    public static final int OPENING_FIELD_NUMBER = 1;
    private boolean opening_;
    /**
     * <code>required bool opening = 1;</code>
     *
     * <pre>
     * 1 打开  2 关闭
     * </pre>
     */
    public boolean hasOpening() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bool opening = 1;</code>
     *
     * <pre>
     * 1 打开  2 关闭
     * </pre>
     */
    public boolean getOpening() {
      return opening_;
    }

    // repeated int32 functionId = 2;
    public static final int FUNCTIONID_FIELD_NUMBER = 2;
    private java.util.List<java.lang.Integer> functionId_;
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    public java.util.List<java.lang.Integer>
        getFunctionIdList() {
      return functionId_;
    }
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    public int getFunctionIdCount() {
      return functionId_.size();
    }
    /**
     * <code>repeated int32 functionId = 2;</code>
     *
     * <pre>
     * 功能编号
     * </pre>
     */
    public int getFunctionId(int index) {
      return functionId_.get(index);
    }

    private void initFields() {
      opening_ = false;
      functionId_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasOpening()) {
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
        output.writeBool(1, opening_);
      }
      for (int i = 0; i < functionId_.size(); i++) {
        output.writeInt32(2, functionId_.get(i));
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
          .computeBoolSize(1, opening_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < functionId_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(functionId_.get(i));
        }
        size += dataSize;
        size += 1 * getFunctionIdList().size();
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

    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction prototype) {
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
     * Protobuf type {@code gameserver.network.protos.game.Msg_S2G_OpenFunction}
     *
     * <pre>
     * 打开和关闭功能
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunctionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.class, gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.Builder.class);
      }

      // Construct using gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.newBuilder()
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
        opening_ = false;
        bitField0_ = (bitField0_ & ~0x00000001);
        functionId_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return gameserver.network.protos.game.ProDebug.internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor;
      }

      public gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction getDefaultInstanceForType() {
        return gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.getDefaultInstance();
      }

      public gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction build() {
        gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction buildPartial() {
        gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction result = new gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.opening_ = opening_;
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          functionId_ = java.util.Collections.unmodifiableList(functionId_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.functionId_ = functionId_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction) {
          return mergeFrom((gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction other) {
        if (other == gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction.getDefaultInstance()) return this;
        if (other.hasOpening()) {
          setOpening(other.getOpening());
        }
        if (!other.functionId_.isEmpty()) {
          if (functionId_.isEmpty()) {
            functionId_ = other.functionId_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureFunctionIdIsMutable();
            functionId_.addAll(other.functionId_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasOpening()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required bool opening = 1;
      private boolean opening_ ;
      /**
       * <code>required bool opening = 1;</code>
       *
       * <pre>
       * 1 打开  2 关闭
       * </pre>
       */
      public boolean hasOpening() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bool opening = 1;</code>
       *
       * <pre>
       * 1 打开  2 关闭
       * </pre>
       */
      public boolean getOpening() {
        return opening_;
      }
      /**
       * <code>required bool opening = 1;</code>
       *
       * <pre>
       * 1 打开  2 关闭
       * </pre>
       */
      public Builder setOpening(boolean value) {
        bitField0_ |= 0x00000001;
        opening_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bool opening = 1;</code>
       *
       * <pre>
       * 1 打开  2 关闭
       * </pre>
       */
      public Builder clearOpening() {
        bitField0_ = (bitField0_ & ~0x00000001);
        opening_ = false;
        onChanged();
        return this;
      }

      // repeated int32 functionId = 2;
      private java.util.List<java.lang.Integer> functionId_ = java.util.Collections.emptyList();
      private void ensureFunctionIdIsMutable() {
        if (!((bitField0_ & 0x00000002) == 0x00000002)) {
          functionId_ = new java.util.ArrayList<java.lang.Integer>(functionId_);
          bitField0_ |= 0x00000002;
         }
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public java.util.List<java.lang.Integer>
          getFunctionIdList() {
        return java.util.Collections.unmodifiableList(functionId_);
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public int getFunctionIdCount() {
        return functionId_.size();
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public int getFunctionId(int index) {
        return functionId_.get(index);
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public Builder setFunctionId(
          int index, int value) {
        ensureFunctionIdIsMutable();
        functionId_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public Builder addFunctionId(int value) {
        ensureFunctionIdIsMutable();
        functionId_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public Builder addAllFunctionId(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureFunctionIdIsMutable();
        super.addAll(values, functionId_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 functionId = 2;</code>
       *
       * <pre>
       * 功能编号
       * </pre>
       */
      public Builder clearFunctionId() {
        functionId_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:gameserver.network.protos.game.Msg_S2G_OpenFunction)
    }

    static {
      defaultInstance = new Msg_S2G_OpenFunction(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:gameserver.network.protos.game.Msg_S2G_OpenFunction)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!game/cs-java-share/ProDebug.proto\022\036gam" +
      "eserver.network.protos.game\"\026\n\024Msg_G2C_G" +
      "etDebugFile\";\n\024Msg_S2G_OpenFunction\022\017\n\007o" +
      "pening\030\001 \002(\010\022\022\n\nfunctionId\030\002 \003(\005"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_gameserver_network_protos_game_Msg_G2C_GetDebugFile_descriptor,
              new java.lang.String[] { });
          internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_gameserver_network_protos_game_Msg_S2G_OpenFunction_descriptor,
              new java.lang.String[] { "Opening", "FunctionId", });
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
