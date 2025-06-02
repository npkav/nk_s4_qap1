# Changelog

---

### v0.1 - 2025-05-23 
- Created base Maven project
- Initial commit

### v0.1.1 - 2025-05-23
- Created packages for the tracker app as well as the cli, model, repo, and service layers
- Added some TODOs for initial direction
- Did some initial routing
- Began constructing some of the classes
- Started keeping docs with a readme and changelog

### v0.1.2 - 2025-05-23
- Updated pom.xml to include JUnit 5 (5.13.0) via junit-jupiter
- Updated Workout model:
  - Added id, durationMins, calsBurned, date fields
- Created Goal model:
  - Added id, currVal, targetVal, targetUnit, description, completionDate fields
  - Progress tracking with percentage calculation by currVal/targetVal
  - Auto-completion detection when currVal equals targetVal