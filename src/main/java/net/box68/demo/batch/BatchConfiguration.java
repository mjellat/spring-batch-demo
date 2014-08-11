package net.box68.demo.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author Matthias Jell
 * 
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("#{'${tokenizer}'}")
    private String[] addressTokenizerConfig;

    @Value("#{'${resources}'}")
    private String[] resources;

    @Bean
    protected Job job() throws IOException {

        return jobBuilderFactory.get("importJob")
                .start(step1(multiResourceReader(),
                        processor(),
                        writer()))
                        .build();
    }

    @Bean
    protected Step step1(final ItemReader<Address> reader,
            final ItemProcessor<Address, Address> processor,
            final ItemWriter<Address> writer) {

        return stepBuilderFactory.get("step1")
                .<Address, Address> chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "multiItemReader")
    protected ItemReader<Address> multiResourceReader() throws IOException {

        PathMatchingResourcePatternResolver patternResolver = new
                PathMatchingResourcePatternResolver();

        ArrayList<Resource> resourcesList = new ArrayList<>();
        for (String resourceAsString : this.resources) {
            Resource[] resource = patternResolver.getResources(resourceAsString);
            resourcesList.addAll(Arrays.asList(resource));
        }


        MultiResourceItemReader<Address> itemReader = new
                MultiResourceItemReader<>();
                itemReader.setDelegate(reader());
        itemReader.setResources(resourcesList.toArray(new Resource[resourcesList.size()]));
                return itemReader;
    }

    protected ResourceAwareItemReaderItemStream<Address> reader() {

        // define line mapper
        DefaultLineMapper<Address> lm = new DefaultLineMapper<>();
        // define tokenizer
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");

        tokenizer.setNames(this.addressTokenizerConfig);
        lm.setLineTokenizer(tokenizer);
        // define fieldset mapper
        BeanWrapperFieldSetMapper<Address> fsm = new BeanWrapperFieldSetMapper<>();
        fsm.setTargetType(Address.class);
        lm.setFieldSetMapper(fsm);
        // define flat file item reader
        FlatFileItemReader<Address> ffreader = new FlatFileItemReader<>();
        ffreader.setEncoding("UTF-8");
        ffreader.setLineMapper(lm);
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
