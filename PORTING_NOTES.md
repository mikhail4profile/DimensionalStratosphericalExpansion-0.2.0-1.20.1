# Stratospherical Expansion — порт 1.18.2 → 1.20.1 (Forge)

Оригинал: Apothic0n, лицензия MIT (`license="MIT"` в `mods.toml`). Файл `LICENSE` в этом проекте
написан по стандартному шаблону — **замените его оригинальным `LICENSE` из репозитория** (с годом
и точным именем правообладателя): MIT требует сохранять исходное уведомление об авторских правах.

## Сборка

Нужна JDK 17 (если её нет, Gradle скачает сам).

    ./gradlew build          # jar: build/libs/DimensionalStratosphericalExpansion-1.20.1-0.2.0.jar
    ./gradlew runClient      # запуск клиента для проверки

## Что изменено

Java (7 классов восстановлены из байткода оригинала; имена пакетов и классов сохранены):
- `Feature.place` вместо `m_142674_`, `RandomSource` вместо `java.util.Random`, `Registries.DIMENSION`,
  `player.level()` вместо поля `level`, `BlockPos.containing(...)` вместо `new BlockPos(double...)`.
- `Material` удалён из 1.20, поэтому «проверка земли» в телепортере (она ничего не меняла — результат
  всегда `(x, 419, z)` / `(x, y, z)`) удалена; вызов `ForgeHooks.onTravelToDimension` тоже: он
  вызывался с ключом не того измерения, а результат игнорировался.
- Вместо `Math.random()` в `SpheroidRockFeature` используется `RandomSource` генерации (детерминированно
  для сида). Вероятности те же.
- Обработчик тика игрока срабатывает один раз за тик (фаза END) и только на сервере; раньше — на обеих фазах.

Датапак:
- Биомы: `precipitation` (rain/snow/none) → `has_precipitation` (true/true/false); поле `category` удалено.
- `minecraft:leave_vine` в деревьях: добавлено `probability: 0.25` (в 1.18.2 было зашито в коде).
- Измерение: формат настроек шума 1.19+. Старые `sampling`, `top_slide`, `bottom_slide`, `terrain_shaper`
  заменены явным density function — это точный аналог ванильного пресета `floating_islands` 1.20.1
  (`y_clamped_gradient` 4→32 и 184→440, `old_blended_noise` 0.25/0.25/80/160/4.0, `initial_density_without_jaggedness = 0`).
  Добавлен обязательный `spawn_target`. Удалён `seed` (с 1.19 измерение использует сид мира).
- `pack.mcmeta`: `pack_format` 8 → 15.
- `mods.toml`: `loaderVersion=[47,)`, зависимости от `forge` и `minecraft 1.20.1`.

## Исправленные баги оригинала (меняют поведение)

- Тег `strato:moss_replaceable_plus_sand` лежал в jar без расширения `.json`, поэтому не загружался, и
  фичи, которые на него ссылаются (`grassy_terracotta*` и др. `vegetation_patch`), по сути не
  работали. Теперь файл называется `moss_replaceable_plus_sand.json`, и эти фичи начнут генерироваться.
- Зависимость от TerraBlender в оригинале была записана в секции `[[dependencies.StartosphericalExpansion]]`
  (несуществующий modId), то есть не применялась, а в коде TerraBlender не используется — убрана.

## Что проверено, а что нет

Проверено машинно по данным ванильной 1.20.1 (misode/mcmeta):
- все `type`, id блоков/фич/биомов/карверов существуют в реестрах 1.20.1;
- ключи каждого объекта сверены с ванильными (оставшиеся две пометки — `min_clipped_height` и `offset` —
  допустимые необязательные поля);
- порядок фич всех 39 биомов измерения (strato + ванильные) — циклов `Feature order cycle` нет;
- структура `surface_rule` и `noise_router` совпадает с ванильной.

**Не проверено:** компиляция Java и запуск игры — в среде, где делался порт, нет доступа к
Forge/Mojang maven. Если `./gradlew build` выдаст ошибку компиляции, это почти наверняка имя метода
Forge/Mojang API — пришлите вывод, поправим. В игре стоит посмотреть:
1. подъём на Y≥420 в обычном мире → телепорт в измерение `strato:stratospherical`; падение ниже Y=-64 — обратно;
2. форма суши (парящие острова): если она отличается от 1.18.2, смотреть `final_density` в
   `data/strato/dimension/stratospherical.json`.
