package de.mari_023.fabric.ae2wtlib.rei;

import de.mari_023.fabric.ae2wtlib.ae2wtlib;
import de.mari_023.fabric.ae2wtlib.ae2wtlibConfig;
import de.mari_023.fabric.ae2wtlib.wut.recipe.Combine;
import de.mari_023.fabric.ae2wtlib.wut.recipe.CombineSerializer;
import de.mari_023.fabric.ae2wtlib.wut.recipe.Upgrade;
import de.mari_023.fabric.ae2wtlib.wut.recipe.UpgradeSerializer;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.DefaultPlugin;
import net.minecraft.item.ItemStack;

public class Plugin implements REIClientPlugin {


    @Override
    public String getPluginProviderName() {
        return ae2wtlib.MOD_NAME;
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(DefaultPlugin.CRAFTING, EntryStacks.of(new ItemStack(ae2wtlib.CRAFTING_TERMINAL)));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(Combine.class, CombineSerializer.TYPE, WUTDisplay::new);
        registry.registerRecipeFiller(Upgrade.class, UpgradeSerializer.TYPE, WUTDisplay::new);
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        // Allow recipe transfer from JEI to crafting and pattern terminal
        registry.register(new CraftingRecipeTransferHandler());
        registry.register(new PatternRecipeTransferHandler());
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        if(!ae2wtlibConfig.INSTANCE.allowTrinket()) return;
        entryRegistry.removeEntry(EntryStacks.of(ae2wtlib.CHECK_TRINKETS));
    }
}