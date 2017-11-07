package org.gradle.android

class VersionCheckTest extends AbstractTest {

    def "fails when applied before Android plugin"() {
        def projectDir = temporaryFolder.newFolder()
        new SimpleAndroidApp(projectDir, cacheDir, "3.0.0", true).writeProject()
        expect:
        def result = withGradleVersion("4.3")
            .withProjectDir(projectDir)
            .withArguments("tasks")
            .buildAndFail()
        result.output.contains("The Android cache fix plugin must be applied after Android plugins.")
    }

    def "does not apply workarounds with Gradle 4.4"() {
        def projectDir = temporaryFolder.newFolder()
        new SimpleAndroidApp(projectDir, cacheDir, "3.0.0").writeProject()
        expect:
        def result = withGradleVersion("4.4-20171105235948+0000")
            .withProjectDir(projectDir)
            .withArguments("tasks")
            .build()
        result.output.contains("Gradle 4.4 is not supported by Android cache fix plugin, not applying workarounds.")
    }

    def "does not apply workarounds with Android 3.1.0-alpha01"() {
        def projectDir = temporaryFolder.newFolder()
        new SimpleAndroidApp(projectDir, cacheDir, "3.1.0-alpha01").writeProject()
        expect:
        def result = withGradleVersion("4.1")
            .withProjectDir(projectDir)
            .withArguments("tasks")
            .build()
        result.output.contains("Android plugin 3.1.0 is not supported by Android cache fix plugin, not applying workarounds.")
    }
}
