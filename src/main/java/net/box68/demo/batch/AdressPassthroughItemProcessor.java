package net.box68.demo.batch;

import net.box68.demo.batch.data.Address;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author Matthias Jell
 *
 */
public final class AdressPassthroughItemProcessor implements ItemProcessor<Address, Address> {

    @Override
    public Address process(final Address item) throws Exception {

        return item;
    }
}