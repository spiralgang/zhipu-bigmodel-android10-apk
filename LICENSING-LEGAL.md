## NOW THAT IVE GOT YOUR ATTENTION PROPERLY, THIS IS THE LICENSING-LEGAL.md; PERFECTLY ORDINARY COMPLIANCE.--NOW YOU NEED TO WORK ON THE FOLLOWING; 

##TWO-DO
#TWO YAMLS TO BUILD AND RUN 

- name: Build and Release APK
  uses: sangatdesai/release-apk@V1.0

##ipypy && gradle yamls 

"

setup-python

   

This action provides the following functionality for GitHub Actions users:

Installing a version of Python or PyPy and (by default) adding it to the PATHOptionally caching dependencies for pip, pipenv and poetry

Registering problem matchers for error output

Breaking changes in V6

Upgraded action from node20 to node24

Make sure your runner is on version v2.327.1 or later to ensure compatibility with this release. See Release Notes

For more details, see the full release notes on the releases page

Basic usage

See action.yml

Python

steps: - uses: actions/checkout@v5 - uses: actions/setup-python@v6 with: python-version: '3.13' - run: python my_script.py

PyPy

steps: - uses: actions/checkout@v5 - uses: actions/setup-python@v6 with: python-version: 'pypy3.10' - run: python my_script.py

GraalPy

steps: - uses: actions/checkout@v5 - uses: actions/setup-python@v6 with: python-version: 'graalpy-24.0' - run: python my_script.py

Free threaded Python

steps: - uses: actions/checkout@v5 - uses: actions/setup-python@v6 with: python-version: '3.13t' - run: python my_script.py

The python-version input is optional. If not supplied, the action will try to resolve the version from the default .python-version file. If the .python-version file doesn't exist Python or PyPy version from the PATH will be used. The default version of Python or PyPy in PATH varies between runners and can be changed unexpectedly so we recommend always setting Python version explicitly using the python-version or python-version-file inputs.

The action will first check the local tool cache for a semver match. If unable to find a specific version in the tool cache, the action will attempt to download a version of Python from GitHub Releases and for PyPy from the official PyPy's dist.

For information regarding locally cached versions of Python or PyPy on GitHub hosted runners, check out GitHub Actions Runner Images.

Supported version syntax

The python-version input supports the Semantic Versioning Specification and some special version notations (e.g. semver ranges, x.y-dev syntax, etc.), for detailed examples please refer to the section: Using python-version input of the Advanced usage guide.

Supported architectures

Using the architecture input, it is possible to specify the required Python or PyPy interpreter architecture: x86, x64, or arm64. If the input is not specified, the architecture defaults to the host OS architecture.

Caching packages dependencies

The action has built-in functionality for caching and restoring dependencies. It uses toolkit/cache under the hood for caching dependencies but requires less configuration settings. Supported package managers are pip, pipenv and poetry. The cache input is optional, and caching is turned off by default.

The action defaults to searching for a dependency file (requirements.txt or pyproject.toml for pip, Pipfile.lock for pipenv or poetry.lock for poetry) in the repository, and uses its hash as a part of the cache key. Input cache-dependency-path is used for cases when multiple dependency files are used, they are located in different subdirectories or different files for the hash that want to be used.

For pip, the action will cache the global cache directoryFor pipenv, the action will cache virtualenv directoryFor poetry, the action will cache virtualenv directories -- one for each poetry project found

Caching pip dependencies:

steps: - uses: actions/checkout@v5 - uses: actions/setup-python@v6 with: python-version: '3.13' cache: 'pip' # caching pip dependencies - run: pip install -r requirements.txt

Note: Restored cache will not be used if the requirements.txt file is not updated for a long time and a newer version of the dependency is available which can lead to an increase in total build time.

The requirements file format allows for specifying dependency versions using logical operators (for example chardet>=3.0.4) or specifying dependencies without any versions. In this case the pip install -r requirements.txt command will always try to install the latest available package version. To be sure that the cache will be used, please stick to a specific dependency version and update it manually if necessary.

