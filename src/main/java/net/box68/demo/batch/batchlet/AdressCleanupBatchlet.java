package net.box68.demo.batch.batchlet;

import javax.batch.api.Batchlet;

/**
 * @author Matthias Jell
 *
 */
public class AdressCleanupBatchlet implements Batchlet {

    public static final String EXIT_SUCCESS = "SUCCESS";

    @Override
    public String process() throws Exception {

        System.out.println("Do some stuff");

        return EXIT_SUCCESS;
    }

    @Override
    public void stop() throws Exception {

    }
}
