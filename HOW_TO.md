# PURPOSE:

This documents encapsulates various processes and commands used to maintain and publish the application.


## Build and Publish

On a Nix based computer you can run the following command from the 'root' directory:

``` bash
./gradlew wasmJsBrowserDistribution ; cp -a composeApp/build/dist/wasmJs/productionExecutable/. docs/
```