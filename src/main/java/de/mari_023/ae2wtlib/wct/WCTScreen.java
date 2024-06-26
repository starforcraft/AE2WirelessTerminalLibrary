package de.mari_023.ae2wtlib.wct;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.Icon;
import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.BackgroundPanel;
import appeng.client.gui.widgets.IconButton;

import de.mari_023.ae2wtlib.AE2wtlibItems;
import de.mari_023.ae2wtlib.TextConstants;
import de.mari_023.ae2wtlib.terminal.ItemButton;
import de.mari_023.ae2wtlib.terminal.WTMenuHost;
import de.mari_023.ae2wtlib.wct.magnet_card.MagnetMode;
import de.mari_023.ae2wtlib.wut.IUniversalTerminalCapable;

public class WCTScreen extends CraftingTermScreen<WCTMenu> implements IUniversalTerminalCapable {
    private final ItemButton magnetCardToggleButton;
    private final ItemButton magnetCardMenuButton;

    public WCTScreen(WCTMenu container, Inventory playerInventory, Component title, ScreenStyle style) {
        super(container, playerInventory, title, style);
        if (getMenu().isWUT())
            addToLeftToolbar(cycleTerminalButton());

        magnetCardToggleButton = new ItemButton(btn -> setMagnetMode(), AE2wtlibItems.MAGNET_CARD);
        addToLeftToolbar(magnetCardToggleButton);

        magnetCardMenuButton = new ItemButton(btn -> getMenu().openMagnetMenu(), AE2wtlibItems.MAGNET_CARD);
        addToLeftToolbar(magnetCardMenuButton);
        magnetCardMenuButton.setMessage(TextConstants.MAGNET_FILTER);
        IconButton deleteButton = new IconButton(btn -> getMenu().openTrashMenu()) {
            @Override
            protected Icon getIcon() {
                return Icon.CONDENSER_OUTPUT_TRASH;
            }
        };
        addToLeftToolbar(deleteButton);
        deleteButton.setMessage(TextConstants.TRASH);

        widgets.add("player", new PlayerEntityWidget(Objects.requireNonNull(Minecraft.getInstance().player)));
        widgets.add("singularityBackground", new BackgroundPanel(style.getImage("singularityBackground")));
    }

    private void setMagnetMode() {
        if (isHandlingRightClick()) {
            switch (getMenu().getMagnetMode()) {
                case OFF -> getMenu().setMagnetMode(MagnetMode.PICKUP_ME);
                case PICKUP_INVENTORY -> getMenu().setMagnetMode(MagnetMode.OFF);
                case PICKUP_ME -> getMenu().setMagnetMode(MagnetMode.PICKUP_INVENTORY);
            }
            return;
        }
        switch (getMenu().getMagnetMode()) {
            case OFF -> getMenu().setMagnetMode(MagnetMode.PICKUP_INVENTORY);
            case PICKUP_INVENTORY -> getMenu().setMagnetMode(MagnetMode.PICKUP_ME);
            case PICKUP_ME -> getMenu().setMagnetMode(MagnetMode.OFF);
        }
    }

    private void setMagnetModeText() {
        switch (getMenu().getMagnetMode()) {
            case INVALID, NO_CARD -> {
                magnetCardToggleButton.setVisibility(false);
                magnetCardMenuButton.setVisibility(false);
            }
            case OFF -> {
                magnetCardToggleButton.setVisibility(true);
                magnetCardMenuButton.setVisibility(true);
                magnetCardToggleButton.setMessage(TextConstants.MAGNETCARD_OFF);
            }
            case PICKUP_INVENTORY -> {
                magnetCardToggleButton.setVisibility(true);
                magnetCardMenuButton.setVisibility(true);
                magnetCardToggleButton.setMessage(TextConstants.MAGNETCARD_INVENTORY);
            }
            case PICKUP_ME -> {
                magnetCardToggleButton.setVisibility(true);
                magnetCardMenuButton.setVisibility(true);
                magnetCardToggleButton.setMessage(TextConstants.MAGNETCARD_ME);
            }
        }
    }

    protected void updateBeforeRender() {
        super.updateBeforeRender();
        setMagnetModeText();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int keyPressed) {
        boolean value = super.keyPressed(keyCode, scanCode, keyPressed);
        if (!value)
            return checkForTerminalKeys(keyCode, scanCode);
        return true;
    }

    @Override
    public WTMenuHost getHost() {
        return (WTMenuHost) getMenu().getHost();
    }
}
