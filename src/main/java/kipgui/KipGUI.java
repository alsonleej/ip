package kipgui;

import kip.KipService;

public class KipGUI {
    private KipService kipService;
    
    public KipGUI() {
        this.kipService = new KipService();
    }
    
    public static void main(String[] args) {
        System.out.println("Hello! This is KipGUI. Use this class to integrate with JavaFX GUI.");
    }

    /**
     * Generates a response for the user's chat message using Kip's logic.
     */
    public String getResponse(String input) {
        return kipService.processCommand(input);
    }
    
    /**
     * Get the underlying KipService for direct access if needed.
     */
    public KipService getKipService() {
        return kipService;
    }
}
