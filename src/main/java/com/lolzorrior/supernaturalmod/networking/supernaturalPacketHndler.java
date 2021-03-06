package com.lolzorrior.supernaturalmod.networking;

import com.lolzorrior.supernaturalmod.capabilities.SupernaturalClass;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static com.lolzorrior.supernaturalmod.SupernaturalMod.MOD_ID;

public class supernaturalPacketHndler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel channel;

    public supernaturalPacketHndler() {}

    public static void register() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);
        int id = 0;
        channel.registerMessage(id++, SupernaturalClass.class, SupernaturalClass::encode, SupernaturalClass::decode, SupernaturalClass::handle);
        channel.registerMessage(id++, PowerUpdatePacket.class, PowerUpdatePacket::encode, PowerUpdatePacket::decode, PowerUpdatePacket::handle);
        channel.registerMessage(id++, PowerUsePacket.class, PowerUsePacket::encode, PowerUsePacket::decode, PowerUsePacket::handle);
    }
}
