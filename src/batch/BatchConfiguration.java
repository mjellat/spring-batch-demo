package batch;

import java.util.List;

import javax.sql.DataSource;

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
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

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
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
                .addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
                .addScript("classpath:schema-partner.sql")
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }

    @Bean
    protected Job job() {
        return jobBuilderFactory.get("importJob").start(step1(reader(),processor(), writer())).build();
    }

    @Bean
    protected Step step1(final ItemReader<PersonInput> reader,
            final ItemProcessor<PersonInput, PersonInput> processor,
            final ItemWriter<PersonInput> writer) {
        return stepBuilderFactory.get("step1")
                .<PersonInput, PersonInput> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    protected ItemReader<PersonInput> reader() {

        DefaultLineMapper<PersonInput> lm = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(new String[]{"name1", "name2", "name3"});
        lm.setLineTokenizer(tokenizer);
        BeanWrapperFieldSetMapper<PersonInput> fsm = new BeanWrapperFieldSetMapper<>();
        fsm.setTargetType(PersonInput.class);
        lm.setFieldSetMapper(fsm);
        FlatFileItemReader<PersonInput> ffreader = new FlatFileItemReader<>();
        ffreader.setEncoding("UTF-8");
        ffreader.setLineMapper(lm);
        ffreader.setResource(new FileSystemResource("C:\\Test.csv"));
        return ffreader;
    }


    @Bean
    protected ItemWriter<PersonInput> writer() {
        ItemWriter<PersonInput> w = new ItemWriter<PersonInput>() {

            @Override
            public void write(final List<? extends PersonInput> items) throws Exception {
                System.out.println(items);
            }
        };
        return w;
    }


    @Bean
    protected ItemProcessor<PersonInput,PersonInput> processor() {
        return new ItemProcessor<PersonInput, PersonInput>() {

            @Override
            public PersonInput process(final PersonInput item) throws Exception {

                return item;
            }
        };
    }

}
