package AppTests;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberSerenityRunner;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(features = "src/test/resources/features",glue = "Steps",plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"
})
public class TestRunner {

}
