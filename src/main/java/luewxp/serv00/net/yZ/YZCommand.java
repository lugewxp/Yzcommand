package luewxp.serv00.net.yZ;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class YZCommand extends JavaPlugin implements TabExecutor {

    private File dataFile;
    private YamlConfiguration dataConfig;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a==================================");
        Bukkit.getConsoleSender().sendMessage("§a     YZCommand 快捷指令插件");
        Bukkit.getConsoleSender().sendMessage("§a          已成功加载！");
        Bukkit.getConsoleSender().sendMessage("§a==================================");

        try {
            // 创建数据文件夹
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
                Bukkit.getConsoleSender().sendMessage("§a[YZCommand] 已创建数据文件夹");
            }

            // 初始化数据文件
            setupDataFile();

            // 注册命令
            this.getCommand("yz").setExecutor(this);
            this.getCommand("yz").setTabCompleter(this);

            Bukkit.getConsoleSender().sendMessage("§a[YZCommand] 插件初始化完成");
            Bukkit.getConsoleSender().sendMessage("§e命令: /yz read [指令] [编号]");
            Bukkit.getConsoleSender().sendMessage("§e命令: /yz open [编号]");
            Bukkit.getConsoleSender().sendMessage("§e命令: /yz list");
            Bukkit.getConsoleSender().sendMessage("§e命令: /yz remove [编号]");

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c[YZCommand] 启动错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        saveDataConfig();
        Bukkit.getConsoleSender().sendMessage("§c[YZCommand] 插件已卸载");
    }

    private void setupDataFile() {
        dataFile = new File(getDataFolder(), "commands.yml");

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§a[YZCommand] 已创建命令数据文件");
            } catch (IOException e) {
                Bukkit.getLogger().severe("[YZCommand] 创建命令数据文件失败: " + e.getMessage());
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("[YZCommand] 保存命令数据失败: " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            showHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "read":
                return handleRead(sender, args);
            case "open":
                return handleOpen(sender, args);
            case "list":
                return handleList(sender);
            case "remove":
                return handleRemove(sender, args);
            default:
                sender.sendMessage("§c未知子命令: " + args[0]);
                showHelp(sender);
                return true;
        }
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("§6=== YZCommand 快捷指令插件 ===");
        sender.sendMessage("§e/yz read [指令] [编号] §7- 保存指令");
        sender.sendMessage("§e/yz open [编号] §7- 执行指令");
        sender.sendMessage("§e/yz list §7- 查看所有指令");
        sender.sendMessage("§e/yz remove [编号] §7- 删除指令");
        sender.sendMessage("§6==============================");
    }

    private boolean handleRead(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c用法: /yz read [指令] [编号]");
            sender.sendMessage("§c示例: /yz read say 你好世界 hello");
            return true;
        }

        // 构建指令字符串（排除最后一个参数，即编号）
        StringBuilder commandBuilder = new StringBuilder();
        for (int i = 1; i < args.length - 1; i++) {
            commandBuilder.append(args[i]).append(" ");
        }
        String commandStr = commandBuilder.toString().trim();
        String id = args[args.length - 1];

        // 保存到配置文件
        dataConfig.set("commands." + id, commandStr);
        saveDataConfig();

        sender.sendMessage("§a已保存指令！");
        sender.sendMessage("§e编号: §f" + id);
        sender.sendMessage("§e指令: §f" + commandStr);
        return true;
    }

    private boolean handleOpen(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c用法: /yz open [编号]");
            sender.sendMessage("§c示例: /yz open hello");
            return true;
        }

        String id = args[1];
        String commandStr = dataConfig.getString("commands." + id);

        if (commandStr == null || commandStr.isEmpty()) {
            sender.sendMessage("§c编号 '§e" + id + "§c' 不存在");
            return true;
        }

        sender.sendMessage("§a▶ 正在执行指令: §e" + commandStr);

        // 执行指令
        boolean success = Bukkit.dispatchCommand(sender, commandStr);

        if (success) {
            sender.sendMessage("§a指令执行成功");
        } else {
            sender.sendMessage("§c指令执行失败");
        }

        return true;
    }

    private boolean handleList(CommandSender sender) {
        if (!dataConfig.contains("commands")) {
            sender.sendMessage("§c暂无保存的指令");
            return true;
        }

        Set<String> keys = dataConfig.getConfigurationSection("commands").getKeys(false);
        if (keys.isEmpty()) {
            sender.sendMessage("§c暂无保存的指令");
            return true;
        }

        sender.sendMessage("§6=== 已保存的指令 (" + keys.size() + " 个) ===");

        int index = 1;
        for (String key : keys) {
            String cmd = dataConfig.getString("commands." + key);
            sender.sendMessage("§e" + index + ". §7[§e" + key + "§7] §f→ §7" + cmd);
            index++;
        }

        return true;
    }

    private boolean handleRemove(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c用法: /yz remove [编号]");
            sender.sendMessage("§c示例: /yz remove hello");
            return true;
        }

        String id = args[1];
        if (!dataConfig.contains("commands." + id)) {
            sender.sendMessage("§c编号 '§e" + id + "§c' 不存在");
            return true;
        }

        String removedCommand = dataConfig.getString("commands." + id);
        dataConfig.set("commands." + id, null);
        saveDataConfig();

        sender.sendMessage("§a已删除指令");
        sender.sendMessage("§e编号: §f" + id);
        sender.sendMessage("§e原指令: §f" + removedCommand);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // 一级补全：子命令
            completions.add("read");
            completions.add("open");
            completions.add("list");
            completions.add("remove");
        } else if (args.length == 2) {
            // 二级补全：编号（针对 open 和 remove 命令）
            String subCommand = args[0].toLowerCase();
            if (subCommand.equals("open") || subCommand.equals("remove")) {
                if (dataConfig.contains("commands")) {
                    completions.addAll(dataConfig.getConfigurationSection("commands").getKeys(false));
                }
            }
        }

        return completions;
    }
}