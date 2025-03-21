package infvoid.fishingnet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FishingNetMod implements ModInitializer {
	public static final String MOD_ID = "fishingnet";

	// Register the Fishing Net Block
	public static final Block FISHING_NET_BLOCK = new FishingNetBlock();
	public static final Item FISHING_NET_ITEM = new BlockItem(FISHING_NET_BLOCK, new Item.Settings());

	@Override
	public void onInitialize() {
		System.out.println("Fishing Net Mod has loaded!");

		// Register the block
		Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "fishing_net"), FISHING_NET_BLOCK);

		// Register the block item
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "fishing_net"), FISHING_NET_ITEM);

		// Add the block item to the Creative menu
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(FISHING_NET_ITEM));
	}
}
