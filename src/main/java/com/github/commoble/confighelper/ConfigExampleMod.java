package com.github.commoble.confighelper;

import com.github.commoble.confighelper.ConfigHelper.ConfigValueListener;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("configexample")
public class ConfigExampleMod
{	
	public static ConfigImplementation config;
	
	public ConfigExampleMod()
	{
		config = ConfigHelper.register(ModConfig.Type.SERVER, ConfigImplementation::new); // instantiate and subscribe our config instance
		
		MinecraftForge.EVENT_BUS.addListener(this::testConfig); // add a test event to make sure our config works
		
	}
	
	public static class ConfigImplementation
	{	
		public ConfigValueListener<Integer> bones;
		public ConfigValueListener<Double> bananas;
		
		public ConfigImplementation(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("General Category");
			this.bones = subscriber.subscribe(builder
				.comment("Bones")
				.translation("configexample.bones")
				.defineInRange("bones", 10, 1, 20));
			this.bananas = subscriber.subscribe(builder
				.comment("Bananas")
				.translation("configexample.bananas")
				.defineInRange("bananas", 0.5D, -10D, Double.MAX_VALUE));
			builder.pop();
		}
	}
	
	// print config values when player breaks a block
	public void testConfig(BreakEvent event)
	{
		System.out.println(config.bones.get());
		System.out.println(config.bananas.get());
	}

}
