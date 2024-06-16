# PURPOSE:

This documents encapsulates various processes and commands used to maintain and publish the application.

## Build and Publish

On a Nix based computer you can run the following command from the `root` directory:

``` bash
./gradlew clean ; ./gradlew wasmJsBrowserDistribution ; cp -a composeApp/build/dist/wasmJs/productionExecutable/. docs/
```
This command builds the production Web Assembly code and then copies the output to the `docs/` directory.
This is because (at the time of writing this document) the project is published using Github Pages.
Pages will gather it's required data from the `docs/` folder.