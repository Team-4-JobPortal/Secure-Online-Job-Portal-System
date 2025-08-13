\# Git Error

---
```git
Yash Kushwaha@ykushwaha MINGW64 ~/Documents/workspace-spring-tools-for-eclipse-4.31.0.RELEASE/Secure-Online-Job-Portal-System (Test)
$ git status
On branch Test
Your branch is up to date with 'origin/Test'.

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        bin/

nothing added to commit but untracked files present (use "git add" to track)

```

### Solution

```git
nano .gitignore

bin/  <--- add this line in the file
```
