# 沸鼎烟火（Boiling Cauldron）

以麻辣烫、钵钵鸡为主题的 Fabric 烹饪模组，围绕“农作物与食材获取 → 炒制底料 → 制作汤底/成品”展开。

当前版本主要完成食材获取链和三种底料；钵钵鸡、麻辣烫等成品玩法仍在设计中，详见 [`docs/design.md`](docs/design.md)。

## 环境要求

- Minecraft 1.20.1
- Java 17+
- Fabric Loader 0.19.3+
- Fabric API 0.92.11+1.20.1
- [森罗厨房（Kaleidoscope Cookery）](https://modrinth.com/mod/kaleidoscope-cookery) 1.0.1-fabric+mc1.20.1

## 当前内容

- 作物：生姜、大蒜、贡菜、花椒、冬笋
- 种子获取：四种作物种子可从村庄箱子和流浪商人处获得
- 菌类与水生材料：竹荪菌落、竹根殖块、盛花荷叶、藏藕泥块
- 底料：菌菇底料、麻辣底料、藤椒底料
- 森罗厨房炒锅配方与 `pot_ingredient` 物品标签
- 完整的方块/物品模型、掉落表、配方、标签与中英文翻译数据生成

## 开发

```powershell
# 启动客户端
.\gradlew.bat runClient

# 启动服务端
.\gradlew.bat runServer

# 运行数据生成
.\gradlew.bat runDatagen

# 构建
.\gradlew.bat build
```

- 数据生成输出位于 `src/main/generated`，除 `.cache` 外需要随代码提交。
- 手写资源位于 `src/main/resources/assets/boiling-cauldron`。
- 设计记录位于 `docs/design.md`，开发计划与任务记录位于 `docs/development-log.md`，变更记录位于 `CHANGELOG.md`。

## 许可证

本项目基于 CC0-1.0 许可，详见 [`LICENSE`](LICENSE)。
