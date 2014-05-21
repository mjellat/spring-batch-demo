package batch;

import org.springframework.core.style.ToStringCreator;


/**
 * @author Matthias Jell
 *
 */
public class PersonInput {

    private String name1;
    private String name2;
    private String name3;

    /**
     * @return the name1
     */
    public String getName1() {

        return name1;
    }

    /**
     * @param name1 the name1 to set
     */
    public void setName1(final String name1) {

        this.name1 = name1;
    }

    /**
     * @return the name2
     */
    public String getName2() {

        return name2;
    }

    /**
     * @param name2 the name2 to set
     */
    public void setName2(final String name2) {

        this.name2 = name2;
    }

    /**
     * @return the name3
     */
    public String getName3() {

        return name3;
    }

    /**
     * @param name3 the name3 to set
     */
    public void setName3(final String name3) {

        this.name3 = name3;
    }

    @Override
    public String toString() {

        return new ToStringCreator(this)
            .append("name1", name1)
            .append("name2", name2)
            .append("name3", name3)
            .toString();
    }

}
