package BrandAnalysis.model;

import java.util.Map;
import java.util.HashMap;

public class AIA {
    private Map<String, Integer> reliable = new HashMap<String, Integer>();
    private Map<String, Integer> trustworthy = new HashMap<String, Integer>();
    private Map<String, Integer> innovative = new HashMap<String, Integer>();
    private Map<String, Integer> customerCentric = new HashMap<String, Integer>();
    private Map<String, Integer> forwardLooking = new HashMap<String, Integer>();
    private Map<String, Integer> sustainable = new HashMap<String, Integer>();
    private Map<String, Integer> protective = new HashMap<String, Integer>();
    private Map<String, Integer> supportive = new HashMap<String, Integer>();
    private Map<String, Integer> healthFocused = new HashMap<String, Integer>();
    private Map<String, Integer> longTerm = new HashMap<String, Integer>();
    private Map<String, Integer> accessible = new HashMap<String, Integer>();
    private Map<String, Integer> responsive = new HashMap<String, Integer>();
    private Map<String, Integer> professional = new HashMap<String, Integer>();
    private Map<String, Integer> caring = new HashMap<String, Integer>();
    private Map<String, Integer> technologicallyAdvanced = new HashMap<String, Integer>();
    private Map<String, Integer> goalOriented = new HashMap<String, Integer>();
    private Map<String, Integer> financiallyStrong = new HashMap<String, Integer>();
    private Map<String, Integer> ethical = new HashMap<String, Integer>();
    private Map<String, Integer> empowering = new HashMap<String, Integer>();
    private Map<String, Integer> collaborative = new HashMap<String, Integer>();

    // Constructor
    public AIA() {
        reliable.put("Reliable", 0);
        trustworthy.put("Trustworthy", 0);
        innovative.put("Innovative", 0);
        customerCentric.put("Customer-centric", 0);
        forwardLooking.put("Forward-looking", 0);
        sustainable.put("Sustainable", 0);
        protective.put("Protective", 0);
        supportive.put("Supportive", 0);
        healthFocused.put("Health-focused", 0);
        longTerm.put("Long-term", 0);
        accessible.put("Accessible", 0);
        responsive.put("Responsive", 0);
        professional.put("Professional", 0);
        caring.put("Caring", 0);
        technologicallyAdvanced.put("Technologically advanced", 0);
        goalOriented.put("Goal-oriented", 0);
        financiallyStrong.put("Financially strong", 0);
        ethical.put("Ethical", 0);
        empowering.put("Empowering", 0);
        collaborative.put("Collaborative", 0);
    }
}
