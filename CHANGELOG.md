# [1.6.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.5.0...v1.6.0) (2025-10-19)


### Bug Fixes

* **build:** Added the mockito agent to the test JVM. ([6bbc808](https://github.com/Paladins-Inn/rollenspiel-cons/commit/6bbc808f93dbb52254dcb622ed2b6b07ef7d8409))
* **build:** getting tests up again by enabling the system to initialize without Google Calendar Service. ([98599ac](https://github.com/Paladins-Inn/rollenspiel-cons/commit/98599ac47bbb977d1018614ac212e54e9c7b6acd))
* **build:** I don't need the hash also added to the tag. ([4245df7](https://github.com/Paladins-Inn/rollenspiel-cons/commit/4245df75035a5a2622f4313dee5e864d81e9eb49))
* **build:** removed not working jackson-datatype-jsr353 ([8a46831](https://github.com/Paladins-Inn/rollenspiel-cons/commit/8a46831e305a36942b7e68583dc0fb0b7602f6c3))
* **build:** The UMA Protection API client did use the old names for the SSO integration pointing to paladins-inn instead of sso. ([c22fd6c](https://github.com/Paladins-Inn/rollenspiel-cons/commit/c22fd6c20340b4937a08e7dc99032ee328bbd8b5))
* **rollenspiel-cons:** According to Postel you don't need to be logged in to be able to logout. You want to log out and after the request you are still logged out. So @PermitAll is the way to go. ([5abd651](https://github.com/Paladins-Inn/rollenspiel-cons/commit/5abd65155b23397dce11f487c821e144eb726484))
* **rollenspiel-cons:** corrected login in layout to .../sso instead of .../paladins-inn and removed the drop down box (because we now only offer one SSO system and do the federation on the keycloak side). ([a38a520](https://github.com/Paladins-Inn/rollenspiel-cons/commit/a38a520c042f751efcdc279ff0c549b5b5f47b87))
* **rollenspiel-cons:** Logout should be possible :-) ([67eb3d5](https://github.com/Paladins-Inn/rollenspiel-cons/commit/67eb3d59c0ac012b680b7f04f8eca0de4e7d8641))
* **rollenspiel-cons:** permit GET, HEAD and OPTIONS to all and demand authentication for everything else. ([4225c76](https://github.com/Paladins-Inn/rollenspiel-cons/commit/4225c7641bf7ffedf571847a33685c336f39f9e2))


### Features

* Added geocode.maps.co for geomapping and improved the Caldav import (tested via google) ([#1](https://github.com/Paladins-Inn/rollenspiel-cons/issues/1)) ([2641dcf](https://github.com/Paladins-Inn/rollenspiel-cons/commit/2641dcf304adb48de625ed10bf0cd9126e4ee93c))
* **iam:** Added the IAM model for handling events. Tests need to be written. ([d3b619b](https://github.com/Paladins-Inn/rollenspiel-cons/commit/d3b619bccb98105161097f170cf4a7c39ff7e7b2))
* **iam:** Adding iam part with JPA persistence. ([d32cc1d](https://github.com/Paladins-Inn/rollenspiel-cons/commit/d32cc1de68b05dbbebac7030ce71d0aa06cd9441))
* **iam:** Adding the UMA client and some more documentation ([e1ba60f](https://github.com/Paladins-Inn/rollenspiel-cons/commit/e1ba60fee483e5036df320b300636c883944da7f))
* **iam:** Removed direct Discord login and added page object to error pages. ([8606d55](https://github.com/Paladins-Inn/rollenspiel-cons/commit/8606d552b32581e64169b1945c1856bdb512e6fd))
* **rollenspiel-cons:** Added JPA implementation of the model. ([2917e94](https://github.com/Paladins-Inn/rollenspiel-cons/commit/2917e94851c2d7ba382dce3cf288c801c45f216a))
* **rollenspiel-cons:** Added location, time and basic event model. ([3fdeecd](https://github.com/Paladins-Inn/rollenspiel-cons/commit/3fdeecdd23b94a5e85347ee8eb6a503af9bbbbb3))
* **rollenspiel-cons:** Added Repositories to retrieve events. ([1268113](https://github.com/Paladins-Inn/rollenspiel-cons/commit/1268113f9a605a71137bc16ef971c8478dd51369))
* **rollenspiel-cons:** Added spring-data-rest for CRUD and some more documentation of ADRs. ([bf79201](https://github.com/Paladins-Inn/rollenspiel-cons/commit/bf792011aec5b5813a9d4746054baf71f4747812))
* **rollenspiel-cons:** Handing the name of the favicon to the browser. ([4eb0d01](https://github.com/Paladins-Inn/rollenspiel-cons/commit/4eb0d012baac23441300046018a3e96b9fa158d9))
* **rollenspiel-cons:** removed IAM implementation and documented IAM decision in arc42 ([d066f53](https://github.com/Paladins-Inn/rollenspiel-cons/commit/d066f53466f6e6ac4f8dd4a31820e130741009ad))
* **rollenspiel-cons:** Update IAM configuration to use annotations instead of central rules in the SecurityConfiguration. And trying to get rid of the black menu. ([df12c77](https://github.com/Paladins-Inn/rollenspiel-cons/commit/df12c77db86932a0ae9e42deb7bce3a3d99d28a6))

# [1.5.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.4.0...v1.5.0) (2025-09-29)


### Features

* **rollenspiel-cons:** Datenschutzerklärung and Impressum added. ([29597d6](https://github.com/Paladins-Inn/rollenspiel-cons/commit/29597d642486f3a2cf6d4866087f85787e12326c))

# [1.4.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.3.0...v1.4.0) (2025-09-29)


### Features

* **rollenspiel-con:** BREAKING CHANGE remove / after ${contextPath} in templates. You need to set the trailing "/" to the contextPath variable in application.yaml. ([34146f1](https://github.com/Paladins-Inn/rollenspiel-cons/commit/34146f141b7db3fb139ce492288db38548275c3e))

# [1.3.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.2.1...v1.3.0) (2025-09-29)


### Features

* **rollenspiel-con:** moved management port to 8081 ([6c45720](https://github.com/Paladins-Inn/rollenspiel-cons/commit/6c45720bacdb7f25c83a240ce5f76d461fa0d47f))

## [1.2.1](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.2.0...v1.2.1) (2025-09-29)


### Bug Fixes

* **rollenspiel-con:** change the filename for the contribution license in copy command. ([1c6e194](https://github.com/Paladins-Inn/rollenspiel-cons/commit/1c6e19439ce1f2d2ae8439f36c790d9b6db3388c))

# [1.2.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.1.0...v1.2.0) (2025-09-29)


### Features

* **rollenspiel-cons:** Changed the logging ([bba4fb5](https://github.com/Paladins-Inn/rollenspiel-cons/commit/bba4fb5495ff045a1e0f9b81cc07978cd79b5438))
* **security:** Add some metadata files to the container. ([b0fe035](https://github.com/Paladins-Inn/rollenspiel-cons/commit/b0fe035a9dfd918036496ce0aa582810d9a928a1))

# [1.1.0](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.0.13...v1.1.0) (2025-09-28)


### Bug Fixes

* **security:** Update okio-jvm to 3.6.0 to fix CVE-2023-3635 ([e0dce02](https://github.com/Paladins-Inn/rollenspiel-cons/commit/e0dce020db06f3b37dc9533d178c26053e881fbf))


### Features

* **build:** Added dependabot configuration ([e0e844a](https://github.com/Paladins-Inn/rollenspiel-cons/commit/e0e844a4b4fb5aad12b25e00ad0277b822028420))

## 1.0.0 till [1.0.13](https://github.com/Paladins-Inn/rollenspiel-cons/compare/v1.0.0...v1.0.13) (2025-09-28)


### Bug Fixes

* **tests:** trying to get the tests running on github actions. ([54165d3](https://github.com/Paladins-Inn/rollenspiel-cons/commit/54165d30d4e1916477f4bbf46f5d34662206d189))
* **tests:** trying to get .env read also on github actions. ([84f6833](https://github.com/Paladins-Inn/rollenspiel-cons/commit/84f68336f5205bd9f155130c82b5dfac7bd1730a))
* **tests:** remove commons-logging as it is not needed at all. ([f617972](https://github.com/Paladins-Inn/rollenspiel-cons/commit/f61797247e01d26a855761c8f303d23ec4715a55))
* **tests:** Activate profile test for context test to read the .env file ([c2efe71](https://github.com/Paladins-Inn/rollenspiel-cons/commit/c2efe718b3d99b127e1c8f2498526af71e17d2f8))
* **build:** read .env file for tests. ([9e0ac29](https://github.com/Paladins-Inn/rollenspiel-cons/commit/9e0ac29ca323a04f126950bbde6d3b8baae21b2d))
* **build:** pull before push ([d76f11c](https://github.com/Paladins-Inn/rollenspiel-cons/commit/d76f11c940074e5816c99178699e08d7a6c10a38))
* **build:** fix typo in git describe ([ce24743](https://github.com/Paladins-Inn/rollenspiel-cons/commit/ce247433f0ec4029d0820f0c80c91073d5cc68cf))
* **build:** fix typo in git describe ([7dbb944](https://github.com/Paladins-Inn/rollenspiel-cons/commit/7dbb944cb429c9933ec7949ef30732796a3c3e04))
* **build:** debug POM_VERSION ([c2ad6f9](https://github.com/Paladins-Inn/rollenspiel-cons/commit/c2ad6f9ace930d22fb0da5f92cf0870f27e75be0))
* **build:** debug POM_VERSION ([d1d8d43](https://github.com/Paladins-Inn/rollenspiel-cons/commit/d1d8d4383ecd93304e67633b3c93e7452850dcb5))
* **build:** debug POM_VERSION ([bbf65f6](https://github.com/Paladins-Inn/rollenspiel-cons/commit/bbf65f6136b47481c533639a8848d689ea1f4a75))
* **build:** change pom.xml version ([6c4422c](https://github.com/Paladins-Inn/rollenspiel-cons/commit/6c4422c23cd157aecc0bbc1bb61307855639a152))
* **build:** change pom.xml injected version to have no v in front of it. ([b340e17](https://github.com/Paladins-Inn/rollenspiel-cons/commit/b340e1710234b7579e93836589adbc2af100bdca))
* **Documentation:** Some naming problems fixed. ([4e59feb](https://github.com/Paladins-Inn/rollenspiel-cons/commit/4e59feb528b3a8696db96d712ceaeeacb30364cb))


### Features

* **build:** Add CI/CD ([8a8d94b](https://github.com/Paladins-Inn/rollenspiel-cons/commit/8a8d94b580c4306b6b2320e8620101ef29e641e3))
* **docs:** Added crude templates for documentation. ([0cfec10](https://github.com/Paladins-Inn/rollenspiel-cons/commit/0cfec106539a407d870400546904a7c623697cc9))
* **Locations:** Add What3Words ([797cb44](https://github.com/Paladins-Inn/rollenspiel-cons/commit/797cb44ddca7fe9698986809d364af4b2d0b5f3a))
