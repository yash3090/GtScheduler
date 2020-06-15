package com.controller;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
public class SortingAlgorithm {

    private String term = "202008"; // can be changed using setTerm method in this class
    // therefore year followed by term --> 08 = fall, 02 = spring, 05 = summer

    private String courseName;
    private String courseNum;
    private boolean isStudio = false;
    private ArrayList<ArrayList<Course>> iterator = new ArrayList<>();

    private String[] wantCrn;
    private String[] noCrn;
    private String[] courseList;
    private int startTime;
    private int endTime;
    private boolean timeConstraints = false;
    private String openCheck;

    /**
     * constructor
     * @param courseList list of courses we want timetable for
     * @param wantCrn list of CRN that are included in every acceptable possible timetable
     * @param noCrn list of CRN that are not going to be there in every acceptable possible timetable
     * @param startTime constraints for min start time
     * @param endTime constraints for max class end time
     */

    public SortingAlgorithm(String[] courseList, String[] wantCrn, String[] noCrn, String startTime, String endTime, String term, String openCheck) { // starttime and endtime in 2400 format
        this.courseList = courseList;
        this.wantCrn = wantCrn;
        this.noCrn = noCrn;
        this.openCheck = openCheck;
        
        if (!term.contentEquals("")) {
        	this.term = term;
        }
        if (startTime.equals("") && endTime.equals("")){
            timeConstraints = false;
        }  else if (!startTime.equals("") && endTime.equals("")) {
            this.startTime = Integer.parseInt(startTime);
            this.endTime = 2100;
            timeConstraints = true;
        } else if (startTime.equals("") && !endTime.equals("")){
            this.startTime = 800;
            this.endTime = Integer.parseInt(endTime);
            timeConstraints = true;
        }  else if (Integer.parseInt(startTime) < 0 || Integer.parseInt(endTime)> 2400) { // throw error that  time mistake
            System.out.println("Timings Not in Range");
            throw new RuntimeException();
        } else {
            this.startTime = Integer.parseInt(startTime);
            this.endTime = Integer.parseInt(endTime);
            timeConstraints = true;
        }


    }

    /**
     * Array of all the possible timetables
     */
    private  ArrayList<ArrayList<Course>> possibleTimeTable = new ArrayList<>();

    /**
     * getter method
     * @return
     */
    public ArrayList<ArrayList<Course>> getPossibleTimeTable() {
        return possibleTimeTable;
    }

    /**
     *
     * @return
     */

    public ArrayList<ArrayList<Course>> getIterator() {
        return iterator;
    }

    private ArrayList<String[]> profGPA = new ArrayList<>();

