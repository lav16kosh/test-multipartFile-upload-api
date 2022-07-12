package uploadFileTests;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/uploadFileTests",
glue="uploadFileTests",
plugin = { "pretty" },
monochrome=true)


public class CucumberRunner {
}