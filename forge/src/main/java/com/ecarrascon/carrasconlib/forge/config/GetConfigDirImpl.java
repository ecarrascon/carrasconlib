package com.ecarrascon.carrasconlib.forge.config;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class GetConfigDirImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
