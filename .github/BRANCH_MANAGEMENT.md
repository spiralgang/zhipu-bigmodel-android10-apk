# Branch Management Guidelines

## Overview
This document establishes guidelines for branch management to prevent clutter and ensure efficient collaboration.

## Branch Naming Conventions

### Feature Branches
- `feature/descriptive-name` - New features
- `bugfix/issue-number-description` - Bug fixes
- `docs/section-name` - Documentation updates
- `refactor/component-name` - Code refactoring

### System Branches
- `main` - Production-ready code
- `develop` - Integration branch (if using GitFlow)

## Branch Lifecycle

### 1. Creation
- Create from latest `main` branch
- Use descriptive names indicating the work being done
- Reference issue numbers where applicable

### 2. Development
- Keep branches focused on single issues/features
- Regular commits with clear messages
- Rebase or merge from `main` regularly to stay current

### 3. Pull Request
- Create PR when work is ready for review
- Use descriptive PR titles and descriptions
- Link to relevant issues using "Fixes #issue-number"

### 4. After Merge
- **Delete feature branches immediately after merge**
- Update local repository: `git branch -d branch-name`
- Clean up remote branches: `git push origin --delete branch-name`

## Automatic Branch Cleanup

### Recommended GitHub Settings
Enable the following in repository settings:

1. **Automatically delete head branches** - Cleans up feature branches after PR merge
2. **Require branches to be up to date** - Prevents merge conflicts
3. **Require pull request reviews** - Ensures code quality

### Manual Cleanup Commands

```bash
# List all branches
git branch -a

# Delete local feature branch
git branch -d feature/branch-name

# Delete remote feature branch  
git push origin --delete feature/branch-name

# Remove tracking branches that no longer exist on remote
git remote prune origin
```

## Current Repository Status

### Active Branches
- `main` - Primary development branch
- `copilot/fix-20` - Current issue work (this branch)

### Stale Branches (Recommended for Deletion)
Based on merged PR analysis:
- `copilot/fix-2` - Work merged via PR #3 
- `copilot/fix-7` - Work merged, conflicts resolved
- `copilot/fix-12` - Work consolidated in main
- `copilot/fix-14` - Work consolidated in main

### Branch Cleanup Script
```bash
#!/bin/bash
# Clean up stale feature branches
git push origin --delete copilot/fix-2 2>/dev/null || echo "Branch copilot/fix-2 already deleted"
git push origin --delete copilot/fix-7 2>/dev/null || echo "Branch copilot/fix-7 already deleted"  
git push origin --delete copilot/fix-12 2>/dev/null || echo "Branch copilot/fix-12 already deleted"
git push origin --delete copilot/fix-14 2>/dev/null || echo "Branch copilot/fix-14 already deleted"
git remote prune origin
```

## Best Practices

### For Contributors
1. **One branch per issue/feature**
2. **Delete branches after merge**  
3. **Use meaningful branch names**
4. **Regularly sync with main branch**
5. **Test before creating PRs**

### For Maintainers  
1. **Enable automatic branch deletion**
2. **Regular branch cleanup reviews**
3. **Close stale PRs promptly**
4. **Maintain clean branch list**
5. **Document branch policies**

## Preventing Future Clutter

### Pre-merge Checklist
- [ ] Branch name follows conventions
- [ ] Work is complete and tested
- [ ] PR description is clear
- [ ] Issues are properly linked
- [ ] Conflicts are resolved

### Post-merge Checklist  
- [ ] Feature branch deleted
- [ ] Related issues closed/updated
- [ ] Documentation updated if needed
- [ ] Release notes updated if applicable

## Emergency Branch Recovery

If a branch is accidentally deleted:

```bash
# Find the commit hash
git reflog

# Recreate branch from commit
git checkout -b recovered-branch <commit-hash>
```

## Integration with Issue Management

- Link branches to issues using branch names: `fix/issue-20-deduplication`
- Use "Fixes #20" in PR descriptions to auto-close issues
- Tag PRs appropriately (bug, enhancement, etc.)
- Update issue status when branches are created/merged

This branch management system works in conjunction with the issue templates to maintain a clean, organized repository structure.