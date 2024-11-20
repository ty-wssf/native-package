package features;

import com.bcht.rminf.App;

import org.junit.jupiter.api.Test;

import org.noear.solon.test.HttpTester;
import org.noear.solon.test.SolonTest;

import java.io.IOException;

@SolonTest(App.class)
public class HelloTest extends HttpTester {
    @Test
    public void hello() throws IOException {
        assert path("/hello?name=world1").get().contains("world1");
        assert path("/hello?name=solon").get().contains("solon");
    }
}