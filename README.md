# YZCommand - Minecraft 快捷指令插件

> 🚀 一个简单高效的快捷指令管理插件，让你通过简单编号快速执行复杂命令！

## 📦 插件特性

- ✅ **一键保存**：将任意长度的命令保存为简短编号
- ✅ **快速执行**：通过编号立即执行保存的命令
- ✅ **永久存储**：重启服务器后指令不会丢失
- ✅ **支持玩家/控制台**：玩家和服务器控制台均可使用
- ✅ **Tab自动补全**：输入命令时智能提示
- ✅ **无依赖**：无需其他插件支持
- ✅ **彩色界面**：友好的彩色反馈信息

## 📖 安装方法

### 1. 下载插件
将 `YZCommand.jar` 文件放入服务器的 `plugins` 文件夹。

### 2. 重启服务器
```bash
# 在服务器控制台输入
restart
# 或使用重载命令
reload confirm
```

### 3. 验证安装
启动服务器后，控制台会显示：
```
==================================
     YZCommand 快捷指令插件
          已成功加载！
==================================
```

## 🎮 命令大全

### 主命令：`/yz` 或 `/yzh` 或 `/yzc`

| 命令 | 权限 | 描述 | 示例 |
|------|------|------|------|
| `/yz read <指令> <编号>` | 无 | 保存指令到指定编号 | `/yz read say 你好世界 hello` |
| `/yz open <编号>` | 无 | 执行指定编号的指令 | `/yz open hello` |
| `/yz list` | 无 | 查看所有保存的指令 | `/yz list` |
| `/yz remove <编号>` | 无 | 删除指定编号的指令 | `/yz remove hello` |
| `/yz` 或 `/yz help` | 无 | 显示帮助信息 | `/yz` |

## 📝 使用教程

### 基础使用

#### 1. 保存你的第一个指令
```bash
# 保存一个问候指令
/yz read say 欢迎来到服务器！ welcome

# 保存传送指令
/yz read tp @p 100 64 100 spawn
```

#### 2. 执行保存的指令
```bash
# 执行欢迎指令
/yz open welcome

# 执行传送指令
/yz open spawn
```

#### 3. 管理指令
```bash
# 查看所有保存的指令
/yz list

# 删除不需要的指令
/yz remove welcome
```

### 高级用法

#### 创建快捷菜单
```bash
# 保存菜单指令
/yz read say §6=== 快捷菜单 === menu
/yz read say §e1. §7回城 → §a/yz open home menu_item1
/yz read say §e2. §7获取食物 → §a/yz open food menu_item2

# 执行菜单
/yz open menu
/yz open menu_item1
/yz open menu_item2
```

#### 批量操作
```bash
# 清理服务器
/yz read kill @e[type=item] clear_items
/yz read kill @e[type=arrow] clear_arrows

# 设置时间和天气
/yz read time set day set_day
/yz read weather clear sunny
```

#### 玩家奖励系统
```bash
# 新手礼包
/yz read give @p diamond 1 newbie_kit1
/yz read give @p cooked_beef 10 newbie_kit2
/yz read effect give @p regeneration 30 1 newbie_kit3
```

## 🔧 配置文件

插件会在首次运行时生成配置文件：
```
plugins/YZCommand/
├── config.yml      # 插件配置文件
└── commands.yml    # 保存的指令数据（不要手动修改）
```

### config.yml 配置项
```yaml
# YZCommand 插件配置

settings:
  # 是否允许控制台使用所有功能（默认：true）
  allow-console: true
  
  # 指令最大保存数量（默认：100）
  max-commands: 100
  
  # 是否记录指令执行日志（默认：true）
  log-commands: true
  
  # 允许的指令前缀（默认：所有指令）
  allowed-commands:
    - say
    - tp
    - give
    - effect
    # 添加更多允许的指令...
```

## 💡 实用场景

### 场景1：服务器管理
```bash
# 保存常用管理命令
/yz read gamemode creative @a op_gm_creative
/yz read difficulty hard op_difficulty_hard
/yz read save-all op_save_world
```

### 场景2：活动组织
```bash
# 活动开始指令
/yz read say §6活动即将开始！ activity_start
/yz read effect give @a speed 60 2 activity_buff
/yz read give @a diamond_sword 1 activity_reward
```

### 场景3：建筑辅助
```bash
# 建筑模式
/yz read gamemode creative @p build_mode
/yz read give @p stone 64 build_stone
/yz read give @p oak_planks 64 build_wood
```

### 场景4：新手引导
```bash
# 新手教程
/yz read title @p title {"text":"欢迎","color":"gold"} welcome_title
/yz read tellraw @p {"text":"输入 /help 查看帮助","color":"yellow"} welcome_msg
```

## ⚠️ 注意事项

1. **权限控制**：
   - 玩家只能执行自己保存的指令（如果实现权限系统）
   - OP可以执行所有指令

2. **指令限制**：
   - 不支持带 `&` 符号的颜色代码（使用 `§`）
   - 建议不要保存过于复杂的命令链

3. **数据安全**：
   - 定期备份 `plugins/YZCommand/commands.yml`
   - 不要分享包含敏感权限的指令编号

4. **性能优化**：
   - 建议保存的指令总数不要超过 100 个
   - 定期清理不再使用的指令

## 🔄 更新日志

### v1.0.0 (当前版本)
- ✅ 基础指令保存/执行功能
- ✅ 数据持久化存储
- ✅ Tab 自动补全
- ✅ 彩色反馈界面
- ✅ 配置文件支持

### 计划功能
- [ ] 权限系统（不同玩家组权限）
- [ ] 指令分类和标签
- [ ] 批量导入/导出指令
- [ ] 定时执行指令功能
- [ ] 与其他插件集成（PlaceholderAPI等）

## ❓ 常见问题

### Q1: 为什么插件加载失败？
A: 检查 Java 版本是否为 17+，确保插件文件完整。

### Q2: 保存的指令重启后消失了？
A: 检查 `commands.yml` 文件是否有写入权限，确保插件正常关闭。

### Q3: 玩家无法使用某些指令？
A: 玩家需要有执行底层命令的权限，插件只负责转发命令。

### Q4: 如何备份指令数据？
A: 复制 `plugins/YZCommand/commands.yml` 文件即可。

### Q5: 支持变量替换吗？
A: 当前版本不支持，计划在后续版本中添加。

## 📞 支持与反馈

- **作者**: lugewxp
- **网站**: lugewxp.serv00.net
- **问题反馈**: 服务器控制台查看详细错误信息

## 📄 开源协议

本插件遵循 MIT 协议开源，欢迎改进和分享！

---

**✨ 提示**: 善用此插件可以极大提升服务器管理效率，建议为常用操作创建简短易记的编号！

**最后更新**: 2026年2月8日

---
