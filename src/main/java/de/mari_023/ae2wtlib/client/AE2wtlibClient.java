package de.mari_023.ae2wtlib.client;

import de.mari_023.ae2wtlib.wat.WATMenu;
import de.mari_023.ae2wtlib.wat.WATScreen;
import de.mari_023.ae2wtlib.wct.TrashMenu;
import de.mari_023.ae2wtlib.wct.TrashScreen;
import de.mari_023.ae2wtlib.wct.WCTMenu;
import de.mari_023.ae2wtlib.wct.WCTScreen;
import de.mari_023.ae2wtlib.wct.magnet_card.config.MagnetMenu;
import de.mari_023.ae2wtlib.wct.magnet_card.config.MagnetScreen;
import de.mari_023.ae2wtlib.wet.WETMenu;
import de.mari_023.ae2wtlib.wet.WETScreen;

import appeng.init.client.InitScreens;

public class AE2wtlibClient {
    public static void onAe2Initialized() {
        InitScreens.register(WCTMenu.TYPE, WCTScreen::new, "/screens/wtlib/wireless_crafting_terminal.json");
        InitScreens.register(WETMenu.TYPE, WETScreen::new, "/screens/wtlib/wireless_pattern_encoding_terminal.json");
        InitScreens.register(WATMenu.TYPE, WATScreen::new, "/screens/wtlib/wireless_pattern_access_terminal.json");
        InitScreens.register(MagnetMenu.TYPE, MagnetScreen::new, "/screens/wtlib/magnet.json");
        InitScreens.register(TrashMenu.TYPE, TrashScreen::new, "/screens/wtlib/trash.json");
    }
}
