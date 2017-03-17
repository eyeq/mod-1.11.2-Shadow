package eyeq.shadow;

import eyeq.shadow.client.renderer.entity.RenderGiantShadow;
import eyeq.shadow.client.renderer.entity.RenderShadow;
import eyeq.shadow.entity.monster.EntityGiantShadow;
import eyeq.shadow.entity.monster.EntityShadow;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.common.registry.UEntityRegistry;
import eyeq.util.item.crafting.UCraftingManager;
import eyeq.util.world.biome.BiomeUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

import static eyeq.shadow.Shadow.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class Shadow {
    public static final String MOD_ID = "eyeq_shadow";

    @Mod.Instance(MOD_ID)
    public static Shadow instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item lucidShard;
    public static Item darkMatter;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        registerEntities();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        registerEntityRenderings();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        lucidShard = new Item().setUnlocalizedName("lucidShard").setCreativeTab(CreativeTabs.MATERIALS);
        darkMatter = new Item().setUnlocalizedName("darkMatter").setCreativeTab(CreativeTabs.MATERIALS);

        GameRegistry.register(lucidShard, resource.createResourceLocation("lucid_shard"));
        GameRegistry.register(darkMatter, resource.createResourceLocation("dark_matter"));

        OreDictionary.registerOre("darkMatter", darkMatter);
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(UCraftingManager.getRecipeCompress(darkMatter, lucidShard));
    }

    public static void registerEntities() {
        UEntityRegistry.registerModEntity(resource, EntityShadow.class, "HeartlessShadow", 0, instance, 0x0C1E2C, 0xF2C846);
        EntityRegistry.addSpawn(EntityShadow.class, 5, 20, 40, EnumCreatureType.MONSTER, BiomeUtils.getBiomes().toArray(new Biome[0]));

        UEntityRegistry.registerModEntity(resource, EntityGiantShadow.class, "HeartlessGiantShadow", 1, instance, 0x0C1E2C, 0xF2C846);
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(lucidShard);
        UModelLoader.setCustomModelResourceLocation(darkMatter);
    }

	@SideOnly(Side.CLIENT)
    public static void registerEntityRenderings() {
        RenderingRegistry.registerEntityRenderingHandler(EntityShadow.class, RenderShadow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGiantShadow.class, RenderGiantShadow::new);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-Shadow");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, lucidShard, "Lucid Shard");
        language.register(LanguageResourceManager.JA_JP, lucidShard, "透きとおるかけら");
        language.register(LanguageResourceManager.EN_US, darkMatter, "Dark Matter");
        language.register(LanguageResourceManager.JA_JP, darkMatter, "ダークマター");

        language.register(LanguageResourceManager.EN_US, EntityShadow.class, "Shadow");
        language.register(LanguageResourceManager.JA_JP, EntityShadow.class, "シャドウ");
        language.register(LanguageResourceManager.EN_US, EntityGiantShadow.class, "Giant Shadow");
        language.register(LanguageResourceManager.JA_JP, EntityGiantShadow.class, "ギガントシャドウ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, lucidShard, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, darkMatter, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
