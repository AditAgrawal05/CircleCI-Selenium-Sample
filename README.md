# Selenium TestNG LambdaTest CircleCI Sample

A sample Java + Selenium + TestNG project that runs cross-browser web automation
on [LambdaTest](https://www.lambdatest.com/) through a CircleCI pipeline.

## Project structure

```
.
├── pom.xml                                  # Maven build + dependencies
├── testng.xml                                # TestNG suite definition
├── .circleci/config.yml                      # CircleCI pipeline
└── src/test/java/com/lambdatest/
    ├── BaseTest.java                         # Sets up/tears down the LambdaTest RemoteWebDriver
    └── SimpleFormTest.java                   # Sample test against LambdaTest's Selenium Playground
```

## Prerequisites

- Java 11+
- Maven 3.6+
- A [LambdaTest account](https://accounts.lambdatest.com/) — grab your `Username` and
  `Access Key` from https://accounts.lambdatest.com/security

## Running locally

Set your LambdaTest credentials as environment variables, then run the suite:

```bash
export LT_USERNAME=your_lambdatest_username
export LT_ACCESS_KEY=your_lambdatest_access_key

mvn test
```

On Windows PowerShell:

```powershell
$env:LT_USERNAME="your_lambdatest_username"
$env:LT_ACCESS_KEY="your_lambdatest_access_key"

mvn test
```

Test results are written to `target/surefire-reports/`, and each run is visible on your
[LambdaTest Automation Dashboard](https://automation.lambdatest.com/).

## Running in CircleCI

The pipeline in `.circleci/config.yml` runs the TestNG suite via Maven on every pushed branch
(including branches behind a pull request).

Before pushing, add your LambdaTest credentials as **project environment variables** in
CircleCI (**Project Settings > Environment Variables**):

| Key             | Value                        |
|-----------------|-------------------------------|
| `LT_USERNAME`   | your LambdaTest username       |
| `LT_ACCESS_KEY` | your LambdaTest access key      |

Once the variables are set, any pipeline run will:
1. Check out the repo and restore the cached `.m2/repository` (keyed on `pom.xml`).
2. Run the TestNG suite defined in `testng.xml` against LambdaTest's Selenium Grid.
3. Publish JUnit-format test results and surefire report artifacts on the CircleCI job page.

### Setting up the pipeline on GitHub

1. Push this repository to GitHub.
2. In CircleCI, go to **Projects**, find the repo, and click **Set Up Project** (CircleCI
   picks up `.circleci/config.yml` automatically).
3. Add the `LT_USERNAME` and `LT_ACCESS_KEY` environment variables under
   **Project Settings > Environment Variables**.
4. Push a commit — the `lambdatest_selenium_testng` job will run automatically.

## Adding more tests

Add new TestNG test classes under `src/test/java/com/lambdatest/`, extend `BaseTest`
to reuse the LambdaTest `RemoteWebDriver` setup/teardown, and register the class in
`testng.xml`.
