package com.lolzorrior.supernaturalmod.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SupernaturalClassProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(ISupernaturalClass.class)
    public static Capability<ISupernaturalClass> SUPERNATURAL_CLASS = null;
    private LazyOptional<ISupernaturalClass> instance = LazyOptional.of(SUPERNATURAL_CLASS::getDefaultInstance);


    @Nonnull
    @Override
    public <T> LazyOptional <T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == SUPERNATURAL_CLASS) {
            return instance.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) SUPERNATURAL_CLASS.getStorage().writeNBT(SUPERNATURAL_CLASS, instance.orElseThrow(NullPointerException::new), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        SUPERNATURAL_CLASS.getStorage().readNBT(SUPERNATURAL_CLASS, instance.orElseThrow(() ->  new IllegalArgumentException("LazyOptional is empty")), null, nbt);
    }




    /*
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return getCapability(cap, null);
    }*/


}
