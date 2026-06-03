package majorFunctionality;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariStartWithProxy {

	public static void main(String[] args) {
		
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start(0);

		// Modify User-Agent
		proxy.addRequestFilter((request, contents, messageInfo) -> {
		    request.headers().remove("User-Agent");
		    request.headers().add("User-Agent",
		        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.6045.123 Safari/537.36");
		    return null;
		});

		// Selenium Proxy setup
		Proxy seleniumProxy = new Proxy();
		String proxyStr = "localhost:" + proxy.getPort();

		seleniumProxy.setHttpProxy(proxyStr);
		seleniumProxy.setSslProxy(proxyStr);

		// Attach to Safari
		SafariOptions options = new SafariOptions();
		options.setCapability("proxy", seleniumProxy);

		WebDriver driver = new SafariDriver(options);

		driver.get("https://www.yes.bank.in/");
		driver.manage().window().maximize();
	}

}
