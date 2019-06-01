package me.drunkenmeows.mobcleaner;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobCleaner extends JavaPlugin implements Listener {
	
	public final static int BATCHSIZE = 50;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e)
	{
		Chunk chunk = e.getChunk();
		
		ArrayList<Ageable> chickens = new ArrayList<Ageable>();
		ArrayList<Ageable> cows = new ArrayList<Ageable>();
		ArrayList<Ageable> pigs = new ArrayList<Ageable>();
		ArrayList<Ageable> sheep = new ArrayList<Ageable>();
		ArrayList<Ageable> villagers = new ArrayList<Ageable>();
			
		for(Entity entity: chunk.getEntities())
		{
			if(entity instanceof Ageable ) 
			{
				if(entity instanceof Cow)
				{
					cows.add((Ageable) entity);
				}
				else if(entity instanceof Pig)
				{
					pigs.add((Ageable) entity);
				}
				else if(entity instanceof Chicken)
				{
					chickens.add((Ageable) entity);
				}
				else if(entity instanceof Sheep)
				{
					sheep.add((Ageable) entity);
					
				}
				else if(entity instanceof Villager)
				{
					villagers.add((Ageable) entity);
				}
			}
		}
		
		if(cows.size() > BATCHSIZE) {
			removeAgable(cows);
		}
		
		if(chickens.size() > BATCHSIZE) {
			removeAgable(chickens);
		}
		
		if(villagers.size() > BATCHSIZE) {
			removeAgable(villagers);
		}
		
		if(pigs.size() > BATCHSIZE) {
			removeAgable(pigs);
		}
		
		if(sheep.size() > BATCHSIZE) {
			removeAgable(sheep);
		}
	}
	
	public void removeAgable(ArrayList<Ageable> ageables)
	{
		int i = 0;
		for(; i < (ageables.size()-BATCHSIZE); i++)
		{
			Ageable a = ageables.get(i);
			if(a.isAdult()) 
			{
				a.remove();
			}
		}
		
		logger.info("["+i+"] " +ageables.get(0).getType().toString()+ " removed.");
	
	}
	
	@Override
	public void onDisable() {
		
		
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this,this);
		
	}	

}
