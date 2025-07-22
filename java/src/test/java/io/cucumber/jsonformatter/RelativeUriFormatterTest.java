package io.cucumber.jsonformatter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class RelativeUriFormatterTest {
    final URI cwd = new File("").toURI();
    final RelativeUriFormatter formater = new RelativeUriFormatter(cwd);

    @Test
    void classpath(){
        URI uri = URI.create("classpath:path/to/example.feature");
        URI formatted = formater.apply(uri);
        assertThat(formatted).isEqualTo(uri);
    }
    
    @Test
    void relative(){
        URI uri = URI.create("path/to/example.feature");
        URI formatted = formater.apply(uri);
        assertThat(formatted).isEqualTo(uri);
    }
    
    @Test
    void childOfCwd(){
        URI uri = new File("path/to/example.feature").toURI();
        URI formatted = formater.apply(uri);
        URI expected = URI.create("file:path/to/example.feature");
        assertThat(formatted).isEqualTo(expected);
    }    

    
    @Test
    void siblingOfCwd(){
        URI uri = new File("../path/to/example.feature").toURI();
        URI formatted = formater.apply(uri);
        URI expected = new File("../path/to/example.feature").toURI();
        assertThat(formatted).isEqualTo(expected);
    }
    
}
