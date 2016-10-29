import static junit.framework.Assert.*;

/**
 * Created by Matt on 10/17/2016.
 */
public class EscapeTest
{
    @org.junit.Test
    public void initialize() throws Exception
    {
        Escape e = new Escape();
        e.initialize("data.txt");
    }

}