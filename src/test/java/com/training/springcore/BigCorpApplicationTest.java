package com.training.springcore;

import com.training.springcore.utils.OutputCapture;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class BigCorpApplicationTest {

    @Rule
    public OutputCapture output = new OutputCapture();

    private BigCorpApplication application = new BigCorpApplication();

    @Test
    public void run() {
        application.run();
        assertThat(output.toString(),
                containsString("BigCorp application"));
    }
}