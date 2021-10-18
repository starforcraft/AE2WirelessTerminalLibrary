package de.mari_023.fabric.ae2wtlib.wut.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import de.mari_023.fabric.ae2wtlib.ae2wtlib;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class UpgradeSerializer extends Serializer<Upgrade> {
    public static final UpgradeSerializer INSTANCE = new UpgradeSerializer();
    public static final String Name = "upgrade";
    public static final Identifier ID = new Identifier(ae2wtlib.MOD_NAME, Name);
    public static final RecipeType<Upgrade> TYPE = RecipeType.register(Name);

    @Override
    public Upgrade read(Identifier id, JsonObject json) {
        UpgradeJsonFormat recipeJson = new Gson().fromJson(json, UpgradeJsonFormat.class);
        if(recipeJson.Terminal == null || validateOutput(recipeJson.TerminalName))
            throw new JsonSyntaxException("A required attribute is missing or invalid!");

        Ingredient Terminal = Ingredient.fromJson(recipeJson.Terminal);

        return new Upgrade(Terminal, recipeJson.TerminalName, id);
    }

    @Override
    public void write(PacketByteBuf packetData, Upgrade recipe) {
        recipe.getTerminal().write(packetData);
        packetData.writeString(recipe.getTerminalName());
    }

    @Override
    public Upgrade read(Identifier id, PacketByteBuf packetData) {
        Ingredient TerminalA = Ingredient.fromPacket(packetData);
        String TerminalAName = packetData.readString(32767);
        return new Upgrade(TerminalA, TerminalAName, id);
    }
}