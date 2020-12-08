# Getting Started

### How to run project

There are 3 options:

- Run the script `pullAndRun.sh` to pull the image from docker repository and run the project
- Run the script `buildAndRun.sh` to build the image and run the project
- Build the project with gradle `./gradlew clean build`, navigate to `build/libs/pokemon-0.0.1-SNAPSHOT.jar` and
  run `java -jar pokemon-0.0.1-SNAPSHOT.jar`

### How to push the project to docker repository

- Run the script `buildAndPush.sh`

### How to see the test coverage report

- Build the project with gradle `./gradlew clean build`, run the following index file in
  browser `build/jacocoHtml/index.html`

### What was done?

- 2 connectors for external API calls (it's service as well but with clear idea to call remote API, its request and
  response are identical to the external API documentation)
- 2 services (will use connectors and remap dtos returned by them to models that go through the application layers,
  models will have only necessary information for the application)
- one controller GET /pokemon/{name}, (the controller invokes services)
- simple error handling
- unit test with jUnit5 e Mockito for all public methods
- feature based package structure (only one for now)
- run & build shell scripts
- simple docker file

### Further improvements

- add code to errore response
- add parsing of errors from external API calls and extract code and message
- add custom exception to communicate to frontend functional errors(like exceeding call limits, etc)
- add logging implemented with AspectJ, to log automatically every request and response for all layers.
- extract code from the controller and put it in a facade.
- controller will be responsible only for converting dto to model(using factory), executing facade code and assembling
  the response.
- add more distinct components: controller, facade, request/response factories, services
- implements more generic approach for connectors
- replace application properties with .yml format
- add application-prod.yaml for production
- add docker compose
- environment files to store passwords(for unlimited access to external API)

## P.S.

- Added 2 integration tests that actually call external services, used during development. Marked as @Disabled to avoid
  exceeding the call limit.
