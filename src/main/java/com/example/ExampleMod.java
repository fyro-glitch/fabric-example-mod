package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class ExampleMod implements ClientModInitializer {
    private long lastFrameClickTime = 0;
    private int lastEntityId = -1;

    @Override
    public void onInitializeClient() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof ItemFrameEntity) {
                long currentTime = System.currentTimeMillis();
                int currentEntityId = entity.getId();

                if (currentEntityId != lastEntityId || (currentTime - lastFrameClickTime) > 3000) {
                    lastFrameClickTime = currentTime;
                    lastEntityId = currentEntityId;

                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal("§c[⚠️ ANTI-TRUFFA] Click bloccato! Riclicca entro 3s per confermare."),
                            true
                        );
                    }
                    
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
    }
}
