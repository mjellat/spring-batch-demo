package net.box68.demo.batch;

import net.box68.demo.batch.batchlet.AdressCleanupBatchlet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.jsr.step.batchlet.BatchletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Matthias Jell
 *
 */
@Configuration
public class BatchletConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    protected Job job2() {
        return jobBuilderFactory.get("batchletJob").start(step2(tasklet())).build();
    }

    @Bean
    protected Step step2(final Tasklet tasklet) {
        return stepBuilderFactory.get("step2")
                .tasklet(tasklet)
                .build();
    }

    @Bean Tasklet tasklet() {
        return new BatchletAdapter(new AdressCleanupBatchlet());
    }
}
