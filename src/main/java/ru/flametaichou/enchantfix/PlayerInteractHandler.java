package ru.flametaichou.enchantfix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.AnvilUpdateEvent;

import java.util.Objects;


public class PlayerInteractHandler {

    @SubscribeEvent
    public void onAnvilRepair(AnvilUpdateEvent event) {
        // Убираю повышение стоимости починки
        if (Objects.nonNull(event.left.getTagCompound())) {
            if (Objects.nonNull(event.left.getTagCompound().getString("RepairCost"))) {
                event.left.getTagCompound().removeTag("RepairCost");
            }
        }
        if (Objects.nonNull(event.right.getTagCompound())) {
            if (Objects.nonNull(event.right.getTagCompound().getString("RepairCost"))) {
                event.right.getTagCompound().removeTag("RepairCost");
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (Objects.nonNull(event.gui) && event.gui.getClass().getName().toLowerCase().contains("enchant")) {
            //event.gui.mc.thePlayer.sendChatMessage("Стол зачарований на этом сервере не работает. Создание чар производится другим способом.");
            //event.setCanceled(true);
        }
    }
}
