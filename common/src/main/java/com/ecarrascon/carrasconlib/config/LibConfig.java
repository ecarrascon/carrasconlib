package com.ecarrascon.carrasconlib.config;

import com.ecarrascon.carrasconlib.GetConfigDir;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;

/**
 * A generic configuration manager that loads and saves a config instance
 * of type T from a JSON file using Gson.
 */
public class LibConfig<T> {
    private final File configFile;
    private T instance;
    private final Class<T> configClass;
    private final Logger logger;
    private final Gson gson;

    /**
     * @param fileName        The name of the config file (e.g. "mymod-config.json").
     * @param configClass     The class of your config data.
     * @param logger          A logger instance for error reporting.
     */
    public LibConfig(String fileName, Class<T> configClass, Logger logger) {
        this.configFile = new File(GetConfigDir.getConfigDirectory().toFile(), fileName);
        this.configClass = configClass;
        this.logger = logger;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        init();
    }

    /**
     * Loads the configuration from file or creates a default instance if not present.
     */
    private void init() {
        if (!configFile.exists()) {
            try {
                // Create a default config instance and save it.
                instance = configClass.getDeclaredConstructor().newInstance();
                save();
            } catch (Exception e) {
                logger.error("Failed to create default configuration instance: {}", e.getMessage(), e);
            }
        } else {
            try (Reader reader = Files.newBufferedReader(configFile.toPath())) {
                instance = gson.fromJson(reader, configClass);
            } catch (IOException e) {
                logger.error("Failed to load configuration file: {}. Using default configuration.", e.getMessage(), e);
                try {
                    instance = configClass.getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    logger.error("Failed to create default configuration instance: {}", ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * Returns the current configuration instance.
     */
    public T get() {
        return instance;
    }

    /**
     * Saves the current configuration instance to file.
     */
    public void save() {
        try (Writer writer = Files.newBufferedWriter(configFile.toPath())) {
            gson.toJson(instance, writer);
        } catch (IOException e) {
            logger.error("Error saving configuration file: {}", e.getMessage(), e);
        }
    }
}
