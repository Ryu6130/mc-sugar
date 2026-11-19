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
	
	public static final EntityType<BlackHole> BLACK_HOLE = register(
		ModEntityTypeIds.BLACK_HOLE,
		EntityType.Builder.<BlackHole>of(BlackHole::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
	);
	
	public static final EntityType<WindAttack> WIND_ATTACK = register(
		ModEntityTypeIds.WIND_ATTACK,
		EntityType.Builder.<WindAttack>of(WindAttack::new, MobCategory.MISC)
			.sized(1F, 0.125F)
	);
	
	public static final EntityType<Alligant> ALLIGANT = register(
		ModEntityTypeIds.ALLIGANT,
		EntityType.Builder.<Alligant>of(Alligant::new, MobCategory.MONSTER)
			.sized(1.25F, 0.75F)
			.notInPeaceful(),
		Alligant.createAttributes()
	);
	
	public static final EntityType<AlligantNest> ALLIGANT_NEST = register(
		ModEntityTypeIds.ALLIGANT_NEST,
		EntityType.Builder.<AlligantNest>of(AlligantNest::new, MobCategory.MONSTER)
			.sized(0.6F, 0.125F)
			.notInPeaceful(),
		AlligantNest.createAttributes()
	);
	
	public static final EntityType<RedAMD> RED_AMD = register(
		ModEntityTypeIds.RED_AMD,
		EntityType.Builder.<RedAMD>of(RedAMD::new, MobCategory.MONSTER)
			.sized(3F, 4F)
			.notInPeaceful(),
		RedAMD.createAttributes()
	);
	
	public static final EntityType<BlueAMD> BLUE_AMD = register(
		ModEntityTypeIds.BLUE_AMD,
		EntityType.Builder.<BlueAMD>of(BlueAMD::new, MobCategory.MONSTER)
			.sized(3F, 4F)
			.notInPeaceful(),
		BlueAMD.createAttributes()
	);
	
	public static final EntityType<Gnome> GNOME = register(
		ModEntityTypeIds.GNOME,
		EntityType.Builder.<Gnome>of(Gnome::new, MobCategory.CREATURE)
			.sized(0.5F, 1F),
		Gnome.createAttributes()
	);
	
	public static final EntityType<Arc> ARC = register(
		ModEntityTypeIds.ARC,
		EntityType.Builder.<Arc>of(Arc::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.eyeHeight(1.74F),
		Arc.createAttributes()
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