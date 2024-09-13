package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/booking_room_unsuccessfully.feature",
        glue = {"steps.booking",
                "steps.hooks"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)

public class BookingRoomUnsuccessfullyRunner {
}
