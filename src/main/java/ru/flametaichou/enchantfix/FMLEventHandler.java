package ru.flametaichou.enchantfix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import java.util.Objects;

public final class FMLEventHandler {

    public static final FMLEventHandler INSTANCE = new FMLEventHandler();

    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {

        EntityPlayer player = event.player;
        if (player.worldObj.getWorldTime() % 80 == 0 && !player.worldObj.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                for (ItemStack itemstack : player.inventory.mainInventory) {
                    if (Objects.nonNull(itemstack) && Objects.nonNull(itemstack.stackTagCompound)) {
                        System.out.println(itemstack.stackTagCompound);
                        NBTTagList nbttaglist = null;
                        if (Objects.nonNull(itemstack.stackTagCompound.getTagList("ench" , 10))) {
                            nbttaglist = itemstack.stackTagCompound.getTagList("ench" , 10);
                            System.out.println(nbttaglist);
                        }
                        if (Objects.nonNull(itemstack.stackTagCompound.getTagList("StoredEnchantments" , 10))) {
                            nbttaglist = itemstack.stackTagCompound.getTagList("StoredEnchantments" , 10);
                            System.out.println(nbttaglist);
                        }
                        if (Objects.nonNull(nbttaglist)) {
                            for (int i = 0; i < nbttaglist.tagCount(); ++i)
                            {
                                short enchantId = nbttaglist.getCompoundTagAt(i).getShort("id");
                                short enchantLvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

                                // Protection
                                if (enchantId == 0) {
                                    if (enchantLvl > 3) {
                                        nbttaglist.getCompoundTagAt(i).setInteger("lvl", 3);
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
    }


}
