# Patchify

Tool used to manage/automate code projects that require source-code patching.

This project was inspired by the tool used by [PaperMC](https://github.com/PaperMC/Paper).

# Example

```
Usage: patchify [OPTIONS] COMMAND [ARGS]...

Options:
  -h, --help  Show this message and exit

Commands:
  init             Initializes a git repository to be able to patch
                   source-code
  rebuild-patches  Rebuild patches.
  apply-patches    Apply all patches to the project.
  edit-patch       Edits a patch
```

## Commands

| Command                 | Description                                                                  |
|:-----------------------:| ---------------------------------------------------------------------------- |
| `init`                  | Initializes a git repository to be able to patch source-code.                |
| `rebuild-patches`       | Rebuild patch files through the commits done to the source directory.        |
| `apply-patches`         | Applies the patches on the `patches` directory to the `source` directory     |
| `edit-patch`            | Allows you to edit an existing patch by rolling back to the commit.          |
| `edit-patch --continue` | Finishes the edit of the specific patch. Automatically rebuilds the patches. |
| `edit-patch --abort`    | Cancels the edit of the specific patch.                                      |
