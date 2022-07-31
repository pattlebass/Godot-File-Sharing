# Godot-File-Sharing

An Android plugin for Godot 3.5+ that allow you to share files saved in the `user://` directory.

If you want to share text, see [Shin-NiL/Godot-Android-Share-Plugin](https://github.com/Shin-NiL/Godot-Android-Share-Plugin).

# API Reference

## Functions
```gdscript
shareFile(path: String, title: String, subject: String, text: String, mimeType: String) -> void
```

> **Note**
> `path` has to be a file saved under `user://` and it has to be globalized with `ProjectSettings.globalize_path()`.

> **Note**
> Arguments cannot be `null`, instead use empty strings.

> **Note**
> See [a list of common MIME types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types).


# Usage
```gdscript
var android_share


func _ready() -> void:
    if Engine.has_singleton("GodotFileSharing"):
        android_share = Engine.get_singleton("GodotFileSharing")


func _on_Button_pressed() -> void:
    var absolute_path = ProjectSettings.globalize_path("user://path/to/file")
    android_share.shareFile(absolute_path, "The title", "The subject", "The text", "image/*")
    # or
    android_share.shareFile(absolute_path, "", "", "", "")
```
