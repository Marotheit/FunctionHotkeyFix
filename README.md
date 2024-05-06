# FunctionHotkeyFix
### Provides permission node support for the F3+F4 (or F3+N) Gamemode Switcher hotkey.
This project would not be possible without [RodneyMKay](https://github.com/SlimeNexus/F3NPerm). He did all the heavy lifting, I have simply updated the plugin for my personal needs.

This plugin will enable the Gamemode Switcher hotkeys (F3+F4 or F3+N) to work properly when the player has the `minecraft.command.gamemode` permission. The plugin also contains a LuckPerms hook to enable instant permission recognition, but will function fine without it.
<br /><br />

## How to Compile

```bash
# Test gradle to ensure your environment is acceptable.
./gradlew test
```
```bash
# Build plugin jar file; The destination directory for the plugin file will be `<Project Location>\build\libs\`.
./gradlew build
```

This plugin was designed to be reliable and lightweight. It does not use any non-public APIs.
Please report any issues, and I will see what I can remedy.
