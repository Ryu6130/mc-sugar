package sugar.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class ModEntityTypes {
	public static final EntityType<DesertDessert> DESERT_DESSERT = register(
		ModEntityTypeIds.DESERT_DESSERT,
		EntityType.Builder.<DesertDessert>of(DesertDessert::new, MobCategory.MONSTER)
			.sized(0.75f, 0.5f)
			.notInPeaceful(),
		DesertDessert.createAttributes()
	);
	
	public static final EntityType<Sugardian> SUGARDIAN = register(
		ModEntityTypeIds.SUGARDIAN,
		EntityType.Builder.<Sugardian>of(Sugardian::new, MobCategory.MONSTER)
			.sized(2.5f, 3f)
			.notInPeaceful(),
		Sugardian.createAttributes()
	);
	
	public static final EntityType<BerryGod> BERRY_GOD = register(
		ModEntityTypeIds.BERRY_GOD,
		EntityType.Builder.<BerryGod>of(BerryGod::new, MobCategory.MONSTER)
			.sized(1.5f, 2.5f)
			.notInPeaceful(),
		BerryGod.createAttributes()
	);
	
	public static final EntityType<PentuskEntity> PENTUSK = register(
		ModEntityTypeIds.PENTUSK,
		EntityType.Builder.<PentuskEntity>of(PentuskEntity::new, MobCategory.MISC)
			.sized(0.5f, 0.5f)
	);
	
	public static final EntityType<HermitDragon> HERMIT_DRAGON = register(
		ModEntityTypeIds.HERMIT_DRAGON,
		EntityType.Builder.<HermitDragon>of(HermitDragon::new, MobCategory.MONSTER)
			.sized(3f, 4.5f)
			.notInPeaceful(),
		HermitDragon.createAttributes()
	);
	
	public static final EntityType<HermitDragonPart> HERMIT_DRAGON_PART = register(
		ModEntityTypeIds.HERMIT_DRAGON_PART,
		EntityType.Builder.<HermitDragonPart>of(HermitDragonPart::new, MobCategory.MISC)
	);
	
	public static final EntityType<Sweeper> SWEEPER = register(
		ModEntityTypeIds.SWEEPER,
		EntityType.Builder.<Sweeper>of(Sweeper::new, MobCategory.MONSTER)
			.sized(0.6F, 1.7F)
			.clientTrackingRange(8)
			.notInPeaceful(),
		Sweeper.createAttributes()
	);
	
	public static final EntityType<Candied> CANDIED = register(
		ModEntityTypeIds.CANDIED,
		EntityType.Builder.<Candied>of(Candied::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.eyeHeight(1.74F)
			.passengerAttachments(new float[]{2.0125F})
			.ridingOffset(-0.7F)
			.clientTrackingRange(8)
			.notInPeaceful(),
		Candied.createAttributes()
	);

	private static <T extends Entity> EntityType<T> register(
        ResourceKey<EntityType<?>> key,
        EntityType.Builder<T> builder
    ) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	private static <T extends LivingEntity> EntityType<T> register(
        ResourceKey<EntityType<?>> key,
        EntityType.Builder<T> builder,
        AttributeSupplier.Builder attributes
    ) {
		EntityType<T> type = register(key, builder);
		FabricDefaultAttributeRegistry.register(type, attributes);
		return type;
	}

	public static void initialize() {}
}