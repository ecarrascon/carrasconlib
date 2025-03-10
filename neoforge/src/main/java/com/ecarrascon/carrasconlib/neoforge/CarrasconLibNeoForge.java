package com.ecarrascon.carrasconlib.neoforge;

import net.neoforged.fml.common.Mod;

import com.ecarrascon.carrasconlib.CarrasconLib;

@Mod(CarrasconLib.MOD_ID)
public final class CarrasconLibNeoForge {
    public CarrasconLibNeoForge() {
        // Run our common setup.
        CarrasconLib.init();
    }
}
