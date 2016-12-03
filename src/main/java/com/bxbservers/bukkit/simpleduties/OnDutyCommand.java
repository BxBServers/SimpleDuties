package com.bxbservers.bukkit.simpleduties;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Logger;

public class OnDutyCommand implements CommandExecutor
{
   private Logger     logger;
   private Permission permission;

   private List<String> groups;

   public OnDutyCommand( Logger logger, Permission permission, List<String> groups )
   {
      this.logger     = logger;
      this.permission = permission;

      this.groups = groups;
   }

   @Override
   public boolean onCommand( CommandSender sender, Command command, String label, String[] args )
   {
      if( !( sender instanceof Player ) )
      {
         sender.sendMessage( ChatColor.RED + "This command must be run as a player." );

         return true;
      }

      Player player = (Player) sender;
      boolean toggled = false;

      for( String group : groups )
      {
         if( !permission.has( sender, "simpleduties.toggle." + group ) )
            continue;

         if( permission.playerInGroup( player, group ) )
            permission.playerRemoveGroup( player, group );
         else
            permission.playerAddGroup( player, group );

         toggled = true;
      }

      if( toggled )
         sender.sendMessage( ChatColor.GREEN + "Staff status toggled." );

      return true;
   }
}
