import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.my")
public class GitHubApplication {
	private static final Logger LOG = LoggerFactory.getLogger(GitHubApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GitHubApplication.class, args);
		LOG.info("GitHubApplication Start Success");
	}

}
