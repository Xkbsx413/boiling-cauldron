# 变更日志

## 2026-10-30

### 收尾与文档

- 重写 `README.md`，补充项目定位、环境要求、当前内容和开发命令。
- 清理根目录临时文件 `data/`（森罗炒锅配料标签的参考拷贝，不参与构建）。
- 将 `src/main/generated/.cache/` 加入 `.gitignore`，并从 Git 索引中移除已跟踪的缓存文件。
- 说明：`src/main/generated` 下新增的方块状态、模型、配方、掉落表和标签仍需随代码提交。

## 2026-10-25

### 森罗厨房前置与底料

- 新增森罗厨房 `1.0.1-fabric+mc1.20.1` 前置依赖。
- 新增菌菇底料、麻辣底料、藤椒底料物品、贴图、翻译和创造模式物品栏条目。
- 新增三种底料的森罗炒锅配方。
- 新增 `ModTags` 与 `ModItemTagProvider`：
  - 原版蘑菇标签 `boiling-cauldron:mushrooms`。
  - 把大蒜、竹荪、花椒和森罗油脂块加入 `kaleidoscope_cookery:pot_ingredient`。
- 新增 `docs/design.md`，记录麻辣烫/钵钵鸡核心玩法设计。
- 数据生成新增底料配方、物品标签、模型和翻译。

## 2026-10-23

- 基线提交：`b45233b`（2026-10-11）
- 记录范围：该提交之后工作区中的全部未提交更改。

### 新增方块与作物

#### 冬笋作物

- 新增 `BambooShootCropBlock`，共 6 个阶段（`AGE_5`，0~5）。
- 可种植在 `#minecraft:bamboo_plantable_on` 上，不受光照限制。
- 每次随机刻有 50% 概率生长一级。
- 成熟后使用骨粉会转化为原版竹子，并按原版竹子逻辑继续生长。
- 仅成熟时破坏掉落 1 个冬笋，未成熟无掉落。
- 新增 6 张阶段贴图和 6 个十字模型，并加入 `cutout` 渲染。

#### 竹荪菌落

- 新增 `BambooFungusColonyBlock`，使用小型碰撞/轮廓形状。
- 可放置在 `#minecraft:dirt` 或耕地上；下方支撑消失时会自动破坏。
- 普通破坏掉落 2~3 个竹荪；使用剪刀破坏掉落菌落方块自身。

#### 竹根殖块

- 新增 `BambooRootBlock` 与 `BambooRootBlockEntity`。
- 方块状态 `bamboo_count` 为 0~5，用于显示当前存活产物数量。
- 每 1200 tick 检查一次，最多尝试生成 2 个产物。
- 基础成功率 55%，附近每格原版竹子增加 1%，最高增加 20%。
- 产物有 55% 概率为冬笋作物，45% 概率为竹荪菌落。
- 生成范围为竹根殖块上方 5×5×8；同一时间最多保留 5 个存活产物。
- 产物位置写入 NBT，产物被采收/破坏后计数自动回落。
- 新增合成配方：8 个竹子 + 1 个泥土。

### 盛花荷叶与藏藕泥块

#### 盛花荷叶

- 新增 `BloomingLotusLeafBlock` 与 `BloomingLotusLeafBlockEntity`。
- 生长阶段 `growth` 为 0~4，共 5 个阶段。
- 自然生长间隔 1200 tick；成熟后每 600 tick 尝试转化下方方块。
- 骨粉规则：
  - 未成熟时增加 1 级，不重置自然生长计时，不产生粒子。
  - 成熟时尝试 5×5 同层扩散；成败都消耗骨粉，成功时父叶和新叶回到阶段 0，父叶产生粒子。
- 转化规则：
  - 从下方第 2~4 格开始扫描。
  - 可穿透水、流动水、海带、海草、珊瑚、海泡菜、睡莲、气泡柱等。
  - 遇到冰会停止；遇到 `#minecraft:dirt`、`#minecraft:sand` 或砂砾时转化为藏藕泥块。
  - 转化成功后荷叶回到阶段 0。
- 扩散目标要求上方为空气、下方为静止水源，且目标区块已加载。
- 新增无序合成配方：原版荷叶 + 任意花朵。
- 旧存档通过 `LotusDataVersion` 判断；缺失时重置生长与转化计时。

