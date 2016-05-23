# Release process

## .m2/settings.xml

First create .m2/setting.xml file using the template in .m2. This should contain your GPG key, passpharse and Sonatype credentials.

## Update version

Make sure the snapshot version in pom.xml is correct (using semantic versioning guidelines). Do not remove -SNAPSHOT from the version string.

## Run tests

Currently the release process does not run tests due to their dependencies, so make sure you run them manually first.

## Run release.sh

Next run release.sh. This will do following:

1. Remove -SNAPSHOT & commit it
2. Compile code
3. Package it according to OSSRH guidelines (signing, javadoc etc.)
4. Upload it to staging reposistory
5. Close it

## Release in OSSRH

1. Go to [OSSRH Nexus](https://oss.sonatype.org/#stagingRepositories) & find your repository
2. Confirm contents, version & activity look correct. If they do not, drop the repository, fix the issues and retry.
3. Select it and click "Promote"
