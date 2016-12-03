package com.bxbservers.bukkit.simpleduties;

import org.bukkit.Bukkit;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin
{
   private Permission permission;

   private List<String> groups = new ArrayList<>();

   @Override
   public void onEnable()
   {
      if( !setupPermissions() )
      {
         getLogger().severe( "Could not get Vault permission provider, disabling plugin." );

         Bukkit.getPluginManager().disablePlugin( this );
      }

      saveDefaultConfig();
      reloadGroups();

      getCommand( "onduty" )
               .setExecutor( new OnDutyCommand( getLogger(), permission, groups ) );
   }

   private void reloadGroups()
   {
      reloadConfig();

      groups = getConfig().getStringList( "groups" );
   }

   private boolean setupPermissions()
   {
      RegisteredServiceProvider<Permission> provider = Bukkit.getServicesManager().getRegistration( Permission.class );

      if( provider != null )
         permission = provider.getProvider();

      return permission != null;
   }
}
