package com.workoutTracker;
import java.io.*;
import java.util.ArrayList;

public class StrengthRanks {

    public static final int BEGINNER = 0;
    public static final int NOVICE = 1;
    public static final int INTERMEDIATE = 2;
    public static final int ADVANCED = 3;
    public static final int ELITE = 4;

    private String exercise;
    private String sex;
    public static ArrayList<StrengthRanks> savedStrengthRanks = new ArrayList<>();
    ArrayList<StrengthLevel> levels = new ArrayList<>();
    public StrengthRanks(String exerciseName,String sex,File file) throws FileIncompatibleException, IOException {
        this.sex = sex;
        this.exercise = exerciseName;
        if(!file.getName().endsWith(".CSV")){
            throw new FileIncompatibleException();
        }
        FileReader read = new FileReader(file);
        BufferedReader reader = new BufferedReader(read);
        while(reader.ready()) {
            String s = reader.readLine();
            String[] line = s.split("\t");
            StrengthLevel level = new StrengthLevel(Integer.parseInt(line[0]),Integer.parseInt(line[1]),
                    Integer.parseInt(line[2]),Integer.parseInt(line[3]),Integer.parseInt(line[4]),
                    Integer.parseInt(line[5]));
            levels.add(level);
        }
    }

    public StrengthRanks(String exerciseName,String sex,String file){
        this.exercise = exerciseName;
        this.sex = sex;
        String[] weights = file.split(" ");
        for(String weightCat: weights){
            String[] lvl = weightCat.split("\t");
            StrengthLevel level = new StrengthLevel(Integer.parseInt(lvl[0]),Integer.parseInt(lvl[1]),
                    Integer.parseInt(lvl[2]),Integer.parseInt(lvl[3]),Integer.parseInt(lvl[4]),
                    Integer.parseInt(lvl[5]));
            levels.add(level);
        }
    }

    /**
     * return -1 if cant find rank
     * @param weight
     * @param lift
     * @return
     */
    public int getStrengthLevel(int weight,int lift){
        for(StrengthLevel level:levels){
            if(weight<=level.getBodyWeight()){
                if(lift<=level.getBeginnerW()){
                    return BEGINNER;
                }else if(lift<=level.getNoviceW()){
                    return NOVICE;
                }else if(lift<=level.getIntermediateW()){
                    return INTERMEDIATE;
                }else if(lift<=level.getAdvancedW()){
                    return ADVANCED;
                }else if(lift<=level.getEliteW()){
                    return ELITE;
                }
            }
        }
        return -1;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static class FileIncompatibleException extends Exception{

        public FileIncompatibleException(){
            super("File incompatible");
        }
    }

    private class StrengthLevel{
        private int bodyWeight;
        private int beginnerW;
        private int noviceW;
        private int intermediateW;
        private int advancedW;
        private int eliteW;
        public StrengthLevel(int bodyWeight, int beginnerW, int noviceW, int intermediateW,int advancedW,
                             int eliteW){
            this.bodyWeight=bodyWeight;
            this.beginnerW=beginnerW;
            this.noviceW=noviceW;
            this.intermediateW=intermediateW;
            this.advancedW=advancedW;
            this.eliteW=eliteW;





        }

        public int getBodyWeight() {
            return bodyWeight;
        }

        public void setBodyWeight(int bodyWeight) {
            this.bodyWeight = bodyWeight;
        }

        public int getBeginnerW() {
            return beginnerW;
        }

        public void setBeginnerW(int beginnerW) {
            this.beginnerW = beginnerW;
        }

        public int getNoviceW() {
            return noviceW;
        }

        public void setNoviceW(int noviceW) {
            this.noviceW = noviceW;
        }

        public int getIntermediateW() {
            return intermediateW;
        }

        public void setIntermediateW(int intermediateW) {
            this.intermediateW = intermediateW;
        }

        public int getAdvancedW() {
            return advancedW;
        }

        public void setAdvancedW(int advancedW) {
            this.advancedW = advancedW;
        }

        public int getEliteW() {
            return eliteW;
        }

        public void setEliteW(int eliteW) {
            this.eliteW = eliteW;
        }
    }
}
