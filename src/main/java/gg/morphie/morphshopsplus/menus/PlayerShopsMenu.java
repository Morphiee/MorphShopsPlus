package gg.morphie.morphshopsplus.menus;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import org.bukkit.event.Listener;

public class PlayerShopsMenu implements Listener {

    private MorphShopsPlus plugin;

    public PlayerShopsMenu(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }
    String[] guiSetup = {
            " ggggggg ",
            " ggggggg ",
            " ggggggg ",
            " ggggggg ",
            " ggggggg ",
            "  ( 1 )  "
    };
}
