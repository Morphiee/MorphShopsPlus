package gg.morphie.morphshopsplus.util.files.messages;

import gg.morphie.morphshopsplus.MorphShopsPlus;

public class GetMessages {
    private MorphShopsPlus plugin;
    public GetMessages(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }
    public String getMessage(String string) {
        String gotString = new GetMessages(plugin).getMessage(string);
        if (gotString != null) return gotString;
        return "Null message";
    }
}
