package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

public class Usage {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
    private int total_cost;

    public Usage(){}
    public Usage(int prompt_tokens, int completion_tokens, int total_tokens, int total_cost){}

    public int getPrompt_tokens() {
        return prompt_tokens;
    }

    public int getCompletion_tokens() {
        return completion_tokens;
    }

    public int getTotal_tokens() {
        return total_tokens;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setPrompt_tokens(int prompt_tokens) {
        this.prompt_tokens = prompt_tokens;
    }

    public void setCompletion_tokens(int completion_tokens) {
        this.completion_tokens = completion_tokens;
    }

    public void setTotal_tokens(int total_tokens) {
        this.total_tokens = total_tokens;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public void printUsage(){
        System.out.println("Prompt Tokens: " + prompt_tokens);
        System.out.println("Completion Tokens: " + completion_tokens);
        System.out.println("Total Tokens: " + total_tokens);
        System.out.println("Total Cost: " + total_cost);
    }
}
