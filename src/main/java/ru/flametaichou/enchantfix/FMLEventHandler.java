package ru.flametaichou.enchantfix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FMLEventHandler {

    public static final FMLEventHandler INSTANCE = new FMLEventHandler();

    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {

        EntityPlayer player = event.player;
        if (player.worldObj.getWorldTime() % 80 == 0 && !player.worldObj.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                for (ItemStack itemstack : player.inventory.mainInventory) {
                    clearEnchantments(itemstack, player);
                }
                for (ItemStack itemstack : player.inventory.armorInventory) {
                    clearEnchantments(itemstack, player);
                }
            }
        }
    }

    private static void clearEnchantments(ItemStack itemstack, EntityPlayer player) {
        if (Objects.nonNull(itemstack) && Objects.nonNull(itemstack.stackTagCompound)) {
            List<NBTTagList> nbttaglists = new ArrayList<NBTTagList>();
            if (Objects.nonNull(itemstack.stackTagCompound.getTagList("ench", 10))) {
                nbttaglists.add(itemstack.stackTagCompound.getTagList("ench", 10));
            }
            if (Objects.nonNull(itemstack.stackTagCompound.getTagList("StoredEnchantments", 10))) {
                nbttaglists.add(itemstack.stackTagCompound.getTagList("StoredEnchantments", 10));
            }
            for (NBTTagList nbttaglist : nbttaglists) {
                if (Objects.nonNull(nbttaglist)) {
                    for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                        short enchantId = nbttaglist.getCompoundTagAt(i).getShort("id");
                        short enchantLvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

                        // Protection
                        if (enchantId == 0) {
                            if (enchantLvl > 3) {
                                nbttaglist.getCompoundTagAt(i).setInteger("lvl", 3);
                                // Возврат
                                if (!player.worldObj.isRemote) {
                                    ItemStack book = new ItemStack(Items.enchanted_book, 1);
                                    EnchantmentData data = new EnchantmentData(Enchantment.protection, 1);
                                    Items.enchanted_book.addEnchantment(book, data);
                                    EntityItem entityBook = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, book);
                                    player.worldObj.spawnEntityInWorld(entityBook);
                                    player.addChatComponentMessage(new ChatComponentText("Вам было возвращено зачарование Защита I"));
                                }
                            }
                        }

                        // Fire Aspect
                        if (enchantId == 20) {
                            nbttaglist.removeTag(i);
                        }

                        // Flame Arrows
                        if (enchantId == 50) {
                            nbttaglist.removeTag(i);
                        }

                        // Infinity
                        if (enchantId == 51) {
                            nbttaglist.removeTag(i);
                        }
                    }
                }
            }
        }
    }
}