The setup-python action does not handle authentication for pip when installing packages from private repositories. For help, refer pip’s VCS support documentation or visit the pip repository.

See examples of using cache and cache-dependency-path for pipenv and poetry in the section: Caching packages of the Advanced usage guide.

Advanced usage

Using the python-version inputUsing the python-version-file inputCheck latest versionCaching packagesOutputs and environment variablesAvailable versions of Python, PyPy and GraalPyHosted tool cacheUsing setup-python with a self-hosted runnerUsing setup-python on GHESAllow pre-releasesUsing the pip-version inputUsing the pip-install input

Recommended permissions

When using the setup-python action in your GitHub Actions workflow, it is recommended to set the following permissions to ensure proper functionality:

permissions: contents: read # access to check out code and install dependencies

License

The scripts and documentation in this project are released under the MIT License.

Contributions

Contributions are welcome! See our Contributor's Guide. "



##&& 




DSL Reference

MavenPublication

Table of Contents

PropertiesMethodsScript blocksProperty detailsMethod details

API Documentation:MavenPublication

A MavenPublication is the representation/configuration of how Gradle should publish something in Maven format. You directly add a named Maven publication the project's publishing.publications container by providing MavenPublication as the type.

publishing { publications { myPublicationName(MavenPublication) { // Configure the publication here } } } 

The default Maven POM identifying attributes are mapped as follows:

groupId - project.groupartifactId - project.nameversion - project.version

For certain common use cases, it's often sufficient to specify the component to publish, and nothing more (MavenPublication.from(org.gradle.api.component.SoftwareComponent). The published component is used to determine which artifacts to publish, and which dependencies should be listed in the generated POM file.

To add additional artifacts to the set published, use the MavenPublication.artifact(java.lang.Object) and MavenPublication.artifact(java.lang.Object, org.gradle.api.Action) methods. You can also completely replace the set of published artifacts using MavenPublication.setArtifacts(java.lang.Iterable). Together, these methods give you full control over what artifacts will be published.

To customize the metadata published in the generated POM, set properties, e.g. MavenPom.getDescription(), on the POM returned via the MavenPublication.getPom() method or directly by an action (or closure) passed into MavenPublication.pom(org.gradle.api.Action). As a last resort, it is possible to modify the generated POM using the MavenPom.withXml(org.gradle.api.Action) method.

// Example of publishing a Java module with a source artifact and a customized POM plugins { id 'java' id 'maven-publish' } task sourceJar(type: Jar) { from sourceSets.main.allJava archiveClassifier = "sources" } publishing { publications { myPublication(MavenPublication) { from components.java artifact sourceJar pom { name = "Demo" description = "A demonstration of Maven POM customization" url = "http://www.example.com/project" licenses { license { name = "The Apache License, Version 2.0" url = "http://www.apache.org/licenses/LICENSE-2.0.txt" } } developers { developer { id = "johnd" name = "John Doe" email = "john.doe@example.com" } } scm { connection = "scm:svn:http://subversion.example.com/svn/project/trunk/" developerConnection = "scm:svn:https://subversion.example.com/svn/project/trunk/" url = "http://subversion.example.com/svn/project/trunk/" } } } } } 

Properties

PropertyDescriptionartifactId

The artifactId for this publication.

artifacts

The complete set of artifacts for this publication.

groupId

The groupId for this publication.

pom

The POM that will be published.

version

The version for this publication.

Methods

MethodDescriptionartifact(source)

Creates a custom MavenArtifact to be included in the publication. The artifact method can take a variety of input:

artifact(source, config)

Creates an MavenArtifact to be included in the publication, which is configured by the associated action. The first parameter is used to create a custom artifact and add it to the publication, as per MavenPublication.artifact(java.lang.Object). The created MavenArtifact is then configured using the supplied action, which can override the extension or classifier of the artifact. This method also accepts the configure action as a closure argument, by type coercion.

from(component)

Provides the software component that should be published.

pom(configure)

Configures the POM that will be published. The supplied action will be executed against the MavenPublication.getPom() result. This method also accepts a closure argument, by type coercion.

setArtifacts(sources)

Clears any previously added artifacts from MavenPublication.getArtifacts() and creates artifacts from the specified sources. Each supplied source is interpreted as per MavenPublication.artifact(java.lang.Object). For example, to exclude the dependencies declared by a component and instead use a custom set of artifacts:

suppressAllPomMetadataWarnings()

Silences all the compatibility warnings for the Maven publication. Warnings are emitted when Gradle features are used that cannot be mapped completely to Maven POM.

suppressPomMetadataWarningsFor(variantName)

Silences the compatibility warnings for the Maven publication for the specified variant. Warnings are emitted when Gradle features are used that cannot be mapped completely to Maven POM.

versionMapping(configureAction)

Configures the version mapping strategy. For example, to use resolved versions for runtime dependencies:

Script blocks

No script blocks

Property details

String artifactId

The artifactId for this publication.

MavenArtifactSet artifacts

The complete set of artifacts for this publication.

String groupId

The groupId for this publication.

MavenPom pom (read-only)

The POM that will be published.

String version

The version for this publication.

Method details

MavenArtifact artifact(Object source)

Creates a custom MavenArtifact to be included in the publication. The artifact method can take a variety of input:

A PublishArtifact instance. Extension and classifier values are taken from the wrapped instance.An AbstractArchiveTask instance. Extension and classifier values are taken from the wrapped instance.Anything that can be resolved to a File via the Project.file(java.lang.Object) method. Extension and classifier values are interpolated from the file name.A Map that contains a 'source' entry that can be resolved as any of the other input types, including file. This map can contain a 'classifier' and an 'extension' entry to further configure the constructed artifact.

The following example demonstrates the addition of various custom artifacts.

plugins { id 'maven-publish' } task sourceJar(type: Jar) { archiveClassifier = "sources" } publishing { publications { maven(MavenPublication) { artifact sourceJar // Publish the output of the sourceJar task artifact 'my-file-name.jar' // Publish a file created outside of the build artifact source: sourceJar, classifier: 'src', extension: 'zip' } } } 

MavenArtifact artifact(Object source, Action<? super MavenArtifact> config)

Creates an MavenArtifact to be included in the publication, which is configured by the associated action. The first parameter is used to create a custom artifact and add it to the publication, as per MavenPublication.artifact(java.lang.Object). The created MavenArtifact is then configured using the supplied action, which can override the extension or classifier of the artifact. This method also accepts the configure action as a closure argument, by type coercion.

plugins { id 'maven-publish' } task sourceJar(type: Jar) { archiveClassifier = "sources" } publishing { publications { maven(MavenPublication) { artifact(sourceJar) { // These values will be used instead of the values from the task. The task values will not be updated. classifier = "src" extension = "zip" } artifact("my-docs-file.htm") { classifier = "documentation" extension = "html" } } } } 

void from(SoftwareComponent component)

Provides the software component that should be published.

Any artifacts declared by the component will be included in the publication.The dependencies declared by the component will be included in the published meta-data.

Currently 3 types of component are supported: 'components.java' (added by the JavaPlugin), 'components.web' (added by the WarPlugin) and `components.javaPlatform` (added by the JavaPlatformPlugin). For any individual MavenPublication, only a single component can be provided in this way. The following example demonstrates how to publish the 'java' component to a Maven repository.

plugins { id 'java' id 'maven-publish' } publishing { publications { maven(MavenPublication) { from components.java } } } 

void pom(Action<? super MavenPom> configure)

Configures the POM that will be published. The supplied action will be executed against the MavenPublication.getPom() result. This method also accepts a closure argument, by type coercion.

void setArtifacts(Iterable<?> sources)

Clears any previously added artifacts from MavenPublication.getArtifacts() and creates artifacts from the specified sources. Each supplied source is interpreted as per MavenPublication.artifact(java.lang.Object). For example, to exclude the dependencies declared by a component and instead use a custom set of artifacts:

plugins { id 'java' id 'maven-publish' } task sourceJar(type: Jar) { archiveClassifier = "sources" } publishing { publications { maven(MavenPublication) { from components.java artifacts = ["my-custom-jar.jar", sourceJar] } } } 

void suppressAllPomMetadataWarnings()

Silences all the compatibility warnings for the Maven publication. Warnings are emitted when Gradle features are used that cannot be mapped completely to Maven POM.

void suppressPomMetadataWarningsFor(String variantName)

Silences the compatibility warnings for the Maven publication for the specified variant. Warnings are emitted when Gradle features are used that cannot be mapped completely to Maven POM.

void versionMapping(Action<? super VersionMappingStrategy> configureAction)

Configures the version mapping strategy. For example, to use resolved versions for runtime dependencies:

plugins { id 'java' id 'maven-publish' } publishing { publications { maven(MavenPublication) { from components.java versionMapping { usage('java-runtime'){ fromResolutionResult() } } } } } 

DocsUser ManualDSL ReferenceRelease NotesJavadoc

NewsBlogNewsletterTwitter

ProductsBuild Scan®Build CacheDevelocity Docs

Get HelpForumsGitHubTrainingServices

Stay UP-TO-DATE on new features and news:

By entering your email, you agree to our Terms and Privacy Policy.

© Gradle Inc. 2021 All rights reserved.
Build and Release APKActions

Build & Release APK via Github Actions

Description

🕷 Build and release APK via Github Actions when you push a new tag to your repository and it will automatically be attached to the same release.

Usage

To use this action simply create the YML file at this specified path at a root level. for example: .github/workflows/android.ymlCopy paste the below YML in your YML fileProvide the required Secrets (check below) and Environment variables (check below)After doing the above, whenever you are ready, make a commit from your Android Project to Github RepoCreate and push the tagAs soon as you push the tag this github action will be initiated and generated apk build will be released under releases with the same tag, which you can check in your github - code - releases

YML

name: Build & Publish Release APK on: push: tags: - '*' jobs: Gradle: runs-on: ubuntu-latest steps: - name: checkout code uses: actions/checkout@v2 - name: setup jdk uses: actions/setup-java@v1 with: java-version: 11 - name: Make Gradle executable run: chmod +x ./gradlew - name: Build Release APK run: ./gradlew assembleRelease - name: Releasing using Hub uses: sangatdesai/release-apk@main env: GITHUB_TOKEN: ${{ secrets.TOKEN }} APP_FOLDER: app

Secrets

You'll need to provide this secret token to use the action, to publish the APK to your own repo and to attach it to the created tag. I am not sure as to why using the default GITHUB_TOKEN provided universally will fail to authorize the user. This is the workaround.

TOKEN: Create a new access token with repo accessEnter these secrets in your Android Project's Github repository's Settings > Secrets

Environment Variables

You'll need to provide these environment variables to specify exactly what information is needed to build the APK.

APP_FOLDER: main folder to search for the apk. Most of the time, it's app

How to push a tag?

Git

Git add . Git commit -m "new release" git push git tag 1.0 git push origin 1.0 

Flavors

By default this will create the 'release' flavor and If you want to change the flavor of the apk being built to let's say debug then change the command in your YAML

YML

...

- name: Build Debug APK run: ./gradlew assembleDebug 

...

Credits

Based on ShaunLWM & kyze8439690

About

Build Android Debug or Release APK when you create a new Tag

V1.0

Latest

By sangatdesai

Tags2 (2)

deploymentpublishing

Contributors1 (1)

+ 1 contributors

Resources

Build and Release APK is not certified by GitHub. It is provided by a third-party and is governed by separate terms of service, privacy policy, and support documentation.

Footer


https://plugins.gradle.org/m2/
