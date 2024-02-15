# Contributing

We appreciate your contribution to the project, and we want to make the process as seamless as possible for you. 
Before starting, please ensure that you have forked the repository and read the [documentation](README.md) carefully.

### Getting Started

To get started, follow Step 1 and Step 2 in the README to set up the analytics token. 
Additionally, enable debugging by referring to the [debugging steps](https://github.com/buildkite/test-collector-android#-debugging).

Once you have completed the setup, you can start implementing your changes.

### Test Local Changes

Currently, our example project references the local `collector/instrumented-test-collector` library to enable immediate testing of changes related to the instrumented test collector. 
However, immediate testing of changes related to the unit-test collector is currently not possible by referencing the local unit-test-collector plugin - `collector/unit-test-collector`. Therefore, you need to publish the SDK locally to test the changes.

To publish the SDK locally, use the following command: ```./gradlew publishToMavenLocal```. You can check out the published SDK in your machine's .m2 folder.

To reference the locally published plugin and library, add the dependency for both with the correct current SDK version (with -SNAPSHOT suffix), as documented in Step 3 & Step 4 in the README.

Once you've tested your changes and are happy with the outcome, create a pull request. This is the formal process of submitting your changes to the project and getting them reviewed and merged by the maintainers.
**Ensure that you remove all references to the locally published repository that you added to the project before creating a pull request, since the CI build will fail as it won't be able to fetch the dependencies.**

### Testing Snapshots

After your pull request is merged, you can test the published version of the SDK with your changes immediately by accessing snapshots. Refer to [Access Snapshots](https://github.com/buildkite/test-collector-android#access-snapshots) for guidance.

### Bumping Version

When bumping the version, update the version in `gradle.properties` as well as `RunEnvironment.VERSION_NAME`.

This ensures the correct library version is uploaded alongside the test analytics.

Other approaches were considered when trying to solve the issue of uploading the library version.

The user could create a new environment variable for the version number. The collectors can then
access the version number through this and create `TestDataUploader` with this information. However,
the user would need to update this environment variable every time they update the library.

Another approach would be to use code generation to automatically generate some code upon publishing
this library. It could try to read the library's version and expose this to the library to use. Frankly,
this approach seems to be ideal but is very difficult to achieve.

Therefore we went with the simple but manual approach to update the version ourselves.