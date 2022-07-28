package com.github.fernandotaa.fileappenderbatch.config;

import com.github.fernandotaa.fileappenderbatch.appender.CounterReader;
import com.github.fernandotaa.fileappenderbatch.appender.FileWriter;
import com.github.fernandotaa.fileappenderbatch.appender.HostConcaterProcessor;
import com.github.fernandotaa.fileappenderbatch.validator.ErrorCheckingPrinterListener;
import com.github.fernandotaa.fileappenderbatch.validator.FileReader;
import com.github.fernandotaa.fileappenderbatch.validator.HostFilterAndRemoverProcessor;
import com.github.fernandotaa.fileappenderbatch.validator.ValidatorCheckerWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BatchConfiguration {

    @Bean("appenderStep")
    public Step step(
        StepBuilderFactory stepBuilderFactory,
        CounterReader counterReader,
        HostConcaterProcessor hostConcaterProcessor,
        FileWriter fileWriter
                    ) {
        return stepBuilderFactory.get("appender_step")
            .<Long, String>chunk(3)
            .reader(counterReader)
            .processor(hostConcaterProcessor)
            .writer(fileWriter)
            .faultTolerant()
            .build();
    }

    @Bean("checkerStep")
    public Step checkerStep(
        StepBuilderFactory stepBuilderFactory,
        FileReader fileReader,
        HostFilterAndRemoverProcessor hostFilterAndRemoverProcessor,
        ValidatorCheckerWriter validatorCheckerWriter,
        ErrorCheckingPrinterListener errorCheckingPrinterListener
                           ) {
        final SimpleStepBuilder simpleStepBuilder = stepBuilderFactory.get("checkerStep")
            .<String, Long>chunk(3)
            .reader(fileReader)
            .processor(hostFilterAndRemoverProcessor)
            .writer(validatorCheckerWriter);

        simpleStepBuilder.listener(errorCheckingPrinterListener);

        return simpleStepBuilder.build();
    }

    @Bean
    public Job job(
        JobBuilderFactory jobBuilderFactory,
        @Qualifier("appenderStep") Step appenderStep,
        @Qualifier("checkerStep") Step checkerStep
                  ) {
        return jobBuilderFactory.get("main_job")
            .start(appenderStep)
            .next(checkerStep)
//            .start(checkerStep)
            .build();
    }

}