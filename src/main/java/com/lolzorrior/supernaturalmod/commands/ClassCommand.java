package com.lolzorrior.supernaturalmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.server.command.EnumArgument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

import static com.lolzorrior.supernaturalmod.capabilities.SupernaturalClassProvider.SUPERNATURAL_CLASS;
import static com.lolzorrior.supernaturalmod.capabilities.SupernaturalClass.SUPERNATURAL_CLASSES;

public class ClassCommand {
    private static final Logger LOGGER = LogManager.getLogger();


    public ClassCommand(CommandDispatcher<CommandSource> dispatcher) {
        register(dispatcher);
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("supernatural").then((Commands.literal("class")).executes((classGet -> getClassEcho(classGet.getSource())))
                .then(Commands.argument("class", StringArgumentType.string())).executes((p_198496_0_) -> {
                    if (Arrays.asList(SUPERNATURAL_CLASSES).contains(StringArgumentType.getString(p_198496_0_, "class"))) {
                        setClass(p_198496_0_.getSource(), StringArgumentType.getString(p_198496_0_, "class"));
                        return 0;
                    }
                    return 0;
                })));
        LOGGER.info("Power Command Registered.");
    }

    private static String getClass(CommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            throw new NullPointerException("Source is not a player.");
        }
        return source.getEntity().getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).getSupernaturalClass();
    }

    private static int setClass(CommandSource source, String sclass) {
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            throw new NullPointerException("Source is not a player.");
        }
        source.getEntity().getCapability(SUPERNATURAL_CLASS).orElseThrow(NullPointerException::new).setSupernaturalClass(sclass);
        String i = getClass(source);
        source.sendFeedback(new TranslationTextComponent("commands.supernaturalmod.class.set", i), true);
        return 0;
    }

    private static int getClassEcho(CommandSource source) {
        String i = getClass(source);
        source.sendFeedback(new TranslationTextComponent("commands.supernaturalmod.class.get", i), true);
        return 0;
    }
}
