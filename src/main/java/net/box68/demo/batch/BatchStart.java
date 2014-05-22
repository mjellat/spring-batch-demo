package net.box68.demo.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Matthias Jell
 *
 */
@ComponentScan
@EnableAutoConfiguration
public class BatchStart {

    public static void main(final String[] args) {

        SpringApplication.run(BatchStart.class, args);
    }
}
