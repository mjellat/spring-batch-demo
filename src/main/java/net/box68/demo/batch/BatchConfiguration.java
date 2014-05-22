package net.box68.demo.batch;

import net.box68.demo.batch.data.Address;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Matthias Jell
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    protected Job job() {

        return jobBuilderFactory.get("importJob").start(step1(reader(), processor(), writer())).build();
    }

    @Bean
    protected Step step1(final ItemReader<Address> reader,
            final ItemProcessor<Address, Address> processor,
            final ItemWriter<Address> writer) {

        return stepBuilderFactory.get("step1")
                .<Address, Address> chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    protected ItemReader<Address> reader() {

        DefaultLineMapper<Address> lm = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(new String[] { "street", "city", "zip" });
        lm.setLineTokenizer(tokenizer);
        BeanWrapperFieldSetMapper<Address> fsm = new BeanWrapperFieldSetMapper<>();
        fsm.setTargetType(Address.class);
        lm.setFieldSetMapper(fsm);
        FlatFileItemReader<Address> ffreader = new FlatFileItemReader<>();
        ffreader.setEncoding("UTF-8");
        ffreader.setLineMapper(lm);
        ffreader.setResource(new ClassPathResource("test.csv"));
        return ffreader;
    }

    @Bean
    protected ItemWriter<Address> writer() {

        return new ConsoleItemWriter<Address>();
    }

    @Bean
    protected ItemProcessor<Address, Address> processor() {

        return new AdressPassthroughItemProcessor();
    }

}
