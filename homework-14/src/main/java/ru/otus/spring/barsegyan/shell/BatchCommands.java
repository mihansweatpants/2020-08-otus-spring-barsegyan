package ru.otus.spring.barsegyan.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job migrateBookDataJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "run-migration")
    public void startMigrationJobWithJobOperator() throws Exception {
        jobLauncher.run(migrateBookDataJob, new JobParameters());
    }
}

