package com.lolzorrior.supernaturalmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lolzorrior.supernaturalmod.capabilities.SupernaturalClassProvider.SUPERNATURAL_CLASS;


public class PowerCommand{
    private static final Logger LOGGER = LogManager.getLogger();

    public PowerCommand(CommandDispatcher<CommandSource> dispatcher) {register(dispatcher);
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("supernatural").then((Commands.literal("power")).executes((powerGet -> getPowerEcho(powerGet.getSource()))).then(Commands.argument("power", IntegerArgumentType.integer())
                .executes((p_198496_0_) -> setPower(p_198496_0_.getSource(), IntegerArgumentType.getInteger(p_198496_0_, "power"))))));
        LOGGER.info("Power Command Registered.");
    }

    private static int getPower(CommandSource source) {
        if (source.getEntity() instanceof ServerPlayerEntity) {
            return source.getEntity().getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getPower();
        } else {
            throw new NullPointerException();
        }
    }

    private static int setPower(CommandSource source, int power) {
        if (source.getEntity() instanceof ServerPlayerEntity) {
            source.getEntity().getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).set(power);
        } else {
            throw new NullPointerException();
        }

        int i = getPower(source);
        source.sendFeedback(new TranslationTextComponent("commands.supernaturalmod.power.set", i), true);
        return i;
    }

    private static int getPowerEcho(CommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            return -1;
        }

        int i = getPower(source);
        source.sendFeedback(new TranslationTextComponent("commands.supernaturalmod.power.get", i), true);
        return i;
    }
}
