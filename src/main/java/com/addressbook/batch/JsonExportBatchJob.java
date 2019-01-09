package com.addressbook.batch;

import com.addressbook.contact.ContactEntity;
import com.addressbook.contact.ContactRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class JsonExportBatchJob {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private ContactRepository contactRepository;
    private Resource outputResource = new FileSystemResource("target/output/contacts_export.json");

    @Autowired
    public JsonExportBatchJob(ContactRepository contactRepository, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.contactRepository = contactRepository;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job JsonExportJob() {
        return jobBuilderFactory.get("jsonExportJob")
                .incrementer(new RunIdIncrementer())
                .flow(jsonJobExportStep())
                .end()
                .build();
    }

    @Bean
    Step jsonJobExportStep() {
        return stepBuilderFactory.get("jsonExportStep")
                .<ContactEntity, ContactEntity>chunk(1)
                .reader(contactReader())
                .writer(contactWriter())
                .build();
    }

    @StepScope
    private ItemWriter<? super ContactEntity> contactWriter() {
        return new JsonFileItemWriterBuilder<ContactEntity>()
                .name("contactsJsonFileItemWriter")
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputResource)
                .build();
    }

    @StepScope
    private ItemReader<? extends ContactEntity> contactReader() {
        RepositoryItemReader<ContactEntity> reader = new RepositoryItemReader<>();
        reader.setName("contactsRepositoryReader");
        reader.setRepository(contactRepository);
        reader.setMethodName("findAll");
        return reader;
    }

}
