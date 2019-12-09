package test;/**
 * Created by V on 2019/12/8.
 */

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * V
 * 2019/12/8 11:23
 */
public class YamlConfigTest {
    public static void main(String[] args) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map map = yaml.load(new FileInputStream(new File("D:\\Idea_Projects\\vrpc\\src\\main\\resources\\client2.yaml")));
        System.out.println(map);

    }
}
