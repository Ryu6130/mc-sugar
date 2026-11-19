package sugar.entity;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.trading.VillagerTrade;
import sugar.SugarMod;

public class GnomeTrades {
    private static List<VillagerTrade> trades = List.of();

    public static void setTrades(List<VillagerTrade> newTrades) {
        trades = List.copyOf(newTrades);
    }

    public static List<VillagerTrade> getTrades() {
        return trades;
    }

    public static List<VillagerTrade> load(ResourceManager manager) {
        List<VillagerTrade> trades = new ArrayList<>();

        Map<Identifier, Resource> resources =
            manager.listResources(
                "gnome_trade",
                id -> id.getPath().endsWith(".json")
            );

        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            try (BufferedReader reader = entry.getValue().openAsReader()) {
                JsonElement json = GsonHelper.parse(reader);
                
                VillagerTrade.CODEC.parse(JsonOps.INSTANCE, json).resultOrPartial(error ->
                    System.err.println(
                        "Failed to load trade: " + entry.getKey() + ": " + error
                    )
                ).ifPresent(trades::add);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return trades;
    }

    public static void initialize() {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(
            SugarMod.id("gnome_trade"),
            (currentReload, taskExecutor, preparationBarrier, reloadExecutor) -> {
                ResourceManager manager = currentReload.resourceManager();

                return CompletableFuture
                    .supplyAsync(
                        () -> load(manager),
                        taskExecutor
                    )
                    .thenCompose(preparationBarrier::wait)
                    .thenAcceptAsync(
                        GnomeTrades::setTrades,
                        reloadExecutor
                    );
            }
        );
    }
}
