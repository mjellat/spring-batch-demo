package net.box68.demo.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * @author Matthias Jell
 *
 */
public final class ConsoleItemWriter<T> implements ItemWriter<T> {

    @Override
    public void write(final List<? extends T> items) throws Exception {

        int i = 0;
        for (T item : items) {

            System.out.println(++i + ":" + item);
        }
    }
}