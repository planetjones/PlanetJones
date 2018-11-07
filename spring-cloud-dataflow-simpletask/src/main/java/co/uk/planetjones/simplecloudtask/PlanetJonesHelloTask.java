package co.uk.planetjones.simplecloudtask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableTask
public class PlanetJonesHelloTask {

	public static void main(String[] args) {
		SpringApplication.run(PlanetJonesHelloTask.class, args);
	}

    @Bean
    public CountToTenTask countToTenTask() {
        return new CountToTenTask();
    }


    public class CountToTenTask implements CommandLineRunner {

        public void run(String... args) throws Exception {

            IntStream
                    .range(1, 11)
                    .forEach(System.out::println);
        }


    }


}
