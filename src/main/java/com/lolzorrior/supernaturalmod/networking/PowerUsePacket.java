package com.lolzorrior.supernaturalmod.networking;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lolzorrior.supernaturalmod.capabilities.SupernaturalClassProvider.SUPERNATURAL_CLASS;

import java.util.function.Supplier;

public class PowerUsePacket {
    private static final Logger LOGGER = LogManager.getLogger();

    private String powerUsed;

    public PowerUsePacket(){this.powerUsed = null;}

    public PowerUsePacket(String powerUsed) {this.powerUsed = powerUsed;}

    public static void encode(PowerUsePacket msg, PacketBuffer buf){
        buf.writeString(msg.powerUsed);
    }

    public static PowerUsePacket decode(PacketBuffer buf){
        return new PowerUsePacket(buf.readString());
    }

    public static void handle(PowerUsePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            ServerPlayerEntity sender = ctx.get().getSender(); // the client that sent this packet
            if (ctx.get().getDirection().getOriginationSide().isServer()) {
                return;
            }
            // do stuff

            if (!sender.getEntityWorld().isRemote())
            {
                switch (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getSupernaturalClass()) {
                    case "Werewolf": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.sendMessage(new StringTextComponent("The hunt is on!"), sender.getUniqueID());
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.SPEED, 200, 3));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Monk": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 3));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Witch Hunter": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addItemStackToInventory(Items.CROSSBOW.getDefaultInstance());
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Zombie": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.GLOWING, 200, 3));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Demon": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 200, 3));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Warlock": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 0, 1));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Mage": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);

                            Vector3d vector3d = sender.getLook(1.0F);
                            double d2 = sender.getPosX() - (sender.getPosX() + vector3d.x * 4.0D);
                            double d3 = sender.getPosYHeight(0.5D) - (0.5D + sender.getPosYHeight(0.5D));
                            double d4 = sender.getPosZ() - (sender.getPosZ() + vector3d.z * 4.0D);

                            FireballEntity fireballentity = new FireballEntity(sender.world, sender, d2, d3, d4);
                            fireballentity.explosionPower = 1;
                            fireballentity.setPosition(sender.getPosX() + vector3d.x * 4.0D, sender.getPosYHeight(0.5D) + 0.5D, fireballentity.getPosZ() + vector3d.z * 4.0D);
                            sender.world.addEntity(fireballentity);

                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    case "Human": {
                        if (sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() >= 50) {
                            sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).consume(50);
                            sender.addPotionEffect(new EffectInstance(Effects.LUCK, 200, 1));
                            sender.sendMessage(new StringTextComponent("You have " + sender.getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower() + " power left."), sender.getUniqueID());
                        } else {
                            sender.sendMessage(new StringTextComponent("Not enough power."), sender.getUniqueID());
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