    /**
     * generates all possible timetables and stores it in possibleTimeTable arraylist
     */

    
    //has to be called to initialize the arraylist of courses
    public void sort() {
        for (String course : courseList) {
            if(course == ""){
                break;
            }
            courseName = course.substring(0, course.indexOf(" "));

            courseNum = course.substring(course.indexOf(" ") + 1);

            Oscar a = new Oscar(term, courseName, courseNum);
            try {
                a.sectionGenerator(); // scrapes oscar for the particular course generating Arraylist of lectures and studios
            } catch (Exception ec) {
                throw new RuntimeException();
                
            }

            ArrayList<Course> sectionsLecture = new ArrayList<>();
            for (Course b: a.getSectionsLecture()) {      
            	
            	if(openCheck.equals("both")) { // based on user input checks for availability of classes
            		b.checkGenerator(); // goes to oscar of the particular course to check availibility of classes
            		if(b.getSpotRemaining() > 0 || b. getWaitRemaining() > 0) {
            			sectionsLecture.add(b);
            		}
            	} else if (openCheck.contentEquals("one")){
            		b.checkGenerator();
            		if(b.getSpotRemaining() > 0) {
            		sectionsLecture.add(b);
            		}
            	} else {
            		sectionsLecture.add(b);
            	}
            }

            ArrayList<Course> sectionsStudio = new ArrayList<>();
            for (Course b: a.getSectionsStudio()) {
            	b.checkGenerator();
            	if(openCheck.equals("both")) { // does the same as above for loop for studios
            		if(b.getSpotRemaining() > 0 || b. getWaitRemaining() > 0) { // seats in lecture or waitlisr
            			sectionsLecture.add(b);
            		}
            	} else if (openCheck.contentEquals("one")){
            		if(b.getSpotRemaining() > 0) { // seats in lecture available
            		sectionsLecture.add(b); //
            		}
            	} else {
            		sectionsLecture.add(b); //no constraints
            	}
            }

            // adds arraylist of lectures of one course to iterator
            iterator.add((a.getSectionsLecture()));

            // tells program that studio present
            if ((a.getSectionsStudio()).size() > 0){
                iterator.add((a.getSectionsStudio()));
                isStudio = true;
            }

            CourseCritique b = new CourseCritique(courseName, courseNum);
            try {
                b.profAvgGpaGenerator(); // generates coursecritique gpa/prof name for the course
            }
            catch(Exception ex){

            }
            profGPA.addAll(b.getNameRating()); // adds it to profGPA


        }
       


        // [ [math1,math2, math3] , [cs1,cs2], [a1,a2] ]

        ArrayList<Integer> innerSize = new ArrayList<>();
        ArrayList<Integer> counter = new ArrayList<>();

        
     // iterate through every possible combination of the course checking conditions
        
        
        int totalCombination = 1; 
        int  noCourses = iterator.size();
        for (int i = 0; i < iterator.size();i++){
            innerSize.add(iterator.get(i).size());
            counter.add(0);
            totalCombination *= iterator.get(i).size();
        }

        //get all combinations and check em

        for(int i = 0; i < totalCombination; i++) {

            ArrayList<Course> tempTimeTable = new ArrayList<>();


            // gets 1 course from each arraylist
            for (int j = 0; j < noCourses; j++){
                tempTimeTable.add(iterator.get(j).get(counter.get(j)));
            }

            boolean DoesClash = false; // want this

            if (!timeConstraints) { // checks for time constraints (user input)
            	// checks if timings clash
                for (int ab = 0; ab < tempTimeTable.size(); ab++) {
                    for (int ac = ab + 1; ac < tempTimeTable.size(); ac++) {
                        if (tempTimeTable.get(ab).clash(tempTimeTable.get(ac))) {
                            DoesClash = true;
                        }
                    }
                }
            } else {

                for (int ab = 0; ab < tempTimeTable.size(); ab++) {
                    for (int ac = ab + 1; ac < tempTimeTable.size(); ac++) {
                        if (tempTimeTable.get(ab).clash(tempTimeTable.get(ac), this.startTime, this.endTime)) {
                            DoesClash = true;
                        }
                    }
                }
            }

            //matches lectures with studios/labs
            boolean matchLectureAndStudio = true;
            if (isStudio) {
                matchLectureAndStudio = false;
                for (int ab = 0; ab < tempTimeTable.size(); ab++) {
                    if (tempTimeTable.get(ab) instanceof Studio) {
                        for (int ac = 0; ac < tempTimeTable.size(); ac++) {
                            if(tempTimeTable.get(ac) instanceof Lecture && ( (tempTimeTable.get(ab)).getCourseId() ).equals( (tempTimeTable.get(ac)).getCourseId() ) && (tempTimeTable.get(ab)).getSection().indexOf((tempTimeTable.get(ac)).getSection()) != -1 ) {
                                matchLectureAndStudio = true;
                            }
                        }
                    }
                }
            }
            
            // ensures timetable has the CRN user wants

            boolean hasAllCrn = true;
            if(wantCrn.length > 0){
                for (String crn : wantCrn) {
                    hasAllCrn = false;
                    for  (int ab = 0; ab < tempTimeTable.size(); ab++) {
                        if ((tempTimeTable.get(ab)).getCrn().equals(crn)) {
                            hasAllCrn = true;
                        }
                    }
                    if (!hasAllCrn) {
                        break;
                    }
                }
            } else {
                hasAllCrn = true;
            }

            
            // ensures timetables doesnt have user specified CRN 
            
            // IDEA : what if we move this up while adding courses to iterator and remove the CRN then??
            boolean hasNoneCrn = true;
            if(noCrn.length > 0){
                for (String crn : noCrn) {
                    hasNoneCrn = true;
                    for  (int ab = 0; ab < tempTimeTable.size(); ab++) {
                        if ((tempTimeTable.get(ab)).getCrn().equals(crn)) {
                            hasNoneCrn = false;
                        }
                    }
                    if (!hasNoneCrn) {
                        break;
                    }
                }
            } else {
                hasNoneCrn = true;
            }

            // checks if all conditions met
            if(!DoesClash && hasAllCrn && hasNoneCrn && matchLectureAndStudio) {
                possibleTimeTable.add(tempTimeTable);
             
            }


            // moves to next possible course combination

            for(int incIndex = noCourses - 1; incIndex >= 0; --incIndex) {
                if (counter.get(incIndex) + 1 < innerSize.get(incIndex)) {
                    int value = counter.get(incIndex);
                    counter.set(incIndex, value + 1);
                    // None of the indices of higher significance need to be
                    // incremented, so jump out of this for loop at this point.
                    break;
                } else {
                    counter.set(incIndex, 0);
                }
            }

        }

    }

    /**
     * ArrayList<ArrayList<Course>> PossibleTimeTable
     */

    private List<Course> bestSchedule;
   
    

    /**
     * goes through all the possible timetables to find the one with the highest avg rating
     * @return best schedule
     */
    public  List<Course> getBestSchedule(){

        if(possibleTimeTable.size() < 0) {
            return null; // or send an error message here
        }
        
        RateMyProf.profRatingGenerator();

        double max= 0.0;
        for (ArrayList<Course> timeTable: possibleTimeTable){
            int count = 0;
            double sum = 0.0;
            for (Course section: timeTable) {
                if (section instanceof Lecture) {
                    count  = count + 1;
                    try {
                        sum = sum + ((Lecture)section).lectureRating(profGPA);
                    } catch (Exception ex){
                        // we need to create error for this
                    }
                }
            }
            double avg = sum/count;
            if (avg > max) {
                max = avg;
                bestSchedule = new ArrayList<>(timeTable);
            }
        }
        return bestSchedule;
    }


    /**
     * used to set term must be in format 202008/ 201908
     * @param term
     */
    public void setTerm(String term) {
        this.term = term;
    }
    public ArrayList<String[]> getProfGPA(){
    	return profGPA;
    }
}