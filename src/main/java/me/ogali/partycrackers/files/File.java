package me.ogali.partycrackers.files;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.utils.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class File {

    private final PartyCrackersPlugin main;
    private final String fileName;
    private FileConfiguration cpFile = null;
    private java.io.File file = null;

    public File(PartyCrackersPlugin main, String fileName) {
        this.main = main;
        this.fileName = fileName;
        saveDefaultFile();
    }

    public void reloadFile() {
        if (this.file == null) {
            this.file = new java.io.File(this.main.getDataFolder(), fileName + ".yml");
        }
        this.cpFile = YamlConfiguration.loadConfiguration(this.file);

        InputStream defaultStream = this.main.getResource(fileName + ".yml");
        if (defaultStream != null) {
            YamlConfiguration defaultFile = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.cpFile.setDefaults(defaultFile);
        }
    }

    public FileConfiguration getFile() {
        if (this.cpFile == null) {
            reloadFile();
        }
        return this.cpFile;
    }

    public void saveFile() {
        if (this.cpFile == null || this.file == null) return;

        try {
            this.getFile().save(this.file);
        } catch (IOException e) {
            Chat.log("COULD NOT SAVE " + this.file + " " + e);
        }
    }

    public void saveDefaultFile() {
        if (this.file == null) {
            this.file = new java.io.File(this.main.getDataFolder(), fileName + ".yml");
        }

        if (!this.file.exists()) {
            this.main.saveResource(fileName + ".yml", false);
        }
    }

}
