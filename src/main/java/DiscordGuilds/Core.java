package DiscordGuilds;

import DiscordGuilds.DirectMessages.DiscordDirectConversationManager;
import DiscordGuilds.Groups.DiscordGroupManager;
import DiscordGuilds.Gui.FensterJFrame;
import DiscordGuilds.Gui.GuiFileFrame;
import DiscordGuilds.Guilds.DiscordGuildManager;
import DiscordGuilds.Utils.JSONUtils.DiscordJSONFile;

import java.io.File;
import java.io.IOException;

public class Core {
    public String name = "DiscordAnalyser";
    public String version = "v1.0 alpha";
    public static FensterJFrame gui;
    public static void main(String[] args) {
        gui = new FensterJFrame();
        gui.setVisible(true);
    }

    public static void unLoadCurrentPackage(){
        DiscordGuildManager.guilds.clear();
        DiscordGroupManager.groups.clear();
        DiscordDirectConversationManager.directConversations.clear();
        gui.model.reload();
    }

    public static void loadPackage(File f){

        File dir = f;
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File folder : directoryListing) {
                File[] subFiles = folder.listFiles();
                if (folder.getName().equals("servers"))
                    checkForGuilds(folder);
            }
            for (File folder : directoryListing) {
                File[] subFiles = folder.listFiles();

                if (folder.getName().equals("messages"))
                    checkForMessages(folder);
            }


        } else {
            System.out.println("[ERROR] You seeem to have set the wrong path for your data-package");
        }

        gui.model.reload();
    }

    private static void checkForGuilds(File folder) {

        for (File f : folder.listFiles()) {
            if(f.listFiles() == null)
                continue;
            for (File subFile : f.listFiles()) {
                if (subFile.getName().endsWith("guild.json")) {
                    try {

                        readGuildFile(subFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void checkForMessages(File folder) {
        for (File f : folder.listFiles()) {
            if(f.listFiles() == null)
                continue;
            for (File subFile : f.listFiles()) {
                if (subFile.getName().endsWith(".json")) {
                    try {

                        readJSON_CSVFile(subFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void readJSON_CSVFile(File f) throws IOException {
        DiscordJSONFile discordJSONFile = new DiscordJSONFile(f, FileType.MESSAGE);
    }
    public static void readGuildFile(File f) throws IOException {
        DiscordJSONFile discordJSONFile = new DiscordJSONFile(f, FileType.GUILD);
    }
}
