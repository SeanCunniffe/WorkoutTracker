package com.workoutTracker;
import java.io.*;
import java.util.ArrayList;

public class StrengthRanks {
    String exercise;
    ArrayList<StrengthLevel> levels = new ArrayList<>();
    public StrengthRanks(String exerciseName,File file) throws FileIncompatibleException, IOException {
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

    public String getStrengthLevel(int weight,int lift){
        for(StrengthLevel level:levels){
            if(weight<=level.getBodyWeight()){
                if(lift<=level.getBeginnerW()){
                    return "Beginner";
                }else if(lift<=level.getNoviceW()){
                    return "Novice";
                }else if(lift<=level.getIntermediateW()){
                    return "Intermediate";
                }else if(lift<=level.getAdvancedW()){
                    return "Advanced";
                }else if(lift<=level.getEliteW()){
                    return "Elite";
                }
            }
        }

        return "";
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
