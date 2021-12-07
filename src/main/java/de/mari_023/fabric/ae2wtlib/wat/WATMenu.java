package de.mari_023.fabric.ae2wtlib.wat;

import appeng.api.config.SecurityPermissions;
import appeng.menu.SlotSemantic;
import appeng.menu.implementations.InterfaceTerminalMenu;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.slot.AppEngSlot;
import de.mari_023.fabric.ae2wtlib.terminal.WTInventory;
import de.mari_023.fabric.ae2wtlib.terminal.IWTInvHolder;
import de.mari_023.fabric.ae2wtlib.wut.ItemWUT;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;

public class WATMenu extends InterfaceTerminalMenu implements IWTInvHolder {

    public static final ScreenHandlerType<WATMenu> TYPE = MenuTypeBuilder.create(WATMenu::new, WATMenuHost.class).requirePermission(SecurityPermissions.BUILD).build("wireless_pattern_access_terminal");

    private final WATMenuHost witGUIObject;

    public WATMenu(int id, final PlayerInventory ip, final WATMenuHost anchor) {
        super(TYPE, id, ip, anchor, false);
        witGUIObject = anchor;

        final int slotIndex = witGUIObject.getSlot();
        if(slotIndex < 100 && slotIndex != 40) lockPlayerInventorySlot(slotIndex);
        createPlayerInventorySlots(ip);

        final WTInventory fixedWITInv = new WTInventory(getPlayerInventory(), witGUIObject.getItemStack(), this);
        addSlot(new AppEngSlot(fixedWITInv, WTInventory.INFINITY_BOOSTER_CARD), SlotSemantic.BIOMETRIC_CARD);
    }

    public boolean isWUT() {
        return witGUIObject.getItemStack().getItem() instanceof ItemWUT;
    }
}