package org.janino.service;

import org.codehaus.janino.ExpressionEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DynamicDiscountService {

    @Value("${rule.file.path}")
    private String ruleFilePath;

    private final ResourceLoader resourceLoader;

    private ExpressionEvaluator evaluator;
    private String lastRule = "";

    public DynamicDiscountService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Load rule at startup
     */
    @PostConstruct
    public void init() throws Exception {
        loadRule();
    }

    /**
     * Check for rule updates every few seconds
     */
    @Scheduled(fixedDelayString = "${rule.refresh.interval}")
    public void refreshRule() throws Exception {
        Resource ruleResource = resourceLoader.getResource("classpath:" + ruleFilePath);

        String rule = new String(Files.readAllBytes(Paths.get(ruleResource.getURI())));

        if (!rule.equals(lastRule)) {
            System.out.println("🔄 Rule updated: " + rule);
            loadRule();
        }
    }

    /**
     * Load (compile) rule file into Janino ExpressionEvaluator
     */
    private void loadRule() throws Exception {
        Resource ruleResource = resourceLoader.getResource("classpath:" + ruleFilePath);

        String rule = new String(Files.readAllBytes(Paths.get(ruleResource.getURI())));

        ExpressionEvaluator exp = new ExpressionEvaluator();
        exp.setParameters(new String[]{"amount"}, new Class[]{double.class});
        exp.setExpressionType(double.class);
        exp.cook(rule);

        this.evaluator = exp;
        this.lastRule = rule;

        System.out.println("✔ Rule loaded: " + ruleFilePath);
        System.out.println("✔ Rule content: " + rule);
    }

    /**
     * Execute dynamic rule
     */
    public double calculate(double amount) throws Exception {
        return (Double) evaluator.evaluate(new Object[]{amount});
    }
}