#### 藏藕泥块

- 新增 `LotusMudBlock`，由成熟荷叶转化泥土/沙子/砂砾获得。
- 普通破坏掉落 1~4 个莲藕；使用精准采集工具或锄头破坏时掉落方块自身。
- 手持任意铲子（`#minecraft:shovels`）右键时：
  - 方块变为原版泥巴。
  - 固定掉落 3 个莲藕。
  - 非创造模式消耗 1 点铲子耐久。
  - 播放铲子音效。
- 目前没有独立合成配方，仅通过荷叶转化获得。

### 其他调整

- 花椒作物从 4 阶段扩展为 5 阶段（`AGE_4`），同步更新模型、轮廓和掉落判定。
- 新增 `SeedItem`，为种子类 `BlockItem` 提供独立翻译键。
- 新增 `ModBlockEntities`，注册竹根殖块和盛花荷叶的方块实体类型。
- 新增内容的方块、物品、翻译和创造模式物品栏条目已补齐。
- 冬笋、竹荪菌落、盛花荷叶加入 `cutout` 渲染。
- 新增内容已补齐掉落表；藏藕泥块加入锹可挖掘标签。

### 数据生成与资源

- `Model.java` 已接入 datagen：
  - 花椒 5 阶段模型。
  - 冬笋 6 阶段十字模型。
  - 竹荪菌落十字模型。
  - 竹根殖块和藏藕泥块的普通方块模型。
  - 盛花荷叶 5 阶段平面模型、blockstate 和 stage0 物品模型。
- 新增 `ModRecipeProvider`，生成竹根殖块和盛花荷叶配方。
- 扩展 `ModLootTableProvider`，生成冬笋、竹荪菌落、盛花荷叶和藏藕泥块的掉落表。
- 移除盛花荷叶的手写 blockstate/模型/物品模型，改由 `src/main/generated` 统一生成。
- 生成结果位于 `src/main/generated/assets/boiling-cauldron` 和 `src/main/generated/data/boiling-cauldron`。
- 新增竹子、竹荪、盛花荷叶、藏藕泥块、生姜等贴图；盛花荷叶阶段 0~4 的正式贴图已补齐。

### 主要文件

新增：

- `src/main/java/com/boilingcauldron/block/BambooShootCropBlock.java`
- `src/main/java/com/boilingcauldron/block/BambooFungusColonyBlock.java`
- `src/main/java/com/boilingcauldron/block/BambooRootBlock.java`
- `src/main/java/com/boilingcauldron/block/BloomingLotusLeafBlock.java`
- `src/main/java/com/boilingcauldron/block/LotusMudBlock.java`
- `src/main/java/com/boilingcauldron/block/entity/BambooRootBlockEntity.java`
- `src/main/java/com/boilingcauldron/block/entity/BloomingLotusLeafBlockEntity.java`
- `src/main/java/com/boilingcauldron/block/entity/ModBlockEntities.java`
- `src/main/java/com/boilingcauldron/item/SeedItem.java`
- `src/main/java/com/boilingcauldron/datagen/ModRecipeProvider.java`

修改：

- `src/main/java/com/boilingcauldron/BoilingCauldron.java`
- `src/main/java/com/boilingcauldron/BoilingCauldronClient.java`
- `src/main/java/com/boilingcauldron/BoilingCauldronDataGenerator.java`
- `src/main/java/com/boilingcauldron/block/ModBlocks.java`
- `src/main/java/com/boilingcauldron/block/SichuanPepperCropBlock.java`
- `src/main/java/com/boilingcauldron/datagen/Model.java`
- `src/main/java/com/boilingcauldron/datagen/ModLootTableProvider.java`
- `src/main/java/com/boilingcauldron/datagen/Chinese.java`
- `src/main/java/com/boilingcauldron/datagen/English.java`
- `src/main/java/com/boilingcauldron/item/ModItemGroups.java`

### 验证

以下命令均已执行并通过：

```powershell
.\gradlew.bat compileJava --no-daemon
.\gradlew.bat runDatagen --no-daemon
.\gradlew.bat build --no-daemon
```
